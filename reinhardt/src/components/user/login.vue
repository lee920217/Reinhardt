<template>
  <div class="ov-login-container">
    <div class="logo">Oversea</div>
    <div>
      <input
        class="login-input login-username"
        type="text"
        placeholder="输入用户名密码"
        v-model="loginParams.uid"
      />
    </div>
    <div>
      <input
        class="login-input login-password"
        type="password"
        placeholder="输入用户名密码"
        v-model="loginParams.pass"
      />
    </div>
    <div>
      <div class="login-button" v-on:touchstart="confirmLogin">登录</div>
    </div>
    <div>
      <div class="transfer" v-on:touchstart="transfer('register')">还没有账号？点击注册</div>
    </div>
  </div>
</template>

<script>
import { Post } from "@/assets/api/api.js";

export default {
  name: "login",
  props: {
    userKey: {
      type: String,
      default: "login"
    }
  },
  data () {
    return {
      loginParams: {
        uid: "",
        pass: ""
      }
    };
  },
  methods: {
    transfer (target) {
      const self = this;
      self.$emit("transfer", target);
    },
    confirmLogin () {
      const self = this;
      // if (!self.emailCheck(self.loginParams.uname)) {
      //   self.$emit("handleError", {
      //     errno: 1,
      //     errmsg: "邮箱地址非法",
      //     redirect: 0,
      //     path: "/"
      //   });
      // }
      if (!self.loginParams.uid) {
        self.$emit("handleError", {
          errno: 1,
          errmsg: "用户名不得为空",
          redirect: 0,
          path: "/"
        });
        return;
      }
      if (!self.loginParams.pass) {
        self.$emit("handleError", {
          errno: 1,
          errmsg: "密码不得为空",
          redirect: 0,
          path: "/"
        });
        return;
      }
      Post("http://localhost:8360/api/user/login", { query: self.loginParams }).then(res => {
        self.handleRequest(res);
      });
      console.log(self.loginParams);
    },
    handleRequest (res) {
      const self = this;
      if (res.code !== 0) {
        self.$emit("handleError", {
          errno: 1,
          errmsg: "登录失败，用户名密码错误",
          redirect: 0,
          path: "/"
        });
      } else {
        self.$emit("handleError", {
          errno: 0,
          errmsg: "登录成功",
          redirect: 0,
          path: "/"
        });
        self.$addUser(res.data.uid, res.data.uname, res.data.uuid, res.data.gender, res.data.email);
        setTimeout(() => {
          self.$emit("handleError", {
            errno: -1,
            errmsg: "登录成功",
            redirect: 0,
            path: "/"
          });
          self.$router.push({ path: "/taskList" });
        }, 2000)
      }
    },
    emailCheck (email) {
      const regExp = /\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*/;
      if (!regExp.test(email)) {
        return false;
      }
      return true;
    }
  }
};
</script>

<style lang="scss" scope>
$designWidth: 750;
@import "@/assets/library/px2rem.scss";
.ov-login-container {
  width: 100%;
  height: 100%;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  .login-input {
    width: px2rem(280);
    height: px2rem(60);
    padding: 0 px2rem(10);
    font-size: px2rem(20);
    border: px2rem(2) solid #000;
    border-radius: px2rem(10);
    margin: px2rem(20) 0;
  }
  .login-button {
    width: px2rem(300);
    height: px2rem(60);
    margin: px2rem(20) 0;
    background-color: #000;
    border-radius: px2rem(10);
    box-sizing: border-box;
    color: #f2f2f2;
    font-size: px2rem(20);
    line-height: px2rem(60);
    font-weight: 600;
  }
  .transfer {
    font-size: px2rem(26);
  }
}
</style>
