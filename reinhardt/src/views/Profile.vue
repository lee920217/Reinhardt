<template>
  <div class="ov-profile-container">
    <div class="back-item" v-on:touchstart="redirectTask"></div>
    <div class="profile-info">
      <div class="avatar">
        <img v-if="$gender == 1" src="@/assets/img/male.png" />
        <img v-if="$gender == 0" src="@/assets/img/female.png" />
      </div>
      <div class="text-info">
        <div class="username">{{ $userId }}</div>
        <div class="email">{{ $email }}</div>
      </div>
    </div>
    <div class="spilt-line"></div>
    <div ref="iscroll" class="route-scroll-container">
      <div>
        <RoutePanel
          v-for="i in taskList"
          v-bind:key="`all-${i.tid}`"
          :panelData="i"
          :renderStatus="renderStatus"
        />
      </div>
    </div>
  </div>
</template>

<script>
import { Post } from "@/assets/api/api.js";

import RoutePanel from "@/components/common/RoutePanel.vue";
import BScroll from "better-scroll";
import { exportAddress } from "@/assets/api/setting.js";
export default {
  name: "Profile",
  data () {
    return {
      taskList: {},
      clickStatus: false,
      scroll: null,
      renderStatus: false
    };
  },
  components: {
    RoutePanel,
  },
  // beforeCreate () {
  //   const self = this;
  //   if (!self.$userId || !self.$uuid || !self.$userName || !self.$gender || !self.$email) {
  //     self.$router.push({ path: '/user' })
  //     return
  //   }
  // },
  created () {
    const self = this;
    if (!self.$userId || !self.$uuid || !self.$userName || !self.$gender || !self.$email) {
      self.$router.push("/user");
      return;
    }
  },
  mounted () {
    this.initClickStatus();
    this.getTaskList();
  },
  methods: {
    refreshSroll () {
      const self = this;
      const el = self.$refs.iscroll;
      el.refresh();
    },
    checkUserStatus () {
      const self = this;
      if (!self.$userId || !self.$uuid || !self.$userName || !self.$gender || !self.$email) {
        self.$router.push("/user");
      }
    },
    redirectToTaskDtl (info) {
      const self = this;
      info.id = info.tid;
      if (!self.clickStatus) {
        return;
      }
      self.$router.push({ name: "Task", params: info });
    },
    initClickStatus () {
      const self = this;
      self.clickStatus = false;
      setTimeout(() => {
        self.clickStatus = true;
      }, 1000);
    },
    redirectTask () {
      const self = this;
      self.$router.push("/");
    },
    getTaskList (v = {}) {
      const self = this;
      if ((v = {})) {
        v = {
          //uuid: self.$uuid,
          uuid: self.$uuid
        };
      }
      Post(`${exportAddress.task}/joinedTask`, { query: v }).then(res => {
        if (res.code !== 0) {
          self.errorData = {
            errno: 1,
            errmsg: "获取列表失败",
            redirect: 0,
            path: "/"
          };
        } else {
          let newObj = {};
          for (let j = 0; j < res.data.length; j++) {
            let dataJ = res.data[j]
            self.getRouteDtl(dataJ.tid)
            dataJ.startCode = dataJ.startCode.split(",");
            dataJ.targetCode = dataJ.targetCode.split(",");
            let newTime = new Date(dataJ.startTime);
            let year = newTime.getFullYear();
            let month = newTime.getMonth() + 1;
            let days = newTime.getDate();
            let hours = newTime.getHours();
            let minutes = newTime.getMinutes();
            let seconds = newTime.getSeconds();
            dataJ.startTime = `${year}-${month}-${days} ${hours}:${minutes}`;
            dataJ.Time = `${hours}:${minutes}`;
            dataJ.Date = `${year}-${month}-${days}`;
            newObj[dataJ.tid] = dataJ
          }
          self.taskList = newObj;
          self.renderStatus = true;
          self.registerScrollCt()
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
      const taskList = self.taskList;
      self.$set(taskList[t], 'userIn', userIn);
      self.$set(taskList[t], 'dtlStatus', true);
      self.$set(taskList[t], 'currentNum', currentNum);
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
  }
};
</script>

<style lang="scss" scope>
$designWidth: 750;
@import "@/assets/library/px2rem.scss";
.ov-profile-container {
  .back-item {
    width: px2rem(50);
    height: px2rem(50);
    position: absolute;
    top: px2rem(40);
    right: px2rem(40);
    background-image: url("../assets/img/quit.png");
    background-size: contain;
    background-repeat: no-repeat;
  }
  .profile-info {
    display: flex;
    padding: px2rem(80) px2rem(40);
    .avatar {
      width: px2rem(80);
      height: px2rem(80);
      border-radius: 50%;
      background-color: #f2f2f2;
      overflow: hidden;
      margin-right: px2rem(40);
      img {
        width: 100%;
        height: 100%;
      }
    }
    .text-info {
      display: flex;
      flex-direction: column;
      justify-content: space-between;
      text-align: left;
      .username {
        font-size: px2rem(30);
      }
      .email {
        font-size: px2rem(25);
      }
    }
  }
  .spilt-line {
    height: px2rem(1);
    background-color: #b5b3b3;
    margin: 0 px2rem(30);
  }
  .scroll-view {
    /* -- Attention: This line is extremely important in chrome 55+! -- */
    touch-action: none;
    /* -- Attention-- */
    position: fixed;
    top: px2rem(250);
    bottom: px2rem(20);
    left: 0;
    right: 0;
    overflow: hidden;
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
}
</style>
