<template>
  <div>
    <div>
      <div class="permissManaTool">
        <el-input size="small" placeholder="请输入角色英文名" v-model="role.name">
          <template slot="prepend">ROLE_</template>
        </el-input>
        <el-input size="small" placeholder="请输入角色中文名" v-model="role.nameZh">
        </el-input>
        <el-button icon="el-icon-plus" size="small" type="primary">添加角色</el-button>
      </div>
    </div>

    <div class="permissManaMain">
      <el-collapse  @change="change" accordion>
        <el-collapse-item   :title="item.nameZh" :name="item.id" v-for="item in roles" :key="index">
          <el-card class="box-card">
            <div slot="header" class="clearfix">
              <span>可访问的资源</span>
              <el-button style="float: right; padding: 3px 0;color:#ff0000" icon="el-icon-delete" type="text"></el-button>
            </div>
            <div>
              <el-tree
                  show-checkbox
                  :data="menus" :props="defaultProps"  ></el-tree>
            </div>
        
          </el-card>
        </el-collapse-item>
      </el-collapse>
    </div>

  </div>
</template>

<script>
export default {
  name: "PermissManager",
  data() {
    return {
      role: {
        name: '',
        nameZh: "",
      },
      roles: [],
      menus: [],
      defaultProps: {
        children: 'children',
        label: 'name'
      },
    };
  },
  mounted() {
    this.initRoles();
  },
  methods: {
    change(name){
      if (name){
        this.initMenus();
      }
    },
    initRoles() {
      this.getRequest(`/system/basic/premiss/`).then(data => {
        if (data) {
          this.roles = data;
        }
      })
    },
    initMenus() {
      this.getRequest(`/system/basic/premiss/menus`).then(data => {
        if (data) {
          this.menus = data;
        }
      })
    }
  }
}
</script>

<style scoped>
.permissManaTool {
  display: flex;
  justify-content: flex-start;
}

.permissManaTool .el-input {
  width: 300px;
  margin-right: 3px;
}

.permissManaMain {
  margin-top: 10px;
  width: 700px;
}
</style>