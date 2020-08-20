<template>
  <el-tabs v-model="activeName" type="card">
    <el-tab-pane label="部门管理" name="depmanager">
      <DepManager></DepManager>
    </el-tab-pane>
    <el-tab-pane label="职位管理" name="posmanager">
      <PosManager></PosManager>
    </el-tab-pane>
    <el-tab-pane label="职称管理" name="joblevelmanager">
      <JobLevelManager></JobLevelManager>
    </el-tab-pane>
    <el-tab-pane label="奖惩规则" name="ecmanager">
      <EcManager></EcManager>
    </el-tab-pane>
    <el-tab-pane label="权限组" name="permissmanager">
      <PermissManager></PermissManager>
    </el-tab-pane>
  </el-tabs>

</template>

<script>
import DepManager from '../../components/sys/basic/DepManager'
import EcManager from '../../components/sys/basic/EcManager'
import JobLevelManager from '../../components/sys/basic/JobLevelManager'
import PermissManager from '../../components/sys/basic/PermissManager'
import PosManager from '../../components/sys/basic/PosManager'

export default {
  data() {
    return {
      activeName: 'permissmanager'
    };
  },
  methods: {},
  components: {
    DepManager,
    EcManager,
    JobLevelManager,
    PermissManager,
    PosManager,
  }
};
</script>

<style scoped>

</style>