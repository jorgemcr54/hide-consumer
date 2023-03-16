package com.personal.hideconsumer.infraestructure.entrypoints;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.personal.hideconsumer.domain.model.PlaceToHide;
import io.cloudevents.CloudEvent;
import io.cloudevents.CloudEventData;
import io.cloudevents.core.builder.CloudEventBuilder;
import io.cloudevents.core.provider.EventFormatProvider;
import io.cloudevents.jackson.JsonFormat;
import org.reactivecommons.async.api.HandlerRegistry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.UUID;


@Configuration
public class ListenerConfig {

    @Value("${place.floor}")
    private int floor;

    @Value("${place.room}")
    private String room;

    @Bean
    public HandlerRegistry handlerRegistry(){
        return HandlerRegistry.register()
                .listenEvent("place.searched", event -> {

                    CloudEvent data = EventFormatProvider
                            .getInstance()
                            .resolveFormat(JsonFormat.CONTENT_TYPE)
                            .deserialize(event.getData());
                    byte[] bytes = data.getData().toBytes();
                    String stringData = new String(bytes, StandardCharsets.UTF_8);
                    try {
                        PlaceToHide placeToHide = new ObjectMapper().readValue(stringData, PlaceToHide.class);
                        if (placeToHide.getRoom().equals(room) && placeToHide.getFloor() == floor){
                            System.out.println("ME ENCONTRARON");
                        }
                        else {
                            System.out.println("NO ME ENCONTRARON");
                        }
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }

                    return Mono.empty();
                }, byte[].class)
                .handleCommand("player.eliminated", command ->{
                    System.out.println("HE SIDO ELIMINAD@");
                    return Mono.empty();
                }, String.class)
                .serveQuery("player.searched.query", query ->{
                    if (query.getRoom().equals(room) && query.getFloor() == floor){
                        return Mono.just("Si estoy aca");
                    }
                    else{
                        return Mono.just("No estoy aca");
                    }
                }, PlaceToHide.class);
    }
}
