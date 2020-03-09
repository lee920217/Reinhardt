<template>
  <div class="index-main-container">
    <CookieMask v-if="maskStatus" @hideMask="hideMask" />
    <LMask v-if="maskStatus" />
    <Header />
    <!-- <AddTask
      v-if="addTaskStatus"
      @cancelAddTaskDialog="cancelNewTask"
      :basicTravelData="basicTravelData"
    />-->
    <div class="slider-container">
      <img src="@/assets/img/slider.png" />
    </div>
    <!-- <div class="create-task-container">
      <div class="base-header">
        <div class="create-text-container">
          <div class="text-header">组团出行</div>
          <div class="text-detail">我们在一起，对抗这世界的恶意</div>
        </div>
      </div>
      <div class="create-body">
        <div class="create-input-container had-bottom-line">
          <div class="point yellow-bg"></div>
          <div class="input-part">
            <input type="text" placeholder="从哪里出发" v-model="basicTravelData.from" />
          </div>
        </div>
        <div class="create-input-container had-bottom-line">
          <div class="point blue-bg"></div>
          <div class="input-part">
            <input type="text" placeholder="我要去哪里" v-model="basicTravelData.to" />
          </div>
        </div>
        <div class="create-input-container">
          <div class="point red-bg"></div>
          <div class="input-part" v-on:touchstart="touchChange">
            <input :type="touchChangeType" placeholder="出发时间" v-model="basicTravelData.time" />
          </div>
        </div>
      </div>
    </div>
    <div class="route-selector-container">
      <div
        class="route-bar offical-route"
        :class="[routeType == 1 ? 'active' : '']"
        v-on:touchstart="changeRouteType(1)"
      >合作路线</div>
      <div
        class="route-bar individual-route"
        :class="[routeType == 2 ? 'active' : '']"
        v-on:touchstart="changeRouteType(2)"
      >个人路线</div>
    </div>
    <div class="route-display-container" v-if="renderStatus && routeType == 1">
      <RoutePanel
        v-for="i in officialTaskList"
        v-bind:key="`official-${i.tid}`"
        :panelData="i"
        :renderStatus="renderStatus"
      />
      <div class="route-more" v-on:touchstart="routerDirect('/list')">查看更多 ></div>
    </div>
    <div class="route-display-container" v-if="renderStatus && routeType == 2">
      <RoutePanel
        v-for="j in indivTaskList"
        v-bind:key="`individual-${j.tid}`"
        :panelData="j"
        :renderStatus="renderStatus"
      />
      <div class="route-more" v-on:touchstart="routerDirect('/list')">查看更多 ></div>
    </div>
    <div class="route-display-container" v-if="!renderStatus">
      <RoutePanel
        v-for="i in renderingTaskList"
        :renderStatus="renderStatus"
        v-bind:key="`individual-${i.tid}`"
        :panelData="i"
      />
      <div class="route-more" v-on:touchstart="routerDirect('/list')">查看更多 ></div>
    </div>-->
    <div class="data-display-container">
      <div class="base-header">
        <div class="create-text-container">
          <div class="text-header">Cases of coronavirus in UK</div>
          <div class="text-detail">勤洗手，减少出门</div>
        </div>
        <div class="update-time">Updated 2020-03-09 22:55</div>
      </div>
      <div class="text-data-container">
        <div class="text-data-detail">
          <div class="num red-font">{{ dataSet.total }}</div>
          <div class="desc">CONFIRMED</div>
        </div>
        <div class="text-data-detail">
          <div class="num yellow-font">{{ dataSet.cured }}</div>
          <div class="desc">CURED</div>
        </div>
        <div class="text-data-detail">
          <div class="num blue-font">{{ dataSet.death }}</div>
          <div class="desc">DEATH</div>
        </div>
        <div class="text-data-detail">
          <div class="num green-font">+{{ dataSet.increase }}</div>
          <div class="desc">Daily Increase</div>
        </div>
      </div>
      <div class="map-data-container">
        <highcharts :constructor-type="'mapChart'" :options="UKMapSettings" ref="ukmap"></highcharts>
      </div>
    </div>
    <div class="chart-display-container">
      <highcharts :constructor-type="'mapChart'" :options="ChartSettings"></highcharts>
    </div>
    <div class="news-display-container">
      <div class="base-header">
        <div class="create-text-container">
          <div class="text-header">Break News</div>
        </div>
      </div>
      <div class="news-body">
        <NewsPanel v-for="(i, v) in newsList" v-bind:key="v" :Newsdata="i" />
        <!-- <div class="news-more">查看更多 ></div> -->
      </div>
    </div>
    <!-- <div class="addnew-task-btn" v-on:touchstart="createNewTask">创建我的路线</div> -->
  </div>
</template>

<script>
import Header from "@/components/common/OVHeader.vue";
import RoutePanel from "@/components/common/RoutePanel.vue";
import AddTask from "@/components/common/AddTask.vue";
import CookieMask from "@/components/common/CookieMask.vue";
import LMask from "@/components/common/Mask.vue";
import NewsPanel from "@/components/common/NewsPanel.vue";
import UKMapSettings from "@/assets/data/uk-map.js";
import ChartSettings from "@/assets/data/uk-ncov2019-chart.js";
import TWEEN from "@tweenjs/tween.js";
import { Post } from "@/assets/api/api.js";
import { exportAddress } from "@/assets/api/setting.js";

//TODO 
const datasss = [[
  ["gb-ay", 0],
  ["gb-3270", 0],
  ["gb-hi", 0],
  ["gb-ab", 0],
  ["gb-ps", 0],
  ["gb-wi", 0],
  ["gb-my", 0],
  ["gb-7398", 0],
  ["gb-eb", 0],
  ["gb-lc", 0],
  ["gb-2393", 0],
  ["gb-db", 1],
  ["gb-de", 0],
  ["gb-an", 0],
  ["gb-bl", 0],
  ["gb-ng", 0],
  ["gb-do", 0],
  ["gb-2458", 0],
  ["gb-er", 0],
  ["gb-ea", 0],
  ["gb-gg", 1],
  ["gb-ed", 0],
  ["gb-ic", 0],
  ["gb-2446", 0],
  ["gb-nn", 0],
  ["gb-rf", 0],
  ["gb-sa", 1],
  ["gb-sl", 0],
  ["gb-wd", 0],
  ["gb-ar", 1],
  ["gb-as", 0],
  ["gb-fk", 1],
  ["gb-zg", 0],
  ["gb-cc", 0],
  ["gb-du", 1],
  ["gb-fi", 0],
  ["gb-ml", 0],
  ["gb-wh", 0],
  ["gb-bo", 0],
  ["gb-dh", 0],
  ["gb-da", 0],
  ["gb-hp", 0],
  ["gb-mb", 0],
  ["gb-rc", 0],
  ["gb-zt", 0],
  ["gb-ha", 0],
  ["gb-zh", 0],
  ["gb-2318", 0],
  ["gb-gc", 2],
  ["gb-mk", 0],
  ["gb-bu", 0],
  ["gb-bn", 0],
  ["gb-bs", 2],
  ["gb-ns", 0],
  ["gb-sj", 0],
  ["gb-2389", 0],
  ["gb-ds", 0],
  ["gb-2391", 0],
  ["gb-ht", 3],
  ["gb-cm", 0],
  ["gb-kh", 1],
  ["gb-ne", 0],
  ["gb-ba", 0],
  ["gb-xb", 0],
  ["gb-ke", 1],
  ["gb-bz", 0],
  ["gb-be", 0],
  ["gb-cn", 0],
  ["gb-eg", 0],
  ["gb-ef", 0],
  ["gb-gr", 0],
  ["gb-hf", 0],
  ["gb-hu", 0],
  ["gb-it", 0],
  ["gb-kc", 0],
  ["gb-cy", 0],
  ["gb-me", 1],
  ["gb-rb", 0],
  ["gb-ru", 0],
  ["gb-su", 0],
  ["gb-th", 0],
  ["gb-wf", 0],
  ["gb-ww", 0],
  ["gb-we", 2],
  ["gb-li", 0],
  ["gb-bf", 3],
  ["gb-ld", 0],
  ["gb-nm", 0],
  ["gb-am", 0],
  ["gb-bb", 0],
  ["gb-cr", 0],
  ["gb-dn", 0],
  ["gb-2347", 0],
  ["gb-lb", 0],
  ["gb-mf", 0],
  ["gb-om", 0],
  ["gb-lr", 0],
  ["gb-cf", 0],
  ["gb-nw", 0],
  ["gb-2354", 0],
  ["gb-dw", 0],
  ["gb-cl", 0],
  ["gb-by", 0],
  ["gb-cs", 0],
  ["gb-pe", 0],
  ["gb-2301", 0],
  ["gb-gd", 0],
  ["gb-sp", 0],
  ["gb-po", 0],
  ["gb-mt", 0],
  ["gb-bj", 0],
  ["gb-cp", 0],
  ["gb-rt", 0],
  ["gb-ca", 2],
  ["gb-vg", 0],
  ["gb-np", 0],
  ["gb-sw", 1],
  ["gb-7122", 1],
  ["gb-bw", 0],
  ["gb-la", 2],
  ["gb-ey", 0],
  ["gb-yk", 3],
  ["gb-di", 0],
  ["gb-fl", 0],
  ["gb-wx", 0],
  ["gb-bg", 0],
  ["gb-no", 0],
  ["gb-tf", 0],
  ["gb-lm", 0],
  ["gb-sb", 0],
  ["gb-fe", 0],
  ["gb-ny", 0],
  ["gb-2420", 0],
  ["gb-tb", 2],
  ["gb-ex", 2],
  ["gb-nf", 0],
  ["gb-bh", 5],
  ["gb-hv", 0],
  ["gb-tr", 0],
  ["gb-ss", 0],
  ["gb-ws", 2],
  ["gb-wr", 0],
  ["gb-hd", 2],
  ["gb-kt", 2],
  ["gb-sr", 2],
  ["gb-es", 0],
  ["gb-ox", 0],
  ["gb-sn", 0],
  ["gb-na", 1],
  ["gb-rl", 0],
  ["gb-hk", 0],
  ["gb-hy", 0],
  ["gb-hr", 0],
  ["gb-lt", 0],
  ["gb-lw", 2],
  ["gb-nh", 0],
  ["gb-sq", 0],
  ["gb-he", 0],
  ["gb-st", 0],
  ["gb-wc", 0],
  ["gb-tk", 0],
  ["gb-6338", 25],
  ["gb-nb", 0],
  ["gb-2367", 1],
  ["gb-7113", 0],
  ["gb-7114", 0],
  ["gb-7115", 0],
  ["gb-7116", 0],
  ["gb-2364", 0],
  ["gb-7118", 0],
  ["gb-7119", 3],
  ["gb-wt", 0],
  ["gb-ms", 5],
  ["gb-7117", 0],
  ["gb-3265", 0],
  ["gb-7130", 0],
  ["gb-7131", 1],
  ["gb-7132", 2],
  ["gb-7133", 0],
  ["gb-3266", 0],
  ["gb-7121", 2],
  ["gb-7123", 3],
  ["gb-7124", 0],
  ["gb-7125", 1],
  ["gb-7126", 0],
  ["gb-7127", 1],
  ["gb-7128", 0],
  ["gb-7129", 3],
  ["gb-2366", 0],
  ["gb-nt", 0],
  ["gb-3267", 0],
  ["gb-7134", 0],
  ["gb-7135", 0],
  ["gb-nl", 0],
  ["gb-7136", 0],
  ["gb-2377", 1],
  ["gb-7137", 0],
  ["gb-7138", 0],
  ["gb-7139", 0],
  ["gb-7140", 0],
  ["gb-7141", 0],
  ["gb-7142", 0],
  ["gb-2381", 0],
  ["gb-bd", 0],
  ["gb-2388", 1],
  ["gb-7143", 0],
  ["gb-7144", 0],
  ["gb-7145", 0],
  ["gb-7146", 0],
  ["gb-7147", 0],
  ["gb-7149", 3],
  ["gb-so", 0],
  ["gb-7150", 0],
  ["gb-7151", 0],
  ["gb-pb", 0],
  ["gb-iw", 0],
  ["gb-mo", 0],
  ["gb-ag", 0],
  ["gb-el", 0],
  ["gb-sm", 0],
  ["gb-ci", 0],
  ["gb-hl", 0],
  ["gb-co", 1],
  ["gb-cw", 0],
  ["gb-nd", 0],
  ["gb-dg", 0],
  ["gb-cu", 2],
  ["gb-sf", 0],
  ["gb-mw", 0],
  ["gb-lu", 0],
  ["gb-wl", 0],
  ["gb-3271", 0]
],
[
  ["gb-ay", 0],
  ["gb-3270", 0],
  ["gb-hi", 0],
  ["gb-ab", 0],
  ["gb-ps", 0],
  ["gb-wi", 0],
  ["gb-my", 0],
  ["gb-7398", 0],
  ["gb-eb", 0],
  ["gb-lc", 1],
  ["gb-2393", 0],
  ["gb-db", 3],
  ["gb-de", 0],
  ["gb-an", 0],
  ["gb-bl", 0],
  ["gb-ng", 2],
  ["gb-do", 10],
  ["gb-2458", 0],
  ["gb-er", 0],
  ["gb-ea", 0],
  ["gb-gg", 1],
  ["gb-ed", 0],
  ["gb-ic", 0],
  ["gb-2446", 0],
  ["gb-nn", 0],
  ["gb-rf", 0],
  ["gb-sa", 1],
  ["gb-sl", 0],
  ["gb-wd", 0],
  ["gb-ar", 1],
  ["gb-as", 0],
  ["gb-fk", 1],
  ["gb-zg", 0],
  ["gb-cc", 0],
  ["gb-du", 1],
  ["gb-fi", 0],
  ["gb-ml", 0],
  ["gb-wh", 0],
  ["gb-bo", 0],
  ["gb-dh", 0],
  ["gb-da", 0],
  ["gb-hp", 0],
  ["gb-mb", 0],
  ["gb-rc", 0],
  ["gb-zt", 0],
  ["gb-ha", 2],
  ["gb-zh", 0],
  ["gb-2318", 0],
  ["gb-gc", 2],
  ["gb-mk", 1],
  ["gb-bu", 1],
  ["gb-bn", 0],
  ["gb-bs", 1],
  ["gb-ns", 0],
  ["gb-sj", 0],
  ["gb-2389", 0],
  ["gb-ds", 0],
  ["gb-2391", 0],
  ["gb-ht", 8],
  ["gb-cm", 0],
  ["gb-kh", 1],
  ["gb-ne", 0],
  ["gb-ba", 0],
  ["gb-xb", 0],
  ["gb-ke", 1],
  ["gb-bz", 1],
  ["gb-be", 1],
  ["gb-cn", 2],
  ["gb-eg", 5],
  ["gb-ef", 0],
  ["gb-gr", 0],
  ["gb-hf", 1],
  ["gb-hu", 3],
  ["gb-it", 0],
  ["gb-kc", 6],
  ["gb-cy", 0],
  ["gb-me", 1],
  ["gb-rb", 1],
  ["gb-ru", 0],
  ["gb-su", 0],
  ["gb-th", 0],
  ["gb-wf", 0],
  ["gb-ww", 2],
  ["gb-we", 2],
  ["gb-li", 1],
  ["gb-bf", 3],
  ["gb-ld", 0],
  ["gb-nm", 0],
  ["gb-am", 0],
  ["gb-bb", 0],
  ["gb-cr", 0],
  ["gb-dn", 0],
  ["gb-2347", 0],
  ["gb-lb", 0],
  ["gb-mf", 0],
  ["gb-om", 0],
  ["gb-lr", 0],
  ["gb-cf", 0],
  ["gb-nw", 0],
  ["gb-2354", 0],
  ["gb-dw", 0],
  ["gb-cl", 0],
  ["gb-by", 0],
  ["gb-cs", 0],
  ["gb-pe", 0],
  ["gb-2301", 0],
  ["gb-gd", 0],
  ["gb-sp", 0],
  ["gb-po", 0],
  ["gb-mt", 0],
  ["gb-bj", 0],
  ["gb-cp", 0],
  ["gb-rt", 0],
  ["gb-ca", 2],
  ["gb-vg", 0],
  ["gb-np", 0],
  ["gb-sw", 1],
  ["gb-7122", 1],
  ["gb-bw", 0],
  ["gb-la", 2],
  ["gb-ey", 0],
  ["gb-yk", 3],
  ["gb-di", 0],
  ["gb-fl", 0],
  ["gb-wx", 0],
  ["gb-bg", 0],
  ["gb-no", 0],
  ["gb-tf", 0],
  ["gb-lm", 0],
  ["gb-sb", 0],
  ["gb-fe", 0],
  ["gb-ny", 0],
  ["gb-2420", 0],
  ["gb-tb", 4],
  ["gb-ex", 3],
  ["gb-nf", 0],
  ["gb-bh", 7],
  ["gb-hv", 0],
  ["gb-tr", 0],
  ["gb-ss", 1],
  ["gb-ws", 3],
  ["gb-wr", 0],
  ["gb-hd", 1],
  ["gb-kt", 2],
  ["gb-sr", 4],
  ["gb-es", 0],
  ["gb-ox", 4],
  ["gb-sn", 1],
  ["gb-na", 2],
  ["gb-rl", 0],
  ["gb-hk", 0],
  ["gb-hy", 0],
  ["gb-hr", 1],
  ["gb-lt", 2],
  ["gb-lw", 2],
  ["gb-nh", 0],
  ["gb-sq", 1],
  ["gb-he", 0],
  ["gb-st", 4],
  ["gb-wc", 0],
  ["gb-tk", 0],
  ["gb-6338", 2],
  ["gb-nb", 0],
  ["gb-2367", 3],
  ["gb-7113", 1],
  ["gb-7114", 0],
  ["gb-7115", 0],
  ["gb-7116", 0],
  ["gb-2364", 0],
  ["gb-7118", 0],
  ["gb-7119", 3],
  ["gb-wt", 0],
  ["gb-ms", 5],
  ["gb-7117", 0],
  ["gb-3265", 0],
  ["gb-7130", 0],
  ["gb-7131", 1],
  ["gb-7132", 3],
  ["gb-7133", 0],
  ["gb-3266", 0],
  ["gb-7121", 2],
  ["gb-7123", 4],
  ["gb-7124", 0],
  ["gb-7125", 2],
  ["gb-7126", 0],
  ["gb-7127", 1],
  ["gb-7128", 5],
  ["gb-7129", 4],
  ["gb-2366", 0],
  ["gb-nt", 3],
  ["gb-3267", 0],
  ["gb-7134", 0],
  ["gb-7135", 2],
  ["gb-nl", 0],
  ["gb-7136", 0],
  ["gb-2377", 1],
  ["gb-7137", 0],
  ["gb-7138", 0],
  ["gb-7139", 0],
  ["gb-7140", 0],
  ["gb-7141", 0],
  ["gb-7142", 1],
  ["gb-2381", 0],
  ["gb-bd", 0],
  ["gb-2388", 1],
  ["gb-7143", 0],
  ["gb-7144", 2],
  ["gb-7145", 2],
  ["gb-7146", 0],
  ["gb-7147", 0],
  ["gb-7149", 3],
  ["gb-so", 0],
  ["gb-7150", 0],
  ["gb-7151", 0],
  ["gb-pb", 1],
  ["gb-iw", 1],
  ["gb-mo", 0],
  ["gb-ag", 0],
  ["gb-el", 0],
  ["gb-sm", 1],
  ["gb-ci", 0],
  ["gb-hl", 5],
  ["gb-co", 2],
  ["gb-cw", 0],
  ["gb-nd", 0],
  ["gb-dg", 0],
  ["gb-cu", 4],
  ["gb-sf", 0],
  ["gb-mw", 2],
  ["gb-lu", 2],
  ["gb-wl", 3],
  ["gb-3271", 0]
], [
  ["gb-ay", 0],
  ["gb-3270", 0],
  ["gb-hi", 0],
  ["gb-ab", 0],
  ["gb-ps", 0],
  ["gb-wi", 0],
  ["gb-my", 0],
  ["gb-7398", 0],
  ["gb-eb", 0],
  ["gb-lc", 1],
  ["gb-2393", 1],
  ["gb-db", 4],
  ["gb-de", 0],
  ["gb-an", 0],
  ["gb-bl", 0],
  ["gb-ng", 2],
  ["gb-do", 12],
  ["gb-2458", 0],
  ["gb-er", 0],
  ["gb-ea", 0],
  ["gb-gg", 1],
  ["gb-ed", 0],
  ["gb-ic", 0],
  ["gb-2446", 0],
  ["gb-nn", 0],
  ["gb-rf", 0],
  ["gb-sa", 1],
  ["gb-sl", 0],
  ["gb-wd", 0],
  ["gb-ar", 1],
  ["gb-as", 0],
  ["gb-fk", 1],
  ["gb-zg", 0],
  ["gb-cc", 0],
  ["gb-du", 1],
  ["gb-fi", 0],
  ["gb-ml", 0],
  ["gb-wh", 0],
  ["gb-bo", 0],
  ["gb-dh", 0],
  ["gb-da", 0],
  ["gb-hp", 0],
  ["gb-mb", 0],
  ["gb-rc", 0],
  ["gb-zt", 0],
  ["gb-ha", 8],
  ["gb-zh", 0],
  ["gb-2318", 0],
  ["gb-gc", 3],
  ["gb-mk", 1],
  ["gb-bu", 1],
  ["gb-bn", 0],
  ["gb-bs", 2],
  ["gb-ns", 0],
  ["gb-sj", 0],
  ["gb-2389", 2],
  ["gb-ds", 0],
  ["gb-2391", 0],
  ["gb-ht", 13],
  ["gb-cm", 0],
  ["gb-kh", 1],
  ["gb-ne", 0],
  ["gb-ba", 0],
  ["gb-xb", 0],
  ["gb-ke", 4],
  ["gb-bz", 1],
  ["gb-be", 3],
  ["gb-cn", 2],
  ["gb-eg", 5],
  ["gb-ef", 0],
  ["gb-gr", 0],
  ["gb-hf", 2],
  ["gb-hu", 3],
  ["gb-it", 0],
  ["gb-kc", 8],
  ["gb-cy", 0],
  ["gb-me", 1],
  ["gb-rb", 1],
  ["gb-ru", 0],
  ["gb-su", 0],
  ["gb-th", 1],
  ["gb-wf", 0],
  ["gb-ww", 3],
  ["gb-we", 3],
  ["gb-li", 1],
  ["gb-bf", 3],
  ["gb-ld", 0],
  ["gb-nm", 0],
  ["gb-am", 0],
  ["gb-bb", 0],
  ["gb-cr", 0],
  ["gb-dn", 0],
  ["gb-2347", 0],
  ["gb-lb", 0],
  ["gb-mf", 0],
  ["gb-om", 0],
  ["gb-lr", 0],
  ["gb-cf", 0],
  ["gb-nw", 0],
  ["gb-2354", 0],
  ["gb-dw", 0],
  ["gb-cl", 0],
  ["gb-by", 0],
  ["gb-cs", 0],
  ["gb-pe", 0],
  ["gb-2301", 0],
  ["gb-gd", 0],
  ["gb-sp", 0],
  ["gb-po", 0],
  ["gb-mt", 0],
  ["gb-bj", 0],
  ["gb-cp", 0],
  ["gb-rt", 0],
  ["gb-ca", 2],
  ["gb-vg", 0],
  ["gb-np", 0],
  ["gb-sw", 1],
  ["gb-7122", 2],
  ["gb-bw", 0],
  ["gb-la", 4],
  ["gb-ey", 0],
  ["gb-yk", 3],
  ["gb-di", 0],
  ["gb-fl", 0],
  ["gb-wx", 0],
  ["gb-bg", 0],
  ["gb-no", 0],
  ["gb-tf", 0],
  ["gb-lm", 0],
  ["gb-sb", 0],
  ["gb-fe", 0],
  ["gb-ny", 0],
  ["gb-2420", 0],
  ["gb-tb", 6],
  ["gb-ex", 5],
  ["gb-nf", 0],
  ["gb-bh", 7],
  ["gb-hv", 0],
  ["gb-tr", 0],
  ["gb-ss", 1],
  ["gb-ws", 3],
  ["gb-wr", 3],
  ["gb-hd", 1],
  ["gb-kt", 2],
  ["gb-sr", 5],
  ["gb-es", 0],
  ["gb-ox", 5],
  ["gb-sn", 2],
  ["gb-na", 4],
  ["gb-rl", 0],
  ["gb-hk", 2],
  ["gb-hy", 0],
  ["gb-hr", 1],
  ["gb-lt", 3],
  ["gb-lw", 2],
  ["gb-nh", 0],
  ["gb-sq", 3],
  ["gb-he", 0],
  ["gb-st", 4],
  ["gb-wc", 0],
  ["gb-tk", 0],
  ["gb-6338", 2],
  ["gb-nb", 0],
  ["gb-2367", 3],
  ["gb-7113", 1],
  ["gb-7114", 0],
  ["gb-7115", 0],
  ["gb-7116", 0],
  ["gb-2364", 0],
  ["gb-7118", 0],
  ["gb-7119", 4],
  ["gb-wt", 0],
  ["gb-ms", 5],
  ["gb-7117", 0],
  ["gb-3265", 0],
  ["gb-7130", 0],
  ["gb-7131", 1],
  ["gb-7132", 3],
  ["gb-7133", 0],
  ["gb-3266", 0],
  ["gb-7121", 3],
  ["gb-7123", 3],
  ["gb-7124", 0],
  ["gb-7125", 2],
  ["gb-7126", 1],
  ["gb-7127", 1],
  ["gb-7128", 5],
  ["gb-7129", 4],
  ["gb-2366", 0],
  ["gb-nt", 3],
  ["gb-3267", 0],
  ["gb-7134", 0],
  ["gb-7135", 2],
  ["gb-nl", 0],
  ["gb-7136", 0],
  ["gb-2377", 1],
  ["gb-7137", 0],
  ["gb-7138", 0],
  ["gb-7139", 0],
  ["gb-7140", 0],
  ["gb-7141", 0],
  ["gb-7142", 3],
  ["gb-2381", 0],
  ["gb-bd", 0],
  ["gb-2388", 1],
  ["gb-7143", 0],
  ["gb-7144", 3],
  ["gb-7145", 2],
  ["gb-7146", 0],
  ["gb-7147", 0],
  ["gb-7149", 4],
  ["gb-so", 0],
  ["gb-7150", 0],
  ["gb-7151", 0],
  ["gb-pb", 1],
  ["gb-iw", 1],
  ["gb-mo", 0],
  ["gb-ag", 0],
  ["gb-el", 0],
  ["gb-sm", 2],
  ["gb-ci", 0],
  ["gb-hl", 5],
  ["gb-co", 3],
  ["gb-cw", 0],
  ["gb-nd", 0],
  ["gb-dg", 0],
  ["gb-cu", 5],
  ["gb-sf", 0],
  ["gb-mw", 2],
  ["gb-lu", 2],
  ["gb-wl", 3],
  ["gb-3271", 0]
], [
  ["gb-ay", 0],
  ["gb-3270", 0],
  ["gb-hi", 0],
  ["gb-ab", 0],
  ["gb-ps", 0],
  ["gb-wi", 0],
  ["gb-my", 0],
  ["gb-7398", 0],
  ["gb-eb", 0],
  ["gb-lc", 1],
  ["gb-2393", 2],
  ["gb-db", 4],
  ["gb-de", 0],
  ["gb-an", 0],
  ["gb-bl", 0],
  ["gb-ng", 2],
  ["gb-do", 12],
  ["gb-2458", 0],
  ["gb-er", 0],
  ["gb-ea", 0],
  ["gb-gg", 3],
  ["gb-ed", 0],
  ["gb-ic", 0],
  ["gb-2446", 0],
  ["gb-nn", 1],
  ["gb-rf", 0],
  ["gb-sa", 1],
  ["gb-sl", 1],
  ["gb-wd", 0],
  ["gb-ar", 1],
  ["gb-as", 4],
  ["gb-fk", 2],
  ["gb-zg", 0],
  ["gb-cc", 0],
  ["gb-du", 1],
  ["gb-fi", 2],
  ["gb-ml", 0],
  ["gb-wh", 3],
  ["gb-bo", 0],
  ["gb-dh", 0],
  ["gb-da", 0],
  ["gb-hp", 0],
  ["gb-mb", 0],
  ["gb-rc", 0],
  ["gb-zt", 0],
  ["gb-ha", 8],
  ["gb-zh", 0],
  ["gb-2318", 0],
  ["gb-gc", 3],
  ["gb-mk", 1],
  ["gb-bu", 1],
  ["gb-bn", 0],
  ["gb-bs", 2],
  ["gb-ns", 0],
  ["gb-sj", 0],
  ["gb-2389", 2],
  ["gb-ds", 0],
  ["gb-2391", 0],
  ["gb-ht", 13],
  ["gb-cm", 0],
  ["gb-kh", 1],
  ["gb-ne", 0],
  ["gb-ba", 0],
  ["gb-xb", 0],
  ["gb-ke", 4],
  ["gb-bz", 1],
  ["gb-be", 3],
  ["gb-cn", 4],
  ["gb-eg", 5],
  ["gb-ef", 1],
  ["gb-gr", 0],
  ["gb-hf", 2],
  ["gb-hu", 3],
  ["gb-it", 0],
  ["gb-kc", 8],
  ["gb-cy", 0],
  ["gb-me", 1],
  ["gb-rb", 1],
  ["gb-ru", 0],
  ["gb-su", 1],
  ["gb-th", 1],
  ["gb-wf", 1],
  ["gb-ww", 3],
  ["gb-we", 3],
  ["gb-li", 1],
  ["gb-bf", 3],
  ["gb-ld", 0],
  ["gb-nm", 0],
  ["gb-am", 0],
  ["gb-bb", 0],
  ["gb-cr", 0],
  ["gb-dn", 0],
  ["gb-2347", 0],
  ["gb-lb", 0],
  ["gb-mf", 0],
  ["gb-om", 0],
  ["gb-lr", 0],
  ["gb-cf", 0],
  ["gb-nw", 0],
  ["gb-2354", 0],
  ["gb-dw", 0],
  ["gb-cl", 0],
  ["gb-by", 0],
  ["gb-cs", 0],
  ["gb-pe", 0],
  ["gb-2301", 0],
  ["gb-gd", 0],
  ["gb-sp", 0],
  ["gb-po", 0],
  ["gb-mt", 0],
  ["gb-bj", 0],
  ["gb-cp", 0],
  ["gb-rt", 0],
  ["gb-ca", 2],
  ["gb-vg", 0],
  ["gb-np", 0],
  ["gb-sw", 1],
  ["gb-7122", 3],
  ["gb-bw", 0],
  ["gb-la", 4],
  ["gb-ey", 0],
  ["gb-yk", 3],
  ["gb-di", 0],
  ["gb-fl", 0],
  ["gb-wx", 0],
  ["gb-bg", 0],
  ["gb-no", 0],
  ["gb-tf", 0],
  ["gb-lm", 0],
  ["gb-sb", 0],
  ["gb-fe", 0],
  ["gb-ny", 0],
  ["gb-2420", 0],
  ["gb-tb", 7],
  ["gb-ex", 5],
  ["gb-nf", 0],
  ["gb-bh", 8],
  ["gb-hv", 2],
  ["gb-tr", 0],
  ["gb-ss", 1],
  ["gb-ws", 3],
  ["gb-wr", 4],
  ["gb-hd", 1],
  ["gb-kt", 2],
  ["gb-sr", 6],
  ["gb-es", 0],
  ["gb-ox", 5],
  ["gb-sn", 2],
  ["gb-na", 4],
  ["gb-rl", 0],
  ["gb-hk", 2],
  ["gb-hy", 0],
  ["gb-hr", 1],
  ["gb-lt", 3],
  ["gb-lw", 3],
  ["gb-nh", 0],
  ["gb-sq", 5],
  ["gb-he", 0],
  ["gb-st", 4],
  ["gb-wc", 0],
  ["gb-tk", 0],
  ["gb-6338", 2],
  ["gb-nb", 0],
  ["gb-2367", 4],
  ["gb-7113", 1],
  ["gb-7114", 0],
  ["gb-7115", 1],
  ["gb-7116", 0],
  ["gb-2364", 0],
  ["gb-7118", 0],
  ["gb-7119", 5],
  ["gb-wt", 0],
  ["gb-ms", 5],
  ["gb-7117", 0],
  ["gb-3265", 0],
  ["gb-7130", 0],
  ["gb-7131", 1],
  ["gb-7132", 5],
  ["gb-7133", 0],
  ["gb-3266", 0],
  ["gb-7121", 3],
  ["gb-7123", 3],
  ["gb-7124", 0],
  ["gb-7125", 4],
  ["gb-7126", 1],
  ["gb-7127", 1],
  ["gb-7128", 5],
  ["gb-7129", 4],
  ["gb-2366", 0],
  ["gb-nt", 5],
  ["gb-3267", 0],
  ["gb-7134", 0],
  ["gb-7135", 2],
  ["gb-nl", 0],
  ["gb-7136", 0],
  ["gb-2377", 1],
  ["gb-7137", 0],
  ["gb-7138", 1],
  ["gb-7139", 2],
  ["gb-7140", 0],
  ["gb-7141", 0],
  ["gb-7142", 2],
  ["gb-2381", 0],
  ["gb-bd", 0],
  ["gb-2388", 1],
  ["gb-7143", 0],
  ["gb-7144", 3],
  ["gb-7145", 2],
  ["gb-7146", 0],
  ["gb-7147", 0],
  ["gb-7149", 5],
  ["gb-so", 0],
  ["gb-7150", 0],
  ["gb-7151", 0],
  ["gb-pb", 1],
  ["gb-iw", 1],
  ["gb-mo", 0],
  ["gb-ag", 0],
  ["gb-el", 0],
  ["gb-sm", 2],
  ["gb-ci", 0],
  ["gb-hl", 5],
  ["gb-co", 4],
  ["gb-cw", 0],
  ["gb-nd", 0],
  ["gb-dg", 0],
  ["gb-cu", 5],
  ["gb-sf", 1],
  ["gb-mw", 2],
  ["gb-lu", 2],
  ["gb-wl", 3],
  ["gb-3271", 0]
]]
export default {
  name: "Index",
  components: {
    Header,
    // RoutePanel,
    // AddTask,
    NewsPanel,
    CookieMask,
    LMask
  },
  data () {
    return {
      UKMapSettings: UKMapSettings,
      ChartSettings: ChartSettings,
      /**
       * @routeType
       * @ 1 => 个人路线
       * @ 2 => 官方路线
       */
      touchChangeType: "text",
      routeType: 1,
      renderStatus: false,
      officialTaskList: {},
      indivTaskList: {},
      maskStatus: false,
      renderingTaskList: [
        { startCode: [0, 1], targetCode: [2, 3], Date: "rendering", time: "rendering" }
      ],
      basicTravelData: {
        from: "",
        to: "",
        time: ""
      },
      newsList: {
        0: {
          title:
            "Coronavirus latest news: people with mild symptoms to be told to stay home, as number of cases in UK rises to 319",
          source: "telegraph",
          time: "2020, Mar 9th, 17:34"
        },
        1: {
          title:
            "Coronavirus farce as dozens of planes fly to UK from Italy's quarantine zones with no checks",
          source: "telegraph",
          time: "2020, Mar 9th, 12:38"
        },
        2: {
          title: "Coronavirus: UK to remain in 'containment' phase of response",
          source: "BBC News",
          time: "2020, Mar 9th, 16:47"
        }
      },
      dataSet: {
        total: 0,
        death: 0,
        cured: 0,
        increase: 0
      },
      animationList: {},
      addTaskStatus: false
    };
  },
  mounted () {
    this.showMask();
    this.showNumAnimation();
    this.statusCheck();
  },
  methods: {

    showMask () {
      const self = this;
      if (self.$cookieConfirm) {
        self.maskStatus = false;
      } else {
        self.maskStatus = true;
      }
    },
    hideMask () {
      const self = this;
      self.maskStatus = false;
      self.$acceptCookie();
    },
    showNumAnimation () {
      const self = this;
      const targetValue = {
        total: 319,
        death: 5,
        cured: 18,
        increase: 46
      };
      const objKey = Object.keys(self.dataSet);
      for (let i = 0; i < objKey.length; i++) {
        self.tweenJS(0, targetValue[objKey[i]], objKey[i]);
      }
    },
    tweenJS (start, end, key) {
      let frameHandler;
      const self = this;
      const animate = function (currentTime) {
        TWEEN.update(currentTime);
        frameHandler = requestAnimationFrame(animate);
      };
      const myTween = new TWEEN.Tween({ tweeningValue: start })
        .to({ tweeningValue: end }, 1000)
        .onUpdate(() => {
          self.dataSet[key] = myTween._object.tweeningValue.toFixed(0);
        })
        .onComplete(() => {
          // Make sure to clean up after ourselves.
          cancelAnimationFrame(frameHandler);
        })
        .start();
      frameHandler = requestAnimationFrame(animate);
    },
    touchChange () {
      const self = this;
      self.touchChangeType = "datetime-local";
    },
    changeRouteType (t) {
      const self = this;
      self.routeType = t;
      if (t == 1) {
        if (Object.keys(self.officialTaskList).length == 0) {
          self.getRoute(t);
        }
      }
      if (t == 2) {
        if (Object.keys(self.indivTaskList).length == 0) {
          self.getRoute(t);
        }
      }
    },
    getRoute (t = 1) {
      const self = this;

      Post(`${exportAddress.task}/page`, {
        query: {
          order_: "start",
          type: t,
          start: self.$currentCity,
          pageSize_: 2
        }
      }).then(res => {
        self.renderStatus = true;
        self.handleRequest(res);
      });
    },
    handleRequest (res) {
      const self = this;
      if (res) {
        if (res.code !== 0) {
          self.errorData = {
            errno: 1,
            errmsg: "获取列表失败",
            redirect: 0,
            path: "/"
          };
        } else {
          for (let j = 0; j < res.data.rows.length; j++) {
            let dataJ = res.data.rows[j];
            /**
             * 优先发异步请求具体任务状态
             */
            self.getRouteDtl(dataJ.tid);
            dataJ.startCode = dataJ.startCode.split(",");
            dataJ.targetCode = dataJ.targetCode.split(",");

            let formatTime = self.timeFormat(dataJ.startTime);
            dataJ.startTime = formatTime["yy-mm-dd hh:mm"];
            dataJ.Time = formatTime["hh:mm"];
            dataJ.Date = formatTime["yy-mm-dd"];
            dataJ.dtlStatus = false;
            if (self.routeType == 1) {
              self.officialTaskList[dataJ.tid] = dataJ;
            } else {
              self.indivTaskList[dataJ.tid] = dataJ;
            }
          }
        }
      } else {
        return false;
      }
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
    handleRouteDtl (d, t) {
      const self = this;
      let currentNum = d.length;
      let userIn = false;
      d.forEach(v => {
        if (v.partnerUuid == self.$uuid) {
          userIn = true;
        }
      });
      if (self.routeType == 1) {
        self.officialTaskList[t].dtlStatus = true;
        self.officialTaskList[t].currentNum = currentNum;
        self.officialTaskList[t].userIn = userIn;
      } else {
        self.indivTaskList[t].dtlStatus = true;
        self.indivTaskList[t].currentNum = currentNum;
        self.indivTaskList[t].userIn = userIn;
      }
    },
    timeFormat (d) {
      /**
       * @type 0 => return 年月日 时:分
       * @type 1 => return 时:分
       * @type 2 => return 年月日
       */
      const self = this;
      let time = new Date(d);
      let year = time.getFullYear();
      let month = time.getMonth() + 1;
      let days = time.getDate();
      let hours = time.getHours();
      let minutes = time.getMinutes();
      let seconds = time.getSeconds();
      let formatTime = {
        "yy-mm-dd hh:mm": `${year}年${month}月${days} ${hours}:${minutes}`,
        "hh:mm": `${hours}:${minutes}`,
        "yy-mm-dd": `${year}年${month}月${days}日`
      };
      return formatTime;
    },
    statusCheck (s = 0) {
      const self = this;
      let geoGet = setInterval(() => {
        if (self.$currentCity) {
          self.getRoute();
          clearInterval(geoGet);
        }
      }, 100);
    },
    createNewTask () {
      const self = this;
      self.addTaskStatus = true;
    },
    cancelNewTask () {
      const self = this;
      self.addTaskStatus = false;
    },
    routerDirect (t) {
      const self = this;
      self.$router.push(t);
    }
  }
};
</script>

<style scoped lang="scss">
$designWidth: 750;
@import "@/assets/library/px2rem.scss";
.index-main-container {
  width: 100%;
  height: 100%;
  background-color: #e5e5e5;
  overflow: scroll;
  //TODO
  .rendering {
    color: #999999;
    background-color: #999999;
    border-radius: px2rem(5);
  }
  .base-header {
    display: flex;
    margin-top: px2rem(32);
    justify-content: space-between;
    .create-text-container {
      text-align: left;
      .text-header {
        padding-left: px2rem(24);
        border-left: px2rem(6) solid #2b44ff;
        font-size: px2rem(30);
        font-weight: 500;
      }
      .text-detail {
        padding-left: px2rem(30);
        font-size: px2rem(22);
        color: #999999;
      }
    }
  }
  .slider-container {
    width: 100%;
    height: px2rem(258);
    margin-top: px2rem(102);
    margin-bottom: px2rem(16);
    img {
      width: 100%;
      height: 100%;
    }
  }
  .create-task-container {
    width: 100%;
    height: px2rem(464);
    background-color: #ffffff;
    overflow: hidden;
    .create-body {
      height: px2rem(316);
      margin: px2rem(24) px2rem(24) 0 px2rem(24);
      background: #ffffff;
      box-shadow: 0px 0px px2rem(20) rgba(0, 0, 0, 0.06);
      border-radius: px2rem(16);
      box-sizing: border-box;
      .create-input-container {
        height: px2rem(104);
        margin: 0 px2rem(24);
        display: flex;
        align-items: center;
        box-sizing: border-box;
        &.had-bottom-line {
          border-bottom: px2rem(1) solid #e8e8e8;
        }
        .point {
          width: px2rem(14);
          height: px2rem(14);
          border-radius: 50%;
          &.yellow-bg {
            background-color: #ffb72b;
          }
          &.blue-bg {
            background-color: #2b44ff;
          }
          &.red-bg {
            background-color: #ff512b;
          }
        }
        .input-part {
          width: 100%;
          input {
            display: block;
            width: 100%;
            height: 100%;
            padding-left: px2rem(24);
            font-size: px2rem(26);
          }
        }
      }
    }
  }
  .route-selector-container {
    width: 100%;
    height: px2rem(80);
    display: flex;
    background-color: #ffffff;
    border-bottom: px2rem(2) solid #e8e8e8;
    .route-bar {
      width: 50%;
      font-size: px2rem(26);
      line-height: px2rem(80);
      margin: 0 px2rem(8);
      &.active {
        border-bottom: px2rem(4) solid #000;
      }
    }
  }
  .route-display-container {
    width: 100%;
    // height: px2rem(882);
    background-color: #ffffff;
    overflow: hidden;
    .route-panel {
      height: px2rem(380);
      margin: px2rem(24) px2rem(24);
      background: #ffffff;
      box-shadow: 0px 0px px2rem(20) rgba(0, 0, 0, 0.06);
      border-radius: px2rem(16);
      .route-client-info {
        height: px2rem(42);
        display: flex;
        align-items: center;
        padding: px2rem(30) 0 0 px2rem(24);
        .client-name {
          font-size: px2rem(30);
          line-height: px2rem(42);
        }
        .client-tag-container {
          display: flex;
          .type-tag {
            height: px2rem(42);
            font-size: px2rem(20);
            line-height: px2rem(42);
            padding: 0 px2rem(5);
            border-radius: px2rem(8);
            margin: 0 px2rem(8);
            &.red-bg {
              background-color: rgba(255, 81, 43, 0.1);
              color: #ff512b;
            }
            &.yellow-bg {
              background: rgba(255, 145, 43, 0.1);
              color: #ff912b;
            }
          }
        }
      }
      .route-location-info {
        .location-info {
          margin-top: px2rem(25);
          display: flex;
          .position-signal {
            width: px2rem(14);
            height: px2rem(14);
            border-radius: 50%;
            background-color: #ffb72b;
            margin: px2rem(15) 0 0 px2rem(24);
            .yellow-bg {
              background-color: #ffb72b;
            }
          }
          .position-text-container {
            .location {
              height: px2rem(40);
              display: flex;
              align-items: baseline;
              margin-left: px2rem(18);
              font-size: px2rem(30);
              .distance {
                margin-left: px2rem(16);
                font-size: px2rem(22);
                color: #999999;
              }
            }
            .postcode {
              margin-left: px2rem(18);
              font-size: px2rem(22);
              color: #666666;
            }
          }
        }
        .spilt-line {
          height: px2rem(2);
          margin: px2rem(24) px2rem(24) px2rem(16) px2rem(24);
          background-color: #f5f5f5;
        }
      }
      .route-status-container {
        display: flex;
        justify-content: space-between;
        align-items: center;
        margin: 0 px2rem(24);
        .route-time {
          font-size: px2rem(26);
        }
        .apply-btn {
          width: px2rem(160);
          height: px2rem(56);
          border-radius: px2rem(28);
          font-size: px2rem(22);
          line-height: px2rem(56);
          &.active {
            border: px2rem(2) solid #2b44ff;
            color: #2b44ff;
          }
          &.done {
            background-color: #eeeeee;
            color: #999999;
          }
        }
      }
    }
    .route-more {
      font-size: px2rem(26);
      margin-bottom: px2rem(16);
    }
  }
  .data-display-container {
    //TODO
    display: flex;
    flex-direction: column;
    width: 100%;
    overflow: hidden;
    margin-top: px2rem(16);
    background-color: #ffffff;
    .base-header {
      height: px2rem(100);
      .update-time {
        width: px2rem(320);
        height: px2rem(32);
        font-size: px2rem(20);
        background-color: #eeeeee;
        color: #999999;
        line-height: px2rem(34);
        border-top-left-radius: px2rem(16);
        border-bottom-left-radius: px2rem(16);
      }
    }
    .text-data-container {
      display: flex;
      // justify-content: space-around;
      .text-data-detail {
        width: px2rem(176);
        height: px2rem(88);
        .num {
          font-size: px2rem(36);
          font-family: PingFang SC;
          font-style: normal;
          font-weight: 600;
          &.red-font {
            color: #ef3d3d;
          }
          &.yellow-font {
            color: #ef723d;
          }
          &.blue-font {
            color: #2b44ff;
          }
          &.purple-font {
            color: #656eb0;
          }
          &.green-font {
            color: green;
          }
        }
        .desc {
          font-size: px2rem(24);
        }
      }
    }
    .map-data-container {
      width: 100%;
      height: px2rem(816);
      div[data-highcharts-chart] {
        height: px2rem(816) !important;
      }
      #highcharts-nhagfnv-0 {
        height: px2rem(816) !important;
      }
    }
  }
  .chart-display-container {
    width: 100%;
    //height: px2rem(400);
    background-color: #ffffff;
    margin-top: px2rem(16);
    overflow: hidden;
  }
  .news-display-container {
    width: 100%;
    background-color: #ffffff;
    overflow: hidden;
    margin-top: px2rem(16);
    padding-bottom: px2rem(20);
    .news-body {
      margin: px2rem(24) px2rem(24) 0 px2rem(24);
      .news-data {
        width: 100%;
        // height: px2rem(144);
        padding: px2rem(24) 0;
        border-bottom: px2rem(2) solid #e8e8e8;
        display: flex;
        flex-direction: column;
        justify-content: space-around;
        .news-title {
          margin-bottom: px2rem(16);
          font-family: PingFang SC;
          font-style: normal;
          font-weight: normal;
          font-size: px2rem(30);
          text-align: left;
          line-height: px2rem(44);
          overflow: hidden;
          text-overflow: ellipsis;
          display: -webkit-box;
          -webkit-line-clamp: 2;
          -webkit-box-orient: vertical;
        }
        .news-source {
          display: flex;
          font-size: px2rem(24);
          color: #999999;
          .source-plat {
            margin-right: px2rem(16);
          }
        }
      }
      .news-more {
        margin-top: px2rem(16);
        font-size: px2rem(26);
      }
    }
  }
  .addnew-task-btn {
    position: fixed;
    bottom: px2rem(16);
    left: 50%;
    transform: translateX(-50%);
    width: px2rem(696);
    height: px2rem(88);
    line-height: px2rem(88);
    background-color: #2b44ff;
    border-radius: px2rem(44);
    font-size: px2rem(30);
    color: #ffffff;
  }
}
</style>
