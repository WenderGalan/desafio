# Desafio AZ

### Dependencies:
  - #### [MAPSTRUCT](https://mapstruct.org/): Usado para criar os DTOs da aplicação.
  - #### [LOMBOK](https://projectlombok.org/): Usado para gerar automaticamente getters, setters, e métodos padrões das entidades.
  - #### SPRING SECURITY
  - #### JPA
  - #### SWAGGER UI: Used for RESTFUL API Documentation
  - #### H2: Banco de dados da aplicação. Usado como banco de dados da aplicação em memória.
  
### Deploy
- #### [BACK-END (CLIQUE AQUI)](https://desafio-api-az.herokuapp.com/api) - HEROKU
  - ##### Default login to api: 'desafio-api'
  - ##### Default password to api: '123456admin'
  
### Como rodar o projeto?
  - Use alguma IDE que tenha suporte para o Lombok e instale o plugin para que os métodos sejam gerados para a análise de código, ou apenas execute a aplicação como o comando abaixo:
  ```shell
  mvn spring-boot:run
  ```
  - Se possuir o docker em sua máquina pode iniciar o projeto por ele, volte a pasta raiz do projeto e execute o comando abaixo:
  ```shell
   docker-compose up -d --build
   ```
- No seu browser acesse as urls: 
    - Front-end: http://localhost:5000
    - Back-end: http://localhost:4000/api/swagger-ui.html