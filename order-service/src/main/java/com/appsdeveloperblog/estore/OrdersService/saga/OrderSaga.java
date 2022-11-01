package com.appsdeveloperblog.estore.OrdersService.saga;

import com.appsdeveloperblog.estore.OrdersService.core.events.OrderCreatedEvent;
import com.sop.chapter9.core.command.ReserveProductCommand;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.spring.stereotype.Saga;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.UUID;
@Saga
public class OrderSaga {
    @Autowired
    private transient CommandGateway commandGateway;
    @StartSaga
    @SagaEventHandler(associationProperty = "orderId")
    @PostMapping
    public void handle(OrderCreatedEvent orderCreatedEvent){
        ReserveProductCommand reserveProductCommand = ReserveProductCommand.builder()
                .orderId(orderCreatedEvent.getOrderId())
                .productId(orderCreatedEvent.getProductId())
                .quantity(orderCreatedEvent.getQuantity())
                .userId(orderCreatedEvent.getUserId())
                .build();
        commandGateway.send(reserveProductCommand,(commandMessage, commandResultMessage) -> {
            if (commandResultMessage.isExceptional()){
                //werf
            }
        });

    }
}
