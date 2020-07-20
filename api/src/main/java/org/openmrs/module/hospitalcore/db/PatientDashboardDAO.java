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

package org.openmrs.module.hospitalcore.db;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.openmrs.Concept;
import org.openmrs.ConceptClass;
import org.openmrs.Encounter;
import org.openmrs.EncounterType;
import org.openmrs.Location;
import org.openmrs.Order;
import org.openmrs.Patient;
import org.openmrs.api.db.DAOException;
import org.openmrs.module.hospitalcore.model.Department;
import org.openmrs.module.hospitalcore.model.DepartmentConcept;
import org.openmrs.module.hospitalcore.model.InventoryDrug;
import org.openmrs.module.hospitalcore.model.OpdDrugOrder;
import org.openmrs.module.hospitalcore.model.OpdPatientQueueLog;
import org.openmrs.module.hospitalcore.model.OpdTestOrder;

public interface PatientDashboardDAO {
	public List<Order> getOrders(List<Concept> concepts, Patient patient, Location location, Date orderStartDate)
			throws DAOException;

	public List<Concept> searchConceptsByNameAndClass(String text, ConceptClass clazz) throws DAOException;

	public List<Encounter> getEncounter(Patient p, Location loc, EncounterType encType, String date)
			throws DAOException;

	// Department
	public Department createDepartment(Department department) throws DAOException;

	public void removeDepartment(Department department) throws DAOException;

	public Department getDepartmentById(Integer id) throws DAOException;

	public Department getDepartmentByWard(Integer wardId) throws DAOException;

	public List<Department> listDepartment(Boolean retired) throws DAOException;

	public Department getDepartmentByName(String name) throws DAOException;

	// DepartmentConcept
	public DepartmentConcept createDepartmentConcept(DepartmentConcept departmentConcept) throws DAOException;

	public DepartmentConcept getByDepartmentAndConcept(Integer departmentId, Integer conceptId) throws DAOException;

	public DepartmentConcept getById(Integer id) throws DAOException;

	public void removeDepartmentConcept(DepartmentConcept departmentConcept) throws DAOException;

	public List<DepartmentConcept> listByDepartment(Integer departmentId, Integer typeConcept) throws DAOException;

	public List<Concept> listByDepartmentByWard(Integer wardId, Integer typeConcept) throws DAOException;

	public List<Concept> searchInvestigation(String text) throws DAOException;

	public List<InventoryDrug> findDrug(String name) throws DAOException;

	public OpdDrugOrder saveOrUpdateOpdDrugOrder(OpdDrugOrder opdDrugOrder) throws DAOException;

	public OpdTestOrder saveOrUpdateOpdOrder(OpdTestOrder opdTestOrder) throws DAOException;

	public OpdPatientQueueLog getOpdPatientQueueLog(Encounter encounter) throws DAOException;

	public List<OpdDrugOrder> getOpdDrugOrder(Encounter encounter) throws DAOException;

	public List<Map<String, Object>> getPatientDrugDetails(String identifier, String date);

	public List<String> getPatientDrugIssueDates(String identifier);
}
