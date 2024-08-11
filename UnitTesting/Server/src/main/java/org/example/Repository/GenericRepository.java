package org.example.Repository;

import org.example.Entity.FoodItem;

import java.util.Optional;

public interface GenericRepository<T, ID> {
    public T save(T resource);

    public T getById(ID id);

    public T update(ID id, T resource);

    public void delete(ID id);
}
