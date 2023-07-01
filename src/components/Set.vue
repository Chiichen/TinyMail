<template>
  <div class="setBody">

        <div class="setQQmail" @click="SelectQQmail" v-if="help==0">
            <img src="/img/qqmail_logo.png" alt="QQLogo" class="setQQLogo">

    </div>

        <div class="set163mail" @click="Select163mail" v-if="help==0">
            <img src="/img/163mail_logo.png" alt="163Logo" class="set163Logo">
        </div>

        <div class="setGmail" @click="SelectGmail" v-if="help==0">
            <img src="/img/gmail_logo.png" alt="GoogleLogo" class="setGoogleLogo">
            <span>Gmail</span>
        </div>

        <!-- 设置选择QQ邮箱添加或者默认时的页面 -->
<!--    v-if="select==1&&help==0"-->
        <div class="setQQMain" >
            <div class="setHeader">
                <el-button class="cancelButton" text style="font-size: large;" @click="return_back"><b>取消</b></el-button>
                <el-button text style="font-size: large;" @click="want_help"><b>帮助</b></el-button>
            </div>

      <div :class="{setQQLogo:select===1,set163Logo:select===2,setGoogleLogo:select===3}">
        <img :src="imgArr[select-1]" alt="Logo">
      </div>

      <div class="setContent">
        <input :model="addmail" :placeholder='mailArr[select-1]' type="text" style="width:250px" size="large"
               class="inputForm"/>
        <el-input size="medium" type="password" v-model="password" style="width:200px" placeholder='邮箱密码/授权码'
                  show-password></el-input>
        <div style="margin-top: 20px">
          <el-radio-group v-model="radio" size="large">
            <el-radio-button label="SMTP"/>
            <el-radio-button label="IMAP"/>
            <el-radio-button label="其他"/>
          </el-radio-group>
        </div>
        <el-form-item>
          <el-button type="primary" size="large" class="addButton" @click="add_mail()">
            <el-icon>
              <Pointer/>
            </el-icon>
            添加邮箱
          </el-button>
        </el-form-item>

        <el-form-item>
          <img src="img/set-img.png" alt="Logo" class="setImg">
        </el-form-item>

      </div>
    </div>

    <!--        &lt;!&ndash; 设置选择163邮箱时的页面 &ndash;&gt;-->
    <!--        <div class="set163Main" v-if="select==2">-->
    <!--            <div class="setHeader">-->
    <!--                <el-button class="cancelButton" text style="font-size: large;" @click="return_back"><b>取消</b></el-button>-->
    <!--                <el-button text style="font-size: large;" @click="want_help"><b>帮助</b></el-button>-->
    <!--            </div>-->

    <!--            <div class="show163Logo" >-->
    <!--                <img src="img/163mail_logo.png" alt="Logo" style="height:30px;width: 200px;">-->
    <!--            </div>-->

    <!--            <div class="setContent">-->
    <!--                    <input :model="addmail" placeholder="输入163邮箱地址" type="text" style="width:250px" size="large" class="inputForm"/>-->

    <!--                <el-form-item>-->
    <!--                    <el-button type="primary" size="large" class="addButton" @click="add_163mail()"><el-icon><Pointer /></el-icon>添加邮箱</el-button>-->
    <!--                </el-form-item>-->

    <!--                <el-form-item>-->
    <!--                    <img src="img/set-img.png" alt="Logo" class="setImg">-->
    <!--                </el-form-item>-->

    <!--            </div>-->
    <!--        </div>-->

    <!--        &lt;!&ndash; 设置选择谷歌邮箱的页面 &ndash;&gt;-->
    <!--        <div class="setGMain" v-if="select==3">-->
    <!--            <div class="setHeader">-->
    <!--                <el-button class="cancelButton" text style="font-size: large;" @click="return_back"><b>取消</b></el-button>-->
    <!--                <el-button text style="font-size: large;" @click="want_help"><b>帮助</b></el-button>-->
    <!--            </div>-->

    <!--            <div class="showGLogo" >-->
    <!--                <img src="img/gmail_logo.png" alt="Logo" style="height:75px;padding-left: 18%;">-->
    <!--            </div>-->
    <!--            <div class="setContent">-->
    <!--                    <input :model="addmail" placeholder="输入谷歌邮箱地址" type="text" style="width:250px" size="large" class="inputForm"/>-->

    <!--                <el-form-item>-->
    <!--                    <el-button type="primary" size="large" class="addButton" @click="add_Gmail()"><el-icon><Pointer /></el-icon>添加邮箱</el-button>-->
    <!--                </el-form-item>-->

    <!--                <el-form-item>-->
    <!--                    <img src="img/set-img.png" alt="Logo" class="setImg">-->
    <!--                </el-form-item>-->

    <!--            </div>-->
    <!--        </div>-->
  </div>
</template>

<script>

import axios from "axios";

export default {
  data() {
    return {
      imgArr: ['img/qqmail_logo.png', 'img/163mail_logo.png', 'img/gmail_logo.png'],
      mailArr: ['输入QQ邮箱地址', "输入163邮箱地址", "输入谷歌邮箱地址"],
      addmail: '',
      password: "",
      select: 1,
      radio:'',
		help:0,
    }
  },
  methods: {
    /* 记录用户输入的不同类型邮箱 */
    add_mail() {
      console.log("添加账号")
      var formData = new FormData();
      console.log(this.radio)

      if(this.radio=='SMTP'&&this.select==1){
        formData.append("servername","smtp.qq.com")//smtp.qq.com
        formData.append("type",0)//smtp 0 ||imap  1
      }
      var username = sessionStorage.getItem("username");
      formData.append("username",username)
      formData.append("serverusername",this.addmail)//邮箱地址
      formData.append("serverpassword",this.password)//
      axios({
        method: 'post',
        url: '/api/api/user/addsetting',
        data:formData
      }).then(res=>{
        console.log(res)

      }).catch(res=>{
        console.log(res)
        //todo 提示错误
      })
      this.$router.push("/home");
    },
    add_QQmail() {
            this.help=1;
      this.$router.push("/home");

    },
    ACKHelp(){
      this.select=1;
      this.help=0;
    },
    add_163mail() {

      this.$router.push("/home");
    },
    add_Gmail() {

      this.$router.push("/home");
    },
    return_back() {
      this.$router.push("/home");
    },
    want_help() {

    },
    SelectQQmail() {
      this.select = 1;
    },
    Select163mail() {
      this.select = 2;
    },
    SelectGmail() {
      this.select = 3;
    },
  },
}
</script>
<style scoped>
.setBody {
  background-image: url(img/setBackground.gif);
  background-size: 100% 100%;
  position: fixed;
  width: 100%;
  height: 100%;
}

.setQQmail {
  position: absolute;
  top: 40%;
  left: 40%;
  border: 8px solid rgb(76, 81, 237);
  border-radius: 50%;
  background-color: rgba(226, 232, 248, 0.75);
  margin-top: -200px;
  margin-left: -250px;
  width: 260px;
  height: 150px;
}

.setQQLogo {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
}

.set163mail {
  position: absolute;
  top: 60%;
  left: 20%;
  border: 8px solid rgb(76, 81, 237);
  border-radius: 50%;
  background-color: rgba(226, 232, 248, 0.75);
  margin-top: -200px;
  margin-left: -250px;
  width: 260px;
  height: 150px;
}

.set163Logo {
  position: absolute;
  top: 50%;
  left: 50%;
  height: 25%;
  transform: translate(-50%, -50%);
}

.setGmail {
  position: absolute;
  top: 80%;
  left: 40%;
  border: 8px solid rgb(76, 81, 237);
  border-radius: 50%;
  background-color: rgba(226, 232, 248, 0.75);
  margin-top: -200px;
  margin-left: -250px;
  width: 260px;
  height: 150px;
}

.setGoogleLogo {
  position: absolute;
  top: 35%;
  left: 50%;
  height: 35%;
  transform: translate(-50%, -50%);
  height: 75px;
  padding-left: 18%;
}

.setGmail span {
  position: absolute;
  top: 60%;
  left: 50%;
  transform: translate(-50%, -50%);
  font-size: 38px;
  font-weight: bold;
  letter-spacing: 3px;
  font-family: "DejaVu Sans Mono";
  color: rgb(208, 94, 94);
}

.setQQMain {
  position: absolute;
  top: 40%;
  left: 70%;
  margin-top: -200px;
  margin-left: -250px;
  width: 350px;
  height: 500px;
  background: rgb(255, 255, 255);
  border-radius: 5%;
  opacity: 90%;
}

.set163Main {
  position: absolute;
  top: 40%;
  left: 70%;
  margin-top: -200px;
  margin-left: -250px;
  width: 350px;
  height: 500px;
  background: rgb(255, 255, 255);
  border-radius: 5%;
  opacity: 90%;
}

.setGMain {
  position: absolute;
  top: 40%;
  left: 70%;
  margin-top: -200px;
  margin-left: -250px;
  width: 350px;
  height: 500px;
  background: rgb(255, 255, 255);
  border-radius: 5%;
  opacity: 90%;
}

.setHeader {

  display: flex;
  padding-left: 4%;
  padding-top: 5%;
  opacity: 100%;
}

.cancelButton {
  margin-right: 180px;
}

.showQQLogo {
  padding-top: 10%;
  padding-left: 80px;
  height: 10px;

}

.show163Logo {
  padding-top: 10%;
  padding-left: 80px;
  /*height: 10px;*/
  height: 30px;
  width: 200px;

}

.showGLogo {
  padding-top: 10%;
  padding-left: 80px;
  height: 10px;
}

.setContent {
  padding-top: 100px;
  padding-left: 15%;
  opacity: 80%;
}

.inputForm {
  border: none;
  outline: none;
  border-bottom: 2px solid #484747;
  margin-bottom: 40px;
}

input::placeholder {
  font-size: large;
}

.addButton {
  width: 255px;

}

.set-img1{
    height: 100px;
    padding-left:70px;
    padding-top:30px;
}
.set-img2{
    height: 100px;
    padding-left:70px;
    padding-top:30px;
}
.set-img3{
    height: 100px;
    padding-left:70px;
    padding-top:30px;
}

.helpMain{
    position: absolute;
  top: 40%;
  left: 50%;
  margin-top: -200px;
  margin-left: -250px;
  width: 450px;
  height: 500px;
  border-radius: 5%;
  opacity: 90%;
  background:-webkit-linear-gradient(bottom,lightblue,white);
}

.helpContent{
    padding-top: 10%;
    padding-left: 10%;
    padding-right: 10%;
}

.helpButton{
    padding-left: 35%;
}
</style>
