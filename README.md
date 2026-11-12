# Lumen Track

**Lumen Track** é uma API REST desenvolvida em Java com Spring Boot para gerenciamento de atividades. O projeto foi criado com foco em boas práticas de desenvolvimento backend, organização em camadas, validação de dados e persistência em banco de dados relacional.

## Tecnologias utilizadas

- Java 21
- Spring Boot 4.1.2
- Spring WebMVC
- Spring Data JPA
- Hibernate
- PostgreSQL
- H2 Database
- Bean Validation
- Springdoc OpenAPI (Swagger)
- Maven

## Funcionalidades

- Cadastrar atividades.
- Listar todas as atividades.
- Buscar atividade por identificador.
- Atualizar atividades.
- Excluir atividades.
- Validar dados de entrada.
- Tratar erros de atividades não encontradas e requisições inválidas.

## Regras de validação

Os dados de uma atividade seguem estas regras:

- Título obrigatório.
- Descrição obrigatória.
- Data limite obrigatória.
- Data limite não pode estar no passado.

## Endpoints

A API utiliza o prefixo `/atividades`.

| Método | Rota | Descrição |
|---|---|---|
| GET | `/atividades` | Lista todas as atividades |
| GET | `/atividades/{id}` | Busca uma atividade por ID |
| POST | `/atividades` | Cadastra uma atividade |
| PUT | `/atividades/{id}` | Atualiza uma atividade |
| DELETE | `/atividades/{id}` | Exclui uma atividade |

### Exemplo de cadastro

**POST** `/atividades`

```json
{
  "titulo": "Organizar documentação",
  "descricao": "Revisar documentos do setor",
  "dataLimite": "2026-12-15"
}
```

## Banco de dados

O projeto possui configurações separadas por ambiente:

- **dev:** PostgreSQL para desenvolvimento.
- **test:** H2 em memória para execução dos testes automatizados.

A senha do PostgreSQL é obtida pela variável de ambiente `DB_PASSWORD`, evitando deixar a credencial diretamente no arquivo de configuração.

## Documentação da API

Com a aplicação em execução, acesse:

[Swagger UI](http://localhost:8081/swagger-ui/index.html)

## Execução local

### Pré-requisitos

- JDK compatível com Java 21.
- PostgreSQL instalado e em execução.
- Banco de dados `lumen_track` criado.
- Maven Wrapper incluído no projeto.

### Configuração

Configure a variável de ambiente `DB_PASSWORD` com a senha do usuário PostgreSQL.

Confira o arquivo `application-dev.properties` e ajuste usuário, senha e endereço do banco conforme seu ambiente.

Ative o perfil `dev` na configuração de execução da aplicação.

### Executar a aplicação

No terminal, dentro da pasta do projeto:

```powershell
.\mvnw.cmd spring-boot:run
```

Se preferir, execute a classe principal pela sua IDE com o perfil `dev` ativo.

### Executar os testes

```powershell
.\mvnw.cmd clean test
```

Os testes utilizam o perfil `test` e o banco H2 em memória.

## Estrutura do projeto

A aplicação está organizada em camadas e pacotes, incluindo:

- `controller`: endpoints HTTP.
- `service`: regras e operações da aplicação.
- `repository`: acesso aos dados.
- `model`: entidades.
- `dto`: objetos de transferência de dados.
- `enums`: enumerações.
- `exception`: tratamento de exceções.

## Objetivo

Projeto de portfólio desenvolvido para demonstrar conhecimentos em desenvolvimento backend com Java, Spring Boot, APIs REST, persistência de dados, validação e testes automatizados.