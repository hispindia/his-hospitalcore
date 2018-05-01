/**
 *  Copyright 2010 Society for Health Information Systems Programmes, India (HISP India)
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


package org.openmrs.module.hospitalcore.util;

import org.openmrs.ConceptClass;
import org.openmrs.api.context.Context;

public class ConceptClassUtil {
	private static ConceptClass medicalExaminationClass = null;
	
	/**
	 * Get concept class of medical examination class
	 * @return ConceptClass
	 */
	public static ConceptClass getMedicalExamminationClass(){
		return getConceptClass(medicalExaminationClass, HospitalCoreConstants.PROPERTY_MEDICAL_EXAMINATION);
	}
	
	private static ConceptClass getConceptClass(ConceptClass conceptClass, String keyname){
		if(conceptClass==null){
			String idStr = Context.getAdministrationService().getGlobalProperty(keyname);
			try {
				Integer id = Integer.parseInt(idStr);
				conceptClass = Context.getConceptService().getConceptClass(id);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return conceptClass;
	}
}
