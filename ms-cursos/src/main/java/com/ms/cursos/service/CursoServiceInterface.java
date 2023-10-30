package com.ms.cursos.service;

import com.ms.cursos.model.Curso;
import com.ms.cursos.payload.dto.CursoDto;
import com.ms.cursos.payload.response.CursoResponse;

import java.util.Optional;

public interface CursoServiceInterface {
    CursoResponse findAll(int page, int size, String sortBy);

    Optional<CursoDto> findById(Long id);

    CursoDto save(CursoDto cursoDto);

    CursoDto update(Long id, CursoDto cursoDto);

    void delete(Long id);
}