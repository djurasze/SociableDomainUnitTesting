package pl.juraszek.sociabletests.domain.order.access;

import io.vavr.control.Either;
import pl.juraszek.sociabletests.domain.client.Client;
import pl.juraszek.sociabletests.domain.order.Order;

import java.util.List;

public class PremiumProductPolicy implements ProductAccessPolicy{

   private static final List<String> PREMIUM_PRODUCTS = List.of(
      "premium_1",
      "premium_2"
   );

   @Override
   public Either<ProductAccessException, Boolean> check(Order order, Client client) {
      if (isPremium(order) && !hasPremiumSubscription(client)) {
         return Either.left(new ProductAccessException(String.format("Client %s cannot order premium product %s", client.name(), order.getProductId())));
      }
      return Either.right(true);
   }

   private boolean hasPremiumSubscription(Client client) {
      return client.hasPremiumSubscription();
   }

   private boolean isPremium(Order order) {
      return PREMIUM_PRODUCTS.contains(order.getProductId());
   }
}
