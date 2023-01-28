package pl.juraszek.sociabletests.domain.order.access;

import io.vavr.control.Either;
import pl.juraszek.sociabletests.domain.client.Client;
import pl.juraszek.sociabletests.domain.order.product.Product;

public interface ProductAccessPolicy {
   Either<ProductAccessException, Boolean> check(Product product, Client client);
}
