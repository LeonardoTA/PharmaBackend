package pe.edu.upeu.PharmaBackend.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pe.edu.upeu.PharmaBackend.entity.Usuario;
import java.util.Optional;
import java.util.Set;
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByUsernameIgnoreCase(String username);
    boolean existsByUsernameIgnoreCase(String username);
    // Relacion 1 a 1: un empleado no puede tener dos usuarios
    boolean existsByEmpleadoId(Long empleadoId);
    // Ids de los empleados que ya tienen usuario
    @Query("select u.empleado.id from Usuario u")
    Set<Long> findEmpleadoIdsConUsuario();
}
