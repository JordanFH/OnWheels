package org.onwheels.springcloud.msvc.autos.services;

import org.onwheels.springcloud.msvc.autos.clients.UsuarioClientRest;
import org.onwheels.springcloud.msvc.autos.models.Usuario;
import org.onwheels.springcloud.msvc.autos.models.entity.Alquiler;
import org.onwheels.springcloud.msvc.autos.models.entity.Auto;
import org.onwheels.springcloud.msvc.autos.repositories.AutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class AutoServiceImpl implements AutoService {
    @Autowired
    private AutoRepository repository;
    @Autowired
    private UsuarioClientRest client;

    @Transactional(readOnly = true)
    @Override
    public List<Auto> listar() {
        return (List<Auto>) repository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public List<Auto> listarActivos() {
        return repository.findByActivoTrue();
    }

    @Transactional(readOnly = true)
    @Override
    public List<Auto> listarDisponibles() {
        return repository.findByDisponibleTrue();
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Auto> porId(Long id) {
        return repository.findById(id);
    }

    @Transactional
    @Override
    public Auto guardar(Auto auto) {
        return repository.save(auto);
    }

    @Transactional
    @Override
    public void eliminar(Long id) {
        repository.deleteById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Auto> porModelo(String modelo) {
        return repository.findByModelo(modelo);
    }

    // Métodos remotos
    @Transactional
    @Override
    public Optional<Usuario> asignarUsuario(Usuario usuario, Long autoId) {
        Optional<Auto> optional = repository.findById(autoId);
        if (optional.isPresent()) {
            Usuario usuarioMsvc = client.detalle(usuario.getId());
            return asignarAlquiler(optional, usuarioMsvc);
        }
        return Optional.empty();
    }

    @Override
    public Optional<Usuario> asignarUsuarioPorId(Long usuarioId, Long autoId) {
        Optional<Auto> optional = repository.findById(autoId);
        Usuario usuarioMsvc = client.detalle(usuarioId);
        if (optional.isPresent() && usuarioMsvc != null) {
            return asignarAlquiler(optional, usuarioMsvc);
        }
        return Optional.empty();
    }

    private Optional<Usuario> asignarAlquiler(Optional<Auto> optional, Usuario usuarioMsvc) {
        Auto auto = optional.get();
        if(!auto.isDisponible()){
            return Optional.empty();
        }
        auto.setDisponible(false);
        Alquiler alquiler = new Alquiler();
        alquiler.setUsuarioId(usuarioMsvc.getId());
        auto.addAlquiler(alquiler);
        repository.save(auto);
        return Optional.of(usuarioMsvc);
    }

    @Transactional
    @Override
    public Optional<Usuario> eliminarUsuario(Usuario usuario, Long autoId) {
        Optional<Auto> optional = repository.findById(autoId);
        if (optional.isPresent()) {
            Usuario usuarioMsvc = client.detalle(usuario.getId());
            return eliminarAlquiler(optional, usuarioMsvc);
        }
        return Optional.empty();
    }

    @Transactional
    @Override
    public Optional<Usuario> eliminarUsuarioPorId(Long usuarioId, Long autoId) {
        Optional<Auto> optional = repository.findById(autoId);
        Usuario usuarioMsvc = client.detalle(usuarioId);
        if (optional.isPresent() && usuarioMsvc != null) {
            return eliminarAlquiler(optional, usuarioMsvc);
        }
        return Optional.empty();
    }

    private Optional<Usuario> eliminarAlquiler(Optional<Auto> optional, Usuario usuarioMsvc) {
        Auto auto = optional.get();
        auto.setDisponible(true);
        Alquiler alquiler = new Alquiler();
        alquiler.setUsuarioId(usuarioMsvc.getId());
        auto.removeAlquiler(alquiler);
        repository.save(auto);
        return Optional.of(usuarioMsvc);
    }
}
