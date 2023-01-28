package pl.juraszek.sociabletests.domain.order.access;

import io.vavr.control.Either;
import pl.juraszek.sociabletests.domain.client.Client;
import pl.juraszek.sociabletests.domain.order.product.Product;

import java.util.List;

public class PremiumProductPolicy implements ProductAccessPolicy {

   private static final List<String> PREMIUM_PRODUCTS = List.of("premium_1", "premium_2");

   @Override
   public Either<ProductAccessException, Boolean> check(Product product, Client client) {
      if (isPremium(product) && !hasPremiumSubscription(client)) {
         return Either.left(new ProductAccessException(
               String.format("Client %s cannot access premium product %s", client.name(), product.productId()), product.productId()));
      }
      return Either.right(true);
   }

   private boolean hasPremiumSubscription(Client client) {
      return client.hasPremiumSubscription();
   }

   private boolean isPremium(Product product) {
      return PREMIUM_PRODUCTS.contains(product.productId());
   }
}
