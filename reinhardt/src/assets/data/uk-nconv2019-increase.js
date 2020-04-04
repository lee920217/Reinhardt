// Prepare demo data
// Data is joined to map using value of 'hc-key' property by default.
// See API docs for 'joinBy' for more info on linking data and map.

// Create the chart
// import dateList from "../api/date.js";
import { dateList, increaseNum } from "../api/chartData.js";

var chartSettings = {
  chart: {
    type: "column",
    backgroundColor: "#232322",
  },
  title: {
    text: undefined,
  },
  subtitle: {
    text: undefined,
  },
  xAxis: {
    visible: true,
    categories: dateList,
  },
  yAxis: {
    title: {
      text: undefined,
    },
    visible: true,
    reversed: false,
    max: Math.max(...increaseNum) + 100,
  },
  plotOptions: {
    series: {
      color: "#b32929",
    },
  },
  series: [
    {
      name: "The number of daily increasing",
      data: increaseNum,
    },
  ],
};

export default chartSettings;
