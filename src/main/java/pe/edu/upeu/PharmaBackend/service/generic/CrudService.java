package pe.edu.upeu.PharmaBackend.service.generic;

import java.util.Optional;

public interface CrudService <REQ, RES, ID>{
    RES create(REQ t);
    RES update(ID id, REQ t);
    RES read(ID id);
    void delete(ID id);
    Iterable<RES> readAll();
}

