<template>
  <div class="ov-profile-container">
    <div class="back-item"></div>
    <div class="profile-info">
      <div class="avatar">
        <img src="@/assets/img/male.png" />
      </div>
      <div class="text-info">
        <div class="username">LutrAAA</div>
        <div class="email">francislee920217@gmail.com</div>
      </div>
    </div>
    <div class="spilt-line"></div>
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
  </div>
</template>

<script>
export default {
  name: "Profile"
}
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
    bottom: px2rem(60);
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
}
</style>