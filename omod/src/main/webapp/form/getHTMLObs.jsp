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
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<c:choose>
	<c:when test="${type eq 'text'}">
		<input style="width:150px;" class="parameter" type="text" name="${obsName}" value="" title="${title}" data="${data}"/>
	</c:when>
	<c:when test="${type eq 'textarea'}">
		<textarea name="${obsName}" rows="2" cols="15" data="${data}">
		</textarea>
	</c:when>
	<c:when test="${type eq 'number'}">
		<input style="width:150px;" class="parameter" type="text" name="${obsName}" value="" title="${title}" data="${data}"/>
	</c:when>
	<c:when test="${type eq 'datetime'}">
		<input style="width:150px;" class="parameter date" type="text" name="${obsName}" value="" title="${title} data="${data}""/>
	</c:when>
	<c:when test="${type eq 'selection'}">
		<select style="width:150px;" class="parameter" name="${obsName}" title="${title}" data="${data}">
			<option value=''>Please select</option>
			<c:forEach var="option" items="${options}">
				<option value="${option}">${option}</option>
			</c:forEach>
		</select>
	</c:when>
	<c:when test="${type eq 'radio'}">
		<c:forEach var="option" items="${options}">
			<span><input class="parameter" type="radio" name="${obsName}" value="${option}" title="${title}" data="${data}"> ${option}</span>
		</c:forEach>
	</c:when>
</c:choose>