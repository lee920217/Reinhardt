<template>
  <div class="news-data">
    <div class="news-title">
      利物浦Beford学生宿舍发生抢劫案，暂无人员伤亡利物浦Beford学生宿舍发生抢劫案，生宿舍发生抢劫案，生宿舍发生抢劫案，暂无人员伤亡
    </div>
    <div class="news-source">
      <div class="source-plat">信息来源：学生宿舍</div>
      <div>2020-02-20</div>
    </div>
  </div>
</template>

<script>
export default {
  name: "NewsPanel"
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
