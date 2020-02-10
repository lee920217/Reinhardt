<template>
  <div class="add-task-container">
    <div class="new-task-header">新建任务</div>
    <div class="new-task-body">
      <div class="item">
        <div class="title">type:</div>
        <div class="select-ct">
          <div class="fake-border" v-on:touchstart="showList">{{ taskType[addNewTaskParams.type] }}</div>
          <ul class="select-list" :class="[selectStatus ? 'active' : '']">
            <li
              class="list-item"
              v-for="(i, v) in taskType"
              v-bind:key="v"
              v-on:touchstart="chooseTaskType(v)"
            >{{ i }}</li>
          </ul>
        </div>
      </div>
      <div class="item">
        <div class="title">City:</div>
        <div class="select-ct">
          <div class="fake-border" v-on:touchstart="showCityList">{{ addNewTaskParams.start }}</div>
          <ul class="select-list" :class="[citySelectStatus ? 'active' : '']">
            <li
              class="list-item"
              v-for="i in cityList"
              v-bind:key="i"
              v-on:touchstart="chooseCity(i)"
            >{{ i }}</li>
          </ul>
        </div>
      </div>

      <div class="item">
        <div class="title">Time(Postcode):</div>
        <input type="datetime-local" v-model="addNewTaskParams.startTime" />
      </div>
      <div class="item">
        <div class="title">From(Location):</div>
        <input type="text" v-model="addNewTaskParams.startDesc" />
      </div>
      <div class="item">
        <div class="title">From(Postcode):</div>
        <input type="text" v-model="addNewTaskParams.startCode" />
      </div>
      <div class="item">
        <div class="title">To(Location):</div>
        <input type="text" v-model="addNewTaskParams.targetDesc" />
      </div>
      <div class="item">
        <div class="title">To(Postcode):</div>
        <input type="text" v-model="addNewTaskParams.targetCode" />
      </div>
      <div class="item">
        <div class="title">Note(备注):</div>
        <input type="text" v-model="addNewTaskParams.description" />
      </div>
    </div>
    <div class="control-ct">
      <div class="cancel btn" v-on:touchstart="cancelAdd">取消</div>
      <div class="confirm btn" v-on:touchstart="confirmAdd()">确认</div>
    </div>
  </div>
</template>

<script>
import { Post } from "@/assets/api/api.js";

export default {
  name: "AddTask",
  data () {
    return {
      selectStatus: false,
      citySelectStatus: false,
      taskType: {
        0: "选择任务类型",
        1: "结伴出行",
        2: "危险地共享"
      },
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
      addNewTaskParams: {
        uuid: this.$uuid,
        type: 0,
        title: "",
        startTime: "",
        start: "请选择城市",
        startDesc: "",
        startCode: "",
        target: "",
        targetDesc: "",
        targetCode: "",
        description: "",
        through: "Tempory unavialiable",
        throughCode: "0,1,2,3"
      }
    };
  },
  methods: {
    cancelAdd () {
      const self = this;
      self.$emit("hideAddTaskDialog", "hideAddTaskDialog");
    },
    confirmAdd () {
      const self = this;
      self.addNewTaskParams.startTime = self.addNewTaskParams.startTime.replace("T", " ");
      if (self.addNewTaskParams.type === 0) {
        self.$emit("handleError", {
          errno: 1,
          errmsg: "请选择任务类型",
          redirect: 0,
          path: "/"
        });
        return;
      }
      if (!self.addNewTaskParams.startTime) {
        self.$emit("handleError", {
          errno: 1,
          errmsg: "请选择开始时间",
          redirect: 0,
          path: "/"
        });
        return;
      }
      if (!self.addNewTaskParams.startTime) {
        self.$emit("handleError", {
          errno: 1,
          errmsg: "请选择开始时间",
          redirect: 0,
          path: "/"
        });
        return;
      }
      if (!self.addNewTaskParams.start || !self.addNewTaskParams.startCode) {
        self.$emit("handleError", {
          errno: 1,
          errmsg: "请选择出发地和出发邮编",
          redirect: 0,
          path: "/"
        });
        return;
      }
      if (
        (self.addNewTaskParams.type == 1 && !self.addNewTaskParams.start) ||
        !self.addNewTaskParams.startCode
      ) {
        self.$emit("handleError", {
          errno: 1,
          errmsg: "请选择结束地和结束邮编",
          redirect: 0,
          path: "/"
        });
        return;
      }
      self.addNewTaskParams['startCode'] = `${self.addNewTaskParams['startDesc']},${self.addNewTaskParams['startCode']}`
      self.addNewTaskParams['targetCode'] = `${self.addNewTaskParams['targetDesc']},${self.addNewTaskParams['targetCode']}`
      Post("http://localhost:8360/api/task/declare", { query: self.addNewTaskParams }).then(res => {
        self.handleRequest(res);
        self.cancelAdd();
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
    }
  }
};
</script>

<style lang="scss" scope>
$designWidth: 750;
@import "@/assets/library/px2rem.scss";
.add-task-container {
  position: fixed;
  width: px2rem(600);
  padding-bottom: px2rem(30);
  background-color: #f2f2f2;
  z-index: 13;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  border-radius: px2rem(10);
  text-align: left;
  box-shadow: 0 px2rem(4) px2rem(8) rgba(0, 0, 0, 0.32),
    0 px2rem(4) px2rem(40) rgba(0, 0, 0, 0.4);
  overflow: hidden;
  .new-task-header {
    padding: px2rem(30);
    margin-bottom: px2rem(20);
    font-size: px2rem(30);
    background-color: #b5b3b3;
    box-shadow: 0 1px 4px 0 rgba(0, 0, 0, 0.3);
  }
  .new-task-body {
    max-height: px2rem(800);
    overflow: scroll;
  }
  .item {
    padding: 0 px2rem(30);
    margin-bottom: px2rem(20);
    .title {
      margin-bottom: px2rem(10);
      font-size: px2rem(28);
      font-weight: bold;
    }
    .select-ct {
      position: relative;
      width: px2rem(440);
      height: px2rem(50);
      margin-top: px2rem(30);
      border: px2rem(1) solid #000;
      border-radius: px2rem(10);
      font-size: px2rem(24);
      line-height: px2rem(50);
      text-align: center;
      color: #000;
      .select-list {
        position: absolute;
        top: px2rem(52);
        width: px2rem(440);
        height: 0;
        background-color: #ffffff;
        border: 0 solid #b5b3b3;
        border-radius: px2rem(10);
        box-sizing: border-box;
        overflow: hidden;
        z-index: 5;
        &.active {
          height: auto !important;
          max-height: px2rem(400);
          overflow: scroll;
          border: px2rem(1) solid #b5b3b3;
          transition: 0.5s;
        }
      }
    }
    input {
      width: px2rem(400);
      height: px2rem(50);
      padding: 0 px2rem(20);
      background-color: transparent;
      border: px2rem(1) solid #000;
      border-radius: px2rem(10);
      font-size: px2rem(24);
    }
  }
  .control-ct {
    display: flex;
    padding: 0 px2rem(30);
    .btn {
      width: px2rem(180);
      height: px2rem(80);
      margin-top: px2rem(30);
      border-radius: px2rem(10);
      text-align: center;
      line-height: px2rem(80);
      font-size: px2rem(30);
      color: #f2f2f2;
      &.cancel {
        background-color: #b5b3b3;
      }
      &.confirm {
        background-color: #4285f4;
        margin-left: px2rem(80);
      }
    }
  }
}
</style>
