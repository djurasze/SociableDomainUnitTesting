package pl.juraszek.sociabletests.application.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.juraszek.sociabletests.domain.order.access.OrderAccessPolicy;
import pl.juraszek.sociabletests.domain.order.access.PremiumProductPolicy;
import pl.juraszek.sociabletests.domain.order.access.ProductAccessPolicy;

import java.util.List;

@Configuration
public class ProductConfiguration {

   @Bean
   public OrderAccessPolicy orderAccessPolicy(List<ProductAccessPolicy> productPolicies) {
      return new OrderAccessPolicy(productPolicies);
   }

   @Bean
   public PremiumProductPolicy agePolicy() {
      return new PremiumProductPolicy();
   }
}
