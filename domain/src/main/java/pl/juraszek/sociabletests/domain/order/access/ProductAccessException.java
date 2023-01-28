package pl.juraszek.sociabletests.domain.order.access;

import lombok.Getter;

public class ProductAccessException extends RuntimeException {

   @Getter
   private final String productId;
   public ProductAccessException(String message, String productId) {
      super(message);
      this.productId = productId;
   }
}
