<template>
  <div class="toast">{{msg}}</div>
</template>

<script>
export default {
  name: 'Toast',
  props: {
    msg: {
      type: String,
      default: 'Tips'
    }
  }
}
</script>

<style lang="scss" scope>
$designWidth: 750;
@import "@/assets/library/px2rem.scss";
.toast {
  position: fixed;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  padding: px2rem(20) px2rem(40);
  z-index: 99;
  background-color: #000000b0;
  border-radius: px2rem(10);
  font-size: px2rem(28);
  color: #f2f2f2;
}
</style>