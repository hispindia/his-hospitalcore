

<%@ include file="/WEB-INF/template/include.jsp" %>

<openmrs:require privilege="Manage department" otherwise="/login.htm" redirect="/module/hospitalcore/patientDetailasCSV.form" />

<spring:message var="pageTitle" code="hospitalcore.download.csv" scope="page"/>

<%@ include file="/WEB-INF/template/header.jsp" %>
<%@ include file="../includes/js_css.jsp" %>

<h2><spring:message code="hospitalcore.download.csv"/></h2>	

<br />
<c:forEach items="${errors.allErrors}" var="error">
	<span class="error"><spring:message code="${error.defaultMessage}" text="${error.defaultMessage}"/></span><
</c:forEach>

<br /><br />

<form:form action="downloadCSV" method="post" id="downloadCSV">
		<fieldset style="width: 400px;">
			
			<input id="submitId" type="submit" value="Downlaod CSV">
		</fieldset>
	</form:form>



<%@ include file="/WEB-INF/template/footer.jsp" %>