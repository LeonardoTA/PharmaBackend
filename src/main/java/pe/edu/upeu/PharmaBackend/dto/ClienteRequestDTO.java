package pe.edu.upeu.PharmaBackend.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ClienteRequestDTO {
    @NotBlank(message = "El DNI es obligatorio")
    @Pattern(
            regexp = "\\d{8}",
            message = "El DNI debe contener exactamente 8 dígitos"
    )
    private String dni;

    @NotBlank(message = "Los nombres son obligatorios")
    @Size(
            min = 2,
            max = 100,
            message = "Los nombres deben tener entre 2 y 100 caracteres"
    )
    private String nombres;

    @NotBlank(message = "Los apellidos son obligatorios")
    @Size(
            min = 2,
            max = 100,
            message = "Los apellidos deben tener entre 2 y 100 caracteres"
    )
    private String apellidos;

    @NotBlank(message = "El correo es obligatorio")
    @Email(message = "El correo electrónico no tiene un formato válido")
    @Size(max = 150)
    private String email;

    @Pattern(
            regexp = "^\\d{9}$",
            message = "El teléfono debe contener 9 dígitos"
    )
    private String telefono;

    @Size(
            max = 250,
            message = "La dirección no debe superar los 250 caracteres"
    )
    private String direccion;

    @NotNull(message = "El estado es obligatorio")
    private Boolean estado;

}
