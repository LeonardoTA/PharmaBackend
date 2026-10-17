package pe.edu.upeu.PharmaBackend.dto.auth;
import jakarta.validation.constraints.*;
import lombok.*;
import java.util.Set;
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class AsignarRolesRequestDTO {
    @NotEmpty(message="Debe asignar al menos un rol")
    private Set<@NotNull(message="El id de rol no puede ser nulo") Long> rolIds;
}
