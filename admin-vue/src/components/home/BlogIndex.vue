<template>
  <div>
    <blog-header></blog-header>
    <hr/>
    <el-row class="container">
      <el-col :span="4" class="menu">
        <li class="el-menu-item is-active" style="">12321</li>
      </el-col>
    </el-row>
    <div>
      <el-progress type="circle" :percentage="0"></el-progress>
      <el-progress type="circle" :percentage="25"></el-progress>
      <el-progress type="circle" :percentage="100" status="success"></el-progress>
      <el-progress type="circle" :percentage="50" status="exception"></el-progress>
    </div>
    <hr/>
    <blog-footer></blog-footer>
  </div>
</template>
<script>
  import blogHeader from '@/components/common/BlogHeader.vue'
  import blogFooter from '@/components/common/BlogFooter.vue'

  export default {
    name: 'BlogIndex',
    // blogHeader/blogFooter组件给申明到components里面然后在template里面使用
    components: { blogHeader, blogFooter }
  }
</script>

<style scoped>

</style>
