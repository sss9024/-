import Vue from "vue";
import VueRouter, { RouteConfig } from "vue-router";
import { component } from "vue/types/umd";
import Home from "../views/Home.vue";
import About from "../views/About.vue";
import Selection from "../views/Selection.vue";
import Description from "../views/Description.vue";
import Connect from "../views/Connect.vue"
import Practice from "../views/Practice.vue"
import Test from "../views/Test.vue";
import Score from "../views/Score.vue";
import NotFound from "../views/NotFound.vue";
import Login from "../views/Login.vue"
// import Image from "../views/image.vue"
import authToken from "../axios/auth"

Vue.use(VueRouter);

const requireAuth = () => (from, to , next) => {
  if(authToken()) return next();
  else {
    alert("로그인이 필요한 서비스입니다.\n로그인을 먼저 해주세요.")
    next('/');
  }
}

const isAuth = () => (from, to, next) => {
  if(authToken()) { 
    alert("로그인 되어 있으면 접근할 수 없는 페이지 입니다.\n로그아웃을 먼저 해주세요.")
    return next('/home');
  }
  else next();
}

const routes: Array<RouteConfig> = [
  {
    path: "/",
    name: "Login",
    component: Login,
    beforeEnter: isAuth()
  },
  {
    path: "/home",
    name: "Home",
    component: Home,
    beforeEnter: requireAuth()
  },
  {
    path: "/about",
    name: "About",
    component: About,
    beforeEnter: requireAuth()
  },
  {
    path: "/main/:exerciseId",
    name: "ExerciseMain",
    component: Description,
    beforeEnter: requireAuth()
  },
  {
    path: "/sel/:exerciseId",
    name: "ExerciseSel",
    component: Selection,
    beforeEnter: requireAuth()
  },
  {
    path: "/guide/:exerciseId/:contentId",
    name: "ContentMain",
    component: Description,
    beforeEnter: requireAuth()
  },
  {
    path: "/connect/:exerciseId/:contentId",
    name: "Connect",
    component: Connect,
    beforeEnter: requireAuth()
  },
  {
    path: "/practice/:exerciseId/:contentId",
    name: "Practice",
    component: Practice,
    beforeEnter: requireAuth()
  },
  {
    path: "/test/:exerciseId/:contentId",
    name: "Test",
    component: Test,
    beforeEnter: requireAuth()
  },
  {
    path: "/score/:exerciseId/:contentId",
    name: "Score",
    component: Score,
    beforeEnter: requireAuth()
  },
  // {
  //   path: "/image",
  //   name: "Image",
  //   component: Image
  // },
  {
    path: "/404",
    name: "NotFound",
    component: NotFound
  },
  {
    path: "*",
    redirect: "/404"
  }
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes
});

export default router;
