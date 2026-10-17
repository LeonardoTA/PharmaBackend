package pe.edu.upeu.PharmaBackend.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.upeu.PharmaBackend.entity.Empleado;
public interface EmpleadoRepository extends JpaRepository<Empleado, Long> {
}
