package pe.edu.upeu.PharmaBackend.dto.reporte;

import java.math.BigDecimal;

public record ProductoMasVendidoDTO(
        Long id,
        String nombre,
        String categoria,
        Long cantidad,
        BigDecimal total
) {}
