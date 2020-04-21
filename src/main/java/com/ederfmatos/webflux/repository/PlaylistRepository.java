package com.ederfmatos.webflux.repository;

import com.ederfmatos.webflux.document.Playlist;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface PlaylistRepository extends ReactiveMongoRepository<Playlist, String> {
}
