<template>
  <div class="newlist-main-container">
    <TopSearch />
    <perfect-scrollbar ref="iscroll" class="news-scroll-container" :options="scrollOptions">
      <NewsPanel />
      <NewsPanel />
      <NewsPanel />
      <NewsPanel />
      <NewsPanel />
      <NewsPanel />
      <NewsPanel />
      <NewsPanel />
      <NewsPanel />
      <NewsPanel />
      <NewsPanel />
      <NewsPanel />
      <NewsPanel />
      <NewsPanel />
      <NewsPanel />
      <NewsPanel />
      <NewsPanel />
      <NewsPanel />
      <NewsPanel />
      <NewsPanel />
      <NewsPanel />
      <NewsPanel />
      <NewsPanel />
      <NewsPanel />
      <NewsPanel />
      <NewsPanel />
      <NewsPanel />
      <NewsPanel />
      <NewsPanel />
      <NewsPanel />
      <NewsPanel />
      <NewsPanel />
      <NewsPanel />
      <NewsPanel />
    </perfect-scrollbar>
  </div>
</template>

<script>
import TopSearch from "@/components/common/TopSearch.vue";
import NewsPanel from "@/components/common/NewsPanel.vue";
export default {
  name: "NewsList",
  components: {
    TopSearch,
    NewsPanel
  },
  data() {
    return {
      scrollOptions: {}
    };
  }
};
</script>

<style lang="scss" scope>
$designWidth: 750;
@import "@/assets/library/px2rem.scss";
.newlist-main-container {
  display: flex;
  width: 100%;
  height: 100%;
  flex-direction: column;
  overflow: hidden;
  .news-scroll-container {
    margin: px2rem(24) px2rem(24) 0 px2rem(24);
    .ps__rail-y {
      display: none;
    }
  }
}
</style>
