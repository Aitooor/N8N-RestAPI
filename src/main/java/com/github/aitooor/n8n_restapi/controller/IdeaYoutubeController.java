package com.github.aitooor.n8n_restapi.controller;

import com.github.aitooor.n8n_restapi.dto.IdeaYoutubeModelDTO;
import com.github.aitooor.n8n_restapi.service.list.IdeaYoutubeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/youtube_shorts")
public class IdeaYoutubeController {

    @Autowired
    protected IdeaYoutubeService ideaYoutubeService;

    @GetMapping(value="", produces = "application/json")
    public List<IdeaYoutubeModelDTO> getYoutubeIdeas() {
        return ideaYoutubeService.getAllItems();
    }

    @GetMapping(value="/sizes", produces = "application/json")
    public long getYoutubeIdeasSizes() {
        return ideaYoutubeService.count();
    }

    @GetMapping(value="/randomModel", produces = "application/json")
    public ResponseEntity<IdeaYoutubeModelDTO> getRandomYoutubeIdea() {
        Optional<IdeaYoutubeModelDTO> idea = ideaYoutubeService.getRandomModel();
        return idea.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping(value="/nextModel", produces = "application/json")
    public ResponseEntity<IdeaYoutubeModelDTO> getNextYoutubeIdea() {
        Optional<IdeaYoutubeModelDTO> idea = ideaYoutubeService.getNextModel();
        return idea.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping(value="/{id}", produces = "application/json")
    public ResponseEntity<IdeaYoutubeModelDTO> getYoutubeIdeaById(@PathVariable String id) {
        Optional<IdeaYoutubeModelDTO> idea = ideaYoutubeService.getModel(id);
        return idea.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping(value = "")
    public ResponseEntity<String> saveYoutubeIdea(@RequestBody IdeaYoutubeModelDTO idea) {
        try {
            if (idea == null) {
                return new ResponseEntity<>("IdeaYoutubeModelDTO data is missing", HttpStatus.BAD_REQUEST);
            }

            ideaYoutubeService.create(idea);
            return new ResponseEntity<>("IdeaYoutubeModelDTO saved successfully", HttpStatus.OK);
        } catch (Exception e) {
            // Log the exception or handle it as needed
            return new ResponseEntity<>("Failed to save idea", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteYoutubeIdea(@PathVariable String id) {
        try {
            Optional<IdeaYoutubeModelDTO> existingIdea = ideaYoutubeService.getModel(id);

            if (existingIdea.isPresent()) {
                ideaYoutubeService.delete(id);
                return new ResponseEntity<>("IdeaYoutubeModelDTO deleted successfully", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("IdeaYoutubeModelDTO not found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            // Log the exception or handle it as needed
            return new ResponseEntity<>("Failed to delete idea", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}