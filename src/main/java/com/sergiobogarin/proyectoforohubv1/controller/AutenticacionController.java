package com.sergiobogarin.proyectoforohubv1.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import com.sergiobogarin.proyectoforohubv1.dominio.usuario.Usuario;
import com.sergiobogarin.proyectoforohubv1.dominio.usuario.UsuarioDTO;
import com.sergiobogarin.proyectoforohubv1.infra.seguridad.JWTTokenDTO;
import com.sergiobogarin.proyectoforohubv1.infra.seguridad.TokenService;

//import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
public class AutenticacionController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    @Transactional
    public ResponseEntity usuarioAutenticacion(@RequestBody @Valid UsuarioDTO usuarioDTO) {
        Authentication authToken = new UsernamePasswordAuthenticationToken(usuarioDTO.email(), usuarioDTO.password());
        var autenticacionUsuario = authenticationManager.authenticate(authToken);
        var token = tokenService.generarToken((Usuario) autenticacionUsuario.getPrincipal());
        return ResponseEntity.ok(new JWTTokenDTO(token));
    }
}