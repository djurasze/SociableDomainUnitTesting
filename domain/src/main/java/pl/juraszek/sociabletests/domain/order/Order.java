package pl.juraszek.sociabletests.domain.order;

import io.vavr.control.Either;
import lombok.*;
import org.springframework.lang.NonNull;
import pl.juraszek.sociabletests.domain.client.Client;
import pl.juraszek.sociabletests.domain.order.access.OrderAccessPolicy;
import pl.juraszek.sociabletests.domain.order.access.ProductAccessExceptions;
import pl.juraszek.sociabletests.domain.order.product.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
@EqualsAndHashCode
@ToString
public class Order {
   @Getter
   private final String id;

   @Getter
   private final List<Product> products;

   @Getter
   private final String clientId;

   @Getter
   private Status status;

   public static @NonNull Order init(@NonNull String clientId) {
      return new Order(UUID.randomUUID().toString(), new ArrayList<>(), clientId, Status.NEW);
   }

   public void add(Product product) {
      if (isOrderActive()) {
         products.add(product);
      }
   }

   private boolean isOrderActive() {
      return status.equals(Status.NEW);
   }

   public void add(List<Product> newProducts) {
      if (isOrderActive()) {
         products.addAll(newProducts);
      }
   }

   public Either<ProductAccessExceptions, Order> place(Client client, OrderAccessPolicy accessPolicy) {
      return accessPolicy.check(this, client).map(this::orderSuccessfullyPlaced);
   }

   private Order orderSuccessfullyPlaced(Boolean result) {
      this.status = Status.PLACED;
      return this;
   }

   public String renderReport() {
      return String.format("Order report:%n %s",
            products.stream().map(Product::render).collect(Collectors.joining("\n")));
   }

   public void reject() {
      if (isOrderActive()) {
         status = Status.REJECTED;
      }
   }

   public enum Status {
      NEW,
      PLACED,
      REJECTED
   }
}
