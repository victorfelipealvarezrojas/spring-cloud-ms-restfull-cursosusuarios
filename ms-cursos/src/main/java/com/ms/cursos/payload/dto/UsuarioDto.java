package com.ms.cursos.payload.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioDto {
    private Long id;
    private String nombre;
    private String email;
    private String password;
}