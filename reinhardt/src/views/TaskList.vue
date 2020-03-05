<template>
  <div class="task-list-container">
    <OVHeader />
    <Toast :msg="toastMsg" v-if="toastStatus > 0" />
    <MaskView v-if="addTask" />
    <AddTask
      v-if="addTask"
      @hideAddTaskDialog="hideAddTaskDialog"
      @handleError="handleError"
      @activeToast="activeToast"
    />
    <MessageTop v-if="errorData.errno >= 0" :errorData="errorData" />
    <CitySelect v-if="maskStatus" @changeCity="changeCity" @maskControl="maskControl" />
    <div class="task-location-select" v-on:touchstart="maskControl(true)">
      <div class="city-name">{{ currentCity }}</div>
    </div>
    <!-- <div class="task-location-search">
      <input type="text" class="location-search" placeholder="输入地名或者邮编" />
      <div class="search-btn"></div>
    </div>-->
    <iscroll-view ref="iscroll" class="scroll-view">
      <div
        class="task-item"
        v-for="i in taskList"
        v-bind:key="i.tid"
        v-on:click="redirectToTaskDtl(i)"
        :class="[i.type == 1 ? 'active' : 'dangerous']"
      >
        <div class="task-item-dtl">
          <div class="dtl-item current-location">
            <div class="location-name">{{ i.startCode[0] }}</div>
            <div class="location-postcode">{{ i.startCode[1] }}</div>
            <div class="time-info">{{ i.Date }}</div>
            <div class="time-info">{{ i.Time }}</div>
          </div>
          <div class="arrow" v-if="i.type == 1"></div>
          <div class="dtl-item target-location" v-if="i.type == 1">
            <div class="location-name">{{ i.targetCode[0] }}</div>
            <div class="location-postcode">{{ i.targetCode[1] }}</div>
          </div>
        </div>
      </div>
    </iscroll-view>
    <div class="add-task" v-on:touchstart="showAddTaskDialog"></div>
  </div>
</template>

<script>
import { Post } from "@/assets/api/api.js";
import OVHeader from "@/components/common/OVHeader.vue";
import CitySelect from "@/components/common/CitySelect.vue";
import MaskView from "@/components/common/Mask.vue";
import AddTask from "@/components/common/AddTask.vue";
import MessageTop from "@/components/common/MessageTop.vue";
import Toast from "@/components/common/Toast.vue";

export default {
  name: "TaskList",
  components: { OVHeader, CitySelect, MaskView, AddTask, MessageTop, Toast },
  data() {
    return {
      currentCity: "London",
      maskStatus: false,
      addTask: false,
      errorData: {
        errno: -1,
        errmsg: ""
      },
      taskList: [],
      scroll: null,
      clickStatus: false,
      toastMsg: "写错了",
      toastStatus: -1
    };
  },
  mounted() {
    this.initClickStatus();
    this.getTaskList();
  },
  methods: {
    refreshSroll() {
      const self = this;
      const el = self.$refs.iscroll;
      el.refresh();
    },
    initClickStatus() {
      const self = this;
      self.clickStatus = false;
      setTimeout(() => {
        self.clickStatus = true;
      }, 1000);
    },
    getTaskList(v = {}) {
      const self = this;
      if ((v = {})) {
        v = {
          order_: "start",
          start: self.currentCity
        };
      }
      Post("http://localhost:8360/api/task/page", { query: v }).then(res => {
        if (res.code !== 0) {
          self.errorData = {
            errno: 1,
            errmsg: "获取列表失败",
            redirect: 0,
            path: "/"
          };
        } else {
          for (let j = 0; j < res.data.rows.length; j++) {
            res.data.rows[j].startCode = res.data.rows[j].startCode.split(",");
            res.data.rows[j].targetCode = res.data.rows[j].targetCode.split(",");
            let newTime = new Date(res.data.rows[j].startTime);
            let year = newTime.getFullYear();
            let month = newTime.getMonth() + 1;
            let days = newTime.getDate();
            let hours = newTime.getHours();
            let minutes = newTime.getMinutes();
            let seconds = newTime.getSeconds();
            res.data.rows[j].startTime = `${year}-${month}-${days} ${hours}:${minutes}`;
            res.data.rows[j].Time = `${hours}:${minutes}`;
            res.data.rows[j].Date = `${year}-${month}-${days}`;
          }
          self.taskList = res.data.rows;
          self.refreshSroll();
        }
      });
    },
    redirectToTaskDtl(info) {
      const self = this;
      info.id = info.tid;
      if (!self.clickStatus) {
        return;
      }
      self.$router.push({ name: "Task", params: info });
    },
    changeCity(city) {
      const self = this;
      self.currentCity = city;
      self.getTaskList();
    },
    maskControl(bol) {
      const self = this;
      self.maskStatus = bol;
    },
    hideAddTaskDialog() {
      const self = this;
      self.initClickStatus();
      self.addTask = false;
    },
    showAddTaskDialog() {
      const self = this;
      self.addTask = true;
    },
    handleError(data) {
      const self = this;
      self.errorData = data;
      setTimeout(() => {
        self.errorData.errno = -1;
      }, 2000);
      self.initClickStatus();
    },
    activeToast(msg) {
      const self = this;
      self.toastMsg = msg;
      self.toastStatus = 1;
      setTimeout(() => {
        self.toastStatus = -1;
      }, 2000);
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
  background-color: #ffffff;
  .task-location-select {
    position: relative;
    margin: px2rem(100) px2rem(20) px2rem(20) px2rem(20);
    height: px2rem(300);
    box-sizing: border-box;
    background-color: #f2f2f2;
    background-image: url("../assets/img/city.png");
    background-size: cover;
    background-repeat: no-repeat;
    background-position: bottom right;
    border-radius: px2rem(10);
    line-height: px2rem(200);
    color: #003399;
    font-weight: bolder;
    .city-name {
      position: absolute;
      left: px2rem(40);
      font-size: px2rem(60);
    }
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
    margin: px2rem(20) 0 px2rem(10) 0;
    .task-item {
      display: flex;
      width: px2rem(711);
      height: px2rem(301);
      margin: 0 auto px2rem(20) auto;
      border-radius: px2rem(10);
      overflow: hidden;
      &.active {
        // background-color: #ecc029;
        background-image: url("../assets/img/travel-ticket.png");
        background-repeat: no-repeat;
        background-size: px2rem(711) px2rem(301);
        color: #000;
      }
      &.dangerous {
        background-image: url("../assets/img/dangerous-ticket.png");
        background-repeat: no-repeat;
        background-size: px2rem(711) px2rem(301);
      }
      .task-item-dtl {
        display: flex;
        width: 100%;
        color: #000;
        justify-content: space-around;
        align-items: center;
        .dtl-item {
          width: 45%;
          height: px2rem(150);
          font-size: px2rem(28);
          font-weight: bolder;
          margin: px2rem(20) px2rem(12) 0 px2rem(12);
          .location-postcode {
            font-weight: lighter;
            text-align: center;
            overflow: hidden;
            /*文本不会换行*/
            white-space: nowrap;
            /*当文本溢出包含元素时，以省略号表示超出的文本*/
            text-overflow: ellipsis;
          }
          .location-name {
            text-align: center;
            overflow: hidden;
            /*文本不会换行*/
            white-space: nowrap;
            /*当文本溢出包含元素时，以省略号表示超出的文本*/
            text-overflow: ellipsis;
          }
          .time-info {
            font-size: px2rem(20);
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
        width: 20%;
        overflow: hidden;
        padding: 0 px2rem(30);
        margin-top: px2rem(20);
        .time-info {
          font-size: px2rem(28);
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
