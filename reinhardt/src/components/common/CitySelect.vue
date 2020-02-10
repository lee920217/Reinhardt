<template>
  <div class="city-select-container">
    <div class="select-header">
      <div class="close-btn" v-on:touchstart="closeBtn(false)"></div>
    </div>
    <iscroll-view class="city-scroll-view">
      <div class="city-item" v-for="item in cityList" v-bind:key="item">
        <div class="dtl" v-on:touchstart="transfer(item)">{{ item }}</div>
      </div>
    </iscroll-view>
  </div>
</template>

<script>
export default {
  name: "CitySelect",
  data() {
    return {
      cityList: [
        "London",
        "Manchester",
        "Edinburgh",
        "Birmingham",
        "Liverpool",
        "Bristol",
        "Glasgow",
        "Leeds",
        "Cardiff",
        "Nottingham",
        "Newcastle",
        "Sheffield",
        "Leicester",
        "Southampton",
        "York",
        "Coventry",
        "Portsmouth",
        "Cambridge",
        "Oxford",
        "Bath",
        "Plymouth",
        "Norwich",
        "Brighton",
        "Derby"
      ]
    };
  },
  methods: {
    transfer(city) {
      const self = this;
      self.$emit("changeCity", city);
      self.$emit("maskControl", false);
    },
    closeBtn(status) {
      const self = this;
      self.$emit("maskControl", false);
    }
  }
};
</script>

<style lang="scss" scope>
$designWidth: 750;
@import "@/assets/library/px2rem.scss";
.city-select-container {
  position: fixed;
  top: 0;
  bottom: 0;
  left: 0;
  right: 0;
  background-color: #ffffff;
  z-index: 11;
  .select-header {
    width: 100%;
    height: px2rem(80);
    display: flex;
    padding: 0 px2rem(10);
    box-sizing: border-box;
    flex-direction: row-reverse;
    align-items: center;
    background-color: #f2f2f2;
    .close-btn {
      width: px2rem(40);
      height: px2rem(40);
      right: px2rem(20);
      background-image: url("../../assets/img/close.png");
      background-size: px2rem(40);
    }
  }
  .city-scroll-view {
    /* -- Attention: This line is extremely important in chrome 55+! -- */
    touch-action: none;
    /* -- Attention-- */
    position: fixed;
    top: px2rem(80);
    bottom: 0;
    left: 0;
    right: 0;
    overflow: hidden;
    padding: 0 px2rem(40);
    font-size: px2rem(30);
    .city-item {
      padding: px2rem(25) 0;
      border-bottom: px2rem(2) solid #f8f8f8;
    }
  }
}
</style>
