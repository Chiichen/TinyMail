import Home from "./components/Home.vue";
import Login from "./components/login.vue";
import Writing from "./components/Writing.vue";
import Receiving from "./components/Receiving.vue";

<template>
  <div>
    <RouterLink to="/Home">Home</RouterLink>
    <RouterLink to="/Login">Login</RouterLink>
    <RouterLink to="/Receiving">Receiving</RouterLink>
    <RouterLink to="/Writing">Writing</RouterLink>
  </div>
</template>

<script>
import {RouterLink} from 'vue-router';

export default{
  components:{
    RouterLink,
  },
};
</script>