package br.com.alura.forum.controller;

import br.com.alura.forum.controller.DTO.TopicoDTO;
import br.com.alura.forum.controller.form.TopicoForm;
import br.com.alura.forum.modelo.Curso;
import br.com.alura.forum.modelo.Topico;
import br.com.alura.forum.repository.CursoRepository;
import br.com.alura.forum.repository.TopicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;


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
    public void cadastrar(@RequestBody TopicoForm form) { // este parametro esta no corpo da requisição
        Topico topico = form.converter(cursoRepository);  // chamo o converter passando as informações que o form nao consegue recuperar
        topicoRepository.save(topico);
    }



}
