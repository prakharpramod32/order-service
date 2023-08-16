package com.demo.order.service;

import com.demo.order.client.ResourceClient;
import com.demo.order.exception.OrderInvalidException;
import com.demo.order.model.Order;
import com.demo.order.model.Resource;
import com.demo.order.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class OrderService {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ResourceClient resourceClient;

    private static final String MIXING = "MIXING";
    private static final String PACKING = "PACKING";
    private static final String FREEZING = "FREEZING";
    List<String> validResources = new ArrayList<>(3);


    public Order createOrder(Order order) throws OrderInvalidException {

        Order createdOrder = null;

        val mixingResource = resourceClient.createResource(getMixingResource());
        val freezingResource = resourceClient.createResource(getFreezingResource());
        val packingResource = resourceClient.createResource(getPackingResource());

        if (validateOrder(List.of(mixingResource.getResourceId(), freezingResource.getResourceId(), packingResource.getResourceId()))) {
            createdOrder = orderRepository.save(order);
        } else {
            throw new OrderInvalidException("Order could not be processed");
        }
        return createdOrder;
    }

    public Order getOrder(String orderId) {
        return orderRepository.findById(orderId).orElse(null);
    }

    private Resource getMixingResource() {
        return Resource.builder()
                .resourceId(MIXING)
                .resourceName("MIXING_MACHINE")
                .build();
    }

    private Resource getFreezingResource() {
        return Resource.builder()
                .resourceId(FREEZING)
                .resourceName("FREEZING_MACHINE")
                .build();
    }

    private Resource getPackingResource() {
        return Resource.builder()
                .resourceId(PACKING)
                .resourceName("PACKING_MACHINE")
                .build();
    }

    private boolean validateOrder(List<String> resourceNames) {
        validResources.add(MIXING);
        validResources.add(FREEZING);
        validResources.add(PACKING);
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return resourceNames.containsAll(validResources);

    }
}
