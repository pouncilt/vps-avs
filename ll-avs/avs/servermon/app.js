Ext.Loader.setConfig({enabled:true});

Ext.Loader.setPath({
    'Ext': '/ext/4.2/src',
    'Ext.ux': '/ext/4.2/ux',
    'LLVA.Session': '/loginutils/js',
    'LLVA.Utils': '/jsonutils/js'
});

Ext.application({
    name: 'ServerMonitor',

    appFolder: 'app',
    controllers: [
        'Controller'
    ],    

    updateQueryStringParameter: function(uri, key, value) {
        var re = new RegExp("([?&])" + key + "=.*?(&|$)", "i");
        var separator = uri.indexOf('?') !== -1 ? "&" : "?";
        if (uri.match(re)) {
            return uri.replace(re, '$1' + key + "=" + value + '$2');
        } else {
            return uri + separator + key + "=" + value;
        }
    },    
    
    launch: function() {        
        SIGNON_TIMEOUT = 5000;
        DEF_STATION_NO = '605';
        DEF_PATIENT_DFN = '10190057';
        DEF_USER_DUZ = '42043';        

        var qry = window.location.search.substring(1);
        var httpArgs = Ext.Object.fromQueryString(qry);   
        if (httpArgs['stationNo'] != undefined) {
            STATION_NO = httpArgs['stationNo'];
        } else {
            STATION_NO = DEF_STATION_NO;
            window.location.href = this.updateQueryStringParameter(window.location.href, 'stationNo', STATION_NO);
        }
        if (httpArgs['patientDfn'] != undefined) {
            PATIENT_DFN = httpArgs['patientDfn'];
        } else {
            PATIENT_DFN = DEF_PATIENT_DFN;
            window.location.href = this.updateQueryStringParameter(window.location.href, 'patientDfn', PATIENT_DFN);
        }        
        if (httpArgs['userDuz'] != undefined) {
            USER_DUZ = httpArgs['userDuz'];
        } else {
            USER_DUZ = DEF_USER_DUZ;
            window.location.href = this.updateQueryStringParameter(window.location.href, 'userDuz', USER_DUZ);
        }           
        Ext.create('Ext.container.Viewport', {
            layout: 'fit',
            items: [{
                xtype: 'panel',
                layout: 'anchor',
                title: 'AVS Server Monitor',
                items: [{
                    xtype: 'console',  
                    title: '',                    
                    border: false
                }]
            }]
        });
    }
});