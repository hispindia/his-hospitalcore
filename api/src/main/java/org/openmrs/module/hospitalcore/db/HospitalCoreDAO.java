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

import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.openmrs.Concept;
import org.openmrs.Encounter;
import org.openmrs.EncounterType;
import org.openmrs.Obs;
import org.openmrs.Patient;
import org.openmrs.PersonAttribute;
import org.openmrs.api.db.DAOException;
import org.openmrs.module.hospitalcore.concept.ConceptModel;
import org.openmrs.module.hospitalcore.model.CoreForm;
import org.openmrs.module.hospitalcore.model.PatientSearch;

public interface HospitalCoreDAO {

	public List<Obs> listObsGroup(Integer personId, Integer conceptId, Integer min, Integer max) throws DAOException;
	public Obs getObsGroupCurrentDate(Integer personId, Integer conceptId) throws DAOException;	
	public Integer buildConcepts(List<ConceptModel> conceptModels);
	public List<Patient> searchPatient(String nameOrIdentifier,String gender, int age , int rangeAge, String date, int rangeDay,String relativeName) throws DAOException;
	
	/**
	 * Search patients
	 * @param hql
	 * @return
	 */
	public List<Patient> searchPatient(String hql);
	
	/**
	 * Get patient search result count
	 * @param hql
	 * @return
	 */
	public BigInteger getPatientSearchResultCount(String hql);
	
	/** 
	 * Get all attributes of an patient
	 * @param patientId
	 * @return
	 */
	public List<PersonAttribute> getPersonAttributes(Integer patientId);
	
	/**
	 * Get last visit encounter
	 * @param patient
	 * @param types
	 * @return
	 */
	public Encounter getLastVisitEncounter(Patient patient, List<EncounterType> types);
	
	/**
	 * Save core form
	 * @param form
	 * @return
	 */
	public CoreForm saveCoreForm(CoreForm form);

	/**
	 * Get core form by id
	 * @param id
	 * @return
	 */
	public CoreForm getCoreForm(Integer id);

	/**
	 * Get core forms by name
	 * @param conceptName
	 * @return
	 */
	public List<CoreForm> getCoreForms(String conceptName);

	/**
	 * Get all core forms
	 * @return
	 */
	public List<CoreForm> getCoreForms();

	/**
	 * Delete core form
	 * @param form
	 */
	public void deleteCoreForm(CoreForm form);
	
	/**
	 * Save patientSearch
	 * @param patientSearch
	 * @return
	 */
	public PatientSearch savePatientSearch(PatientSearch patientSearch);
	
	/**
	 * 
	 * Auto generated method comment
	 * 
	 * @param patientID
	 * @return
	 */
	public java.util.Date getLastVisitTime (Patient patientID);
	
	//ghanshyam,22-oct-2013,New Requirement #2940 Dealing with dead patient
	public PatientSearch getPatient(int patientID);
	
	//public List<Patient> getAllEncounterCurrentDate(String date,Set<EncounterType> encounterTypes);
	
	public Set<Encounter> getEncountersByPatientAndDate(String date,Set<EncounterType> encounterTypes);
	public Set<Encounter>getEncountersByPatientAndDateFromObs(String date);
	public List<Obs> getObsInstanceForDiagnosis(Encounter encounter,Concept concept) throws DAOException;
}
