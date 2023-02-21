package org.onwheels.springcloud.msvc.autos.repositories;

import org.onwheels.springcloud.msvc.autos.models.entity.Auto;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface AutoRepository extends CrudRepository<Auto, Long> {
    Optional<Auto> findByModelo(String modelo);
    List<Auto> findByDisponibleTrue();
    List<Auto> findByActivoTrue();
}
