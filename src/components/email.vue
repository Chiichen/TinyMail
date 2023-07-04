<template>
  <el-container v-loading="loading">
    <el-header>
      <el-form>
        <el-button type="primary" @click="goBack">返回</el-button>
      </el-form>
    </el-header>
    <el-main>
      <!--      <div v-for="email in emails" :key="email.id">-->
      <!--        <div class="email-header">-->
      <!--          <div class="email-sender">发件人：{{ email.sender }}</div>-->
      <!--          <div class="email-time">时间：{{ email.time }}</div>-->
      <!--        </div>-->
      <!--        <div class="email-body">-->
      <!--          <div class="email-content">{{ email.subject }}</div>-->
      <!--          <div class="email-attachments">-->
      <!--            <el-button type="primary" @click="showAttachments" v-if="email.attachments.length > 0">-->
      <!--              附件 ({{ email.attachments.length }})-->
      <!--            </el-button>-->
      <!--          </div>-->
      <!--        </div>-->
      <!--      </div>-->

      <div class="email-header">
        <div class="email-sender">发件人：{{ sender }}</div>
        <div class="email-time">时间：{{ time }}</div>
      </div>
      <div class="email-body">
        <div class="email-content">{{ subject }}</div>
        <div class="email-attachments">
          <!--            <el-button type="primary" @click="showAttachments" v-if="email.attachments.length > 0">-->
          <!--              附件 ({{ email.attachments.length }})-->
          <!--            </el-button>-->
          <el-icon style="height: 7px ;top: 9px">
            <Document/>
          </el-icon>
          附件 {{ attachArr.length }} 个
          <el-collapse v-model="activeNames" @change="handleChange" v-for="(item,index) in attachArr" :key="index">
            <el-collapse-item :title="item" name="1">
              <el-button type="text" class="view-button" @click="downLoadFile(index)">下载</el-button>
            </el-collapse-item>
          </el-collapse>
          <!--            <el-descriptions title="附件" >-->
          <!--              <el-descriptions-item >-->
          <!--                <template #label>-->
          <!--                  <div class="cell-item">-->
          <!--                    <el-icon :style="iconStyle">-->
          <!--                      <user />-->
          <!--                    </el-icon>-->
          <!--                    附件1-->
          <!--                  </div>-->
          <!--                </template>-->
          <!--                asdsadas.txt-->
          <!--              </el-descriptions-item>-->
          <!--            </el-descriptions>-->
        </div>
      </div>
    </el-main>
  </el-container>
</template>

<script>
import emitter from '../event';
import axios from "axios";

export default {
  // props:{
  //   emails:{
  //     type:Array,
  //     required:true,
  //   }
  // },
  data() {

    return {
      activeNames: "附件",
      index: 1,
      attachArr: [],
      sender: "",
      subject: "",
      time: "",
      loading:true,
    }


  },
  created() {
    console.log(this.$route.params.id)
    // this.index=this.$route.params.id+1
    this.index = Number(this.$route.params.id) + 1
    console.log(this.index)


    axios({
      method: 'get',
      url: '/api/api/mail/getdetail',
      // data:formData,
      params: {
        "username": sessionStorage.getItem('username'),
        "serverusername": sessionStorage.getItem('serverusername'),
        "index": this.index,
      }
    }).then(res => {
      console.log(res)
      console.log(res.data.data.attachname)
      this.attachArr = res.data.data.attachname

      console.log(res.data.data.mail)
      var temp = res.data.data.mail
      this.sender = temp.fromAddress
      this.subject = temp.subject
      this.time = this.formDate(new Date(temp.date))

      this.loading=false

    }).catch(res => {
      console.log(res)
    })
  },
  methods: {
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
      return formattedDate
    },
    downLoadFile(fileIndex) {
      console.log(fileIndex)
      console.log(this.attachArr[fileIndex])
      // 我们使用 axios 对象创建了一个 GET 请求，并将其发送到图片的 URL。
      // 然后，我们设置响应类型为二进制流，以便下载图片。在请求成功后，我们将响应结果转换为 Blob 对象，并创建一个 URL 对象，
      // 将其赋值给一个链接元素的 href 属性，以便在浏览器中下载图片。最后，我们使用 click() 方法触发链接元素的点击事件，以启动下载操作。
      axios({
        url: '/api/api/mail/getAttachment',
        method: 'GET',
        responseType: 'blob', // 设置响应类型为二进制流
        params: {
          "username": sessionStorage.getItem('username'),
          "serverusername": sessionStorage.getItem('serverusername'),
          "index": this.index,
          "attindex":fileIndex+1,
        }
      })
      .then(response => {
        const url = window.URL.createObjectURL(new Blob([response.data]));
        const link = document.createElement('a');
        link.href = url;
        link.setAttribute('download', this.attachArr[fileIndex]);
        document.body.appendChild(link);
        link.click();
        this.$tips({
          // tip:res.response.data.msg,
          // tipDetail:res.response.data.data,
          tip:"提示",
          tipDetail:"附件下载成功",
          type: 'success'
        })
      })
      .catch(error => {
        console.error(error);
      });
      // axios({
      //   method: 'get',
      //   url: '/api/api/mail/getAttachment',
      //   // data:formData,
      //   params: {
      //     "username": sessionStorage.getItem('username'),
      //     "serverusername": sessionStorage.getItem('serverusername'),
      //     "index": this.index,
      //     "attindex":fileIndex,
      //   }
      // }).then(res => {
      //   console.log(res)
      //   console.log(res.data.data)
      //
      // }).catch(res => {
      //   console.log(res)
      // })
    },
    goBack() {

      this.$router.push("/home/Receiving");
      emitter.emit('back', false);
    },
    showAttachments() {

    },
  },
};
</script>

<style scoped>

.email-header {
  border-bottom: 1px solid #ccc;
  padding: 10px;
}

.email-sender {
  font-weight: bold;
  margin-bottom: 5px;
}

.email-time {
  font-size: 14px;
  color: #666;
}

.email-body {
  margin-top: 20px;
  padding: 10px;
}

.email-content {
  margin-bottom: 10px;
}

.email-attachments {
  margin-top: 20px;
  background: #ececec;
}

.cell-item {
  padding: 0 8px 6px 12px;
  background: #fff;
  _height: 60px;
  line-height: 140%;
}
</style>
