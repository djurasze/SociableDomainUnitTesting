package pl.juraszek.sociabletests.domain.client;

import org.springframework.lang.NonNull;

import java.util.Optional;

public class ClientProviderStub implements ClientProvider {
   @Override
   public @NonNull Optional<Client> fetchClient(@NonNull String name) {
      return Optional.ofNullable(switch (name) {
         case "john_doe" -> new Client(name, false);
         case "jane_doe" -> new Client(name, true);
         default -> null;
      });

   }
}
