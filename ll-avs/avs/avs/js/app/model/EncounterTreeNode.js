Ext.define('LLVA.AVS.Wizard.model.EncounterTreeNode', {
    extend: 'Ext.data.Model',

    fields: [
        'id',
        'fmDatetime',
        'datetime',
        'locationIen',
        'locationName',
        'status',
        'selected',
        'expanded',
        'checked',
        'leaf'
    ]

});
