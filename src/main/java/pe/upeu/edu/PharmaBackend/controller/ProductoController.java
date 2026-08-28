package pe.upeu.edu.PharmaBackend.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.upeu.edu.PharmaBackend.dto.ProductoRequestDTO;
import pe.upeu.edu.PharmaBackend.dto.ProductoResponseDTO;
import pe.upeu.edu.PharmaBackend.service.service.ProductoService;

@RestController
@RequestMapping("/api/v1/productos")
public class ProductoController {
    private final ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    // Listado de productos
    @GetMapping
    public ResponseEntity<Iterable<ProductoResponseDTO>> findAll() {
        return ResponseEntity.ok(
                productoService.readAll()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductoResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(
                productoService.read(id)
        );
    }

    @PostMapping
    public ResponseEntity<ProductoResponseDTO> create(@Valid @RequestBody ProductoRequestDTO requestDTO) {
        ProductoResponseDTO response = productoService.create(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductoResponseDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody ProductoRequestDTO requestDTO) {
        return ResponseEntity.ok(
                productoService.update(id, requestDTO)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ProductoResponseDTO> delete(@PathVariable Long id) {
        productoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
