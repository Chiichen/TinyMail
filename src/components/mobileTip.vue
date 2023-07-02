<template>
  <div>
<!--    <el-alert class="el-alert" :title="tip" type="error" effect="dark" :description="tipDetail" v-if="type==='error'" @close="closeErr"/>-->
<!--    <el-alert :title="tip" type="success" effect="dark" :description="tipDetail" v-if="type==='success'" @close="closeErr"/>-->
<!--    <el-alert :title="tip" type="info" effect="dark" :description="tipDetail" v-if="type==='info'" @close="closeErr"/>-->


    <div class="el-alert">
      <h3>{{tip}}</h3>
      <p class="box-content">{{tipDetail}}</p>
    </div>
  </div>
</template>

<script>
export default {
  name: 'mobileTip',
  data(){

  },
  props: {

    tip: {
      type: String,
      default: ''
    },
    tipDetail:{
      type: String,
      default: ''
    },
    type: {
      type: String,
      required: false,
      default: 'success'
    }
  },
  beforeCreate() {
    console.log("创建",this.type,this.tip,this.tipDetail)
  },
  methods: {
    closeErr(){
      console.log("恢复")
      console.log(this)
    },
  }
}
</script>

<style scoped>
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
.box-content {
  width: 450px;
  height: 20px;
  margin: 10px auto;
  font-size: 14px;
  padding: 8px 16px;
  background: #fff;
  border-radius: 3px;
  margin-bottom: 8px;
}
</style>
