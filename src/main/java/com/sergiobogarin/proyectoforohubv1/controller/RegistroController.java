package com.sergiobogarin.proyectoforohubv1.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.util.UriComponentsBuilder;

import com.sergiobogarin.proyectoforohubv1.dominio.usuario.RegistroUsuarioDTO;
import com.sergiobogarin.proyectoforohubv1.dominio.usuario.RespuestaUsuarioDTO;
import com.sergiobogarin.proyectoforohubv1.dominio.usuario.UsuarioRepository;
import com.sergiobogarin.proyectoforohubv1.dominio.usuario.UsuarioService;

import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;

public class RegistroController {
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping
    @Transactional
    public ResponseEntity<?> registrarUsuario(@RequestBody @Valid RegistroUsuarioDTO registroUsuarioDTO,
            UriComponentsBuilder uriComponentsBuilder) {
        try {
            RegistroUsuarioDTO usuario = usuarioService.registrarUsuario(registroUsuarioDTO);
            RespuestaUsuarioDTO respuestaUsuarioDTO;
            respuestaUsuarioDTO = new RespuestaUsuarioDTO(usuario.getId(), usuario.getName());
            URI url = uriComponentsBuilder.path("usuario/{id}").buildAndExpand(usuario.getId()).toUri();
            return ResponseEntity.created(url).body(respuestaUsuarioDTO);
        } catch (ConstraintViolationException ex) {
            return ResponseEntity.badRequest().body("Validation failed: " + ex.getMessage());
        }
    }
}
