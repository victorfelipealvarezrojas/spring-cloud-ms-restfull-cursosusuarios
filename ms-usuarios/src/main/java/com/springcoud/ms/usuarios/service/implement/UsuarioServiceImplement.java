package com.springcoud.ms.usuarios.service.implement;

import com.springcoud.ms.usuarios.exception.ResourceNotFoundException;
import com.springcoud.ms.usuarios.model.Usuario;
import com.springcoud.ms.usuarios.payload.mapper.DtoToUsuario;
import com.springcoud.ms.usuarios.payload.mapper.UserResponse;
import com.springcoud.ms.usuarios.payload.mapper.UsuarioToDto;
import com.springcoud.ms.usuarios.payload.response.UsuarioResponse;
import com.springcoud.ms.usuarios.payload.dto.UsuarioDto;
import com.springcoud.ms.usuarios.repository.UsuarioRepository;
import com.springcoud.ms.usuarios.service.UsuarioServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UsuarioServiceImplement implements UsuarioServiceInterface {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioToDto usuarioToDto;

    @Autowired
    private DtoToUsuario dtoToUsuario;

    @Override
    @Transactional(readOnly = true)
    public UsuarioResponse findAll(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        Page<Usuario> usuarioPage = usuarioRepository.findAll(pageable);

        List<UsuarioDto> contentDto = usuarioPage.getContent()
                .stream()
                .map(this::mapToDto)
                .toList();

        return new UserResponse().getBuild(contentDto, usuarioPage);
    }

    @Override
    public List<UsuarioDto> findAllByIds(Iterable<Long> ids) {
        return usuarioRepository.findAllById(ids)
                .stream()
                .map(this::mapToDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public UsuarioDto findById(Long id) {
        return usuarioRepository.findById(id)
                .map(this::mapToDto)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Usuario", "id", id));
    }

    @Override
    @Transactional
    public UsuarioDto save(UsuarioDto usrDto) {
        Usuario usr = usuarioRepository.save(mapToEntity(usrDto));
        return mapToDto(usr);
    }

    @Override
    public UsuarioDto update(Long id, UsuarioDto usrDto) {
        usuarioRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Usuario", "id", id));

        Usuario usrUpdate = usuarioRepository.save(mapToEntity(usrDto));
        return mapToDto(usrUpdate);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        usuarioRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Usuario", "id", id));
        usuarioRepository.deleteById(id);
    }

    private UsuarioDto mapToDto(Usuario usuario) {
        return new UsuarioToDto().UsuarioMapToDto(usuario);
    }

    private Usuario mapToEntity(UsuarioDto usuarioDto) {
        return new DtoToUsuario().DtoMapToUsuario(usuarioDto);
    }
}