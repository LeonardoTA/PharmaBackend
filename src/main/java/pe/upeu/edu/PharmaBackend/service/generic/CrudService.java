package pe.upeu.edu.PharmaBackend.service.generic;

import java.util.Optional;

public interface CrudService<T, ID> {
    T create(T t);
    T update(T t);
    Optional<T> read(ID id);
    void delete(ID id);
    Iterable<T> readAll();
}
