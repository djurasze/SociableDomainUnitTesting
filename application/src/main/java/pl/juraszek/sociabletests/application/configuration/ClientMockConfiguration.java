package pl.juraszek.sociabletests.application.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import pl.juraszek.sociabletests.domain.client.ClientProvider;
import pl.juraszek.sociabletests.domain.client.ClientProviderStub;

@Profile("mock-client-service")
@Configuration
public class ClientMockConfiguration {

   @Bean
   public ClientProvider clientProvider() {
      return new ClientProviderStub();
   }
}
