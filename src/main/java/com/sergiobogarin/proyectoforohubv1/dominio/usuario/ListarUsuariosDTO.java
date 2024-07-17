package com.sergiobogarin.proyectoforohubv1.dominio.usuario;

public record ListarUsuariosDTO(
                Long id,
                String name,
                String email) {
        public ListarUsuariosDTO(Usuario usuario) {

                this(usuario.getId(), usuario.getName(), usuario.getEmail());
        }
}
