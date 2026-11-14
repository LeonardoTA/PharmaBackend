package pe.edu.upeu.PharmaBackend.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.context.ApplicationEventPublisher;
import pe.edu.upeu.PharmaBackend.auditoria.EventoAuditoria;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.upeu.PharmaBackend.dto.CategoriaRequestDTO;
import pe.edu.upeu.PharmaBackend.dto.CategoriaResponseDTO;
import pe.edu.upeu.PharmaBackend.entity.Categoria;
import pe.edu.upeu.PharmaBackend.exception.RecursoNoEncontradoException;
import pe.edu.upeu.PharmaBackend.exception.ReglaNegocioException;
import pe.edu.upeu.PharmaBackend.repository.CategoriaRepository;
import pe.edu.upeu.PharmaBackend.repository.ProductoRepository;
import pe.edu.upeu.PharmaBackend.service.service.CategoriaService;

import java.util.List;

@Service
public class CategoriaServiceImpl implements CategoriaService {
    private static final Logger LOG = LoggerFactory.getLogger(CategoriaServiceImpl.class);

    private final ApplicationEventPublisher eventos;
    private final CategoriaRepository categoriaRepository;
    private final ProductoRepository productoRepository;

    public CategoriaServiceImpl(CategoriaRepository categoriaRepository,
                                ProductoRepository productoRepository, ApplicationEventPublisher eventos) {
        this.eventos = eventos;
        this.categoriaRepository = categoriaRepository;
        this.productoRepository = productoRepository;
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = "categorias", allEntries = true)
    public CategoriaResponseDTO create(CategoriaRequestDTO t) {
        String nombre = t.getNombre().trim();
        if(categoriaRepository.existsByNombreIgnoreCase(nombre)){
            throw new ReglaNegocioException(
                    "Ya existe una categoria con el nomre "+ nombre
            );
        }

        Categoria categoria = new Categoria();
        categoria.setNombre(nombre);
        categoria.setDescripcion(t.getDescripcion());
        categoria.setEstado(t.getEstado());

        Categoria catCreada = categoriaRepository.saveAndFlush(categoria);

        return convertirResponse(catCreada);
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = { "categorias", "productos" }, allEntries = true)
    public CategoriaResponseDTO update(Long aLong, CategoriaRequestDTO t) {
        Categoria categoria = categoriaRepository.findById(aLong).orElseThrow(()->
                new RecursoNoEncontradoException(
                        "Categoria no encontrada con id: "+ aLong
                )
        );
        categoria.setNombre(t.getNombre());
        categoria.setDescripcion(t.getDescripcion());
        categoria.setEstado(t.getEstado());

        Categoria catActualizada = categoriaRepository.saveAndFlush(categoria);

        return convertirResponse(catActualizada);
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = "categorias", key = "'id-' + #aLong")
    public CategoriaResponseDTO read(Long aLong) {
        Categoria categoria = categoriaRepository.findById(aLong)
                .orElseThrow(()->
                        new RecursoNoEncontradoException(
                                "Categoria no encontrada con id: "+ aLong
                        )
                );
        return convertirResponse(categoria);
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = "categorias", allEntries = true)
    public void delete(Long aLong) {
        Categoria categoria = categoriaRepository.findById(aLong).orElseThrow(()->
                new RecursoNoEncontradoException(
                        "Categoria no encontrada con id: "+ aLong
                )
        );
        if (productoRepository.existsByCategoriaId(aLong)) {
            throw new ReglaNegocioException("No se puede eliminar la categoría porque tiene productos asociados.");
        }
        categoriaRepository.delete(categoria);
        eventos.publishEvent(EventoAuditoria.de("CATEGORIA_ELIMINADA", "Categoria", categoria.getId(), categoria.getNombre()));
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = "categorias", key = "'todas'")
    public List<CategoriaResponseDTO> readAll() {
        long inicio = System.currentTimeMillis();
        LOG.info("Inicio listar categorias");
        List<CategoriaResponseDTO> lista = categoriaRepository.findAll()
                .stream()
                .map(this::convertirResponse)
                .toList();
        LOG.info("Fin listar categorias | duracionMs={}", System.currentTimeMillis() - inicio);
        return lista;
    }

    private CategoriaResponseDTO convertirResponse(Categoria categoria){
        return new CategoriaResponseDTO(
                categoria.getId(),
                categoria.getNombre(),
                categoria.getDescripcion(),
                categoria.getEstado(),
                categoria.getFechaCreacion(),
                categoria.getFechaModificacion(),
                categoria.getCreadoPor(),
                categoria.getModificadoPor()
        );
    }
}
