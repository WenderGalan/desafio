import Vue from 'vue'
import Router from 'vue-router'
import PersonTable from './components/person/PersonTable'
import About from './components/About'

Vue.use(Router)

const router = new Router({
  mode: 'history',
  routes: [
    {
      path: '/',
      component: PersonTable,
      meta: {
        title: 'Person API - Home'
      }
    },
    {
      path: '/about',
      component: About,
      meta: {
        title: 'Person API - About'
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