
---

# 🗂️ 2) DER — `docs/DER.md`

```markdown
# DER — VidaPlus

```mermaid
erDiagram
  PACIENTE {
    bigint id PK
    varchar nome
    char(11) cpf UK
    date data_nascimento
    varchar telefone
    varchar email
    timestamp criado_em
    timestamp atualizado_em
  }

  PROFISSIONAL {
    bigint id PK
    varchar nome
    char(11) cpf UK
    varchar conselho
    varchar especialidade
    varchar telefone
    varchar email
    timestamp criado_em
    timestamp atualizado_em
  }

  AGENDAMENTO {
    bigint id PK
    bigint paciente_id FK
    bigint profissional_id FK
    timestamp inicio
    timestamp fim
    varchar status
    timestamp criado_em
    timestamp atualizado_em
  }

  USUARIO {
    bigint id PK
    varchar nome
    varchar email UK
    varchar senha_hash
    varchar role
    timestamp criado_em
    timestamp atualizado_em
  }

  AUDITORIA {
    bigint id PK
    varchar metodo
    varchar rota
    int status
    varchar usuario
    varchar ip
    timestamp data_hora
  }

  PACIENTE ||--o{ AGENDAMENTO : "tem"
  PROFISSIONAL ||--o{ AGENDAMENTO : "atende"
