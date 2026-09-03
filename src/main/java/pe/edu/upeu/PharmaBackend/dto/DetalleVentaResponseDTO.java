package pe.edu.upeu.PharmaBackend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DetalleVentaResponseDTO {

    private Long productoId;
    private String productoNombre;
    private Integer cantidad;
    private BigDecimal precio;
    private BigDecimal subtotal;
}
