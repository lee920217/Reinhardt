<template>
  <div class="home">
    <div class="logo">Oversea Help</div>
    <div class="logo">勤洗手</div>
    <div class="logo">戴口罩</div>
    <div class="button">Get Start</div>
  </div>
</template>

<script>
// @ is an alias to /src
// eslint-disable-next-line import/no-unresolved
// eslint-disable-next-line import/extensions

export default {
  name: "Home",
  components: {}
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
}
</style>
