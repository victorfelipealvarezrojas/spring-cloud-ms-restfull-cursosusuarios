package com.springcoud.ms.usuarios.payload.mapper;

import com.springcoud.ms.usuarios.model.Usuario;
import com.springcoud.ms.usuarios.payload.dto.UsuarioDto;
import org.springframework.stereotype.Component;

@Component
public class UsuarioToDto {
    public UsuarioDto UsuarioMapToDto(Usuario usuario) {
        return UsuarioDto.builder()
                .id(usuario.getId())
                .nombre(usuario.getNombre())
                .email(usuario.getEmail())
                .password(usuario.getPassword())
                .build();
    }
}
