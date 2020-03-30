// Prepare demo data
// Data is joined to map using value of 'hc-key' property by default.
// See API docs for 'joinBy' for more info on linking data and map.

// Create the chart

import dateList from "../api/date.js";

var chartSettings = {
  chart: {
    type: "area"
  },
  title: {
    text: "总计确诊病例"
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
      name: "The number of total patients",
      data: [
        2,
        3,
        7,
        7,
        8,
        12,
        15,
        20,
        23,
        35,
        40,
        51,
        85,
        115,
        163,
        206,
        273,
        319,
        373,
        456,
        590,
        798,
        1140,
        1372,
        1543,
        1951,
        2626,
        3269,
        3983,
        5018,
        5683,
        6650,
        8077,
        9529,
        11658,
        14579,
        17089,
        19522,
        22141
      ],
      color: "#b32929"
    },
    {
      name: "The number of total death",
      data: [
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        1,
        2,
        2,
        3,
        5,
        6,
        8,
        8,
        10,
        21,
        35,
        55,
        71,
        103,
        144,
        177,
        233,
        281,
        335,
        422,
        465,
        578,
        759,
        1019,
        1228,
        1415
      ],
      color: "#5d5353ba"
    }
  ]
};

export default chartSettings;
