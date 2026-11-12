package pe.edu.upeu.PharmaBackend.controller;
import java.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import pe.edu.upeu.PharmaBackend.dto.AuditoriaEventoDTO;
import pe.edu.upeu.PharmaBackend.dto.PaginaResponseDTO;
import pe.edu.upeu.PharmaBackend.service.service.AuditoriaService;
import pe.edu.upeu.PharmaBackend.exception.ReglaNegocioException;
@RestController
@RequestMapping("/api/v1/auditoria")
public class AuditoriaController {
    private final AuditoriaService service;
    public AuditoriaController(AuditoriaService service) { this.service = service; }
    @GetMapping
    public PaginaResponseDTO<AuditoriaEventoDTO> buscar(
            @RequestParam(required = false) String usuario,
            @RequestParam(required = false) String accion,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate desde,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate hasta,
            @RequestParam(defaultValue = "0") int pagina,
            @RequestParam(defaultValue = "20") int tamanio) {
        if (desde != null && hasta != null && desde.isAfter(hasta))
            throw new ReglaNegocioException("La fecha desde no puede ser posterior a la fecha hasta.");
        return service.buscar(usuario, accion, desde, hasta, pagina, tamanio);
    }
}
