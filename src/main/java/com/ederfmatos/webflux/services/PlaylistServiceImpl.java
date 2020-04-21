package com.ederfmatos.webflux.services;

import com.ederfmatos.webflux.document.Playlist;
import com.ederfmatos.webflux.repository.PlaylistRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class PlaylistServiceImpl implements PlaylistService {

    private final PlaylistRepository repository;

    public PlaylistServiceImpl(PlaylistRepository repository) {
        this.repository = repository;
    }

    @Override
    public Flux<Playlist> findAll() {
        return repository.findAll();
    }

    @Override
    public Mono<Playlist> findById(String id) {
        return repository.findById(id);
    }

    @Override
    public Mono<Playlist> save(Playlist playlist) {
        return repository.save(playlist);
    }
}
