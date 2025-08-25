# VidaPlus API (SGHSS) — Java/Spring Boot + PostgreSQL

API acadêmica para gerenciamento de **Pacientes**, **Profissionais** e **Agendamentos**, com **JWT**, **validações**, **auditoria** e **documentação Swagger**.

## Tecnologias
- Java 17, Spring Boot 3.3
- Spring Web, Data JPA, Validation, Security
- PostgreSQL
- JWT (jjwt), BCrypt
- Auditoria por filtro HTTP
- Swagger/OpenAPI (springdoc)
- (Opcional) Flyway para migrações/seed

## Requisitos
- Java 17
- PostgreSQL 17+
- Maven (ou `mvnw` do projeto)

## Banco de dados
Crie o banco e usuário:
```sql
CREATE DATABASE vida_plus;
CREATE USER vida_user WITH PASSWORD 'vida_pass';
GRANT ALL PRIVILEGES ON DATABASE vida_plus TO vida_user;
