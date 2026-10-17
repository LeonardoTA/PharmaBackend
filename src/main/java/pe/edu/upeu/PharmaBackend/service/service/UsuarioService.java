package pe.edu.upeu.PharmaBackend.service.service;
import pe.edu.upeu.PharmaBackend.dto.auth.EmpleadoResponseDTO;
import pe.edu.upeu.PharmaBackend.dto.auth.RolResponseDTO;
import pe.edu.upeu.PharmaBackend.dto.auth.UsuarioRequestDTO;
import pe.edu.upeu.PharmaBackend.dto.auth.UsuarioResponseDTO;
import java.util.List;
import java.util.Set;
public interface UsuarioService {
    UsuarioResponseDTO asignarRoles(Long id, Set<Long> rolIds, String actor);
    UsuarioResponseDTO cambiarEstado(Long id, boolean estado, String actor);
    UsuarioResponseDTO crear(UsuarioRequestDTO request);
    List<UsuarioResponseDTO> listar();
    List<EmpleadoResponseDTO> listarEmpleados();
    List<RolResponseDTO> listarRoles();
}
