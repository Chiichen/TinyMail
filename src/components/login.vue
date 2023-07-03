<!-- 登录界面的绘制 -->
<template>
  <div class="loginBody" v-if="isLogin">

    <!-- 注册界面的绘制 -->
    <div class="registerDiv" v-if="!ACKLogin">
      <div class="registerHeader">
        <el-button bg text color="#AFEEEE" @click="returnLogin" class="outButton"><el-icon><Back /></el-icon>返回</el-button>
        <img src="img/logo.png" alt="Logo" class="logo-RE">
      </div>
      <div class="register-content">
        <el-form :model="registerForm" label-width="100px" :rules="registerRules">
          <el-form-item prop="setNickName" label="昵称">
            <el-input size="medium" type="text" v-model="registerForm.setNickName" style="width:200px"></el-input>
          </el-form-item>

          <el-form-item prop="setUserName" label="用户名">
            <el-input  size="medium" type="text" v-model="registerForm.setUserName" style="width:200px"></el-input>
          </el-form-item>

          <el-form-item prop="setPassWord" label="密码">
            <el-input  size="medium" type="password" v-model="registerForm.setPassWord" style="width:200px" show-password></el-input>
          </el-form-item>

          <el-form-item  prop="REcode" label="验证码" >
            <el-input  type="text" v-model="registerForm.REcode"   style="width:100px"  autocomplete="false" ></el-input>
            <el-image :src="imageDataUrl" alt="JPEG 图片" @click="clickImg"></el-image>
          </el-form-item>


          <el-form-item>
            <el-button type="success" @click="overRegister" >确认</el-button>
          </el-form-item>

        </el-form>

      </div>

    </div>

    <!-- 登录界面的绘制 -->
    <div class="loginDiv" v-if="ACKLogin">
      <div class="logo">
        <img src="img/logo.png" alt="Logo" class="logo-img">
      </div>

      <div class="login-content">



        <el-form  :model="loginForm" :rules="loginRules" ref="loginForms" label-width="100px">

          <el-form-item  prop="userName"  >
            <el-input size="medium" type="text" v-model="loginForm.userName" placeholder="用户名" style="width:200px" autocomplete="false" clearable></el-input>
          </el-form-item>

          <el-form-item  prop="passWord"  >
            <el-input  type="password" v-model="loginForm.passWord" placeholder="密码"  style="width:200px" show-password autocomplete="false" clearable></el-input>
          </el-form-item>

          <el-form-item  prop="code"  >
            <el-input  type="text" v-model="loginForm.code" placeholder="验证码"  style="width:100px"  autocomplete="false" clearable></el-input>
            <el-image :src="imageDataUrl" alt="JPEG 图片" @click="clickImg"></el-image>
          </el-form-item>

          <el-form-item  >
            <el-button type="primary" @click="loginButton"  > 登录</el-button>
            <el-button type="success" @click="registerButton" plain> 注册</el-button>
          </el-form-item>


        </el-form>

      </div>

    </div>

<!--    <el-alert :title="$GlobalErr" type="error" effect="dark" :description="desc" v-if="err!==''" @close="closeErr"/>-->
  </div>
  <router-view v-if="!isLogin"></router-view>



</template>

<script >
import axios from 'axios'
import mobileTip from "./mobileTip.vue";
export default {
  name:"Login",
  components: {mobileTip},
  data(){
    return{
      isLogin:true,
      base64Data:'',
      ACKLogin:true,
      // err:"",//错误的信息
      // desc:"",//错误的详细信息

      loginForm:{
        userName:'',
        passWord:'',
        code:''
      },
      loginRules:{
        userName:[{ required: true, message: "请输入账号", trigger: "blur" },{
          min: 1,
          max: 20,
          message: "账号长度应为10到20位",
          trigger: 'blur'
        }],
        passWord:[{ required: true, message: "请输入密码", trigger: "blur" },{
          min:6,
          max:20,
          message:"密码长度应为6到20位",
          trigger:'blur'
        }],
      },
      registerForm:{
        setNickName:'',
        setUserName:'',
        setPassWord:'',
        REcode:'',

      },
      registerRules:{
        setNickName:[{required:true,message:"请输入所要注册的账号昵称",trigger:"blur"}],
        setUserName:[{required:true,message:"请输入所要注册的账号用户名",trigger:"blur"},{
          min:1,
          max:20,
          message:"账号长度应为10到20位",
          trigger:'blur'
        }],
        setPassWord:[{ required: true, message: "请输入所要注册的密码", trigger: "blur" },{
          pattern:/^(?![\d]+$)(?![a-zA-Z]+$)(?![^\da-zA-Z]+$)([^\u4e00-\u9fa5\s]){6,20}$/,
          message:"密码长度应为6到20位,同时需包含字母、符号与数字中的至少两种",
          trigger:'blur'
        }],



      },

    }
  },
  computed: {
  imageDataUrl() {
    return `data:image/jpeg;base64,${this.base64Data}`;
  }
  },
  methods:{
    clickImg(){
      axios({
        method: 'get',
        url: '/api/api/verifyCode/base64'
      }).then(res=>{
        console.log(res)
        this.base64Data=res.data.msg
      }).catch(res=>{
        console.log(res)
        // this.err=res.data.msg
        // this.$GlobalErr=res.response.data.msg
        // this.$GlobalDesc=res.response.data.data
      })
    },
    // closeErr(){
    //   console.log("")
    //   this.err=""
    //   this.desc=""
    // },
    loginButton(){
      /*
    post常用的请求数据（data）格式有两种：
    （1）applicition/json
    （2）form-data 表单提交（图片上传，文件上传）
     */

      //第一种写法叫做post别名请求方法
      // http://localhost:8080/static/data.json?id=1
      // applicition/json 请求
      // let data = {
      //   id: 1
      // }
      // axios.post('../../static/data.json', data)
      //     .then((res) => {
      //       console.log('数据：', res);
      //     })
      //第二种写法
      // axios({
      //   method: 'post',
      //   url: '../../static/data.json',
      //   data: data,
      // }).then((res) => {
      //   console.log('数据：', res)
      // })
      // form-data 请求
      // let formData = new FormData()
      // for (let key in data) {
      //   formData.append(key, data[key])
      // }
      // axios.post('../../static/data.json', formData)
      //     .then((res) => {
      //       console.log('数据：', res);
      //     })


      // console.log(proxy);
        let reqData={
          username:this.loginForm.userName,
          nickname:"",
          password:this.loginForm.passWord,
          authorities:this.loginForm.code
        }
        // proxy.axios.post({
        //   url: '/api/api/user/register',
        //   data: data}).then(res=>{
        //     console.log(res)
        // })
      // http://localhost:9000/api/verifyCode/image
        axios({
          method: 'post',
          url: '/api/api/user/login?username='+this.loginForm.userName+"&password="+this.loginForm.passWord+"&vc="+this.loginForm.code,
        }).then(res=>{
          console.log(res)
          sessionStorage.setItem("username",this.loginForm.userName)
          //获取邮箱信息 并保存






          this.$router.push("/home")
          this.isLogin=false
        }).catch(res=>{
          console.log(res)
          //todo 提示错误
          // this.err=res.response.data.msg
          // this.desc=res.response.data.data
          // this.$GlobalErr=res.response.data.msg
          // this.$GlobalDesc=res.response.data.data
           console.log(this)
          this.$tips({
            // tip:res.response.data.msg,
            // tipDetail:res.response.data.data,
            tip:"登录失败",
            tipDetail:"验证码不正确，请重新输入",
            type: 'error'
          })
        })


    },
    registerButton(){
      this.ACKLogin=false;
      // let reqData={
      //   username:this.loginForm.userName,
      //   nickname:"",
      //   password:this.loginForm.passWord,
      //   authorities:this.loginForm.code
      // }
      // axios({
      //   method: 'post',
      //   url: '/api/api/user/register?username='+this.loginForm.userName+"&password="+this.loginForm.passWord+"&vc="+this.loginForm.code,
      // }).then(res=>{
      //   console.log(res)
      // }).catch(res=>{
      //   console.log(res)
      // })


    },
    returnLogin(){
      this.ACKLogin=true;
    },
    isDisabled(){

    },
    overRegister(){
      //注册按钮  注册成功要提示注册成功 并返回登录界面
      console.log("点击注册确定")
      // let reqData={
      //   username:this.loginForm.userName,
      //   nickname:"",
      //   password:this.loginForm.passWord,
      //   authorities:this.loginForm.code
      // }

      var formData = new FormData();
      console.log(this.registerForm)
      formData.append("username",this.registerForm.setUserName)
      formData.append("nickname",this.registerForm.setNickName)
      formData.append("password",this.registerForm.setPassWord)
      formData.append("vc",this.registerForm.REcode)
      axios({
        method: 'post',
        url: '/api/api/user/register',
        data:formData,
      }).then(res=>{
        console.log(res)
        this.ACKLogin=true;
      }).catch(res=>{
        console.log(res)
        //todo 提示错误
        this.$GlobalErr=res.response.data.msg
        this.$GlobalDesc=res.response.data.data
      })

      /* 添加提交的部分 */
    },
  },
  mounted() {
    axios({
      method: 'get',
      url: '/api/api/verifyCode/base64',
    }).then(res=>{
      console.log(res)
      this.base64Data=res.data.msg
    }).catch(res=>{
      console.log(res)
    })
  }
};

</script>d

<style scoped >

.el-alert {
  margin: 20px 0 0;

  position: absolute;
  top: 1%;
  left: 50%;
  /*margin-top: -200px;*/
  margin-left: -250px;
  width: 450px;
  height: 50px;
}
.logo {
  text-align: center;
  margin-top: 20px;
}

/* 调整Logo图片的大小 */
.logo-img {
  width: 280px; /* 设置图片宽度 */
  height: auto; /* 根据宽度等比缩放高度 */
}

/* 注意图片的路径 */
.loginBody {
  background-image: url(img/loginBackground.png);
  background-size: 100% 100%;
  position: fixed;
  width: 100%;
  height: 100%;
}
.loginDiv {
  position: absolute;
  top: 50%;
  left: 50%;
  margin-top: -200px;
  margin-left: -250px;
  width: 450px;
  height: 330px;
  background: rgb(255, 255, 255);
  border-radius: 5%;
  opacity: 80%;
}

.login-content {
  width: 400px;
  height: 250px;
  position: absolute;
  top: 100px;
  left: 25px;
}

.registerDiv{
  position: absolute;
  top: 50%;
  left: 50%;
  margin-top: -200px;
  margin-left: -250px;
  width: 450px;
  height: 330px;
  background: rgb(255, 255, 255);
  border-radius: 5%;
  opacity: 80%;
}

.registerHeader{
  display: flex;
  padding-left: 7%;
  padding-top: 5%;
}

.outButton{
  margin-right:30px;
}

.logo-RE{
  height: 40px;
  width: 200px;
}
.register-content{
  padding-top: 15px;
  padding-left:30px
}



</style>

