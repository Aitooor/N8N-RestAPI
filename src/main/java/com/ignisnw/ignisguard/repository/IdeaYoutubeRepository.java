package com.ignisnw.ignisguard.repository;

import com.ignisnw.ignisguard.dto.IdeaYoutubeModelDTO;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IdeaYoutubeRepository extends MongoRepository<IdeaYoutubeModelDTO, String> {

}
