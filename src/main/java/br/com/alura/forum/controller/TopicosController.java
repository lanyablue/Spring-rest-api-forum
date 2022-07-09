package br.com.alura.forum.controller;

import br.com.alura.forum.controller.DTO.TopicoDTO;
import br.com.alura.forum.modelo.Curso;
import br.com.alura.forum.modelo.Topico;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;


@RestController
public class TopicosController {

    @RequestMapping("/topicos")
    public List<TopicoDTO> lista() {
        Topico topico = new Topico("Dúvida",
                                   "Dúvida com spring",
                                                     new Curso("Spring",
                                                               "Programação"));

        return TopicoDTO.converter(Arrays.asList(topico, topico, topico));
    }



}
