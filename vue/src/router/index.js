import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/Home.vue'
import Login from '../views/Login.vue'
import File from '../components/File.vue'
import Safe from '../components/Safe.vue'
import Recycle from '../components/Recycle.vue'
import Collect  from '../components/Collect.vue'
import Share  from '../components/Share.vue'
import Shop from '../components/Shop.vue'
import UserInfo from '../components/UserInfo.vue'
import UserService from '../components/UserService.vue'
import Consume from '../components/Consume.vue'
import FileList from '../components/FileList.vue'
import SafeVerify from '../components/SafeVerify.vue'
import SearchResult from '../components/SearchResult.vue'
import AdminLogin from '../views/AdminLogin.vue'
import AdminHome from '../views/AdminHome.vue'
import AdminUserTable from '../components/AdminUserTable.vue'
import AdminCombo from '../components/Combo.vue'
import Leaderboard from '../components/ComboLeaderboard.vue'
import Index from '../views/index.vue'
import Feedback from '../components/Feedback.vue'
import AdminVerify from '../components/AdminVerify .vue'
import AdminTable from '../components/AdminTable.vue'
import UserSeacher from '../components/UserSeacher.vue'
import AddShare from '../views/addShare.vue'
import AdminStatistics from'../components/AdminStatistics.vue'

Vue.use(VueRouter)
const RouterPush = VueRouter.prototype.push
VueRouter.prototype.push = function push (to) {
  return RouterPush.call(this, to).catch(err => err)
}
const RouterReplace = VueRouter.prototype.replace
VueRouter.prototype.replace = function replace (to) {
  return RouterReplace.call(this, to).catch(err => err)
}
// router.afterEach((to)=>{
//   history.pushState(null, null, location.protocol + '//' + location.host + '/#' + to.path)
// })
const routes = [
  {
    path:'/',
    name:'Index',
    component: Index
  },
  {
    path:'/addshare',
    name:'AddShare',
    component: AddShare
  },
  {
    path: '/login',
    name: 'Login',
    component: Login
  },
  {
    path: '/home',
    name: 'Home',
    redirect:'/home/file',
    component:Home,
    children:[
      {
        // 文件页
        path: '/home/file',
        name: 'File',
        component: File,
      },
     
      {
        path:'/home/search',
        name: 'SearchResult',
        component:SearchResult
      },
      //回收
      {
        path:'/home/recycle',
        name:'Recycle',
        component:Recycle

      },
      // 收藏
      {
        path:'/home/collect',
        name:'Collect',
        component:Collect
      },
      {
        path:'/home/safeverify',
        name:'SafeVerify',
        component:SafeVerify
      },
      // 保险
     {
      path:'/home/safe',
      name:'Safe',
      component:Safe
     },
     //分享
     {
       path:'/home/share',
       name:'Share',
       component:Share
     },
     //购买页
     {
       path:'/home/shop',
       name:'Shop',
       component: Shop
     },
     //消费记录
     {
       path:'/home/consume',
       name:'Consume',
       component:Consume
     },
     //用户信息
     {
       path:'/home/userinfo',
       name:'UserInfo',
       component:UserInfo
     },
     //客服
     {
       path:'/home/userservice',
       name:'UserService',
       component:UserService
     },
     {
       path: '/home/filelist',
       name: 'FileList',
       component: FileList
     }
     
    ]

  },
  {
    path:'/adminlogin',
    name:'AdminLogin',
    component:AdminLogin
  },
  {
    path:'/adminhome',
    name:'AdminHome',
    component: AdminHome,
    children:[
      {
      path:'/adminhome/usertable',
      name:'UserTable',
      component: AdminUserTable
      },
      {
        path:'/adminhome/combo',
        name:'Combo',
        component:AdminCombo   
      },{
        path:'/adminhome/leaderboard',
        name:'Leaderboard',
        component:Leaderboard
      },{
        path:'/adminhome/feedback',
        name:'Feedback',
        component:Feedback
      },{
        path:'/adminhome/adminverify',
        name:'AdminVerify',
        component:AdminVerify
      },{
        path:'/adminhome/admintable',
        name:'AdminTable',
        component:AdminTable
      },{
        path:'/adminhome/userseacher',
        name:'UserSeacher',
        component:UserSeacher
      },
      {
        path:'/adminhome/statistics',
        name:' AdminStatistics',
        component: AdminStatistics
      }
    ]
  }

 
]

const router = new VueRouter({
  mode: 'hash',
  routes,
  // scrollBehavior: () => {
  //   history.pushState(null, null, document.URL)
  // }
})

export default router
