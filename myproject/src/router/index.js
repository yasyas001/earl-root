import Vue from 'vue'
import Router from 'vue-router'
import A from '@/components/A'
import B from '@/components/B'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/a',
      name: 'A',
      component: A
    },
    {
      path: '/B',
      name: 'B',
      component: B
    }
  ]
})
