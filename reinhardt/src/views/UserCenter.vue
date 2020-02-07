<template>
  <div class="user-center-container">
    <MessageTop v-if="errorData.errno >= 0" :errorData="errorData" />
    <Login
      v-if="userKey == 'login'"
      v-bind:test="test"
      v-bind:userKey="userKey"
      @transfer="transfer"
      @handleError="handleError"
    />
    <Register
      v-if="userKey == 'register'"
      v-bind:userKey="userKey"
      v-bind:test="test"
      @transfer="transfer"
      @handleError="handleError"
    />
  </div>
</template>

<script>
import Register from "@/components/user/register.vue";
import Login from "@/components/user/login.vue";
import MessageTop from "@/components/common/MessageTop.vue";

export default {
  name: "UserCenter",
  components: { Register, Login, MessageTop },
  data () {
    return {
      userKey: 'login',
      errorData: {
        errno: -1,
        errmsg: ''
      }
    }
  },
  methods: {
    transfer (data) {
      const self = this;
      self.userKey = data;
    },
    handleError (data) {
      const self = this;
      self.errorData = data;
      console.log(data)
    },
    test () {
      console.log(1)
    }
  }
};
</script>

<style lang="scss" scope>
$designWidth: 750;
@import "@/assets/library/px2rem.scss";
.user-center-container {
  width: 100%;
  height: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
}
</style>
