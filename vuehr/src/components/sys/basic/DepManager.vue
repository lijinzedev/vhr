<template>
  <div style="width: 500px;">
    <el-input
        placeholder="请输入部门名称搜索......"
        prefix-icon="el-icon-search"
        v-model="filterText">
    </el-input>

    <el-tree
        :data="deps"
        :props="defaultProps"
        :expand-on-click-node="false"
        :filter-node-method="filterNode"
        ref="tree">
        <span class="custom-tree-node" slot-scope="{ node, data }">
        <span>{{ data.name }}</span>
        <span>
          <el-button class="depButton"
                     type="primary"
                     size="mini"
                     @click="() => addDep(data)">
            添加部门
          </el-button>
          <el-button
              class="depButton"
              type="danger"
              size="mini"
              @click="() => deleteDep(data)">
            删除部门
          </el-button>
        </span>
      </span>
    </el-tree>
    <el-dialog
        title="添加部门"
        :visible.sync="dialogVisible"
        width="30%">
      <div>
        <table>
          <tr>
            <td>
              <el-tag>上级部门</el-tag>
            </td>
            <td>{{ pname }}</td>
          </tr>
          <tr>
            <td>
              <el-tag>部门名称</el-tag>
            </td>
            <td>
              <el-input v-model="dep.name" placeholder="请输入部门名称"></el-input>
            </td>
          </tr>
        </table>
      </div>
      <span slot="footer" class="dialog-footer">
    <el-button @click="dialogVisible = false">取 消</el-button>
    <el-button type="primary" @click="doAddDep">确 定</el-button>
  </span>
    </el-dialog>

  </div>
</template>

<script>
export default {
  name: "DepManager",
  watch: {
    filterText(val) {
      this.$refs.tree.filter(val);
    }
  },
  mounted() {
    this.initdeps();
  },
  methods: {
    initDep() {
      this.dep = {
        name: "",
        parentId: -1
      };
      this.pname = "";
    },
    addDep2Deps(deps, dep) {
      for (let i = 0; i < deps.length; i++) {
        let d = deps[i];
        if (d.id == dep.parentId) {
          debugger
          d.childrens = d.childrens.concat(dep)
          return;
        } else {
          this.addDep2Deps(d.childrens, dep);
        }
      }
    },
    doAddDep() {
      this.postRequest("/system/basic/department/", this.dep).then(value => {
        if (value) {
          this.addDep2Deps(this.deps, value);
          this.dialogVisible = false;
          this.initDep();
        }
      })
    },
    addDep(data) {
      this.pname = data.name;
      this.dep.parentId = data.id;
      this.dialogVisible = true;
    },
    deleteDep(data) {
      if (data.isParent) {
        this.$message.error("不可以直接删除父部门")
        return;
      }
      this.$confirm(`此操作将永久删除[${data.name}]部门,是否继续?`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.deleteRequest(`/system/basic/department/${data.id}`).then(value => {
          if (value) {
            this.removeDepFromDeps(this.deps, data.id);
          }

        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      });

    },
    filterNode(value, data) {
      if (!value) return true;
      return data.name.indexOf(value) !== -1;
    },
    initdeps() {
      this.getRequest('/system/basic/department/').then(data => {
        if (data) {
          this.deps = data;
        }
      })
    },
    removeDepFromDeps(deps, id) {
      for (let i = 0; i < deps.length; i++) {
        let d = deps[i];
        if (d.id == id) {
          deps.splice(i, 1);
          return;
        } else {
          this.removeDepFromDeps(d.childrens, id);
        }
      }
    }
  },
  data() {
    return {
      dep: {
        name: "",
        parentId: -1
      },
      pname: "",
      dialogVisible: false,
      // 树的数据
      deps: [],
      defaultProps: {
        children: 'childrens',
        label: 'name'
      },
      //搜索框数据
      filterText: '',
    }
  },

}
</script>

<style scoped>
.depButton {
  padding: 2px;
}

.custom-tree-node {
  display: flex;
  justify-content: space-between;
  width: 100%;
}
</style>