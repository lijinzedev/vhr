import {getRequest} from "@/utils/api";
import da from "element-ui/src/locale/lang/da";

export const initMenu = (router, store) => {
    if (store.state.routes.length > 0) {
        return;
    }

    getRequest("/system/config/menu").then(data => {
        //如果请求到了数据
        if (data) {

            let fmtRoutes = [];
            fmtRoutes = formatRoutes(data);
            router.addRoutes(fmtRoutes);
            store.commit('initRoutes', fmtRoutes);
        }


    }).catch(data => {

        console.log(data)
    })
}

export const formatRoutes = (router) => {
    let fmRoutes = [];
    router.forEach(routes => {
        // 批量定义
        let {
            path,
            component,
            name,
            meta,
            iconCls,
            children
        } = routes;
        // 递归的处理 children
        if (children && children instanceof Array) {
            children = formatRoutes(children);
        }

        let fmRouter = {
            path: path,
            name: name,
            iconCls: iconCls,
            meta: meta,
            children: children,
            component(resolve) {
                if (component.startsWith("Home")) {
                    require(['../views/' + component + '.vue'], resolve);
                } else if (component.startsWith("Emp")) {
                    require(['../views/emp/' + component + '.vue'], resolve);
                } else if (component.startsWith("Per")) {
                    require(['../views/per/' + component + '.vue'], resolve);
                } else if (component.startsWith("Sal")) {
                    require(['../views/sal/' + component + '.vue'], resolve);
                } else if (component.startsWith("Sta")) {
                    require(['../views/sta/' + component + '.vue'], resolve);
                } else if (component.startsWith("Sys")) {
                    require(['../views/sys/' + component + '.vue'], resolve);
                }
            }
        }
        fmRoutes.push(fmRouter);

    })
    return fmRoutes;
}