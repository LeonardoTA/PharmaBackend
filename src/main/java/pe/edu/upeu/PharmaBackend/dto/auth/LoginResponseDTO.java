package pe.edu.upeu.PharmaBackend.dto.auth;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;
/*
 * Respuesta del login. El frontend guarda el token y lo envia en cada
 * peticion como:  Authorization: Bearer <token>
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LoginResponseDTO {
    private String token;
    private String tipo;
    private long expiraEnSegundos;
    private String username;
    private String nombreCompleto;
    private List<String> roles;
    private List<String> permisos;
}
