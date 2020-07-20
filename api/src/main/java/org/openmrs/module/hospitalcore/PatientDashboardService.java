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
import java.util.Map;
import java.util.Set;

import org.openmrs.Concept;
import org.openmrs.Encounter;
import org.openmrs.EncounterType;
import org.openmrs.Location;
import org.openmrs.Order;
import org.openmrs.Patient;
import org.openmrs.api.APIException;
import org.openmrs.module.hospitalcore.model.Department;
import org.openmrs.module.hospitalcore.model.DepartmentConcept;
import org.openmrs.module.hospitalcore.model.InventoryDrug;
import org.openmrs.module.hospitalcore.model.OpdDrugOrder;
import org.openmrs.module.hospitalcore.model.OpdPatientQueueLog;
import org.openmrs.module.hospitalcore.model.OpdTestOrder;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface PatientDashboardService {

	public List<Concept> searchDiagnosis(String text) throws APIException;

	public List<Concept> searchProcedure(String text) throws APIException;

	public List<Order> getOrders(List<Concept> concepts, Patient patient, Location location, Date orderStartDate)
			throws APIException;

	public List<Concept> getAnswers(Concept labSet) throws APIException;

	public List<Encounter> getEncounter(Patient p, Location loc, EncounterType encType, String date)
			throws APIException;

	public Set<Concept> listDiagnosisByOpd(Integer opdConcept) throws APIException;

	// Department
	public Department createDepartment(Department department) throws APIException;

	public void removeDepartment(Department department) throws APIException;

	public Department getDepartmentById(Integer id) throws APIException;

	public Department getDepartmentByWard(Integer wardId) throws APIException;

	public List<Department> listDepartment(Boolean retired) throws APIException;

	public Department getDepartmentByName(String name) throws APIException;

	// DepartmentConcept
	public DepartmentConcept createDepartmentConcept(DepartmentConcept departmentConcept) throws APIException;

	public DepartmentConcept getByDepartmentAndConcept(Integer departmentId, Integer conceptId) throws APIException;

	public DepartmentConcept getById(Integer id) throws APIException;

	public void removeDepartmentConcept(DepartmentConcept departmentConcept) throws APIException;

	public List<DepartmentConcept> listByDepartment(Integer departmentId, Integer typeConcept) throws APIException;

	public List<Concept> listByDepartmentByWard(Integer wardId, Integer typeConcept) throws APIException;

	public List<Concept> searchInvestigation(String text) throws APIException;

	public List<InventoryDrug> findDrug(String name) throws APIException;

	public OpdDrugOrder saveOrUpdateOpdDrugOrder(OpdDrugOrder opdDrugOrder) throws APIException;

	public OpdTestOrder saveOrUpdateOpdOrder(OpdTestOrder opdTestOrder) throws APIException;

	public OpdPatientQueueLog getOpdPatientQueueLog(Encounter encounter) throws APIException;

	public List<OpdDrugOrder> getOpdDrugOrder(Encounter encounter) throws APIException;

	public List<Map<String, Object>> getPatientDrugDetails(String identifier, String date);

	public List<String> getPatientDrugIssueDates(String identifier);

}
