package pl.juraszek.sociabletests.application.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.juraszek.sociabletests.domain.order.access.PremiumProductPolicy;

@Configuration
public class ProductConfiguration {

   @Bean
   public PremiumProductPolicy agePolicy() {
      return new PremiumProductPolicy();
   }
}
