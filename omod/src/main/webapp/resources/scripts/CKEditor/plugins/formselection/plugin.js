CKEDITOR.plugins.add( 'formselection', {
	init: function( editor ){
		editor.addCommand( 'insertformselection',{
			exec : function( editor ) {    						
				tb_show("testing", "selectObsPopup.form?type=selection&modal=true&height=250&width=500");
			}
		});
		
		editor.ui.addButton( 'formselection', {
			label: 'Insert dropdown',
			command: 'insertformselection',
			icon: this.path + 'images/formselection.png'
		});
	}
});