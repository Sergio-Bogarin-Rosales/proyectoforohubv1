package com.sergiobogarin.proyectoforohubv1.dominio.usuario;

import jakarta.validation.constraints.NotNull;

public record ActualizacionUsuarioDTO(
        @NotNull Long id,
        String name,
        String email) {

}
