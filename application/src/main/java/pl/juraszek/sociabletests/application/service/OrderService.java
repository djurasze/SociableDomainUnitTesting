package pl.juraszek.sociabletests.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import pl.juraszek.sociabletests.domain.client.Client;
import pl.juraszek.sociabletests.domain.client.ClientAccessException;
import pl.juraszek.sociabletests.domain.client.ClientProvider;
import pl.juraszek.sociabletests.domain.order.Order;
import pl.juraszek.sociabletests.domain.order.OrderRepository;
import pl.juraszek.sociabletests.domain.order.access.OrderAccessPolicy;
import pl.juraszek.sociabletests.domain.order.product.Product;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

   private final ClientProvider clientProvider;
   private final OrderAccessPolicy orderAccessPolicy;
   private final OrderRepository orderRepository;

   /**
    * Create order with empty product basket
    * Add products to order
    * Get Client
    * Check if client can make an order according to given policies
    * Place order
    */
   public Order placeOrder(@NonNull List<Product> products, @NonNull String clientId) {
      Order order = Order.init(clientId);
      order.add(products);

      clientProvider.fetchClient(clientId)
            .ifPresentOrElse(client -> placeOrderForClient(order, client), () -> handleNotExistentClient(clientId));

      return orderRepository.save(order);
   }

   private void placeOrderForClient(Order order, Client client) {
      order.place(client, orderAccessPolicy).getOrElse(() -> handleOrderPlacementFailure(order));
   }

   private Order handleOrderPlacementFailure(Order order) {
      order.reject();
      return order;
   }

   private void handleNotExistentClient(String clientId) {
      throw new ClientAccessException(String.format("Client with given id %s not found!", clientId));
   }

}