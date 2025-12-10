package com.abhishek.order_service.service;

import com.abhishek.order_service.dto.InventoryResponse;
import com.abhishek.order_service.dto.OrderLineItemsDto;
import com.abhishek.order_service.dto.OrderRequest;
import com.abhishek.order_service.model.Order;
import com.abhishek.order_service.model.OrderLineItems;
import com.abhishek.order_service.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
    private final WebClient webClient;
    public void placeOrder(OrderRequest orderRequest){
        Order order=new Order();
        order.setOrderNumber(UUID.randomUUID().toString());

        System.out.println("at line 29");

        List<OrderLineItems> orderLineItemsList=orderRequest.getOrderLineItemsDtoList()
                .stream()
                .map(this::maptoDto)
                .toList();

        order.setOrderLineItemsList(orderLineItemsList);

        System.out.println("Here then what about at 34");

        List<String> skucodes=order.getOrderLineItemsList().stream().map(OrderLineItems::getSkuCode).toList();

        //Asking the inventory service if the product is in stock
        InventoryResponse[] inventoryResponsesArray = webClient.get()
                .uri("http://localhost:8082/api/inventory", uriBuilder -> uriBuilder.queryParam("skuCode", skucodes).build())
                .retrieve()
                .bodyToMono(InventoryResponse[].class)
                .block(); //To make the request synchronous

        System.out.println("Here i cam");
        System.out.println(Arrays.toString(inventoryResponsesArray));

        Boolean allProduct=Arrays.stream(inventoryResponsesArray).allMatch(InventoryResponse::isInStock);
        if(allProduct) {
            orderRepository.save(order);
        }
        else{
            throw new IllegalArgumentException("The required product is out of stock");
        }
    }

    private OrderLineItems maptoDto(OrderLineItemsDto orderLineItemsDto){
        OrderLineItems orderLineItems=new OrderLineItems();
        orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
        orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());
        orderLineItems.setPrice(orderLineItemsDto.getPrice());
        return orderLineItems;
    }
}
