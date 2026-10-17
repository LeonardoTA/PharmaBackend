package pe.edu.upeu.PharmaBackend.mapper;
import org.springframework.stereotype.Component;
import pe.edu.upeu.PharmaBackend.dto.auth.EmpleadoResponseDTO;
import pe.edu.upeu.PharmaBackend.dto.auth.RolResponseDTO;
import pe.edu.upeu.PharmaBackend.dto.auth.UsuarioResponseDTO;
import pe.edu.upeu.PharmaBackend.entity.Empleado;
import pe.edu.upeu.PharmaBackend.entity.Rol;
import pe.edu.upeu.PharmaBackend.entity.Usuario;
import pe.edu.upeu.PharmaBackend.security.PermisosPorRol;
import java.util.Collection;
import java.util.List;
/*
 * Convierte las entidades de seguridad en DTO planos.
 */
@Component
public class UsuarioMapper {
    public UsuarioResponseDTO toUsuarioResponse(Usuario usuario) {
        Empleado empleado = usuario.getEmpleado();
        return new UsuarioResponseDTO(
                usuario.getId(),
                usuario.getUsername(),
                empleado.getId(),
                empleado.getNombreCompleto(),
                empleado.getCargo(),
                nombresRoles(usuario.getRoles()),
                PermisosPorRol.permisos(usuario.getRoles()),
                usuario.getEstado(),
                usuario.getFechaCreacion()
        );
    }
    public EmpleadoResponseDTO toEmpleadoResponse(Empleado empleado, boolean tieneUsuario) {
        return new EmpleadoResponseDTO(
                empleado.getId(),
                empleado.getDni(),
                empleado.getNombres(),
                empleado.getApellidos(),
                empleado.getCargo(),
                empleado.getEmail(),
                empleado.getTelefono(),
                empleado.getEstado(),
                tieneUsuario
        );
    }
    public RolResponseDTO toRolResponse(Rol rol) {
        return new RolResponseDTO(
                rol.getId(),
                rol.getNombre(),
                rol.getDescripcion(),
                PermisosPorRol.permisos(List.of(rol))
        );
    }
    public List<String> nombresRoles(Collection<Rol> roles) {
        return roles.stream()
                .map(Rol::getNombre)
                .sorted()
                .toList();
    }
}
