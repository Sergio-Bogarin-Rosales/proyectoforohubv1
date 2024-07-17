package com.sergiobogarin.proyectoforohubv1.dominio.respuesta;

import java.time.LocalDateTime;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RespuestaDTO(
        @NotBlank String solution,
        @NotNull @Valid Long usuario_Id,
        @NotNull @Valid Long topico_Id,
        LocalDateTime creationDate) {

}
