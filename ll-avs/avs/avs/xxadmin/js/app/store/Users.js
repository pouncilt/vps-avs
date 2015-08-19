Ext.define('LLVA.AVS.Admin.store.Users', {
    extend: 'Ext.data.Store',
    model: 'LLVA.AVS.Admin.model.User',    
    root: {
        expanded: true,
        text: '',
        children: []
    },    
    folderSort: false,
    autoLoad: false,
    autoSync: false  
});
