package com.example.app.storage.service;

import java.util.List;

/**
 * Interface represents common methods for service layer.
 * @param <T> data type
 * @param <D> data transfer object (DTO)
 * @param <I> identifier type
 */
public interface GenericService<T, D, I> {

    T add(D dto);
    List<T> getAll();
    T getById(I id);
    void change(T obj);
    void deleteById(I id);
    void delete(T obj);
    D map(T obj);

}
