package cafeteria.Services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import cafeteria.domain.Producto;
import cafeteria.repository.ProductoRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ProductoServices {

    @Autowired
    private final ProductoRepository productoRepository;

    public ProductoServices(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    @Value("${upload.path:static/img}")
    private String uploadPath;

    @Transactional(readOnly=true)
    public List<Producto> getProductos(boolean active) {
        if(active) return productoRepository.findByActivoTrue();
        return productoRepository.findAll();
    }

    @Transactional(readOnly=true)
    public Producto getProductoById(Integer id) {
        return productoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + id));
    }

    @Transactional
    public void save(Producto producto, MultipartFile imagen) throws IOException {
        // Si es una actualización y no hay nueva imagen, mantener la imagen anterior
        if (producto.getIdProducto() != null && (imagen == null || imagen.isEmpty())) {
            Producto productoExistente = productoRepository.findById(producto.getIdProducto())
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
            producto.setNombreImagen(productoExistente.getNombreImagen());
        }
        
        // Solo procesar si hay una imagen nueva
        if (imagen != null && !imagen.isEmpty()) {
            // Validar tipo de archivo
            String contentType = imagen.getContentType();
            if (contentType == null || !contentType.startsWith("image/")) {
                throw new IOException("El archivo debe ser una imagen");
            }

            // Generar un nombre único para evitar colisiones
            String nombreOriginal = imagen.getOriginalFilename();
            String extension = nombreOriginal.substring(nombreOriginal.lastIndexOf("."));
            String nombreUnico = UUID.randomUUID().toString() + extension;
            
            java.nio.file.Path directorioImagenes = Paths.get("src/main/resources/static/img");
            
            // Crear directorio si no existe
            if (!Files.exists(directorioImagenes)) {
                Files.createDirectories(directorioImagenes);
            }

            java.nio.file.Path rutaCompleta = directorioImagenes.resolve(nombreUnico);
            Files.copy(imagen.getInputStream(), rutaCompleta, StandardCopyOption.REPLACE_EXISTING);
            producto.setNombreImagen(nombreUnico);
        }
        
        // Si activo es null, establecer como true por defecto
        if (producto.getActivo() == null) {
            producto.setActivo(true);
        }
        
        productoRepository.save(producto);
    }

    @Transactional
    public void delete(Integer id) {
        if (!productoRepository.existsById(id)) {
            throw new RuntimeException("Producto no encontrado con ID: " + id);
        }
        productoRepository.deleteById(id);
    }

    @Transactional
    public void toggleActivo(Integer id) {
        Producto producto = productoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + id));
        producto.setActivo(!producto.getActivo());
        productoRepository.save(producto);
    }
}