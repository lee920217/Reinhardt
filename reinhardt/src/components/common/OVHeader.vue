<template>
  <div class="ov-header-container">
    <div v-if="username == ''" class="user-center" v-on:touchstart="transfer"></div>
    <div v-else class="user-info">
      <div>健康平安，{{ username }}</div>
      <div class="user-list">
        <div class="list-info">
          <div class="item">个人信息</div>
          <div class="item">退出登录</div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "OVHeader",
  data() {
    return {
      username: ""
    };
  },
  created: function() {
    const self = this;
    self.username = self.$userName;
  },
  mounted: function() {
    console.log(this.$userId);
    // this.$addUser(11, "cao");
  },
  methods: {
    checkLoginStatus() {
      const self = this;
      let userId = self.$userId;
    },
    transfer() {
      const self = this;
      self.$router.push({ path: "/user" });
    }
  }
};
</script>

<style lang="scss" scope>
$designWidth: 750;
@import "@/assets/library/px2rem.scss";
.ov-header-container {
  position: fixed;
  width: 100%;
  height: px2rem(100);
  top: 0;
  display: flex;
  flex-direction: row-reverse;
  align-items: center;
  padding: 0 px2rem(20);
  box-sizing: border-box;
  .user-center {
    width: px2rem(80);
    height: px2rem(80);
    right: px2rem(20);
    background-image: url("../../assets/img/user_center.png");
    background-size: cover;
  }
  .user-info {
    position: relative;
    font-size: px2rem(30);
    &:hover {
      .user-list {
        display: block;
      }
    }
    .user-list {
      display: none;
      position: absolute;
      width: px2rem(180);
      top: px2rem(60);
      right: 0;
      background-color: #ffffff;
      font-size: px2rem(20);
      color: #000;
      box-shadow: px2rem(6) px2rem(4) px2rem(8) px2rem(4) #888888;
      .list-info {
        .item {
          margin: px2rem(20) 0;
        }
      }
    }
  }
}
</style>
