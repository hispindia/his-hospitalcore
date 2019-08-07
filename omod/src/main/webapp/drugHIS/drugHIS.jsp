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

<openmrs:require privilege="Manage department" otherwise="/login.htm" redirect="/module/hospitalcore/drugHIS.form" />

<spring:message var="pageTitle" code="hospitalcore.download.excel" scope="page"/>

<%@ include file="/WEB-INF/template/header.jsp" %>
<%@ include file="../includes/js_css.jsp" %>

<h2><spring:message code="hospitalcore.downloadd.excel"/></h2>	

<br />
<script type="text/javascript">  jQuery(document).ready(function() {
	options1 = {
			pattern: 'yyyy-mmm', // Default is 'mm/yyyy' and separator char is not mandatory
			};
	$('#monthpicker').datepicker('destroy');
	//$('#monthpicker').monthpicker('destroy');
	$('#monthpicker').monthpicker(options1);
	});

function viewReport() {
	var date = jQuery("#monthpicker").val();	
	if(date==""){
	alert("Provide Date");
	return false;
	}
	else{
	jQuery('#viewReport').empty();
	jQuery.ajax({
					type : "GET",
					url : getContextPath() + "/module/hospitalcore/patientDrugReport.form",
					data : ({
						
						date:date
						
					}),
					success : function(data) { 
					jQuery("#viewReport").html(data);	
					
					}
	  });
	}
	}
	
function exportpatientExcel()
{var date = jQuery("#monthpicker").val();	
	if(date==""){
	alert("Provide Date");
	return false;
	}
	else{
		jQuery.ajax({
					type : "GET",
					url : getContextPath() + "/module/hospitalcore/patientDrugReport.form",
					data : ({
						
						date:date
						
					}),
					success : function(data) { 
					jQuery("#myTab").html(data);	
					tableToExcel('myTab','TestExcel');
					}
	  });
	
	
	}
}
var tableToExcel = (function() {

	  var uri = 'data:application/vnd.ms-excel;base64,'
	    , template = '<html xmlns:o="urn:schemas-microsoft-com:office:office" xmlns:x="urn:schemas-microsoft-com:office:excel" xmlns="http://www.w3.org/TR/REC-html40"><head ><!--[if gte mso 9]><xml><x:ExcelWorkbook><x:ExcelWorksheets><x:ExcelWorksheet><x:Name>{worksheet}</x:Name><x:WorksheetOptions><x:DisplayGridlines/></x:WorksheetOptions></x:ExcelWorksheet></x:ExcelWorksheets></x:ExcelWorkbook></xml><![endif]--></head><body><table>{table}</table></body></html>'
	    , base64 = function(s) { return window.btoa(unescape(encodeURIComponent(s))) }
	    , format = function(s, c) { return s.replace(/{(\w+)}/g, function(m, p) { return c[p]; }) }
	  return function(table, name) {
	    if (!table.nodeType) table = document.getElementById(table)
	    var link = document.createElement("a");
	    var ctx = {worksheet: 'OPDDrugReport', table: table.innerHTML}
	   
		link.href = uri + base64(format(template, ctx));

		link.style = 'visibility:hidden';
		link.download ='DrugReport.xls';

		document.body.appendChild(link);
		link.click();
	  }
	})()
		
		function base64(s) {
			return window.btoa(unescape(encodeURIComponent(s)));
			}

			function format(s, c) {
			return s.replace(/{(\\w+)}/g, function(m, p) { return c[p]; });
			}




</script>
<c:forEach items="${errors.allErrors}" var="error">
	<span class="error"><spring:message code="${error.defaultMessage}" text="${error.defaultMessage}"/></span><
</c:forEach>
<tr>

Frequency:
<td><input type="text" name="date" id="monthpicker"/></td>
<input type="button" value="<spring:message code='hospitalcore.download.view'/>" onclick="viewReport();"/> 
<input type="button" value="<spring:message code='hospitalcore.download.buttons'/>" onclick="exportpatientExcel();"/> 
</body>
</html>
<div id="viewReport"></div>
<table id="myTab" style="visibility:hidden"></table>

<br /><br />



<%@ include file="/WEB-INF/template/footer.jsp" %>