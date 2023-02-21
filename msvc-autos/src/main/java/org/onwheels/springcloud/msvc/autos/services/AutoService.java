package org.onwheels.springcloud.msvc.autos.services;

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
}
