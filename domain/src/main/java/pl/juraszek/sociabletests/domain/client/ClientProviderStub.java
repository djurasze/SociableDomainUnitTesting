package pl.juraszek.sociabletests.domain.client;

import org.springframework.lang.NonNull;

import java.util.Optional;

public class ClientProviderStub implements ClientProvider {
   @Override
   public @NonNull Optional<Client> fetchClient(@NonNull String name) {
      return "john_doe".equals(name) ? Optional.of(new Client(name, false)) : Optional.empty();
   }
}
