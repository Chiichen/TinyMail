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
          <div class="email-info">
            <div class="email-sender">{{ email.sender }}</div>
            <div class="email-time">{{email.time}}</div>
          </div>
          <div class="email-subject">{{ email.subject }}</div>
          <el-button type="text" class="view-button" @click="goToEmailDetail(email.id)">详情</el-button>
        </div>
      </div>
    </el-main>
<!--    <el-footer>-->
<!--      <el-form class="searchForm">-->
<!--        <el-input size="medium" placeholder="搜索邮件" v-model="searchKeyword" clearable></el-input>-->
<!--      </el-form>-->
<!--    </el-footer>-->
<!--    <email :emails="emails"></email>-->
<!--    <iframe src="https://www.w3school.com.cn/jsref/dom_obj_frame.asp" style="height: 1000px"></iframe>-->
    <div v-html="htmlContent"></div>
  </el-container>
</template>

<script>
import emitter from '../event';
import email from './email.vue'

export default {
  data() {
    return {
      emails: [
        {
          id: 1,
          sender: 'John Doe',
          subject: 'Hello',
          time:'three hours ago',
          attachments:['Hello.text'],
        },
        {
          id: 3,
          sender: 'xwb',
          subject: 'Welcome',
          time:'four hours ago',
          attachments:['file.pdf'],
        },
      ],

      components:{
        email,
      },

      htmlContent:"<h1>Hello, World!</h1>"//显示html的1
    };
  },

  methods: {
    layoutReceiving() {
      emitter.emit('back', true);
    },
    goToEmailDetail(id){
      console.log("进入详情页",id)
      // this.$router.push(`/email/${id}`);
      emitter.emit('toRouter', `/email/${id}`);
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

.email-info{
  flex:1;
  display:flex;
  justify-content:space-between;
}

.email-sender {
  font-weight: bold;
  margin-bottom: 5px;
}

.email-time{
  font-size:14px;
  color:#999;
  margin-bottom: 5px;
}

.email-subject {
  font-size: 16px;
  white-space:nowrap;
  overflow:hidden;
  text-overflow:ellipsis;
}

.searchForm {
  padding-top: 10px;
}
</style>
