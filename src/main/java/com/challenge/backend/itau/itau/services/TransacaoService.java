package com.challenge.backend.itau.itau.services;

import com.challenge.backend.itau.itau.dtos.Estatistica;
import com.challenge.backend.itau.itau.dtos.Transacao;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.DoubleSummaryStatistics;
import java.util.List;

@Service
public class TransacaoService {

    List<Transacao> transacoes = new ArrayList<>();

    public boolean salvarTransacoes(Transacao transacao){
        if (transacao.valor() < 0) {
            return false;
        }
        if (transacao.dataHora() == null){
            return false;
        }
        if (transacao.dataHora().isAfter(OffsetDateTime.now())){
            return false;
        }
        transacoes.add(transacao);
        System.out.println(transacoes);
        return true;
    }

   public void deletarTransacao(){
        transacoes.clear();
   }
    public Estatistica calcularEstatistica(){
        OffsetDateTime sessenta = OffsetDateTime.now().minusSeconds(60);
        DoubleSummaryStatistics stats = transacoes.stream()
                .filter(t -> t.dataHora().isAfter(sessenta))
                .mapToDouble(t -> t.valor())
                .summaryStatistics();

        if (stats.getCount() == 0){
            return new Estatistica(0, 0.0, 0.0, 0.0, 0.0);
        }
        return new Estatistica(stats.getCount(), stats.getSum(), stats.getAverage(), stats.getMin(), stats.getMax());
    }

}
