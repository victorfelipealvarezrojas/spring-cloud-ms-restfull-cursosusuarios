package com.ms.cursos.payload.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class CursoDto {
    private Long id;
    private String nombre;
    private List<UsuarioDto> usuarios;
}
