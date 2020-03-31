// Prepare demo data
// Data is joined to map using value of 'hc-key' property by default.
// See API docs for 'joinBy' for more info on linking data and map.

// Create the chart

var chartSettings = {
  chart: {
    plotBackgroundColor: null,
    plotBorderWidth: null,
    plotShadow: false,
    type: "pie",
    backgroundColor: "#232322"
  },
  title: {
    text: "确诊区域"
  },
  subtitle: {
    text: "United Kingdom"
  },
  tooltip: {
    pointFormat: "{series.name}: <b>{point.percentage:.1f}%</b>"
  },
  accessibility: {
    point: {
      valueSuffix: "%"
    }
  },
  plotOptions: {
    pie: {
      allowPointSelect: true,
      cursor: "pointer",
      dataLabels: {
        enabled: false
      },
      showInLegend: true
    }
  },
  legend: {
    itemStyle: {
      color: "#b3b2b2"
    }
  },
  series: [
    {
      name: "Area",
      colorByPoint: true,
      data: [
        {
          name: "英格兰东部",
          y: 1339
        },
        {
          name: "伦敦",
          y: 6521
        },
        {
          name: "中部地区",
          y: 3188
        },
        {
          name: "东北约克郡",
          y: 2087
        },
        {
          name: "西北地区",
          y: 1847
        },
        {
          name: "东南地区",
          y: 2121
        },
        {
          name: "西南地区",
          y: 818
        }
      ]
    }
  ]
};

export default chartSettings;
