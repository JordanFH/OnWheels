package org.onwheels.springcloud.msvc.usuarios.controllers;

import jakarta.validation.Valid;
import org.onwheels.springcloud.msvc.usuarios.models.entity.Usuario;
import org.onwheels.springcloud.msvc.usuarios.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/usuario")
public class UsuarioController {
    @Autowired
    private UsuarioService service;

    @GetMapping("/todos")
    public ResponseEntity<?> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping
    public ResponseEntity<?> listarActivos() {
        return ResponseEntity.ok(service.listarActivos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> detalle(@PathVariable Long id) {
        Optional<Usuario> optional = service.porId(id);
        if (optional.isPresent()) {
            Usuario dbUsuario = optional.get();
            if (dbUsuario.isActivo()) {
                return ResponseEntity.ok(dbUsuario);
            }
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<?> crear(@Valid @RequestBody Usuario usuario, BindingResult result) {
        if (result.hasErrors()) {
            return validar(result);
        }
        if (service.porUsuario(usuario.getUsuario()).isPresent()) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("Mensaje", "Ya existe ese usuario"));
        }
        if (service.porEmail(usuario.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("Mensaje", "Ya existe un usuario con ese email"));
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(service.guardar(usuario));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editar(@Valid @RequestBody Usuario usuario, BindingResult result, @PathVariable Long id) {
        if (result.hasErrors()) {
            return validar(result);
        }
        Optional<Usuario> optional = service.porId(id);
        if (optional.isPresent()) {
            Usuario dbUsuario = optional.get();
            if (!usuario.getUsuario().equalsIgnoreCase(dbUsuario.getUsuario()) && service.porUsuario(usuario.getUsuario()).isPresent()) {
                return ResponseEntity.badRequest().body(Collections.singletonMap("Mensaje", "Ya existe ese usuario"));
            }
            if (!usuario.getEmail().equalsIgnoreCase(dbUsuario.getEmail()) && service.porEmail(usuario.getEmail()).isPresent()) {
                return ResponseEntity.badRequest().body(Collections.singletonMap("Mensaje", "Ya existe un usuario con ese email"));
            }
            dbUsuario.setUsuario(usuario.getUsuario());
            dbUsuario.setEmail(usuario.getEmail());
            dbUsuario.setPassword(usuario.getPassword());
            dbUsuario.setActivo(usuario.isActivo());
            return ResponseEntity.status(HttpStatus.CREATED).body(service.guardar(dbUsuario));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        Optional<Usuario> optional = service.porId(id);
        if (optional.isPresent()) {
            Usuario dbUsuario = optional.get();
            dbUsuario.setActivo(false);
            service.guardar(dbUsuario);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    /*
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        Optional<Usuario> optional = service.porId(id);
        if (optional.isPresent()) {
            service.eliminar(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
    */

    private ResponseEntity<Map<String, String>> validar(BindingResult result) {
        Map<String, String> errores = new HashMap<>();
        result.getFieldErrors().forEach(error -> errores.put(error.getField(), error.getDefaultMessage()));
        return ResponseEntity.badRequest().body(errores);
    }
}
