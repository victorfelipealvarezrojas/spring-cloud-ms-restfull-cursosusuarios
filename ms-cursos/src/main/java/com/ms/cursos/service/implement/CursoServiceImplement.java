package com.ms.cursos.service.implement;

import com.ms.cursos.clients.UsuarioClientRemoteRest;
import com.ms.cursos.exception.ResourceNotFoundException;
import com.ms.cursos.model.Curso;
import com.ms.cursos.model.CursoUsuario;
import com.ms.cursos.payload.dto.CursoDto;
import com.ms.cursos.payload.dto.UsuarioDto;
import com.ms.cursos.payload.mapper.CursoResponseMapper;
import com.ms.cursos.payload.mapper.CursoToDto;
import com.ms.cursos.payload.mapper.DtoToCurso;

import com.ms.cursos.payload.response.CursoResponse;
import com.ms.cursos.service.CursoServiceInterface;
import com.ms.cursos.repository.CursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CursoServiceImplement implements CursoServiceInterface {
    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private UsuarioClientRemoteRest usuarioClientRemoteRest;

    @Autowired
    private CursoToDto cursoToDto;

    @Autowired
    private DtoToCurso dtoToCurso;

    @Override
    @Transactional(readOnly = true)
    public CursoResponse findAll(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        Page<Curso> cursoPage = cursoRepository.findAll(pageable);

        List<CursoDto> contentDto = cursoPage.getContent()
                .stream()
                .map(this::mapToDto)
                .toList();

        return new CursoResponseMapper().getBuild(contentDto, cursoPage);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CursoDto> findById(Long id) {
        return cursoRepository.findById(id)
                .map(this::mapToDto)
                .or(() -> resourceNotFound(id));
    }

    @Override
    @Transactional()
    public CursoDto save(CursoDto cursoDto) {
        Curso curso = cursoRepository.save(mapToEntity(cursoDto));
        return mapToDto(curso);
    }

    @Override
    @Transactional
    public CursoDto update(Long id, CursoDto cursoDto) {
        cursoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Curso", "id", id));
        Curso curso = cursoRepository.save(mapToEntity(cursoDto));
        return mapToDto(curso);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        cursoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Curso", "id", id));
        cursoRepository.deleteById(id);
    }

    @Override
    @Transactional
    public Optional<UsuarioDto> assignUserToCurso(Long CursoId, UsuarioDto usuarioDto) {
        Curso curso = cursoRepository.findById(CursoId)
                .orElseThrow(() -> new ResourceNotFoundException("Curso", "id", CursoId));

        UsuarioDto usuarioRemote = usuarioClientRemoteRest.getUsuarioByIdRemote(usuarioDto.getId());

        CursoUsuario cursoUsuario = new CursoUsuario();
        cursoUsuario.setId(CursoId);
        cursoUsuario.setUsuarioId(usuarioRemote.getId());

        curso.addCursoUsuario(cursoUsuario);
        cursoRepository.save(curso);
        return Optional.of(usuarioRemote);
    }

    @Override
    @Transactional
    public Optional<UsuarioDto> createUserToCurso(Long CursoId, UsuarioDto usuarioDto) {
        Curso curso = cursoRepository.findById(CursoId)
                .orElseThrow(() -> new ResourceNotFoundException("Curso", "id", CursoId));

        UsuarioDto newUsuarioRemote = usuarioClientRemoteRest.createUsuarioRemote(usuarioDto);

        CursoUsuario cursoUsuario = new CursoUsuario();
        cursoUsuario.setId(CursoId);
        cursoUsuario.setUsuarioId(newUsuarioRemote.getId());

        curso.addCursoUsuario(cursoUsuario);
        cursoRepository.save(curso);
        return Optional.of(newUsuarioRemote);
    }

    @Override
    @Transactional
    public Optional<UsuarioDto> deleteUserToCurso(Long CursoId, UsuarioDto usuarioDto) {
        Curso curso = cursoRepository.findById(CursoId)
                .orElseThrow(() -> new ResourceNotFoundException("Curso", "id", CursoId));

        UsuarioDto usuarioRemote = usuarioClientRemoteRest.getUsuarioByIdRemote(usuarioDto.getId());

        CursoUsuario cursoUsuario = new CursoUsuario();
        cursoUsuario.setId(CursoId);
        cursoUsuario.setUsuarioId(usuarioRemote.getId());

        curso.removeCursoUsuario(cursoUsuario);
        cursoRepository.save(curso);
        return Optional.of(usuarioRemote);
    }

    @Override
    public Optional<CursoDto> FindCursoIdWithUser(Long CursoId) {
        Curso curso = cursoRepository.findById(CursoId)
                .orElseThrow(() -> new ResourceNotFoundException("Curso", "id", CursoId));

        if (!curso.getCursoUsuarios().isEmpty()) {
            List<Long> ids = curso.getCursoUsuarios().stream().map(CursoUsuario::getUsuarioId).toList();
            List<UsuarioDto> usuarios = usuarioClientRemoteRest.getAllUsuariosByIdsRemote(ids);
            curso.setUsuarios(usuarios);
            return Optional.of(cursoToDto.CursoMapToDto(curso));
        }
        return Optional.empty();
    }

    private CursoDto mapToDto(Curso curso) {
        return new CursoToDto().CursoMapToDto(curso);
    }

    private Curso mapToEntity(CursoDto cursoDto) {
        return new DtoToCurso().DtoMapToCurso(cursoDto);
    }

    private Optional<CursoDto> resourceNotFound(Long id) {
        throw new ResourceNotFoundException("Curso", "id", id);
    }
}