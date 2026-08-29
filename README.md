# API de Destinos de Viagem - Agência de Viagens

Primeira versão de uma API RESTful para gerenciamento de destinos de viagem, desenvolvida como desafio prático da disciplina de Desenvolvimento de Sistemas Web (SENAI/SC - Análise e Desenvolvimento de Sistemas).

## 1. Visão geral do problema

A agência de viagens deseja modernizar seus serviços digitais expondo uma API REST que permita a integração com aplicativos de turismo, parceiros comerciais e futuras plataformas digitais. Esta primeira entrega tem como foco a definição da arquitetura da solução e a construção dos endpoints principais para o gerenciamento de destinos de viagem.

A API permite: cadastrar, listar, pesquisar por nome ou por localização, detalhar, atualizar, avaliar e excluir destinos.

## 2. Arquitetura proposta

O projeto segue uma arquitetura em camadas, dentro de uma única aplicação Spring Boot.

**Responsabilidade de cada camada:**

- **Controller**: único ponto de contato com o mundo externo via HTTP. Não contém regra de negócio, apenas recebe a requisição, delega para o `Service` e devolve a resposta com o status HTTP adequado.
- **Service**: concentra toda a lógica de negócio da aplicação, incluindo a regra de recálculo da média de avaliação de um destino.
- **Repository**: responsável por armazenar e recuperar os destinos. Nesta versão, os dados são mantidos em memória, sem uso de banco de dados.
- **Entity**: representa a estrutura de dados de um destino de viagem.

Essa separação garante que, se o projeto evoluir para uma persistência real (ex: banco de dados relacional via Spring Data JPA), apenas a camada `Repository` precisaria ser substituída, `Controller` e `Service` permaneceriam praticamente inalterados.

## 3. Justificativa de linguagem, framework e tecnologias

- **Java 21**: escolhido por ser uma LTS (Long-Term Support) com suporte estendido, o que a torna a versão recomendada para projetos novos no mercado atualmente. Além disso, já é a linguagem adotada no restante do curso, o que mantém consistência com o que vem sendo estudado.
- **Spring Boot 4**: framework escolhido por reduzir a complexidade de configuração de uma API REST (servidor embutido, injeção de dependência automática, serialização JSON), permitindo focar na modelagem do problema em vez de configuração de infraestrutura. É também o framework mais utilizado no mercado brasileiro para APIs Java, o que aproxima o projeto de um cenário profissional real.
- **Spring Web**: única dependência necessária nesta etapa, já que não há persistência em banco nem mecanismos de segurança no escopo definido.
- **Armazenamento em memória**: optou-se por uma `List<Destination>` gerenciada por um `@Component` (`DestinationRepository`), em vez de um banco de dados real, conforme definido no desafio. Essa camada foi isolada especificamente para que uma futura troca por persistência real (JPA/banco) exija o mínimo de mudança nas demais camadas.

## 4. Endpoints da API

Base URL local: `http://localhost:8080`

---

### 4.1. Cadastrar destino

`POST /destinations`

**Exemplo de requisição:**

```http
POST /destinations
Content-Type: application/json

{
  "id": 1,
  "name": "Foz do Iguaçu",
  "location": "Paraná, Brasil",
  "description": "Cataratas do Iguaçu e Parque Nacional"
}
```

**Exemplo de resposta - `201 Created`:**

```json
{
  "id": 1,
  "name": "Foz do Iguaçu",
  "location": "Paraná, Brasil",
  "description": "Cataratas do Iguaçu e Parque Nacional",
  "ratings": [],
  "rating": 0.0
}
```
---

### 4.2. Listar todos os destinos

`GET /destinations`

**Exemplo de requisição:**

```http
GET /destinations
```

**Exemplo de resposta - `200 OK`:**

```json
[
  {
    "id": 1,
    "name": "Foz do Iguaçu",
    "location": "Paraná, Brasil",
    "description": "Cataratas do Iguaçu e Parque Nacional",
    "ratings": [5, 4],
    "rating": 4.5
  },
  {
    "id": 2,
    "name": "Bonito",
    "location": "Mato Grosso do Sul, Brasil",
    "description": "Ecoturismo e rios de águas cristalinas",
    "ratings": [],
    "rating": 0.0
  }
]
```

---

### 4.3. Pesquisar destino por nome

`GET /destinations/search/name?name={termo}`

**Exemplo de requisição:**

```http
GET /destinations/search/name?name=foz
```

**Exemplo de resposta - `200 OK`:**

```json
[
  {
    "id": 1,
    "name": "Foz do Iguaçu",
    "location": "Paraná, Brasil",
    "description": "Cataratas do Iguaçu e Parque Nacional",
    "ratings": [5, 4],
    "rating": 4.5
  }
]
```

---

### 4.4. Pesquisar destino por localização

`GET /destinations/search/location?location={termo}`

**Exemplo de requisição:**

```http
GET /destinations/search/location?location=paraná
```

**Exemplo de resposta - `200 OK`:**

```json
[
  {
    "id": 1,
    "name": "Foz do Iguaçu",
    "location": "Paraná, Brasil",
    "description": "Cataratas do Iguaçu e Parque Nacional",
    "ratings": [5, 4],
    "rating": 4.5
  }
]
```

---

### 4.4. Detalhar um destino específico

`GET /destinations/{id}`

**Exemplo de requisição:**

```http
GET /destinations/1
```

**Exemplo de resposta - `200 OK`:**

```json
{
  "id": 1,
  "name": "Foz do Iguaçu",
  "location": "Paraná, Brasil",
  "description": "Cataratas do Iguaçu e Parque Nacional",
  "ratings": [5, 4],
  "rating": 4.5
}
```

---

### 4.6. Atualizar informações de um destino

`PUT /destinations/{id}`

**Exemplo de requisição:**

```http
PUT /destinations/1
Content-Type: application/json

{
  "name": "Foz do Iguaçu",
  "location": "Paraná, Brasil",
  "description": "Cataratas do Iguaçu, Parque Nacional e Marco das Três Fronteiras"
}
```

**Exemplo de resposta - `200 OK`:**

```json
{
  "id": 1,
  "name": "Foz do Iguaçu",
  "location": "Paraná, Brasil",
  "description": "Cataratas do Iguaçu, Parque Nacional e Marco das Três Fronteiras",
  "ratings": [5, 4],
  "rating": 4.5
}
```

---

### 4.7. Registrar avaliação de um destino

`POST /destinations/{id}/ratings`

**Exemplo de requisição:**

```http
POST /destinations/1/ratings
Content-Type: application/json

{
  "rating": 5
}
```

**Exemplo de resposta - `200 OK`:**

```json
{
  "id": 1,
  "name": "Foz do Iguaçu",
  "location": "Paraná, Brasil",
  "description": "Cataratas do Iguaçu e Parque Nacional",
  "ratings": [5, 4, 5],
  "rating": 4.67
}
```

---

### 4.8. Excluir um destino

`DELETE /destinations/{id}`

**Exemplo de requisição:**

```http
DELETE /destinations/1
```

**Exemplo de resposta - `204 No Content`**

_(sem corpo de resposta)_

---

## 6. Resumo dos endpoints

| Método | Endpoint| Ação|
| ------ | ------- | --- |
| POST   | `/destinations` | Cadastrar destino |
| GET    | `/destinations` | Listar todos os destinos |
| GET    | `/destinations/search/name?name=` | Pesquisar por nome |
| GET    | `/destinations/search/location?location=` | Pesquisar por localização | 
| GET    | `/destinations/{id}` | Detalhar destino |
| PUT    | `/destinations/{id}` | Atualizar destino | 
| POST   | `/destinations/{id}/ratings` | Registrar avaliação |
| DELETE | `/destinations/{id}` | Excluir destino |

## 7. Instruções de execução

### Pré-requisitos

- Java 21 instalado (`java -version` para conferir)
- Maven instalado (`mvn -version` para conferir)

### Passos

```bash
# 1. Clonar o repositório
git clone <url-do-repositorio>
cd <pasta-do-projeto>

# 2. Executar a aplicação
mvn spring-boot:run
```

A API sobe por padrão em `http://localhost:8080`.

### Testando a API

Recomenda-se o uso do Postman ou Insomnia para testar os endpoints acima. Basta importar as requisições de exemplo desta documentação (seção 4) apontando para `http://localhost:8080`.

Nenhuma configuração adicional (variáveis de ambiente, banco de dados, chaves de API) é necessária - os dados são armazenados apenas em memória durante a execução da aplicação e são perdidos ao reiniciá-la.