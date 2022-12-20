package pl.juraszek.sociabletests.domain.client;

import org.springframework.lang.NonNull;

import java.util.Optional;

public interface ClientProvider {

   @NonNull Optional<Client> fetchClient(@NonNull String name);
}
