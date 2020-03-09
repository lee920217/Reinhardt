// Prepare demo data
// Data is joined to map using value of 'hc-key' property by default.
// See API docs for 'joinBy' for more info on linking data and map.

// Create the chart

var chartSettings = {
  chart: {
    zoomType: "xy"
  },
  title: {
    text: "Cases of coronavirus in UK"
  },
  subtitle: {
    text: "Trend and total patients"
  },
  xAxis: [
    {
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
        "03-09"
      ],
      crosshair: true
    }
  ],
  yAxis: [
    {
      // Primary yAxis
      labels: {
        format: "{value}",
        style: {
          color: "#d02c2c"
        }
      },
      title: {
        text: undefined,
        style: {
          color: "#d02c2c"
        }
      }
    },
    {
      // Secondary yAxis
      title: {
        text: undefined,
        style: {
          color: "rgb(124, 181, 236)"
        }
      },
      labels: {
        format: "{value}",
        style: {
          color: "rgb(124, 181, 236)"
        }
      },
      opposite: true
    }
  ],
  tooltip: {
    shared: true
  },
  legend: {
    layout: "vertical",
    align: "left",
    x: 120,
    verticalAlign: "top",
    y: 100,
    floating: true,
    backgroundColor: "rgba(255,255,255,0.25)" // theme
  },
  series: [
    {
      name: "Total Increase",
      type: "column",
      yAxis: 1,
      color: "rgb(124, 181, 236)",
      data: [2, 1, 4, 0, 1, 4, 3, 5, 1, 14, 4, 12, 34, 31, 47, 48, 67, 46]
    },
    {
      name: "Total Patients",
      type: "spline",
      color: "#d02c2c",
      data: [2, 3, 7, 7, 8, 12, 15, 20, 21, 35, 39, 51, 85, 116, 163, 211, 273, 319]
    }
  ]
};

export default chartSettings;
