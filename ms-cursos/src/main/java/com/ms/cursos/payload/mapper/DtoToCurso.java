package com.ms.cursos.payload.mapper;

import com.ms.cursos.model.Curso;
import com.ms.cursos.payload.dto.CursoDto;
import org.springframework.stereotype.Component;

@Component
public class DtoToCurso {
    public Curso DtoMapToCurso(CursoDto usuario) {
        return Curso.builder()
                .id(usuario.getId())
                .nombre(usuario.getNombre())
                .build();
    }
}
