<template>
  <div class="cookie-mask-container">
    <div class="content">
      <div class="content-header">Cookie</div>我们会使用Cookie来完善您的使用体验，请确认您了解我们收集了这些Cookie
    </div>
    <div class="notice-main-ct">
      <div class="notice-header">Notice</div>
      <div class="notice-body">
        <div class="notice-item">
          · 这些数据和新闻都是权威的数据，但可能
          <b>不是</b> 准确及时的
        </div>
        <div class="notice-item">
          · 开发者们正努力致力于数据的更新, 但开发者们
          <b>不会</b>创造数据，所以无法对数据的准确性负责
        </div>
        <div class="notice-item">
          ·
          <b>不</b>要直接引用该数据，如果需要，可以去GOV.UK获取
        </div>
      </div>
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
      self.$emit("hideMask", "");
    }
  }
};
</script>

<style lang="scss" scoped>
$designWidth: 750;
@import "@/assets/library/px2rem.scss";
.cookie-mask-container {
  position: fixed;
  /// top: px2rem(700);
  left: 0;
  right: 0;
  bottom: 0;
  display: flex;
  flex-direction: column;
  justify-content: space-around;
  background-color: #ffffff;
  padding: px2rem(32) 0;
  font-size: pxrem(24);
  .content-header {
    font-size: px2rem(50);
    text-align: left;
    font-weight: 500;
  }

  z-index: 30;
  .content {
    text-align: left;
    padding: 0 px2rem(32) px2rem(32) px2rem(32);
    font-size: px2rem(24);
    font-weight: 300;
  }
  .notice-main-ct {
    padding: 0 px2rem(32);
    .notice-header {
      text-align: left;
      font-weight: 500;
    }
    .notice-body {
      padding-bottom: px2rem(32);
      text-align: left;
      font-weight: 300;
      font-size: px2rem(30);
      .notice-item {
        margin-bottom: px2rem(20);
        font-size: px2rem(24);
      }
    }
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
