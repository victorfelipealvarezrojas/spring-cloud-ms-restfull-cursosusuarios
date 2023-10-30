package com.ms.cursos.payload.response;

import com.ms.cursos.payload.dto.CursoDto;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class CursoResponse {
    private List<CursoDto> content;
    private int page;
    private int size;
    private long totalElements;
    private int totalPages;
    private boolean last;
}
