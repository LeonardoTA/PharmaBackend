package pe.edu.upeu.PharmaBackend.dto.auth;
import jakarta.validation.constraints.*;
import lombok.*;
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class CambioPasswordRequestDTO {
    @NotBlank(message="La contraseña actual es obligatoria")
    private String passwordActual;
    @NotBlank(message="La nueva contraseña es obligatoria")
    @Size(min=8,max=72,message="La contraseña debe tener entre 8 y 72 caracteres")
    @Pattern(regexp="^(?=.*[A-Za-z])(?=.*\\d).+$",message="La contraseña debe contener al menos una letra y un número")
    private String passwordNueva;
}
