<!-- 邮箱主页的绘制 -->
<template>
  <el-container class="home-container">
    <el-header><!-- 顶部区域 -->
      <div>
        <span>TinyMail</span>
      </div>

      <!-- 设置按钮 -->
      <div class="headerButton">
        <el-dropdown trigger="click">
          <el-button class="setButton" type="primary">
            <el-icon>
              <Setting/>
            </el-icon>
            设置
            <el-icon class="el-icon--right">
              <arrow-down/>
            </el-icon>
          </el-button>
          <template #dropdown>


            <el-dropdown-menu>

              <el-dropdown-item>
                <div
                    style="padding-left:20px;text-align:center;background-image: linear-gradient(to right,#94a1aa,#FFFFFF); color:black">
                  <span style="font-size:20px;">{{ NAME }}</span>
                </div>
              </el-dropdown-item>

              <el-dropdown-item v-for="item in items" :key="item.id" divided @click="changeMail(item.email)">
                <el-icon v-if="whichMail===item.email">
                  <Star/>
                </el-icon>
                <div style="text-align:center;color:black;font-size: 13px;font-family: '微软雅黑';">
                  <span>{{ item.email }}</span>
                </div>

              </el-dropdown-item>

              <el-dropdown-item @click="want_set" divided style="text-align:center;font-weight: bold;">
                <el-icon style="font-size:15px;margin-right: 9px;">
                  <CirclePlusFilled/>
                </el-icon>
                <span style="font-size:15px;">添加邮箱</span>
              </el-dropdown-item>

              <el-dropdown-item divided style="text-align:center;font-weight: bold;" @click="want_modify">
                <el-icon style="font-size:15px;margin-right: 9px;">
                  <House/>
                </el-icon>
                <span style="font-size:15px;">个人中心</span>
              </el-dropdown-item>


            </el-dropdown-menu>
          </template>
        </el-dropdown>

        <!-- 退出按钮 -->
        <el-button type="primary" @click="logout">
          <el-icon>
            <Edit/>
          </el-icon>
          退出
        </el-button>
      </div>

    </el-header>
    <!-- 主界面 -->
    <el-container>
      <!-- 控制区域 -->
      <el-aside width="200px">
        <el-menu
            background-color="#3a3f4f"
            text-color="#fff"
            active-text-color="#ffd04b">
          <el-menu-item index="1" @click="writeLetter">
            <i class="el-icon-menu"></i>
            <span><el-icon><EditPen/></el-icon>写信</span>
          </el-menu-item>
          <el-menu-item index="2" @click="receiveLetter">
            <i class="el-icon-setting"></i>
            <span><el-icon><Message/></el-icon>收信</span>
          </el-menu-item>
        </el-menu>
      </el-aside>
      <!-- 展示区域 -->
      <el-main>

        <!-- welcome的默认显示位置 -->
        <div class="welcome-div" v-if="isWelcome">
          <img src="/img/welcome.png" alt="Welcome">
          <p>请点击左侧按钮，亲</p>
        </div>

        <!-- 写信与收信按钮的实现 -->
        <router-view v-if="!isWelcome"></router-view>

      </el-main>
    </el-container>
  </el-container>
</template>

<script>
import emitter from '../event'
import axios from "axios";

export default {
  name: "Home",

  data() {
    return {
      isWelcome: true,
      // {id:1,email:'HUAZI@qq.com'},
      // {id:2,email:'ZIHUA@163.com'},
      items: [],
      NAME: '',
      whichMail:'',

    }


  },
  methods: {
    back(isWelcome) {
      console.log("回到默认界面", isWelcome)
      this.isWelcome = isWelcome
      console.log(this.isWelcome)
    },
    changeMail(mail) {
      console.log(mail)
      sessionStorage.setItem('serverusername',mail)
      this.whichMail=mail
    },
    toRouter(route) {
      console.log(route)
      this.$router.push(route)
      this.isWelcome = false
      emitter.on('back', this.back)
    },
    writeLetter() {
      this.$router.push("/home/Writing")
      this.isWelcome = false
      emitter.on('back', this.back)

    },
    receiveLetter() {
      this.$router.push("/home/Receiving")
      this.isWelcome = false
      emitter.on('back', this.back)


      //设置全局事件总线用于路由跳转
      emitter.on('toRouter', this.toRouter)
    },

    /* 返回登录界面 */
    logout() {
      this.$router.push("/login");
    },

    /*  进入添加邮箱界面 */
    want_set() {
      this.$router.push("/set");
    },

    /* 进入个人中心页面 */
    want_modify() {
      this.$router.push("/home/modify")
      this.isWelcome = false
      emitter.on('back', this.back)
    },


    /* 获取已添加邮箱的数据 */
    getEmail() {

    },
  },
  beforeCreate() {

    var username = sessionStorage.getItem("username");
    this.NAME = username;
    console.log("获取用户设置", username)
    var formData = new FormData();
    formData.append("username", username)
    axios({
      method: 'get',
      url: '/api/api/user/getsetting',
      params: {
        "username": username
      }
    }).then(res => {
      console.log(res)
      for (let i = 0; i < res.data.data.length; i++) {
        var temp = {
          id: i,
          email: res.data.data[i].serverusername
        }
        this.items.push(temp)
        if (i === 0) {
          sessionStorage.setItem('serverusername', res.data.data[i].serverusername)
          this.whichMail=res.data.data[i].serverusername
        }
      }
    }).catch(res => {
      console.log(res)
    })

    axios({
      method: 'get',
      url: '/api/api/user/getnickname',
      params: {
        "username": username
      }
    }).then(res => {
      console.log(res)
      console.log(res.data.data.nickname)
      sessionStorage.setItem('nickname', res.data.data.nickname)
      this.NAME = res.data.data.nickname;
    })


  },
  created() {
    // var username = sessionStorage.getItem("username");

  },
  computed: {}
};
</script>

<style scoped>


.home-container {
  font-family: "微软雅黑";
  height: 100%;
}

.el-header {
  font-family: "宋体";
  background-color: rgb(119, 200, 186);
  display: flex;
  justify-content: space-between;
  padding-left: 30px;
  align-items: center;
  color: #fff;
  font-size: 40px;
}

.headerButton {
  display: flex;

}

.setButton {
  margin-right: 30px;
}


.el-aside {
  background-color: #3a3f4f;
}

.el-main {
  background-color: white;
}

/* welcome的样式 */
.welcome-div {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  width: 300px;
  height: 300px;
  padding-left: 500px;
  padding-top: 150px;
}

.welcome-div img {
  height: 80%;
  display: block;
  margin: auto;
}

.welcome-div p {
  text-align: center;
  font-family: "微软雅黑";
}

</style>
