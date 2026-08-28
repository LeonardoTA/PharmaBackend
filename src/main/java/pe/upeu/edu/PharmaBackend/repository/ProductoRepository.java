package pe.upeu.edu.PharmaBackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.upeu.edu.PharmaBackend.entity.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Long> {

    boolean existsByNombreIgnoreCase(String nombre);

    boolean existsByNombreIgnoreCaseAndIdNot(String nombre, Long id);
}
