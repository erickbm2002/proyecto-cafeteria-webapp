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
}
