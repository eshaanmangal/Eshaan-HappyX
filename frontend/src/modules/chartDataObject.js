export const chatData = {
    getPieChart( payload ) {

        const { chartData, totalCount } = payload;

        return {
            chart: {
              plotShadow: false,
              backgroundColor: null,
              plotBorderWidth: 0,
              height: 200,
            },
            title: {
              text: `<b style="font-size: 10px;color: #9D92B2">Total Count</b><br/><b style="font-size: 20px;color: #9D92B2">${totalCount}</b>`,
              align: 'center',
              verticalAlign: 'center',
              y: 50
            },
            legend: {
              symbolHeight: 10,
              symbolWidth: 10,
              symbolRadius: 2,
              verticalAlign: 'bottom',
              itemDistance: 5,
              itemMarginTop: 5,
              itemHoverStyle: {
                color: '#fff',
              },
              itemStyle: {
                  color: '#999',
                  fontSize: '10px',
                  lineHeight: 20,
              }
            },
            tooltip: {
                pointFormat: '<b>{point.y}</b>'
            },
            plotOptions: {
              pie: {
                dataLabels: {
                  enabled: false
                },
                borderWidth:0,
                center: ['50%', '50%'],
                size: '100%',
                colors: ["#9D92B2", "#06A99C","#FD7272", "#FF9416", "#ffe6e6"],
                showInLegend: true
              }
            },
            series: [{
              type: 'pie',
              innerSize: '85%',
              data: chartData
            }],
            credits: {
              enabled: false
            }
        };

    },

    getBarChart( payload ) {

        const { seriesData, categoriesData } = payload;

        return {
            chart: {
              type: "column",
              plotShadow: false,
              backgroundColor: null,
              plotBorderWidth: 0,
              height: 200,
            },
            credits: {
              enabled: false
            },
            legend: {
              symbolHeight: 10,
              symbolWidth: 10,
              symbolRadius: 2,
              verticalAlign: 'bottom',
              itemDistance: 5,
              itemMarginTop: 5,
              itemHoverStyle: {
                color: '#fff',
              },
              itemStyle: {
                  color: '#999',
                  fontSize: '10px',
                  lineHeight: 20,
              }
            },
            title: {
              text: ""
            },
            xAxis: {
              categories: categoriesData
            },
            yAxis: {
              title: {
                text: ""
              },
              labels: {
                enabled: false
            },
              gridLineWidth:0
            },
            tooltip: {
              pointFormat:
                '<span style="color:{series.color};font-size:10px;">{series.name}</span>: <b>{point.y}</b><br/>',
              shared: true
            },
            plotOptions: {
              column: {
                stacking: "percent",
                borderWidth:0,
              }
            },
            series: seriesData
          }

    }
}