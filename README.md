```markdown
# Order Application

Esta é uma aplicação Spring Boot para gerenciar pedidos, utilizando PostgreSQL como banco de dados e Kafka para mensageria.

## Pré-requisitos

- Docker e Docker Compose
- Java 11 ou superior
- Maven

## Configuração

1. Clone o repositório:
   ```sh
   git clone <URL_DO_REPOSITORIO>
   cd <NOME_DO_REPOSITORIO>
   ```

2. Configure o banco de dados e Kafka utilizando Docker Compose:
   ```sh
   docker-compose up -d
   ```

3. Verifique se os serviços estão rodando:
   ```sh
   docker ps
   ```

## Executando a Aplicação

1. Compile e empacote a aplicação:
   ```sh
   mvn clean package
   ```

2. Execute a aplicação:
   ```sh
   java -jar target/<NOME_DO_ARQUIVO>.jar
   ```

## Populando o Banco de Dados

1. Conecte-se ao banco de dados PostgreSQL:
   ```sh
   psql -h localhost -U postgres -d order_db
   ```

2. Execute o script SQL para popular o banco de dados:
   ```sh
   \i src/main/resources/sql/insert-products.sql
   ```

## Acessando a Aplicação

A aplicação estará disponível em `http://localhost:8080`.

## Exemplos de Pedidos 

Exemplos de ordens estão disponíveis em src/main/resources/broker/kafka-orders.json


## Configurações Adicionais

As configurações da aplicação podem ser encontradas no arquivo `src/main/resources/application.properties`.

## Licença

Este projeto está licenciado sob os termos da licença MIT.
```