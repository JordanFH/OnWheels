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
    public Optional<Usuario> asignarUsuario(Usuario usuario, Long cursoId) {
        Optional<Auto> optional = repository.findById(cursoId);
        if (optional.isPresent()) {
            Usuario usuarioMsvc = client.detalle(usuario.getId());
            Auto auto = optional.get();
            Alquiler alquiler = new Alquiler();
            alquiler.setUsuarioId(usuarioMsvc.getId());
            auto.addAlquiler(alquiler);
            repository.save(auto);
            return Optional.of(usuarioMsvc);
        }
        return Optional.empty();
    }

    @Transactional
    @Override
    public Optional<Usuario> eliminarUsuario(Usuario usuario, Long cursoId) {
        Optional<Auto> optional = repository.findById(cursoId);
        if (optional.isPresent()) {
            Usuario usuarioMsvc = client.detalle(usuario.getId());
            Auto auto = optional.get();
            Alquiler alquiler = new Alquiler();
            alquiler.setUsuarioId(usuarioMsvc.getId());
            auto.removeAlquiler(alquiler);
            repository.save(auto);
            return Optional.of(usuarioMsvc);
        }
        return Optional.empty();
    }
}
