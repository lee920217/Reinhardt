<template>
  <div class="denote-container">
    <div class="denote-header">赞助4Lemna运营</div>
    <div class="denote-body">
      <div class="denote-content">
        <p>4Lemna将会持续更新英国疫情地图及数据可视化</p>
        <p>您的赞助将会被投入到功能开发及服务维护当中</p>
      </div>
      <div class="denote-qrcode">
        <img src="../../assets/img/qrcode.jpg" />
      </div>
    </div>
    <div class="denote-footer">
      <div class="cancel ft-btn" @click="showDenote">关闭</div>
    </div>
  </div>
</template>

<script>
export default {
  name: "DenoteDialog",
  methods: {
    showDenote () {
      const self = this;
      self.$emit('changeDenoteDialog', false)
    }
  }
}
</script>

<style lang="scss" scope>
$designWidth: 750;
@import "@/assets/library/px2rem.scss";
.denote-container {
  position: absolute;
  width: px2rem(650);
  height: px2rem(1000);
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  z-index: 999;
  background-color: #f2f2f2;
  .denote-header {
    font-size: px2rem(28);
    text-align: left;
    padding: px2rem(24) 0 px2rem(24) px2rem(24);
    background-color: #e0b741;
    font-weight: 900;
  }
  .denote-body {
    .denote-content {
      font-size: px2rem(24);
      text-align: left;
      padding: px2rem(24) 0 px2rem(24) px2rem(24);
    }
    img {
      width: px2rem(600);
      height: px2rem(600);
    }
  }
  .denote-footer {
    display: flex;
    flex-direction: center;
    justify-content: space-around;
    margin-top: px2rem(20);
    .ft-btn {
      width: px2rem(200);
      height: px2rem(60);
      font-size: px2rem(30);
      line-height: px2rem(60);
      border-radius: px2rem(10);
      color: #f2f2f2;
      &.confirm {
        background-color: #1aad19;
      }
      &.cancel {
        background-color: #232322;
      }
    }
  }
}
</style>