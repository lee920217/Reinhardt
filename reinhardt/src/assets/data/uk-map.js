// Prepare demo data
// Data is joined to map using value of 'hc-key' property by default.
// See API docs for 'joinBy' for more info on linking data and map.

// Create the chart
import data from "./uk-data.js";
var settings = {
  chart: {
    map: "countries/gb/gb-all",
    backgroundColor: "#232322"
  },
  colors: ["#f99e09", "#fd6904", "#dc3029", "#cc1142", "#a00f5c"],
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
        from: 1,
        to: 10
      },
      {
        from: 10,
        to: 50
      },
      {
        from: 50,
        to: 100
      },
      {
        from: 100,
        to: 500
      },
      {
        from: 500
      }
    ]
  },
  legend: {
    align: "left",
    verticalAlign: "bottom",
    floating: true,
    layout: "vertical",
    backgroundColor: "#232322",
    valueDecimals: 0,
    symbolRadius: 0,
    symbolHeight: 14,
    itemStyle: {
      color: "#b3b2b2",
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
