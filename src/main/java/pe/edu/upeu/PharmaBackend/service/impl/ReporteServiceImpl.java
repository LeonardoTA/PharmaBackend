package pe.edu.upeu.PharmaBackend.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pe.edu.upeu.PharmaBackend.dto.reporte.ProductoMasVendidoDTO;
import pe.edu.upeu.PharmaBackend.dto.reporte.VentaPorCategoriaDTO;
import pe.edu.upeu.PharmaBackend.exception.ReglaNegocioException;
import pe.edu.upeu.PharmaBackend.repository.VentaRepository;
import pe.edu.upeu.PharmaBackend.service.service.ReporteService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReporteServiceImpl implements ReporteService {

    private final VentaRepository ventaRepository;

    @Override
    public List<VentaPorCategoriaDTO> ventasPorCategoria(LocalDate desde, LocalDate hasta) {
        long inicio = System.currentTimeMillis();
        log.info("Inicio ventasPorCategoria | desde={} | hasta={}", desde, hasta);
        
        validarRango(desde, hasta);
        
        LocalDateTime fechaDesde = desde != null ? desde.atStartOfDay() : null;
        LocalDateTime fechaHasta = hasta != null ? hasta.atTime(LocalTime.MAX) : null;
        
        List<VentaPorCategoriaDTO> resultado = ventaRepository.reporteVentasPorCategoria(fechaDesde, fechaHasta);
        
        log.info("Fin ventasPorCategoria | desde={} | hasta={} | filas={} | duracionMs={}", 
                 desde, hasta, resultado.size(), System.currentTimeMillis() - inicio);
        
        return resultado;
    }

    @Override
    public List<ProductoMasVendidoDTO> productosMasVendidos(LocalDate desde, LocalDate hasta) {
        long inicio = System.currentTimeMillis();
        log.info("Inicio productosMasVendidos | desde={} | hasta={}", desde, hasta);
        
        validarRango(desde, hasta);
        
        LocalDateTime fechaDesde = desde != null ? desde.atStartOfDay() : null;
        LocalDateTime fechaHasta = hasta != null ? hasta.atTime(LocalTime.MAX) : null;
        
        List<ProductoMasVendidoDTO> resultado = ventaRepository.reporteProductosMasVendidos(fechaDesde, fechaHasta);
        
        log.info("Fin productosMasVendidos | desde={} | hasta={} | filas={} | duracionMs={}", 
                 desde, hasta, resultado.size(), System.currentTimeMillis() - inicio);
        
        return resultado;
    }
    
    private void validarRango(LocalDate desde, LocalDate hasta) {
        if (desde != null && hasta != null && desde.isAfter(hasta)) {
            throw new ReglaNegocioException("La fecha 'desde' no puede ser posterior a la fecha 'hasta'");
        }
    }
}
