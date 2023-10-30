package com.ms.cursos.payload.mapper;

import com.ms.cursos.model.Curso;
import com.ms.cursos.payload.dto.CursoDto;
import com.ms.cursos.payload.response.CursoResponse;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CursoResponseMapper {
    public CursoResponse getBuild(List<CursoDto> contentDto, Page<Curso> cursoPage) {
        return CursoResponse.builder()
                .content(contentDto)
                .page(cursoPage.getNumber())
                .size(cursoPage.getSize())
                .totalElements(cursoPage.getTotalElements())
                .totalPages(cursoPage.getTotalPages())
                .last(cursoPage.isLast())
                .build();
    }
}
