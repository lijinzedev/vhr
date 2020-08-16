<template>
  <div>
    <el-container>
      <!--头部导航栏-->
      <el-header class="homeHeader">
        <div class="title"> 微人事</div>
        <el-dropdown class="userInfo" @command="commandHandler">
  <span class="el-dropdown-link">
    {{ user.name }}<i><img :src="user.userface" alt=""></i>
  </span>
          <el-dropdown-menu slot="dropdown" command="userInfo">
            <!--个人中心 -->
            <el-dropdown-item command="userInfo">个人中心</el-dropdown-item>
            <!--设置-->
            <el-dropdown-item command="setting">设置</el-dropdown-item>
            <!--注销登录-->
            <el-dropdown-item command="logout" divided>注销登录</el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
      </el-header>

      <!--内容-->
      <el-container>

        <!--侧抽屉菜单-->
        <el-aside width="200px">
          <el-menu @select="menuClick" unique-opened>
            <el-submenu :index="index+'' " v-for="(item,index) in routes" v-if="!item.hidden" :key="index">
              <template slot="title">
                <i style="color: #2990ff;margin-right: 8px " :class="item.iconCls"></i>
                <span>{{ item.name }}</span>
              </template>
              <el-menu-item :index="child.path" v-for="(child,j) in item.children" :key="j">{{ child.name }}
              </el-menu-item>
            </el-submenu>
          </el-menu>
        </el-aside>
        <!--中心主体-->
        <el-main>
          <!--         面包线-->
          <el-breadcrumb separator-class="el-icon-arrow-right" v-if="this.$router.currentRoute.path!='/home'">
            <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item>{{ this.$router.currentRoute.name }}</el-breadcrumb-item>
          </el-breadcrumb>
          <div class="homeWelcome" v-if="this.$router.currentRoute.path=='/home'">
            欢迎来到微人事系统!
          </div>
          <router-view class="homeRouter"/>
        </el-main>
      </el-container>
    </el-container>


  </div>
</template>

<script>
export default {
  name: "Home",
  data() {
    return {
      user: JSON.parse(window.sessionStorage.getItem("user"))
    }
  },
  computed: {
    routes() {
      return this.$store.state.routes;
    }
  },
  methods: {
    commandHandler(cmd) {
      if (cmd == 'logout') {
        this.$confirm('此操作将注销登录, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.getRequest('/logout');
          window.sessionStorage.clear();
          this.$router.replace('/')
          this.$store.state.routes = [];
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消操作'
          });
        });

      } else if (cmd == 'setting') {

      } else if (cmd == 'userInfo') {

      }
    },
    menuClick(index) {

      this.$router.push(index)
    }

  }
}
</script>

<style scoped>
.homeHeader {
  background-color: #2990ff;
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 0px 15px;
  box-sizing: border-box;
}

.homeHeader .title {
  font-size: 30px;
  font-family: 华文行楷;
  color: #ffffff;
}

.homeHeader .userInfo {
  cursor: pointer;
}

.el-dropdown-link {
  cursor: pointer;
  color: #ffffff;
  display: flex;
  align-items: center;
}

.el-dropdown-link img {
  width: 48px;
  height: 48px;
  border-radius: 24px;
  margin-left: 8px;
}

.el-icon-arrow-down {
  font-size: 12px;
}

.homeWelcome {
  text-align: center;
  align-items: center;
  font-size: 80px;
  font-family: 华文行楷;
  color: #2990ff;
  padding-top: 100px;
}
.homeRouter{
  margin: 15px 0px 0px 0px ;
}
</style>