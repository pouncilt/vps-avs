Ext.define('ServerMonitor.controller.Controller', {
    extend: 'Ext.app.Controller',
    views: [
        'Console'
    ],
    
    refs: [
        {ref: 'console',  selector: 'console'}           
    ],                         
    
    urls: {
        userInfo:               '/avs/w/s/user/userInfo.action',
        facilities:             '/avs/w/login/stations.action',
        login:                  '/avs/w/login/LoginController',
        logout:                 '/avs/w/login/logout.action'            
    },    
    
    init: function() {
        this.control({
            'console': {
                afterrender: function() {
                    this.onConsoleRendered();
                }
            }, 'console button[action=status]': {
                click: this.checkStatus
            }, 'console button[action=restart]': {
                click: this.restartServer
            }
        });
    },

    onConsoleRendered: function() {
        this.setupSession(this.checkStatus);
    },
    
    setupSession: function(callback) {
        var session = Ext.create('LLVA.Session.Manager', {
            scope: this,
            urls: {
                userInfo:   this.urls['userInfo'],
                facilities: this.urls['facilities'],
                login:      this.urls['login'],
                logout:     this.urls['logout']
            },            
            callback: callback,
            allowDuzLogin: true,
            stationNo: STATION_NO,
            timeout: SIGNON_TIMEOUT
        });
    },    
    
    checkStatus: function() {                  
        var console = this.getConsole();
        console.toggleButtons(false);
        console.updateStatus('Checking AVS Status...');        
        console.updateResults('');
        console.updateLatency('');
        console.updateLastCheck('');
        var start = new Date(); 
        Ext.Ajax.timeout = SIGNON_TIMEOUT;
        Ext.Ajax.request({
            url: 'http://' + window.location.hostname + '/avs/w/login/LoginController',
            params: {
                institution: STATION_NO,
                userDuz: USER_DUZ
            },
            scope: this,
            success: function(response) {
                console.updateStatus('available');                
                console.updateResults(response.responseText);                
                console.updateLatency((new Date().getTime() - start.getTime()) + ' msec');
                console.toggleButtons(true); 
            },            
            failure: function(response, requestOptions) {    
                console.toggleButtons(true);            
                console.updateStatus('unavailable');
                console.updateLatency('No response within ' + SIGNON_TIMEOUT + ' msec or unable to connect.');
                console.updateLastCheck(dateFormat(new Date()));
            }
        });                               
    },
    
    restartServer: function(button) {
        Ext.MessageBox.show({title: 'Server Restart', 
             msg: 'This will restart the AVS server.  Do you wish to continue?',
             buttons: Ext.MessageBox.YESNO,
             modal: true,
             fn: function(btn, text) {
                if (btn === 'yes') {                    
                    var success = false;
                    Ext.Ajax.request({
                        url: 'http://' + window.location.hostname + '/smserver',
                        params: {
                            action: 'restart'            
                        },
                        scope: this,
                        success: function(response) {
                            if (response.responseText == 'success') {
                                Ext.MessageBox.show({title: 'Server Restarted', 
                                        msg: 'The AVS server has been restarted.  It will take several minutes for it to come online.',
                                        buttons: Ext.MessageBox.OK,
                                        modal: false                                 
                                });
                            } else {
                                Ext.MessageBox.show({title: 'Server NOT Restarted', 
                                        msg: 'The AVS server could not be restarted.',
                                        buttons: Ext.MessageBox.OK,
                                        modal: false                                 
                                });                                                    
                            }
                        },            
                        failure: function(response, requestOptions) {    
                            Ext.MessageBox.show({title: 'Server NOT Restarted', 
                                    msg: 'The AVS server could not be restarted.',
                                    buttons: Ext.MessageBox.OK,
                                    modal: false                                 
                            });                        
                        }
                    });                                 
                }
            }
        });    
    }
        
});