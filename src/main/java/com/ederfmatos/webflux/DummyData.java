package com.ederfmatos.webflux;

//import com.ederfmatos.webflux.document.Playlist;
//import com.ederfmatos.webflux.repository.PlaylistRepository;
//import reactor.core.publisher.Flux;
//import java.util.UUID;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DummyData implements CommandLineRunner {

//    private final PlaylistRepository repository;

//    public DummyData(PlaylistRepository repository) {
//        this.repository = repository;
//    }

    @Override
    public void run(String... args) throws Exception {
//        repository.deleteAll()
//                .thenMany(
//                        Flux.just("API Rest Spring Boot", "Deploy de uma aplicação java", "Java 8", "Github", "Spring Security",
//                                "Web Service Restfull", "Bean no Spring Framework")
//                        .map(name -> new Playlist(UUID.randomUUID().toString(), name))
//                        .flatMap(repository::save))
//                        .subscribe(System.out::println);
    }
}
