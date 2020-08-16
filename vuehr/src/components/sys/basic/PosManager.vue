<!--职位管理-->
<template>
  <div>
    <div>
      <!--      搜索-->
      <div>
        <el-input
            class="addPosinput"
            size="small"
            placeholder="添加职位"
            prefix-icon="el-icon-plus"
            v-model="pos.name">
        </el-input>
        <el-button icon="el-icon-plus" size="small" type="primary" @click="addPostion"
                   @keydown.enter.native="addPostion"> 添加
        </el-button>
      </div>
      <!--      表格-->
      <div class="posMangtable">
        <el-table

            :data="positions"
            border
            @selection-change="handleSelectionChange">
          stripe
          size="small"
          style="width: 70%">
          <el-table-column
              type="selection"
          >
          </el-table-column>
          <el-table-column
              prop="id"
              label="编号"
              width="55">
          </el-table-column>
          <el-table-column
              prop="name"
              label="职位名称"
              width="200">
          </el-table-column>
          <el-table-column
              prop="createDate"
              label="创建时间">
          </el-table-column>
          <el-table-column
              label="操作">
            <template slot-scope="scope">
              <el-button
                  size="mini"
                  @click="showEditView(scope.$index, scope.row)">编辑
              </el-button>
              <el-button
                  size="mini"
                  type="danger"
                  @click="handleDelete(scope.$index, scope.row)">删除
              </el-button>
            </template>

          </el-table-column>
        </el-table>
      </div>
      <!--      批量删除-->
      <el-button type="danger" size="small" style="margin-top: 8px;" :disabled="multipleSelection.length==0"
                 @click="deleteMany">批量删除
      </el-button>
    </div>
    <!--对话框-->
    <el-dialog
        title="修改职位"
        :visible.sync="dialogVisible"
        width="30%">
      <div>
        <el-tag>职位名称</el-tag>
        <el-input size="small" class="updatePosInput" v-model="updataPos.name"></el-input>
      </div>
      <span slot="footer" class="dialog-footer">
    <el-button size="small" @click="dialogVisible = false">取 消</el-button>
    <el-button size="small" type="primary" @click="doupdate">确 定</el-button>
  </span>
    </el-dialog>

  </div>


</template>

<script>
export default {
  name: "DepManager",
  data() {
    return {
      pos: {
        name: ''
      },
      multipleSelection: [],
      updataPos: {
        name: '',
      },
      dialogVisible: false,
      // 表格数据
      positions: []
    }
  },
  mounted() {
    this.initPositions();
  },
  methods: {
    deleteMany() {
      this.$confirm(`确定删除${this.multipleSelection.length}条记录吗?`, '警告', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        let ids = '?';
        this.multipleSelection.forEach(item => {
          ids += 'ids=' + item.id + '&';
        })
        this.deleteRequest("/system/basic/pos/"+ids).then(data => {
          if (data) {
            this.initPositions();
          }

        })

      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      });
    },
    handleSelectionChange(val) {
      this.multipleSelection = val;
    },
    doupdate() {
      this.dialogVisible = false;
      this.putRequest('/system/basic/pos/', this.updataPos).then(date => {
        if (date) {
          this.initPositions();
          this.updataPos.name = '';
          this.dialogVisible = false
        }

      })
    },
    initPositions() {
      this.getRequest('/system/basic/pos/').then(data => {
        // 如果请求成功
        if (data) {
          this.positions = data;
        }

      })
    },
    showEditView(index, data) {
      this.dialogVisible = true;
      Object.assign(this.updataPos, data)
    },
    handleDelete(index, data) {
      this.$confirm(`确定删除${this.pos.name}职位吗?`, '警告', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.deleteRequest("/system/basic/pos/" + data.id).then(data => {
          if (data) {
            this.initPositions();
          }

        })

      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      });
    },
    addPostion() {
      if (this.pos.name) {
        this.postRequest('/system/basic/pos/', this.pos).then(resp => {
          // 添加成功
          if (resp) {
            this.initPositions();
            this.pos.name = "";
          }
        })
      } else {
        this.$message.error('职位名称不可以为空');
      }
    },

  }
}
</script>


<style>
.addPosinput {
  width: 300px;
  margin-right: 8px
}

.posMangtable {
  margin-top: 10px;
}

.updatePosInput {
  width: 200px;
  margin-left: 8px;

}
</style>