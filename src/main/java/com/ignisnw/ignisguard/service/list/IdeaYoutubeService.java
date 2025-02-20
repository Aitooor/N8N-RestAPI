package com.ignisnw.ignisguard.service.list;

import com.ignisnw.ignisguard.dto.IdeaYoutubeModelDTO;
import com.ignisnw.ignisguard.repository.IdeaYoutubeRepository;
import com.ignisnw.ignisguard.service.CrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IdeaYoutubeService implements CrudService<IdeaYoutubeModelDTO> {

    @Autowired
    IdeaYoutubeRepository repository;

    @Override
    public List<IdeaYoutubeModelDTO> getAllItems() {
        return repository.findAll();
    }

    @Override
    public long count() { return repository.count(); }

    @Override
    public Optional<IdeaYoutubeModelDTO> getModel(String id) {
        return repository.findById(id);
    }

    @Override
    public boolean exist(String id) {
        return repository.existsById(id);
    }

    @Override
    public void create(IdeaYoutubeModelDTO ticketModel) {
        repository.save(ticketModel);
    }

    @Override
    public void update(String id, IdeaYoutubeModelDTO ticketModel) {
        if(!exist(id)) {
            return;
        }

        repository.save(ticketModel);
    }

    @Override
    public void delete(String id) { repository.deleteById(id); }
    @SuppressWarnings("unused")
    public void delete(IdeaYoutubeModelDTO ticketModel) { repository.delete(ticketModel); }
    @SuppressWarnings("unused")
    public void deleteAll() { repository.deleteAll(); }
    @SuppressWarnings("unused")
    public void deleteAll(Iterable<? extends String> ids) { repository.deleteAllById(ids); }
}
