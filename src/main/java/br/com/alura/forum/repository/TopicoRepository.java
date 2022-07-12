package br.com.alura.forum.repository;

import br.com.alura.forum.modelo.Topico;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TopicoRepository extends JpaRepository<Topico, Long> {

    Page<Topico> findByCurso_Nome(String nomeCurso, Pageable paginacao); //assinatura, filtrada pelo relacionamento dos atributos, o underline
                                                                           // indica que será de um relacionamento

    /*@Query("SELECT t from Topico t WHERE t.curso.nome = :nomeCurso")   query manual
    List<Topico> carregarPorNomeDoCurso(@Param("nomeCurso") String nomeCurso); */


}
