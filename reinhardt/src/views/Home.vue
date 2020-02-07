<template>
  <div class="home">
    <OVHeader />
    <div class="logo">Oversea Help</div>
    <div class="logo">勤洗手</div>
    <div class="logo">戴口罩</div>
    <div class="ov-button">Get Start</div>
  </div>
</template>

<script>
// @ is an alias to /src
// eslint-disable-next-line import/no-unresolved
// eslint-disable-next-line import/extensions
import OVHeader from "@/components/common/OVHeader.vue";

export default {
  name: "Home",
  components: { OVHeader },
  mounted: function() {
    setTimeout(() => {
      console.log(this.$userId);
    }, 5000);
  },
  methods: {}
};
</script>
<style lang="scss" scoped>
$designWidth: 750;
@import "@/assets/library/px2rem.scss";
.home {
  width: 100%;
  height: 100%;
  display: flex;
  align-content: center;
  align-items: center;
  justify-content: center;
  flex-direction: column;
  .logo {
    height: px2rem(120);
    line-height: px2rem(120);
  }
  .ov-button {
    background-color: #000000;
    color: #f2f2f2;
    padding: px2rem(10) px2rem(20);
    border-radius: px2rem(10);
    margin-top: px2rem(150);
  }
}
</style>
