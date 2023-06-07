<template>
  <div class="loginBody">
    <div class="loginDiv">
      <div class="logo">
        <img src="img/logo.png" alt="Logo" class="logo-img">
      </div>

      <div class="login-content">



        <el-form  :model="loginform" :rules="rules" ref="loginForms" label-width="100px">

          <el-form-item  prop="userName"  >
            <el-input size="medium" type="text" v-model="loginform.userName" placeholder="用户名" style="width:200px" autocomplete="false" clearable></el-input>
          </el-form-item>

          <el-form-item  prop="passWord"  >
            <el-input  type="password" v-model="loginform.passWord" placeholder="密码"  style="width:200px" show-password autocomplete="false" clearable></el-input>
          </el-form-item>

          <el-form-item  >
            <el-button type="primary" @click="submitButton" > 登录</el-button>
          </el-form-item>


        </el-form>

      </div>

    </div>
  </div>
</template>

<script >

export default {
  name:"login",


  data(){
    return{
      loginform:{
        userName:'',
        passWord:'',
      },
      rules:{
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
      }
    }
  },
  methods:{
    submitButton(){

    }
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
  background: white;
  border-radius: 5%;

}

.login-content {
  width: 400px;
  height: 250px;
  position: absolute;
  top: 100px;
  left: 25px;
}




</style>

