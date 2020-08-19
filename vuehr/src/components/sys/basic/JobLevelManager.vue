<!-- 职称管理-->
<template>
  <div>
    <!--    添加-->
    <div>
      <el-input placeholder="添加职称..." size="small" v-model="jl.name" style="width: 300px"
                prefix-icon="el-icon-plus"></el-input>
      <el-select size="small" v-model="jl.titleLevel" placeholder="职称等级" style="margin-left: 5px;margin-right: 5px">
        <el-option
            v-for="item in titleLevels"
            :key="item"
            :label="item"
            :value="item">
        </el-option>
      </el-select>
      <el-button icon="el-icon-plus" size="small" type="primary" @click="addJobLevel">添加</el-button>
    </div>
    <!--    表格-->
    <div style="margin-top: 10px">
      <el-table
          :data="jls"
          size="small"
          border
          @selection-change="handleSelectionChange"
          style="width: 80%">
        <el-table-column
            type="selection"
        ></el-table-column>
        <el-table-column
            prop="id"
            label="编号"
            width="55">
        </el-table-column>
        <el-table-column
            prop="name"
            label="职位名称"
            width="150">
        </el-table-column>
        <el-table-column
            prop="titleLevel"
            label="职称级别">
        </el-table-column>
        <el-table-column
            prop="createDate"
            label="创建时间">
        </el-table-column>
        <el-table-column
            prop="enabled"
            label="是否启用">
          <template slot-scope="scope">
            <el-tag v-if="scope.row.enabled" type="success">已启用</el-tag>
            <el-tag v-else="scope.row.enabled" type="danger">未启用</el-tag>
          </template>
        </el-table-column>
        <el-table-column
            label="操作">
          <template slot-scope="scope">
            <el-button size="small" @click="handleUpdate(scope.$index, scope.row)">编辑</el-button>
            <el-button size="small" type="danger" @click="handleDelete(scope.$index, scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <!--      批量删除-->
    <el-button type="danger" size="small" style="margin-top: 8px;" :disabled="multipleSelection.length==0"
               @click="deleteMany">批量删除
    </el-button>
    <!--对话框-->
    <el-dialog
        title="修改职称"
        :visible.sync="dialogVisible"
        width="30%">
      <div>
        <table>
          <tr><td><el-tag>职称名</el-tag></td>
            <td><el-input size="small" v-model="updataJob.name"></el-input>
            </td>
          </tr>
          <tr>
            <td>
              <el-tag>级别</el-tag>
            </td>
            <td>
              <el-select size="small" v-model="updataJob.titleLevel" placeholder="职称等级"
              >
                <el-option
                    v-for="item in titleLevels"
                    :key="item"
                    :label="item"
                    :value="item">
                </el-option>
              </el-select>
            </td>
          </tr>
          <tr>
            <td>
              <el-tag>是否启用</el-tag>
            </td>
            <td>
              <el-switch
                  v-model="updataJob.enabled"
                  size="small"
                  active-text="启用"
                  inactive-text="禁用"
              >
              </el-switch>
            </td>
          </tr>

        </table>
      </div>
      <span slot="footer" class="dialog-footer">
    <el-button size="small" @click="dialogVisible = false">取 消</el-button>
    <el-button size="small" type="primary" @click="doUpdate">确 定</el-button>
      </span>
    </el-dialog>
  </div>

</template>

<script>
export default {
  name: "DepManager",
  // 生命周期当中嵌入初始化方法
  mounted() {
    this.initJobLevel();
  },
  data() {
    return {
      multipleSelection: [],
      updataJob: {
        name: "",
        titleLevel: "",
        enabled: false
      },

      dialogVisible: false,
      jls: [],
      jl: {
        name: "",
        titleLevel: ""
      },
      titleLevels: [
        `正高级`,
        `副高级`,
        `初级`,
        `中级`,
        `员级`,
      ],
    }
  },
  methods: {
    initJobLevel() {
      this.getRequest("/system/basic/jobLevel/").then(data => {
            if (data) {
              this.jls = data;
            }
          }
      )
    },
    addJobLevel() {
      if (this.jl.titleLevel && this.jl.name) {
        this.postRequest("/system/basic/jobLevel/", this.jl).then(data => {
          if (data) {
            this.initJobLevel();
            this.jl = {
              name: "",
              titleLevel: ""
            }
          }

        });
      } else {
        this.$message({
          type: 'info',
          message: '添加的字段不能为空'
        });
      }
    },
    handleDelete(index, data) {
      this.$confirm(`确定删除[${data.name}]职称吗?`, '警告', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.deleteRequest("/system/basic/jobLevel/" + data.id).then(data => {
          if (data) {
            this.initJobLevel();
          }

        })

      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      });
    },
    handleUpdate(index, data) {
      this.dialogVisible = true;
      Object.assign(this.updataJob, data);
    },
    doUpdate() {
      this.putRequest("/system/basic/jobLevel/", this.updataJob).then(data => {
        if (data) {
          this.initJobLevel();
        }
        this.dialogVisible = false;
      });
    },
    handleSelectionChange(val){
      this.multipleSelection = val;
    },
    deleteMany(){
      this.$confirm(`确定删除${this.multipleSelection.length}条记录吗?`, '警告', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        let ids = '?';
        this.multipleSelection.forEach(item => {
          ids += 'ids=' + item.id + '&';
        })
        this.deleteRequest("/system/basic/jobLevel/"+ids).then(data => {
          if (data) {
            this.initJobLevel();
          }

        })

      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      });
    },
  },

}
</script>

<style scoped>

</style>