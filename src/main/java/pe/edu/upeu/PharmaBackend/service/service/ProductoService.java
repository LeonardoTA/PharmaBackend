package pe.edu.upeu.PharmaBackend.service.service;

import pe.edu.upeu.PharmaBackend.dto.PaginaResponseDTO;
import pe.edu.upeu.PharmaBackend.dto.ProductoRequestDTO;
import pe.edu.upeu.PharmaBackend.dto.ProductoResponseDTO;
import pe.edu.upeu.PharmaBackend.service.generic.CrudService;

public interface ProductoService extends CrudService<ProductoRequestDTO, ProductoResponseDTO, Long> {
    PaginaResponseDTO<ProductoResponseDTO> listar(
            int pagina, int tamanio, String ordenarPor, String direccion, Long categoriaId);
}
