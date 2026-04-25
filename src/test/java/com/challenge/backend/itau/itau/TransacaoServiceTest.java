package com.challenge.backend.itau.itau;

import com.challenge.backend.itau.itau.dtos.Estatistica;
import com.challenge.backend.itau.itau.dtos.Transacao;
import com.challenge.backend.itau.itau.services.TransacaoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class TransacaoServiceTest {

    TransacaoService service = new TransacaoService();
    @BeforeEach
    void setUp() {
        service.deletarTransacao();
    }


    @Test
    void deveRejeitarTransacaoComValorNegativo() {
        Transacao transacao = new Transacao(OffsetDateTime.now().minusHours(1), -10.0);
        boolean resultado = service.salvarTransacoes(transacao);
        assertFalse(resultado, "Valor negativo deve ser rejeitado");
    }

    @Test
    void deveRejeitarTransacaoComValorZero() {
        Transacao transacao = new Transacao(OffsetDateTime.now().minusHours(1), 0.0);
        boolean resultado = service.salvarTransacoes(transacao);
        assertFalse(resultado, "Valor zero deve ser rejeitado");
    }

    @Test
    void deveRejeitarTransacaoComDataHoraFutura() {
        Transacao transacao = new Transacao(OffsetDateTime.now().plusHours(1), 100.0);
        boolean resultado = service.salvarTransacoes(transacao);
        assertFalse(resultado, "Data futura deve ser rejeitada");
    }

    @Test
    void deveRejeitarTransacaoComDataHoraNula() {
        Transacao transacao = new Transacao(null, 100.0);
        boolean resultado = service.salvarTransacoes(transacao);
        assertFalse(resultado, "DataHora nula deve ser rejeitada");
    }

    @Test
    void deveAceitarTransacaoValida() {
        Transacao transacao = new Transacao(OffsetDateTime.now().minusSeconds(30), 250.0);

        boolean resultado = service.salvarTransacoes(transacao);
        assertTrue(resultado, "Transação válida deve ser aceita");
    }


    @Test
    void deveRetornarEstatisticaZeradaSemTransacoes() {
        Estatistica estatistica = service.calcularEstatistica();

        assertEquals(0, estatistica.count());
        assertEquals(0.0, estatistica.sum());
        assertEquals(0.0, estatistica.avg());
        assertEquals(0.0, estatistica.min());
        assertEquals(0.0, estatistica.max());
    }

    @Test
    void deveCalcularEstatisticaCorretamente() {
        service.salvarTransacoes(new Transacao(OffsetDateTime.now().minusSeconds(10), 100.0));
        service.salvarTransacoes(new Transacao(OffsetDateTime.now().minusSeconds(20), 200.0));
        service.salvarTransacoes(new Transacao(OffsetDateTime.now().minusSeconds(30), 300.0));
        Estatistica estatistica = service.calcularEstatistica();
        assertEquals(3,     estatistica.count());
        assertEquals(600.0, estatistica.sum());
        assertEquals(200.0, estatistica.avg());
        assertEquals(100.0, estatistica.min());
        assertEquals(300.0, estatistica.max());
    }

    @Test
    void deveIgnorarTransacoesForaDaJanelaDe60Segundos() {
        service.salvarTransacoes(new Transacao(OffsetDateTime.now().minusSeconds(10), 50.0));  // dentro
        service.salvarTransacoes(new Transacao(OffsetDateTime.now().minusSeconds(90), 500.0)); // fora — não deve entrar
        Estatistica estatistica = service.calcularEstatistica();
        assertEquals(1,    estatistica.count());
        assertEquals(50.0, estatistica.sum());
    }


    @Test
    void deveLimparTodasAsTransacoes() {
        service.salvarTransacoes(new Transacao(OffsetDateTime.now().minusSeconds(5), 99.0));
        service.deletarTransacao();
        Estatistica estatistica = service.calcularEstatistica();
        assertEquals(0, estatistica.count());
    }
}