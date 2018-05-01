CKEDITOR.plugins.add( 'formtextarea', {
	init: function( editor ){
		editor.addCommand( 'insertformtextarea',{
			exec : function( editor ) {    						
				tb_show("testing", "selectObsPopup.form?type=textarea&modal=true&height=250&width=500");
			}
		});
		
		editor.ui.addButton( 'formtextarea', {
			label: 'Insert textarea',
			command: 'insertformtextarea',
			icon: this.path + 'images/textarea.png'
		});
	}
});