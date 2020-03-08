<template>
  <div class="cookie-mask-container">
    <div class="content">
      <div class="content-header">
        Cookie
      </div>
      We use cookies to give you the best online experience. Please let us know if you agree to all
      of these cookies.
    </div>
    <div class="notice-main-ct">
      <div class="notice-header">
        Notice
      </div>
      <div class="notice-body">
        <div class="notice-item">· All these data and news may <b>NOT</b> accurate and updated</div>
        <div class="notice-item">
          · The developers will work hard on data updated, but they are <b>NOT</b> responsible for
          any data accuracy
        </div>
        <div class="notice-item">
          · These data on the website can <b>NOT</b> be reference, please use the data on GOV.UK
          directly
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
    hideBtn() {
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
  top: px2rem(700);
  left: 0;
  right: 0;
  bottom: 0;
  display: flex;
  flex-direction: column;
  justify-content: space-around;
  background-color: #ffffff;
  .content-header {
    font-size: px2rem(50);
    text-align: left;
    font-weight: 500;
  }

  z-index: 30;
  .content {
    text-align: left;
    padding: 0 px2rem(32) px2rem(32) px2rem(32);
    font-size: px2rem(30);
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
