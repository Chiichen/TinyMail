<template>
  <el-container v-loading="loading">
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
            <div class="email-time">{{ email.time }}</div>
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
    <el-pagination
        v-model:current-page="currentPage4"
        page-size="10"
        background
        layout=" prev, pager, next, jumper,total"
        :total="pageTotal"
        @current-change="handleCurrentChange"
    />
  </el-container>
</template>

<script>
import emitter from '../event';
import email from './email.vue'
import axios from "axios";

export default {

  data() {
    return {
      emails: [
        // {
        //   id: 1,
        //   sender: 'John Doe',
        //   subject: 'Hello',
        //   time: 'three hours ago',
        //   attachments: ['Hello.text'],
        // },
      ],
      loading:true,

      components: {
        email,
      },
      currentPage4: 1,
      pageTotal: 10,

      htmlContent: "<h1>Hello, World!</h1>"//显示html的1
    };
  },
  created() {
    var formData = new FormData();
    formData.append("username", sessionStorage.getItem('username'))
    formData.append("serverusername", "3220497145@qq.com")
    formData.append("pagenum", 1)
    axios({
      method: 'get',
      url: '/api/api/mail/getinfo',
      // data:formData,
      params: {
        "username": sessionStorage.getItem('username'),
        "serverusername": sessionStorage.getItem('serverusername'),
        "pagenum": 1,
      }
    }).then(res => {
      console.log(res)
      console.log(res.data.data)
      for (let i = 0; i < res.data.data.length; i++) {
        var each=res.data.data[i]
        var temp = {
          id: i,
          sender: each.fromAddress,
          subject: each.subject,
          time: this.formDate(new Date(each.date)),
          attachments: ['Hello.text'],
        }
        this.emails.push(temp)
      }
      this.loading=false

    }).catch(res => {
      console.log(res)
    })
  },

  methods: {
    formDate(dateString){
      // const dateString = "Thu Jun 29 2023 18:08:41 GMT+0800 (中国标准时间)";
      const date = new Date(dateString);
      const year = date.getFullYear();
      const month = ("0" + (date.getMonth() + 1)).slice(-2);
      const day = ("0" + date.getDate()).slice(-2);
      const hours = ("0" + date.getHours()).slice(-2);
      const minutes = ("0" + date.getMinutes()).slice(-2);
      const seconds = ("0" + date.getSeconds()).slice(-2);
      const formattedDate = `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`;
      return formattedDate
    },
    handleCurrentChange(pageNo) {
      console.log(pageNo)
    },
    layoutReceiving() {
      emitter.emit('back', true);
    },
    goToEmailDetail(id) {
      console.log("进入详情页", id)
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

.email-info {
  flex: 1;
  display: flex;
  justify-content: space-between;
}

.email-sender {
  font-weight: bold;
  margin-bottom: 5px;
}

.email-time {
  font-size: 14px;
  color: #999;
  margin-bottom: 5px;
}

.email-subject {
  font-size: 16px;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

.searchForm {
  padding-top: 10px;
}
</style>
