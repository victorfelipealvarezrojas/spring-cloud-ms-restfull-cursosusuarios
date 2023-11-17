package com.springcoud.ms.usuarios.controller;

import com.springcoud.ms.usuarios.payload.dto.UsuarioDto;
import com.springcoud.ms.usuarios.payload.response.UsuarioResponse;
import com.springcoud.ms.usuarios.service.UsuarioServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {
    @Autowired
    private UsuarioServiceInterface usuarioService;

    @GetMapping
    public ResponseEntity<UsuarioResponse> getAllUsuarios(
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "5", required = false) int size,
            @RequestParam(name = "sortBy", defaultValue = "id", required = false) String sortBy
    ) {
        return ResponseEntity.ok(usuarioService.findAll(page, size, sortBy));
    }

    @GetMapping("/usuariosByIds")
    public ResponseEntity<?> getAllUsuariosByIds(@RequestParam List<Long> ids) {
        return ResponseEntity.ok(usuarioService.findAllByIds(ids));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDto> getUsuarioById(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(usuarioService.findById(id));
    }

    @PostMapping
    public ResponseEntity<UsuarioDto> createUsuario(@RequestBody UsuarioDto usuarioDto) {
        return ResponseEntity.ok(usuarioService.save(usuarioDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDto> updateUsuario(@PathVariable(name = "id") Long id,
                                                    @RequestBody UsuarioDto usuarioDto) {
        return ResponseEntity.ok(usuarioService.update(id, usuarioDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUsuario(@PathVariable(name = "id") Long id) {
        usuarioService.delete(id);
        return ResponseEntity.ok("Usuario deleted successfully!!!");
    }
}
