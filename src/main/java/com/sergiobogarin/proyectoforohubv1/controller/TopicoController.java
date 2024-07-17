package com.sergiobogarin.proyectoforohubv1.controller;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.sergiobogarin.proyectoforohubv1.dominio.topico.TopicoActualizadoDTO;
import com.sergiobogarin.proyectoforohubv1.dominio.topico.ListarTopicosDTO;
import com.sergiobogarin.proyectoforohubv1.dominio.topico.RespuestaTopicoDTO;
import com.sergiobogarin.proyectoforohubv1.dominio.topico.Topico;
import com.sergiobogarin.proyectoforohubv1.dominio.topico.TopicoDTO;
import com.sergiobogarin.proyectoforohubv1.dominio.topico.TopicoRepository;
import com.sergiobogarin.proyectoforohubv1.dominio.topico.TopicoService;
import com.sergiobogarin.proyectoforohubv1.dominio.usuario.UsuarioRepository;
import com.sergiobogarin.proyectoforohubv1.infra.errores.ValidacionDeIntegridad;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@ResponseBody
@RequestMapping("/topico")
@SecurityRequirement(name = "bearer-key")
public class TopicoController {
    @Autowired
    private TopicoRepository topicoRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private TopicoService topicoService;

    @PostMapping("/topico")
    @Transactional
    public ResponseEntity topicoRegistrado(@RequestBody @Valid TopicoDTO topicoDTO) throws ValidacionDeIntegridad {
        var topicoRegistrado = topicoService.topicoCreado(topicoDTO);
        return ResponseEntity.ok(topicoRegistrado);
    }

    @GetMapping("/topicos")
    public ResponseEntity<Page<ListarTopicosDTO>> listarTopicos(@PageableDefault(size = 10) Pageable paged) {
        return ResponseEntity.ok(topicoRepository.findByActiveTrue(paged).map(ListarTopicosDTO::new));
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity topicoActualizado(@RequestBody @Valid TopicoActualizadoDTO topicoActualizadoDTO) {
        Topico topico = topicoRepository.getReferenceById(topicoActualizadoDTO.id());
        topico.topicoActualizado(topicoActualizadoDTO);
        return ResponseEntity.ok(new RespuestaTopicoDTO(topico.getId(),
                topico.getTitle(), topico.getMessage(),
                topico.getStatus(), topico.getAuthor().getId(),
                topico.getCourse(), topico.getDate()));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity eliminarTopico(@PathVariable Long id) {
        Topico topico = topicoRepository.getReferenceById(id);
        topico.diactivateTopic();
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<RespuestaTopicoDTO> respuestaTopico(@PathVariable Long id) {
        Topico topico = topicoRepository.getReferenceById(id);
        var topicoId = new RespuestaTopicoDTO(topico.getId(),
                topico.getTitle(),
                topico.getMessage(),
                topico.getStatus(),
                topico.getAuthor().getId(),
                topico.getCourse(),
                topico.getDate());
        return ResponseEntity.ok(topicoId);
    }
}
