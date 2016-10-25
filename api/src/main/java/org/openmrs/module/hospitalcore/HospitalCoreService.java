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

import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.sql.Date;
import java.util.List;
import java.util.Set;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.openmrs.Concept;
import org.openmrs.Encounter;
import org.openmrs.EncounterType;
import org.openmrs.Obs;
import org.openmrs.Patient;
import org.openmrs.PersonAttribute;
import org.openmrs.User;
import org.openmrs.api.APIException;
import org.openmrs.api.ConceptService;
import org.openmrs.api.OpenmrsService;
import org.openmrs.module.hospitalcore.model.CoreForm;
import org.openmrs.module.hospitalcore.model.PatientSearch;
import org.springframework.transaction.annotation.Transactional;
import org.xml.sax.SAXException;

@Transactional
public interface HospitalCoreService extends OpenmrsService {

	public List<Obs> listObsGroup(Integer personId, Integer conceptId,
			Integer min, Integer max) throws APIException;

	public EncounterType insertEncounterTypeByKey(String type)
			throws APIException;

	public void creatConceptQuestionAndAnswer(ConceptService conceptService,
			User user, String conceptParent, String... conceptChild)
			throws APIException;

	public Obs createObsGroup(Patient patient, String properyKey);

	public Concept insertConceptUnlessExist(String dataTypeName,
			String conceptClassName, String conceptName) throws APIException;

	public Obs getObsGroup(Patient patient);

	public Obs getObsGroupCurrentDate(Integer personId) throws APIException;

	public void insertSynonym(Concept concept, String name);

	public void insertMapping(Concept concept, String sourceName,
			String sourceCode);

	/**
	 * Insert the concept unless it exists
	 * 
	 * @param dataTypeName
	 *            name of datatype
	 * @param conceptClassName
	 *            name of concept class
	 * @param name
	 *            name of the concept
	 * @param shortname
	 *            shortname of the concept
	 * @param description
	 *            description of the concept
	 * @return the created concept or the existing concept
	 * @throws APIException
	 */
	public Concept insertConcept(String dataTypeName, String conceptClassName,
			String name, String shortname, String description)
			throws APIException;

	/**
	 * Import concepts from XML files.
	 * 
	 * @param diagnosisStream
	 * @param mappingStream
	 * @param synonymStream
	 * @return The number of concepts successfully imported into the system
	 * @throws XPathExpressionException
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	public Integer importConcepts(InputStream diagnosisStream,
			InputStream mappingStream, InputStream synonymStream)
			throws XPathExpressionException, ParserConfigurationException,
			SAXException, IOException;

	/**
	 * Search patients
	 * 
	 * @param nameOrIdentifier
	 * @param gender
	 * @param age
	 * @param rangeAge
	 * @param date
	 * @param rangeDay
	 * @param relativeName
	 * @return
	 * @throws APIException
	 */
	public List<Patient> searchPatient(String nameOrIdentifier, String gender,
			int age, int rangeAge, String date, int rangeDay,
			String relativeName) throws APIException;

	/**
	 * Search patients
	 * 
	 * @param hql
	 * @return
	 */
	public List<Patient> searchPatient(String hql);

	/**
	 * Get patient search result count
	 * 
	 * @param hql
	 * @return
	 */
	public BigInteger getPatientSearchResultCount(String hql);

	/**
	 * Get all attributes of an patient
	 * 
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
	public Encounter getLastVisitEncounter(Patient patient,
			List<EncounterType> types);

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
	 * get Last Visit time
	 * 
	 * @param patientID
	 * @return
	 */
	public java.util.Date getLastVisitTime (int patientID);
	
	//ghanshyam,22-oct-2013,New Requirement #2940 Dealing with dead patient
	public PatientSearch getPatient(int patientID);
	
	
	//New requirement to download patient
	public Set<Patient> getAllEncounterCurrentDate(String date);
	
}
