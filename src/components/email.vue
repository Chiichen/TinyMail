<template>
  <el-container>
    <el-header>
      <el-form>
        <el-button type="primary" @click="goBack">返回</el-button>
      </el-form>
    </el-header>
    <el-main>
      <div class="email-header">
        <div class="email-sender">发件人：{{ email.sender }}</div>
        <div class="email-time">时间：{{ email.time }}</div>
      </div>
      <div class="email-body">
        <div class="email-content">{{ email.content }}</div>
        <div class="email-attachments">
          <el-button type="primary" @click="showAttachments" v-if="email.attachments.length > 0">
            附件 ({{ email.attachments.length }})
          </el-button>
        </div>
      </div>
    </el-main>
  </el-container>
</template>

<script>
import emitter from '../event';

export default {
  data() {
    return {
      email: {
        sender: '',
        time: '',
        content: '',
        attachments: [
          //可以在里面写附件的展示逻辑
        ],
      },
    };
  },
  methods: {
    goBack() {
      emitter.emit('back');
    },
    showAttachments() {

    },
  },
};
</script>

<style scoped>
.email-header {
  border-bottom: 1px solid #ccc;
  padding: 10px;
}

.email-sender {
  font-weight: bold;
  margin-bottom: 5px;
}

.email-time {
  font-size: 14px;
  color: #666;
}

.email-body {
  margin-top: 20px;
  padding: 10px;
}

.email-content {
  margin-bottom: 10px;
}

.email-attachments {
  margin-top: 20px;
}
</style>
