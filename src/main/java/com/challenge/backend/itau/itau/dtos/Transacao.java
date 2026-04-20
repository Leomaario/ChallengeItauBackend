package com.challenge.backend.itau.itau.dtos;
import java.time.OffsetDateTime;



public record Transacao(OffsetDateTime dataHora, double valor) {
}
