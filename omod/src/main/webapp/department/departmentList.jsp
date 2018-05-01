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

<openmrs:require privilege="Manage department" otherwise="/login.htm" redirect="/module/hospitalcore/departmentList.form" />

<spring:message var="pageTitle" code="hospitalcore.department.manage" scope="page"/>

<%@ include file="/WEB-INF/template/header.jsp" %>
<%@ include file="../includes/js_css.jsp" %>
<h2><spring:message code="hospitalcore.department.manage"/></h2>	

<br />
<c:forEach items="${errors.allErrors}" var="error">
	<span class="error"><spring:message code="${error.defaultMessage}" text="${error.defaultMessage}"/></span><
</c:forEach>
<input type="button" value="<spring:message code='hospitalcore.department.add'/>" onclick="ACT.go('department.form');"/>
<br /><br />
<form method="get" >
<span class="boxHeader"><spring:message code="hospitalcore.department.list"/></span>
<div class="box">
<c:choose>
<c:when test="${not empty departments}">
<table cellpadding="5" cellspacing="0" width="100%">
<tr align="center">
	<th>#</th>
	<th><spring:message code="hospitalcore.department.name"/></th>
	<th><spring:message code="hospitalcore.department.ward"/></th>
	<th><spring:message code="hospitalcore.department.retired"/></th>
	<th><spring:message code="hospitalcore.department.createdOn"/></th>
	<th><spring:message code="hospitalcore.department.createdBy"/></th>
	<th></th>
</tr>
<c:forEach items="${departments}" var="department" varStatus="varStatus">
	<tr align="center" class='${varStatus.index % 2 == 0 ? "oddRow" : "evenRow" } '>
		<td><c:out value="${ varStatus.count }"/></td>	
		<td><a href="#" onclick="ACT.go('department.form?departmentId=${ department.id}');">${department.name}</a> </td>
		<td>
			<c:forEach items="${department.wards}" var="ward" >
				${ward.name}<br/>
			</c:forEach>
		
		</td>
		<td>${department.retired }</td>
		<td><openmrs:formatDate date="${department.createdOn}" type="textbox"/></td>
		<td>${department.createdBy}</td>
		<td>
			<a href="#" title="Add|View|Edit concept to department" onclick="ACT.go('departmentConcept.form?dId=${ department.id}');">Add|View|Edit concept</a>
		</td>
	</tr>
</c:forEach>

</table>
</c:when>
<c:otherwise>
	No department found.
</c:otherwise>
</c:choose>

</div>
</form>


<%@ include file="/WEB-INF/template/footer.jsp" %>