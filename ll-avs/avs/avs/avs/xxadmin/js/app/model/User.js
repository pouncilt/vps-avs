Ext.define('LLVA.AVS.Admin.model.User', {
    extend: 'Ext.data.Model',

    fields: [
        {name: 'userId', type: 'int'},
        'userName'
    ],
    proxy: {
        id: 'usersProxy',
        type: 'ajax',
        api: {
            read: 'w/s/db/users.action'
        },
        reader: {
            type: 'json'
        },
        writer: {
            type: 'json',
            writeAllFields: false
        }                        
    }

});
