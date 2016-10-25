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

<openmrs:require privilege="Manage department" otherwise="/login.htm" redirect="/module/hospitalcore/downloadCsv.form" />

<spring:message var="pageTitle" code="hospitalcore.download.csv" scope="page"/>

<%@ include file="/WEB-INF/template/header.jsp" %>
<%@ include file="../includes/js_css.jsp" %>

<h2><spring:message code="hospitalcore.download.csv"/></h2>	

<br />
<script type="text/javascript">  jQuery(document).ready(function() {
	jQuery('#date').datepicker({yearRange:'c-30:c+30', dateFormat: 'dd/mm/yy', changeMonth: true, changeYear: true});
});
function exportpatientCSV(){
	
	var date = jQuery("#date").val();		
	window.location = "patientDetailasCSV.form?date=" + date;		
	
}


</script>
<c:forEach items="${errors.allErrors}" var="error">
	<span class="error"><spring:message code="${error.defaultMessage}" text="${error.defaultMessage}"/></span><
</c:forEach>
Date:<input id="date" name="date" value="${currentDate}" style="text-align:right;"/>
<input type="button" value="<spring:message code='hospitalcore.download.buttons'/>" onclick="exportpatientCSV();"/> 
<br /><br />



<%@ include file="/WEB-INF/template/footer.jsp" %>