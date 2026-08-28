package pe.upeu.edu.PharmaBackend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductoResponseDTO {
    private Long id;
    private String nombre;
    private String descripcion;
    private BigDecimal precio;
    private BigInteger stock;
    private Boolean estado;
    private Long categoriaId;
    private String categoriaNombre;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaModificacion;
}
