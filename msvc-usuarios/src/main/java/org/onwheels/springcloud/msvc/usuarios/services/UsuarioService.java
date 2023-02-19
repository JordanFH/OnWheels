package org.onwheels.springcloud.msvc.usuarios.services;

import org.onwheels.springcloud.msvc.usuarios.models.entity.Usuario;

import java.util.List;
import java.util.Optional;

public interface UsuarioService {

    List<Usuario> listar();

    List<Usuario> listarActivos();

    Optional<Usuario> porId(Long id);

    Usuario guardar(Usuario usuario);

    void eliminar(Long id);

    Optional<Usuario> porUsuario(String usuario);
    Optional<Usuario> porEmail(String email);
}
