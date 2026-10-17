package pe.edu.upeu.PharmaBackend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pe.edu.upeu.PharmaBackend.dto.reporte.ProductoMasVendidoDTO;
import pe.edu.upeu.PharmaBackend.dto.reporte.VentaPorCategoriaDTO;
import pe.edu.upeu.PharmaBackend.service.service.ReporteService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/reportes")
@PreAuthorize("hasAuthority('REPORTES_VER')")
@RequiredArgsConstructor
public class ReporteController {

    private final ReporteService reporteService;

    @GetMapping("/ventas-por-categoria")
    public ResponseEntity<List<VentaPorCategoriaDTO>> ventasPorCategoria(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate desde,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate hasta) {
        return ResponseEntity.ok(reporteService.ventasPorCategoria(desde, hasta));
    }

    @GetMapping("/productos-mas-vendidos")
    public ResponseEntity<List<ProductoMasVendidoDTO>> productosMasVendidos(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate desde,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate hasta) {
        return ResponseEntity.ok(reporteService.productosMasVendidos(desde, hasta));
    }
}
