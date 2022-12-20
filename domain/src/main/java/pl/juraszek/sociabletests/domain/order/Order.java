package pl.juraszek.sociabletests.domain.order;

import io.vavr.control.Either;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import pl.juraszek.sociabletests.domain.client.Client;
import pl.juraszek.sociabletests.domain.order.access.ProductAccessException;
import pl.juraszek.sociabletests.domain.order.access.ProductAccessExceptions;
import pl.juraszek.sociabletests.domain.order.access.ProductAccessPolicy;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class Order {
   @Getter
   private final String id;

   @Getter
   private final String productId;
   @Getter
   private final int amount;
   @Getter
   private final String clientId;

   public static @NonNull Order make(@NonNull String productId, int amount,@NonNull String clientId) {
      return new Order(UUID.randomUUID().toString(), productId, amount, clientId);
   }

   public Either<ProductAccessExceptions, Order> canBeMade(Client client, List<ProductAccessPolicy> accessPolicies) {
      List<Either<ProductAccessException, Boolean>> result = accessPolicies.stream()
            .map(productAccessPolicy -> productAccessPolicy.check(this, client))
            .toList();
      List<ProductAccessException> errors = result.stream()
            .filter(Either::isLeft).map(Either::getLeft)
            .collect(Collectors.toList());
      return errors.isEmpty() ? Either.right(this) : Either.left(new ProductAccessExceptions(errors));
   }
}
