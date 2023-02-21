package org.onwheels.springcloud.msvc.autos.controllers;

import jakarta.validation.Valid;
import org.onwheels.springcloud.msvc.autos.models.entity.Auto;
import org.onwheels.springcloud.msvc.autos.services.AutoService;
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
@RequestMapping("/api/auto")
public class AutoController {
    @Autowired
    private AutoService service;

    @GetMapping("/todos")
    public ResponseEntity<?> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/disponibles")
    public ResponseEntity<?> listarDisponibles() {
        return ResponseEntity.ok(service.listarDisponibles());
    }

    @GetMapping
    public ResponseEntity<?> listarActivos() {
        return ResponseEntity.ok(service.listarActivos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> detalle(@PathVariable Long id) {
        Optional<Auto> optional = service.porId(id);
        if (optional.isPresent()) {
            Auto dbAuto = optional.get();
            if (dbAuto.isActivo()) {
                return ResponseEntity.ok(dbAuto);
            }
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<?> crear(@Valid @RequestBody Auto auto, BindingResult result) {
        if (result.hasErrors()) {
            return validar(result);
        }
        if (service.porModelo(auto.getModelo()).isPresent()) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("Mensaje", "Ya existe un auto con ese modelo"));
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(service.guardar(auto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editar(@Valid @RequestBody Auto auto, BindingResult result, @PathVariable Long id) {
        if (result.hasErrors()) {
            return validar(result);
        }
        Optional<Auto> optional = service.porId(id);
        if (optional.isPresent()) {
            Auto dbAuto = optional.get();
            if (!auto.getModelo().equalsIgnoreCase(dbAuto.getModelo()) && service.porModelo(auto.getModelo()).isPresent()) {
                return ResponseEntity.badRequest().body(Collections.singletonMap("Mensaje", "Ya existe un auto con ese modelo"));
            }
            dbAuto.setMarca(auto.getMarca());
            dbAuto.setModelo(auto.getModelo());
            dbAuto.setAnio(auto.getAnio());
            dbAuto.setCosto_hora(auto.getCosto_hora());
            dbAuto.setDisponible(auto.isDisponible());
            dbAuto.setActivo(auto.isActivo());
            return ResponseEntity.status(HttpStatus.CREATED).body(service.guardar(dbAuto));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        Optional<Auto> optional = service.porId(id);
        if (optional.isPresent()) {
            Auto dbAuto = optional.get();
            dbAuto.setActivo(false);
            service.guardar(dbAuto);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    private ResponseEntity<Map<String, String>> validar(BindingResult result) {
        Map<String, String> errores = new HashMap<>();
        result.getFieldErrors().forEach(error -> errores.put(error.getField(), error.getDefaultMessage()));
        return ResponseEntity.badRequest().body(errores);
    }
}
