<template>
  <el-container>
    <el-header>
      <el-form>
        <el-button type="primary" @click="layoutReceiving">退出</el-button>
      </el-form>
    </el-header>
    <el-main>
      <div class="email-list">
        <div v-for="email in emails" :key="email.id" class="email-item">
          <div class="email-sender">{{ email.sender }}</div>
          <div class="email-subject">{{ email.subject }}</div>
        </div>
      </div>
    </el-main>
    <el-footer>
      <el-form class="searchForm">
        <el-input size="medium" placeholder="搜索邮件" v-model="searchKeyword" clearable></el-input>
      </el-form>
    </el-footer>
<!--    <iframe src="https://www.w3school.com.cn/jsref/dom_obj_frame.asp" style="height: 1000px"></iframe>-->
    <div v-html="htmlContent"></div>
  </el-container>
</template>

<script>
import emitter from '../event';

export default {
  data() {
    return {
      emails: [{
        sender:'xwb',
        time:'1010101',
        content:'123123',
      }],
      searchKeyword: '',
      htmlContent:"<h1>Hello, World!</h1>"//显示html的1
    };
  },

  methods: {
    layoutReceiving() {
      emitter.emit('back', "asdasd");
    },
  },
};
</script>

<style scoped>
.email-list {
  margin-top: 20px;
}

.email-item {
  border-bottom: 1px solid #ccc;
  padding: 10px;
}

.email-sender {
  font-weight: bold;
  margin-bottom: 5px;
}

.email-subject {
  font-size: 16px;
}

.searchForm {
  padding-top: 10px;
}
</style>
