package pe.edu.upeu.PharmaBackend.dto.reporte;

import java.math.BigDecimal;

public record VentaPorCategoriaDTO(
        Long categoriaId,
        String categoria,
        Long cantidad,
        BigDecimal total
) {}
