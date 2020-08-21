<template>
  <div>
    <div>
      <div class="permissManaTool">
        <el-input size="small" placeholder="请输入角色英文名" v-model="role.name">
          <template slot="prepend">ROLE_</template>
        </el-input>
        <el-input size="small" placeholder="请输入角色中文名" @keydown.enter.native="doAddRole" v-model="role.nameZh">
        </el-input>
        <el-button icon="el-icon-plus" size="small" type="primary" @click="doAddRole">添加角色</el-button>
      </div>
    </div>

    <div class="permissManaMain">
      <el-collapse v-model="activeNames" @change="change" accordion>
        <el-collapse-item :title="item.nameZh" :name="item.id" v-for="(item,index) in roles" :key="index">
          <el-card class="box-card">
            <div slot="header" class="clearfix">
              <span>可访问的资源</span>
              <el-button style="float: right; padding: 3px 0;color:#ff0000" @click="doDeleteRole(item)" icon="el-icon-delete"
                         type="text"></el-button>
            </div>
            <div>
              <el-tree
                  show-checkbox
                  ref="tree"
                  node-key="id"
                  :key="index"
                  :default-checked-keys="defaultCheckKeys"
                  :data="menus" :props="defaultProps">
              </el-tree>
              <div style="display: flex; justify-content: flex-end">
                <el-button size="mini" @click="cancleUpdate">取消修改</el-button>
                <el-button size="mini" type="primary" @click="doUpdata(item.id,index)" class="btn btn-primary">确认修改
                </el-button>
              </div>
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
      activeNames: -1,
      defaultCheckKeys: [],
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
    doDeleteRole(item) {
      this.$confirm(`确定删除${item.nameZh}角色吗`, '警告', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.deleteRequest(`/system/basic/premiss/role/${item.id}`).then(data => {
          if (data) {
            this.initRoles()
          }
        });
      }).catch((e) => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      });

    },
    doAddRole() {
      if (this.role.name && this.role.nameZh) {

        this.postRequest("/system/basic/premiss/", this.role).then(value => {
          if (value) {
            this.initRoles();
            this.role = [];
          }
        })
      } else {
        this.$message.error("值不能为空!")
      }
    },
    cancleUpdate() {
      this.activeNames = -1;
    },
    doUpdata(id, index) {
      let treeElement = this.$refs.tree[index];
      let selectKeys = treeElement.getCheckedKeys(true);
      this.putRequest(`/system/basic/premiss/${id}`, selectKeys).then(data => {
        if (data) {
          this.activeNames = -1;
        }
      })
    },
    initDefaultKets(val) {
      this.getRequest(`/system/basic/premiss/${val}`).then(data => {
        if (data) {
          this.defaultCheckKeys = data;
        }
      })
    },
    change(name) {
      if (name) {
        this.initMenus();
        this.initDefaultKets(name);
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