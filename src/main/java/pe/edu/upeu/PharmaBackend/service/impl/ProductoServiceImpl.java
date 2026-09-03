package pe.edu.upeu.PharmaBackend.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    public Iterable<ProductoResponseDTO> readAll() {
        return productoRepository.findAll()
                .stream()
                .map(this::convertirResponse)
                .toList();
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
