package pl.juraszek.sociabletests.application.service;

import io.vavr.control.Either;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import pl.juraszek.sociabletests.domain.client.Client;
import pl.juraszek.sociabletests.domain.client.ClientProvider;
import pl.juraszek.sociabletests.domain.order.Order;
import pl.juraszek.sociabletests.domain.order.OrderRepository;
import pl.juraszek.sociabletests.domain.order.access.OrderAccessPolicy;
import pl.juraszek.sociabletests.domain.order.product.Product;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SolitaryOrderServiceTest {
   @Mock
   private ClientProvider clientProvider;
   @Mock
   private OrderAccessPolicy orderAccessPolicy;
   @Mock
   private OrderRepository orderRepository;

   @InjectMocks
   private OrderService orderService;

   @Test
   void shouldPlaceValidOrder() {
      UUID id = UUID.randomUUID();
      try (MockedStatic<UUID>  mockedUUID = mockStatic(UUID.class)) {
         // given
         mockedUUID.when(UUID::randomUUID).thenReturn(id);

         String productId = "product1";
         int amount = 2;
         String clientId = "client1";
         Order intermediateOrder = Order.init(clientId);
         List<Product> products = List.of(new Product(productId, amount));
         intermediateOrder.add(products);

         Order finalOrder = Order.init(clientId);
         finalOrder.add(products);
         ReflectionTestUtils.setField(finalOrder, "status", Order.Status.PLACED);



         Client client = new Client(clientId, false);

         // when
         when(clientProvider.fetchClient(clientId)).thenReturn(Optional.of(client));
         when(orderAccessPolicy.check(intermediateOrder, client)).thenReturn(Either.right(true));
         when(orderRepository.save(finalOrder)).thenReturn(finalOrder);
         Order result = orderService.placeOrder(products, clientId);

         // then
         assertThat(result).isNotNull();
         assertThat(result.getId()).isNotBlank();
         assertThat(result.getId()).isEqualTo(id.toString());
         verify(clientProvider).fetchClient(clientId);
         verify(orderAccessPolicy).check(finalOrder, client);
         verify(orderRepository).save(finalOrder);
      }
   }

}
