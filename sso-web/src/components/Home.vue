<template>
  <div>
    <a>{{ msg }}</a>
  </div>
  <n-button @click="logout">
    登出
  </n-button>
</template>

<script setup>
import {ref} from "vue";
import * as demo from "@/api/demo";
import * as auth from "@/api/auth";
import router from "@/router";

var msg = ref("")

demo.hello().then(res => {
  msg.value = res.data.data
})
var logout = () => {
  auth.logout().then(res => {
    localStorage.removeItem('token')
    router.push('/login')
  })
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

</style>
