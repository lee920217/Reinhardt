<template>
  <div class="route-panel" :class="[renderStatus ? '' : 'rendering']">
    <div class="route-client-info">
      <div class="client-name"></div>
      <div class="client-tag-container">
        <div class="type-tag red-bg">
          {{ panelData.trafficType == 1 ? "步行" : "" }}
          {{ panelData.trafficType == 2 ? "4座汽车" : "" }}
          {{ panelData.trafficType == 3 ? "8座汽车" : "" }}
          {{ panelData.trafficType == 4 ? "巴士" : "" }}
        </div>
        <div
          class="type-tag yellow-bg"
          v-if="panelData.dtlStatus"
        >{{renderStatus ? `人数${panelData.currentNum}/`: ''}}{{ panelData.scaleLimit }}</div>
      </div>
    </div>
    <div class="route-location-info">
      <div class="start-location-info location-info">
        <div class="position-signal"></div>
        <div class="position-text-container">
          <div class="location">
            {{ panelData.startCode[0] }}
            <!-- <div class="distance">{{ distance }} miles</div> -->
          </div>
          <div class="postcode">{{ panelData.start }}, {{ panelData.startCode[1] }}, United Kingdom</div>
        </div>
      </div>
      <div class="end-location-info location-info">
        <div class="position-signal"></div>
        <div class="position-text-container">
          <div class="location">{{ panelData.targetCode[0] }}</div>
          <div
            class="postcode"
          >{{ panelData.target }}, {{ panelData.targetCode[1] }}, United Kingdom</div>
        </div>
      </div>
      <div class="spilt-line"></div>
    </div>
    <div class="route-status-container">
      <div class="route-time">{{ panelData.Date }} {{ panelData.Time }}</div>
      <div class="apply-btn quit-route" v-if="panelData.userIn" @touchstart="quitRoute">退出行程</div>
      <div
        class="apply-btn"
        :class="[panelData.currentNum < panelData.scaleLimit && !panelData.userIn ? 'active' : 'disable']"
        v-if="panelData.dtlStatus"
        @touchstart.prevent="joinRoute"
      >
        {{panelData.currentNum == panelData.scaleLimit ? '路线已满': ''}}
        {{panelData.userIn ? '已加入': '申请加入'}}
      </div>
    </div>
  </div>
</template>

<script>
import { Get, Post } from "@/assets/api/api.js";
import { calDistance } from "@/assets/api/distance.js";
import { exportAddress } from "@/assets/api/setting.js";

export default {
  name: "RoutePanel",
  props: {
    panelData: {
      type: Object
    },
    renderStatus: {
      type: Boolean
    },
    pageType: {
      type: Number,
      default: 0
    }
  },
  data () {
    return {
      distance: "",
      routeData: {
        startCode: [],
        targetCode: []
      },
    };
  },
  mounted () {
    // this.localiseData();
  },
  methods: {
    localiseData () {
      const self = this;
      self.routeData = self.panelData;
    },
    getMilesOfStart () {
      const self = this;
      const cLati = self.$latitude;
      const cLont = self.$longtitude;
      Get(`${exportAddress.postcodesio}/postcodes/${self.routeData.startCode[1]}`).then(res => {
        if (res.status == 200 && res.result) {
          const distance = calDistance(cLati, cLont, res.result.latitude, res.result.longitude);
          self.distance = distance;
        }
      });
    },
    GoToPage () {
      /**
       * 1为执行操作
       * 2为调回登录
       */
      const self = this;
      if (self.pageType == 1) {
        return true
      } else {
        return false
      }
    },
    joinRoute () {
      const self = this;
      const uuid = self.$uuid;
      const pageRlt = self.GoToPage();
      if (!pageRlt) {
        if (self.$userId == -1) {
          self.$router.push('/user');
          return
        } else {
          self.$router.push({ name: "Task", params: self.panelData })
          return
        }
      }
      if (self.$userId == -1) {
        self.$router.push('/user');
        return
      } else {
        if (self.panelData.userIn) {
          return
        }
        Post(`${exportAddress.task}/join`, {
          query: {
            tid: self.panelData.tid,
            partnerUuid: self.$uuid,
            message: '加入了行程'
          }
        }).then(res => {
          if (res.code == 0) {
            self.$emit('updateTeamStauts', { userIn: true });
          }
        })
      }
    },
    quitRoute () {
      const self = this;
      Post(`${exportAddress.task}/secede`, {
        query: {
          tid: self.panelData.tid,
          partnerUuid: self.$uuid,
        }
      }).then(res => {
        if (res.code == 0) {
          self.$emit('updateTeamStauts', { userIn: false });
        }
      })
    },
    directToDtl (t) {
      const self = this;
      if (self.renderStatus && self.panelData.dtlStatus) {
        self.$router.push({ name: "Task", params: self.panelData })
      }
    }
  }
};
</script>

<style scope lang="scss">
$designWidth: 750;
@import "@/assets/library/px2rem.scss";
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
          text-align: left;
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
      text-align: center;
      &.quit-route {
        color: #ff512b;
        border: px2rem(2) solid #ff512b;
      }
      &.active {
        border: px2rem(2) solid #2b44ff;
        color: #2b44ff;
      }
      &.disable {
        background-color: #eeeeee;
        color: #999999;
      }
    }
  }
  &.rendering {
    color: #bbbbbb;
    .client-name {
      width: px2rem(90);
      height: px2rem(42);
      background-color: #bbbbbb;
      border-radius: px2rem(5);
    }
    .client-tag-container {
      .type-tag {
        width: px2rem(76);
        height: px2rem(32);
        background-color: #bbbbbb;
        border-radius: px2rem(5);
      }
    }
    .route-location-info {
      .location-info {
        .position-text-container {
          .location {
            width: px2rem(276);
            height: px2rem(32);
            background-color: #bbbbbb;
            color: #bbbbbb;
            border-radius: px2rem(5);
            .distance {
              font-size: 0 !important;
            }
          }
          .postcode {
            width: px2rem(358);
            height: px2rem(26);
            margin-top: px2rem(8);
            background-color: #bbbbbb;
            color: #bbbbbb;
            border-radius: px2rem(5);
            font-size: 0 !important;
          }
        }
      }
    }
    .route-status-container {
      .route-time {
        width: px2rem(230);
        height: px2rem(36);
        background-color: #bbbbbb;
        color: #bbbbbb;
        border-radius: px2rem(5);
      }
      .apply-btn {
        color: #eeeeee !important;
      }
    }
  }
}
</style>
