<template>
  <div class="topsearch-main-container">
    <div class="city-selector">
      <select>
        <option value="0">{{ $currentCity ? $currentCity : "定位中" }}</option>
        <option v-for="i in cityList" :key="i">{{ i }}</option>
      </select>
    </div>
    <div class="lemna-search">
      <input type="text" placeholder="可输入邮编" />
    </div>
    <div class="search-btn">搜索</div>
  </div>
</template>

<script>
export default {
  name: "TopSearch",
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
      ],
      renderStatus: false
    };
  }
};
</script>

<style lang="scss" scope>
$designWidth: 750;
@import "@/assets/library/px2rem.scss";
.topsearch-main-container {
  width: 100%;
  height: px2rem(88);
  margin-top: px2rem(20);
  display: flex;
  justify-content: space-around;
  align-items: center;
  padding: 0 px2rem(20);
  box-sizing: border-box;
  .city-selector {
    width: px2rem(140);
    height: px2rem(50);
    select {
      display: block;
      width: px2rem(140);
      height: px2rem(50);
      font-size: px2rem(22);
      color: #666666;
      -webkit-appearance: none;
    }
  }
  .lemna-search {
    width: px2rem(336);
    height: px2rem(50);
    border: px2rem(2) solid #cccccc;
    border-radius: px2rem(5);
    padding-left: px2rem(10);
    input {
      display: block;
      height: px2rem(50);
      line-height: px2rem(50);
      font-size: px2rem(24);
      box-sizing: border-box;
    }
  }
  .search-btn {
    width: px2rem(130);
    height: px2rem(52);
    background-color: #2b44ff;
    color: #ffffff;
    border-radius: px2rem(5);
    font-size: px2rem(26);
    line-height: px2rem(50);
  }
}
</style>
