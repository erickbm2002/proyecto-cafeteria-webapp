package cafeteria.Services;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cafeteria.domain.Descuento;
import cafeteria.domain.Producto;
import cafeteria.repository.DescuentoRepository;
import cafeteria.repository.ProductoRepository;

@Service
public class DescuentoService {

    private final DescuentoRepository descuentoRepository;
    
    @Autowired
    private ProductoRepository productoRepository;

    public DescuentoService(DescuentoRepository descuentoRepository) {
        this.descuentoRepository = descuentoRepository;
    }

    @Transactional(readOnly = true)
    public List<Descuento> getAllDescuentos() {
        return descuentoRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Descuento> getDescuentosActivos() {
        return descuentoRepository.findByActivoTrue();
    }

    @Transactional(readOnly = true)
    public Descuento getDescuentoById(Integer id) {
        return descuentoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Descuento no encontrado con ID: " + id));
    }

    @Transactional(readOnly = true)
    public List<Descuento> getDescuentosByProducto(Producto producto) {
        return descuentoRepository.findByProducto(producto);
    }

    @Transactional(readOnly = true)
    public Optional<Descuento> getDescuentoVigente(Integer idProducto) {
        Producto producto = productoRepository.findById(idProducto)
            .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + idProducto));
        return descuentoRepository.findDescuentoVigente(producto, LocalDate.now());
    }

    @Transactional
    public Descuento save(Descuento descuento) {
        // Validar que el producto existe
        if (descuento.getProducto() == null || descuento.getProducto().getIdProducto() == null) {
            throw new RuntimeException("Debe especificar un producto válido");
        }

        // Validar porcentaje
        if (descuento.getPorcentaje() == null || 
            descuento.getPorcentaje().compareTo(BigDecimal.ZERO) < 0 || 
            descuento.getPorcentaje().compareTo(new BigDecimal(100)) > 0) {
            throw new RuntimeException("El porcentaje debe estar entre 0 y 100");
        }

        // Validar fechas
        if (descuento.getFechaInicio() != null && descuento.getFechaFin() != null) {
            if (descuento.getFechaFin().isBefore(descuento.getFechaInicio())) {
                throw new RuntimeException("La fecha de fin no puede ser anterior a la fecha de inicio");
            }
        }

        // Establecer valores por defecto
        if (descuento.getActivo() == null) {
            descuento.setActivo(true);
        }

        return descuentoRepository.save(descuento);
    }

    @Transactional
    public void delete(Integer id) {
        if (!descuentoRepository.existsById(id)) {
            throw new RuntimeException("Descuento no encontrado con ID: " + id);
        }
        descuentoRepository.deleteById(id);
    }

    @Transactional
    public void toggleActivo(Integer id) {
        Descuento descuento = getDescuentoById(id);
        descuento.setActivo(!descuento.getActivo());
        descuentoRepository.save(descuento);
    }

    @Transactional(readOnly = true)
    public BigDecimal calcularPrecioConDescuento(Integer idProducto) {
        Producto producto = productoRepository.findById(idProducto)
            .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + idProducto));
        
        Optional<Descuento> descuento = getDescuentoVigente(idProducto);
        if (descuento.isPresent()) {
            return descuento.get().calcularPrecioConDescuento(producto.getPrecio());
        }
        return producto.getPrecio();
    }
}