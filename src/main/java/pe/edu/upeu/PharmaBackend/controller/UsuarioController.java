package pe.edu.upeu.PharmaBackend.controller;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.edu.upeu.PharmaBackend.dto.auth.EmpleadoResponseDTO;
import pe.edu.upeu.PharmaBackend.dto.auth.RolResponseDTO;
import pe.edu.upeu.PharmaBackend.dto.auth.UsuarioRequestDTO;
import pe.edu.upeu.PharmaBackend.dto.auth.UsuarioResponseDTO;
import pe.edu.upeu.PharmaBackend.service.service.UsuarioService;
import java.util.List;
/*
 * Administracion de la seguridad: usuarios, empleados y roles.
 * Solo usuarios con el permiso USUARIOS_ADMINISTRAR (rol ADMIN);
 * las reglas estan en SecurityConfig.
 */
@RestController
@RequestMapping("/api/v1")
public class UsuarioController {
private final UsuarioService usuarioService;
    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }
    @org.springframework.web.bind.annotation.PutMapping("/usuarios/{id}/roles")
    public ResponseEntity<UsuarioResponseDTO> asignarRoles(
            @org.springframework.web.bind.annotation.PathVariable Long id,
            @Valid @RequestBody pe.edu.upeu.PharmaBackend.dto.auth.AsignarRolesRequestDTO request,
            org.springframework.security.core.Authentication authentication) {
        return ResponseEntity.ok(usuarioService.asignarRoles(id, request.getRolIds(), authentication.getName()));
    }
    @org.springframework.web.bind.annotation.PatchMapping("/usuarios/{id}/estado")
    public ResponseEntity<UsuarioResponseDTO> cambiarEstado(
            @org.springframework.web.bind.annotation.PathVariable Long id,
            @Valid @RequestBody pe.edu.upeu.PharmaBackend.dto.auth.EstadoUsuarioRequestDTO request,
            org.springframework.security.core.Authentication authentication) {
        return ResponseEntity.ok(usuarioService.cambiarEstado(id, request.getEstado(), authentication.getName()));
    }
    @GetMapping("/usuarios")
    public ResponseEntity<List<UsuarioResponseDTO>> listar() {
        return ResponseEntity.ok(usuarioService.listar());
    }
    @PostMapping("/usuarios")
    public ResponseEntity<UsuarioResponseDTO> crear(
            @Valid @RequestBody UsuarioRequestDTO request) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(usuarioService.crear(request));
    }
    /* Empleados, indicando cuales ya tienen usuario (relacion 1 a 1). */
    @GetMapping("/empleados")
    public ResponseEntity<List<EmpleadoResponseDTO>> empleados() {
        return ResponseEntity.ok(usuarioService.listarEmpleados());
    }
    /* Roles disponibles con los permisos que otorga cada uno. */
    @GetMapping("/roles")
    public ResponseEntity<List<RolResponseDTO>> roles() {
        return ResponseEntity.ok(usuarioService.listarRoles());
    }
}
