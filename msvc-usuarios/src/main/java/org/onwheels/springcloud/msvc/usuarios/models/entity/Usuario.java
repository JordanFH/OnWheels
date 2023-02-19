package org.onwheels.springcloud.msvc.usuarios.models.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "No se aceptan carácteres especiales, ni espacios. Solo se aceptan letras y números")
    @Size(max = 30, message = "El campo usuario solo acepta un máximo de 30 carácteres")
    @NotBlank(message = "El campo usuario no puede estar vacío")
    private String usuario;

    @Email
    @Column(unique = true)
    @Size(max = 60, message = "El campo email solo acepta un máximo de 60 carácteres")
    @NotBlank(message = "El campo email no puede estar vacío")
    private String email;

    @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "No se aceptan carácteres especiales, ni espacios. Solo se aceptan letras y números")
    @Size(min = 8, max = 15, message = "El campo password debe tener entre 8 y 15 carácteres")
    @NotBlank(message = "El campo email no puede estar vacío")
    private String password;

    @Transient
    private String conf_password;

    private boolean activo = true;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConf_password() {
        return conf_password;
    }

    public void setConf_password(String conf_password) {
        this.conf_password = conf_password;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    @PreRemove
    public void preRemove() {
        this.activo = false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Usuario user = (Usuario) o;
        return id.equals(user.id) && usuario.equals(user.usuario) && email.equals(user.email);
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", usuario='" + usuario + '\'' +
                ", email='" + email + '\'' +
                ", activo=" + activo +
                '}';
    }
}
