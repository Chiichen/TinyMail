<!-- 收信界面的绘制 -->
<template>
    <el-container>
        <el-header>
            <el-form>
                    <el-button type="primary" @click="layoutReceiving">退出</el-button>  
                </el-form>
        </el-header>
    </el-container>
</template>
<script>
import emitter from '../event';
    export default{
        data () {
            return {
                
            }
        },
        methods: {
            layoutReceiving(){
                emitter.emit('back',"asdasd")
            }
        },
    }
</script>
<style scoped>

</style>