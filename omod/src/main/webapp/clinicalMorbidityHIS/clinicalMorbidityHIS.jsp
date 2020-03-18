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

<openmrs:require privilege="Manage department" otherwise="/login.htm" redirect="/module/hospitalcore/clinicalMorbidityHIS.form" />

<spring:message var="pageTitle" code="hospitalcore.clinical.excel" scope="page"/>

<%@ include file="/WEB-INF/template/header.jsp" %>
<%@ include file="../includes/js_css.jsp" %>

<h2><spring:message code="hospitalcore.clinical.excel"/></h2>	

<c:forEach items="${errors.allErrors}" var="error">
	<span class="error"><spring:message code="${error.defaultMessage}" text="${error.defaultMessage}"/></span>
</c:forEach>

<div>
	<label>
        <strong>Select Frequency : </strong>
        <select id="selectMonth"></select>
        <select id="selectYear"></select>
	</label>
	<label>
		<strong>Select Ward : </strong>
		<select id="selectWard"></select>
	</label>
    
    <input type="button" value="View" onclick="viewReport();"/> 
	<input type="button" value="Download" onclick="tableToExcel('dataTable', 'Clinical Morbidity Report');"/> 
</div>

<div id="reportLoader"
	style="display: none; position: absolute; z-index: 1000; left: 50%; top: 50%; font-weight: bolder; color: darkgray; font-size: larger;">Loading...</div>
<div id="viewReport" style="overflow-x: auto; overflow-y: auto; height: 600px;"></div>

<script type="text/javascript">
	let months = ["January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"];
	let wards = ["OPD WARD","OUTREACH OPD","IPD WARD"];
	let date = new Date();
	let currentYear = date.getFullYear();
	let currentMonth = date.getMonth();

	let selectMonthOption = "";
    months.forEach((month, index) => {
		let value = index >= 10 ? (index+1) : "0" + (index+1);
		if (currentMonth == index) {
			selectMonthOption += "<option value='" + value + "' selected>" + month + "</option>";
		} else {
			selectMonthOption += "<option value='" + value + "'>" + month + "</option>";
		}
		
    })
	document.querySelector("#selectMonth").innerHTML = selectMonthOption;

	let selectYearOption = "<option value='" + currentYear + "' selected>" + currentYear + "</option>";
	for (let year = currentYear-1; year > 2009; year--) {
		selectYearOption += "<option value='" + year + "'>" + year + "</option>";
	}
	document.querySelector("#selectYear").innerHTML = selectYearOption;

	let selectWardOption = "";
	wards.forEach((ward, index) => {
		if (index == 0) {
			selectWardOption += "<option value='" + ward + "' selected>" + ward + "</option>";
		} else {
			selectWardOption += "<option value='" + ward + "'>" + ward + "</option>";
		}
	});
	document.querySelector("#selectWard").innerHTML = selectWardOption;


	// get context path in order to build controller url
	function getContextPath(){		
		pn = location.pathname;
		len = pn.indexOf("/", 1);				
		cp = pn.substring(0, len);
		return cp;
	}

	function viewReport() {
		let month = document.querySelector("#selectMonth").value;
		let year = document.querySelector("#selectYear").value;
		let ward = document.querySelector("#selectWard").value;
		
		jQuery('#viewReport').html("");
		document.getElementById("reportLoader").style.display = "block";
		jQuery.ajax({
			type : "GET",
			url : getContextPath() + "/module/hospitalcore/clinicalMorbidityReport.form",
			data : ({
				m : month,
				y : year,
				w : ward
			}),
			success : function(result) { 
				document.getElementById("reportLoader").style.display = "none";
				jQuery("#viewReport").html(result);	
			}
		});
	}

	var tableToExcel = (function() {
		var uri = 'data:application/vnd.ms-excel;base64,',
	  	template = '<html xmlns:o="urn:schemas-microsoft-com:office:office" xmlns:x="urn:schemas-microsoft-com:office:excel" xmlns="http://www.w3.org/TR/REC-html40"><head ><!--[if gte mso 9]><xml><x:ExcelWorkbook><x:ExcelWorksheets><x:ExcelWorksheet><x:Name>{worksheet}</x:Name><x:WorksheetOptions><x:DisplayGridlines/></x:WorksheetOptions></x:ExcelWorksheet></x:ExcelWorksheets></x:ExcelWorkbook></xml><![endif]--></head><body><table>{table}</table></body></html>',
		base64 = function(s) { return window.btoa(unescape(encodeURIComponent(s))) },
		format = function(s, c) { return s.replace(/{(\w+)}/g, function(m, p) { return c[p]; }) }
		  
		return function(table, name) {
	    	if (!table.nodeType) table = document.getElementById(table)
	    	var link = document.createElement("a");
	    	var ctx = {worksheet: 'ClinicalMorbidityReport', table: table.innerHTML}
	   
			link.href = uri + base64(format(template, ctx));

			link.style = 'visibility:hidden';
			link.download ='ClinicalMorbidityReport.xls';

			document.body.appendChild(link);
			link.click();
	  	}
	})()
</script>

<%@ include file="/WEB-INF/template/footer.jsp" %>