<template>
  <div class="task-list-container">
    <OVHeader />
    <MaskView v-if="addTask" />
    <AddTask v-if="addTask" @hideAddTaskDialog="hideAddTaskDialog" />
    <MessageTop v-if="errorData.errno >= 0" :errorData="errorData" />
    <CitySelect v-if="maskStatus" @changeCity="changeCity" @maskControl="maskControl" />
    <div class="task-location-select" v-on:touchstart="maskControl(true)">{{ currentCity }}</div>
    <div class="task-location-search">
      <input type="text" class="location-search" placeholder="输入地名或者邮编" />
      <div class="search-btn"></div>
    </div>
    <iscroll-view class="scroll-view">
      <div class="task-item active">
        <div class="task-item-dtl">
          <div class="dtl-item current-location">
            <div class="location-name">Hardman House</div>
            <div class="location-postcode">L1 9JG</div>
          </div>
          <div class="arrow"></div>
          <div class="dtl-item target-location">
            <div class="location-name">Hardman House</div>
            <div class="location-postcode">L1 9JG</div>
          </div>
        </div>
        <div class="time-team-info">
          <div class="time-info">19:59</div>
          <div class="team-info">
            <div class="team-avatar"></div>
            <div class="team-avatar"></div>
            <div class="team-avatar"></div>
            <div class="team-avatar"></div>
            <div class="team-avatar"></div>
            <div class="team-avatar"></div>
            <div class="team-avatar"></div>
            <div class="team-avatar"></div>
            <div class="team-avatar"></div>
            <div class="team-avatar"></div>
            <div class="team-avatar"></div>
            <div class="team-avatar"></div>
            <div class="team-avatar"></div>
            <div class="team-avatar"></div>
            <div class="team-avatar"></div>
            <div class="team-avatar"></div>
            <div class="team-avatar"></div>
            <div class="team-avatar"></div>
          </div>
        </div>
      </div>
      <div class="task-item dangerous">
        <div class="task-item-dtl">
          <div class="dtl-item current-location">
            <div class="location-name">Hardman House</div>
            <div class="location-postcode">L1 9JG</div>
          </div>
        </div>
        <div class="time-team-info">
          <div class="time-info">19:59</div>
          <div class="team-info">
            <div class="team-avatar"></div>
          </div>
        </div>
      </div>
    </iscroll-view>
    <div class="add-task" v-on:touchstart="showAddTaskDialog"></div>
  </div>
</template>

<script>
import OVHeader from "@/components/common/OVHeader.vue";
import CitySelect from "@/components/common/CitySelect.vue";
import MaskView from "@/components/common/Mask.vue";
import AddTask from "@/components/common/AddTask.vue";
import MessageTop from "@/components/common/MessageTop.vue";

export default {
  name: "TaskList",
  components: { OVHeader, CitySelect, MaskView, AddTask, MessageTop },
  data () {
    return {
      currentCity: "London",
      maskStatus: false,
      addTask: false,
      errorData: {
        errno: -1,
        errmsg: ''
      }
    };
  },
  methods: {
    changeCity (city) {
      const self = this;
      self.currentCity = city;
    },
    maskControl (bol) {
      const self = this;
      self.maskStatus = bol;
    },
    hideAddTaskDialog () {
      const self = this;
      self.addTask = false;
    },
    showAddTaskDialog () {
      const self = this;
      self.addTask = true;
    }
  }
};
</script>

<style lang="scss" scope>
$designWidth: 750;
@import "@/assets/library/px2rem.scss";
.task-list-container {
  width: 100%;
  height: 100%;
  overflow: hidden;
  .task-location-select {
    margin: px2rem(100) px2rem(20) px2rem(20) px2rem(20);
    height: px2rem(200);
    box-sizing: border-box;
    background-color: #5d2b2b;
    border-radius: px2rem(10);
    line-height: px2rem(200);
    color: #f2f2f2;
    font-weight: bolder;
  }
  .task-location-search {
    display: flex;
    padding: 0 px2rem(20);
    align-items: center;
    .location-search {
      flex-grow: 1;
      height: px2rem(80);
      border: px2rem(2) solid #b7b2b2;
      border-radius: px2rem(10);
      padding: 0 px2rem(20);
      font-size: px2rem(25);
    }
    .search-btn {
      width: px2rem(80);
      height: px2rem(80);
      background-color: #5cadff;
      background-image: url("../assets/img/search.png");
      background-size: px2rem(40) px2rem(40);
      background-position: 50% 50%;
      background-repeat: no-repeat;
      margin-left: px2rem(20);
      border-radius: px2rem(10);
    }
  }
  .scroll-view {
    /* -- Attention: This line is extremely important in chrome 55+! -- */
    touch-action: none;
    /* -- Attention-- */
    position: fixed;
    top: px2rem(400);
    bottom: 0;
    left: 0;
    right: 0;
    overflow: hidden;
    .task-item {
      width: px2rem(710);
      height: px2rem(200);
      margin: px2rem(20) auto px2rem(20) auto;
      border-radius: px2rem(10);
      overflow: hidden;
      &.active {
        background-color: #5cadff;
      }
      &.dangerous {
        background-color: #ed4014;
      }
      .task-item-dtl {
        display: flex;
        color: #f2f2f2;
        .dtl-item {
          height: px2rem(90);
          font-size: px2rem(35);
          font-weight: bolder;
          margin: px2rem(20) px2rem(30) 0 px2rem(30);
          text-align: left;
          .location-postcode {
            font-weight: lighter;
          }
        }
        .arrow {
          width: px2rem(60);
          height: px2rem(90);
          background-image: url("../assets/img/arrow-right.png");
          background-size: contain;
          background-repeat: no-repeat;
          background-position: 50% 50%;
        }
      }
      .time-team-info {
        width: 100%;
        overflow: hidden;
        padding: 0 px2rem(30);
        margin-top: px2rem(20);
        display: flex;
        .time-info {
          font-size: px2rem(40);
          color: #f2f2f2;
          font-weight: bolder;
        }
        .team-info {
          flex: 1;
          padding: 0 px2rem(20);
          display: flex;
          align-items: center;
          .team-avatar {
            width: px2rem(40);
            height: px2rem(40);
            background-color: #f2f2f2;
            border-radius: 50%;
            margin: 0 px2rem(6);
          }
        }
      }
    }
  }
  .add-task {
    position: fixed;
    width: px2rem(100);
    height: px2rem(100);
    right: px2rem(20);
    bottom: px2rem(40);
    background-image: url("../assets/img/add.png");
    background-color: #f2f2f2;
    background-size: contain;
    z-index: 10;
    border-radius: 50%;
  }
}
</style>
