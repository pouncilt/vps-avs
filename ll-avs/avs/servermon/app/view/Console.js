Ext.define('ServerMonitor.view.Console' ,{
    extend: 'Ext.form.Panel',
    alias : 'widget.console',

    title : 'Console',
    frame: false,
    width: 500,    
    bodyPadding: 15,
    buttonAlign: 'left',

    fieldDefaults: {
        labelAlign: 'left',
        labelWidth: 100,
        anchor: '100%'
    },
    
    items: [{
        xtype: 'displayfield',
        id: 'hostfield',
        fieldLabel: 'Server',
        value: window.location.hostname.toUpperCase()    
    }, {
        xtype: 'displayfield',
        id: 'statusfield',
        fieldLabel: 'Status',
        value: ''    
    }, {        
        xtype: 'displayfield',
        id: 'resultsfield',
        fieldLabel: 'Results',
        height: 175,
        value: ''    
    }, {     
        xtype: 'displayfield',
        id: 'latencyfield',
        fieldLabel: 'Latency',
        hidden: true,
        value: ''    
    }, {
        xtype: 'displayfield',
        id: 'lastcheckfield',
        fieldLabel: 'Last Check',
        hidden: true,
        value: ''        
    }],
    
    buttons: [{
        text: 'Check Status',
        id: 'checkstatusbtn',
        disabled: true,
        action: 'status'
    }, {
        text: 'Restart Server',
        id: 'restartserverbtn',
        disabled: true,
        action: 'restart'
    }],
    
    updateLastCheck: function(datetime) {
        if (datetime == '') {
            Ext.getCmp('lastcheckfield').hide();
        } else {
            Ext.getCmp('lastcheckfield').show();
        }     
        Ext.getCmp('lastcheckfield').setValue(datetime);
        if (datetime == '') {
            Ext.getCmp('lastcheckfield').hide();
        } else {
            Ext.getCmp('lastcheckfield').show();
        }          
    },    
    
    updateStatus: function(status) {
        if (status == 'unavailable') {
            Ext.getCmp('statusfield').setValue('<span style="color:red;font-weight: bold;">Unavailable</span>');
        } else if (status == 'available') {
            Ext.getCmp('statusfield').setValue('<span style="color:green;font-weight: bold;">Available</span>');
        } else {
            Ext.getCmp('statusfield').setValue('<span style="color:blue;font-weight: bold;">' + status + '</span>');            
        }
    }, 
    
    updateLatency: function(latency) {
        if (latency == '') {
            Ext.getCmp('latencyfield').hide();
        } else {
            Ext.getCmp('latencyfield').show();
        }        
        Ext.getCmp('latencyfield').setValue(latency);
        if (latency == '') {
            Ext.getCmp('latencyfield').hide();
        } else {
            Ext.getCmp('latencyfield').show();
        }          
    },
    
    updateResults: function(results) {
        Ext.getCmp('resultsfield').setValue(results);
        if (results == '') {
            Ext.getCmp('resultsfield').hide();
        } else {
            Ext.getCmp('resultsfield').show();
        }        
    },
        
    toggleButtons: function(enabled) {
        Ext.getCmp('checkstatusbtn').setDisabled(!enabled);
        Ext.getCmp('restartserverbtn').setDisabled(!enabled);        
    }
    
});