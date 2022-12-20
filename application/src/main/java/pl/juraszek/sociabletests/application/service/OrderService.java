package pl.juraszek.sociabletests.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.juraszek.sociabletests.domain.client.ClientProvider;
import pl.juraszek.sociabletests.domain.order.Order;
import pl.juraszek.sociabletests.domain.order.OrderRepository;
import pl.juraszek.sociabletests.domain.order.access.ProductAccessPolicy;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

   private final ClientProvider clientProvider;
   private final List<ProductAccessPolicy> accessPolicies;
   private final OrderRepository orderRepository;

   /**
    * Create order
    * Get Client
    * Check if client can make an order according to given policies
    */
   public Order makeOrder(String productId, int amount, String clientId) {
      Order order = Order.make(productId, amount, clientId);
      clientProvider.fetchClient(clientId)
            .map(client -> order.canBeMade(client, accessPolicies))
            .map(orderState -> orderState.getOrElseThrow(productAccessExceptions -> {
               throw productAccessExceptions;
            }));
      return orderRepository.save(order);
   }
}