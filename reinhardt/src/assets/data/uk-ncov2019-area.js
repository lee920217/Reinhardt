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
          y: 104
        },
        {
          name: "Midlands",
          y: 42
        },
        {
          name: "North East and Yorkshire",
          y: 32
        },
        {
          name: "North West",
          y: 43
        },
        {
          name: "South East",
          y: 60
        },
        {
          name: "South West",
          y: 44
        },
        {
          name: "To be determined",
          y: 30
        }
      ]
    }
  ]
};

export default chartSettings;
