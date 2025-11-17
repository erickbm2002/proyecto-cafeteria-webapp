package cafeteria.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cafeteria.domain.Producto;
import cafeteria.repository.ProductoRepository;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductoServices {

    @Autowired
    private final ProductoRepository productoRepository;

    

    public ProductoServices(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    @Transactional(readOnly=true)
    public List<Producto> getProductos(boolean active) {
        if(active) return productoRepository.findByActivoTrue();
        return productoRepository.findAll();
    }
 
}
