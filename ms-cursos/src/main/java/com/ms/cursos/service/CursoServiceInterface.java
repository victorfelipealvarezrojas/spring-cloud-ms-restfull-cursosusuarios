package com.ms.cursos.service;

import com.ms.cursos.model.Curso;

import java.util.List;
import java.util.Optional;

public interface CursoServiceInterface {
    List<Curso> findAll(int page, int size, String sortBy);

    Optional<Curso> findById(Long id);

    Curso save(Curso cursoDto);

    Curso update(Long id, Curso cursoDto);

    void delete(Long id);
}