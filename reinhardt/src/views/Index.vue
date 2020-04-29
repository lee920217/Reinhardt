<template>
  <div class="index-main-container">
    <CookieMask v-if="maskStatus" @hideMask="hideMask" />
    <LMask v-if="maskStatus" />
    <ToastMessage v-if="ToastMsg" :msg="ToastMsg" />
    <AnimationMap v-if="animationStatus" @changeAnimationStatus="changeAnimationStatus" />
    <DenoteDialog v-if="denote" @changeDenoteDialog="changeDenoteDialog" />
    <Header @changeDenoteDialog="changeDenoteDialog" />
    <AddTask
      v-if="addTaskStatus"
      @cancelAddTaskDialog="cancelNewTask"
      :basicTravelData="basicTravelData"
    />
    <!-- <div class="slider-container">
      <img src="@/assets/img/slider.png" />
    </div>-->
    <!-- <div class="for-nconv2019">
      <a target="_blank" href="https://forms.gle/doSUTnvpmRrhjqu98">留英华人健康包申领 >>>></a>
    </div>-->
    <div class="data-display-container">
      <div class="base-header">
        <div class="create-text-container">
          <div class="text-header">英国确诊人数</div>
          <div class="text-detail">勤洗手，减少出门</div>
        </div>
        <div class="update-time">更新于 2020-04-30 09:00</div>
        <div class="show-animation" v-on:click="changeAnimationStatus(true)">
          <div class="button">疫情蔓延动态展示</div>
          <div class="new">New!</div>
        </div>
      </div>
      <div class="text-data-container">
        <div class="text-data-detail">
          <div class="num red-font">{{ dataSet.total }}</div>
          <div class="num-add red-font">+{{ dataSet.totalInc }}</div>
          <div class="desc">确诊</div>
        </div>
        <div class="text-data-detail">
          <div class="num yellow-font">{{ dataSet.cured }}</div>
          <div class="desc">治愈人数</div>
        </div>
        <div class="text-data-detail">
          <div class="num blue-font">{{ dataSet.death }}</div>
          <div class="num-add blue-font">+{{ dataSet.deathInc }}</div>
          <div class="desc">死亡</div>
        </div>
        <div class="text-data-detail">
          <div class="num green-font">{{ dataSet.test }}</div>
          <div class="num-add green-font">+{{ dataSet.testInc }}</div>
          <div class="desc">测试</div>
        </div>
      </div>
      <div class="text-data-container">
        <div class="text-data-detail">
          <div
            class="num light-yellow-font"
          >{{ ((dataSet.totalInc / dataSet.testInc)*100).toFixed(2)}}%</div>
          <div class="desc">确诊率</div>
        </div>
        <div class="text-data-detail">
          <div
            class="num light-green-font"
          >{{ ((dataSet.cured / dataSet.total) * 100).toFixed(2) }}%</div>
          <div class="desc">康复率</div>
        </div>
        <div class="text-data-detail">
          <div class="num white-font">{{ ((dataSet.death / dataSet.total) * 100).toFixed(2) }}%</div>
          <div class="desc">死亡率</div>
        </div>
      </div>
      <div class="map-data-container">
        <highcharts :constructor-type="'mapChart'" :options="UKMapSettings" ref="ukmap"></highcharts>
      </div>
    </div>
    <div class="chart-total-container chart-display-header">
      <div class="header">疫情地区变化</div>
      <div class="input-postcode">
        <input type="text" v-model="searchPostcode" placeholder="输入邮编查询您地区情况" />
        <div class="search-btn" v-on:click="checkAreaStatus">{{searchStatus}}</div>
      </div>
      <div v-if="areaLocation" class="area-text">
        <span class="location">{{areaLocation}}</span>地区共有
        <span class="confirmed-num">{{areaNum}}</span>例新冠肺炎，减少出门，注意安全
      </div>
    </div>
    <transition name="slide-show" v-if="AreaSettings">
      <div class="chart-display-container">
        <highcharts :constructor-type="'mapChart'" :options="AreaSettings" ref="areaChart"></highcharts>
      </div>
    </transition>
    <div class="chart-total-container chart-display-header">
      <div class="header">总确诊数</div>
      <div class="time-limit">
        <div
          class="time-item"
          :class="[totalChartTime == 0 ? 'active': '']"
          v-on:click="totoalChartTimeUpdate(0, 1)"
        >全部</div>
        <div
          class="time-item"
          :class="[totalChartTime == 1 ? 'active': '']"
          v-on:click="totoalChartTimeUpdate(1, 1)"
        >30天内</div>
        <div
          class="time-item"
          :class="[totalChartTime == 2 ? 'active': '']"
          v-on:click="totoalChartTimeUpdate(2, 1)"
        >15天内</div>
        <div
          class="time-item"
          :class="[totalChartTime == 3 ? 'active': '']"
          v-on:click="totoalChartTimeUpdate(3, 1)"
        >一周内</div>
      </div>
    </div>
    <div class="chart-display-container">
      <highcharts :constructor-type="'mapChart'" :options="ChartSettings" ref="totalChart"></highcharts>
    </div>

    <div class="chart-total-container chart-display-header">
      <div class="header">增长速度</div>
      <div class="time-limit">
        <div
          class="time-item"
          :class="[increaseChartTime == 0 ? 'active': '']"
          v-on:click="totoalChartTimeUpdate(0, 2)"
        >全部</div>
        <div
          class="time-item"
          :class="[increaseChartTime == 1 ? 'active': '']"
          v-on:click="totoalChartTimeUpdate(1, 2)"
        >30天内</div>
        <div
          class="time-item"
          :class="[increaseChartTime == 2 ? 'active': '']"
          v-on:click="totoalChartTimeUpdate(2, 2)"
        >15天内</div>
        <div
          class="time-item"
          :class="[increaseChartTime == 3 ? 'active': '']"
          v-on:click="totoalChartTimeUpdate(3, 2)"
        >一周内</div>
      </div>
    </div>
    <div class="chart-display-container">
      <highcharts
        :constructor-type="'mapChart'"
        :options="IncreaseChartSettings"
        ref="increaseChart"
      ></highcharts>
    </div>
    <div class="footer">
      <div class="source-ct">
        <div class="source-title">数据来源</div>
        <div class="source-item">
          <a
            href="https://www.gov.uk/government/publications/coronavirus-covid-19-number-of-cases-in-england/coronavirus-covid-19-number-of-cases-in-england"
          >GOVUK -- 英国政府官方数据</a>
        </div>
        <div class="source-item">
          <a
            href="https://en.wikipedia.org/wiki/2020_coronavirus_pandemic_in_the_United_Kingdom"
          >wikipedia - 维基百科</a>
        </div>
        <div class="source-item">
          <a
            href="https://www.arcgis.com/apps/opsdashboard/index.html#/f94c3c90da5b4e9f9a0b19484dd4bb14"
          >Arcgis</a>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import Header from "@/components/common/OVHeader.vue";
import RoutePanel from "@/components/common/RoutePanel.vue";
import AnimationMap from "@/components/Animation.vue";
import AddTask from "@/components/common/AddTask.vue";
import CookieMask from "@/components/common/CookieMask.vue";
import LMask from "@/components/common/Mask.vue";
import NewsPanel from "@/components/common/NewsPanel.vue";
import ToastMessage from "@/components/common/Toast.vue";
import DenoteDialog from "@/components/common/DenoteDialog.vue";
import UKMapSettings from "@/assets/data/uk-map.js";
import ChartSettings from "@/assets/data/uk-ncov2019-chart.js";
import IncreaseChartSettings from "@/assets/data/uk-nconv2019-increase.js";
import areaChart from "@/assets/data/uk-ncov2019-area.js";
import TWEEN from "@tweenjs/tween.js";
import { Get, Post } from "@/assets/api/api.js";
import { exportAddress } from "@/assets/api/setting.js";
import { dateList, totalNum, deathNum, increaseNum, testNum, hospitalNum } from "@/assets/api/chartData.js";
import cityData from "@/assets/data/city-map.js";


export default {
  name: "Index",
  components: {
    Header,
    // RoutePanel,
    // AddTask,
    // NewsPanel,
    CookieMask,
    LMask,
    ToastMessage,
    AnimationMap,
    DenoteDialog
  },
  data () {
    return {
      animationStatus: false,
      UKMapSettings: UKMapSettings,
      ChartSettings: ChartSettings,
      IncreaseChartSettings: IncreaseChartSettings,
      AreaSettings: null,
      /**
       * @routeType
       * @ 1 => 个人路线
       * @ 2 => 官方路线
       */
      touchChangeType: "text",
      ToastMsg: '',
      routeType: 1,
      renderStatus: false,
      officialTaskList: {},
      indivTaskList: {},
      maskStatus: false,
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
          title: "国际航班锐减1477班仅剩131班，民航局“4个1”新规29日执行",
          source: "中国民航局",
          time: "2020, Mar 26th, 00:00"
        },
      },
      dataSet: {
        total: 0,
        death: 0,
        cured: 0,
        test: 0,
        hospital: 0,
        totalInc: 0,
        deathInc: 0,
        curedInc: 0,
        testInc: 0,
        hospitalInc: 0,
      },
      totalChartTime: 0,
      increaseChartTime: 0,
      animationList: {},
      addTaskStatus: false,
      cityDataShow: false,
      searchPostcode: '',
      areaLocation: '',
      areaNum: '',
      searchStatus: '搜索',
      denote: false,
    };
  },
  mounted () {
    // this.showMask();
    this.showNumAnimation();
    // // this.statusCheck();
    // this.getRoute();
    // this.ttt();
  },
  methods: {
    // showMask () {
    //   const self = this;
    //   if (self.$cookieConfirm) {
    //     self.maskStatus = false;
    //   } else {
    //     self.maskStatus = true;
    //   }
    // },
    changeDenoteDialog (r) {
      const self = this;
      self.denote = r;
    },
    changeAnimationStatus (status) {
      const self = this;
      self.animationStatus = status;
    },
    limitChange (arr, aim) {
      return arr.slice(aim)
    },
    totoalChartTimeUpdate (t, c) {
      const self = this;
      let limitLength = 0;
      let timeLimit = dateList;
      if (t == 0) {
        limitLength = -timeLimit.length;
      } else {
        if (t == 1) {
          limitLength = -30
        } else if (t == 2) {
          limitLength = -15
        } else if (t == 3) {
          limitLength = -7
        }
      }
      timeLimit = self.limitChange(timeLimit, limitLength);
      if (c == 1) {
        const totalChart = self.$refs.totalChart.chart
        let totalList = totalNum;
        let deathList = deathNum;
        totalList = self.limitChange(totalList, limitLength);
        deathList = self.limitChange(deathList, limitLength);
        totalChart.xAxis[0].update({
          categories: timeLimit
        });
        totalChart.series[0].update({
          data: totalList
        })
        totalChart.series[1].update({
          data: deathList
        })
        self.totalChartTime = t;
      } else {
        const increaseChart = self.$refs.increaseChart.chart
        let increaseList = increaseNum;
        increaseList = self.limitChange(increaseList, limitLength);
        increaseChart.xAxis[0].update({
          categories: timeLimit
        })
        increaseChart.series[0].update({
          data: increaseList
        });
        self.increaseChartTime = t;
      }
    },
    hideMask () {
      const self = this;
      self.maskStatus = false;
      self.$acceptCookie();
    },
    showNumAnimation () {
      const self = this;
      const targetValue = {
        total: totalNum[totalNum.length - 1],
        death: deathNum[deathNum.length - 1],
        cured: 359,
        hospital: hospitalNum[hospitalNum.length - 1],
        test: testNum[testNum.length - 1],
        totalInc: totalNum[totalNum.length - 1] - totalNum[totalNum.length - 2],
        deathInc: deathNum[deathNum.length - 1] - deathNum[deathNum.length - 2],
        curedInc: 0,
        testInc: testNum[testNum.length - 1] - testNum[testNum.length - 2],
        hospitalInc: hospitalNum[hospitalNum.length - 1] - hospitalNum[hospitalNum.length - 2]
      };
      const objKey = Object.keys(self.dataSet);
      for (let i = 0; i < objKey.length; i++) {
        self.tweenJS(0, targetValue[objKey[i]], objKey[i]);
      }
    },
    tweenJS (start, end, key) {
      let frameHandler;
      const self = this;
      const animate = function (currentTime) {
        TWEEN.update(currentTime);
        frameHandler = requestAnimationFrame(animate);
      };
      const myTween = new TWEEN.Tween({ tweeningValue: start })
        .to({ tweeningValue: end }, 1000)
        .onUpdate(() => {
          self.dataSet[key] = myTween._object.tweeningValue.toFixed(0);
        })
        .onComplete(() => {
          // Make sure to clean up after ourselves.
          cancelAnimationFrame(frameHandler);
        })
        .start();
      frameHandler = requestAnimationFrame(animate);
    },
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
          let newTmp = {};
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

            newTmp[dataJ.tid] = dataJ;
          }
          if (self.routeType == 1) {
            self.officialTaskList = newTmp;
          } else {
            self.indivTaskList = newTmp;
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
      let newList = [];
      if (self.routeType == 1) {
        newList = self.officialTaskList;
        newList[t].dtlStatus = true;
        newList[t].currentNum = currentNum;
        newList[t].userIn = userIn;
      } else {
        newList = self.indivTaskList;
        newList[t].dtlStatus = true;
        newList[t].currentNum = currentNum;
        newList[t].userIn = userIn;
      }
      if (self.routeType == 1) self.officialTaskList = newList;
      if (self.routeType == 2) self.indivTaskList = newList;
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
      self.ToastMsg = '';
      // setTimeout(() => {
      //   self.ToastMsg = ""
      // }, 3000);
      // self.addTaskStatus = true;
    },
    cancelNewTask () {
      const self = this;
      self.addTaskStatus = false;
    },
    routerDirect (t) {
      const self = this;
      self.$router.push(t);
    },
    toastMsgWarning (msg) {
      const self = this;
      self.ToastMsg = msg;
      setTimeout(() => {
        self.ToastMsg = "";
      }, 1000);
    },
    checkAreaStatus () {
      const self = this;
      const postCode = self.searchPostcode;
      self.searchStatus = '搜索中……'
      const status = self.postcodeVaild(postCode);
      if (status) {
        self.getAreaName(postCode);
      } else {
        self.toastMsgWarning('邮编错误')
      }
    },
    getAreaName (postcode) {
      const self = this;
      let requestAddr = exportAddress;
      const c = cityData;
      Get(`${requestAddr.postcodesio}/postcodes/${postcode}`).then(res => {
        const area = res.result.admin_district;
        const adminDistrictCode = res.result.codes.admin_district;
        // areaChart
        const totalDate = c[adminDistrictCode].totalDate;
        const totalNum = c[adminDistrictCode].totalNum;
        const dailyNum = c[adminDistrictCode].dailyNum;
        const newChart = areaChart(totalDate, totalNum, totalDate, dailyNum)
        self.AreaSettings = newChart;
        self.areaLocation = area;
        self.areaNum = c[adminDistrictCode].total;
        self.searchStatus = '搜索';
        // console.log(area, adminDistrictCode);
      })
    },
    postcodeVaild (postCode) {
      const self = this;
      const re =
        "^([A-Za-z][A-Ha-hJ-Yj-y]?[0-9][A-Za-z0-9]? ?[0-9][A-Za-z]{2}|[Gg][Ii][Rr] ?0[Aa]{2})$";
      if (postCode.match(re) == null) {
        return false
      }
      return true
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
  background-color: #2f2e2e;
  overflow-y: scroll;
  //max-width: px2rem(1250);
  margin: 0 auto;
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
  .for-nconv2019 {
    margin: px2rem(112) 0 px2rem(24) 0;
    padding: px2rem(24) px2rem(24);
    background-color: #232322;
    color: #ffffff;
    font-size: px2rem(24);
    text-align: left;
    a {
      color: #f2f2f2;
      &:link {
        color: #f2f2f2;
      }
      &:visited {
        color: #f2f2f2;
      }
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
    margin-top: px2rem(104);
    background-color: #232322;
    .base-header {
      position: relative;
      height: px2rem(100);
      color: #f2f2f2;
      .update-time {
        width: px2rem(320);
        height: px2rem(32);
        font-size: px2rem(20);
        background-color: #424040;
        color: #999999;
        line-height: px2rem(34);
        border-top-left-radius: px2rem(16);
        border-bottom-left-radius: px2rem(16);
      }
      .show-animation {
        position: absolute;
        top: px2rem(40);
        right: px2rem(20);
        z-index: 96;
        display: flex;
        width: px2rem(300);
        color: #f2f2f2;
        border-radius: px2rem(10);
        margin-left: px2rem(30);
        .button {
          width: px2rem(200);
          height: px2rem(30);
          font-size: px2rem(24);
          padding: 0 px2rem(4);
          margin: 0 px2rem(8);
          line-height: px2rem(40);
        }
        .new {
          height: px2rem(40);
          font-size: px2rem(24);
          padding: px2rem(2) px2rem(3);
          color: red;
          border-radius: px2rem(10);
          line-height: px2rem(30);
        }
      }
    }
    .text-data-container {
      display: flex;
      // justify-content: space-around;

      .text-data-detail {
        width: px2rem(176);
        height: px2rem(128);
        color: #f2f2f2;
        font-weight: bolder;
        .num {
          font-size: px2rem(36);
          font-family: PingFang SC;
          font-style: normal;
          font-weight: 900;
          &.red-font {
            color: #f35252;
          }
          &.yellow-font {
            color: #ff824d;
          }
          &.blue-font {
            color: #3599e2;
          }
          &.purple-font {
            color: #656eb0;
          }
          &.green-font {
            color: #4cc16f;
          }
          &.light-yellow-font {
            color: #f7de25;
          }
          &.light-green-font {
            color: #00f2ff;
          }
          &.white-font {
            color: #f2f2f2;
          }
        }
        .num-add {
          font-size: px2rem(28);
          font-family: PingFang SC;
          font-style: normal;
          font-weight: bolder;
          &.red-font {
            color: #f35252;
          }
          &.yellow-font {
            color: #ff824d;
          }
          &.blue-font {
            color: #3599e2;
          }
          &.purple-font {
            color: #656eb0;
          }
          &.green-font {
            color: #4cc16f;
          }
        }
        .desc {
          font-size: px2rem(24);
        }
      }
    }
    .map-data-container {
      position: relative;
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
  .chart-display-header {
    position: relative;
    margin-top: px2rem(16);
    background-color: #222321;
    .header {
      padding: px2rem(24) 0;
      font-size: px2rem(32);
      color: #f2f2f2;
    }
    .time-limit {
      width: px2rem(400);
      height: px2rem(50);
      display: flex;
      margin-left: px2rem(24);
      border: px2rem(2) solid #ffffff;
      justify-content: space-between;
      border-radius: px2rem(10);
      overflow: hidden;
      .time-item {
        height: px2rem(50);
        line-height: px2rem(50);
        padding: 0 px2rem(12);
        font-size: px2rem(24);
        color: #f2f2f2;
        &.active {
          color: #222321;
          background-color: #ffffff;
        }
        &:hover {
          cursor: pointer;
        }
      }
    }
    .input-postcode {
      display: flex;
      justify-content: center;
      padding-bottom: px2rem(48);
      input {
        width: px2rem(300);
        height: px2rem(48);
        padding: 0 px2rem(20);
        background-color: #222321;
        border: px2rem(2) solid #f2f2f2;
        border-radius: px2rem(10);
        font-size: px2rem(24);
        color: #f2f2f2;
      }
      .search-btn {
        height: px2rem(52);
        padding: 0 px2rem(20);
        margin: 0 px2rem(24);
        border-radius: px2rem(10);
        font-size: px2rem(24);
        line-height: px2rem(52);
        background-color: #3599e2;
        color: #f2f2f2;
      }
    }
    .area-text {
      color: #f2f2f2;
      font-size: px2rem(24);
      padding-bottom: px2rem(48);
      .location {
        font-weight: bolder;
        color: #f7de25;
      }
      .confirmed-num {
        font-weight: bolder;
        color: #f35252;
      }
    }
  }
  .chart-display-container {
    width: 100%;
    //height: px2rem(400);
    background-color: #ffffff;
    // overflow: hidden;
  }
  .footer {
    background-color: #232322;
    margin-top: px2rem(24);
    .source-ct {
      padding: px2rem(24) px2rem(48);
      .source-title {
        font-size: px2rem(28);
        color: #f2f2f2;
        text-align: left;
      }
      .source-item {
        margin-top: px2rem(12);
        font-size: px2rem(24);
        text-align: left;
        text-decoration: none;
        color: #f2f2f2;
        outline: none;
        a {
          color: #f2f2f2;
          &:link {
            color: #f2f2f2;
          }
          &:visited {
            color: #f2f2f2;
          }
        }
      }
    }
  }
  .news-display-container {
    width: 100%;
    background-color: #232322;
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
