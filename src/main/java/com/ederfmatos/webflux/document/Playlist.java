package com.ederfmatos.webflux.document;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Document
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Playlist implements Serializable {

    @Id
    private String id;
    private String name;

}
