import Vue from "vue";
import VueRouter from "vue-router";
import Home from "../views/Home.vue";
import UserCenter from "../views/UserCenter.vue";
import TaskList from "../views/TaskList.vue";
import TaskView from "../views/taskView.vue";
import Profile from "../views/Profile.vue";
import Index from "../views/Index.vue";
import List from "../views/List.vue";
import NewsList from "../views/NewsList.vue";
import News from "../views/News.vue";
import Temp from "../views/Temp.vue";

Vue.use(VueRouter);

const routes = [
  {
    path: "/",
    name: "首页",
    component: Index
  },
  {
    path: "/home",
    name: "home",
    component: Home
  },
  {
    path: "/news/:id",
    name: "Nes",
    component: News
  },
  {
    path: "/user",
    name: "User",
    component: UserCenter
  },
  {
    path: "/list",
    name: "List",
    component: List
  },
  {
    path: "/newsList",
    name: "NewsList",
    component: NewsList
  },
  {
    path: "/task/:id",
    name: "Task",
    component: TaskView
  },
  {
    path: "/profile",
    name: "Profile",
    component: Profile
  },
  {
    path: "/Temp",
    name: "Temp",
    component: Temp
  }
];
const VueRouterPush = VueRouter.prototype.push;
VueRouter.prototype.push = function push(to) {
  return VueRouterPush.call(this, to).catch(err => {});
};

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes
});

export default router;
