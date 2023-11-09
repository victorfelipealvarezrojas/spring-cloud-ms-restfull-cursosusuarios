package com.ms.cursos.clients;

import com.ms.cursos.payload.dto.UsuarioDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(
        name = "ms-usuarios",
        url = "localhost:8001"
)
public interface UsuarioClientRemoteRest {
    @GetMapping("/{id}")
    public UsuarioDto getUsuarioByIdRemote(@PathVariable(name = "id") Long id);

    @PostMapping
    public UsuarioDto createUsuarioRemote(@RequestBody UsuarioDto usuarioDto);
}
