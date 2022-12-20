package pl.juraszek.sociabletests.domain.order.access;

import org.springframework.lang.NonNull;

import java.util.List;

public class ProductAccessExceptions extends RuntimeException {

   private final List<ProductAccessException> exceptions;

   public ProductAccessExceptions(@NonNull List<ProductAccessException> exceptions) {
      this.exceptions = exceptions;
   }
}
