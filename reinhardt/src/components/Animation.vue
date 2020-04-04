<template>
  <div class="map-animation-container">
    <div class="close" v-on:click="closeAnimation">X</div>
    <div class="data-and-num">
      <div class="date">2020-{{date}}</div>
      <div class="count">
        <span>确诊</span>
        {{count}}
        <span>人</span>
      </div>
    </div>
    <div class="map-ct">
      <highcharts :constructor-type="'mapChart'" :options="UKMapSettings" ref="ukmap"></highcharts>
    </div>
  </div>
</template>

<script>
import UKMapSettings from "@/assets/data/uk-animation-map.js";
import { historyData, historyDate } from "@/assets/data/animation-data.js";
import { dateList, totalNum, deathNum, increaseNum } from "@/assets/api/chartData.js";
import TWEEN from "@tweenjs/tween.js";
export default {
  name: 'Animation',
  data () {
    return {
      UKMapSettings: UKMapSettings,
      date: "03-05",
      count: 0,
      animationCounter: null
    }
  },
  mounted () {
    this.updateAnimation();
    this.tweenJS(0, 115)
  },
  destroyed () {
    this.date = "03-05";
    this.count = "115";
    this.UKMapSettings = UKMapSettings;
    clearInterval(self.animationCounter);
  },
  methods: {
    closeAnimation () {
      const self = this;
      self.date = "03-05";
      self.count = "115";
      self.UKMapSettings = UKMapSettings;
      clearInterval(self.animationCounter);
      self.$emit('changeAnimationStatus', false)
    },
    updateAnimation () {
      const self = this;
      let index = 0;
      let length = historyData.length;
      self.animationCounter = setInterval(() => {
        if (index >= length) {
          clearInterval(animationCounter);
          return
        }
        let currentDate = historyDate[index];
        self.date = currentDate;
        let dateIndex = dateList.indexOf(currentDate);
        let currentCount = totalNum[dateIndex];
        // self.count = currentCount;
        // console.log('c', self.count);
        // console.log('n', currentCount);
        self.tweenJS(self.count, currentCount)

        let mapBase = self.$refs.ukmap.chart;
        mapBase.series[0].update({
          data: historyData[index]
        })
        index++;
      }, 1000)
    },
    tweenJS (start, end) {
      let frameHandler;
      const self = this;
      const animate = function (currentTime) {
        TWEEN.update(currentTime);
        frameHandler = requestAnimationFrame(animate);
      };
      const myTween = new TWEEN.Tween({ tweeningValue: start })
        .to({ tweeningValue: end }, 633)
        .onUpdate(() => {
          self.count = myTween._object.tweeningValue.toFixed(0);
        })
        .onComplete(() => {
          // Make sure to clean up after ourselves.
          cancelAnimationFrame(frameHandler);
        })
        .start();
      frameHandler = requestAnimationFrame(animate);
    },
  }
}
</script>

<style lang="scss" scoped>
$designWidth: 750;
@import "@/assets/library/px2rem.scss";
.map-animation-container {
  position: fixed;
  top: 0;
  bottom: 0;
  left: 0;
  right: 0;
  background-color: #232322;
  z-index: 99;
  .close {
    position: absolute;
    width: px2rem(48);
    height: px2rem(48);
    color: #f2f2f2;
    font-size: px2rem(60);
    top: px2rem(24);
    right: px2rem(24);
  }
  .data-and-num {
    margin-top: px2rem(200);
    margin-bottom: px2rem(200);
    color: #f2f2f2;
    font-weight: 900;
    .count {
      color: #fc4b4b;
      span {
        font-size: px2rem(30);
        font-weight: 400;
      }
    }
  }
}
</style>