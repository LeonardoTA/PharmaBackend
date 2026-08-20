package pe.upeu.edu.PharmaBackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.upeu.edu.PharmaBackend.entity.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

    boolean existsByNombreIgnoreCase(String nombre);

    boolean existsByNombreIgnoreCaseAndIdNot(String nombre, Long id);
}
