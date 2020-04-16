// Prepare demo data
// Data is joined to map using value of 'hc-key' property by default.
// See API docs for 'joinBy' for more info on linking data and map.

// Create the chart

// import dateList from "../api/date.js";;

function areaChart(totalDate, totalNum, dailyDate, dailyNum) {
  return {
    chart: {
      zoomType: "xy",
      backgroundColor: "#232322"
    },
    title: {
      text: undefined
    },
    subtitle: {
      text: undefined
    },
    xAxis: [
      {
        visible: true,
        categories: totalDate
      }
    ],
    yAxis: [
      {
        title: {
          text: undefined
        },
        visible: true,
        reversed: false
        // max: Math.max(...totalNum) + 100
      },
      {
        // Secondary yAxis
        title: {
          text: undefined
        },
        //max: Math.max(...deathNum) + 100,
        opposite: true
      }
    ],
    plotOptions: {
      series: {
        color: "#b32929"
      }
    },
    series: [
      {
        name: "当日总共确诊",
        data: totalNum,
        type: "column",
        yAxis: 1,
        color: "#580606"
      },
      {
        name: "当日新增确诊",
        type: "spline",
        data: dailyNum,
        color: "#ffffff"
      }
    ]
  };
}

export default areaChart;
