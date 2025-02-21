package com.github.aitooor.n8n_restapi.service;

import java.util.List;
import java.util.Optional;

public interface CrudService<T> {
    List<T> getAllItems();
    long count();
    Optional<T> getRandomModel();
    Optional<T> getNextModel();
    Optional<T> getModel(String id);
    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    boolean exist(String id);
    void create(T t);
    void update(String id, T t);
    void delete(String id);
}