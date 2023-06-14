<template>
  <div id="app">
   <login></login>
  </div>
</template>
<script>
import Navigation from "./components/Navigation.vue";
import Login from "./components/Login.vue";
import Home from "./components/Home.vue";
import Welcome from "./components/Welcome.vue";
import Writing from "./components/Writing.vue";
import Receiving from "./components/Receiving.vue";
import Set from './components/Set.vue';

export default{
  data(){
    return {

    };
  },
  components:{
    Login,
    Home,
    Writing,
    Receiving,
    Welcome,
    Set,
    }
}
</script>
<style>

</style>