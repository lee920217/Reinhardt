// Prepare demo data
// Data is joined to map using value of 'hc-key' property by default.
// See API docs for 'joinBy' for more info on linking data and map.

// Create the chart
import dateList from "../api/date.js";

var chartSettings = {
  chart: {
    type: "column"
  },
  title: {
    text: "每日增长总数"
  },
  subtitle: {
    text: "United Kingdom"
  },
  xAxis: {
    visible: true,
    categories: dateList
  },
  yAxis: {
    title: {
      text: undefined
    },
    visible: true,
    reversed: false
  },
  plotOptions: {
    series: {
      color: "#b32929"
    }
  },
  series: [
    {
      name: "The number of daily increasing",
      data: [
        2,
        1,
        4,
        0,
        1,
        4,
        3,
        5,
        3,
        12,
        5,
        11,
        34,
        30,
        48,
        43,
        67,
        46,
        54,
        83,
        134,
        208,
        342,
        232,
        171,
        407,
        676,
        643,
        715,
        1035,
        665,
        967,
        1427,
        1542,
        2129,
        2894,
        2510
      ]
    }
  ]
};

export default chartSettings;
