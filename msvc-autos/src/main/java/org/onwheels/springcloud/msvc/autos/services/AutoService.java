package org.onwheels.springcloud.msvc.autos.services;

import org.onwheels.springcloud.msvc.autos.models.Usuario;
import org.onwheels.springcloud.msvc.autos.models.entity.Auto;

import java.util.List;
import java.util.Optional;

public interface AutoService {
    List<Auto> listar();

    List<Auto> listarActivos();

    List<Auto> listarDisponibles();

    Optional<Auto> porId(Long id);

    Auto guardar(Auto auto);

    void eliminar(Long id);

    Optional<Auto> porModelo(String modelo);

    // Métodos remotos
    Optional<Usuario> asignarUsuario(Usuario usuario, Long autoId);
    Optional<Usuario> asignarUsuarioPorId(Long usuarioId, Long autoId);
    Optional<Usuario> eliminarUsuario(Usuario usuario, Long autoId);
    Optional<Usuario> eliminarUsuarioPorId(Long usuarioId, Long autoId);
}
