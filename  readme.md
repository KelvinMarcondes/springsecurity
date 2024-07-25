# Projeto Spring Security 6 com JWT, OAuth 2 e Docker Compose

Este projeto demonstra a implementação de autenticação e autorização usando Spring Security 6, 
JWT e OAuth 2, com um banco de dados MySQL configurado via Docker Compose.

## Tecnologias Utilizadas
- **Spring Boot 3**
- **Spring Security 6**
- **JWT (JSON Web Token)**
- **OAuth 2**
- **Docker Compose**
- **MySQL**

## Pré-requisitos
- **Java 21** ou superior
- **Maven**
- **Docker** e **Docker Compose**


## Configuração do Projeto

### 1. Clonar o Repositório
```bash
git clone https://github.com/KelvinMarcondes/springsecurity.git
```

### 2. Configurar o Banco de Dados
Certifique-se de que o Docker e o Docker Compose estão instalados. No diretório raiz do projeto, execute:
```bash
docker-compose up -d
```
Isso iniciará um contêiner MySQL com as configurações definidas no arquivo docker-compose.yml.

### 3. Configurar o Spring Security
No arquivo application.properties, configure as propriedades do banco de dados e as chaves JWT:
```bash
# Enables the DATA.SQL for DATABASE
spring.sql.init.mode=always

spring.jpa.defer-datasource-initialization=true
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

spring.datasource.url=jdbc:mysql://localhost:3306/mydb
spring.datasource.username=admin
spring.datasource.password=123
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

jwt.public.key=classpath:app.pub
jwt.private.key=classpath:app.key
```

### 4. Executar a Aplicação
   Use o Maven para compilar e executar a aplicação:
```bash
./mvnw spring-boot:run
```