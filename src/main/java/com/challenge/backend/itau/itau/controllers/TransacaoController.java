package com.challenge.backend.itau.itau.controllers;

import com.challenge.backend.itau.itau.dtos.Transacao;
import com.challenge.backend.itau.itau.services.TransacaoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class TransacaoController {
    //Injetando a classe transacaoService no controller atraves de um construtor
    private final TransacaoService transacaoService;
    //construtor - injetando o services e referenciando o transicaoService do proprio contrutor
    public TransacaoController(TransacaoService transacaoService) {
        this.transacaoService = transacaoService;
    }


    @PostMapping("/transacao") //usando metodo post e mapeando a rota da transação pedido no readme
    ResponseEntity transacao(@RequestBody Transacao transacao){
        //acessando o metodo salvarTransacoes do service e passando o parametro transacao para salvar os atributos do tipo transacao
        boolean transacaoFeita = transacaoService.salvarTransacoes(transacao);
        if (transacaoFeita){
            return ResponseEntity.status(201).build();
        } else {
            return ResponseEntity.status(422).build();
        }
    }

    @DeleteMapping("/transacao")
    ResponseEntity deletartransacao(){
        transacaoService.deletarTransacao();
        return ResponseEntity.ok().build();
    }

    @GetMapping("/estatistica")
    ResponseEntity mostrarEstatisticas(){
        return ResponseEntity.ok(transacaoService.calcularEstatistica());
    }
}
