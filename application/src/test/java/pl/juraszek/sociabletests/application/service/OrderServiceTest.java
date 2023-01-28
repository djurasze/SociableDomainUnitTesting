package pl.juraszek.sociabletests.application.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.juraszek.sociabletests.application.SociableDomainTest;
import pl.juraszek.sociabletests.domain.client.ClientAccessException;
import pl.juraszek.sociabletests.domain.order.Order;
import pl.juraszek.sociabletests.domain.order.access.ProductAccessExceptions;
import pl.juraszek.sociabletests.domain.order.product.Product;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SociableDomainTest
class OrderServiceTest {

   @Autowired
   private OrderService orderService;
   private final String UUID_PATTERN = "^[0-9a-f]{8}-[0-9a-f]{4}-[1-5][0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}$";

   @Test
   void shouldMakeOrder() {
      //   given
      String clientId = "john_doe";
      String productId = "basic";
      int amount = 10;
      Product product = new Product(productId, amount);

      //      when
      Order order = orderService.makeOrder(product, clientId);

      //      then
      assertThat(order).isNotNull();
      assertThat(order.getId()).isNotBlank();
      //Assert that the string is a valid UUID
      assertThat(order.getId()).matches(UUID_PATTERN);
   }

   @Test
   void shouldNotMakeOrderWhenClientNotExists() {
      //   given
      String clientId = "none";
      String productId = "premium_1";
      int amount = 10;
      Product product = new Product(productId, amount);

      //      when
      assertThrows(ClientAccessException.class,
            () -> orderService.makeOrder(product, clientId));
   }

   @Test
   void shouldNotMakeOrderWhenProductNotAccessible() {
      //   given
      String clientId = "john_doe";
      String productId = "premium_1";
      int amount = 10;
      Product product = new Product(productId, amount);

      //      when
      assertThrows(ProductAccessExceptions.class,
            () -> orderService.makeOrder(product, clientId));
   }
}