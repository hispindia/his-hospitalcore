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
	<c:when test='${duplicatedFormFound}'>
		<script type='text/javascript'>
			DUPLICATED_FORM = true;
		</script>
		<span style='color:red;'>Duplicated form with the same concept was found!</span>
		<c:forEach var='form' items='${duplicatedForms}'>
			<a href='editForm.form?id=${form.id}'>
				${form.name}
			</a>
		</c:forEach>
	</c:when>
	<c:otherwise>
		Fine!
		<script type='text/javascript'>
			DUPLICATED_FORM = false;
		</script>
	</c:otherwise>
</c:choose>