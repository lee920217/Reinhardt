// Prepare demo data
// Data is joined to map using value of 'hc-key' property by default.
// See API docs for 'joinBy' for more info on linking data and map.

// Create the chart

var chartSettings = {
  chart: {
    plotBackgroundColor: null,
    plotBorderWidth: null,
    plotShadow: false,
    type: "pie"
  },
  title: {
    text: "Areas of Corona Virus"
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
  series: [
    {
      name: "Area",
      colorByPoint: true,
      data: [
        {
          name: "East of England",
          y: 32
        },
        {
          name: "London",
          y: 136
        },
        {
          name: "Midlands",
          y: 44
        },
        {
          name: "North East and Yorkshire",
          y: 44
        },
        {
          name: "North West",
          y: 53
        },
        {
          name: "South East",
          y: 83
        },
        {
          name: "South West",
          y: 42
        },
        {
          name: "To be determined",
          y: 57
        }
      ]
    }
  ]
};

export default chartSettings;
