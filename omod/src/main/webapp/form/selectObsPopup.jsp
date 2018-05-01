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
		jQuery("#conceptPopup").autocomplete(openmrsContextPath + '/module/hospitalcore/ajax/autocompleteConceptSearch.htm').result(function(event, item){
			SELECTOBSPOPUP.showConceptInfo();
		});
		jQuery("#conceptPopup").focus();
	});
	
	SELECTOBSPOPUP = {
		
		// Get concept information and display on popup
		showConceptInfo: function(){
			
			jQuery.ajax({
				type : "GET",
				url : openmrsContextPath + "/module/hospitalcore/getConceptInfo.form",
				data : ({
					name: jQuery('#conceptPopup').val()
				}),
				dataType: "json",
				success : function(data) {
					jQuery("#title").val(data.description);
				},
				error : function(xhr, ajaxOptions, thrownError) {
					alert(thrownError);
				}
			});		
		},
		
		// Insert obs
		insert: function(){
		
			title = jQuery("#title").val();
			validations = SELECTOBSPOPUP.getValidation();
			json = {
				validations: validations		
			};
			
			EDIT.insertObs(jQuery('#conceptPopup').val(), '${type}', jQuery("#title").val(), JSON.stringify(json)); 
			tb_remove();
		},
		
		// Get validations
		getValidation: function(){
			validations = [];
			
			// get predefined validations
			jQuery("input.validation[type=checkbox]:checked").each(function(index, item){
				validation = {
					name: jQuery(item).attr("id"),
					regex: jQuery(item).val(),
					message: jQuery("#" + jQuery(item).attr("id") + "_message").val()
				};
				validations.push(validation);
			});
			
			// get custom validation
			if(!StringUtils.isBlank(jQuery("#validation_custom").val())){
				validation = {
					name: "validation_custom",
					regex: jQuery("#validation_custom").val(),
					message: jQuery("#validation_custom_message").val()
				};
				validations.push(validation);				
			}
			return validations;
		}
	}
</script>

<center>
	<table style="width:100%">
		<tr>
			<td>
				<b>Concept</b>
			</td>
			<td>
				<input id="conceptPopup" style="width:350px;"/>
			</td>
		</tr>
		<tr>			
			<td>
				
			</td>
			<td>
				<c:if test="${type eq 'selection'}">
					<input type="checkbox" name="sex" id="multiple" /><label for="multiple"> Multiple selection</label>
				</c:if>
			</td>
		</tr>
		<tr>
			<td valign='top'><b>Hint</b></td>
			<td>
				<textarea id="title" style="width:350px; height: 50px;"></textarea>
			</td>
		</tr>
		<tr>
			<td valign='top'><b>Validations</b></td>
			<td>
				<table style="width:100%">
					
					<tr>
						<td>
							<input class="validation" type="checkbox" id="validation_mandatory" value="\S" checked="checked"/><label for="validation_required"> Mandatory</label>
						</td>
						<td>
							<input class="validation" id="validation_mandatory_message" value="This field is required" style="width:250px;"/>
						</td>
					</tr>
					<tr>
						<td>
							<input class="validation" type="checkbox" id="validation_number" value="[-+]?([0-9]*\.[0-9]*)"/><label for="validation_number"> Number</label>
						</td>
						<td>
							<input class="validation" id="validation_number_message" value="Please enter a number into this field" style="width:250px;"/>
						</td>
					</tr>
					<tr>
						<td>
							<input class="validation" type="checkbox" id="validation_digit" value="[0-9]+"/><label for="validation_digit"> Digit</label>
						</td>
						<td>
							<input class="validation" id="validation_digit_message" value="Please enter digits into this field" style="width:250px;"/>
						</td>
					</tr>
					<tr>
						<td>
							<input class="validation" type="checkbox" id="validation_date" value="^(((0[1-9]|[12]\d|3[01])\/(0[13578]|1[02])\/((19|[2-9]\d)\d{2}))|((0[1-9]|[12]\d|30)\/(0[13456789]|1[012])\/((19|[2-9]\d)\d{2}))|((0[1-9]|1\d|2[0-8])\/02\/((19|[2-9]\d)\d{2}))|(29\/02\/((1[6-9]|[2-9]\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))))$"/><label for="validation_date"> Date</label>
						</td>
						<td>
							<input class="validation" id="validation_date_message" value="Please enter a correct date" style="width:250px;"/>
						</td>
					</tr>
					<tr>
						<td>
							<input class="validation" id="validation_custom" title="Custom validation" style="width:80px;"/>
						</td>
						<td>
							<input class="validation" id="validation_custom_message" value="" style="width:250px;" title="Custom validation"/>
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>	
	
	<input type="button" onClick="SELECTOBSPOPUP.insert();" value="Insert"/>
	<input type="button" onClick="tb_remove();" value="Close"/>
</center>