package pl.juraszek.sociabletests.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
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
    * Create order
    * Get Client
    * Check if client can make an order according to given policies
    */
   public Order makeOrder(@NonNull List<Product> products,@NonNull String clientId) {
      Order order = Order.init(clientId);
      order.add(products);
      return orderRepository.save(validateOrder(order, clientId));
   }

   public Order makeOrder(Product product, @NonNull String clientId) {
      Order order = Order.init(clientId);
      order.add(product);
      return orderRepository.save(validateOrder(order, clientId));
   }

   private Order validateOrder(Order order, String clientId) {
      return clientProvider.fetchClient(clientId)
            .map(client -> order.canBeMade(client, orderAccessPolicy))
            .map(orderState -> orderState.getOrElseThrow(productAccessExceptions -> {
               throw productAccessExceptions;
            }))
            .orElseThrow(() -> {
               throw new ClientAccessException(String.format("Client with given id %s not found!", clientId));
            });
   }
}