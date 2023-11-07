package com.ms.cursos.model;

import com.ms.cursos.payload.dto.UsuarioDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Builder
@AllArgsConstructor
@Data
@Entity
@Table(
        name = "cursos"
)
public class Curso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    private String nombre;

    @OneToMany(
            orphanRemoval = true,
            cascade       = CascadeType.ALL,
            fetch         = FetchType.LAZY
    )
    @JoinColumn(name = "curso_id")  // This is the name of the column in the table curso_usuario
    private List<CursoUsuario> cursoUsuarios;

    @Transient // This annotation is used to indicate that an attribute is not to be persisted in the database
    private List<UsuarioDto> usuarios;

    public Curso(){
        cursoUsuarios = new ArrayList<>();
        usuarios = new ArrayList<>();
    }

    public void addCursoUsuario(CursoUsuario cursoUsuario) {
        if (cursoUsuarios != null)
            cursoUsuarios.add(cursoUsuario);
    }

    public void addCursosUsuarios(List<CursoUsuario> cursoUsuarios) {
        if (cursoUsuarios != null)
            cursoUsuarios.forEach(this::addCursoUsuario);
    }

    public void removeCursoUsuario(CursoUsuario cursoUsuario) {
        if (cursoUsuarios != null)
            cursoUsuarios.remove(cursoUsuario);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Curso curso)) return false;
        return Objects.equals(id, curso.id)
                && Objects.equals(nombre, curso.nombre)
                && Objects.equals(cursoUsuarios, curso.cursoUsuarios);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nombre, cursoUsuarios);
    }
}
