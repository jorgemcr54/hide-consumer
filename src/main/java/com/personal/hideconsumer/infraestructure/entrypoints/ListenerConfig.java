package com.personal.hideconsumer.infraestructure.entrypoints;

import com.personal.hideconsumer.domain.model.PlaceToHide;
import org.reactivecommons.async.api.HandlerRegistry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Mono;


@Configuration
public class ListenerConfig {

    @Value("${place.floor}")
    private int floor;

    @Value("${place.room}")
    private String room;

    @Bean
    public HandlerRegistry handlerRegistry(){
        return HandlerRegistry.register()
                .listenEvent("place.searched", event ->{
                    if (event.getData().getRoom().equals(room) && event.getData().getFloor()==floor){
                        System.out.println("ME ENCONTRARON");
                    }
                    else {
                        System.out.println("NO ME ENCONTRARON");
                    }
                    return Mono.empty();
                }, PlaceToHide.class)
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
