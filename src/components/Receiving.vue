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

    <el-pagination
        v-model:current-page="currentPage"
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
      currentPage: 1,
      pageTotal: 10,


    };
  },
  created() {
    // var formData = new FormData();
    // formData.append("username", sessionStorage.getItem('username'))
    // formData.append("serverusername", "3220497145@qq.com")
    // formData.append("pagenum", 1)
    // axios({
    //   method: 'get',
    //   url: '/api/api/mail/getinfo',
    //   // data:formData,
    //   params: {
    //     "username": sessionStorage.getItem('username'),
    //     "serverusername": sessionStorage.getItem('serverusername'),
    //     "pagenum": 1,
    //   }
    // }).then(res => {
    //   console.log(res)
    //   console.log(res.data.data)
    //   this.pageTotal=res.data.data.num
    //   for (let i = 0; i < res.data.data.maillist.length; i++) {
    //     var each=res.data.data.maillist[i]
    //     var temp = {
    //       id: i,
    //       sender: each.fromAddress,
    //       subject: each.subject,
    //       time: this.formDate(new Date(each.date)),
    //     }
    //     this.emails.push(temp)
    //   }
    //   this.loading=false
    //
    // }).catch(res => {
    //   console.log(res)
    // })

    this.getOnePageMail(1)


    //给切换邮箱绑定事件
    emitter.on('changeMail', this.getOnePageMail)
  },

  methods: {
    getOnePageMail(pageNo){
      this.loading=true
      this.emails=[]
      axios({
        method: 'get',
        url: '/api/api/mail/getinfo',
        // data:formData,
        params: {
          "username": sessionStorage.getItem('username'),
          "serverusername": sessionStorage.getItem('serverusername'),
          "pagenum": pageNo,
        }
      }).then(res => {
        console.log(res)
        console.log(res.data.data)
        this.pageTotal=res.data.data.num
        for (let i = 0; i < res.data.data.maillist.length; i++) {
          var each=res.data.data.maillist[i]
          var temp = {
            id: i,
            sender: each.fromAddress,
            subject: each.subject,
            time: this.formDate(each.date),
          }
          this.emails.push(temp)
        }
        this.loading=false

      }).catch(res => {
        console.log(res)
      })
    },
    formDate(dateString) {
      // const dateString = "Thu Jun 29 2023 18:08:41 GMT+0800 (中国标准时间)";
      const date = new Date(dateString);
      const year = date.getFullYear();
      const month = ("0" + (date.getMonth() + 1)).slice(-2);
      const day = ("0" + date.getDate()).slice(-2);
      const hours = ("0" + date.getHours()).slice(-2);
      const minutes = ("0" + date.getMinutes()).slice(-2);
      const seconds = ("0" + date.getSeconds()).slice(-2);
      const formattedDate = `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`;
      console.log(formattedDate)
      if (formattedDate === 'NaN-aN-aN aN:aN:aN') {
        const datetimeStr=dateString
        const monthMap = {
          '1月': '01',
          '2月': '02',
          '3月': '03',
          '4月': '04',
          '5月': '05',
          '6月': '06',
          '7月': '07',
          '8月': '08',
          '9月': '09',
          '10月': '10',
          '11月': '11',
          '12月': '12'
        };
        const datetimeArr = datetimeStr.split(' ');
        datetimeArr[2] = monthMap[datetimeArr[2]];

// 将日期时间字符串转换为Date对象
        const datetime = new Date(datetimeArr.join(' '));

// 将Date对象格式化为标准日期时间格式
        const year = datetime.getFullYear();
        const month = ('0' + (datetime.getMonth() + 1)).slice(-2);
        const day = ('0' + datetime.getDate()).slice(-2);
        const hour = ('0' + datetime.getHours()).slice(-2);
        const minute = ('0' + datetime.getMinutes()).slice(-2);
        const second = ('0' + datetime.getSeconds()).slice(-2);
        //const formattedDatetime = `${year}-${month}-${day} ${hour}:${minute}:${second}`;
        const formattedDatetime = `${year}-${day}-${month} ${hour}:${minute}:${second}`;

        console.log(formattedDatetime); // 输出：2023-07-04 19:14:41
        return formattedDatetime


      }
      return formattedDate
    },
    handleCurrentChange(pageNo) {
      console.log(pageNo)
      this.getOnePageMail(pageNo)

    },
    layoutReceiving() {
      emitter.emit('back', true);
    },
    goToEmailDetail(id) {
      //根据当前页面得序号  和id获取
      console.log(this.currentPage)
      console.log("进入详情页", id)
      var index=(this.currentPage-1)*10+id
      // this.$router.push(`/email/${id}`);
      emitter.emit('toRouter', `/email/${index}`);
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
