CKEDITOR.plugins.add( 'formdate', {
	init: function( editor ){
		editor.addCommand( 'insertformdate',{
			exec : function( editor ) {    						
				tb_show("testing", "selectObsPopup.form?type=textbox&modal=true&height=250&width=500");
			}
		});
		
		editor.ui.addButton( 'formdate', {
			label: 'Insert date',
			command: 'insertformdate',
			icon: this.path + 'images/formdate.gif'
		});
	}
});