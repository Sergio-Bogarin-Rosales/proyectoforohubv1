package com.sergiobogarin.proyectoforohubv1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sergiobogarin.proyectoforohubv1.dominio.usuario.RegistroUsuarioDTO;
import com.sergiobogarin.proyectoforohubv1.dominio.usuario.UsuarioService;

@RestController
@RequestMapping("usuario")
public class DesactivacionUsuarioController {
    @Autowired
    private UsuarioService usuarioService;

    @DeleteMapping("/{id}/desactivar")
    public ResponseEntity<?> desactivarUsuario(@PathVariable Long id) {
        try {
            RegistroUsuarioDTO respuesta = usuarioService.desactivarUser(id);
            return ResponseEntity.ok(respuesta);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
