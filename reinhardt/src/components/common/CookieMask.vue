<template>
  <div class="cookie-mask-container">
    <div class="content">
      We use cookies to give you the best online experience.
      Please let us know if you agree to all of these cookies.
    </div>
    <div class="button" v-on:touchstart="hideBtn">Yes, I agree with that</div>
  </div>
</template>

<script>
export default {
  name: "CookieMask",
  methods: {
    hideBtn () {
      const self = this;
      self.$emit('hideMask', '')
    }
  }
}
</script>

<style lang="scss" scoped>
$designWidth: 750;
@import "@/assets/library/px2rem.scss";
.cookie-mask-container {
  position: fixed;
  top: px2rem(900);
  left: 0;
  right: 0;
  bottom: 0;
  display: flex;
  flex-direction: column;
  justify-content: center;
  background-color: #ffffff;
  z-index: 30;
  .content {
    padding: 0 px2rem(24) px2rem(24) px2rem(24);
    font-size: px2rem(30);
  }
  .button {
    width: px2rem(696);
    height: px2rem(88);
    margin: 0 auto;
    line-height: px2rem(88);
    background-color: #2b44ff;
    border-radius: px2rem(44);
    font-size: px2rem(30);
    color: #ffffff;
  }
}
</style>