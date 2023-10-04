package com.springcoud.ms.usuarios.payload.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UsuarioDto {
    private Long id;
    private String nombre;
    private String email;
    private String password;
}
