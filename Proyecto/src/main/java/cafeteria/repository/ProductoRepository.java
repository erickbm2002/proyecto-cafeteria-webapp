package cafeteria.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import cafeteria.domain.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Integer> {
    public List<Producto> findByActivoTrue();
}
