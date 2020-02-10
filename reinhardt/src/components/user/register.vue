<template>
  <div class="ov-register-container">
    <div class="logo">Oversea</div>
    <div>
      <input
        class="register-input register-username"
        type="text"
        placeholder="输入邮箱"
        v-model="registerParams.email"
      />
    </div>
    <div>
      <input
        class="register-input register-nickname"
        type="text"
        placeholder="输入用户名"
        v-model="registerParams.uid"
      />
    </div>
    <div>
      <input
        class="register-input register-password"
        type="password"
        placeholder="输入密码"
        v-model="registerParams.pass"
      />
    </div>
    <div>
      <input
        class="register-input register-password"
        type="password"
        placeholder="再次输入密码"
        v-model="registerParams.repass"
      />
    </div>
    <div class="register-gender">
      <div
        class="gender-male"
        :class="[registerParams.gender == 1 ? 'active' : '']"
        v-on:touchstart="clickChangeGender(1)"
      ></div>
      <div
        class="gender-female"
        :class="[registerParams.gender == 0 ? 'active' : '']"
        v-on:touchstart="clickChangeGender(0)"
      ></div>
    </div>
    <div>
      <div class="register-button" v-on:touchstart="confirmRegister">注册</div>
    </div>
    <div>
      <div class="transfer" v-on:touchstart="transfer('login')">已有账号？点击登录</div>
    </div>
  </div>
</template>

<script>
import { Post } from "@/assets/api/api.js";
export default {
  name: "register",
  props: {
    userKey: {
      type: String,
      default: "register"
    }
  },
  data() {
    return {
      registerParams: {
        gender: -1,
        email: "",
        uname: "",
        uid: "",
        mobile: "000",
        pass: "",
        repass: ""
      }
    };
  },
  methods: {
    clickChangeGender(gender) {
      const self = this;
      self.registerParams.gender = gender;
    },
    transfer(target) {
      const self = this;
      self.$emit("transfer", target);
    },
    emailCheck(email) {
      const regExp = /\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*/;
      if (!regExp.test(email)) {
        return false;
      }
      return true;
    },
    confirmRegister() {
      const self = this;
      self.registerParams.uname = self.registerParams.uid;
      if (!self.emailCheck(self.registerParams.email)) {
        self.$emit("handleError", {
          errno: 1,
          errmsg: "邮箱地址非法",
          redirect: 0,
          path: "/"
        });
        return;
      }
      if (self.registerParams.pass.length <= 8) {
        self.$emit("handleError", {
          errno: 1,
          errmsg: "密码长度需大于8位",
          redirect: 0,
          path: "/"
        });
        return;
      }
      if (self.registerParams.pass !== self.registerParams.repass) {
        self.$emit("handleError", {
          errno: 1,
          errmsg: "两次输入密码不一致",
          redirect: 0,
          path: "/"
        });
        return;
      }
      if (
        self.registerParams.pass === self.registerParams.repass &&
        self.registerParams.email &&
        self.registerParams.uname &&
        self.registerParams.uid &&
        self.registerParams.mobile &&
        self.registerParams.pass
      ) {
        let finalParams = self.registerParams;
        delete finalParams.repass;
        Post("http://localhost:8360/api/user/regist", { query: self.registerParams }).then(res => {
          self.handleRequest(res);
        });
      } else {
        self.$emit("handleError", {
          errno: 1,
          errmsg: "信息不完整",
          redirect: 0,
          path: "/"
        });
      }
      console.log(self.registerParams);
    },
    handleRequest(res) {
      const self = this;
      if (res.code === 0) {
        self.$emit("handleError", {
          errno: 0,
          errmsg: "注册成功",
          redirect: 0,
          path: "/"
        });
        self.$addUser(res.data.uid, res.data.uname, res.data.uuid, res.data.gender, res.data.email);
        self.$router.push({ path: "/taskList" });
      } else {
        if ("repeat uid".indexOf(res.msg) >= 0) {
          self.$emit("handleError", {
            errno: 0,
            errmsg: "用户名已经存在",
            redirect: 0,
            path: "/"
          });
        }
      }
    }
  }
};
</script>

<style lang="scss" scope>
$designWidth: 750;
@import "@/assets/library/px2rem.scss";
.ov-register-container {
  width: 100%;
  height: 100%;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  .register-input {
    width: px2rem(280);
    height: px2rem(60);
    padding: 0 px2rem(10);
    font-size: px2rem(20);
    border: px2rem(2) solid #000;
    border-radius: px2rem(10);
    margin: px2rem(20) 0;
    &select {
      font-size: px2rem(20);
    }
    option {
      font-size: px2rem(20);
    }
  }
  .register-gender {
    width: px2rem(280);
    height: px2rem(60);
    display: flex;
    justify-content: space-between;
    .gender-male {
      width: px2rem(60);
      height: px2rem(60);
      background-image: url("../../assets/img/boy.png");
      background-size: contain;
      opacity: 0.5;
      &.active {
        opacity: 1;
      }
    }
    .gender-female {
      width: px2rem(60);
      height: px2rem(60);
      background-image: url("../../assets/img/girl.png");
      background-size: contain;
      opacity: 0.5;
      &.active {
        opacity: 1;
      }
    }
  }
  .register-button {
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
