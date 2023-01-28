package pl.juraszek.sociabletests.domain.order;

import io.vavr.control.Either;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import pl.juraszek.sociabletests.domain.client.Client;
import pl.juraszek.sociabletests.domain.order.access.OrderAccessPolicy;
import pl.juraszek.sociabletests.domain.order.access.ProductAccessExceptions;
import pl.juraszek.sociabletests.domain.order.product.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class Order {
   @Getter
   private final String id;

   @Getter
   private final List<Product> products;

   @Getter
   private final String clientId;

   public static @NonNull Order init(@NonNull String clientId) {
      return new Order(UUID.randomUUID().toString(), new ArrayList<>(), clientId);
   }

   public void add(Product product) {
      products.add(product);
   }

   public void add(List<Product> newProducts) {
      products.addAll(newProducts);
   }

   public Either<ProductAccessExceptions, Order> canBeMade(Client client, OrderAccessPolicy accessPolicy) {
      return accessPolicy.check(this, client).map(aBoolean -> this);
   }

   public String renderReport() {
      return String.format("Order report:%n %s",products.stream().map(Product::render).collect(Collectors.joining("\n")));
   }
}
