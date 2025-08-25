classDiagram
  class Paciente {
    +Long id
    +String nome
    +String cpf
    +LocalDate dataNascimento
    +String telefone
    +String email
    +LocalDateTime criadoEm
    +LocalDateTime atualizadoEm
  }

  class Profissional {
    +Long id
    +String nome
    +String cpf
    +String conselho
    +String especialidade
    +String telefone
    +String email
    +LocalDateTime criadoEm
    +LocalDateTime atualizadoEm
  }

  class Agendamento {
    +Long id
    +Paciente paciente
    +Profissional profissional
    +LocalDateTime inicio
    +LocalDateTime fim
    +StatusAgendamento status
    +LocalDateTime criadoEm
    +LocalDateTime atualizadoEm
  }

  class StatusAgendamento {
  }

  class Usuario {
    +Long id
    +String nome
    +String email
    +String senhaHash
    +Role role
    +LocalDateTime criadoEm
    +LocalDateTime atualizadoEm
  }

  class Role {
  }

  class Auditoria {
    +Long id
    +String metodo
    +String rota
    +int status
    +String usuario
    +String ip
    +LocalDateTime dataHora
  }

  Paciente "1" --> "0..*" Agendamento : possui
  Profissional "1" --> "0..*" Agendamento : realiza
  Agendamento --> StatusAgendamento
  Usuario --> Role
