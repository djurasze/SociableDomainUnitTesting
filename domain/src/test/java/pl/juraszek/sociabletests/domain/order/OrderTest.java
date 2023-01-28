package pl.juraszek.sociabletests.domain.order;

import org.junit.jupiter.api.Test;
import pl.juraszek.sociabletests.domain.order.product.Product;

import static org.assertj.core.api.Assertions.assertThat;

class OrderTest {

   @Test
   void shouldRenderReportForSingleProduct() {
     // given
      Order order = Order.init("client_1");
      Product product = new Product("product_1", 100);
      order.add(product);
      String expectedReport = String.format("Order report:%n %s", product.render());

      // when
      String report = order.renderReport();

      // then
     assertThat(report)
       .isEqualTo(expectedReport);
   }
}