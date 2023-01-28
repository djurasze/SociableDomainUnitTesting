package pl.juraszek.sociabletests.domain.order.product;

import org.springframework.lang.NonNull;

public record Product(@NonNull String productId, int amount) {

   public String render() {
      return String.format("ProductId: %s, amount: %s", productId, amount);
   }
}
