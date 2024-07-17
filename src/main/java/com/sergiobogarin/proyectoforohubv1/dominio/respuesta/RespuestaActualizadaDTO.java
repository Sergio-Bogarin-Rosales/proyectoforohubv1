package com.sergiobogarin.proyectoforohubv1.dominio.respuesta;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotNull;

public record RespuestaActualizadaDTO(
    @NotNull Long id,
        String solution,
        @NotNull Long usuario_Id,
        @NotNull Long topico_Id,
        LocalDateTime creationDate
) {

}
