<template>
    <el-container class="home-container">
        <el-header><!-- 顶部区域 -->
          <div>
          <span>TinyMail</span>
      </div>
  
      <el-dropdown trigger="click">
        <span class="el-dropdown-link">
          切换账号
          <i class="el-icon-arrow-down el-icon--right"></i>
        </span>
        <el-dropdown-menu slot="dropdown">
          <el-dropdown-item @click.native="">
            QQ邮箱
          </el-dropdown-item>
  
          <el-dropdown-item @click.native="">          
              163邮箱
          </el-dropdown-item>
  
          <el-dropdown-item @click.native="">
              谷歌邮箱
          </el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
      
      <el-button type="primary" @click="logout">退出</el-button>
        </el-header>
        <!-- 主界面 -->
        <el-container>
            <!-- 控制区域 -->
            <el-aside width="200px">
              <el-menu
            background-color="#3a3f4f"
            text-color="#fff"
            active-text-color="#ffd04b">         
            <el-menu-item index="1">
              <i class="el-icon-menu"></i>
              <span >写信</span>
            </el-menu-item>
            <el-menu-item index="2">
              <i class="el-icon-setting"></i>
              <span >收信</span>
            </el-menu-item>
          </el-menu>           
              </el-aside>
            <!-- 展示区域 -->
            <el-main>
  
            </el-main>
        </el-container>
    </el-container>
  </template>
  
  <script>
  export default {
    data(){
      return {
        
      }
      
    },
    methods:{
        logout(){
            
        }
    }
  };
  </script>
  
  <style  scoped>
  .home-container{
    font-family: "微软雅黑";
    height: 100%;
  }
  .el-header{
       background-color:rgb(119, 200, 186);
       display: flex; 
       justify-content: space-between;
       padding-left: 30px;
       align-items: center;
       color: #fff;
       font-size: 40px;
   }
    .el-aside{
       background-color: #3a3f4f;
   }
    .el-main{
       background-color: #eaedf1;
   }
   
  
   
  </style>
  