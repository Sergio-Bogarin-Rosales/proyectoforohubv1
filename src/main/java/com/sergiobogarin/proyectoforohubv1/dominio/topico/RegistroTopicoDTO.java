package com.sergiobogarin.proyectoforohubv1.dominio.topico;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RegistroTopicoDTO(
        @NotBlank(message = "Título es obligatorio") String title,
        @NotBlank(message = "Mensaje es obligatorio") String message,
        @NotBlank(message = "Curso es obligatorio") String course,
        @NotNull(message = "Author_id es obligatorio") Long author_id) {

}
