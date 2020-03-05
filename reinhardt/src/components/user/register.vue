<template>
  <div class="ov-register-container">
    <backPage :path="'user'" @transfer="transfer" />
    <div class="logo">
      补全信息
      <div class="desc">该邮箱未注册，请补全您的信息</div>
    </div>
    <div class="register-avatar-container">
      <div class="avatar-ct">
        <img :src="avatar['1-1']" />
      </div>
      <div class="text-ct">修改头像</div>
      <div class="arrow-ct">></div>
    </div>
    <div class="register-common-container">
      <div class="title">用户名</div>
      <div class="mobile">
        <input
          type="text"
          v-model="registerParams.uid"
          placeholder="Nick name"
          v-on:input="checkInputStatus"
        />
      </div>
    </div>
    <div class="register-info-container">
      <div class="title">联系人</div>
      <div class="info-ct">
        <div class="username-ct">
          <input
            type="text"
            v-model="registerParams.uname"
            placeholder="姓名"
            v-on:input="checkInputStatus"
          />
        </div>
        <div class="gender-selector">
          <div
            class="gender-male gender-ct"
            :class="[registerParams.gender == 1 ? 'active' : '']"
            v-on:touchstart="clickChangeGender(1)"
          >先生</div>
          <div
            class="gender-female gender-ct"
            :class="[registerParams.gender == 0 ? 'active' : '']"
            v-on:touchstart="clickChangeGender(0)"
          >女士</div>
        </div>
      </div>
    </div>
    <div class="register-common-container">
      <div class="title">电话</div>
      <div class="mobile">
        <input type="number" v-model="registerParams.mobile" placeholder="手机号码（选填）" />
      </div>
    </div>
    <div>
      <div
        class="register-button"
        :class="[clickStatus ? 'active' : 'disable']"
        v-on:touchstart="confirmRegister"
      >注册Lmena</div>
    </div>
  </div>
</template>

<script>
import { Post } from "@/assets/api/api.js";
import backPage from "@/components/common/BackPage.vue";
export default {
  name: "register",
  props: {
    userKey: {
      type: String,
      default: "register"
    },
    transferData: {
      type: Object
    }
  },
  components: {
    backPage
  },
  data () {
    return {
      registerParams: {
        gender: -1,
        uname: "",
        uid: "",
        mobile: ""
      },
      clickStatus: false,
      avatar: {
        "1-1": require("@/assets/img/avatar-1-1.png")
      }
    };
  },
  methods: {
    //TODO
    confirtTest () {
      const self = this;
      const a = Object.assign(self.registerParams, self.transferData);
    },
    clickChangeGender (gender) {
      const self = this;
      self.registerParams.gender = gender;
      self.checkInputStatus();
    },
    transfer (target) {
      const self = this;
      self.$emit("transfer", target);
    },
    confirmRegister () {
      const self = this;
      if (self.registerParams.uname && self.registerParams.uid && self.registerParams.gender) {
        let finalParams = Object.assign(self.registerParams, self.transferData);
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
    },
    handleRequest (res) {
      const self = this;
      if (res.code === 0) {
        self.$emit("handleError", {
          errno: 0,
          errmsg: "注册成功",
          redirect: 0,
          path: "/"
        });
        self.$addUser(res.data.uid, res.data.uname, res.data.uuid, res.data.gender, res.data.email);
        self.$router.push({ path: "/" });
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
    },
    checkInputStatus () {
      const self = this;
      if (
        self.registerParams.uname &&
        self.registerParams.gender != -1 &&
        self.registerParams.uid
      ) {
        self.clickStatus = true;
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
  .register-avatar-container {
    position: relative;
    width: px2rem(622);
    height: px2rem(96);
    display: flex;
    align-items: center;
    border-bottom: px2rem(2) solid #eeeeee;
    box-sizing: border-box;
    .avatar-ct {
      width: px2rem(80);
      height: px2rem(80);
      img {
        width: 100%;
        height: 100%;
      }
    }
    .text-ct {
      font-size: px2rem(28);
      color: #cccccc;
      margin-left: px2rem(74);
    }
    .arrow-ct {
      position: absolute;
      right: 0;
      font-size: px2rem(30);
      color: #cccccc;
    }
  }
  .register-info-container {
    display: flex;
    .title {
      width: px2rem(90);
      margin-right: px2rem(56);
      margin-top: px2rem(30);
      font-size: px2rem(28);
      text-align: left;
      font-weight: 500;
    }
    .info-ct {
      width: px2rem(476);
      .username-ct {
        height: px2rem(96);
        border-bottom: px2rem(2) solid #eeeeee;
      }
      input {
        width: 100%;
        height: px2rem(96);
        box-sizing: border-box;
        font-size: px2rem(28);
        &::placeholder {
          color: #cccccc;
        }
      }
    }
    .gender-selector {
      display: flex;
      align-items: center;
      height: px2rem(96);
      border-bottom: px2rem(2) solid #eeeeee;
      .gender-ct {
        width: px2rem(120);
        height: px2rem(48);
        border: px2rem(2) solid #eeeeee;
        margin-right: px2rem(24);
        font-size: px2rem(26);
        line-height: px2rem(48);
        border-radius: px2rem(4);
        &.active {
          color: #2b44ff;
          border: px2rem(2) solid #2b44ff;
        }
      }
    }
  }
  .register-common-container {
    display: flex;
    align-items: center;
    width: px2rem(622);
    height: px2rem(96);
    border-bottom: px2rem(2) solid #eeeeee;
    .title {
      width: px2rem(140);
      font-size: px2rem(28);
      font-weight: 500;
      text-align: left;
    }
    input {
      height: px2rem(96);
      //margin-left: px2rem(94);
      font-size: px2rem(28);
      box-sizing: border-box;
      &::placeholder {
        font-size: px2rem(28);
        color: #cccccc;
      }
    }
  }
  .register-button {
    width: px2rem(622);
    height: px2rem(88);
    margin: px2rem(64) 0;
    border-radius: px2rem(60);
    box-sizing: border-box;
    color: #f2f2f2;
    font-size: px2rem(30);
    line-height: px2rem(88);
    &.active {
      background-color: #2b44ff;
      color: #ffffff;
    }
    &.disable {
      background-color: #eeeeee;
      color: #bbbbbb;
    }
  }
  .transfer {
    font-size: px2rem(26);
  }
}
</style>
