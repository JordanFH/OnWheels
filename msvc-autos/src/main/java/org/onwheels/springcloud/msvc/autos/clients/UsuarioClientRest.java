package org.onwheels.springcloud.msvc.autos.clients;

import org.onwheels.springcloud.msvc.autos.models.Usuario;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "msvc-usuarios", url = "localhost:8001/api/usuario")
public interface UsuarioClientRest {
    @GetMapping("/{id}")
    Usuario detalle(@PathVariable Long id);
}
