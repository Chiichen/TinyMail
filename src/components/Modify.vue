<!-- 未完成的任务：
1.点击确认后进行密码方面的验证
2.修改用户名后对应基本信息处对内容的修改 -->




<template>
   <!--  默认展示页面 -->
    <div class="container">
        <div class="title">
            <span style="padding-left: 1%;font-weight: bold;font-family:'微软雅黑';font-size: large;">基本信息</span>
        </div>
        <div class="divider"></div>
        <div class="message">
            <el-row :gutter="20">
    <el-col :span="2" :offset="1" class="firstSet"><div >昵称</div></el-col>
    <el-col :span="3" :offset="1" class="firstShow">李绮华</el-col>
    <el-col :span="2"><div><el-button text type="danger" size="larger" @click="modify_username">修改</el-button></div></el-col>
    <el-col :span="3" :offset="2" class="secondSet">联系电话<div ></div></el-col>
    <el-col :span="7" :offset="2" class="secondShow"><div >12345678901</div></el-col>
  </el-row>
  <div class="divider"></div>
  <el-row :gutter="20">
    <el-col :span="2" :offset="1" class="firstSet"><div >密码 </div></el-col>
    <el-col :span="2" :offset="1" class="firstShow"><div><el-button text type="danger" size="larger" @click="modify_password">修改</el-button></div></el-col>
    <el-col :span="3" :offset="5" class="secondSet">个人备注<div ></div></el-col>
    <el-col :span="7" :offset="2" class="secondShow"><div >暂无</div></el-col>
  </el-row>
        </div>
    </div>

    <!-- 修改用户名页面 -->
    <div  class="Modify" v-if="select==1">
        <div class="usernameMain">
            <div style="padding-top: 15%;margin-bottom: 70px;">
                <span style="font-size: 30px;color: rgb(0, 0, 0);padding-left: 30%;">修改昵称</span>
            </div>
            <div style="padding-left: 10%;">
                
                <input v-model="MODusername" placeholder="请输入修改后的昵称" type="text" style="width:300px;font-size: 30px;" size="larger" class="inputForm"/>
            </div>
            <el-form-item class="ButtonUS" >
            <el-button type="primary" @click="MODusernameOK"  > 确认</el-button>
            <el-button type="success" @click="cancel" style="margin-left: 60px;" > 取消</el-button>
          </el-form-item>
            
        </div>
    </div>

    <!-- 修改密码页面 -->
    <div  class="Modify" v-if="select==2">
        <div class="passwordMain">
            <div style="padding-top: 10%;margin-bottom:40px ;">
                <span style="font-size: 25px;color: rgb(0, 0, 0);padding-left: 35%;">修改密码</span>
            </div>
            <el-form style="padding-left: 10%;" :rules="modifyRules" :model="modifyForm">
                <el-form-item label="原密码：" >
            <el-input size="medium" type="password" v-model="modifyForm.originalPW" placeholder="请输入原密码" style="width:200px"  show-password clearable></el-input>
          </el-form-item>

          <el-form-item  label="新密码："  >
            <el-input size="medium" type="password" v-model="modifyForm.newPWA" placeholder="请输入新密码"  style="width:200px" show-password  clearable></el-input>
          </el-form-item>

          <el-form-item  label="新密码："  >
            <el-input size="medium" type="password" v-model="modifyForm.newPWB" placeholder="请再次输入新密码"  style="width:200px" show-password  clearable></el-input>
          </el-form-item>

            </el-form>

            <el-form-item style="padding-left: 25%;padding-top: 20px;" >
            <el-button type="primary" @click="MODpasswordOK" style="margin-right: 30px;" > 确认</el-button>
            <el-button type="success" @click="cancel" > 取消</el-button>
          </el-form-item>
        </div>
    </div>
</template>

<script>

import emitter from '../event';
import axios from "axios";

export default{
    data () {
        return {
            select:0,
            MODusername:'',
            modifyForm:{
                originalPW:'',
                newPWA:'',
                newPWB:'',
            },
            /* modifyRules:{
            originalPW:[{ required: true, message: "请输入原密码", trigger: "blur" },{
          min:6,
          max:20,
          message:"密码长度应为6到20位",
          trigger:'blur'
        }],
        newPWA:[
        { required: true, message: "请输入新密码", trigger: "blur" },{
          min:6,
          max:20,
          message:"密码长度应为6到20位",
          trigger:'blur'
        }
        ],
        newPWB:[
        { required: true, message: "请再次输入新密码", trigger: "blur" },{
          min:6,
          max:20,
          message:"密码长度应为6到20位",
          trigger:'blur'
        }
        ],
        }, */
        }
        
    },
    methods: {
        modify_username(){
            this.select=1;
        },
        modify_password(){
            this.select=2;
        },
        

        cancel(){
            this.select=0;
        },
        MODusernameOK(){

            this.select=0;
        },
        MODpasswordOK(){

            this.select=0;
        },
    },
}
</script>

<style scoped>

.container{
    
}

.title{
    display: flex;
    justify-content: start;
    
}

.divider{
    background-color: #f0f0f0;
    height: 1px;
    margin:20px 0;
}


.message{
    font-family: '宋体';
    
}

.firstSet{
    text-align:justify;
    text-align-last:justify ;
}
.firstShow{
    text-align:justify;
}
.secondSet{
    text-align:justify;
}
.secondShow{
    text-align:justify;
}

.Modify{
  position: absolute;
  top: 65%;
  left: 60%;
  margin-top: -200px;
  margin-left: -250px;
  width: 350px;
  height: 390px;
  background:transparent;
  border-radius: 5%;
}

.usernameMain{
    position: absolute;
    width: 400px;
  height: 350px;
  position: absolute;
  top: 100px;
  left: 25px;
  background:-webkit-linear-gradient(top,lightblue,rgb(240, 210, 210));
  border-radius: 5%;
    
}

.inputForm {
  border: none;
  outline: none;
  border-bottom: 2px solid #484747;
}

.ButtonUS{
    padding-left: 20%;
    padding-top: 70px;
    
}

.passwordMain{
    position: absolute;
    width: 400px;
  height: 350px;
  position: absolute;
  top: 100px;
  left: 25px;
  background:-webkit-linear-gradient(top,rgb(185, 232, 184),rgb(240, 210, 210));
  border-radius: 5%;
}

</style>

