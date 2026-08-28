package pe.upeu.edu.PharmaBackend.service.service;

import pe.upeu.edu.PharmaBackend.dto.ProductoRequestDTO;
import pe.upeu.edu.PharmaBackend.dto.ProductoResponseDTO;
import pe.upeu.edu.PharmaBackend.service.generic.CrudService;

public interface ProductoService extends CrudService<ProductoRequestDTO, ProductoResponseDTO, Long> {
}
