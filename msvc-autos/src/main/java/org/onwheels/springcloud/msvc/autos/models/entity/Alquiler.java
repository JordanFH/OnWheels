package org.onwheels.springcloud.msvc.autos.models.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Entity
@Table(name = "alquileres")
public class Alquiler {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(name="usuario_id")
    private Long usuarioId;

    @Column(name="auto_id")
    private Long autoId;

    @Column(name = "fecha_inicio")
    @FutureOrPresent(message = "El campo fecha de inicio no puede ser antes de hoy")
    @NotNull(message = "El campo fecha de inicio no puede ser nulo")
    @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime fechaInicio = LocalDateTime.now();

    @Column(name = "fecha_fin")
    @FutureOrPresent(message = "El campo fecha de fin no puede ser antes de hoy")
    @NotNull(message = "El campo fecha de fin no puede ser nulo")
    @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime fechaFin = LocalDateTime.now();

    @Column(name = "costo_total", columnDefinition = "DECIMAL(6,2) DEFAULT 0.00")
    @DecimalMin(value = "0.00", message = "El costo total no puede ser menor a 0")
    @Digits(integer = 9, fraction = 2, message = "El costo total debe tener máximo 7 dígitos enteros y 2 decimales")
    private float costoTotal;

    @Column(name = "fecha_creacion")
    @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime fechaCreacion;

    @Column(name = "fecha_actualizacion")
    @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime fechaActualizacion;

    @PostPersist
    public void postPersist() {
        this.fechaCreacion = LocalDateTime.now();
    }

    @PostUpdate
    public void postUpdate() {
        this.fechaActualizacion = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public Long getAutoId() {
        return autoId;
    }

    public void setAutoId(Long autoId) {
        this.autoId = autoId;
    }

    public LocalDateTime getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDateTime fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDateTime getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDateTime fechaFin) {
        this.fechaFin = fechaFin;
    }

    public float getCostoTotal() {
        return costoTotal;
    }

    public void setCostoTotal(float costoTotal) {
        this.costoTotal = costoTotal;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public LocalDateTime getFechaActualizacion() {
        return fechaActualizacion;
    }

    public void setFechaActualizacion(LocalDateTime fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }
}
