<!-- 写信界面的组件 -->
<template>
    <el-container>
             <el-header>
                <el-form>
                    <el-button type="primary" @click="layoutWriting">退出</el-button>  
                </el-form>

             </el-header>
             <el-main>
               <el-form>
                 <el-form-item label="收信对象:">
                   <el-input size="medium" type="text" style="width:100%" autocomplete="false" clearable v-model="textform.texttitle"></el-input>
                 </el-form-item>
                 <el-form-item label="主题内容:">
                   <el-input size="medium" type="text" style="width:100%" autocomplete="false" clearable v-model="textform.texttopic"></el-input>
                 </el-form-item>
                 <!-- 正文内容的Html引入疑点 -->
                 <el-form-item label="正文内容:">
                   <el-input  type="textarea" style="width:100%"  :rows="17" v-model="textform.textbody" @keydown.ctrl.enter="parseHtml">
                  <div v-html="formattedHtml"></div>
                  </el-input>
                 </el-form-item>
               </el-form>
             </el-main>
             <el-footer>
                <el-form class="sendoutForm">
                    <el-button type="primary" @click="sendout"><el-icon><Promotion /></el-icon>发送</el-button>  
                </el-form>
             </el-footer>
           </el-container>
</template>
<script >
import emitter from '../event';
   export default{
       data () {
           return {
               textform:{
                   textbody:'',
                   texttitle:'',
                    texttopic:'',
                       },
              formattedHtml:'',
           }
       },
       methods: {
        layoutWriting(){
            emitter.emit('back',"asdasd")
            
        },
        sendout(){
            
        },
        parseHtml(){
          this.formattedHtml=this.htmlCode;
        },
       },
   }
</script>
<style scoped>
.sendoutForm{
    padding-top: 50px;
}
</style>