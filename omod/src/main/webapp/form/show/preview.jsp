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
		jQuery(".date").datepicker({yearRange:'c-100:c+100', dateFormat: 'dd/mm/yy', changeMonth: true, changeYear: true});
		jQuery(".parameter").tipTip();		
	});
	
	FORMPREVIEW = {
	
		// Submit the form
		submit: function(){
			if(FORMPREVIEW.validate()){
				eval('${form.afterSubmit}');
				tb_remove();
			}
		},
	
		// Validate form
		validate: function(){
		
			validateResult = true;
			pairs = jQuery("#formContent").serialize();
			jQuery(".validationError").hide();
			
			jQuery(pairs.split("&")).each(function(index, item){
				params = item.split("=");				
				parameter = params[0];
				value = params[1];				
				parameter = parameter.replace(/\+/g , " ");
				
				json = FORMPREVIEW.getMetadata(parameter);
				if(json!=null){
					validations = json.validations;
					
					jQuery(validations).each(function(index, validation){
						pattern = new RegExp(validation.regex, "g");
						if(!pattern.test(jQuery("input[name='" + parameter + "']").val())){
							jQuery("input[name='" + parameter + "']").after("<span class='validationError' style='color:red;'>" + validation.message + "</span>");
							validateResult = false;
						}
					});
				}
			});			
			return validateResult;
		},
		
		// Get metadata from form
		getMetadata: function(parameter){			
			data = jQuery("input[name='" + parameter + "']").attr("data");
			if(data!=undefined){
				data = data.replace(/'/g, '"');
				return jQuery.parseJSON(data);
			} else {
				return null;
			}
		}
	}
</script>

<form id="formContent">
	${form.content}
</form>

<input type="button" value="Save" onClick="FORMPREVIEW.submit();"/>
<input type="button" value="Cancel" onClick="tb_remove();"/>