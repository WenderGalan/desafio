[![Netlify Status](https://api.netlify.com/api/v1/badges/d9187f07-f1a6-4f33-85a8-832f5b9631c5/deploy-status)](https://app.netlify.com/sites/desafioaz/deploys)
# Desafio AZ

### Dependencies (front-end):
  - #### [VUE](https://vuejs.org/): Framework web.
  - #### [AXIOS](https://www.npmjs.com/package/axios): Usado para fazer as requisições assíncronas.
  - #### [VUE-ROUTER](https://router.vuejs.org/): Usado para a navegação entre as páginas do SPA.
  - #### [VUETIFY](https://vuetifyjs.com/): Usado para facilitar a criação das telas, no geral melhoria em layout sem muito css.
  
### Deploy
- #### [FRONT-END (CLIQUE AQUI)](https://desafioaz.netlify.app/) - NETLIFY
  
### Como rodar o projeto?
  - Para executar o comando abaixo é necessário ter o node na versão mais atualizada em sua máquina:
  ```shell
  npm install

  npm run serve
  ```
  - Para compilação para produção:
  ```shell
    npm run build
  ```
  - Se possuir o docker em sua máquina pode iniciar o projeto por ele, volte a pasta raiz do projeto e execute o comando abaixo:
  ```shell
   docker-compose up -d --build
   ```
- No seu browser acesse as urls: 
    - Front-end: http://localhost:5000
    - Back-end: http://localhost:4000/api/swagger-ui.html
