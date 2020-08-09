import Vue from 'vue'
import Vuex from 'vuex'
import da from "element-ui/src/locale/lang/da";

Vue.use(Vuex);


export default new Vuex.Store(
    {
        state: {
            routes: []
        },
        mutations: {
            initRoutes(state, data) {
                state.routes = data;
            }
        },
        actions: {


        }

    }
)