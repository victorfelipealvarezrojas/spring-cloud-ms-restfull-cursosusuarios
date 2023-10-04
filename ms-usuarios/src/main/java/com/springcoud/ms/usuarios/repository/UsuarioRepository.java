package com.springcoud.ms.usuarios.repository;

import com.springcoud.ms.usuarios.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {}
