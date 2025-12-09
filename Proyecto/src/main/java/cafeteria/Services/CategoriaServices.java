package cafeteria.Services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cafeteria.domain.Categoria;
import cafeteria.repository.CategoriaRepository;

@Service
public class CategoriaServices {

    private CategoriaRepository categoriaRepository;

    public CategoriaServices(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    @Transactional(readOnly=true)
    public List<Categoria> getCategorias(boolean active) {
        if(active) return categoriaRepository.findByActivoTrue();
        return categoriaRepository.findAll();
    }

    @Transactional(readOnly=true)
    public Categoria getCategoriaById(Integer id) {
        return categoriaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Categoría no encontrada con ID: " + id));
    }

    @Transactional
    public void save(Categoria categoria) {
        // Validar que el nombre no esté vacío
        if (categoria.getNombre() == null || categoria.getNombre().trim().isEmpty()) {
            throw new RuntimeException("El nombre de la categoría no puede estar vacío");
        }

        // Si activo es null, establecer como true por defecto
        if (categoria.getActivo() == null) {
            categoria.setActivo(true);
        }

        // Verificar si ya existe una categoría con el mismo nombre (excepto si es actualización)
        List<Categoria> categoriasExistentes = categoriaRepository.findAll();
        for (Categoria cat : categoriasExistentes) {
            if (cat.getNombre().equalsIgnoreCase(categoria.getNombre().trim()) 
                && !cat.getIdCategoria().equals(categoria.getIdCategoria())) {
                throw new RuntimeException("Ya existe una categoría con el nombre: " + categoria.getNombre());
            }
        }

        categoriaRepository.save(categoria);
    }

    @Transactional
    public void delete(Integer id) {
        Categoria categoria = categoriaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Categoría no encontrada con ID: " + id));
        
        // Verificar si la categoría tiene productos asociados
        if (categoria.getProductos() != null && !categoria.getProductos().isEmpty()) {
            throw new RuntimeException("No se puede eliminar la categoría porque tiene productos asociados. " +
                                     "Primero elimine o reasigne los productos.");
        }
        
        categoriaRepository.deleteById(id);
    }

    @Transactional
    public void toggleActivo(Integer id) {
        Categoria categoria = categoriaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Categoría no encontrada con ID: " + id));
        categoria.setActivo(!categoria.getActivo());
        categoriaRepository.save(categoria);
    }
}