package pl.juraszek.sociabletests.application.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import pl.juraszek.sociabletests.domain.order.OrderRepository;
import pl.juraszek.sociabletests.domain.order.OrderRepositoryStub;

@Profile("mock-order-repository")
@Configuration
public class RepositoryMockConfiguration {

   @Bean
   public OrderRepository orderRepository() {
      return new OrderRepositoryStub();
   }
}
