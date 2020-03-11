// Prepare demo data
// Data is joined to map using value of 'hc-key' property by default.
// See API docs for 'joinBy' for more info on linking data and map.

// Create the chart

var chartSettings = {
  chart: {
    type: "area"
  },
  title: {
    text: "Increase Cases of coronavirus"
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
      "03-11"
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
      name: "Increase patients",
      data: [2, 1, 4, 0, 1, 4, 3, 5, 3, 12, 5, 11, 34, 30, 48, 43, 67, 46, 54, 83]
    }
  ]
};

export default chartSettings;
