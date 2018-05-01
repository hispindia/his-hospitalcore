CKEDITOR.plugins.add( 'formradio', {
	init: function( editor ){
		editor.addCommand( 'insertformradio',{
			exec : function( editor ) {    						
				tb_show("testing", "selectObsPopup.form?type=radio&modal=true&height=250&width=500");
			}
		});
		
		editor.ui.addButton( 'formradio', {
			label: 'Insert radio',
			command: 'insertformradio',
			icon: this.path + 'images/formradio.png'
		});
	}
});
