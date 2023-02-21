package org.onwheels.springcloud.msvc.usuarios.services;

import org.onwheels.springcloud.msvc.usuarios.models.entity.Usuario;
import org.onwheels.springcloud.msvc.usuarios.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServiceImpl implements UsuarioService {
    @Autowired
    private UsuarioRepository repository;

    @Transactional(readOnly = true)
    @Override
    public List<Usuario> listar() {
        return (List<Usuario>) repository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public List<Usuario> listarActivos() {
        return repository.findByActivoTrue();
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Usuario> porId(Long id) {
        return repository.findById(id);
    }

    @Transactional
    @Override
    public Usuario guardar(Usuario usuario) {
        return repository.save(usuario);
    }

    @Transactional
    @Override
    public void eliminar(Long id) {
        repository.deleteById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Usuario> porUsuario(String usuario) {
        return repository.findByUsuario(usuario);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Usuario> porEmail(String email) {
        return repository.findByEmail(email);
    }
}
