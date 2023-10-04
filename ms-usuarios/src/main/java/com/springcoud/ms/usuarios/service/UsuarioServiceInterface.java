package com.springcoud.ms.usuarios.service;

import com.springcoud.ms.usuarios.model.Usuario;
import com.springcoud.ms.usuarios.payload.response.UsuarioResponse;
import com.springcoud.ms.usuarios.payload.dto.UsuarioDto;

import java.util.Optional;

public interface UsuarioServiceInterface {
    UsuarioResponse findAll(int page, int size, String sortBy);

    UsuarioDto findById(Long id);

    UsuarioDto save(UsuarioDto usrDto);

    UsuarioDto update(Long id, UsuarioDto usrDto);

    void delete(Long id);
}
