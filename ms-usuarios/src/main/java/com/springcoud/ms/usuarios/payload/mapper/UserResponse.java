package com.springcoud.ms.usuarios.payload.mapper;

import com.springcoud.ms.usuarios.model.Usuario;
import com.springcoud.ms.usuarios.payload.dto.UsuarioDto;
import com.springcoud.ms.usuarios.payload.response.UsuarioResponse;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserResponse {
    public UsuarioResponse getBuild(List<UsuarioDto> contentDto, Page<Usuario> usuarioPage) {
        return UsuarioResponse.builder()
                .content(contentDto)
                .page(usuarioPage.getNumber())
                .size(usuarioPage.getSize())
                .totalElements(usuarioPage.getTotalElements())
                .totalPages(usuarioPage.getTotalPages())
                .last(usuarioPage.isLast())
                .build();
    }
}
