package com.springcoud.ms.usuarios.payload.response;

import com.springcoud.ms.usuarios.payload.dto.UsuarioDto;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class UsuarioResponse {
    private List<UsuarioDto> content;
    private int page;
    private int size;
    private long totalElements;
    private int totalPages;
    private boolean last;
}
