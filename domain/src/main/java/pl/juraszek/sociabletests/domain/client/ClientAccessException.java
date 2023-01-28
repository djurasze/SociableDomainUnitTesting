package pl.juraszek.sociabletests.domain.client;

public class ClientAccessException extends RuntimeException {
   public ClientAccessException(String message) {
      super(message);
   }
}
