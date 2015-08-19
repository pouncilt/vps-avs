// override the path if necessary
var fusionChartsPath = '/fusion/charts/';
var fusionMapsPath = '/fusion/maps/';
var fusionWidgetsPath = '/fusion/widgets/';
var powerChartsPath = '/fusion/power/';

// FusionCharts
function createFusionChart(chartPath, chartId, width, height, opts) {
    return new FusionCharts(chartPath, chartId, width, height, "0", "1", "#FFFFFF", opts);
}

function renderFusionChart(chartPath, chartId, width, height, renderAt, opts) {
    var chart = createFusionChart(chartPath, chartId, width, height, opts);
    chart.render(renderAt);
    return chart;
}
      
function getChartData(client, url, renderAt, callback) {
    Ext.Ajax.request({
         url: url,
         success: function (result, request) {
             var jsonData = Ext.JSON.decode(result.responseText);
             var md = jsonData.metadata;
             var params = {swfUrl: fusionChartsPath + md.type + '.swf',
                           id: md.chartId, width: md.width, height: md.height,
                           debugMode: '0', registerWithJS: '1',
                           bgColor: '#FFFFFF', detectFlashVersion: '1',
                           autoInstallRedirect: '1'};
             var fusionChart = FusionCharts(md.chartId);
             if (fusionChart == null) {
                 fusionChart = new FusionCharts(params);
             }
             fusionChart.configure({
                 "PBarLoadingText" : "Loading Chart.",
                 "XMLLoadingText" : "Retrieving Data.",
                 "ChartNoDataText" : "No data to display.",
                 "RenderingChartText" : "Rendering Chart.",
                 "LoadDataErrorText" : "Error loading data.",
                 "InvalidXMLText" : "Invalid data."                   
             });
             fusionChart.setJSONData(result.responseText);
             fusionChart.render(renderAt);                         
             if (callback) {
                 callback(fusionChart, client, md);
             }
         }
    });        
}         

function exportChart(chartId, format) {
    var fusionChart = FusionCharts(chartId);
    if(fusionChart.hasRendered()) {
        fusionChart.exportChart({ exportFormat: format });
    }
}

// FusionMaps


// FusionWidgets


// PowerCharts
