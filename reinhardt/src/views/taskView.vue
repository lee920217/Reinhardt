<template>
  <div class="task-view-container">
    <div class="panel-item">
      <div class="back-item"></div>
      <div class="item">
        <div class="title">Task ID:</div>
        <div class="content">{{ $route.params.id }}</div>
      </div>
      <div class="item">
        <div class="title">From:</div>
        <div class="content">Hardman Street / L1 9JG</div>
      </div>
      <div class="item">
        <div class="title">To:</div>
        <div class="content">Hardman Street / L1 9JG</div>
      </div>
      <div class="item">
        <div class="title">Time:</div>
        <div class="content">2020/12/31 19:59</div>
      </div>
      <div class="item">
        <div class="title">Note:</div>
        <div class="content">今天去买菜</div>
      </div>
      <div class="item">
        <div class="title">Team:</div>
        <div class="team-info">
          <div class="team-item">
            <div class="avatar">
              <img src="@/assets/img/female.png" />
            </div>
            <div class="user-name">LutrAAA</div>
          </div>
          <div class="team-item">
            <div class="avatar">
              <img src="@/assets/img/female.png" />
            </div>
            <div class="user-name">LutrAAA</div>
          </div>
          <div class="team-item">
            <div class="avatar">
              <img src="@/assets/img/female.png" />
            </div>
            <div class="user-name">LutrAAA</div>
          </div>
          <div class="team-item">
            <div class="avatar">
              <img src="@/assets/img/female.png" />
            </div>
            <div class="user-name">LutrAAA</div>
          </div>
        </div>
      </div>
    </div>
    <div class="chat-dialog">
      <div class="unteammate-mask">
        <div class="join-btn">加入Task</div>
      </div>
      <iscroll-view class="chat-scroll-view">
        <div class="chat-message">
          <div class="user-name">caocaocao:</div>
          <div class="msg">出发出发</div>
        </div>
        <div class="chat-message">
          <div class="user-name">caocaocao:</div>
          <div class="msg">出发出发</div>
        </div>
        <div class="chat-message system-msg">
          <div class="user-name">caocaocao</div>
          <div class="msg">加入了</div>
        </div>
      </iscroll-view>
      <div class="input-box">
        <input type="text" placeholder="从这里发送消息" />
        <div class="enter-btn">确认</div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "taskView"
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
  padding: px2rem(20) px2rem(20) px2rem(20) px2rem(20);
  box-sizing: border-box;
  .panel-item {
    padding: px2rem(20) px2rem(20) 0 px2rem(20);
    box-sizing: border-box;
    background-color: #cad0c9;
    border-radius: px2rem(10);
    margin-bottom: px2rem(20);
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
    .item {
      padding-bottom: px2rem(20);
      .title {
        font-size: px2rem(18);
      }
      .content {
        font-size: px2rem(36);
      }
      .team-info {
        display: flex;
        .team-item {
          position: relative;
          display: flex;
          margin-right: px2rem(20);
          .avatar {
            width: px2rem(60);
            height: px2rem(60);
            background-color: #ab3074;
            border-radius: 50%;
            text-align: center;
            img {
              width: px2rem(50);
              height: px2rem(50);
            }
          }
          .user-name {
            display: none;
            position: absolute;
            height: px2rem(40);
            top: px2rem(-50);
            left: px2rem(-30);
            padding: 0 px2rem(20);
            background-color: #f2f2f2;
            border: px2rem(2) solid #000;
            font-size: px2rem(20);
            line-height: px2rem(40);
          }
          &:hover {
            .user-name {
              display: block;
            }
          }
        }
      }
    }
  }
  .chat-dialog {
    position: relative;
    flex-grow: 1;
    display: flex;
    border: px2rem(2) solid #b5b3b3;
    border-radius: px2rem(10);
    box-sizing: border-box;
    overflow: hidden;
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
