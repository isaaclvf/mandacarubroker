# MandaCaru Broker API

## Descrição
A Mandacaru Broker API é uma aplicação Spring Boot que fornece operações CRUD (Create, Read, Update, Delete) para gerenciar informações sobre ações (stocks). Um home broker é uma plataforma que permite aos investidores negociar ativos financeiros, como ações, diretamente pela internet. O termo "home" se refere ao fato de que as operações podem ser realizadas a partir da casa do investidor, de forma online, sem a necessidade de intermediários físicos, como corretores de valores. A Mandacaru Broker API possibilita o desenvolvimento de um home broker, permitindo o gerenciamento de ações e outras funcionalidades relacionadas à negociação de ativos financeiros.


## Recursos

### Listar Todas as Ações
Retorna uma lista de todas as ações disponíveis. Se o banco de dados estiver vazio, o endpoint retornará uma lista vazia.

**Endpoint:**
```http
GET /stocks
```

**Exemplo de Resposta (quando há ações cadastradas):**
```json
[
  {
    "id": "b1fa6562-b13e-41b4-b7ff-2eb59a1e1b67",
    "symbol": "ABC0",
    "companyName": "ABC Company",
    "price": -123.45
  },
  {
    "id": "f60067a7-76bc-4b4d-9155-538a8edfbab7",
    "symbol": "DEF1",
    "companyName": "DEF Company",
    "price": -67
  }
]
```

**Exemplo de Resposta (quando o banco de dados está vazio):**
```json
[]
```

### Obter uma Ação por ID

Retorna os detalhes de uma ação específica com base no ID. Se a ação com o ID especificado não for encontrada, o endpoint retornará uma resposta com status 404 (Not Found).

**Endpoint:**
```http
GET /stocks/{id}
```

**Exemplo de Resposta (quando a ação é encontrada):**
```json
{
  "id": "b1fa6562-b13e-41b4-b7ff-2eb59a1e1b67",
  "symbol": "ABC0",
  "companyName": "ABC Company",
  "price": -123.45
}
```

### Criar uma Nova Ação
Cria uma nova ação com base nos dados fornecidos. Se a ação for criada com sucesso, o endpoint retornará um código de status 201 (Created) e os detalhes da ação criada.

**Endpoint:**
```http
POST /stocks
```
**Corpo da Solicitação (Request Body):**
- `symbol` (string, obrigatório): O símbolo da ação. Deve ser composto três letras seguidas de um dígito.
- `companyName` (string, obrigatório): O nome da empresa associada à ação. 
- `price` (number, obrigatório): O preço da ação. Deve ser positivo.

**Exemplo de Requisição**
```JSON
{
  "symbol": "BBA3",
  "companyName": "Banco do Brasil SA",
  "price": 56.97
}

```
**Códigos de Resposta:**
- 201 Created: Retornado quando a ação é criada com sucesso. 
- 400 Bad Request: Retornado quando há um erro na requisição, como um campo obrigatório ausente ou um formato inválido.

### Atualizar uma Ação por ID
Atualiza os detalhes de uma ação específica com base no ID. Se a ação com o ID especificado não for encontrada, o endpoint retornará uma resposta com status 404 (Not Found). Em caso de sucesso, o endpoint retornará um código de status 200 (OK) e os detalhes da ação atualizada.


**Endpoint:**
```http
PUT /stocks/{id}
```
**Corpo da Solicitação (Request Body):**
- `symbol` (string, obrigatório): O símbolo da ação. Deve ser composto três letras seguidas de um dígito.
- `companyName` (string, obrigatório): O nome da empresa associada à ação.
- `price` (number, obrigatório): O preço da ação. Deve ser positivo.

**Exemplo de Requisição:**
```JSON
{
  "symbol": "BBAS3",
  "companyName": "Banco do Brasil SA",
  "price": 59.97
}

```

**Exemplo de Resposta (quando a ação é atualizada com sucesso):**
```json
{
    "id": 1,
    "symbol": "BBAS3",
    "companyName": "Banco do Brasil SA",
    "price": 59.97
}
```
**Códigos de Resposta:**
- 200 OK: Retornado quando a ação é atualizada com sucesso.
- 404 Not Found: Retornado quando a ação com o ID especificado não é encontrada.


### Excluir uma Ação por ID
Exclui uma ação específica com base no ID. Se a ação com o ID especificado não for encontrada, o endpoint retornará uma resposta com status 404 (Not Found). Em caso de sucesso, o endpoint retornará um código de status 204 (No Content).

**Endpoint:**
```http
DELETE /stocks/{id}
```

**Exemplo de Resposta (quando a ação é excluída com sucesso):**
```http
HTTP/1.1 204 No Content
```

**Códigos de Resposta:**
- 204 No Content: Retornado quando a ação é excluída com sucesso.
- 404 Not Found: Retornado quando a ação com o ID especificado não é encontrada.


## Uso
1. Clone o repositório: `git clone https://github.com/seu-usuario/MandaCaruBrokerAPI.git`
2. Importe o projeto em sua IDE preferida.
3. Configure o banco de dados e as propriedades de aplicação conforme necessário.
4. Execute o aplicativo Spring Boot.
5. Acesse a API em `http://localhost:8080`.

## Requisitos
- Java 11 ou superior
- Maven
- Banco de dados

## Tecnologias Utilizadas
- Spring Boot
- Spring Data JPA
- Maven
- PostgreSQL

## Contribuições
Contribuições são bem-vindas!

## Licença
Este projeto está licenciado sob a [Licença MIT](LICENSE).

