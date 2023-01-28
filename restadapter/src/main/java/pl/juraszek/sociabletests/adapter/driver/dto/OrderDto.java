package pl.juraszek.sociabletests.adapter.driver.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import pl.juraszek.sociabletests.domain.order.Order;

import java.util.List;

public record OrderDto(@JsonProperty(access = JsonProperty.Access.READ_ONLY) String id,
                       @NotNull List<ProductDto> products, @NotNull String clientId) {
   public static OrderDto of(Order order) {
      return new OrderDto(order.getId(), ProductDto.of(order.getProducts()), order.getClientId());
   }
}
