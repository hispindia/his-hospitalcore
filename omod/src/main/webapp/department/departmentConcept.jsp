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
<style>
	.ui-button { margin-left: -1px; }
	.ui-button-icon-only .ui-button-text { padding: 0.35em; } 
	.ui-autocomplete-input { margin: 0; padding: 0.48em 0 0.47em 0.45em; }
</style>

<c:forEach items="${errors.allErrors}" var="error">
	<span class="error"><spring:message code="${error.defaultMessage}" text="${error.defaultMessage}"/></span>
</c:forEach>
<spring:bind path="departmentConceptCommand">
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
<input type="hidden" id="pageId" value="pageDepartmentConcept"/>
<span class="boxHeader"><spring:message code="hospitalcore.departmentConcept.manage"/></span>
<form method="post" class="box" id="formDepartmentConcept">

<table cellspacing="5">
	<tr><td colspan="3">
	<strong>Department:</strong><em>*</em>
		<select name="departmentId" id="departmentId" onchange="HOSPITALCORE.onChangeDepartment(this);" style="width:300px">
			<option value="">Please choose department</option>
			<c:if test="${not empty listDepartment }">
				<c:forEach items="${listDepartment}" var="department">
					<option value="${department.id}" 
						<c:if test="${department.id == dId}">selected="selected"</c:if>
					>${department.name} 
					</option>
				</c:forEach>
			</c:if>
		</select>
	</td>
	</tr>
	<tr><td colspan="3">
	<strong>Diagnosis:</strong><em>*</em>
	<input class="ui-autocomplete-input ui-widget-content ui-corner-all" id="diagnosis" style="width:280px" name="diagnosis"/>
	</td>
	</tr>
   <tr>
        <td>
          <!-- List of all available DataElements -->
          <div id="divAvailableDiagnosisList">
          <select size="15" style="width:550px" id="availableDiagnosisList" name="availableDiagnosisList" multiple="multiple" style="min-width:25em;height:10em" ondblclick="moveSelectedById( 'availableDiagnosisList', 'selectedDiagnosisList');">
              <c:forEach items="${diagnosisList}" var="diagnosis">
              	 <option value="${diagnosis.id}" >${diagnosis.name}</option>
              </c:forEach>
          </select>
          </div>
        </td>
        <td>
        	<input type="button" value="&gt;"  style="width:50px" onclick="moveSelectedById( 'availableDiagnosisList', 'selectedDiagnosisList');"/><br/>
            <input type="button" value="&lt;"  style="width:50px" onclick="moveSelectedById( 'selectedDiagnosisList', 'availableDiagnosisList');"/><br/>
			<input type="button" value="&gt;&gt;"  style="width:50px" onclick="moveAllById( 'availableDiagnosisList', 'selectedDiagnosisList' );"/><br/>
			<input type="button" value="&lt;&lt;"  style="width:50px" onclick="moveAllById( 'selectedDiagnosisList', 'availableDiagnosisList' );"/>
		</td>			
        <td>
          <!-- List of all selected DataElements -->
          <select id="selectedDiagnosisList" size="15" style="width:550px" name="selectedDiagnosisList" multiple="multiple" style="min-width:25em;height:10em" ondblclick="moveSelectedById( 'selectedDiagnosisList', 'availableDiagnosisList' );">
          	  <c:forEach items="${listDiagnosisDepartment}" var="ss">
              	 <option value="${ss.concept.id}" >${ss.concept.name}</option>
              </c:forEach>
          </select>
        </td>
  </tr>
  <tr><td colspan="3">
	<div class="ui-widget">
		<strong>Procedure:</strong><em>*</em>
		<input class="ui-autocomplete-input ui-widget-content ui-corner-all" id="procedure" style="width:280px" name="procedure"/>
	</div>
  
 </td></tr>
  <tr>
        <td>
        	
          <!-- List of all available DataElements -->
          <div id="divAvailableProcedureList">
          <select size="15" style="width:550px" id="availableProcedureList" name="availableProcedureList" multiple="multiple" style="min-width:25em;height:5em" ondblclick="moveSelectedById( 'availableProcedureList', 'selectedProcedureList');">
             <c:forEach items="${listProcedures}" var="procedure">
              	 <option value="${procedure.conceptId}" >${procedure.name}</option>
              </c:forEach>
          </select>
          </div>
        </td>
        <td>
        	<input type="button" value="&gt;"  style="width:50px" onclick="moveSelectedById( 'availableProcedureList', 'selectedProcedureList');"/><br/>
            <input type="button" value="&lt;"  style="width:50px" onclick="moveSelectedById( 'selectedProcedureList', 'availableProcedureList');"/><br/>
			<input type="button" value="&gt;&gt;"  style="width:50px" onclick="moveAllById( 'availableProcedureList', 'selectedProcedureList' );"/><br/>
			<input type="button" value="&lt;&lt;"  style="width:50px" onclick="moveAllById( 'selectedProcedureList', 'availableProcedureList' );"/>
		</td>			
        <td>
          <!-- List of all selected DataElements -->
          <select size="15" style="width:550px" id="selectedProcedureList" name="selectedProcedureList" multiple="multiple" style="min-width:25em;height:5em" ondblclick="moveSelectedById( 'selectedProcedureList', 'availableProcedureList' )">
          	  <c:forEach items="${listProcedureDepartment}" var="ss">
              	 <option value="${ss.concept.id}" >${ss.concept.name}</option>
              </c:forEach>
          </select>
        </td>
  </tr>
  
  
  <!-- Symptom -->
  
   <tr><td colspan="3">
	<div class="ui-widget">
		<strong>Symptom:</strong><em>*</em>
		<input class="ui-autocomplete-input ui-widget-content ui-corner-all" id="symptom" style="width:280px" name="symptom"/>
	</div>
  
 </td></tr>
  <tr>
        <td>
        	
          <!-- List of all available DataElements -->
          <div id="divAvailableSymptomList">
          <select size="15" style="width:550px" id="availableSymptomList" name="availableSymptomList" multiple="multiple" style="min-width:25em;height:5em" ondblclick="moveSelectedById( 'availableSymptomList', 'selectedSymptomList');">
             <c:forEach items="${listSymptom}" var="symptom">
              	 <option value="${symptom.conceptId}" >${symptom.name}</option>
              </c:forEach>
          </select>
          </div>
        </td>
        <td>
        	<input type="button" value="&gt;"  style="width:50px" onclick="moveSelectedById( 'availableSymptomList', 'selectedSymptomList');"/><br/>
            <input type="button" value="&lt;"  style="width:50px" onclick="moveSelectedById( 'selectedSymptomList', 'availableSymptomList');"/><br/>
			<input type="button" value="&gt;&gt;"  style="width:50px" onclick="moveAllById( 'availableSymptomList', 'selectedSymptomList' );"/><br/>
			<input type="button" value="&lt;&lt;"  style="width:50px" onclick="moveAllById( 'selectedSymptomList', 'availableSymptomList' );"/>
		</td>			
        <td>
          <!-- List of all selected DataElements -->
          <select size="15" style="width:550px" id="selectedSymptomList" name="selectedSymptomList" multiple="multiple" style="min-width:25em;height:5em" ondblclick="moveSelectedById( 'selectedSymptomList', 'availableSymptomList' )">
          	  <c:forEach items="${listSymptomDepartment}" var="ss">
              	 <option value="${ss.concept.id}" >${ss.concept.name}</option>
              </c:forEach>
          </select>
        </td>
  </tr>
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
</table>      
<br />
<input type="submit" value="<spring:message code="general.save"/>" onclick="HOSPITALCORE.submit();">
<input type="button" value="<spring:message code="general.cancel"/>" onclick="ACT.go('departmentList.form');">
</form>
<%@ include file="/WEB-INF/template/footer.jsp" %>