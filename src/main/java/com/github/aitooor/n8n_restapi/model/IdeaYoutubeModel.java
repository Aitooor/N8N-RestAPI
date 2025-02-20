package com.github.aitooor.n8n_restapi.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class IdeaYoutubeModel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    private List<String> ideasUrls;
    private List<String> ideasImageUrls;
}
