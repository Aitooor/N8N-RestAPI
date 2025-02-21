package com.github.aitooor.n8n_restapi.repository;

import com.github.aitooor.n8n_restapi.dto.IdeaYoutubeModelDTO;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IdeaYoutubeRepository extends MongoRepository<IdeaYoutubeModelDTO, String> {
}
