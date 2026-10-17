package pe.edu.upeu.PharmaBackend.dto.auth;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.List;
/*
 * Datos publicos del usuario: NUNCA incluye la contrasena ni su hash.
 * Del empleado y de los roles se devuelven datos planos (sin anidar
 * entidades).
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UsuarioResponseDTO {
    private Long id;
    private String username;
    private Long empleadoId;
    private String empleadoNombre;
    private String cargo;
    private List<String> roles;
    private List<String> permisos;
    private Boolean estado;
    private LocalDateTime fechaCreacion;
}
