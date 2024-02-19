<template>
  <div>
    <n-form
        class="login-form"
        :label-width="80"
        label-placement="left"
        :model="loginData">
      <n-form-item path="username">
        <n-input v-model:value="loginData.username" placeholder="输入姓名"/>
      </n-form-item>
      <n-form-item path="password">
        <n-input
            :value="loginData.password"
            type="password"
            show-password-on="mousedown"
            placeholder="输入密码"
            :maxlength="8"
        />
      </n-form-item>
      <n-form-item>
        <n-button style="width: 100%; margin-bottom: 20px;" type="info" @click="login()">登录
        </n-button>
      </n-form-item>
    </n-form>
  </div>
</template>

<script setup>

import {ref} from "vue";
import * as auth from "@/api/auth";
import router from "@/router";
import request from "@/utils/request";

var loginData = ref({
  username: 'shenhuaxin',
  password: 'shenhuaxin'
})

var login = () => {
  var br = getQueryString("r")
  loginData.value.p_redirect = br
  auth.login(loginData.value, loginData.value)
      .then(res => {
        localStorage.setItem("token", res.data.data.token)
        if (res.data.data.redirectUrl) {
          window.location.href = decodeURIComponent(res.data.data.redirectUrl)
        } else {
          router.push("/")
        }
      }).catch(err => {
    console.log(err)
  })
}
function getQueryString(name) {
  var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
  var r = window.location.href?.split("?")[1]?.match(reg);
  if (r != null) {
    return r[2];
  }
  return null;
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.login-form {
  position: relative;
  width: 480px;
  max-width: 100%;
  padding: 0 30px;
  margin: 0 auto;
  overflow: hidden;
}
</style>
