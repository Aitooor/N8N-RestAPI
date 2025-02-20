package com.ignisnw.ignisguard.dto;

import com.ignisnw.ignisguard.model.IdeaYoutubeModel;
import lombok.*;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Setter
@Document(collection = "idea_youtube_models")
@TypeAlias("idea_youtube_model")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class IdeaYoutubeModelDTO {

    private String id;
    private List<String> ideasUrls;
    private List<String> ideasImageUrls;


    @SuppressWarnings("unused")
    public static IdeaYoutubeModelDTO fromEntity(IdeaYoutubeModel ideaYoutubeModel) {
        return new IdeaYoutubeModelDTO(
                ideaYoutubeModel.getId(),
                ideaYoutubeModel.getIdeasUrls(),
                ideaYoutubeModel.getIdeasImageUrls()
        );
    }
}
