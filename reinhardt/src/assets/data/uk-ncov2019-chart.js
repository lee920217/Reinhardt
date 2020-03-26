// Prepare demo data
// Data is joined to map using value of 'hc-key' property by default.
// See API docs for 'joinBy' for more info on linking data and map.

// Create the chart

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
    categories: [
      "01-31",
      "02-06",
      "02-10",
      "02-11",
      "02-12",
      "02-23",
      "02-27",
      "02-28",
      "02-29",
      "03-01",
      "03-02",
      "03-03",
      "03-04",
      "03-05",
      "03-06",
      "03-07",
      "03-08",
      "03-09",
      "03-10",
      "03-11",
      "03-12",
      "03-13",
      "03-14",
      "03-15",
      "03-16",
      "03-17",
      "03-18",
      "03-19",
      "03-20",
      "03-21",
      "03-22",
      "03-23",
      "03-24",
      "03-25"
    ]
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
        9529
      ]
    }
  ]
};

export default chartSettings;
