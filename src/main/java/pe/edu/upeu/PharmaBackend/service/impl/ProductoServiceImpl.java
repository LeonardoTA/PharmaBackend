package pe.edu.upeu.PharmaBackend.service.impl;

import pe.edu.upeu.PharmaBackend.dto.PaginaResponseDTO;
import pe.edu.upeu.PharmaBackend.util.PaginacionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.upeu.PharmaBackend.dto.ProductoRequestDTO;
import pe.edu.upeu.PharmaBackend.dto.ProductoResponseDTO;
import pe.edu.upeu.PharmaBackend.entity.Categoria;
import pe.edu.upeu.PharmaBackend.entity.Producto;
import pe.edu.upeu.PharmaBackend.exception.RecursoNoEncontradoException;
import pe.edu.upeu.PharmaBackend.exception.ReglaNegocioException;
import pe.edu.upeu.PharmaBackend.repository.CategoriaRepository;
import pe.edu.upeu.PharmaBackend.repository.ProductoRepository;
import pe.edu.upeu.PharmaBackend.service.service.ProductoService;

import java.util.List;
import java.util.Set;

@Service
public class ProductoServiceImpl implements ProductoService {
    private static final Logger LOG = LoggerFactory.getLogger(ProductoServiceImpl.class);

    private final ProductoRepository productoRepository;
    private final CategoriaRepository categoriaRepository;

    public ProductoServiceImpl(ProductoRepository productoRepository, CategoriaRepository categoriaRepository) {
        this.productoRepository = productoRepository;
        this.categoriaRepository = categoriaRepository;
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = "productos", allEntries = true)
    public ProductoResponseDTO create(ProductoRequestDTO t) {
        String nombre = t.getNombre().trim();
        if (productoRepository.existsByNombreIgnoreCase(nombre)) {
            throw new ReglaNegocioException(
                    "Ya existe un producto con el nombre " + nombre
            );
        }

        Categoria categoria = categoriaRepository.findById(t.getCategoriaId()).orElseThrow(() ->
                new RecursoNoEncontradoException(
                        "Categoria no encontrada con id: " + t.getCategoriaId()
                )
        );

        Producto producto = new Producto();
        producto.setNombre(nombre);
        producto.setDescripcion(t.getDescripcion());
        producto.setPrecio(t.getPrecio());
        producto.setStock(t.getStock());
        producto.setEstado(t.getEstado());
        producto.setCategoria(categoria);

        Producto prodCreado = productoRepository.save(producto);

        return convertirResponse(prodCreado);
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = "productos", allEntries = true)
    public ProductoResponseDTO update(Long id, ProductoRequestDTO t) {
        Producto producto = productoRepository.findById(id).orElseThrow(() ->
                new RecursoNoEncontradoException(
                        "Producto no encontrado con id: " + id
                )
        );

        String nombre = t.getNombre().trim();
        if (productoRepository.existsByNombreIgnoreCaseAndIdNot(nombre, id)) {
            throw new ReglaNegocioException(
                    "Ya existe otro producto con el nombre " + nombre
            );
        }

        Categoria categoria = categoriaRepository.findById(t.getCategoriaId()).orElseThrow(() ->
                new RecursoNoEncontradoException(
                        "Categoria no encontrada con id: " + t.getCategoriaId()
                )
        );

        producto.setNombre(nombre);
        producto.setDescripcion(t.getDescripcion());
        producto.setPrecio(t.getPrecio());
        producto.setStock(t.getStock());
        producto.setEstado(t.getEstado());
        producto.setCategoria(categoria);

        Producto prodActualizado = productoRepository.save(producto);

        return convertirResponse(prodActualizado);
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = "productos", key = "'id-' + #id")
    public ProductoResponseDTO read(Long id) {
        Producto producto = productoRepository.findById(id).orElseThrow(() ->
                new RecursoNoEncontradoException(
                        "Producto no encontrado con id: " + id
                )
        );
        return convertirResponse(producto);
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = "productos", allEntries = true)
    public void delete(Long id) {
        Producto producto = productoRepository.findById(id).orElseThrow(() ->
                new RecursoNoEncontradoException(
                        "Producto no encontrado con id: " + id
                )
        );
        productoRepository.delete(producto);
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = "productos", key = "'todas'")
    public List<ProductoResponseDTO> readAll() {
        return productoRepository.findAll()
                .stream()
                .map(this::convertirResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = "productos", key = "'pagina-' + #pagina + '-' + #tamanio + '-' + #ordenarPor + '-' + #direccion + '-' + #categoriaId")
    public PaginaResponseDTO<ProductoResponseDTO> listar(
            int pagina, int tamanio, String ordenarPor, String direccion, Long categoriaId) {
        var pageable = PaginacionUtil.construir(
                pagina, tamanio, ordenarPor, direccion,
                Set.of("id", "nombre", "precio", "stock", "estado"), "nombre");
        return PaginaResponseDTO.de(
                productoRepository.listarPagina(categoriaId, pageable).map(this::convertirResponse));
    }

    private ProductoResponseDTO convertirResponse(Producto producto) {
        Long categoriaId = producto.getCategoria() != null ? producto.getCategoria().getId() : null;
        String categoriaNombre = producto.getCategoria() != null ? producto.getCategoria().getNombre() : null;

        return new ProductoResponseDTO(
                producto.getId(),
                producto.getNombre(),
                producto.getDescripcion(),
                producto.getPrecio(),
                producto.getStock(),
                producto.getEstado(),
                categoriaId,
                categoriaNombre,
                producto.getFechaCreacion(),
                producto.getFechaModificacion()
        );
    }
}
