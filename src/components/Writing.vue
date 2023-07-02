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
               <el-form>
                 <el-text>添加附件：</el-text>
<!--                 <upload :uploadParams="uploadParams" ></upload>-->
                 <el-upload
                     class="upload-demo"
                     drag
                     action="/api/api/ok"
                     multiple
                     :on-success="handleVideoSuccess"
                     :before-upload="beforeUpload"
                     :on-error="onError"
                 >
                   <el-icon class="el-icon--upload"><upload-filled /></el-icon>
                   <div class="el-upload__text">
                     Drop file here or <em>click to upload</em>
                   </div>
                   <template #tip>
                     <div class="el-upload__tip">
                       jpg/png files with a size less than 500kb
                     </div>
                   </template>
                 </el-upload>
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
import { UploadFilled } from '@element-plus/icons-vue'
import axios from "axios";
   export default{
       data () {
           return {
               textform:{
                   textbody:'',
                   texttitle:'',
                    texttopic:'',
                       },
              formattedHtml:'',
             fileList:[],
           }
       },
       methods: {
        layoutWriting(){
            emitter.emit('back',true)

        },
       onError(err){
          console.log(err)
       },
        sendout(){
          console.log(this.textform)
          console.log(this.fileList)
          var formData = new FormData();
          var username = sessionStorage.getItem("username");
          var serverusername = sessionStorage.getItem("serverusername");


          formData.append('serverusername',"3220497145@qq.com")
          formData.append('username',"huaziya")
          formData.append('subject',this.textform.texttopic)
          formData.append('content',this.textform.textbody)
          formData.append('fromAddress',"3220497145@qq.com")
          formData.append('toAddress',this.textform.texttitle)
          // formData.append('files',this.fileList)

          console.log("发送邮件")
          console.log(this.fileList.length)

          if(this.fileList.length>0){

            for (const fileListElement of this.fileList) {
              formData.append("files",fileListElement.raw)
            }
            // for (var i = 0; i < this.fileList.length; i++) {
            //   formData.append('files',this.fileList[i])
            // }
            axios.post('/api/api/mail/attachedsend',formData,{
              headers:{'Content-Type': 'multipart/form-data'}
            }).then(res=>{
              console.log(res)
            }).catch(res=>{
              console.log(res)
            })
          }else {
            axios({
              method: 'post',
              url: '/api/api/mail/send',
              data:formData
              // 'serverusername':"3220497145@qq.com",
              // 'subject':this.textform.texttopic,
              // 'content':this.textform.textbody,
              // 'fromAddress':"3220497145@qq.com",
              // 'toAddress':this.textform.texttitle,
            }).then(res=>{
              console.log(res)
            }).catch(res=>{
              console.log(res)
            })
          }

        },
        parseHtml(){
          this.formattedHtml=this.htmlCode;
        },

         beforeUpload(file){
          console.log(file)
           const isLt10M=file.size/1024/1024<10
          //判断文件的类型
          if(file.type){

          }

         },
         handleVideoSuccess(res,file){
           console.log(res,file)
           this.fileList.push(file)
           console.log(this.fileList)
         }
       },
   }
</script>
<style scoped>
.sendoutForm{
    padding-top: 50px;
}
</style>
