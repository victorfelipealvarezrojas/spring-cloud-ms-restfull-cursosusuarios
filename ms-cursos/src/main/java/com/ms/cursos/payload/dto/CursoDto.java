package com.ms.cursos.payload.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CursoDto {
    private Long id;
    private String nombre;
}
