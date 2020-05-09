import Vue from 'vue'
import Router from 'vue-router'
import Licitacao from './components/licitacao/Licitacao'
import Proposta from './components/proposta/Proposta'
import Sobre from './components/sobre/Sobre'

Vue.use(Router)

const router = new Router({
  mode: 'history',
  routes: [
    {
      path: '/',
      component: Licitacao,
      meta: {
        title: 'Licitações'
      }
    },
    {
      name: 'propostas',
      path: '/propostas',
      component: Proposta,
      meta: {
        title: 'Propostas'
      }
    },
    {
      path: '/sobre',
      component: Sobre,
      meta: {
        title: 'Sobre'
      }
    },
    {
      path: '*',
      redirect: '/'
    }
  ]
})

// Add to router to change the name of page title
router.beforeEach((to, from, next) => {
  document.title = to.meta.title || 'DESAFIO API'
  next()
})

export default router