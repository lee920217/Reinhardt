<template>
  <div class="add-task-container">
    <backPage :path="'index'" @transfer="transfer" />
    <Toast :msg="toastMsg" v-if="toastStatus > 0" />
    <div class="title-container">
      <div class="title">创建我的线路</div>
      <div class="desc">官方和个人路线没有满足您的要求时，可以自己创建</div>
    </div>
    <div class="new-task-body">
      <div class="task-info-input">
        <div class="title">出发地</div>
        <div class="input-ct">
          <input
            type="text"
            placeholder="出发地名称"
            v-model="addNewTaskParams.startDesc"
            v-on:input="checkInputContent"
          />
        </div>
      </div>
      <div class="task-info-input">
        <div class="title">出发地邮编</div>
        <div class="input-ct">
          <div class="city" v-if="addNewTaskParams.start">{{ cityObj.from }}</div>
          <input
            type="text"
            placeholder="出发地邮编"
            v-model="addNewTaskParams.startCode"
            v-on:input="checkInputContent"
          />
        </div>
      </div>
      <div class="task-info-input">
        <div class="title">到达地名称</div>
        <div class="input-ct">
          <input
            type="text"
            placeholder="到达地名称"
            v-model="addNewTaskParams.targetDesc"
            v-on:input="checkInputContent"
          />
        </div>
      </div>
      <div class="task-info-input">
        <div class="title">到达地邮编</div>
        <div class="input-ct">
          <div class="city" v-if="addNewTaskParams.target">{{ cityObj.to }}</div>
          <input
            type="text"
            placeholder="到达地邮编"
            v-model="addNewTaskParams.targetCode"
            v-on:input="checkInputContent"
          />
        </div>
      </div>
      <div class="task-info-input">
        <div class="title">时间</div>
        <div class="input-ct">
          <input type="datetime-local" placeholder="出发时间" v-model="addNewTaskParams.startTime" />
        </div>
      </div>
      <div class="task-info-input">
        <div class="title">交通工具</div>
        <div class="input-ct">
          <select
            class="traffic-selector"
            v-model="addNewTaskParams.trafficType"
            v-on:input="checkInputContent"
          >
            <option class="default" :value="0">选择交通工具</option>
            <option :value="1">步行</option>
            <option :value="2">4座汽车</option>
            <option :value="3">8座汽车</option>
            <option :value="4">巴士</option>
          </select>
        </div>
      </div>
      <div class="task-info-input">
        <div class="title">人数</div>
        <div class="input-ct">
          <input
            type="number"
            placeholder="人数（包括创建人）"
            v-model="addNewTaskParams.scaleLimit"
            v-on:input="checkInputContent"
          />
        </div>
      </div>
    </div>
    <div
      class="confirm-add-task"
      :class="[clickStatus ? 'active' : 'disable']"
      v-on:touchstart="confirmAdd"
    >{{ createStatus }}</div>
  </div>
</template>

<script>
import backPage from "@/components/common/BackPage.vue";
import Toast from "@/components/common/Toast.vue";
import { Get, Post } from "@/assets/api/api.js";
import { exportAddress } from "@/assets/api/setting.js";

export default {
  name: "AddTask",
  props: {
    basicTravelData: {
      type: Object
    }
  },
  components: {
    backPage,
    Toast
  },
  data () {
    return {
      selectStatus: false,
      citySelectStatus: false,
      currentTime: null,
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
      toastMsg: "",
      toastStatus: 0,
      createStatus: "创建路线",
      cityObj: {
        from: "",
        to: ""
      },
      clickStatus: false,
      addNewTaskParams: {
        uuid: this.$uuid,
        type: 1,
        title: "",
        startTime: "",
        start: "",
        startDesc: "",
        startCode: "",
        target: "",
        targetDesc: "",
        targetCode: "",
        description: "",
        trafficType: 0,
        scaleLimit: null,
        through: "Tempory unavialiable",
        throughCode: "0,1,2,3"
      }
    };
  },
  mounted: function () {
    this.timeFormat();
    this.combineBasicData();
  },
  methods: {
    combineBasicData () {
      const self = this;
      self.addNewTaskParams.startDesc = self.basicTravelData.from;
      self.addNewTaskParams.targetDesc = self.basicTravelData.to;
      self.addNewTaskParams.startTime = self.basicTravelData.time;
    },
    timeFormat () {
      const self = this;
      const time = new Date();
      const timeDtl = {
        Y: time.getFullYear(),
        M: self.addZero(time.getMonth() + 1),
        d: self.addZero(time.getDate()),
        h: self.addZero(time.getHours()),
        m: self.addZero(time.getMinutes())
      };
      let formatTime = `${timeDtl["Y"]}-${timeDtl["M"]}-${timeDtl["d"]}T${timeDtl["h"]}:${timeDtl["m"]}`;
      self.currentTime = formatTime;
    },
    addZero (v) {
      if (v < 10) {
        v = "0" + v;
      }
      return v;
    },
    toastMsgWarning (msg) {
      const self = this;
      self.toastMsg = msg;
      self.toastStatus = 1;
      setTimeout(() => {
        self.toastStatus = 0;
        self.toastMsg = "";
      }, 1000);
    },
    confirmAdd () {
      const self = this;
      if (!self.clickStatus) {
        return;
      }
      self.addNewTaskParams.startTime = self.addNewTaskParams.startTime.replace("T", " ");
      const postcodeCheck = self.postcodeVaild();
      if (!postcodeCheck) {
        self.toastMsgWarning("邮编格式错误");
      }
      if (!self.addNewTaskParams.startTime) {
        self.toastMsgWarning("出发时间不得为空");
        return;
      }
      if (!self.addNewTaskParams.startCode) {
        self.toastMsgWarning("出发地邮编不得为空");
        return;
      }
      if (!self.addNewTaskParams.startDesc) {
        self.toastMsgWarning("出发地名称不得为空");
        return;
      }
      if (!self.addNewTaskParams.targetCode) {
        self.toastMsgWarning("目的地邮编不得为空");
        return;
      }
      if (!self.addNewTaskParams.targetDesc) {
        self.toastMsgWarning("目的地名称不得为空");
        return;
      }
      if (!self.addNewTaskParams.trafficType) {
        self.toastMsgWarning("交通方式不得为空");
        return;
      }
      if (!self.addNewTaskParams.scaleLimit) {
        self.toastMsgWarning("人数限制不得为空");
        return;
      }
      self.createStatus = "创建中……";
      self.clickStatus = false;
      let getStartCity = self.requestCityByPostCode(self.addNewTaskParams.startCode);
      let getTargetCity = self.requestCityByPostCode(self.addNewTaskParams.targetCode);
      Promise.all([getStartCity, getTargetCity]).then(res => {
        self.addNewTaskParams.start = res[0].result.admin_district;
        self.addNewTaskParams.target = res[1].result.admin_district;
        self.cityObj = {
          from: res[0].result.admin_district,
          to: res[1].result.admin_district
        };
        self.addNewTaskParams[
          "startCode"
        ] = `${self.addNewTaskParams["startDesc"]},${self.addNewTaskParams["startCode"]}`;
        self.addNewTaskParams[
          "targetCode"
        ] = `${self.addNewTaskParams["targetDesc"]},${self.addNewTaskParams["targetCode"]}`;
        self.requestAddNewTask();
      });
    },
    requestCityByPostCode (postcode) {
      const self = this;
      let requestAddr = exportAddress;
      postcode = postcode.replace(/\s+/g, "");
      return new Promise((resolve, reject) => {
        Get(`${requestAddr.postcodesio}/postcodes/${postcode}`).then(res => {
          if (res.status == 200 && res.result) {
            resolve(res);
          } else {
            reject("error");
          }
        });
      });
    },
    requestAddNewTask () {
      const self = this;
      let requestAddr = exportAddress;
      Post(`${requestAddr.task}/declare`, { query: self.addNewTaskParams }).then(res => {
        self.handleRequest(res);
        self.transfer();
      });
    },
    handleRequest (res) {
      const self = this;
      if (res.code === 0) {
        self.$emit("handleError", {
          errno: 0,
          errmsg: "创建任务成功",
          redirect: 0,
          path: "/"
        });
      } else {
        self.$emit("handleError", {
          errno: 1,
          errmsg: "创建任务失败",
          redirect: 0,
          path: "/"
        });
      }
    },
    showList () {
      const self = this;
      self.selectStatus = true;
    },
    showCityList () {
      const self = this;
      self.citySelectStatus = true;
    },
    chooseCity (v) {
      const self = this;
      self.citySelectStatus = false;
      self.addNewTaskParams["start"] = v;
      self.addNewTaskParams["target"] = v;
    },
    chooseTaskType (v) {
      const self = this;
      self.selectStatus = false;
      self.addNewTaskParams["type"] = v;
    },
    hideAll () {
      const self = this;
      self.citySelectStatus = false;
      self.selectStatus = false;
    },
    postcodeVaild () {
      const self = this;
      const startPostcode = self.addNewTaskParams.startCode;
      const targetPostcode = self.addNewTaskParams.targetCode;
      const re =
        "^([A-Za-z][A-Ha-hJ-Yj-y]?[0-9][A-Za-z0-9]? ?[0-9][A-Za-z]{2}|[Gg][Ii][Rr] ?0[Aa]{2})$";
      if (startPostcode.match(re) == null) {
        self.toastMsgWarning("出发地邮编格式错误");
        return false;
      }
      if (startPostcode.match(re) == null) {
        self.toastMsgWarning("目的地邮编格式错误");
        return false;
      }
      return true;
    },
    transfer () {
      const self = this;
      self.$emit("cancelAddTaskDialog", "cancel");
    },
    checkInputContent () {
      const self = this;
      if (
        self.addNewTaskParams.startDesc &&
        self.addNewTaskParams.startCode &&
        self.addNewTaskParams.targetDesc &&
        self.addNewTaskParams.targetCode &&
        self.addNewTaskParams.startTime &&
        self.addNewTaskParams.trafficType &&
        self.addNewTaskParams.scaleLimit
      ) {
        self.clickStatus = true;
      }
    }
  }
};
</script>

<style lang="scss" scope>
$designWidth: 750;
@import "@/assets/library/px2rem.scss";
.add-task-container {
  position: fixed;
  width: 100%;
  height: 100%;
  padding-bottom: px2rem(30);
  background-color: white;
  z-index: 21;
  text-align: left;
  overflow: hidden;
  .title-container {
    margin-top: px2rem(256);
    margin-left: px2rem(64);
    .title {
      font-size: px2rem(40);
      font-weight: 500;
    }
    .desc {
      font-size: px2rem(26);
      color: rgba(22, 24, 35, 0.5);
    }
  }
  .new-task-body {
    width: px2rem(622);
    margin: px2rem(84) auto 0 auto;
    .task-info-input {
      width: 100%;
      height: px2rem(96);
      display: flex;
      border-bottom: px2rem(2) solid #f7f7f7;
      .title {
        width: px2rem(140);
        font-size: px2rem(28);
        font-weight: 500;
        line-height: px2rem(98);
      }
      .input-ct {
        position: relative;
        width: px2rem(400);
        height: px2rem(96);
        margin-left: px2rem(64);
        font-size: px2rem(30);
        .city {
          position: absolute;
          top: 50%;
          right: 0;
          transform: translateY(-50%);
          padding: px2rem(8) px2rem(16);
          font-size: px2rem(20);
          color: #2b44ff;
          border: px2rem(2) solid #2b44ff;
          border-radius: px2rem(4);
        }
        input {
          height: 100%;
          box-sizing: border-box;
          line-height: px2rem(96);
          font-size: px2rem(28);
          &::placeholder {
            font-size: px2rem(28);
            color: #cccccc;
          }
        }
        .traffic-selector {
          height: px2rem(96);
          line-height: px2rem(96);
          font-size: px2rem(28);
          -webkit-appearance: none;
          .default {
            color: #cccccc;
          }
        }
      }
    }
  }
  .confirm-add-task {
    width: px2rem(622);
    height: px2rem(96);
    margin: px2rem(64) auto 0 auto;
    border-radius: px2rem(60);
    font-size: px2rem(30);
    text-align: center;
    line-height: px2rem(96);
    &.active {
      background-color: #2b44ff;
      color: #ffffff;
    }
    &.disable {
      background-color: #eeeeee;
      color: #bbbbbb;
    }
  }
}
</style>
