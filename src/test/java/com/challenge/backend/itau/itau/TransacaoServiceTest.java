package com.challenge.backend.itau.itau;

import com.challenge.backend.itau.itau.dtos.Transacao;
import com.challenge.backend.itau.itau.services.TransacaoService;
import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;

public class TransacaoServiceTest {
pub
    TransacaoService service = new TransacaoService();

    @Test
    void devRejeitarTransacoesComValorNegativo(){
        Transacao transacao = new Transacao(OffsetDateTime.now().minusHours(1), -10.0);
        service.salvarTransacoes(transacao);
    }


}
