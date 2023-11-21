package com.ms.cursos.clients;

import com.ms.cursos.payload.dto.UsuarioDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(
        name = "ms-usuarios",
        url = "${USUARIOS_SERVICE_URL}"
)
public interface UsuarioClientRemoteRest {
    @GetMapping("/{id}")
    public UsuarioDto getUsuarioByIdRemote(@PathVariable(name = "id") Long id);

    @PostMapping
    public UsuarioDto createUsuarioRemote(@RequestBody UsuarioDto usuarioDto);

    @GetMapping("/usuariosByIds")
    public List<UsuarioDto> getAllUsuariosByIdsRemote(@RequestParam Iterable<Long> ids);
}
