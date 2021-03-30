package com.example.app.storage.dao;

import java.util.List;

/**
 * Generic interface representing CRUD operations over the data.
 * @param <T> data type
 * @param <I> identifier type
 */
public interface Crud<T, I> {


    I save(T obj);
    T get(I id);
    List<T> list();
    void update(T obj);
    void delete(T obj);

}
