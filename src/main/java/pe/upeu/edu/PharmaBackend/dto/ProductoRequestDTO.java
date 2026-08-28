package pe.upeu.edu.PharmaBackend.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.BigInteger;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductoRequestDTO {
    @NotBlank(message = "El nombre del producto es obligatorio")
    @Size(
            min = 3,
            max = 50,
            message = "El nombre debe tener entre 3 y 50 caracteres"
    )
    private String nombre;
    @Size(
            max = 200,
            message = "La descripción no debe superar los 200 caracteres"
    )
    private String descripcion;

    @NotNull(message = "El precio es obligatorio")
    @Positive(message = "El precio debe ser un valor positivo mayor a 0")
    private BigDecimal precio;

    @NotNull(message = "El stock es obligatorio")
    @Min(
            value = 0, message = "El stock no puede ser un número negativo"
    )
    private BigInteger stock;

    @NotNull(message = "El estado es obligatorio")
    private Boolean estado;

    @NotNull(message = "El ID de la categoría es obligatorio")
    private Long categoriaId;

}
