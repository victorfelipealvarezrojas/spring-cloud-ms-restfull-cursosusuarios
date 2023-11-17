package com.ms.cursos.controller;

import com.ms.cursos.payload.dto.CursoDto;
import com.ms.cursos.payload.dto.UsuarioDto;
import com.ms.cursos.payload.response.CursoResponse;
import com.ms.cursos.service.CursoServiceInterface;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Optional;

@RestController
@RequestMapping("/api/cursos")
public class CursoController {

    @Autowired
    private CursoServiceInterface cursoService;

    @GetMapping
    public ResponseEntity<CursoResponse> getAllCursos(
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "5", required = false) int size,
            @RequestParam(name = "sortBy", defaultValue = "id", required = false) String sortBy
    ) {
        return ResponseEntity.ok(cursoService.findAll(page, size, sortBy));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CursoDto> getCursoById(@PathVariable(name = "id") Long id) {
        Optional<CursoDto> cursoOptional = cursoService.findById(id);

        return cursoOptional
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("v2/{id}")
    public ResponseEntity<CursoDto> getCursoByIds(@PathVariable(name = "id") Long id) {
        Optional<CursoDto> cursoOptional = cursoService.FindCursoIdWithUser(id);

        return cursoOptional
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<CursoDto> createCurso(@RequestBody CursoDto cursoDto) {
        return ResponseEntity.ok(cursoService.save(cursoDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CursoDto> updateCurso(@PathVariable(name = "id") Long id,
                                                 @RequestBody CursoDto cursoDto) {
        return ResponseEntity.ok(cursoService.update(id, cursoDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCurso(@PathVariable(name = "id") Long id) {
        cursoService.delete(id);
        return ResponseEntity.ok("Curso deleted successfully");
    }

    // method remotes for user
    @PutMapping("/asignar-usuario-toCurso/{cursoId}")
    public ResponseEntity<?> asignarUsuario(@RequestBody UsuarioDto usuario, @PathVariable Long cursoId) {
        Optional<UsuarioDto> usuarioOptional;
        try {
            usuarioOptional = cursoService.assignUserToCurso(cursoId, usuario);
        } catch (FeignException.FeignClientException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("mensaje", "Error Server Remote"));
        }

        if (usuarioOptional.isPresent())
            return ResponseEntity.status(HttpStatus.OK).body(usuarioOptional.get());

        return ResponseEntity.notFound().build();
    }

    @PostMapping("/crear-usuario-toCurso/{cursoId}")
    public ResponseEntity<?> crearUsuario(@RequestBody UsuarioDto usuario, @PathVariable Long cursoId) {
        Optional<UsuarioDto> usuarioOptional;
        try {
            usuarioOptional = cursoService.createUserToCurso(cursoId, usuario);
        } catch (FeignException.FeignClientException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("mensaje", "Error Server Remote"));
        }

        if (usuarioOptional.isPresent())
            return ResponseEntity.status(HttpStatus.CREATED).body(usuarioOptional.get());

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/eliminar-usuario-toCurso/{cursoId}")
    public ResponseEntity<?> eliminarUsuario(@RequestBody UsuarioDto usuario, @PathVariable Long cursoId) {
        Optional<UsuarioDto> usuarioOptional;
        try {
            usuarioOptional = cursoService.deleteUserToCurso(cursoId, usuario);
        } catch (FeignException.FeignClientException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("mensaje", "Error Server Remote"));
        }

        if (usuarioOptional.isPresent())
            return ResponseEntity.status(HttpStatus.OK).body(usuarioOptional.get());

        return ResponseEntity.notFound().build();
    }
}