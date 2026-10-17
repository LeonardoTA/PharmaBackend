package pe.edu.upeu.PharmaBackend.dto.auth;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Set;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UsuarioRequestDTO {
    @NotBlank(message = "El usuario es obligatorio")
    @Size(min = 4, max = 50, message = "El usuario debe tener entre 4 y 50 caracteres")
    @Pattern(
            regexp = "^[a-zA-Z0-9._-]+$",
            message = "El usuario solo admite letras, números, punto, guion y guion bajo"
    )
    private String username;
    /*
     * BCrypt solo considera los primeros 72 bytes de la contrasena:
     * por eso el maximo es 72.
     */
    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 8, max = 72, message = "La contraseña debe tener entre 8 y 72 caracteres")
    @Pattern(
            regexp = "^(?=.*[A-Za-z])(?=.*\\d).+$",
            message = "La contraseña debe contener al menos una letra y un número"
    )
    private String password;
    // Relacion 1 a 1: el empleado dueño de la cuenta
    @NotNull(message = "El empleado es obligatorio")
    private Long empleadoId;
    // Relacion muchos a muchos: uno o varios roles
    @NotEmpty(message = "Debe asignar al menos un rol")
    private Set<@NotNull(message = "El id de rol no puede ser nulo") Long> rolIds;
}
