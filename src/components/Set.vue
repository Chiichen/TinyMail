<template>
    <div class="setBody">
        <div class="setMain">
            <div class="setHeader">
                <el-button class="cancelButton" text style="font-size: large;" @click="return_back"><b>取消</b></el-button>
                <el-button text style="font-size: large;" @click="want_help"><b>帮助</b></el-button>
            </div>
            <div class="setLogo">
                <img src="img/logo.png" alt="Logo" >
            </div>
            <div class="setContent">
                    <input :model="addEmail" placeholder="输入邮箱地址" type="text" style="width:250px" size="large" class="inputForm"/>
                
                <el-form-item>
                    <el-button type="primary" size="large" class="addButton" @click="add()"><el-icon><Pointer /></el-icon>添加账号</el-button>
                </el-form-item>

                <el-form-item>
                    <img src="img/set-img.png" alt="Logo" class="setImg">
                </el-form-item>
                
            </div>
        </div>
    </div>
</template>

<script>

export default{
    data () {
        return {
            addEmail:'',
        }
    },
    methods: {
        add(){

        },
        return_back(){
            this.$router.push("/home");
        },
        want_help(){

        },
    },
}
</script>
<style scoped>
.setBody{
    background-image: url(img/setBackground.gif);
  background-size: 100% 100%;
  position: fixed;
  width: 100%;
  height: 100%;
}

.setMain{
    position: absolute;
  top: 40%;
  left: 55%;
  margin-top: -200px;
  margin-left: -250px;
  width: 350px;
  height: 500px;
  background: rgb(255, 255, 255);
  border-radius: 5%;
}

.setHeader{

    display: flex;
    padding-left:4%;
    padding-top:5%;
    opacity: 100%;
}

.cancelButton{
    margin-right: 180px;
}

.setLogo{
    padding-top:5%;
    padding-left: 30px;
    height: 10px;

}


.setContent{
    padding-top:100px;
    padding-left:15%;
    opacity: 80%;
}

.inputForm{
    border:none; 
    outline:none;
    border-bottom:2px solid #484747;
    margin-bottom: 40px;
}
input::placeholder{
    font-size:large;
}

.addButton{
    width:255px;
    
}

.setImg{
    height: 100px;
    padding-left:70px;
    padding-top:30px;
}
</style>