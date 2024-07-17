package com.sergiobogarin.proyectoforohubv1.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sergiobogarin.proyectoforohubv1.dominio.usuario.ActualizacionUsuarioDTO;
import com.sergiobogarin.proyectoforohubv1.dominio.usuario.ListarUsuariosDTO;
import com.sergiobogarin.proyectoforohubv1.dominio.usuario.RespuestaUsuarioDTO;
import com.sergiobogarin.proyectoforohubv1.dominio.usuario.Usuario;
import com.sergiobogarin.proyectoforohubv1.dominio.usuario.UsuarioRepository;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/usuario")
@SecurityRequirement(name = "bearer-key")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping("/usuarios")
    public ResponseEntity<Page<ListarUsuariosDTO>> listarUsuarios(@PageableDefault(size = 10) Pageable paged) {
        return ResponseEntity.ok(usuarioRepository.findByActiveTrue(paged).map(ListarUsuariosDTO::new));
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity actualizacionUsuario(@RequestBody @Valid ActualizacionUsuarioDTO actualizacionUsuarioDTO) {
        Usuario usuario = usuarioRepository.getReferenceById(actualizacionUsuarioDTO.id());
        usuario.actualizacionUsuario(actualizacionUsuarioDTO);
        return ResponseEntity.ok(new ActualizacionUsuarioDTO(usuario.getId(), usuario.getName(), usuario.getEmail()));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity eliminarUsuario(@PathVariable Long id) {
        Usuario usuario = usuarioRepository.getReferenceById(id);
        usuario.deactivateUser();
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<RespuestaUsuarioDTO> registrarUsuario(@PathVariable Long id) {
        Usuario usuario = usuarioRepository.getReferenceById(id);
        var usuarioDetail = new RespuestaUsuarioDTO(usuario.getId(), usuario.getName());
        return ResponseEntity.ok(usuarioDetail);
    }
}
