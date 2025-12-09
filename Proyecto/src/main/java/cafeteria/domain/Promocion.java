package cafeteria.domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@EntityListeners(AuditingEntityListener.class)

@Data
@Entity
@Table(name = "promocion")
public class Promocion implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_promocion")
    private Integer idPromocion;

    @NotBlank(message = "El título es requerido")
    @Size(max = 100, message = "El título no puede exceder 100 caracteres")
    @Column(nullable = false, length = 100)
    private String titulo;

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    @Column(name = "fecha_inicio")
    private LocalDate fechaInicio;

    @Column(name = "fecha_fin")
    private LocalDate fechaFin;

    @Column(nullable = false)
    private Boolean activo = true;

     @Column(name = "fecha_creacion", updatable = false)
    @CreatedDate
    private LocalDateTime fechaCreacion;

    @Column(name = "fecha_modificacion")
    @LastModifiedDate
    private LocalDateTime fechaModificacion;
    
    // Método auxiliar para verificar si la promoción está vigente
    public boolean isVigente() {
        LocalDate hoy = LocalDate.now();
        return activo && 
               (fechaInicio == null || !hoy.isBefore(fechaInicio)) && 
               (fechaFin == null || !hoy.isAfter(fechaFin));
    }
}