# Challenge Itaú Backend

API REST feita como desafio técnico para a vaga de Backend Júnior do Itaú.

## Sobre o projeto

A ideia é simples: receber transações financeiras e calcular estatísticas em cima delas. 
Tudo em memória, sem banco de dados.

Desenvolvido com Java 17 e Spring Boot.

## Como rodar

```bash
./mvnw spring-boot:run
```

## Endpoints

| Método | Rota | O que faz |
|--------|------|-----------|
| POST | /transacao | Recebe uma transação |
| DELETE | /transacao | Apaga todas as transações |
| GET | /estatistica | Retorna estatísticas dos últimos 60s |

### Exemplo de transação

```json
{
  "valor": 100.50,
  "dataHora": "2026-04-20T10:00:00.000-03:00"
}
```

## Tecnologias

- Java 17
- Spring Boot
- Maven

## Dificuldades

A parte mais chata foi lidar com o `OffsetDateTime` nas validações de data — 
especialmente filtrar as transações dos últimos 60 segundos usando streams. 
As validações também deram trabalho por conta de sintaxe, mas no fim ficou bem simples.

## Contato

Leomario — leomariodev@outlook.com  
LinkedIn: https://www.linkedin.com/in/leomaario/
