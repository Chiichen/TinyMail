<template>
  <div>
    <Home/>
  </div>
</template>
<script>
import Home from "./components/Home.vue";
import Login from "./components/Login.vue";
import Writing from "./components/Writing.vue";
import Receiving from "./components/Receiving.vue";
import Welcome from "./components/Welcome.vue";
export default{
  name:'App',
  components:{
    Home,
    Login,
    Writing,
    Receiving,
    Welcome,
  }
}
</script>
<style>

</style>