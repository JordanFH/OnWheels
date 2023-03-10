package org.onwheels.springcloud.msvc.autos.models.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.onwheels.springcloud.msvc.autos.models.Usuario;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "autos")
public class Auto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 50, message = "El campo marca solo acepta un máximo de 50 carácteres")
    @NotBlank(message = "El campo marca no puede estar vacío")
    private String marca;

    @Column(unique = true)
    @Size(max = 50, message = "El campo modelo solo acepta un máximo de 50 carácteres")
    @NotBlank(message = "El campo modelo no puede estar vacío")
    private String modelo;

    @Min(value = 1885, message = "El año debe estar entre 1885 y 2023")
    @Max(value = 2023, message = "El año debe estar entre 1885 y 2023")
    private short anio;

    @DecimalMin(value = "0.00", message = "El costo por hora debe estar entre 0 y 1000")
    @DecimalMax(value = "1000.00", message = "El costo por hora debe estar entre 0 y 1000")
    @Digits(integer = 4, fraction = 2, message = "El costo por hora debe tener máximo 4 dígitos enteros y 2 decimales")
    @Column(columnDefinition = "DECIMAL(6,2) DEFAULT 0.00")
    private float costo_hora;

    private boolean disponible = true;

    private boolean activo = true;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "auto_id")
    private List<Alquiler> alquileres;

    public Auto(){
        alquileres = new ArrayList<>();
    }
    public void addAlquiler(Alquiler alquiler){
        alquileres.add(alquiler);
    }

    public void removeAlquiler(Alquiler alquiler){
        alquileres.remove(alquiler);
    }

    public List<Alquiler> getAlquileres() {
        return alquileres;
    }

    public void setAlquileres(List<Alquiler> alquileres) {
        this.alquileres = alquileres;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public short getAnio() {
        return anio;
    }

    public void setAnio(short anio) {
        this.anio = anio;
    }

    public float getCosto_hora() {
        return costo_hora;
    }

    public void setCosto_hora(float costo_hora) {
        this.costo_hora = costo_hora;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
}
