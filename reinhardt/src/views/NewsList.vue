<template>
  <div class="newlist-main-container">
    <TopSearch />
    <perfect-scrollbar ref="iscroll" class="news-scroll-container" :options="scrollOptions">
      <NewsPanel v-for="(i, v) in newsList" v-bind:key="v" :Newsdata="i" />
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
      scrollOptions: {},
      newsList: {
        0: {
          title:
            "Coronavirus warning for 115 people as patient tests positive after attending Liverpool Hospital",
          source: "Echo",
          time: "2020, Mar 7th, 10:23"
        },
        1: {
          title: "Coronavirus latest news: 209 people test positive as UK case count surges",
          source: "telegraph",
          time: "2020, Mar 7th, 22:59"
        },
        2: {
          title:
            "FIVE more cases of the deadly virus have been recorded in Scotland bringing the number of infected in the country to 16 , authorities said.",
          source: "edition",
          time: "2020, Mar 5th, 23:20"
        }
      }
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
