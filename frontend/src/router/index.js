import Vue from 'vue'
import VueRouter from 'vue-router'

const Login = () => import('views/Login')
const File = () => import('views/File')
const Register = () => import('views/Register')

Vue.use(VueRouter)

const routes = [
  {
    path: '',
    redirect: '/login'
  },
  {
    path: '/login',
    component: Login
  },
  {
    path: '/file/:token?',
    component: File
  },
  {
    path: '/register',
    component: Register
  },

]

const router = new VueRouter({
  routes,
  mode: 'history'
})

export default router