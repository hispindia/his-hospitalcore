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


package org.openmrs.module.hospitalcore;

import java.util.Date;
import java.util.List;

import org.openmrs.Concept;
import org.openmrs.Order;
import org.openmrs.Role;
import org.openmrs.api.APIException;
import org.openmrs.api.OpenmrsService;
import org.openmrs.module.hospitalcore.model.Lab;
import org.openmrs.module.hospitalcore.model.LabTest;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface LabService extends OpenmrsService {

	/**
	 * LAB
	 */
	public Lab saveLab(Lab lab) throws APIException;
	
	public Lab getLabByName(String name) throws APIException;
	
	public List<Lab> getAllLab() throws APIException;
	
	public List<Lab> getAllActivelab() throws APIException;
	
	public Lab getLabByRole(Role role) throws APIException;
	
	public List<Lab> getLabByRoles(List<Role> roles) throws APIException;
	
	public Lab getLabById(Integer labId) throws APIException;
	
	public void deleteLab(Lab lab) throws APIException;
	
	public Lab getLabByConcept(Concept concept, List<Lab> labs) throws APIException;
	
	/**
	 * LAB TEST
	 */
	
	public LabTest saveLabTest(LabTest labTest) throws APIException;
	
	public LabTest getLabTestById(Integer labTestId) throws APIException;
	
	public LabTest getLabTestByOrder(Order order) throws APIException;
	
	public LabTest getLabTestBySampleNumber(String sampleNumber) throws APIException;
	
	public List<LabTest> getLatestLabTestByDate(Lab lab, Date date) throws APIException;
	
	public String getNextSampleNumber(Lab lab, Date date) throws APIException;
	
	public void deleteLabTest(LabTest labtest) throws APIException;
	
	public void deleteLabTestByOrder(Order order) throws APIException;	
}
