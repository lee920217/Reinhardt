<template>
  <div class="add-task-container">
    <div class="new-task-header">新建任务</div>
    <div class="item">
      <div class="title">type:</div>
      <div class="select-ct">
        <div
          class="fake-border"
          v-on:touchstart="showList"
        >{{taskType[addNewTaskParams.currentTask]}}</div>
        <ul class="select-list" :class="[selectStatus ? 'active': '']">
          <li
            class="list-item"
            v-for="(i, v) in taskType"
            v-bind:key="v"
            v-on:touchstart="chooseTaskType(v)"
          >{{i}}</li>
        </ul>
      </div>
    </div>
    <div class="item">
      <div class="title">Time(Postcode):</div>
      <input type="datetime-local" v-model="addNewTaskParams.time" />
    </div>
    <div class="item">
      <div class="title">From(Location):</div>
      <input type="text" v-model="addNewTaskParams.fromL" />
    </div>
    <div class="item">
      <div class="title">From(Postcode):</div>
      <input type="text" v-model="addNewTaskParams.fromP" />
    </div>
    <div class="item">
      <div class="title">To(Location):</div>
      <input type="text" v-model="addNewTaskParams.toL" />
    </div>
    <div class="item">
      <div class="title">To(Postcode):</div>
      <input type="text" v-model="addNewTaskParams.toP" />
    </div>
    <div class="item">
      <div class="title">Note(备注):</div>
      <input type="text" v-model="addNewTaskParams.note" />
    </div>
    <div class="control-ct">
      <div class="cancel btn" v-on:touchstart="cancelAdd">取消</div>
      <div class="confirm btn" v-on:touchstart="confirmAdd()">确认</div>
    </div>
  </div>
</template>

<script>
export default {
  name: "AddTask",
  data () {
    return {
      selectStatus: false,
      taskType: {
        0: '选择任务类型',
        1: '结伴出行',
        2: '危险地共享',
      },
      addNewTaskParams: {
        currentTask: 0,
        start_time: '',
        start: '',
        fromP: '',
        target: '',
        toP: '',
        description: ''
      }
    }
  },
  methods: {
    cancelAdd () {
      const self = this;
      self.$emit('hideAddTaskDialog', 'hideAddTaskDialog')
    },
    confirmAdd () {
      const self = this;
      console.log(self.addNewTaskParams)
    },
    showList () {
      const self = this;
      self.selectStatus = true;
    },
    chooseTaskType (v) {
      const self = this;
      self.selectStatus = false;
      self.addNewTaskParams['currentTask'] = v;
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
        &.active {
          height: auto !important;
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
