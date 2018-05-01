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
<%@ include file="/WEB-INF/template/header.jsp" %>
<%@ include file="../includes/js_css.jsp" %>
<br/>
<script type="text/javascript" src="${pageContext.request.contextPath}/moduleResources/hospitalcore/scripts/CKEditor/ckeditor.js"></script>

<script type="text/javascript">		

	EDIT = {
	
		// Insert obs from popup
		insertObs: function(name, type, title, data){
			jQuery.ajax({
				type : "GET",
				url : openmrsContextPath + "/module/hospitalcore/getHTMLObs.form",
				data : ({
					name	: name,
					type	: type,
					title   : title,
					data	: data
				}),
				success : function(data) {
					CKEDITOR.instances.editor1.insertHtml(data);
				},
				error : function(xhr, ajaxOptions, thrownError) {
					alert(thrownError);
				}
			});	
		},
		
		// validate all data before submitting
		submitForm: function(){				
			jQuery("#coreForm").submit();
		},
		
		toggleEvents: function(){
			jQuery("#events").toggle();
		}
	};
	
	
</script>

<form id='coreForm' method="post" enctype="multipart/form-data">		 
	<spring:bind path="form.id">
		<input type='hidden' name="${status.expression}" value="${status.value}"/>
	</spring:bind>
	<table>		
		<tr>
			<spring:bind path="form.name">
				<td>Name</td>
				<td><input type="text" name="${status.expression}" value="${status.value}" style="width:350px;"/></td>
			</spring:bind>
		</tr>
		<tr>
			<spring:bind path="form.description">
				<td>Description</td>
				<td><input type="text" name="${status.expression}" value="${status.value}" style="width:350px;"/></td>
			</spring:bind>
		</tr>
	</table>
	<spring:bind path="form.content">
		<textarea class="ckeditor" cols="80" id="editor1" name="${status.expression}" rows="10">
			${status.value}
		</textarea>
	</spring:bind>
	<a href="javascript:EDIT.toggleEvents();">Events</a><br/>
	<div id="events" style="display:none;">
		<table cellspacing="20">		
			<tr>
				<spring:bind path="form.afterSubmit">
					<td valign="top">After submit</td>
					<td>
						<textarea name="${status.expression}" style="width:350px;" rows="5">
							${status.value}
						</textarea>
					</td>
				</spring:bind>
			</tr>
		</table>
	</div>
	<input type="button" value="Save" onClick="EDIT.submitForm();"/>	
	<input type="button" value="Cancel" onClick="javascript:window.location.href='listForm.form'"/>	
</form>



<%@ include file="/WEB-INF/template/footer.jsp" %>  