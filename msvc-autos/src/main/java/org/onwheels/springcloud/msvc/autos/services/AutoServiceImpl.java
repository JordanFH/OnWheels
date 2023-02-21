package org.onwheels.springcloud.msvc.autos.services;

import org.onwheels.springcloud.msvc.autos.models.entity.Auto;
import org.onwheels.springcloud.msvc.autos.repositories.AutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AutoServiceImpl implements AutoService {
    @Autowired
    private AutoRepository repository;

    @Override
    public List<Auto> listar() {
        return (List<Auto>) repository.findAll();
    }

    @Override
    public List<Auto> listarActivos() {
        return repository.findByActivoTrue();
    }

    @Override
    public List<Auto> listarDisponibles() {
        return repository.findByDisponibleTrue();
    }

    @Override
    public Optional<Auto> porId(Long id) {
        return repository.findById(id);
    }

    @Override
    public Auto guardar(Auto auto) {
        return repository.save(auto);
    }

    @Override
    public void eliminar(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Optional<Auto> porModelo(String modelo) {
        return repository.findByModelo(modelo);
    }
}
