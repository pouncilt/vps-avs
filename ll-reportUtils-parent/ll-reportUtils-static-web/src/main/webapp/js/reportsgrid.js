Ext.ns('LLVA.ReportsGrid');

LLVA.ReportsGrid.Report = Ext.extend(Ext.util.Observable, 
  
    (function() {
                   
        function formatSearchFields(fields) {
            var searchFields = '';
            if (fields) {
                var i;
                for (i = 0; i < fields.getCount(); i++) {
                    if ((fields.get(i).getXType() === 'combo') ||
                        (fields.get(i).getXType() === 'textfield'))  {
                        var value = fields.get(i).getValue();
                        if (value && value.trim() !== '') {
                            searchFields += fields.get(i).getId() + '=' + fields.get(i).getValue();
                        }
                        if ((i < fields.getCount()-1) && (fields.get(i).getValue() != null) && 
                            (fields.get(i).getValue().trim() !== '')) {
                            searchFields += '|';
                        }
                    }
                }
            }
            return searchFields;
        }
  
        function formatSearchOptions(options) {
            var searchOptions = '';
            if (options) {
                var i;
                for (i = 0; i < options.getCount(); i++) {
                  if (options.get(i).getXType() === 'fieldset') {
                      var fieldSetItems = options.get(i).items;
                      if (!fieldSetItems) {
                          return;
                      }
                      var j;
                      for (j = 0; j < fieldSetItems.getCount(); j++) {
                          if ((fieldSetItems.get(j).getXType() === 'checkbox') ||
                              (fieldSetItems.get(j).getXType() === 'radio')) {
                              searchOptions += fieldSetItems.get(j).getId() + '=' + fieldSetItems.get(j).getValue();
                              if (j < fieldSetItems.getCount()-1) {
                                  searchOptions += '|';
                              }
                          }
                      }
                  }
                }
            }
            return searchOptions;
        }
        
        return {
          
            // public fields
            id: undefined,    
            reportsListUrl: undefined,
            reportUrl: undefined,
            reports: undefined,
            params: undefined,
            startDate: undefined,
            endDate: undefined,
            groupField: undefined,
            pageSize: 100,
            showItemsPerPageField: true,
            itemsPerPageLabel: '# Items/Page:',
            updateProgressLabelCallback: undefined,
            remoteSort: false,
            remoteGroup: false,         
            reportsLabel: 'Report',
            loadBtnLabel: 'Load Report',               
            selectedReportId: undefined,
            autoLoadReport: true,
            defaultReport: undefined,
            autoExpandReports: false,
            renderEl: undefined,
            frame: true,
            border: true,
            tools: undefined,
            title: undefined,
            width: 1000,
            height: 600,
            stripeRows: true,
            columnLines: true,
            loadMask: true,
            trackMouseOver: false,
            disableSelection: true,
            helpContentEl: undefined,
            selectionModel: undefined,
            gridPlugins: undefined,            
            showReportsSelection: true,
            showDateEditors: true,
            showRefresh: true,
            showTopClearGroupingBtn: false,
            showSearch: true,
            showPrint: true,
            showExport: true,
            showTopToolbar: true,
            showBottomToolbar: true,
            showFooterToolbar: true,
            showHeader: true,
            showWelcomeTip: true,
            showClearFilterDataBtn: true,
            showClearSearchDataBtn: true,
            showClearGroupingBtn: true,            
            resizeable: true,
            showGroupSummary: false,
            showRowNumbers: false,
            forceFit: true,
            hideGroupedColumn: false,                    
            showGroupName: true,
            enableNoGroups: true,
            enableGroupingMenu: true,            
            groupTextTpl: '{text} ({[values.rs.length]} {[values.rs.length > 1 ? "Items" : "Item"]})',
            tbar: undefined,
            bbar: undefined,
            fbar: undefined,
            refreshButtonHandler: undefined,
            printButtonHandler: undefined,
            exportButtonHandler: undefined,
            rowClickHandler: undefined,
            rowDoubleClickHandler: undefined,
            rowRightClickHandler: undefined,
            cellClickHandler: undefined,
            cellDoubleClickHandler: undefined,
            onGroupChangeHandler: undefined,
            onSortChangeHandler: undefined,
            onDataLoadedCallback: undefined,
            afterLayoutCallback: undefined,                          
                  
            constructor : function(config) {
                config = config || {};
                LLVA.ReportsGrid.Report.superclass.constructor.call(this);
                Ext.Component.AUTO_ID++;
                if (!config.id) {
                    this.id = 'ReportsGrid-' + Ext.Component.AUTO_ID;
                }
                this.reportId = null;
                this.start = 0;
                this.activePage = 1;
                if (this.params === undefined) {
                    this.params = {};  
                }                
                if (this.title === undefined) {
                    this.title = '&nbsp;';
                }
                Ext.apply(this, config);                    
                
                // Top Toolbar
                if ((this.tbar === undefined) && (this.showTopToolbar === true)) {
                
                    this.searchForm = new LLVA.ReportsGrid.SearchForm({
                        ownerReport: this,
                        refreshButtonHandler: this.refreshButtonHandler
                    });    
                    
                    this.topToolbar = new LLVA.ReportsGrid.TopToolbar({
                        ownerReport: this,
                        searchForm: this.searchForm,
                        reportsLabel: this.reportsLabel,
                        loadBtnLabel: this.loadBtnLabel,
                        showReportsSelection: this.showReportsSelection,
                        showDateEditors: this.showDateEditors,
                        startDate: this.startDate,
                        endDate: this.endDate,
                        showRefresh: this.showRefresh,
                        showClearGroupingBtn: this.showTopClearGroupingBtn,
                        showSearch: this.showSearch,
                        showPrint: this.showPrint,
                        showExport: this.showExport,
                        refreshButtonHandler: this.refreshButtonHandler,
                        printButtonHandler: this.printButtonHandler,
                        exportButtonHandler: this.exportButtonHandler                        
                    });                               
                
                    Ext.Ajax.request({
                        url: this.reportsListUrl,                             
                        scope: this,
                        success: function (result, request) { 
                            this.reports = Ext.util.JSON.decode(result.responseText);
                            var reportsList = [];
                            this.defaultReport = undefined;                            
                            for (var i=0; i < this.reports.length; i++) {       
                                reportsList.push([this.reports[i].id, this.reports[i].name]);
                                if (this.reports[i].defaultReport) {
                                    defaultReport = this.reports[i].id;
                                }
                            }
                            this.topToolbar.setReportsList(reportsList);
                            this.topToolbar.setDefaultReport(this.defaultReport);                            
                        }
                    });       

                } else if ((this.tbar !== undefined) && (this.showTopToolbar === true)) {
                    this.topToolbar = this.tbar;  
                } else {
                    this.topToolbar = undefined;
                }
                // Footer Toolbar
                if ((this.fbar === undefined) && (this.showFooterToolbar === true)) {
                    this.footerToolbar = new LLVA.ReportsGrid.AggregatesToolbar();
                } else if ((this.fbar !== undefined) && (this.showFooterToolbar === true)) {
                    this.footerToolbar = this.fbar;  
                } else {
                    this.footerToolbar = undefined;
                }
                this.reportsDataStore = new LLVA.ReportsGrid.ReportsDataStore({
                    ownerReport: this,
                    topToolbar: this.topToolbar,
                    footerToolbar: this.footerToolbar,
                    remoteSort: this.remoteSort,
                    remoteGroup: this.remoteGroup
                });                
                // Bottom Toolbar
                if ((this.bbar === undefined) && (this.showBottomToolbar === true)) {
                    this.bottomToolbar = new LLVA.ReportsGrid.PagingToolbar({
                         ownerReport: this,
                         dataStore: this.reportsDataStore,
                         showItemsPerPageField: this.showItemsPerPageField,
                         itemsPerPageLabel: this.itemsPerPageLabel,
                         updateProgressLabelCallback: this.updateProgressLabelCallback,
                         showClearFilterDataBtn: this.showClearFilterDataBtn,
                         showClearSearchDataBtn: this.showClearSearchDataBtn,
                         showClearGroupingBtn: this.showClearGroupingBtn
                    });              
                } else if ((this.bbar !== undefined) && (this.showBottomToolbar === true)) {
                    this.bottomToolbar = this.bbar;  
                } else {
                    this.bottomToolbar = undefined;
                }
                this.gridFilters = new Ext.ux.grid.GridFilters();
                
                if (this.resizeable === true) {
                    this.panelResizer = new Ext.ux.PanelResizer({
                        minHeight: 100
                    });
                } else {
                    this.panelResizer = null;
                }
                if (this.showGroupSummary === true) {
                    this.groupSummary = new Ext.ux.grid.GroupSummary();
                } else {
                    this.groupSummary = null;
                }      
                                
                // iterate through plugins and remove any nulls
                if (this.gridPlugins && !(this.gridPlugins.propertyIsEnumerable('length')) && 
                    typeof this.gridPlugins === 'object' && typeof this.gridPlugins.length === 'number') {
                    var i;
                    this.gridPlugins.forEach(function(p, index, arr) {
                        if (p === null) {
                              arr.splice(index, 1);
                        }  
                    });                         
                }

                this.reportsGridPanel = new LLVA.ReportsGrid.GridPanel({
                    ownerReport: this,
                    dataStore: this.reportsDataStore,
                    gridFilters: this.gridFilters,
                    panelResizer: this.panelResizer,
                    groupSummary: this.groupSummary,
                    topToolbar: this.topToolbar,
                    footerToolbar: this.footerToolbar,
                    bottomToolbar: this.bottomToolbar,
                    selectionModel: this.selectionModel,   
                    gridPlugins: this.gridPlugins, 
                    frame: this.frame,    
                    border: this.border,   
                    tools: this.tools,  
                    title: this.title,
                    width: this.width,
                    height: this.height,
                    stripeRows: this.stripeRows,
                    columnLines: this.columnLines,
                    showRowNumbers: this.showRowNumbers,
                    showHeader: this.showHeader,
                    forceFit: this.forceFit,
                    hideGroupedColumn: this.hideGroupedColumn,                    
                    showGroupName: this.showGroupName,
                    enableNoGroups: this.enableNoGroups,
                    enableGroupingMenu: this.enableGroupingMenu,
                    startCollapsed: this.startCollapsed,
                    groupTextTpl: this.groupTextTpl,
                    groupMode: this.groupMode,                    
                    loadMask: this.loadMask,
                    trackMouseOver: this.trackMouseOver,
                    disableSelection: this.disableSelection,
                    helpContentEl: this.helpContentEl,
                    renderTo: this.renderEl
                });      
                if (this.showWelcomeTip) {
                    this.welcomeTip = new LLVA.ReportsGrid.WelcomeTip({
                        target: this.reportsComboBox                                               
                    });       
                    this.welcomeTip.showAt([this.topToolbar.getReportsComboBox().getEl().getX() - 30, 
                                            this.topToolbar.getReportsComboBox().getEl().getY() + 35]);                                                                      
                }                
                if (this.autoLoadReport) {
                    this.getReportData(this.defaultReport); 
                }
            },
            
            getId: function() {
                return this.id;
            },

            loadReport: function(reportId, rptParams) {
                if ((reportId !== undefined) && 
                    (this.topToolbar !== undefined) && 
                    (this.topToolbar instanceof LLVA.ReportsGrid.TopToolbar) &&
                    (this.showReportsSelection)) {
                    this.topToolbar.setSelectedReportId(reportId);                     
                }
                this.getReportData(reportId, rptParams);
            },
            
            filterBy: function(fn) {
                this.reportsDataStore.filterBy(fn);
                this.reportsDataStore.dataChanged();
            },
            
            dataChanged: function() {
                this.reportsDataStore.dataChanged();
            },
            
            getParams: function() {
                return this.params;  
            },
            
            getSelectedReportId: function() {
                return this.selectedReportId;
            },
            
            getReportsGridPanel: function() {
                return this.reportsGridPanel;  
            },
            
            getReportsDataStore: function() {
                return this.reportsDataStore;  
            },
            
            getTopToolbar: function() {
                return this.topToolbar;  
            },
            
            getBottomToolbar: function() {
                return this.bottomToolbar;  
            },
            
            getFooterToolbar: function() {
                return this.footerToolbar;  
            },                                                            
            
            setStart: function(_start) {
                this.start = _start;
            },
            
            getPageSize: function() {
                return this.pageSize;  
            },
            
            setPageSize: function(_pageSize) {
                this.pageSize = _pageSize;
            },    
            
            isPaged: function() {
                return this.showBottomToolbar;  
            },
            
            getGroupField: function() {
                return this.groupField;  
            },       
            
            setGroupField: function(_groupField) {
                this.groupField = _groupField;  
            },
            
            setStartDate: function(_startDate) {
                this.startDate = _startDate;  
            },
            
            getStartDate: function() {
                return this.startDate; 
            },
            
            setEndDate: function(_endDate) {
                this.endDate = _endDate;  
            },
            
            getEndDate: function() {
                return this.endDate; 
            },            
            
            getActivePage: function() {
                return this.activePage;
            },
            
            setActivePage: function(_activePage) {
                this.activePage = _activePage;
            },
            
            getReportUrl: function() {
                return this.reportUrl;
            },

            clearFilterData: function() {
                this.gridFilters.clearFilters();  
            },
            
            getSearchFields: function() {
                return this.searchFields;
            },
            
            getSearchOptions: function() {
                return this.searchOptions;
            },                       
            
            getNumRecords: function() {
                return this.reportsDataStore.getTotalCount(); 
            },
            
            getFields: function() {
                return this.reportsDataStore.fields; 
            },            
            
            getSelectedRecords: function() {
                if (this.selectionModel !== undefined) {
                    return this.selectionModel.getSelections();
                }
            },
            
            getNumSelectedRows: function() {
                if (this.selectionModel !== undefined) {
                    return this.selectionModel.getCount();
                }
            },                         
                        
            clearSelectedRows: function(fast) {
                if (this.selectionModel !== undefined) {
                    return this.selectionModel.clearSelections(fast);
                }
            },      
            
            deselectRow: function(row, preventViewNotify) {
                if (this.selectionModel !== undefined) {
                    return this.selectionModel.deselectRow(row, preventViewNotify);
                }
            },                     
            
            selectAllRows: function() {
                if (this.selectionModel !== undefined) {
                    return this.selectionModel.selectAll();
                }
            },              
            
            selectRows: function(row, keepExisting, preventViewNotify) {
                if (this.selectionModel !== undefined) {
                    return this.selectionModel.selectRows(row, keepExisting, preventViewNotify);
                }
            },      
            
            getRecord: function(index) {
                return this.reportsDataStore.getAt(index);
            },
            
            getRecordById: function(id) {
                return this.reportsDataStore.getById(id);                  
            },         
            
            getIndexOfRecord: function(rec) {
                return this.reportsDataStore.indexOf(rec);
            },
            
            getGridView: function() {
              return this.reportsGridPanel.getView();
            },                       
            
            getColumnModel: function() {
                return this.reportsGridPanel.getColumnModel();
            },
            
            setColumnRenderer: function(col, fn) {
                this.reportsGridPanel.getColumnModel().setRenderer(col, fn);  
            },
            
            onReportsLoaded: function(store, recs) {
                if (this.autoExpandReports) {
                    this.topToolbar.expandReports();
                }
            },
            
            onDataLoaded: function() {
                if (this.searchForm !== undefined) {
                    this.searchForm.initFields();
                }
                if (this.onDataLoadedCallback !== undefined) {
                    this.onDataLoadedCallback(this.reportsGridPanel, this.reportsDataStore);
                }
            },
            
            onRowClicked: function(grid, row, event) {
                if (this.rowClickHandler !== undefined) {
                    this.rowClickHandler(grid, row, event);
                }
            },
            
            onRowDoubleClicked: function(grid, row, event) {
                if (this.rowDoubleClickHandler !== undefined) {
                    this.rowDoubleClickHandler(grid, row, event);
                }
            },            
            
            onRowRightClicked: function(grid, row, event) {
                if (this.rowRightClickHandler !== undefined) {
                    this.rowRightClickHandler(grid, row, event);
                }
            },     
            
            onCellClick: function(grid, row, col, event) {
                if (this.cellClickHandler !== undefined) {
                    this.cellClickHandler(grid, row, col, event);
                }
            },            
            
            onCellDoubleClick: function(grid, row, col, event) {
                if (this.cellDoubleClickHandler !== undefined) {
                    this.cellDoubleClickHandler(grid, row, col, event);
                }
            },    
            
            onGroupChange: function(grid, groupField) {
                if (this.onGroupChangeHandler !== undefined) {
                    this.onGroupChangeHandler(grid, groupField);
                }
            },  
            
            onSortChange: function(grid, sortInfo) {
                if (this.onSortChangeHandler !== undefined) {
                    this.onSortChangeHandler(grid, sortInfo);
                }
            },                       
            
            afterLayout: function(grid, layout) {
                if (this.afterLayoutCallback !== undefined) {
                    this.afterLayoutCallback(grid, layout);  
                }
            },         
            
            groupBy: function(field) {
                this.reportsDataStore.groupBy(field);
            },
            
            collapseAllGroups: function() {
                this.reportsGridPanel.getView().collapseAllGroups(); 
            },
            
            clearGrouping: function() {
                this.reportsDataStore.clearGrouping();
                this.setGroupField(undefined);                
            },
            
            setColHidden: function(colIndex, hide) {
                this.reportsGridPanel.getColumnModel().setHidden(colIndex, hide);
            },
            
            findColumnIndex: function(colName) {
                return this.reportsGridPanel.getColumnModel().findColumnIndex(colName);
            },
            
            setDefaultSort: function(field, dir) {
                this.reportsDataStore.setDefaultSort(field, dir);
            },    
            
            sort: function(field, dir) {
                this.reportsDataStore.sort(field, dir);
            },           

            setSearchForm: function(selectedReportId) {                
                this.clearSearchData(false);
                var i;
                for (i = 0; i < this.reports.length; i++) {
                    if (selectedReportId === this.reports[i].id) {
                        this.topToolbar.setSearchFields(this.reports[i].searchFields);
                        this.topToolbar.setSearchCombos(this.reports[i].searchCombos);                    
                    }
                }                
            },            
            
            clearSearchData: function(promptReload) {
                this.searchFields = '';
                this.searchOptions = '';
                var items = this.searchForm.items;                
                var i, j;
                for (i = 0; i < items.getCount(); i++) {
                    if (items.get(i).getXType() === 'fieldset') {
                        var fieldSetItems = items.get(i).items;
                        for (j = 0; j < fieldSetItems.getCount(); j++) {
                            if ((fieldSetItems.get(j).getXType() === 'checkbox') ||
                                (fieldSetItems.get(j).getXType() === 'radio')) {
                                var defVal1 = false;
                                for (j = 0; j < this.reports.length; j++) {
                                    if (items.get(i).id === this.reports[i].id) {
                                        defVal1 = this.reports[i].value;
                                    }
                                }                                
                                fieldSetItems.get(j).setValue(defVal1);
                            }
                        }
                    } else {
                        var defVal2;
                        for (j = 0; j < this.reports.length; j++) {
                            if (items.get(i).id === this.reports[i].id) {
                                defVal2 = this.reports[i].value;
                            }
                        }
                        if (defVal2 !== undefined) {
                            items.get(i).setValue(defVal2);
                        } else {
                            items.get(i).reset();
                        }
                    }
                }
                var self = this;
                if (promptReload) {  
                    var refreshReportHandler = function(btn, text) {
                        if (btn === 'yes') {
                            self.getReportData();
                        }
                    }; 
                    Ext.MessageBox.confirm('Reload?', 'Report options fields cleared.  Would you like to reload the report?', refreshReportHandler);
                }
            },           
                        
            getReportData: function(reportId, rptParams) {              				
                if ((this.getNumRecords() > 0) || (this.getGroupField() !== undefined)) {
                    this.setGroupField(undefined);    
                    this.reportsDataStore.clearGrouping(); 
                }
                if ((this.searchForm !== undefined) && (this.searchForm.items !== undefined)) {
                    this.searchFields = formatSearchFields(this.searchForm.items);
                    this.searchOptions = formatSearchOptions(this.searchForm.items);
                }
                var reportIdParam = null;
                if (reportId !== undefined) {
                    this.reportId = reportId;
                    reportIdParam = {
                      reportId: reportId
                    };
                }
                var dateParams = null;
                if ((this.topToolbar !== undefined) && (this.topToolbar instanceof LLVA.ReportsGrid.TopToolbar)) {                
                    if (this.showDateEditors) {
                        var startDate = this.topToolbar.getStartDate();
                        var endDate = this.topToolbar.getEndDate();     
                        if (endDate < startDate) { 
                            alert("Start Date must be prior to or same as End Date.");   
                            return;
                        }
                        var startDateStr = startDate.format("mm/dd/yyyy");
                        var endDateStr = endDate.format("mm/dd/yyyy"); 
                        dateParams = {
                            startDate: startDateStr,
                            endDate: endDateStr  
                        };
                    }
                    if ((this.showReportsSelection) && ((reportId === undefined) || (reportId === null))) {
                        this.reportId = this.topToolbar.getSelectedReportId(); 
                        reportIdParam = {
                            reportId: this.reportId
                        };
                    }                    
                }     				
                // base params     
                var params = {format: "json", start: this.start, limit: this.pageSize, groupField: this.groupField, 
                              searchFields: this.searchFields, searchOptions: this.searchOptions};            
                var z;
                // add report id param
                if (reportIdParam !== null) { 
                    for (z in reportIdParam) {
                        if (reportIdParam.hasOwnProperty(z)) {
                            params[z] = reportIdParam[z];
                        }
                    }                
                }   
                // add date params
                if (dateParams !== undefined) {
                    for (z in dateParams) {
                        if (dateParams.hasOwnProperty(z)) {
                            params[z] = dateParams[z];
                        }
                    }   
                }       
                // add user-supplied params (global and local)
                if (this.params !== undefined) {
                    for (z in this.params) {
                        if (this.params.hasOwnProperty(z)) {
                            params[z] = this.params[z];
                        }
                    }   
                }                                      
                if (rptParams !== undefined) {
                    for (z in rptParams) {
                        if (rptParams.hasOwnProperty(z)) {
                            params[z] = rptParams[z];
                        }
                    }   
                }     		                  
                if (this.reportsDataStore.multiSortInfo.sorters !== undefined) {
                    this.reportsDataStore.multiSortInfo.sorters.clear();
                }                
                if (this.reportsDataStore.sorters !== undefined) {
                    this.reportsDataStore.sorters.clear();
                }
                this.reportsDataStore.loadData({params: params});
            },
            
            printReport: function(allPages, rptParams, printUrl) {
                if ((this.searchForm !== undefined) && (this.searchForm.items !== undefined)) {
                    this.searchFields = formatSearchFields(this.searchForm.items);
                    this.searchOptions = formatSearchOptions(this.searchForm.items);
                }
                var startDateStr = '';
                var endDateStr = '';
                if ((this.topToolbar !== undefined) && (this.topToolbar instanceof LLVA.ReportsGrid.TopToolbar)) {                
                    var startDate = this.topToolbar.getStartDate();
                    var endDate = this.topToolbar.getEndDate();     
                    if (endDate < startDate) { 
                        alert("Start Date must be prior to or same as End Date.");   
                        return;
                    }
                    startDateStr = startDate.format("mm/dd/yyyy");
                    endDateStr = endDate.format("mm/dd/yyyy"); 
                    if (this.showReportsSelection) {
                        this.reportId = this.topToolbar.getSelectedReportId(); 
                    }
                } 
                var url = "loadproxy.html?msg=Generating Printable Report&closeOnLoad=0&dataUrl=";          
                if (printUrl !== undefined) {
                    url += printUrl;
                } else {
                    url += this.reportUrl;                                          
                }
                url += "&searchFields=" + this.searchFields + "&searchOptions=" + this.searchOptions + "&format=pdf";
                // add report id and date fields
                if (this.reportId !== null) {                  
                    url += "&reportId=" + this.reportId;
                }
                if ((startDateStr !== '') && (endDateStr !== '')) {
                    url += "&startDate=" + startDateStr + "&endDate=" + endDateStr;
                }
                var z; 
                // add user-supplied params (global and local)
                if (this.params !== undefined) {                                                                           
                    for (z in this.params) {
                        url += '&' + z + '=' + this.params[z];  
                    }
                }
                if (rptParams !== undefined) {                
                    for (z in rptParams) {
                        url += '&' + z + '=' + rptParams[z];  
                    }
                }                
                if (!allPages) {
                    url += "&start=" + this.start + "&limit=" + this.pageSize;
                }
                if (this.groupField) {
                    url += "&groupField=" + this.groupField;
                }               
                window.open(url,"pdfReport","menubar=1,resizable=1,width=800,height=600");
            },
            
            exportReport: function(format, allPages, rptParams, exportUrl) {
                if ((this.searchForm !== undefined) && (this.searchForm.items !== undefined)) {
                    this.searchFields = formatSearchFields(this.searchForm.items);
                    this.searchOptions = formatSearchOptions(this.searchForm.items);
                }
                var startDateStr = '';
                var endDateStr = '';
                if ((this.topToolbar !== undefined) && (this.topToolbar instanceof LLVA.ReportsGrid.TopToolbar)) {                
                    var startDate = this.topToolbar.getStartDate();
                    var endDate = this.topToolbar.getEndDate();     
                    if (endDate < startDate) { 
                        alert("Start Date must be prior to or same as End Date.");   
                        return;
                    }
                    startDateStr = startDate.format("mm/dd/yyyy");
                    endDateStr = endDate.format("mm/dd/yyyy"); 
                    if (this.showReportsSelection) {
                        this.reportId = this.topToolbar.getSelectedReportId(); 
                    }
                }                 
                var url = "loadproxy.html?msg=Exporting Report Data&closeOnLoad=1&dataUrl=";          
                if (exportUrl !== undefined) {
                    url += exportUrl;
                } else {
                    url += this.reportUrl;  
                }
                url += "&searchFields=" + this.searchFields + "&searchOptions=" + this.searchOptions + "&format=" + format;
                // add report id and date fields
                if (this.reportId !== null) {                  
                    url += "&reportId=" + this.reportId;
                }
                if ((startDateStr !== '') && (endDateStr !== '')) {
                    url += "&startDate=" + startDateStr + "&endDate=" + endDateStr;
                }                                          
                var z;                 
                // add user-supplied params (global and local)
                if (this.params !== undefined) {                
                    for (z in this.params) {
                        url += '&' + z + '=' + this.params[z];  
                    }
                }  
                if (rptParams !== undefined) {                                                                           
                    for (z in rptParams) {
                        url += '&' + z + '=' + rptParams[z];  
                    }
                }                                                      
                if (!allPages) {
                    url += "&start=" + this.start + "&limit=" + this.pageSize;
                }
                window.open(url,"textReport","menubar=1,resizable=0,width=350,height=150");
            }            

        };
    }())
          
);     


/* Reports ComboBox */
LLVA.ReportsGrid.ReportsComboBox = Ext.extend(Ext.form.ComboBox,
     
    (function() {
  
        return {
        
            // public members    
            ownerReport: undefined,
          
            initComponent: function() {
                              
                var config = {
                    id: 'ReportType-' + Ext.Component.AUTO_ID,
                    width: 175,
                    store: new Ext.data.ArrayStore({       
                        autoDestroy: true,
                        storeId: 'reportsStore',
                        idIndex: 0,
                        fields: ['id','name'],
                        listeners:{
                            scope: this,
                            'load': function(store, recs, opts) {
                                this.ownerReport.onReportsLoaded(store, recs);
                            }
                        }                        
                    }),
                    displayField: 'name',
                    valueField: 'id',
                    selectOnFocus: true,
                    mode: 'local',
                    typeAhead: false,
                    editable: false,
                    triggerAction: 'all',
                    emptyText: 'Select...',
                    lazyInit:false,
                    listeners:{
                        scope: this,
                        'select': function(combo, rec, index) {
                            this.ownerReport.selectedReportId = rec.id;
                            this.ownerReport.setSearchForm(rec.id);
                        }
                    }
                };
                Ext.apply(this, Ext.apply(this.initialConfig, config));
                LLVA.ReportsGrid.ReportsComboBox.superclass.initComponent.apply(this, arguments);
            },
            
            setReportsList : function(reports) {
                this.getStore().loadData(reports);
            }, 
            
            setDefaultReport : function(defaultReport) {
                if (this.defaultReport != undefined) {
                    this.setValue(this.defaultReport);
                }            
            }            
        };
    }())        
);     


LLVA.ReportsGrid.WelcomeTip = Ext.extend(Ext.ToolTip,

    (function() {
    
        return {
        
            // public members
            target: undefined,
            
            initComponent: function() {
            
                var config = {
                    id: 'WelcomeTip-' + Ext.Component.AUTO_ID,
                    target: this.target,
                    anchor: 'bottom',
                    width: 250,
                    dismissDelay: 7000,
                    closable: true,
                    autoHide: true,
                    html: '<b>Welcome!&nbsp;&nbsp;Select a report from the menu above and click the Load Report button.</b>' 
                };
                Ext.apply(this, Ext.apply(this.initialConfig, config));
                LLVA.ReportsGrid.WelcomeTip.superclass.initComponent.apply(this, arguments);
            }
        };
    }())                    
);     

/* Date Field */
LLVA.ReportsGrid.DateField = Ext.extend(Ext.form.DateField,
      
    (function() {
          
        return {
      
            // public members    
            hidden: false,  
  
            initComponent: function() {
                     
                var config = {
                    id: 'DateField-' + Ext.Component.AUTO_ID,
                    width: 100,
                    allowBlank :false,
                    value: new Date(),
                    hidden: this.hidden
                };
                Ext.apply(this, Ext.apply(this.initialConfig, config));
                LLVA.ReportsGrid.DateField.superclass.initComponent.apply(this, arguments);
            }
        };
    }())                    
);     


/* Search Form */
LLVA.ReportsGrid.SearchForm = Ext.extend(Ext.form.FormPanel,
    
    (function() {
          
        return {
      
            // public members    
            ownerReport: undefined,
            refreshButtonHandler: undefined,
          
            initComponent: function() {
                                
                var config = {
                    id: 'SearchForm-' + Ext.Component.AUTO_ID,
                    labelWidth: 110, 
                    frame: true,
                    title: 'Report Options',
                    bodyStyle: 'padding:5px 5px 0',
                    width: 300,
                    defaults: {width: 160},
                    defaultType: 'textfield',
                    buttons: [{
                        text: 'Load Report',
                        scope: this,
                        handler: function() {
                            this.ownerCt.ownerCt.ownerCt.hideSearchForm();
                            if (this.refreshButtonHandler !== undefined) {
                                this.refreshButtonHandler(this.ownerReport.getTopToolbar().getSelectedReportId());
                            } else {
                                this.ownerReport.setStart(0);
                                this.ownerReport.setActivePage(1);
                                this.ownerReport.getReportData();
                            }
                        }
                    },{
                        text: 'Reset',
                        scope: this,
                        handler: function() {
                            this.ownerReport.clearSearchData(false);
                        }
                    },{
                        text: 'Close',
                        scope: this,
                        handler: function() { 
                            this.ownerCt.ownerCt.ownerCt.hideSearchForm();
                        }
                    }]
                };
                Ext.apply(this, Ext.apply(this.initialConfig, config));
                LLVA.ReportsGrid.SearchForm.superclass.initComponent.apply(this, arguments);
                
            },
            initFields: function() {
                var s1 = this.ownerReport.getSearchFields();
                var s2 = this.ownerReport.getSearchOptions();
                if (!s1 && !s2) {
                    return;
                }
                var searchFields = s1.split('|');
                var i;
                for (i = 0; i < searchFields.length; i++) {
                    var fieldValue = searchFields[i].split('=')[1];
                    if (fieldValue && fieldValue.trim() !== '') {
                        var fieldId = searchFields[i].split('=')[0];
                        var field = this.findById(fieldId);
                        if (field) {
                            field.setValue(fieldValue);
                        }

                    }
                }
                var searchOptions = s2.split('|');
                for (i = 0; i < searchOptions.length; i++) {
                    var fieldId = searchOptions[i].split('=')[0];
                    var fieldValue = searchOptions[i].split('=')[1];
                    var field = this.findById(fieldId);
                    if (field) {
                        field.setValue(fieldValue);
                    }
                }                
            }

        };
    }())        
); 


/* Search Data Store */
LLVA.ReportsGrid.SearchDataStore = Ext.extend(Ext.data.Store,
    
    (function() {
      
        return {
          
            // public fields    
            reportUrl: undefined,
            action: undefined,
            params: undefined,
          
            constructor: function(config) {
              
                Ext.apply(this, config);
              
                Ext.apply(this, {

                    baseParams: {
                        action: this.action
                    },
                    proxy: new Ext.data.HttpProxy({
                        api: {
                            read: this.reportUrl
                        },
                        method: 'POST'
                    }),
                    reader: new Ext.data.JsonReader({
                        idProperty: 'id',
                        root: 'root',
                        fields: [
                            {name: 'id'},
                            {name: 'name'}
                        ]
                    })       
                });
                var z;                  
                for (z in this.params) {
                    this.setBaseParam(z, this.params[z]);  
                }
                
                LLVA.ReportsGrid.SearchDataStore.superclass.constructor.apply(this, arguments);
            }        
        };
        
    }())
    
);  


function partial(func /*, 0..n args */) {
    var args = Array.prototype.slice.call(arguments).splice(1);
    return function() {
        var allArguments = args.concat(Array.prototype.slice.call(arguments));
        return func.apply(this, allArguments);
    };
}


/* Print/Export Button */
LLVA.ReportsGrid.PrintExportButton = Ext.extend(Ext.Button,
      
    (function() {
          
        return {
      
            // public members 
            ownerReport: undefined,   
            hidden: false,  
            type: undefined,
            showMenu: true,
            printButtonHandler: undefined,
            exportButtonHandler: undefined,
  
            doPrint: function() {
              this.ownerReport.printReport('all');
            },         
            
            doExport: function() {
              this.ownerReport.exportReport('csv', 'all');
            },
            
            initComponent: function() {
                     
                var id;
                var icon;
                var text;
                var menu;                
                var handler;
                if (this.type === 'print') {
                    id = 'Print-' + Ext.Component.AUTO_ID;
                    icon = '/reportutils/images/print.gif';
                    text = '&nbsp;Print';
                    if (this.showMenu) {
                        menu = {xtype: 'menu',
                                plain: true,
                                hidden: !this.showMenu,                                
                                items: [{
                                    icon: '/reportutils/images/docsingle.gif',
                                    text: 'Current Page',                                    
                                    scope: this,
                                    handler: function() {
                                        if (this.printButtonHandler !== undefined) {
                                            this.printButtonHandler();
                                        } else {
                                            this.ownerReport.printReport();
                                        }
                                    }
                                },{
                                    icon: '/reportutils/images/docstack.gif',
                                    text: 'All Pages',
                                    scope: this,
                                    handler: function() {
                                        if (this.printButtonHandler !== undefined) {
                                            this.printButtonHandler('all');
                                        } else {
                                            this.ownerReport.printReport('all');
                                        }
                                    }
                                }]};            
                    } else {
                        handler = this.doPrint;  
                    }        
                } else {
                    id = 'Export-' + Ext.Component.AUTO_ID;
                    icon = '/reportutils/images/export.png';
                    text = '&nbsp;Export';
                    if (this.showMenu) {
                        menu = {xtype: 'menu',
                                plain: true,
                                hidden: !this.showMenu,                                
                                items: [{
                                    icon: '/reportutils/images/docsingle.gif',
                                    text: 'Current Page',                                    
                                    scope: this,
                                    handler: function() {
                                        if (this.exportButtonHandler !== undefined) {
                                            this.exportButtonHandler('all');
                                        } else {                                      
                                            this.ownerReport.exportReport('csv');
                                        }                                        
                                    }
                                },{
                                    icon: '/reportutils/images/docstack.gif',
                                    text: 'All Pages',
                                    scope: this,
                                    handler: function() {
                                        if (this.exportButtonHandler !== undefined) {
                                            this.exportButtonHandler('all');
                                        } else {                                      
                                            this.ownerReport.exportReport('csv','all');
                                        }
                                    }
                                }]};     
                    } else {
                        handler = this.doExport;  
                    }                                                            
                }
                var config = {
                    id: id,
                    text: text,
                    icon: icon,
                    width: 75,
                    hidden: this.hidden,
                    menu: menu,
                    handler: handler
                };
                Ext.apply(this, Ext.apply(this.initialConfig, config));
                LLVA.ReportsGrid.PrintExportButton.superclass.initComponent.apply(this, arguments);
            }
        };
    }())                    
);    
                        

/* Accessory Separator/Spacer Methods */
  
var addSeparator = function(visible) {
    if (visible) {
        return '-';  
    } else {
        return new Ext.form.Label({html: "&nbsp;", hidden: true});
    }  
};

var addSpacer = function(visible) {
    return new Ext.form.Label({html: "&nbsp;", hidden: !visible});
};


/* Top Toolbar */
LLVA.ReportsGrid.TopToolbar = Ext.extend(Ext.Toolbar,
   
    (function() {
                         
        return {
          
            // public fields
            ownerReport: undefined,            
            searchForm: undefined,     
            reportsLabel: undefined,
            loadBtnLabel: undefined,
            showReportsSelection: undefined,      
            showDateEditors: undefined,
            startDate: undefined,
            endDate: undefined,
            showRefresh: undefined,
            showClearGroupingBtn: undefined,
            showSearch: undefined,
            showPrint: undefined,
            showExport: undefined,
            refreshButtonHandler: undefined,
            printButtonHandler: undefined,
            exportButtonHandler: undefined,
            
            initComponent: function() {
                this.ignoreSearchMenuHide = false;
                this.reportsComboBox = new LLVA.ReportsGrid.ReportsComboBox({
                    ownerReport: this.ownerReport,
                    hidden: !this.showReportsSelection
                });              
                this.startDateField = new LLVA.ReportsGrid.DateField({hidden: !this.showDateEditors, value: this.startDate}); 
                this.endDateField = new LLVA.ReportsGrid.DateField({hidden: !this.showDateEditors, value: this.endDate});      
                this.printButton = new LLVA.ReportsGrid.PrintExportButton({
                    ownerReport: this.ownerReport,
                    hidden: !this.showPrint,
                    type: 'print',
                    showMenu: this.ownerReport.isPaged(),  
                    printButtonHandler: this.printButtonHandler
                });
                this.exportButton = new LLVA.ReportsGrid.PrintExportButton({
                    ownerReport: this.ownerReport,
                    hidden: !this.showExport,
                    type: 'export',
                    showMenu: this.ownerReport.isPaged(),
                    exportButtonHandler: this.exportButtonHandler                    
                });    
                var config = {
                    id: 'ReportsGridTopToolbar-' + Ext.Component.AUTO_ID,
                    items: [
                        new Ext.form.Label( {
                            html: "&nbsp;" + this.reportsLabel + ":&nbsp;&nbsp;",
                            hidden: !this.showReportsSelection
                        }),                         
                        this.reportsComboBox,
                        addSpacer(this.showDateEditors),           
                        addSeparator(this.showDateEditors),           
                        new Ext.form.Label( {
                            html: "&nbsp;Start Date:&nbsp;&nbsp;",
                            hidden: !this.showDateEditors
                        }),     
                        this.startDateField,
                        addSpacer(this.showDateEditors),        
                        addSeparator(this.showDateEditors),
                        new Ext.form.Label( {
                            html: "&nbsp;End Date:&nbsp;&nbsp;",
                            hidden: !this.showDateEditors
                        }),  
                        this.endDateField,
                        addSpacer(this.showDateEditors),       
                        addSeparator(this.showDateEditors),
                        addSpacer(this.showRefresh),                       
                        new Ext.Button( {
                            text:'&nbsp;' + this.loadBtnLabel,
                            id: 'Refresh-' + Ext.Component.AUTO_ID,
                            width: 100,
                            icon: '/ext/3/resources/images/default/grid/refresh.gif',
                            scope: this,
                            hidden: !this.showRefresh,
                            handler: function() { 
                                if (this.refreshButtonHandler !== undefined) {
                                    this.refreshButtonHandler(this.ownerReport.getTopToolbar().getSelectedReportId());
                                } else {
                                    this.ownerReport.setStart(0);
                                    this.ownerReport.setActivePage(1);
                                    this.ownerReport.getReportData();
                                }
                           }}  
                        ), 
                        addSpacer(this.showClearGroupingBtn),                           
                        addSeparator(this.showClearGroupingBtn),
                        addSpacer(this.showClearGroupingBtn),                        
                        new Ext.Button( {
                            text:'&nbsp;Clear Grouping&nbsp;',
                            id: 'ClearGrouping-' + Ext.Component.AUTO_ID,
                            icon: '/reportutils/images/control_rewind.png',
                            scope: this,
                            hidden: !this.showClearGroupingBtn,
                            handler: function(){ 
                                this.ownerReport.clearGrouping();
                           }}  
                        ),                                  
                        addSpacer(this.showSearch),                           
                        addSeparator(this.showSearch),
                        addSpacer(this.showSearch),
                        new Ext.Button({
                            text: '&nbsp;Options',
                            id: 'Search-' + Ext.Component.AUTO_ID,
                            width: 75,
                            icon: '/reportutils/images/search.gif',
                            scope: this,
                            hidden: !this.showSearch || (this.searchForm === undefined),
                            menu: {
                                id: 'SearchFormMenu-' + Ext.Component.AUTO_ID,
                                xtype: 'menu',
                                plain: true,
                                items: [
                                    this.searchForm
                                ],
                                listeners: {
                                    beforeshow: function() {
                                        this.ownerCt.ownerCt.setIgnoreSearchMenuHide(true);
                                        this.ownerCt.ownerCt.initSearchFields();
                                    },
                                    beforeHide: function() {
                                        if (this.ownerCt !== undefined) {
                                            return !this.ownerCt.ownerCt.getIgnoreSearchMenuHide();
                                        } else {
                                          return false;
                                        }
                                    }
                                }
                            }
                        }),
                        '->',    
                        this.printButton,                        
                        addSpacer(this.showExport && this.showPrint),                           
                        addSeparator(this.showExport && this.showPrint),
                        addSpacer(this.showExport && this.showPrint),     
                        this.exportButton,                        
                        new Ext.form.Label( {
                            html: "&nbsp;",
                            hidden: !this.showExport
                        })                         
                    ]
                };
                this.startDateField.setValue(this.startDate);
                this.endDateField.setValue(this.endDate);                
                Ext.apply(this, Ext.apply(this.initialConfig, config));
                LLVA.ReportsGrid.TopToolbar.superclass.initComponent.apply(this, arguments);               
            }, 
    
            getSelectedReportId : function() {
                return this.reportsComboBox.getValue();
            },
            
            setSelectedReportId : function(reportId) {
                this.reportsComboBox.setValue(reportId);
            },
            
            setReportsList : function(reportsList) {
                this.reportsComboBox.setReportsList(reportsList);
            },
            
            setDefaultReport : function(defaultReport) {
                this.reportsComboBox.setDefaultReport(defaultReport);
            },
            
            getReportsComboBox : function() {
                return this.reportsComboBox;
            },
            
            getStartDate : function() {
                return this.startDateField.getValue();
            },
            
            getEndDate : function() {
                return this.endDateField.getValue();
            },    
            
            setIgnoreSearchMenuHide : function(value) {                
                this.ignoreSearchMenuHide = value;
            },
            
            getIgnoreSearchMenuHide : function() {
                return this.ignoreSearchMenuHide;  
            },
            
            hideSearchForm : function() {
                this.ignoreSearchMenuHide = false;
                this.searchForm.ownerCt.hide(true);
            },
            
            initSearchFields : function() {
                this.searchForm.initFields();
            },
            
            setSearchFields : function(searchFields) {
                this.searchForm.removeAll();
                if (!searchFields) {
                    return;
                }
                var i;
                for (i = 0; i < searchFields.length; i++) {
                    if (searchFields[i].action) {
                        var cb = new Ext.form.ComboBox({
                            id: searchFields[i].id,
                            store: new LLVA.ReportsGrid.SearchDataStore({
                                reportUrl: this.ownerReport.getReportUrl(),
                                action: searchFields[i].action,
                                params: this.ownerReport.getParams()
                            }),
                            triggerAction: 'all',
                            editable: true,
                            selectOnFocus: true,
                            allowBlank: true,
                            fieldLabel: searchFields[i].fieldLabel,
                            displayField: 'name',
                            typeAhead: false,
                            loadingText: 'Searching...',
                            hideTrigger:true,
                            minChars: 2,
                            value: searchFields[i].value                            
                        });
                        this.searchForm.add(cb);
                    } else {
                        this.searchForm.add(searchFields[i]);
                    }
                }
            },
            
            setSearchCombos : function(searchCombos) {
                var i, j;                 
                for (i = 0; i < searchCombos.length; i++) {            
                    var comboData = [];
                    for (j = 0; j < searchCombos[i].options.length; j++) {
                        comboData.push({id: searchCombos[i].options[j].id, name: searchCombos[i].options[j].name});
                    }
                    var cb = new Ext.form.ComboBox({
                        id: searchCombos[i].id,
                        store: new Ext.data.JsonStore({
                            fields: [
                                'id',
                                'name'
                            ],
                            data: comboData
                        }),
                        mode: 'local',
                        triggerAction: 'all',
                        editable: false,
                        selectOnFocus: true,
                        submitValue: true,
                        allowBlank: true,
                        fieldLabel: searchCombos[i].fieldLabel,
                        valueField: 'id',
                        displayField: 'name',
                        hiddenName: 'id',
                        emptyText: 'Select...',
                        typeAhead: false,
                        value: searchCombos[i].value                        
                    });
                    this.searchForm.add(cb);
                }
            },

            setSearchOptions : function(searchOptions) {
                var fieldSet;
                if ((searchOptions) && (searchOptions.length > 0)) {
                    fieldSet = new Ext.form.FieldSet({
                        title: 'Options',
                        collapsible: false,
                        autoHeight: true,
                        autoWidth: true,
                        hideLabel: true
                    });
                    this.searchForm.add(fieldSet);
                    var i;
                    for (i = 0; i < searchOptions.length; i++) {
                        fieldSet.add(searchOptions[i]);
                    }
                }
            },
            
            expandReports : function() {
                this.reportsComboBox.focus();
                this.reportsComboBox.expand();
            }            
        };
    }())
          
);     


/* Paging Toolbar */
LLVA.ReportsGrid.PagingToolbar = Ext.extend(Ext.PagingToolbar,
  
    (function() {
                    
        // private members        
        var handleProgressBarClick = function(e) {
            var box = this.progressBar.getBox(),
                xy = e.getXY(),
                position = xy[0] - box.x,
                pages = Math.ceil(this.store.getTotalCount() / this.pageSize),
                newPage = Math.ceil(position / (this.progressBar.width / pages));
            this.changePage(newPage);
        };
        
        return {
          
            // public fields  
            ownerReport: undefined,            
            dataStore: undefined,
            updateProgressLabelCallback: undefined,
            showItemsPerPageField: true,
            itemsPerPageLabel: undefined,            
            showClearFilterDataBtn: true,
            showClearSearchDataBtn: true,
            showClearGroupingBtn: true,
            
            initComponent: function() {
                          
                var config = {
                    id: 'ReportsGridPagingToolbar-' + Ext.Component.AUTO_ID,
                    pageSize: this.ownerReport.getPageSize(),
                    store: this.dataStore,
                    displayInfo: false
                };
                Ext.apply(this, Ext.apply(this.initialConfig, config));
                LLVA.ReportsGrid.PagingToolbar.superclass.initComponent.apply(this, arguments);
                this.progressBar = this.add(new Ext.ProgressBar({
                    text: 'Loading...',                    
                    width: 200,
                    scope: this,
                    animate: {
                        duration: 1,
                        easing: 'bounceOut'
                    },
                    listeners: { 
                        render: function(pb) {
                            pb.mon(pb.getEl().applyStyles('cursor:pointer'), 'click', handleProgressBarClick, this);
                        }
                    }
                })); 
                if (this.showItemsPerPageField) {
                    this.addSpacer();
                    this.addSeparator();
                    this.addSpacer();
                    this.addText(this.itemsPerPageLabel);
                    this.addSpacer();
                    this.add(new Ext.form.NumberField({
                        allowBlank: false,
                        allowDecimals: false,
                        allowNegative: false,
                        minValue: 1,
                        width: 36,
                        blankText: '',
                        scope: this,
                        selectOnFocus: true,
                        enableKeyEvents: true,                   
                        value: this.ownerReport.getPageSize(),
                        listeners: {
                            keyup: function(field, e) {
                                this.ownerCt.ownerReport.setPageSize(this.getValue()); 
                                this.pageSize = this.getValue(); 
                            },
                            specialkey: function(field, e) {
                                if (e.getKey() === e.ENTER) {
                                    this.ownerCt.ownerReport.setPageSize(this.getValue());
                                    this.pageSize = this.getValue();
                                    this.ownerCt.ownerReport.getReportData();
                                }
                            }
                        }
                        
                    }));
                }
                this.add('->');
                this.addButton({
                    text: '&nbsp;Clear Filter Data&nbsp;',
                    scope: this,
                    hidden: !this.showClearFilterDataBtn,
                    handler: function() {
                        this.ownerReport.clearFilterData();
                    }
                });                
                if (this.showClearFilterDataBtn) {
                    this.addSpacer();
                    this.addSeparator();
                    this.addSpacer();                
                }
                this.addButton({
                    text: '&nbsp;Clear Search Data&nbsp;',
                    scope: this,
                    hidden: !this.showClearSearchDataBtn,
                    handler: function() {
                        this.ownerReport.clearSearchData(true);
                    }
                });                
                if (this.showClearSearchDataBtn) {
                    this.addSpacer();
                    this.addSeparator();
                    this.addSpacer();
                }
                this.addButton({
                    text: '&nbsp;Clear Grouping&nbsp;',
                    scope: this,
                    icon: '/reportutils/images/control_rewind.png',
                    hidden: !this.showClearGroupingBtn,
                    handler: function() {
                        this.dataStore.clearGrouping();
                    }
                });
                this.addSpacer();
                this.refresh.hideParent = true;
                this.refresh.hide();
                this.on('beforechange', function(tb, params) {    
                    this.ownerReport.setStart(params.start);
                    this.ownerReport.getReportData();
                    return false; 
                });      
                this.on('change', function(tb, pageData) {
                    this.ownerReport.setActivePage(pageData.activePage);
                    var pct = pageData.activePage / pageData.pages; 
                    if (this.updateProgressLabelCallback === undefined) {
                        if (pageData.total > 0) {                            
                            var msg = String.format('Displaying items {0} - {1} of {2}',
                                                    (((pageData.activePage-1) * tb.pageSize) + 1),
                                                    (((pageData.activePage-1) * tb.pageSize) + this.dataStore.getCount()),
                                                    pageData.total);
                            this.progressBar.updateProgress(pct, msg);
                        } else {
                            this.progressBar.updateProgress(0, 'No items to display');
                        }
                    } else {
                        var val = 0;
                        if (pageData.total > 0) {
                            val = pct;
                        }
                        var msg = this.updateProgressLabelCallback(pageData, tb, this.dataStore);
                        this.progressBar.updateProgress(val, msg);
                    }
                });
            }
        };
    }())

);        


/* Report Data Store */
LLVA.ReportsGrid.ReportsDataStore = Ext.extend(Ext.data.GroupingStore,
    
    (function() {
                  
        return {
          
            // public fields  
            ownerReport: undefined,      
            topToobar: undefined,
            footerToolbar: undefined,
            remoteSort: false,
            remoteGroup: false,
          
            constructor: function(config) {                                
               
                Ext.apply(this, config);
                              
                Ext.apply(this, {
                    storeId: 'ReportsDataStore-' + Ext.Component.AUTO_ID, 
                    proxy: new Ext.data.HttpProxy({
                        api: {
                            read: this.ownerReport.getReportUrl()
                        },
                        method: 'POST'
                    }),
                    reader: new Ext.data.JsonReader({
                        fields:[]
                    }),
                    remoteSort: this.remoteSort,
                    remoteGroup: this.remoteGroup,
                    listeners: {
                        load: function (st, recs, opts) {
                            /*
                            if ((this.topToolbar !== undefined) && (this.topToolbar instanceof LLVA.ReportsGrid.TopToolbar)) {       
                                this.topToolbar.setSearchFields(this.reader.jsonData.searchFields);
                                this.topToolbar.setSearchCombos(this.reader.jsonData.searchCombos);
                            }
                            */
                            if ((this.footerToolbar !== undefined) && (this.footerToolbar instanceof LLVA.ReportsGrid.AggregatesToolbar)) {
                                this.footerToolbar.setAggregates(this.reader.jsonData.aggregates);
                            }
                            if (this.reader.jsonData.metaData.groupField) {
                                this.groupBy(this.reader.jsonData.metaData.groupField); 
                            }   
                            if (this.reader.jsonData.metaData.sortInfo.field) {
                                this.sort(this.reader.jsonData.metaData.sortInfo.field, 
                                          this.reader.jsonData.metaData.sortInfo.direction); 
                            }                                      
                            if (this.reader.jsonData.metaData.startCollapsed) {
                                this.ownerReport.collapseAllGroups();
                            } 
                            this.dataChanged();   
                            this.ownerReport.onDataLoaded();
                        },
                        groupchange: function(store, groupField) {
                            this.ownerReport.setGroupField(groupField); 
                        },
                        exception: function() {
                            this.ownerReport.onDataLoaded();  
                        }
                    }
                });
                
                LLVA.ReportsGrid.ReportsDataStore.superclass.constructor.apply(this, arguments);
            },
            
            loadData : function(params) {
                this.load(params);  
            },
            
            dataChanged : function() {
                if (this.reader.jsonData.metaData.hideGroupedColumn) {
                    var colIndex = this.ownerReport.findColumnIndex(this.reader.jsonData.metaData.groupField);
                    if (colIndex >= 0) {
                        this.ownerReport.setColHidden(colIndex, true);
                    }
                }       
            }
        };
    }())
            
);


/* Aggregates Toolbar */
LLVA.ReportsGrid.AggregatesToolbar = Ext.extend(Ext.Toolbar,

    (function() {
            
        return {
          
            // public members    
            initComponent: function() {
                var config = {
                    id: 'ReportsGridAggregatesToolbar-' + Ext.Component.AUTO_ID,
                    height: 20,
                    items: []
                };
                Ext.apply(this, Ext.apply(this.initialConfig, config));
                LLVA.ReportsGrid.AggregatesToolbar.superclass.initComponent.apply(this, arguments);
            },
            
            setAggregates: function(aggregates) {
                this.removeAll(true);
                if (aggregates) {
                    var i;
                    for(i = 0; i < aggregates.length; i++) {
                        this.add(aggregates[i].name + ':', '<b>'+aggregates[i].total+'</b>', ' ');
                    }
                    this.doLayout();
                }
            }
        };
    }())
          
);


/* Help Window */
LLVA.ReportsGrid.HelpWindow = Ext.extend(Ext.Window,
  
    (function() {
      
        return {

            // public members
            initComponent: function() {
                
                Ext.apply(this, {  

                    layout:'fit',
                    width:600,
                    height:400,
                    title: 'Help',
                    closeAction:'hide',
                    plain: true,
                    buttons: [{
                        text: 'Close',
                        scope: this,
                        handler: function() {
                            this.hide();
                        }
                    }]                            

                });
                
                LLVA.ReportsGrid.HelpWindow.superclass.initComponent.apply(this, arguments);
            }, 
            
            displayHelp: function(helpContentEl) {
                this.show();
                var el = Ext.getDom(helpContentEl);
                this.body.dom.innerHTML = el.innerHTML;
            }
        };
    }())
            
);


/* Reports Grid Panel */
LLVA.ReportsGrid.GridPanel = Ext.extend(Ext.grid.GridPanel,
  
    (function() {
                          
        return {
          
            // public fields  
            ownerReport: undefined,
            dataStore: undefined,    
            gridFilters: undefined,
            panelResizer: undefined,
            groupSummary: undefined,
            selectionModel: undefined,
            topToolbar: undefined,
            footerToolbar: undefined,
            bottomToolbar: undefined,
            gridPlugins: undefined,
            frame: undefined,
            border: undefined,
            tools: undefined,
            title: undefined,
            width: undefined,
            height: undefined,
            showRowNumbers: undefined,
            stripeRows: undefined,
            columnLines: undefined,
            loadMask: undefined,
            trackMouseOver: undefined,
            disableSelection: undefined,
            helpContentEl: undefined,
            showHeader: true,
            forceFit: true,
            groupTextTpl: undefined,
    
            initComponent:function() {
          
                var self = this;
                this.helpWindow = new LLVA.ReportsGrid.HelpWindow();    
                if (this.gridPlugins === undefined) {
                    this.gridPlugins = [];
                }                            
                if (this.gridFilters !== null) {
                    this.gridPlugins.push(this.gridFilters); 
                }
                if (this.panelResizer !== null) {
                    this.gridPlugins.push(this.panelResizer); 
                }      
                if (this.groupSummary !== null) {
                    this.gridPlugins.push(this.groupSummary); 
                }                   
                Ext.apply(this, {  
                    store: this.dataStore,
                    id: 'ReportsGridPanel-' + Ext.Component.AUTO_ID,
                    buttonAlign: 'left',
                    tbar: this.topToolbar,
                    fbar: this.footerToolbar,
                    bbar: this.bottomToolbar,
                    columns: [],
                    plugins: this.gridPlugins,
                    sm: this.selectionModel,
                    cls:'x-panel-blue',
                    iconCls:'icon-grid',
                    layout: 'fit',
                    frame: this.frame,
                    border: this.border,
                    width: this.width,
                    height: this.height,
                    stripeRows: this.stripeRows,
                    columnLines: this.columnLines,
                    loadMask: this.loadMask,
                    trackMouseOver: this.trackMouseOver,
                    disableSelection: this.disableSelection,
                    title: this.title,
                    header: this.showHeader,                    
                    
                    view: new Ext.grid.GroupingView({
                        forceFit: this.forceFit,
                        showGroupName: this.showGroupName,
                        enableNoGroups: this.enableNoGroups,
                        enableGroupingMenu: this.enableGroupingMenu,
                        groupTextTpl: this.groupTextTpl,
                        onAdd: function(store, records, index) {
                            //alert(index);
                            //this.insertRows(store, index, index + (records.length-1));
                        },
                        onDataChange: function() { 
                            try {
                                var columns = [];  
                                if(self.showRowNumbers) { 
                                    columns.push(new Ext.grid.RowNumberer()); 
                                }              
                                if(self.selectionModel !== undefined) { 
                                    columns.push(self.selectionModel); 
                                }    
                                if ((self.gridPlugins !== undefined) && (self.gridPlugins.length > 0)){
                                    Ext.each(self.gridPlugins, function(plugin) {
                                        if (!(plugin instanceof Ext.ux.grid.GridFilters) &&
                                            !(plugin instanceof Ext.ux.PanelResizer) && 
                                            !(plugin instanceof Ext.ux.grid.GroupSummary)) {
                                            columns.push(plugin); 
                                        }
                                    });
                                }          
                                Ext.each(this.ds.reader.jsonData.columns, function(column){
                                    columns.push(column);
                                });
                                this.cm.setConfig(columns); 
                                this.syncFocusEl(0);                                
                                if (this.ds.reader.jsonData.metaData.title !== undefined) {
                                    this.grid.setTitle(this.ds.reader.jsonData.metaData.title);
                                }
                                if (this.ds.reader.jsonData.gridFilters) {
                                    this.grid.gridFilters.addFilters(this.ds.reader.jsonData.gridFilters.filters);
                                    delete this.ds.reader.jsonData.gridFilters.filters;
                                    Ext.apply(this.grid.gridFilters, this.ds.reader.jsonData.gridFilters);
                                }
                                if (this.grid.getView().getGroupField()) {
                                    self.ownerReport.setGroupField(this.grid.getView().getGroupField());
                                } else {
                                    self.ownerReport.setGroupField(undefined);
                                }          
                                this.grid.autoExpandColumn = this.ds.reader.jsonData.metaData.autoExpandColumn;
                                this.grid.getView().refresh();                                                                                                                        
                            } catch(err) {
                                // expect exception
                            }
                        }                        
                    }),
                    tools: this.tools,               
                    listeners: {
                        afterlayout: {scope:this, single: true, fn: this.afterLayout}, 
                        rowclick: this.onRowClicked,
                        rowdblclick: this.onRowDoubleClicked,
                        rowcontextmenu: this.onRowRightClicked,
                        cellclick: this.onCellClick,
                        celldblclick: this.onCellDoubleClick,
                        groupchange: this.onGroupChange,
                        sortchange: this.onSortChange
                    }
                });

                LLVA.ReportsGrid.GridPanel.superclass.initComponent.apply(this, arguments);
            },
            
            getOwnerReport: function() {
                return this.ownerReport;  
            },
            
            getGridPlugins: function() {
                return this.gridPlugins;  
            },            
            
            onRender:function() {
                LLVA.ReportsGrid.GridPanel.superclass.onRender.apply(this, arguments);  
            },
            
            afterLayout: function(grid, layout) {
                this.ownerReport.afterLayout(grid, layout);
            },
            
            onRowClicked: function(grid, row, event) {
                this.ownerReport.onRowClicked(grid, row, event);
            },
            
            onRowDoubleClicked: function(grid, row, event) {
                this.ownerReport.onRowDoubleClicked(grid, row, event);
            },
            
            onRowRightClicked: function(grid, row, event) {
                this.ownerReport.onRowRightClicked(grid, row, event);
            },
            
            onCellClick: function(grid, row, col, event) {
                this.ownerReport.onCellClick(grid, row, col, event);
            },
            
            onCellDoubleClick: function(grid, row, col, event) {
                this.ownerReport.onCellDoubleClick(grid, row, col, event);
            },
            
            onGroupChange: function(grid, groupField) {
                this.ownerReport.onGroupChange(grid, groupField);
            },
            
            onSortChange: function(grid, sortInfo) {
                this.ownerReport.onSortChange(grid, sortInfo);
            }                         
            
        };
    }())
            
);
