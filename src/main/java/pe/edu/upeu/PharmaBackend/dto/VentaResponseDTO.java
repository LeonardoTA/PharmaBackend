package pe.edu.upeu.PharmaBackend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class VentaResponseDTO {

    private Long id;
    private LocalDateTime fechaRegistro;

    private Long clienteId;
    private String clienteNombre;

    private String estado;
    private BigDecimal total;

    private List<DetalleVentaResponseDTO> detalles;
}
