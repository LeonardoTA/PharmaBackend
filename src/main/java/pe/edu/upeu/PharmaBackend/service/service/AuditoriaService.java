package pe.edu.upeu.PharmaBackend.service.service;
import java.time.LocalDate;
import pe.edu.upeu.PharmaBackend.auditoria.EventoAuditoria;
import pe.edu.upeu.PharmaBackend.dto.AuditoriaEventoDTO;
import pe.edu.upeu.PharmaBackend.dto.PaginaResponseDTO;
public interface AuditoriaService {
    void registrar(EventoAuditoria evento);
    PaginaResponseDTO<AuditoriaEventoDTO> buscar(String usuario, String accion,
            LocalDate desde, LocalDate hasta, int pagina, int tamanio);
}
