package pl.juraszek.sociabletests.adapter.driver.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import pl.juraszek.sociabletests.domain.order.Order;

public record OrderDto(@JsonProperty(access = JsonProperty.Access.READ_ONLY) String id, @NotNull String productId,
                       @NotNull String clientId, @NotNull int amount) {
   public static OrderDto of(Order order) {
      return new OrderDto(order.getId(), order.getProductId(), order.getClientId(), order.getAmount());
   }

}
