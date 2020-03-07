<template>
  <div class="list-main-container">
    <TopSearch />
    <div class="router-selector-container">
      <div
        class="route-bar offical-route"
        :class="[routeType == 0 ? 'active' : '']"
        v-on:touchstart="changeRouteType(0)"
      >全部</div>
      <div
        class="route-bar offical-route"
        :class="[routeType == 1 ? 'active' : '']"
        v-on:touchstart="changeRouteType(1)"
      >合作路线</div>
      <div
        class="route-bar individual-route"
        :class="[routeType == 2 ? 'active' : '']"
        v-on:touchstart="changeRouteType(2)"
      >个人路线</div>
    </div>
    <div ref="iscroll" class="route-scroll-container">
      <div>
        <RoutePanel
          v-for="i in allTaskList"
          v-bind:key="`official-${i.tid}`"
          :panelData="i"
          :renderStatus="renderStatus"
        />
      </div>
    </div>

    <!-- <div class="route-scroll-container">
      <RoutePanel
        v-for="i in allTaskList"
        v-bind:key="`official-${i.tid}`"
        :panelData="i"
        :renderStatus="renderStatus"
      />
    </div>-->
  </div>
</template>

<script>
import RoutePanel from "@/components/common/RoutePanel.vue";
import TopSearch from "@/components/common/TopSearch.vue";
import { Post, Get } from "@/assets/api/api.js";
import { exportAddress } from "@/assets/api/setting.js";
import BScroll from "better-scroll";

export default {
  name: "List",
  components: {
    RoutePanel,
    TopSearch
  },
  data () {
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
      scrollOptions: {},
      renderStatus: false,
      routeType: 0,
      allTaskList: [],
      officialTaskList: [],
      indivTaskList: [],
      scroll: null,
      totalCount: 0,
      currentCount: 0,
      currPage: 0,
      pageCt: 4
    };
  },
  created () { },
  destroyed () { },
  mounted () {
    this.getRoute();
  },
  methods: {
    refreshSroll () {
      const self = this;
      const el = self.$refs.iscroll;
      el.refresh();
    },
    changeRouteType (t) {
      const self = this;
      self.routeType = t;
    },
    getRoute () {
      const self = this;
      if (self.currentCount >= self.totalCount && self.currentCount != 0) {
        return;
      }
      let pageNum = self.currPage + 1;
      Post(`${exportAddress.task}/page`, {
        query: {
          order_: "start",
          pageSize_: 4,
          pageNum_: pageNum == 0 ? 1 : pageNum,
          start: self.$currentCity
        }
      }).then(res => {
        self.renderStatus = true;
        self.handleRequest(res);
      });
    },
    timeFormat (d) {
      /**
       * @type 0 => return 年月日 时:分
       * @type 1 => return 时:分
       * @type 2 => return 年月日
       */
      const self = this;
      let time = new Date(d);
      let year = time.getFullYear();
      let month = time.getMonth() + 1;
      let days = time.getDate();
      let hours = time.getHours();
      let minutes = time.getMinutes();
      let seconds = time.getSeconds();
      let formatTime = {
        "yy-mm-dd hh:mm": `${year}年${month}月${days} ${hours}:${minutes}`,
        "hh:mm": `${hours}:${minutes}`,
        "yy-mm-dd": `${year}年${month}月${days}`
      };
      return formatTime;
    },
    handleRequest (res) {
      const self = this;

      if (res) {
        if (res.code !== 0) {
          self.errorData = {
            errno: 1,
            errmsg: "获取列表失败",
            redirect: 0,
            path: "/"
          };
        } else {
          self.totalCount = res.data.records;
          self.currentCount = self.currentCount + res.data.rows.length;
          self.currPage = self.currPage + 1;
          for (let j = 0; j < res.data.rows.length; j++) {
            let dataJ = res.data.rows[j];
            dataJ.startCode = dataJ.startCode.split(",");
            dataJ.targetCode = dataJ.targetCode.split(",");
            let formatTime = self.timeFormat(dataJ.startTime);
            dataJ.startTime = formatTime["yy-mm-dd hh:mm"];
            dataJ.Time = formatTime["hh:mm"];
            dataJ.Date = formatTime["yy-mm-dd"];
            dataJ.dtlStatus = false;
            // if (dataJ.type == 1) {
            //   self.officialTaskList.push(dataJ);
            // } else {
            //   self.indivTaskList.push(dataJ);
            // }
            self.allTaskList.push(dataJ);
          }
          self.registerScrollCt();
        }
      } else {
        return false;
      }
    },
    registerScrollCt () {
      const self = this;
      self.$nextTick(() => {
        self.scroll = new BScroll(this.$refs.iscroll, {
          probeType: 3
          // scrollY: true
        });
        self.scroll.on("scrollEnd", () => {
          // 滚动到底部
          if (self.scroll.y <= self.scroll.maxScrollY + 50) {
            self.reachEnd();
          }
        });
      });
    },
    reachEnd () {
      const self = this;
      self.getRoute();
    }
  }
};
</script>

<style scope lang="scss">
$designWidth: 750;
@import "@/assets/library/px2rem.scss";
.list-main-container {
  height: 100%;
  display: flex;
  flex-direction: column;
  overflow: hidden;
  .router-selector-container {
    width: 100%;
    height: px2rem(80);
    display: flex;
    background-color: #ffffff;
    .route-bar {
      width: px2rem(140);
      font-size: px2rem(24);
      line-height: px2rem(80);
      margin: 0 px2rem(8);
      &.active {
        border-bottom: px2rem(4) solid #000;
      }
    }
  }

  .route-scroll-container {
    touch-action: none;
    flex: 1;
    overflow: hidden;
  }
}
</style>
