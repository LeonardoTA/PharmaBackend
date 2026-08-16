package pe.upeu.edu.PharmaBackend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.upeu.edu.PharmaBackend.entity.Categoria;
import pe.upeu.edu.PharmaBackend.service.service.CategoriaService;

import java.util.List;

@RestController
@RequestMapping("/api/categorias")
public class CategoriaController {
    private final CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }
// -- EndPoint de Categoria --
    @GetMapping
    public Iterable<Categoria> getCategorias() {
        return categoriaService.readAll();
    }
}
