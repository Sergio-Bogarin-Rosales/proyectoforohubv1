package com.sergiobogarin.proyectoforohubv1.dominio.topico;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TopicoRepository extends JpaRepository<Topico, Long> {
    boolean existsByTitleIgnoreCase(String title);
    boolean existsByMessageIgnoreCase(String message);
    Page<Topico> findByActiveTrue(Pageable paged);

}
