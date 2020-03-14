<template>
  <div class="ov-header-container">
    <div class="app-header">
      Lemna
      <div class="desc">- 2019 nCOV in United Kingdom Data Live</div>
    </div>
  </div>
</template>

<script>
export default {
  name: "OVHeader",
  data () {
    return {
      showList: false
    };
  },
  created: function () {
    // const self = this;
    // self.username = self.$userName;
    // if (self.$userId == -1) {
    //   self.$router.push({ path: "/taskList" });
    // }
  },
  mounted: function () {
    // this.$addUser(11, "cao");
  },
  methods: {}
};
</script>

<style lang="scss" scope>
$designWidth: 750;
@import "@/assets/library/px2rem.scss";
.ov-header-container {
  position: fixed;
  width: 100%;
  height: px2rem(88);
  top: 0;
  display: flex;
  flex-direction: row;
  align-items: center;
  padding: 0 px2rem(20);
  box-sizing: border-box;
  z-index: 20;
  background-color: #ffffff;
  .app-header {
    display: flex;
    align-items: center;
    font-family: PingFang SC;
    font-style: normal;
    font-weight: 600;
    font-size: px2rem(34);
    .desc {
      margin-left: px2rem(16);
      font-size: px2rem(26);
      font-weight: 300;
    }
  }
}
</style>
