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
import jakarta.validation.Path;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

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

    @Value("${upload.path:static/img}")
    private String uploadPath;

     @Transactional
    public void save(Producto producto, MultipartFile imagen) throws IOException {
        // Solo procesar si hay una imagen
        if (imagen != null && !imagen.isEmpty()) {
            // Generar un nombre único para evitar colisiones
            String nombreOriginal = imagen.getOriginalFilename();
            String extension = nombreOriginal.substring(nombreOriginal.lastIndexOf("."));
            String nombreUnico = UUID.randomUUID().toString() + extension;
            

            java.nio.file.Path directorioImagenes = Paths.get("src/main/resources/static/img");
            
 
            java.nio.file.Path rutaCompleta = directorioImagenes.resolve(nombreUnico);
            Files.copy(imagen.getInputStream(), rutaCompleta, StandardCopyOption.REPLACE_EXISTING);
            producto.setNombreImagen(nombreUnico);
        }
        
        productoRepository.save(producto);
    }
 
}
