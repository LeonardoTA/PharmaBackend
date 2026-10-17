package pe.edu.upeu.PharmaBackend.dto.auth;
import jakarta.validation.constraints.NotNull;
import lombok.*;
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class EstadoUsuarioRequestDTO {
    @NotNull(message="El estado es obligatorio")
    private Boolean estado;
}
