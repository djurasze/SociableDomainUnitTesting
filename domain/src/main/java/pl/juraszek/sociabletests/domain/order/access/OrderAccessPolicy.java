package pl.juraszek.sociabletests.domain.order.access;

import io.vavr.control.Either;
import lombok.RequiredArgsConstructor;
import pl.juraszek.sociabletests.domain.client.Client;
import pl.juraszek.sociabletests.domain.order.Order;
import pl.juraszek.sociabletests.domain.order.product.Product;

import java.util.List;

@RequiredArgsConstructor
public class OrderAccessPolicy {

   private final List<ProductAccessPolicy> productPolicies;

   public Either<ProductAccessExceptions, Boolean> check(Order order, Client client) {
      List<Either<ProductAccessExceptions, Boolean>> results = order.getProducts()
            .stream()
            .map(product -> check(product, client))
            .toList();

      List<ProductAccessException> errors = results.stream()
            .filter(Either::isLeft)
            .map(Either::getLeft)
            .flatMap(productAccessExceptions -> productAccessExceptions.getExceptions().stream())
            .toList();

      return errors.isEmpty() ? Either.right(true) : Either.left(new ProductAccessExceptions(errors));

   }

   private Either<ProductAccessExceptions, Boolean> check(Product product, Client client) {
      List<Either<ProductAccessException, Boolean>> result = productPolicies.stream()
            .map(productAccessPolicy -> productAccessPolicy.check(product, client))
            .toList();
      List<ProductAccessException> errors = result.stream().filter(Either::isLeft).map(Either::getLeft).toList();
      return errors.isEmpty() ? Either.right(true) : Either.left(new ProductAccessExceptions(errors));

   }

}
