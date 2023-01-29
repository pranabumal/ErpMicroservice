package com.pranab.order.service;

import com.pranab.order.dto.InventoryResponse;
import com.pranab.order.dto.OrderLineItemDto;
import com.pranab.order.dto.OrderRequest;
import com.pranab.order.model.Order;
import com.pranab.order.model.OrderLineItems;
import com.pranab.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {


    private final OrderRepository orderRepository;

    private final WebClient.Builder webClientBuilder;
    public void placeOrder(OrderRequest orderRequest){
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());
        List<OrderLineItems> orderLineItems = orderRequest.getOrderLineItemsList()
                .stream()
                .map(this::mapToDto)
                .toList();
        order.setOrderLineItemsList(orderLineItems);

        List<String> codes = order.getOrderLineItemsList().stream().map(OrderLineItems::getCode).toList();


        //Call Inventory Service, and place order if product is in stock

        InventoryResponse [] inventoryResponsesArray = webClientBuilder.build().get()
                .uri("http://inventory-service/api/inventory", uriBuilder -> uriBuilder.queryParam("code",codes).build() )
                .retrieve()
                .bodyToMono(InventoryResponse[].class)
                .block();

        if (inventoryResponsesArray != null) {
            boolean allProductInStock = Arrays.stream(inventoryResponsesArray).allMatch(InventoryResponse::getIsInStock);
            if(allProductInStock){
                orderRepository.save(order);
            }else {
                throw new IllegalArgumentException("Product not in stock");
            }
        }else {
            throw new IllegalArgumentException("Product not in stock");
        }



    }

    private OrderLineItems mapToDto(OrderLineItemDto orderLineItemDto) {
        OrderLineItems orderLineItems = new OrderLineItems();
        orderLineItems.setCode(orderLineItemDto.getCode());
        orderLineItems.setPrice(orderLineItemDto.getPrice());
        orderLineItems.setQuantity(orderLineItemDto.getQuantity());
        return orderLineItems;
    }
}
