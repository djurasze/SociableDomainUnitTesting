package pl.juraszek.sociabletests.adapter.driver.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.juraszek.sociabletests.adapter.driver.dto.OrderDto;
import pl.juraszek.sociabletests.adapter.driver.dto.ProductDto;
import pl.juraszek.sociabletests.application.service.OrderService;

@RestController
@RequestMapping("/api/orders")
@Slf4j
@RequiredArgsConstructor
public class OrderController {

   private final OrderService orderService;

   @PostMapping
   public OrderDto order(@RequestBody @Valid OrderDto orderDto) {
      log.info("Creating order {}", orderDto);
      var order = orderService.makeOrder(ProductDto.convert(orderDto.products()), orderDto.clientId());
      return OrderDto.of(order);
   }

}
