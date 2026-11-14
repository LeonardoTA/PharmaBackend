package pe.edu.upeu.PharmaBackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.upeu.PharmaBackend.entity.Producto;

import java.util.List;

public interface ProductoRepository extends JpaRepository<Producto, Long> {

    @org.springframework.data.jpa.repository.Query("select p from Producto p join fetch p.categoria where (:categoriaId is null or p.categoria.id = :categoriaId)")
    org.springframework.data.domain.Page<Producto> listarPagina(Long categoriaId, org.springframework.data.domain.Pageable pageable);

    boolean existsByNombreIgnoreCase(String nombre);
    boolean existsByNombreIgnoreCaseAndIdNot(String nombre, long id);

    List<Producto> findByCategoriaId(Long categoriaId);
    boolean existsByCategoriaId(Long categoriaId);

}
