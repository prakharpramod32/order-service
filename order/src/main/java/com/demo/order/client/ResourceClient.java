package com.demo.order.client;

import com.demo.order.model.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class ResourceClient {


    @Autowired
    WebClient webClient;
    @Value("${service.resource.createResource}")
    private String endpoint;

    public Resource createResource(Resource resource) {
        return webClient.post()
                .uri(endpoint)
                .body(Mono.just(resource), Resource.class)
                .retrieve()
                .bodyToMono(Resource.class)
                .block();
    }


}
