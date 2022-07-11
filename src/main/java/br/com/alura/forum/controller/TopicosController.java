package br.com.alura.forum.controller;

import br.com.alura.forum.controller.DTO.DetalhesDoTopicoDTO;
import br.com.alura.forum.controller.DTO.TopicoDTO;
import br.com.alura.forum.controller.form.AtualizacaoTopicoForm;
import br.com.alura.forum.controller.form.TopicoForm;
import br.com.alura.forum.modelo.Curso;
import br.com.alura.forum.modelo.Topico;
import br.com.alura.forum.repository.CursoRepository;
import br.com.alura.forum.repository.TopicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/topicos")
public class TopicosController {

    @Autowired     // injeção de dependencias
    private TopicoRepository topicoRepository;
    @Autowired     // injeção de dependencias
    private CursoRepository cursoRepository;

    @GetMapping    // VERBO GET
    public List<TopicoDTO> lista(String nomeCurso) {
        if (nomeCurso == null){
            List<Topico> topicos = topicoRepository.findAll();
            return TopicoDTO.converter(topicos);
        } else {
            List<Topico> topicos = topicoRepository.findByCurso_Nome(nomeCurso);
            return TopicoDTO.converter(topicos);
        }
    }

    @PostMapping    // VERBO POST
    @Transactional
    public ResponseEntity<TopicoDTO> cadastrar(@RequestBody @Valid TopicoForm form,
                                               UriComponentsBuilder uriBuilder) { // este parametro esta no corpo da requisição
        Topico topico = form.converter(cursoRepository);  // chamo o converter passando as informações que o form nao consegue recuperar
        topicoRepository.save(topico);

        URI uri = uriBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
        return ResponseEntity.created(uri).body(new TopicoDTO(topico));
    }

    @GetMapping("/{id}")  //id dinamico
    public ResponseEntity<DetalhesDoTopicoDTO> detalhar(@PathVariable Long id) {
        Optional<Topico> topico = topicoRepository.findById(id);

        if (topico.isPresent()) {
            return ResponseEntity.ok(new DetalhesDoTopicoDTO(topico.get()));
        }

        return ResponseEntity.notFound().build();

    }

    @PutMapping("/{id}")
    @Transactional   // dispara o commit no banco de dados
    public ResponseEntity<TopicoDTO> atualizar(@PathVariable Long id,
                                               @RequestBody @Valid AtualizacaoTopicoForm form) {
        Optional<Topico> optional = topicoRepository.findById(id);

        if (optional.isPresent()) {
            Topico topico = form.atualizar(id, topicoRepository);
            return ResponseEntity.ok(new TopicoDTO(topico));
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> remover(@PathVariable Long id) {
        Optional<Topico> optional = topicoRepository.findById(id);
        if (optional.isPresent()) {
            topicoRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.notFound().build();

    }



}
