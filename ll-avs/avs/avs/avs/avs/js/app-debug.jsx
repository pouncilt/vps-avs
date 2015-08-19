/* Options for jslint: */
/*jslint browser: true, unparam: true, sloppy: true, vars: true, white: true, regexp: true, maxerr: 50, indent: 4 */
/*global Ext, LLVA */

Ext.Loader.setConfig({enabled:true});

Ext.Loader.setPath({
    'Ext': '/ext/4.2/src',
    'Ext.ux': '/ext/4.2/ux',
    'Ext.ux.form.field': '/ext/4.2/ux/form/field',
    'LLVA.Session': '/loginutils/js',
    'LLVA.Utils': '/jsonutils/js'
});

Ext.ns('LLVA.Checkout');
LLVA.Checkout.Wizard = {

    name: 'LLVA.Checkout.Wizard',
    appFolder: 'js/app',
    controllers: ['Wizard'],
    
    serverIndex: 0,

    requires: [
        'LLVA.Utils.MessageBox',
        'LLVA.Session.Manager',
        'Ext.form.field.Display',
        'Ext.window.MessageBox',
        'Ext.selection.CheckboxModel',
        'Ext.ux.form.field.TinyMCE',
        'LLVA.Checkout.Wizard.model.Demographics',
        'LLVA.Checkout.Wizard.model.SearchResult',
        'LLVA.Checkout.Wizard.model.Printer',
        'LLVA.Checkout.Wizard.model.ClinicalService'
    ],
    
    stores: ['Printers'],
    
    params: {
        session: null,
        hasConnectivity: true,
        
        patientDfn: null,
        datetime: null,
        locationIen: null,

        facilitySettings: null,
        demographics: null,
        trendingOffsetDays: null,                

        urls: {
            servers:                'servers.json',
            userInfo:               '/w/s/user/userInfo.action',
            facilities:             '/w/login/stations.action',
            login:                  '/w/login/LoginController',
            logout:                 '/w/login/logout.action',            
            divisions:              '/w/login/divisions.action',
            userDivision:           '/w/login/defaultDivision.action',
            clientStrings:          '/w/s/checkout/getClientStrings.action',
            facilitySettings:       '/w/s/checkout/fetch-settings.action',
            demographics:           '/w/s/checkout/demographics.action',
            sheet:                  '/w/s/checkout/avs.action',
            kramesSearch:           '/w/s/infosheets/kramesSearchContent.action',
            kramesContent:          '/w/s/infosheets/kramesGetContent.action',
            encounters:             '/w/s/patient/patientEncounters.action',
            getRemoteMeds:          '/w/s/checkout/getRemoteMeds.action',
            setRemoteVAMedsHtml:    '/w/s/checkout/setRemoteVaMedsHtml.action',
            setRemoteNonVAMedsHtml: '/w/s/checkout/setRemoteNonVAMedsHtml.action',
            pdf:                    '/w/s/checkout/pdf.action',
            tiu:                    '/w/s/checkout/tiu.action',
            setCustomContent:       '/w/s/checkout/setCustomContent.action',
            setLocked:              '/w/s/checkout/setLocked.action',
            setDefaultPrinter:      '/w/s/checkout/setDefaultPrinter.action',
            getDefaultPrinter:      '/w/s/checkout/getDefaultPrinter.action',
            printers:               '/w/s/checkout/find-printers.action',
            saveComments:           '/w/s/checkout/saveComments.action',
            fetchServices:          '/w/s/checkout/fetch-services.action',
            languages:              '/w/s/checkout/languages.action',
            printPdf:               '/w/s/checkout/printPdf.action',
            admin:                  '/admin/index-cprs.html',
            help:                   '/help/index.html',
            tips:                   '/help/tips.html'
        }
    },

    launch: function() {
        Ext.Ajax.timeout = 120000;
        APP_GLOBAL = this;
        this.loadHttpArgs();
        if (this.httpArgsAreValid() === false) {
            alert('invalid args');
            return;
        }      
        this.getServers();        
    },

    loadHttpArgs: function() {
        var query = window.location.search;
        this.setParam('protocol', window.location.protocol);
        this.setParam('hostname', window.location.hostname);
        this.setParam('port', window.location.port != '' ? window.location.port : 80);
        var path = window.location.pathname.split('/');
        this.setParam('path', '/' + path[1]);
        this.httpArgs = Ext.Object.fromQueryString(query.substring(1));
        this.setParam('hasConnectivity', this.httpArgs.offline ? false : true);
    },

    httpArgsAreValid: function() {
        var requiredArgs = [
            'stationNo',
            'userDuz',
            'patientDfn'
        ];

        var index = 0;
        for (index in requiredArgs) {
            if (requiredArgs.hasOwnProperty(index)) {
                var property = requiredArgs[index];
                if (this.httpArgs[property]) {                    
                    this.setParam(property, this.httpArgs[property]);
                } else {
                    LLVA.Utils.MessageBox.error('Required HTTP argument is missing: "' + property + '"');
                    return false;                
                }
            }
        }
        return true;
    },

    updateQueryStringParameter: function(uri, key, value) {
        var re = new RegExp("([?|&])" + key + "=.*?(&|$)", "i");
        separator = uri.indexOf('?') !== -1 ? "&" : "?";
        if (uri.match(re)) {
            return uri.replace(re, '$1' + key + "=" + value + '$2');
        } else {
            return uri + separator + key + "=" + value;
        }
    },    
    
    getServers: function() {
        Ext.Ajax.request({
            url: this.getParam('urls').servers,
            method: 'GET',
            scope: this,
            success: function(response) {
                var result = Ext.JSON.decode(response.responseText);
                this.setParam('servers', result.servers);
                this.getUserDivision();
            },
            failure: function(response, requestOptions) {
                var obj = {"servers": [this.getParam('hostname')]};                
                this.setParam('servers', obj.servers);
                this.getUserDivision();
            }
        });                
    },    
    
    getUrl: function(serverIndex, name) {
        serverIndex = serverIndex < LLVA.Checkout.Wizard.params['servers'].length ? serverIndex : 0;        
        var server = LLVA.Checkout.Wizard.params['servers'][serverIndex];
        var url = this.getParam('protocol') + '//' + server + ':' + this.getParam('port') + 
               this.getParam('path') + this.getParam('urls')[name];
        //alert(url);
        return url;
    },    
    
    getUserDivision: function() {

        var mask = new Ext.LoadMask(Ext.getBody(), {msg:"Signon Setup..."});
        mask.show();    
        Ext.Ajax.request({
            url: this.getUrl(this.serverIndex, 'userDivision'),
            params: {
                stationNo: this.getParam('stationNo'),
                userDuz: this.getParam('userDuz')
            },
            method: 'GET',
            scope: this,
            success: function(response) {
                mask.hide();
                var result = Ext.JSON.decode(response.responseText);
                this.setParam('stationNo', result.facilityNo);
                this.setupSession(this.loadFacilitySettings);
            },
            failure: function(response, requestOptions) {
                if (response.status != '403') {
                    mask.hide();
                    this.serverIndex++;  
                    this.getUserDivision();
                }
            }
        });                
    },
    
    setupSession: function(callback) {
    
        var session = Ext.create('LLVA.Session.Manager', {
            scope: this,
            urls: {
                userInfo:   this.getUrl(this.serverIndex, 'userInfo'),
                facilities: this.getUrl(this.serverIndex, 'facilities'),
                login:      this.getUrl(this.serverIndex, 'login'),
                logout:     this.getUrl(this.serverIndex, 'logout')
            },            
            callback: callback,
            hasConnectivity: this.getParam('hasConnectivity'),
            allowDuzLogin: true,
            stationNo: this.getParam('stationNo')
        });
        this.setParam('session', session);
    },

    loadFacilitySettings: function() {

        var mask = new Ext.LoadMask(Ext.getBody(), {msg:"Loading settings..."});
        mask.show();    
    
        Ext.Ajax.request({
            url: this.getUrl(this.serverIndex, 'facilitySettings'),
            params: {
                facilityNo: this.getParam('stationNo')
            },
            method: 'GET',
            scope: this,
            success: function(response) {
                mask.hide();
                var result = Ext.JSON.decode(response.responseText);
                if (result.success) {
                    var facilitySettings = Ext.create('LLVA.Checkout.Wizard.model.FacilitySettings', result.root[0]);
                    this.setParam('facilitySettings', facilitySettings); 
                }
                this.loadUserInterface();
            },
            failure: function(response, requestOptions) {
                if (response.status != '403') {
                    mask.hide();
                    this.serverIndex++;  
                    this.loadFacilitySettings();
                }
            }
        });                
    },
    
    loadUserInterface: function() {
    
        var mask = new Ext.LoadMask(Ext.getBody(), {msg:"Loading patient info..."});
        mask.show();    
        
        Ext.Ajax.request({
            url: this.getUrl(this.serverIndex, 'demographics'),
            params: {
                patientDfn: this.getParam('patientDfn')
            },
            method: 'GET',
            scope: this,
            success: function(response) {
                mask.hide();
                var result = Ext.JSON.decode(response.responseText);
                if (result.success) {
                    var demographics = Ext.create('LLVA.Checkout.Wizard.model.Demographics', result.root[0]);
                    this.setParam('demographics', demographics);
                    Ext.widget('wizardviewport');
                }
            },
            failure: function(response, requestOptions) {
                mask.hide();
                this.serverIndex++;
                if (response.status != '403') {
                    mask.hide();
                    this.serverIndex++;  
                    this.loadUserInterface();
                }                
            }
        });
    },

    getParam: function(name) {
        return LLVA.Checkout.Wizard.params[name];
    },

    setParam: function(name, value) {
        LLVA.Checkout.Wizard.params[name] = value;
    }

};
