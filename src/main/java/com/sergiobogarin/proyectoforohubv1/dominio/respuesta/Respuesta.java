package com.sergiobogarin.proyectoforohubv1.dominio.respuesta;

import com.sergiobogarin.proyectoforohubv1.dominio.topico.Topico;
import com.sergiobogarin.proyectoforohubv1.dominio.usuario.Usuario;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "Respuesta")
@Table(name = "respuestas")
public class Respuesta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime creationDate;
    private String solution;
    @OneToOne
    @JoinColumn(name = "author", referencedColumnName = "id")
    private Usuario author;
    @OneToOne
    @JoinColumn(name = "topico", referencedColumnName = "id")
    private Topico topico;
    private boolean active;

    public Respuesta(Long id, String solution, Usuario usuario, Topico topico, LocalDateTime creationDate) {
        this.id = id;
        this.solution = solution;
        this.author = usuario;
        this.topico = topico;
        this.creationDate = LocalDateTime.now();
    }

    public void respuestaActualizada(RespuestaActualizadaDTO respuestaActualizadaDTO) {
        if (respuestaActualizadaDTO.solution() != null) {
            this.solution = respuestaActualizadaDTO.solution();
        }
    }

    public void diactivateResponse() {

        this.active = false;
    }
}
