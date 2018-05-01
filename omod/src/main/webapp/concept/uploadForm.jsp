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
<%@ include file="/WEB-INF/template/header.jsp"%>

<script type="text/javascript" src="${pageContext.request.contextPath}/moduleResources/hospitalcore/scripts/jquery/jquery-1.4.2.min.js"></script>
<c:choose>
	<c:when test="${empty diagnosisNo}">
		<script type="text/javascript">
			function submitForm(){
				$("#loading").show();
				$("#uploadFileForm").submit();
			}
		</script>
		<div id="loading" style="display:none;">
			Please wait while importing diagnosis. It takes time based on the number of diagnosis...
		</div>
		<form id="uploadFileForm" method="post" enctype="multipart/form-data">	
			<spring:bind path="uploadFile.diagnosisFile">
				Diagnosis: 
				<input type="file" name="${status.expression}" />
			</spring:bind><br/>
			<spring:bind path="uploadFile.synonymFile">
				Synonym: 
				<input type="file" name="${status.expression}" />
			</spring:bind><br/>
			<spring:bind path="uploadFile.mappingFile">
				Mapping: 
				<input type="file" name="${status.expression}" />
			</spring:bind><br/>
			<input type="button" value="Upload" onClick="submitForm();"/>
		</form>
	</c:when>
	<c:otherwise>
		<c:choose>
			<c:when test="${fail}">
				${error}
			</c:when>
			<c:otherwise>
				${diagnosisNo} diagnosis have been imported!
			</c:otherwise>
		</c:choose>
	</c:otherwise>
</c:choose>


<%@ include file="/WEB-INF/template/footer.jsp"%>


