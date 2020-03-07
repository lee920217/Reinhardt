<template>
  <div class="news-data">
    <div class="news-title">
      {{ Newsdata.title }}
    </div>
    <div class="news-source">
      <div class="source-plat">{{ Newsdata.source }}</div>
      <div>{{ Newsdata.time }}</div>
    </div>
  </div>
</template>

<script>
export default {
  name: "NewsPanel",
  props: {
    Newsdata: {
      type: Object
    }
  }
};
</script>

<style scoped lang="scss">
$designWidth: 750;
@import "@/assets/library/px2rem.scss";
.news-data {
  width: 100%;
  // height: px2rem(144);
  padding: px2rem(24) 0;
  border-bottom: px2rem(2) solid #e8e8e8;
  display: flex;
  flex-direction: column;
  justify-content: space-around;
  .news-title {
    margin-bottom: px2rem(16);
    font-family: PingFang SC;
    font-style: normal;
    font-weight: normal;
    font-size: px2rem(30);
    text-align: left;
    line-height: px2rem(44);
    overflow: hidden;
    text-overflow: ellipsis;
    display: -webkit-box;
    -webkit-line-clamp: 2;
    -webkit-box-orient: vertical;
  }
  .news-source {
    display: flex;
    font-size: px2rem(24);
    color: #999999;
    .source-plat {
      margin-right: px2rem(16);
    }
  }
}
</style>
