package pl.juraszek.sociabletests.domain.order.access;

import io.vavr.control.Either;
import pl.juraszek.sociabletests.domain.client.Client;
import pl.juraszek.sociabletests.domain.order.Order;

public interface ProductAccessPolicy {
   Either<ProductAccessException, Boolean> check(Order order, Client client);
}
