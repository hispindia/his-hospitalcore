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
<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/headerMinimal.jsp" %>
<c:choose>
	<c:when test="${not empty patients}" >
	<span class="boxHeader">List Patients</span> 
	<table class="box">
		<tr>
			<th>Identifier</th>
			<th>Name</th>
			<th>Age</th>
		</tr>
		<c:forEach items="${patients}" var="patient" varStatus="varStatus">
			<tr class='${varStatus.index % 2 == 0 ? "oddRow" : "evenRow" } '  >
				<td ><a href="#" onclick="ISSUE.addPatient('createPatientIssueDrug.form?patientId=${patient.patientId}');">${patient.patientIdentifier.identifier}</a></td>
				<td>${patient.givenName} ${patient.middleName} ${patient.familyName}</td>
                <td>
                	<c:choose>
                		<c:when test="${patient.age == 0  }">&lt 1</c:when>
                		<c:otherwise >${patient.age }</c:otherwise>
                	</c:choose>
                </td>
			</tr>
		</c:forEach>
	</table>
	</c:when>
	<c:otherwise>
	No Patient found.
	</c:otherwise>
</c:choose>
<script>
	INVENTORY.initTableHover();
</script>

<%@ include file="patientSearchPaging.jsp"%>
