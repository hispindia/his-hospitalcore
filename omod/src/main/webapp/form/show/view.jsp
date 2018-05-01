 <%--
 *  Copyright 2009 Society for Health Information Systems Programmes, India (HISP India)
 *
 *  This file is part of Hospitalcore module.
 *
 *  Hospitalcore module is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.

 *  Hospitalcore module is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with Hospitalcore module.  If not, see <http://www.gnu.org/licenses/>.
 *
--%> 
<%@ include file="/WEB-INF/template/include.jsp" %>

<script type="text/javascript">
	jQuery(document).ready(function(){			
		jQuery("#formContent").fillForm("${values}");
		makeForView();
	});
	
	// Make all inputs for view (hide box, dropdown)
	function makeForView(){
		jQuery("#formContent input").each(function(index){
			input = jQuery(this);
			input.css("border", "0px");
			input.css("color", "blue");
			input.css("font-size", "medium");
			input.attr("disabled", "disabled");
			input.css("background-color", "white");
			
			// make changes for radio buttons
			if(input.attr('type')=='radio'){
				if(input.attr('checked')==false){
					input.parent().hide();
				} else {
					input.hide();
					input.parent().css("color", "blue");
					input.parent().css("font-size", "medium");					
				}
			}
		});
		
		jQuery("#form_content select").each(function(index){
			select = jQuery(this);
			select.css("border", "0px");			
			selectedOption = jQuery("option:selected", select);
			selectedOption.css("color", "blue");
			select.after("<span style='color:blue; font-size: medium;'>"+selectedOption.html()+"</span>");
			select.hide();
		});
		
		jQuery("#form_content textarea").each(function(index){
			textarea = jQuery(this);
			value = textarea.val().replace(/\n/g, "<br/>");			
			textarea.after("<p style='color:blue; font-size: medium;'>" + value + "</p>");
			textarea.hide();
		});
	}
	
	// Print form
	function printForm(){
		
		jQuery("#formContent").printArea({
			mode : "popup",
			popClose : true
		});
	}
</script>

<form id="formContent" method="post" action="${pageContext.request.contextPath}/module/hospitalcore/showForm.form">
	<input type="hidden" name="encounterId" value="${encounterId}"/>
	<input type="hidden" name="redirect" value="${redirect}"/>
	${form.content}
</form>

<input type="button" value="Print" onClick="printForm();"/>
<input type="button" value="Close" onClick="tb_remove();"/>
