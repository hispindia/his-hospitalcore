/**
 *  Copyright 2010 Health Information Systems Project of India
 *
 *  This file is part of Hospital-core module.
 *
 *  Hospital-core module is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.

 *  Hospital-core module is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with Hospital-core module.  If not, see <http://www.gnu.org/licenses/>.
 *
 **/

package org.openmrs.module.hospitalcore.matcher;

import org.openmrs.Patient;
import org.openmrs.PersonAttribute;
import org.openmrs.api.context.Context;

public class RelativeNameMatcher implements Matcher<Patient> {
	private String[] parts;

	public RelativeNameMatcher(String relativeName) {
		relativeName = relativeName.toLowerCase();
		relativeName = relativeName.replaceAll("'", " ");
		parts = relativeName.split(" ");
	}

	public boolean matches(Object obj) {
		Patient patient = (Patient) obj;
		PersonAttribute pat = patient.getAttribute(Context.getPersonService()
				.getPersonAttributeTypeByName("Father/Husband Name"));
		String relativeName = pat.getValue().toLowerCase();
		for (String part : parts) {
			if (relativeName.contains(part))
				return true;
		}
		return false;
	}
}
