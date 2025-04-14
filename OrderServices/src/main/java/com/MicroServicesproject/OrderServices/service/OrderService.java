package com.MicroServicesproject.OrderServices.service;

import com.MicroServicesproject.OrderServices.client.InventoryClient;
import com.MicroServicesproject.OrderServices.dto.OrderLineItemsDto;
import com.MicroServicesproject.OrderServices.dto.OrderRequest;
import com.MicroServicesproject.OrderServices.event.OrderPlacedEvent;
import com.MicroServicesproject.OrderServices.model.Order;
import com.MicroServicesproject.OrderServices.model.OrderLineItems;
import com.MicroServicesproject.OrderServices.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;
    private final InventoryClient inventoryClient;
    private final KafkaTemplate<String, OrderPlacedEvent> kafkaTemplate;

    public void placeOrder(OrderRequest orderRequest){

        var isProductInStock = inventoryClient.isInStock(orderRequest.skuCode(), orderRequest.quantity());

        if(isProductInStock){
            Order order = new Order();
            order.setOrderNumber(UUID.randomUUID().toString());

            List<OrderLineItems> orderLineItems = orderRequest.getOrderLineItemsDtoList()
                    .stream()
                    .map(this::mapToDto)
                    .toList();

            order.setOrderLineItemsList(orderLineItems);
            orderRepository.save(order);

            //Send Message to Kafka topic
            OrderPlacedEvent orderPlacedEvent = new OrderPlacedEvent(order.getOrderNumber(), orderRequest.userDetails.email());
            log.info("Start sending email {}", orderPlacedEvent);
            kafkaTemplate.send("order-placed", orderPlacedEvent);
            log.info("End sending email {}", orderPlacedEvent);
        }
        else {
            throw new RuntimeException("Product with skuCode " + orderRequest.skuCode() + " is not in stock");
        }


    }
    private OrderLineItems mapToDto(OrderLineItemsDto orderLineItemsDto){
        OrderLineItems orderLineItems = new OrderLineItems();
        orderLineItems.setPrice(orderLineItems.getPrice());
        orderLineItems.setQuantity(orderLineItems.getQuantity());
        orderLineItems.setSkuCode(orderLineItems.getSkuCode());
        return orderLineItems;
    }
}

