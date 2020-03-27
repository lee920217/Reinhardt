// Prepare demo data
// Data is joined to map using value of 'hc-key' property by default.
// See API docs for 'joinBy' for more info on linking data and map.

// Create the chart
import data from "./uk-data.js";
var settings = {
  chart: {
    map: "countries/gb/gb-all"
  },
  colors: ["#ffffff", "#F88F8F", "#E34E4E", "#860A0A", "#580606", "#400404"],
  title: {
    style: {
      display: "none"
    }
  },
  mapNavigation: {
    enabled: true,
    buttonOptions: {
      verticalAlign: "bottom"
    }
  },

  colorAxis: {
    dataClassColor: "category",
    dataClasses: [
      {
        to: 0
      },
      {
        from: 1,
        to: 5
      },
      {
        from: 6,
        to: 20
      },
      {
        from: 21,
        to: 100
      },
      {
        from: 101,
        to: 200
      },
      {
        from: 201,
        to: 400
      }
    ]
  },
  legend: {
    align: "left",
    verticalAlign: "bottom",
    floating: true,
    layout: "vertical",
    valueDecimals: 0,
    backgroundColor: "rgba(255,255,255,0.9)",
    symbolRadius: 0,
    symbolHeight: 14,
    itemStyle: {
      color: "#000000",
      fontWeight: "bold"
    }
  },
  series: [
    {
      data: data,
      name: "肺炎疫情",
      itemStyle: {
        color: "#000000",
        fontWeight: "bold",
        fontSize: "24px"
      },
      states: {
        hover: {
          color: "#BADA55"
        }
      }
    }
  ]
};

export default settings;
