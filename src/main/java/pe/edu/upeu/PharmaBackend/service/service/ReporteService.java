package pe.edu.upeu.PharmaBackend.service.service;

import pe.edu.upeu.PharmaBackend.dto.reporte.ProductoMasVendidoDTO;
import pe.edu.upeu.PharmaBackend.dto.reporte.VentaPorCategoriaDTO;

import java.time.LocalDate;
import java.util.List;

public interface ReporteService {
    List<VentaPorCategoriaDTO> ventasPorCategoria(LocalDate desde, LocalDate hasta);
    List<ProductoMasVendidoDTO> productosMasVendidos(LocalDate desde, LocalDate hasta);
}
