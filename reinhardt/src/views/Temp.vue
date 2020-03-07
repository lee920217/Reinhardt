<template>
  <div class="index-main-container">
    <Header />
    <AddTask
      v-if="addTaskStatus"
      @cancelAddTaskDialog="cancelNewTask"
      :basicTravelData="basicTravelData"
    />
    <div class="slider-container">
      <img src="@/assets/img/slider.png" />
    </div>
    <div class="create-task-container">
      <div class="base-header">
        <div class="create-text-container">
          <div class="text-header">组团出行</div>
          <div class="text-detail">我们在一起，对抗这世界的恶意</div>
        </div>
      </div>
      <div class="create-body">
        <div class="create-input-container had-bottom-line">
          <div class="point yellow-bg"></div>
          <div class="input-part">
            <input type="text" placeholder="从哪里出发" v-model="basicTravelData.from" />
          </div>
        </div>
        <div class="create-input-container had-bottom-line">
          <div class="point blue-bg"></div>
          <div class="input-part">
            <input type="text" placeholder="我要去哪里" v-model="basicTravelData.to" />
          </div>
        </div>
        <div class="create-input-container">
          <div class="point red-bg"></div>
          <div class="input-part" v-on:touchstart="touchChange">
            <input :type="touchChangeType" placeholder="出发时间" v-model="basicTravelData.time" />
          </div>
        </div>
      </div>
    </div>
    <div class="route-selector-container">
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
    <div class="route-display-container" v-if="renderStatus && routeType == 1">
      <RoutePanel
        v-for="i in officialTaskList"
        v-bind:key="`official-${i.tid}`"
        :panelData="i"
        :renderStatus="renderStatus"
      />
      <div class="route-more" v-on:touchstart="routerDirect('/list')">查看更多 ></div>
    </div>
    <div class="route-display-container" v-if="renderStatus && routeType == 2">
      <RoutePanel
        v-for="j in indivTaskList"
        v-bind:key="`individual-${j.tid}`"
        :panelData="j"
        :renderStatus="renderStatus"
      />
      <div class="route-more" v-on:touchstart="routerDirect('/list')">查看更多 ></div>
    </div>
    <div class="route-display-container" v-if="!renderStatus">
      <RoutePanel
        v-for="i in renderingTaskList"
        :renderStatus="renderStatus"
        v-bind:key="`individual-${i.tid}`"
        :panelData="i"
      />
      <div class="route-more" v-on:touchstart="routerDirect('/list')">查看更多 ></div>
    </div>
    <div class="data-display-container">
      <div class="base-header">
        <div class="create-text-container">
          <div class="text-header">疫情数量及区域统计</div>
          <div class="text-detail">我们需要了解，我们需要面对的是什么</div>
        </div>
        <div class="update-time">截止 2020-03-06 10:55</div>
      </div>
      <div class="text-data-container">
        <div class="text-data-detail">
          <div class="num red-font">163</div>
          <div class="desc">肺炎疫情</div>
        </div>
        <div class="text-data-detail">
          <div class="num yellow-font">9</div>
          <div class="desc">累计治愈</div>
        </div>
        <div class="text-data-detail">
          <div class="num blue-font">2</div>
          <div class="desc">累计死亡</div>
        </div>
        <!-- <div class="text-data-detail">
          <div class="num purple-font">51+</div>
          <div class="desc">事件统计</div>
        </div>-->
      </div>
      <div class="map-data-container">
        <highcharts :constructor-type="'mapChart'" :options="UKMapSettings"></highcharts>
      </div>
    </div>
    <div class="news-display-container">
      <div class="base-header">
        <div class="create-text-container">
          <div class="text-header">事件实时播报</div>
        </div>
      </div>
      <div class="news-body">
        <NewsPanel v-for="(i, v) in newsList" v-bind:key="v" :Newsdata="i" />
        <!-- <div class="news-more">查看更多 ></div> -->
      </div>
    </div>
    <!-- <div class="addnew-task-btn" v-on:touchstart="createNewTask">创建我的路线</div> -->
  </div>
</template>

<script>
import Header from "@/components/common/OVHeader.vue";
import RoutePanel from "@/components/common/RoutePanel.vue";
import AddTask from "@/components/common/AddTask.vue";
import NewsPanel from "@/components/common/NewsPanel.vue";
import UKMapSettings from "@/assets/data/uk-map.js";
import { Post } from "@/assets/api/api.js";
import { exportAddress } from "@/assets/api/setting.js";

export default {
  name: "Index",
  components: {
    Header,
    RoutePanel,
    AddTask,
    NewsPanel
  },
  data () {
    return {
      UKMapSettings: UKMapSettings,
      /**
       * @routeType
       * @ 1 => 个人路线
       * @ 2 => 官方路线
       */
      touchChangeType: "text",
      routeType: 1,
      renderStatus: false,
      officialTaskList: {},
      indivTaskList: {},
      renderingTaskList: [
        { startCode: [0, 1], targetCode: [2, 3], Date: "rendering", time: "rendering" }
      ],
      basicTravelData: {
        from: "",
        to: "",
        time: ""
      },
      newsList: {
        0: {
          title: "伦敦Barnet确诊3人",
          source: "My London",
          time: "2020, Mar 6th, 10:34"
        },
        1: {
          title: "利物浦增加两例确诊病例, 一人为曾与感染者接触, 另一人是刚刚从意大利北部回来",
          source: "Echo",
          time: "2020, Mar 5th, 16:23"
        },
        2: {
          title: "英国累计确诊达到116例, 并出现首例死亡病例",
          source: "DHS",
          time: "2020, Mar 5th, 19:25"
        }
      },
      addTaskStatus: false
    };
  },
  mounted () {
    this.statusCheck();
  },
  methods: {
    touchChange () {
      const self = this;
      self.touchChangeType = "datetime-local";
    },
    changeRouteType (t) {
      const self = this;
      self.routeType = t;
      if (t == 1) {
        if (Object.keys(self.officialTaskList).length == 0) {
          self.getRoute(t);
        }
      }
      if (t == 2) {
        if (Object.keys(self.indivTaskList).length == 0) {
          self.getRoute(t);
        }
      }
    },
    getRoute (t = 1) {
      const self = this;

      Post(`${exportAddress.task}/page`, {
        query: {
          order_: "start",
          type: t,
          start: self.$currentCity,
          pageSize_: 2
        }
      }).then(res => {
        self.renderStatus = true;
        self.handleRequest(res);
      });
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
          for (let j = 0; j < res.data.rows.length; j++) {
            let dataJ = res.data.rows[j];
            /**
             * 优先发异步请求具体任务状态
             */
            self.getRouteDtl(dataJ.tid);
            dataJ.startCode = dataJ.startCode.split(",");
            dataJ.targetCode = dataJ.targetCode.split(",");

            let formatTime = self.timeFormat(dataJ.startTime);
            dataJ.startTime = formatTime["yy-mm-dd hh:mm"];
            dataJ.Time = formatTime["hh:mm"];
            dataJ.Date = formatTime["yy-mm-dd"];
            dataJ.dtlStatus = false;
            if (self.routeType == 1) {
              self.officialTaskList[dataJ.tid] = dataJ;
            } else {
              self.indivTaskList[dataJ.tid] = dataJ;
            }
          }
        }
      } else {
        return false;
      }
    },
    getRouteDtl (t) {
      const self = this;
      Post(`${exportAddress.task}/partners`, {
        query: {
          tid: t
        }
      }).then(res => {
        if (res.code === 0) {
          self.handleRouteDtl(res.data, t);
        } else {
          //TODO 错误处理
        }
      });
    },
    handleRouteDtl (d, t) {
      const self = this;
      let currentNum = d.length;
      let userIn = false;
      d.forEach(v => {
        if (v.partnerUuid == self.$uuid) {
          userIn = true;
        }
      });
      if (self.routeType == 1) {
        self.officialTaskList[t].dtlStatus = true;
        self.officialTaskList[t].currentNum = currentNum;
        self.officialTaskList[t].userIn = userIn;
      } else {
        self.indivTaskList[t].dtlStatus = true;
        self.indivTaskList[t].currentNum = currentNum;
        self.indivTaskList[t].userIn = userIn;
      }
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
        "yy-mm-dd": `${year}年${month}月${days}日`
      };
      return formatTime;
    },
    statusCheck (s = 0) {
      const self = this;
      let geoGet = setInterval(() => {
        if (self.$currentCity) {
          self.getRoute();
          clearInterval(geoGet);
        }
      }, 100);
    },
    createNewTask () {
      const self = this;
      self.addTaskStatus = true;
    },
    cancelNewTask () {
      const self = this;
      self.addTaskStatus = false;
    },
    routerDirect (t) {
      const self = this;
      self.$router.push(t);
    }
  }
};
</script>

<style scoped lang="scss">
$designWidth: 750;
@import "@/assets/library/px2rem.scss";
.index-main-container {
  width: 100%;
  height: 100%;
  background-color: #e5e5e5;
  overflow: scroll;
  //TODO
  .rendering {
    color: #999999;
    background-color: #999999;
    border-radius: px2rem(5);
  }
  .base-header {
    display: flex;
    margin-top: px2rem(32);
    justify-content: space-between;
    .create-text-container {
      text-align: left;
      .text-header {
        padding-left: px2rem(24);
        border-left: px2rem(6) solid #2b44ff;
        font-size: px2rem(30);
        font-weight: 500;
      }
      .text-detail {
        padding-left: px2rem(30);
        font-size: px2rem(22);
        color: #999999;
      }
    }
  }
  .slider-container {
    width: 100%;
    height: px2rem(258);
    margin-top: px2rem(102);
    margin-bottom: px2rem(16);
    img {
      width: 100%;
      height: 100%;
    }
  }
  .create-task-container {
    width: 100%;
    height: px2rem(464);
    background-color: #ffffff;
    overflow: hidden;
    .create-body {
      height: px2rem(316);
      margin: px2rem(24) px2rem(24) 0 px2rem(24);
      background: #ffffff;
      box-shadow: 0px 0px px2rem(20) rgba(0, 0, 0, 0.06);
      border-radius: px2rem(16);
      box-sizing: border-box;
      .create-input-container {
        height: px2rem(104);
        margin: 0 px2rem(24);
        display: flex;
        align-items: center;
        box-sizing: border-box;
        &.had-bottom-line {
          border-bottom: px2rem(1) solid #e8e8e8;
        }
        .point {
          width: px2rem(14);
          height: px2rem(14);
          border-radius: 50%;
          &.yellow-bg {
            background-color: #ffb72b;
          }
          &.blue-bg {
            background-color: #2b44ff;
          }
          &.red-bg {
            background-color: #ff512b;
          }
        }
        .input-part {
          width: 100%;
          input {
            display: block;
            width: 100%;
            height: 100%;
            padding-left: px2rem(24);
            font-size: px2rem(26);
          }
        }
      }
    }
  }
  .route-selector-container {
    width: 100%;
    height: px2rem(80);
    display: flex;
    background-color: #ffffff;
    border-bottom: px2rem(2) solid #e8e8e8;
    .route-bar {
      width: 50%;
      font-size: px2rem(26);
      line-height: px2rem(80);
      margin: 0 px2rem(8);
      &.active {
        border-bottom: px2rem(4) solid #000;
      }
    }
  }
  .route-display-container {
    width: 100%;
    // height: px2rem(882);
    background-color: #ffffff;
    overflow: hidden;
    .route-panel {
      height: px2rem(380);
      margin: px2rem(24) px2rem(24);
      background: #ffffff;
      box-shadow: 0px 0px px2rem(20) rgba(0, 0, 0, 0.06);
      border-radius: px2rem(16);
      .route-client-info {
        height: px2rem(42);
        display: flex;
        align-items: center;
        padding: px2rem(30) 0 0 px2rem(24);
        .client-name {
          font-size: px2rem(30);
          line-height: px2rem(42);
        }
        .client-tag-container {
          display: flex;
          .type-tag {
            height: px2rem(42);
            font-size: px2rem(20);
            line-height: px2rem(42);
            padding: 0 px2rem(5);
            border-radius: px2rem(8);
            margin: 0 px2rem(8);
            &.red-bg {
              background-color: rgba(255, 81, 43, 0.1);
              color: #ff512b;
            }
            &.yellow-bg {
              background: rgba(255, 145, 43, 0.1);
              color: #ff912b;
            }
          }
        }
      }
      .route-location-info {
        .location-info {
          margin-top: px2rem(25);
          display: flex;
          .position-signal {
            width: px2rem(14);
            height: px2rem(14);
            border-radius: 50%;
            background-color: #ffb72b;
            margin: px2rem(15) 0 0 px2rem(24);
            .yellow-bg {
              background-color: #ffb72b;
            }
          }
          .position-text-container {
            .location {
              height: px2rem(40);
              display: flex;
              align-items: baseline;
              margin-left: px2rem(18);
              font-size: px2rem(30);
              .distance {
                margin-left: px2rem(16);
                font-size: px2rem(22);
                color: #999999;
              }
            }
            .postcode {
              margin-left: px2rem(18);
              font-size: px2rem(22);
              color: #666666;
            }
          }
        }
        .spilt-line {
          height: px2rem(2);
          margin: px2rem(24) px2rem(24) px2rem(16) px2rem(24);
          background-color: #f5f5f5;
        }
      }
      .route-status-container {
        display: flex;
        justify-content: space-between;
        align-items: center;
        margin: 0 px2rem(24);
        .route-time {
          font-size: px2rem(26);
        }
        .apply-btn {
          width: px2rem(160);
          height: px2rem(56);
          border-radius: px2rem(28);
          font-size: px2rem(22);
          line-height: px2rem(56);
          &.active {
            border: px2rem(2) solid #2b44ff;
            color: #2b44ff;
          }
          &.done {
            background-color: #eeeeee;
            color: #999999;
          }
        }
      }
    }
    .route-more {
      font-size: px2rem(26);
      margin-bottom: px2rem(16);
    }
  }
  .data-display-container {
    //TODO
    display: flex;
    flex-direction: column;
    width: 100%;
    overflow: hidden;
    margin-top: px2rem(16);
    background-color: #ffffff;
    .base-header {
      height: px2rem(100);
      .update-time {
        width: px2rem(294);
        height: px2rem(32);
        font-size: px2rem(20);
        background-color: #eeeeee;
        color: #999999;
        line-height: px2rem(32);
        border-top-left-radius: px2rem(16);
        border-bottom-left-radius: px2rem(16);
      }
    }
    .text-data-container {
      display: flex;
      // justify-content: space-around;
      .text-data-detail {
        width: px2rem(176);
        height: px2rem(88);
        .num {
          font-size: px2rem(36);
          font-family: PingFang SC;
          font-style: normal;
          font-weight: 600;
          &.red-font {
            color: #ef3d3d;
          }
          &.yellow-font {
            color: #ef723d;
          }
          &.blue-font {
            color: #2b44ff;
          }
          &.purple-font {
            color: #656eb0;
          }
        }
        .desc {
          font-size: px2rem(24);
        }
      }
    }
    .map-data-container {
      width: 100%;
      height: px2rem(816);
      div[data-highcharts-chart] {
        height: px2rem(816) !important;
      }
      #highcharts-nhagfnv-0 {
        height: px2rem(816) !important;
      }
    }
  }
  .news-display-container {
    width: 100%;
    background-color: #ffffff;
    overflow: hidden;
    margin-top: px2rem(16);
    padding-bottom: px2rem(20);
    .news-body {
      margin: px2rem(24) px2rem(24) 0 px2rem(24);
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
      .news-more {
        margin-top: px2rem(16);
        font-size: px2rem(26);
      }
    }
  }
  .addnew-task-btn {
    position: fixed;
    bottom: px2rem(16);
    left: 50%;
    transform: translateX(-50%);
    width: px2rem(696);
    height: px2rem(88);
    line-height: px2rem(88);
    background-color: #2b44ff;
    border-radius: px2rem(44);
    font-size: px2rem(30);
    color: #ffffff;
  }
}
</style>
