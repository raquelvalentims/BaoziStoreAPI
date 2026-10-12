# Baozi Store API

API REST acadêmica para cadastro de clientes, produtos e pedidos, desenvolvida com Java 21, Spring Boot 3.4.5, Spring Data JPA, Bean Validation e banco relacional H2.

> **Antes da entrega:** substitua `Raquel Valentim - RU PREENCHER` na requisição de cadastro de cliente pelo nome completo do estudante seguido do RU real. Atualize também a identificação e os dados do repositório no relatório acadêmico.

## Requisitos

- JDK 21 (ou JDK 17+, ajustando a propriedade `java.version` no `pom.xml` se necessário).
- Apache Maven 3.9 ou compatível.
- Postman (para executar a coleção e capturar as telas exigidas pela atividade).

## Executar localmente

No diretório deste projeto:

```bash
mvn spring-boot:run
```

A API fica disponível em `http://localhost:8080`. O H2 usa um arquivo local `baozi-store.mv.db`, criado durante a execução. O console de desenvolvimento fica em `http://localhost:8080/h2-console`, com JDBC URL `jdbc:h2:file:./baozi-store;AUTO_SERVER=TRUE;DB_CLOSE_ON_EXIT=FALSE`, usuário `sa` e senha vazia. Não exponha o console de H2 em um ambiente público.

## Endpoints

| Entidade | Método e rota | Função |
|---|---|---|
| Cliente | `POST /api/clientes` | Cadastrar cliente |
| Cliente | `GET /api/clientes` | Listar clientes |
| Cliente | `GET /api/clientes/{id}` | Consultar cliente por ID |
| Cliente | `PUT /api/clientes/{id}` | Atualizar cliente |
| Cliente | `DELETE /api/clientes/{id}` | Excluir cliente |
| Produto | `POST /api/produtos` | Cadastrar produto |
| Produto | `GET /api/produtos` | Listar produtos |
| Produto | `GET /api/produtos/{id}` | Consultar produto por ID |
| Produto | `PUT /api/produtos/{id}` | Atualizar produto |
| Produto | `DELETE /api/produtos/{id}` | Excluir produto |
| Pedido | `POST /api/pedidos` | Registrar pedido |
| Pedido | `GET /api/pedidos` | Listar pedidos |
| Pedido | `GET /api/pedidos/{id}` | Consultar pedido por ID |
| Pedido | `PUT /api/pedidos/{id}` | Atualizar pedido |
| Pedido | `DELETE /api/pedidos/{id}` | Excluir pedido |

Os endpoints recebem e retornam JSON. `Pedido` usa relacionamento JPA com `Cliente` e `Produto`, mas os DTOs da API recebem/devolvem os campos `clienteId` e `produtoId`.

## Exemplos de JSON

### Criar cliente

```json
{
  "nome": "Raquel Valentim - RU PREENCHER",
  "clienteDesde": "2026-10-12"
}
```

### Criar produto

```json
{
  "nome": "Baozi Tradicional",
  "preco": 8.50,
  "estoque": true
}
```

### Criar pedido

```json
{
  "clienteId": 1,
  "produtoId": 1,
  "quantidade": 3
}
```

Os IDs do exemplo de pedido devem corresponder aos retornados pelos POSTs realizados antes.

## Teste com Postman

1. Inicie a aplicação com `mvn spring-boot:run`.
2. Importe `postman/BaoziStore.postman_collection.json` e `postman/BaoziStore.local.postman_environment.json` no Postman; selecione o ambiente **Baozi Store - Local**.
3. Atualize o RU no corpo do primeiro POST da coleção.
4. Execute a coleção na ordem apresentada. As três requisições POST salvam automaticamente os IDs retornados em variáveis do ambiente.
5. Para a atividade acadêmica, execute cada requisição individualmente e tire capturas reais do Postman mostrando método, URL, corpo enviado quando houver, código HTTP e corpo da resposta. Faça os prints exigidos **antes** das três requisições DELETE, pois elas removem os registros do cenário.
6. Anexe as capturas reais ao PDF. A coleção é apenas um facilitador e não substitui as evidências de tela solicitadas pela professora.

### Capturas obrigatórias sugeridas

- POST de Cliente, Produto e Pedido;
- GET geral de Cliente, Produto e Pedido;
- GET por ID de Cliente, Produto e Pedido;
- DELETE de Pedido, Cliente e Produto, nesta ordem para respeitar as referências do banco.

A API também implementa PUT para as três entidades. A coleção contém essas requisições como teste complementar.

## Organização MVC

- `model`: entidades JPA (`Cliente`, `Produto`, `Pedido`);
- `repository`: interfaces Spring Data JPA;
- `controller`: endpoints REST;
- `dto`: contratos de entrada e resposta para a API;
- `src/main/resources/application.properties`: configuração do servidor e banco H2.
