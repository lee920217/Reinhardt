<template>
  <div class="ov-login-container">
    <backPage :path="'/'" />
    <div class="logo">
      登录
      <div class="desc">未注册邮箱会自动创建账号</div>
    </div>
    <div>
      <input
        class="login-input login-username"
        type="text"
        placeholder="输入登录邮箱"
        v-model="loginParams.email"
        v-on:input="checkInputContent"
      />
      <div v-if="errno == 1" class="error-msg">用户名密码错误</div>
    </div>
    <div>
      <input
        class="login-input login-password"
        type="password"
        placeholder="输入登录密码"
        v-model="loginParams.pass"
        v-on:input="checkInputContent"
      />
      <div v-if="errno == 2" class="error-msg">用户名密码错误</div>
    </div>
    <div>
      <div
        class="login-button"
        :class="[clickStatus ? 'active' : 'disable']"
        v-on:touchstart="confirmLogin"
      >
        立即登录
      </div>
    </div>
    <div>
      <div class="transfer" v-on:touchstart="transfer('register')">还没有账号？点击注册</div>
    </div>
    <div class="thrid-party-login">
      <div class="title">第三方账号登录</div>
      <div class="third-party-icon">
        <div class="icon-ct">
          <img src="@/assets/img/wechat.png" />
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { Post } from "@/assets/api/api.js";
import backPage from "@/components/common/BackPage.vue";

export default {
  name: "login",
  props: {
    userKey: {
      type: String,
      default: "login"
    },
    transferData: {
      type: Object
    }
  },
  components: {
    backPage
  },
  data() {
    return {
      clickStatus: false,
      loginParams: {
        email: "",
        pass: ""
      },
      /**
       * @errno
       * 1 => 用户名错误
       * 2 => 密码错误
       */
      errno: 0
    };
  },
  mouted() {},
  methods: {
    getTransferData() {
      const self = this;
      self.loginParams = self.transferData;
    },
    transfer(data) {
      const self = this;
      self.$emit("transfer", data);
    },
    confirmLogin() {
      const self = this;
      if (!self.emailCheck(self.loginParams.email)) {
        self.$emit("handleError", {
          errno: 1,
          errmsg: "邮箱地址非法",
          redirect: 0,
          path: "/"
        });
      }
      if (!self.clickStatus) {
        return;
      }
      if (!self.loginParams.email) {
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
      if (self.loginParams.pass.length < 8) {
        self.$emit("handleError", {
          errno: 1,
          errmsg: "密码不得小于8位",
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
    checkInputContent() {
      const self = this;
      if (self.loginParams.email && self.loginParams.pass && self.loginParams.pass >= 8) {
        self.clickStatus = true;
      } else {
        return;
      }
    },
    handleRequest(res) {
      const self = this;
      if (res.code !== 0) {
        if (res.code == 2) {
          self.$emit("toRegister", self.loginParams);
        } else {
          self.$emit("handleError", {
            errno: 1,
            errmsg: "登录失败，用户名密码错误",
            redirect: 0,
            path: "/"
          });
        }
        return;
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
        }, 500);
      }
    },
    emailCheck(email) {
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
  position: relative;
  width: 100%;
  height: 100%;
  display: flex;
  flex-direction: column;
  align-items: center;
  .logo {
    width: px2rem(622);
    text-align: left;
    margin-top: px2rem(300);
    margin-bottom: px2rem(74);
    font-weight: 500;
    font-size: px2rem(40);
    .desc {
      font-size: px2rem(26);
      color: rgba(22, 24, 35, 0.5);
    }
  }
  .login-input {
    width: px2rem(622);
    height: px2rem(94);
    padding: 0 px2rem(20);
    font-size: px2rem(25);
    border: px2rem(2) solid #eeeeee;
    border-radius: px2rem(80);
    margin: px2rem(16) 0;
    box-sizing: border-box;
  }
  .error-msg {
    margin-left: px2rem(10);
    text-align: left;
    font-size: px2rem(26);
    color: #ef3d3d;
  }
  .login-button {
    width: px2rem(622);
    height: px2rem(94);
    margin: px2rem(60) 0;
    border-radius: px2rem(60);
    box-sizing: border-box;
    color: #f2f2f2;
    font-size: px2rem(30);
    line-height: px2rem(94);
    font-weight: 600;
    &.disable {
      background-color: #eeeeee;
      color: #bbbbbb;
    }
    &.active {
      background-color: #2b44ff;
      color: #f2f2f2;
    }
  }
  .transfer {
    font-size: px2rem(26);
  }
  .thrid-party-login {
    position: absolute;
    bottom: px2rem(200);
    .title {
      font-size: px2rem(24);
      color: #999999;
    }
    .third-party-icon {
      display: flex;
      justify-content: center;
      margin-top: px2rem(16);
      .icon-ct {
        width: px2rem(60);
        height: px2rem(60);
        img {
          width: 100%;
          height: 100%;
        }
      }
    }
  }
}
</style>
