package com.sergiobogarin.proyectoforohubv1.dominio.topico;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotNull;

public record TopicoActualizadoDTO(
    @NotNull Long id,
    String title,
    String message,
    Status status,
    @NotNull
    Long usuario_Id,
    String curso,
    LocalDateTime date) {
}
