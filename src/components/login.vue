<!-- 登录界面的绘制 -->
<template>
  <div class="loginBody">

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

          <el-form-item  >
            <el-button type="primary" @click="loginButton"  > 登录</el-button>
            <el-button type="success" @click="registerButton" plain> 注册</el-button>
          </el-form-item>


        </el-form>

      </div>

    </div>
  </div>
</template>

<script >
export default {
  name:"Login",


  data(){
    return{
      ACKLogin:true,

      loginForm:{
        userName:'',
        passWord:'',
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
  methods:{
    loginButton(){
      /* 添加提交的部分 */
    },
    registerButton(){
      this.ACKLogin=false;
    },
    returnLogin(){
      this.ACKLogin=true;
    },
    overRegister(){
      this.ACKLogin=true;
      /* 添加提交的部分 */
    },
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

