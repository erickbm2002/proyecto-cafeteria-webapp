package cafeteria.Services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cafeteria.domain.Promocion;
import cafeteria.repository.PromocionRepository;

@Service
public class PromocionService {

    private final PromocionRepository promocionRepository;

    public PromocionService(PromocionRepository promocionRepository) {
        this.promocionRepository = promocionRepository;
    }

    @Transactional(readOnly = true)
    public List<Promocion> getAllPromociones() {
        return promocionRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Promocion> getPromocionesActivas() {
        return promocionRepository.findByActivoTrue();
    }

    @Transactional(readOnly = true)
    public List<Promocion> getPromocionesVigentes() {
        return promocionRepository.findPromocionesVigentes(LocalDate.now());
    }

    @Transactional(readOnly = true)
    public Promocion getPromocionById(Integer id) {
        return promocionRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Promoción no encontrada con ID: " + id));
    }

    @Transactional
    public Promocion save(Promocion promocion) {
        // Validar que el título no esté vacío
        if (promocion.getTitulo() == null || promocion.getTitulo().trim().isEmpty()) {
            throw new RuntimeException("El título de la promoción es requerido");
        }

        // Validar fechas
        if (promocion.getFechaInicio() != null && promocion.getFechaFin() != null) {
            if (promocion.getFechaFin().isBefore(promocion.getFechaInicio())) {
                throw new RuntimeException("La fecha de fin no puede ser anterior a la fecha de inicio");
            }
        }

        // Establecer valores por defecto
        if (promocion.getActivo() == null) {
            promocion.setActivo(true);
        }

        // Los campos de fecha se manejan automáticamente por la BD
        // No es necesario setearlos manualmente aquí

        return promocionRepository.save(promocion);
    }

    @Transactional
    public void delete(Integer id) {
        if (!promocionRepository.existsById(id)) {
            throw new RuntimeException("Promoción no encontrada con ID: " + id);
        }
        promocionRepository.deleteById(id);
    }

    @Transactional
    public void toggleActivo(Integer id) {
        Promocion promocion = getPromocionById(id);
        promocion.setActivo(!promocion.getActivo());
        promocionRepository.save(promocion);
    }
}
