package com.ms.cursos.payload.mapper;

import com.ms.cursos.model.Curso;
import com.ms.cursos.payload.dto.CursoDto;
import org.springframework.stereotype.Component;

@Component
public class CursoToDto {
    public CursoDto CursoMapToDto(Curso curso) {
        return CursoDto.builder()
                .id(curso.getId())
                .nombre(curso.getNombre())
                .build();
    }
}
