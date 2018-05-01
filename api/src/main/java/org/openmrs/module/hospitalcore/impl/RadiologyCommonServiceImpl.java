/**
 *  Copyright 2013 Society for Health Information Systems Programmes, India (HISP India)
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
 *  author:ghanshyam
 *  date:10-april-2013
 *  issue:#1302 Radiology record
 **/

package org.openmrs.module.hospitalcore.impl;

import java.util.List;

import org.openmrs.Concept;
import org.openmrs.ConceptAnswer;
import org.openmrs.Patient;
import org.openmrs.api.APIException;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.hospitalcore.RadiologyCommonService;
import org.openmrs.module.hospitalcore.db.RadiologyCommonDAO;
import org.openmrs.module.hospitalcore.model.RadiologyTest;

public class RadiologyCommonServiceImpl extends BaseOpenmrsService implements RadiologyCommonService {

	public RadiologyCommonServiceImpl() {
	}
	
	protected RadiologyCommonDAO dao;

	public void setDao(RadiologyCommonDAO dao) {
		this.dao = dao;
	}
	
	public List<RadiologyTest> getAllTest(Patient patient) throws APIException {
		return dao.getAllTest(patient);
	}
	
	public ConceptAnswer getConceptAnswer(Concept concept) throws APIException {
		return dao.getConceptAnswer(concept);
	}
	
	public List<RadiologyTest> getAllTest(Patient patient,String date) throws APIException {
		return dao.getAllTest(patient,date);
	}
	
	public List<RadiologyTest> getAllTest(Patient patient,String date,Concept concept) throws APIException {
		return dao.getAllTest(patient,date,concept);
	}
	
	public List<RadiologyTest> getAllSubTest(Patient patient,String date,Concept concept) throws APIException {
		return dao.getAllSubTest(patient,date,concept);
	}
}
