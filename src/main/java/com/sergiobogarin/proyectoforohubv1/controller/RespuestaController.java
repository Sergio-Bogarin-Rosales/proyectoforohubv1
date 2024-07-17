package com.sergiobogarin.proyectoforohubv1.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.sergiobogarin.proyectoforohubv1.dominio.topico.TopicoRepository;
import com.sergiobogarin.proyectoforohubv1.dominio.respuesta.RespuestaDTO;
import com.sergiobogarin.proyectoforohubv1.dominio.respuesta.RespuestaActualizadaDTO;
import com.sergiobogarin.proyectoforohubv1.dominio.respuesta.RespuestaCreadaDTO;
import com.sergiobogarin.proyectoforohubv1.dominio.respuesta.ListarRespuestasDTO;
import com.sergiobogarin.proyectoforohubv1.dominio.respuesta.Respuesta;
import com.sergiobogarin.proyectoforohubv1.dominio.respuesta.RespuestaRepository;
import com.sergiobogarin.proyectoforohubv1.dominio.respuesta.RespuestaService;
import com.sergiobogarin.proyectoforohubv1.dominio.usuario.UsuarioRepository;
import com.sergiobogarin.proyectoforohubv1.infra.errores.ValidacionDeIntegridad;

public class RespuestaController {
    @Autowired
    private TopicoRepository topicoRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private RespuestaService respuestaService;
    @Autowired
    private RespuestaRepository repository;

    @PostMapping
    @Transactional
    public ResponseEntity respuestaRegistrada(@RequestBody @Valid RespuestaDTO respuestaDTO)
            throws ValidacionDeIntegridad {
        var respuestaRegistrada = respuestaService.respuestaCreadaDTO(respuestaDTO);
        return ResponseEntity.ok(respuestaRegistrada);
    }

    @GetMapping("/respuestas")
    public ResponseEntity<Page<Object>>  listarRespuestas(@PageableDefault(size = 10) Pageable paged){
        return ResponseEntity.ok(repository.findByActiveTrue(paged).map(ListarRespuestasDTO::new));
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity respuestaActualizada(@RequestBody @Valid RespuestaActualizadaDTO respuestaActualizadaDTO) {
        Respuesta respuesta = repository.getReferenceById(respuestaActualizadaDTO.id());
        respuesta.respuestaActualizada(respuestaActualizadaDTO);
        return ResponseEntity.ok(new RespuestaCreadaDTO(respuesta.getId(), respuesta.getSolution(),
                respuesta.getAuthor().getId(),
                respuesta.getTopico().getId(),
                respuesta.getCreationDate()));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity eliminarRespuesta(@PathVariable Long id) {
        Respuesta respuesta = repository.getReferenceById(id);
        respuesta.diactivateResponse();
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<RespuestaCreadaDTO> respuestaCreada(@PathVariable Long id) {
        Respuesta respuesta = repository.getReferenceById(id);
        var respuestaRegistrada = new RespuestaCreadaDTO(respuesta.getId(),
                respuesta.getSolution(),
                respuesta.getAuthor().getId(),
                respuesta.getTopico().getId(),
                respuesta.getCreationDate());
        return ResponseEntity.ok(respuestaRegistrada);
    }
}
