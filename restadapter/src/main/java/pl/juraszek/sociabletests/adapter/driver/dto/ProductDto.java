package pl.juraszek.sociabletests.adapter.driver.dto;

import org.springframework.lang.NonNull;
import pl.juraszek.sociabletests.domain.order.product.Product;

import java.util.List;

public record ProductDto(@NonNull String productId, int amount) {
   public static List<ProductDto> of(@NonNull List<Product> products) {
      return products.stream().map(ProductDto::of).toList();
   }

   public static ProductDto of(@NonNull Product product) {
      return new ProductDto(product.productId(), product.amount());
   }

   public static List<Product> convert(List<ProductDto> products) {
      return products.stream().map(productDto -> new Product(productDto.productId(), productDto.amount())).toList();
   }
}
