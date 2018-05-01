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

<openmrs:require privilege="manage department" otherwise="/login.htm" redirect="/module/hospitalcore/department.form" />

<%@ include file="/WEB-INF/template/header.jsp" %>
<%@ include file="../includes/js_css.jsp" %>


<c:forEach items="${errors.allErrors}" var="error">
	<span class="error"><spring:message code="${error.defaultMessage}" text="${error.defaultMessage}"/></span>
</c:forEach>
<spring:bind path="department">
<c:if test="${not empty  status.errorMessages}">
<div class="error">
<ul>
<c:forEach items="${status.errorMessages}" var="error">
    <li>${error}</li>   
</c:forEach>
</ul>
</div>
</c:if>
</spring:bind>
<input type="hidden" id="pageId" value="pageDepartment"/>
<span class="boxHeader"><spring:message code="hospitalcore.department.manage"/></span>
<form method="post" class="box" id="formDepartment">
<spring:bind path="department.id">
			<input type="hidden" name="${status.expression}" id="${status.expression}" value="${status.value}" />
</spring:bind>
<table>
	<tr>
		<td><spring:message code="hospitalcore.department.name"/><em>*</em></td>
		<td>
			<spring:bind path="department.name">
				<input type="text" id="${status.expression}" name="${status.expression}" value="${status.value}" />
				<c:if test="${status.errorMessage != ''}"><span class="error">${status.errorMessage}</span></c:if>
			</spring:bind>
		</td>
	</tr>
	<tr>
		
		<td><spring:message code="hospitalcore.department.ward"/><em>*</em></td>
		<td>
			<spring:bind path="department.wards">
			<select  name="${status.expression }" id="${status.expression }" multiple="multiple" size="10" style="width:250px">
				<c:forEach items="${wards}" var="ward">
					<option value="${ward.id}"
						<c:forEach items="${status.value}" var="wardValue">
							<c:if test="${ward.id == wardValue.id}"> selected</c:if>
						</c:forEach>
					>${ward.name} 
					</option>
				</c:forEach>
			</select>
			<c:if test="${status.errorMessage != ''}"><span class="error">${status.errorMessage}</span></c:if>
			</spring:bind>
		</td>

	</tr>
	
	<tr>
		<td valign="top"><spring:message code="hospitalcore.department.retired"/></td>
		<td>
			<spring:bind path="department.retired">
				<openmrs:fieldGen type="java.lang.Boolean" formFieldName="${status.expression}" val="${status.editor.value}" parameters="isNullable=false" />
				<c:if test="${status.errorMessage != ''}"><span class="error">${status.errorMessage}</span></c:if>
			</spring:bind>
		</td>
	</tr>
	
</table>
<br />
<input type="submit" value="<spring:message code="general.save"/>">
<input type="button" value="<spring:message code="general.cancel"/>" onclick="ACT.go('departmentList.form');">
</form>
<%@ include file="/WEB-INF/template/footer.jsp" %>