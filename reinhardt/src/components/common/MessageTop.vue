<template>
  <transition name="fade">
    <div class="message-container" :class="[errorData.errno ? 'error': 'success']">
      <div class="warning-icon"></div>
      <div class="warning-message">{{errorData.errmsg}}</div>
    </div>
  </transition>
</template>

<script>
/**
 * @params errno
 * @ 0 => success
 * @ 1 => error
 * @ 2 => 常规提醒
 * @params errmsg
 * @string 提醒文字
 * @parms redirect
 * @ 0 => 不跳转
 * @ 1 => 取path地址跳转
 * @string path
 */
export default {
  name: "MessageTop",
  props: {
    errorData: {
      type: Object
    }
  }
}
</script>

<style lang="scss" scope>
$designWidth: 750;
@import "@/assets/library/px2rem.scss";
.message-container {
  position: fixed;
  width: 100%;
  height: px2rem(180);
  display: flex;
  justify-content: center;
  align-items: center;
  top: 0;
  overflow: hidden;
  z-index: 15;
  &.error {
    background-color: #f35454;
    .warning-icon {
      background-image: url("../../assets/img/warning.png");
    }
  }
  &.success {
    background-color: #19be6b;
    .warning-icon {
      background-image: url("../../assets/img/success.png");
    }
  }
  .warning-icon {
    width: px2rem(80);
    height: px2rem(80);
    background-size: contain;
  }
  .warning-message {
    font-size: px2rem(30);
    color: #f2f2f2;
    margin-left: px2rem(20);
  }
}
.fade-enter-active,
.fade-leave-active {
  transition: opacity 0.5s;
}
.fade-enter, .fade-leave-to /* .fade-leave-active below version 2.1.8 */ {
  opacity: 0;
}
</style>