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
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/template/include.jsp"%>

<script type="text/javascript">
	jQuery(document).ready(function() {
		jQuery('#dateOfVisit').datepicker({yearRange:'c-30:c+30', dateFormat: 'dd/mm/yy', changeMonth: true, changeYear: true});
	});
</script>

<form id="searchForm" method="post">
	<input type="hidden" id="view" value="${view}"/>	
	<input type="hidden" id="resultBoxId" value="${resultBoxId}"/>
		<table cellspacing="10">
			<tr>
				<td>Name/Identifier</td>
				<td>
					<input type="text" id="phrase" onkeyup="ADVSEARCH.startSearch(event);" onblur="ADVSEARCH.delay();"/>
				</td>
				<td>
					<a href="#" onClick="jQuery('#advanceSearchBox').toggle(500);">Advance search</a>
				</td>
				<td>
					<img id="ajaxLoader" style="display:none;" src='${pageContext.request.contextPath}/moduleResources/hospitalcore/ajax-loader.gif'/>
				</td>
			</tr>
		</table>
	<div id="advanceSearchBox" style="display:none;">
		<table cellspacing="10">
			<tr>
				<td>Gender</td>
				<td>
					<select id="gender" onblur="ADVSEARCH.delay();">
						<option value="Any">Any</option>
						<option value="M">Male</option>
						<option value="F">Female</option>
					</select>
				</td>
				<td><td>
				<td><td>
				<td></td>
			</tr>
			<tr>
				<td>Age</td>
				<td>
					<input type="text" id="age" onblur="ADVSEARCH.delay();"/>
				</td>
				<td>Range &plusmn;<td>
				<td><select id="ageRange" name="ageRange" style="width:100px; text-align:right;" onblur="ADVSEARCH.delay();">
						<option value="0">Exact</option>
						<option value="1">1 year</option>
						<option value="2" selected="selected">2 years</option>
						<option value="3">3 years</option>
						<option value="4">4 years</option>
						<option value="5">5 years</option>
					</select><td>
				<td><span id="searchAgeError" style="color:red;"/></td>
			</tr>
			<tr>
				<td>Date</td>
				<td>
					<input type="text" id="dateOfVisit" onblur="ADVSEARCH.delay();"/>
				</td>
				<td>Range &plusmn;<td>
				<td><select id="dateRange" name="dateRange" style="width:100px; text-align:right;" onblur="ADVSEARCH.delay();">
						<option value="0">Exact</option>
						<option value="1">1 day</option>
						<option value="2" selected="selected">2 days</option>
						<option value="3">3 days</option>
						<option value="4">4 days</option>
						<option value="5">5 days</option>
					</select><td>
				<td><span id="searchDateError" style="color:red;"/></td>
			</tr>
			<tr>
				<td>Relative name</td>
				<td>
					<input type="text" id="relativeName" onblur="ADVSEARCH.delay();"/>
				</td>
				<td><span id="searchRelativeNameError" style="color:red;"/></td>
			</tr>	
		</table>	
	</div>
	
</form>