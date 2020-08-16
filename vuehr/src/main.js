import Vue from 'vue'
import App from './App.vue'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import router from './router'
import {postKeyValueRequest} from "@/utils/api";
import {putRequest} from "@/utils/api";
import {deleteRequest} from "@/utils/api";
import {getRequest} from "@/utils/api";
import {postRequest} from "@/utils/api";
import store from './store'
import {initMenu} from "@/utils/menus";
import 'font-awesome/css/font-awesome.min.css'
Vue.prototype.postKeyValueRequest=postKeyValueRequest;
Vue.prototype.putRequest=putRequest;
Vue.prototype.deleteRequest=deleteRequest;
Vue.prototype.getRequest=getRequest;
Vue.prototype.postRequest=postRequest;
Vue.use(ElementUI);
Vue.config.productionTip = false

// 前置导航守卫 类似于java的拦截器
router.beforeEach((to,from,next)=>{
    if (to.path=='/') next();
    else {
        if(window.sessionStorage.getItem("user")){
            initMenu(router,store);
            next();
        }else {
            next("/?redirect="+to.path);
        }

    }

});
new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
