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

public class AgeMatcher implements Matcher<Patient> {
	private Integer age;
	private Integer range;

	public AgeMatcher(Integer age, Integer range) {
		this.age = age;
		this.range = range;
	}

	public boolean matches(Object patient) {
		if (((Patient) patient).getAge() >= age - range
				&& ((Patient) patient).getAge() <= age + range) {
			return true;
		} else {
			return false;
		}
	}
}
