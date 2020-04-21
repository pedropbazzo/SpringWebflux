package com.ederfmatos.webflux.controller;

import com.ederfmatos.webflux.document.Playlist;
import com.ederfmatos.webflux.services.PlaylistService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.time.Duration;

@RestController("playlist")
@RequestMapping("/playlists")
public class PlaylistController {

    private final PlaylistService service;

    public PlaylistController(PlaylistService service) {
        this.service = service;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Flux<Playlist> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<Playlist> findAll(@PathVariable String id) {
        return service.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Playlist> save(@RequestBody Playlist playlist) {
        return service.save(playlist);
    }

    @GetMapping(value="/events", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Tuple2<Long, Playlist>> events(){
        Flux<Long> interval = Flux.interval(Duration.ofSeconds(2));
        Flux<Playlist> playlistFlux = service.findAll();
        return Flux.zip(interval, playlistFlux);
    }

}
