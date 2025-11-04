package cafeteria.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import cafeteria.domain.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {
    
    public List<Categoria> findByActivoTrue();

}
