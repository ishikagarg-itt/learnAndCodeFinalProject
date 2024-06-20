package org.example.Repository;

import java.util.Optional;

public interface GenericRepository<T, ID> {
    public Optional<T> findByName(String name);
    public T save(T resource);

    public T findById(ID id);
}
