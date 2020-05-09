import Vue from 'vue'
import axios from 'axios'

const username = 'desafio-api'
const password = '123456admin'
const token = Buffer.from(`${username}:${password}`, 'utf8').toString('base64')

Vue.use({
  install(Vue) {
    Vue.prototype.$http = axios.create({
      // Ou irá pegar a URL do netlify ou acessar a API do docker
      // baseURL: process.env.API_URL || 'http://localhost:8080/api',
      baseURL: 'https://desafio-api-az.herokuapp.com/api',
      headers: {
        Authorization: `Basic ${token}`
      }
    })
  }
})