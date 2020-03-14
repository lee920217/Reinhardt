<template>
  <div class="task-view-container">
    <Header />
    <RoutePanel
      class="route-panel"
      :panelData="taskDtl"
      :renderStatus="true"
      @updateTeamStauts="updateTeamStauts"
    />
    <div class="chat-dialog">
      <div class="unteammate-mask" v-if="!taskDtl.userIn">
        <div class="join-btn" v-on:touchstart="joinTask">加入Task</div>
      </div>
      <iscroll-view class="chat-scroll-view">
        <div class="chat-message" v-for="i in messageList" v-bind:key="i.modifyTime">
          <div class="user-name">{{i.uname}}:</div>
          <div class="msg">{{i.message}}</div>
        </div>
      </iscroll-view>
      <div class="input-box">
        <input type="text" placeholder="从这里发送消息" v-model="inputMessage" />
        <div class="enter-btn" v-on:click="postMessage">确认</div>
      </div>
    </div>
  </div>
</template>

<script>
import { Post } from "@/assets/api/api.js";
import RoutePanel from "@/components/common/RoutePanel.vue";
import Header from "@/components/common/OVHeader.vue";
import { exportAddress } from "@/assets/api/setting.js";
export default {
  name: "taskView",
  components: {
    RoutePanel,
    Header
  },
  data () {
    return {
      tid: null,
      taskDtl: {
        targetCode: [],
        startCode: [],
      },
      messageList: [],
      isIn: false,
      inputMessage: '',
      msgloop: ''
    }
  },
  mounted: function () {
    this.getTaskDtl();
    this.loopMessage();
    //this.getMember();
  },
  beforeDestroy () {
    const self = this;
    clearInterval(self.msgloop)
  },
  destroyed () {
    const self = this;
    clearInterval(self.msgloop)
  },
  methods: {
    updateTeamStauts (data) {
      const self = this;
      self.taskDtl.userIn = data.userIn;
    },
    loopMessage () {
      const self = this;
      self.msgloop = setInterval(() => {
        self.getMessage()
      }, 2000)
    },
    getTaskDtl () {
      const self = this;
      const taskInfo = self.$route.params;
      self.taskDtl = taskInfo;
      console.log(self.taskDtl);
      self.tid = parseInt(self.taskDtl.tid);
    },
    redirectToList () {
      const self = this;
      self.$router.go(-1);
    },
    getMember () {
      const self = this;
      const tid = self.tid;
      Post(`${exportAddress.message}/partners`, {
        query: {
          tid: tid
        }
      }).then(res => {
        if (res.code === 0) {
          const data = res.data;
          for (let i = 0; i < data.length; i++) {
            if (data[i].partnerUuid == self.$uuid) {
              self.isIn = true;
              self.getMessage();
            }
          }
        }
      })
    },
    getMessage () {
      const self = this;
      const tid = self.tid;
      Post(`${exportAddress.message}/fetch`, {
        query: {
          tid: tid,
          mid: "0",
          order_: "create_time",
          sort_: "asc",
          pageSize_: "10"
        }
      }).then(res => {
        if (res.code === 0) {
          self.messageList = res.data
        }
      })
    },
    postMessage () {
      const self = this;
      const tid = self.tid;
      const uuid = self.$uuid;
      const message = self.inputMessage;

      Post(`${exportAddress.message}/send`, {
        query: {
          tid: tid,
          uuid: uuid,
          message: message
        }
      }).then(res => {
        self.inputMessage = ""
        self.getMessage();
      })
    },
    joinTask () {
      const self = this;
      const taskId = self.$route.params.id;
      Post("http://localhost:8360/api/task/join", {
        query: {
          tid: self.$route.params.id,
          partnerUuid: self.$uuid,
          message: '加入了行程'
        }
      }).then(res => {
      })
    }
  }
};
</script>

<style lang="scss" scope>
$designWidth: 750;
@import "@/assets/library/px2rem.scss";
.task-view-container {
  height: 100%;
  display: flex;
  flex-direction: column;
  text-align: left;
  //padding: px2rem(20) px2rem(20) px2rem(20) px2rem(20);
  box-sizing: border-box;
  .route-panel {
    margin-top: px2rem(100);
  }
  .chat-dialog {
    position: relative;
    flex-grow: 1;
    display: flex;
    border: px2rem(2) solid #b5b3b3;
    border-radius: px2rem(10);
    overflow: hidden;
    margin: px2rem(20) px2rem(10) px2rem(20) px2rem(10);
    .unteammate-mask {
      position: absolute;
      top: 0;
      right: 0;
      bottom: 0;
      left: 0;
      background-color: #000000ba;
      z-index: 12;
      .join-btn {
        position: absolute;
        z-index: 13;
        width: px2rem(160);
        height: px2rem(70);
        top: 50%;
        left: 50%;
        transform: translate(-50%, -50%);
        background-color: #5cadff;
        border-radius: px2rem(10);
        font-size: px2rem(28);
        text-align: center;
        line-height: px2rem(70);
        color: #f2f2f2;
      }
    }
    .chat-scroll-view {
      /* -- Attention: This line is extremely important in chrome 55+! -- */

      /* -- Attention-- */
      position: absolute;
      top: 0;
      bottom: px2rem(80);
      left: 0;
      right: 0;
      overflow: hidden;
      .chat-message {
        display: flex;
        padding: 0 px2rem(20);
        font-size: px2rem(26);
        .user-name {
          font-weight: bolder;
        }
      }
    }
    .input-box {
      position: absolute;
      width: 100%;
      padding: 0 px2rem(10);
      bottom: px2rem(15);
      box-sizing: border-box;
      display: flex;
      input {
        flex-grow: 1;
        margin-right: px2rem(10);
        border: px2rem(2) solid #b5b3b3;
        border-radius: px2rem(10);
        padding: 0 px2rem(10);
        font-size: px2rem(26);
      }
    }
    .enter-btn {
      width: px2rem(100);
      height: px2rem(60);
      font-size: px2rem(20);
      border-radius: px2rem(10);
      line-height: px2rem(60);
      text-align: center;
      color: #f2f2f2;
      background-color: #5cadff;
    }
  }
}
</style>
