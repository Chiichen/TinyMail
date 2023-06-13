<!-- 登录界面的绘制 -->
<template>
  <div class="loginBody" v-if="isLogin">

    <!-- 注册界面的绘制 -->
    <div class="registerDiv" v-if="!ACKLogin">
      <div class="registerHeader">
        <el-button bg text color="#AFEEEE" @click="returnLogin">返回</el-button>
      </div>
      <div class="register-content">
        <el-form :model="registerForm" label-width="100px" :rules="registerRules">
          <el-form-item prop="setUserName" label="用户名">
            <el-input  size="medium" type="text" v-model="registerForm.setUserName" style="width:200px"></el-input>
          </el-form-item>

          <el-form-item prop="setPassWord" label="密码">
            <el-input  size="medium" type="password" v-model="registerForm.setPassWord" style="width:200px" show-password></el-input>
          </el-form-item>

          <el-form-item label="验证码">
            
          </el-form-item>

          <!-- 在上述账号密码格式都正确的情况下才能确定的功能还没实现 -->
          <el-form-item>
            <el-button type="success" @click="overRegister">确认</el-button>
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
  </div>
  <router-view v-if="!isLogin"></router-view>
</template>

<script >
import axios from 'axios'
import {getCurrentInstance} from 'vue'
export default {
  name:"Login",


  data(){
    return{
      isLogin:true,
      base64Data:'',
      ACKLogin:true,

      loginForm:{
        userName:'',
        passWord:'',
        code:''
      },
      loginRules:{
        userName:[{ required: true, message: "请输入账号", trigger: "blur" },{
          min: 10,
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
        setUserName:'',
        setPassWord:'',
      },
      registerRules:{
        setUserName:[{required:true,message:"请输入所要注册的账号",trigger:"blur"},{
          min:10,
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
      })
    },
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
          username:this.loginform.userName,
          nickname:"",
          password:this.loginform.passWord,
          authorities:this.loginform.code
        }
        // proxy.axios.post({
        //   url: '/api/api/user/register',
        //   data: data}).then(res=>{
        //     console.log(res)
        // })
      // http://localhost:9000/api/verifyCode/image
        axios({
          method: 'post',
          url: '/api/api/user/login?username='+this.loginform.userName+"&password="+this.loginform.passWord+"&vc="+this.loginform.code,
        }).then(res=>{
          console.log(res)
          this.$router.push("/home")
          this.isLogin=false
        }).catch(res=>{
          console.log(res)
        })

    },
    registerButton(){
      this.ACKLogin=false;
      let reqData={
        username:this.loginform.userName,
        nickname:"",
        password:this.loginform.passWord,
        authorities:this.loginform.code
      }
      axios({
        method: 'post',
        url: '/api/api/user/register?username='+this.loginform.userName+"&password="+this.loginform.passWord+"&vc="+this.loginform.code,
      }).then(res=>{
        console.log(res)
      }).catch(res=>{
        console.log(res)
      })


    },
    returnLogin(){
      this.ACKLogin=true;
    },
    overRegister(){
      this.ACKLogin=true;
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
  padding-left: 7%;
  padding-top: 5%;
}

.register-content{
  padding-top: 30px;
  padding-left:30px
}


</style>

