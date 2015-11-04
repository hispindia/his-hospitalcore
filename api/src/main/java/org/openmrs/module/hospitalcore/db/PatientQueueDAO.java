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

import java.util.List;

import org.openmrs.Concept;
import org.openmrs.ConceptAnswer;
import org.openmrs.Encounter;
import org.openmrs.Obs;
import org.openmrs.Patient;
import org.openmrs.Person;
import org.openmrs.api.APIException;
import org.openmrs.api.db.DAOException;
import org.openmrs.module.hospitalcore.model.OpdPatientQueue;
import org.openmrs.module.hospitalcore.model.OpdPatientQueueLog;
import org.openmrs.module.hospitalcore.model.PatientDrugHistory;
import org.openmrs.module.hospitalcore.model.PatientFamilyHistory;
import org.openmrs.module.hospitalcore.model.PatientMedicalHistory;
import org.openmrs.module.hospitalcore.model.PatientPersonalHistory;
import org.openmrs.module.hospitalcore.model.TriagePatientData;
import org.openmrs.module.hospitalcore.model.TriagePatientQueue;
import org.openmrs.module.hospitalcore.model.TriagePatientQueueLog;
/**
 * <p> Class: PatientQueueDAO </p>
 * <p> Package: org.openmrs.module.hospitalcore.db </p> 
 * <p> Author: Nguyen manh chuyen </p>
 * <p> Update by: Nguyen manh chuyen </p>
 * <p> Version: $1.0 </p>
 * <p> Create date: Feb 16, 2011 12:34:23 PM </p>
 * <p> Update date: Feb 16, 2011 12:34:23 PM </p>
 **/
public interface PatientQueueDAO {
	//opd patient queue
	public OpdPatientQueue saveOpdPatientQueue(OpdPatientQueue opdPatientQueue) throws DAOException;
	public OpdPatientQueue updateOpdPatientQueue(Integer id, String status) throws DAOException;
	public PatientMedicalHistory getPatientHistoryByPatientId(Integer id) throws DAOException;
	public PatientDrugHistory getPatientDrugHistoryByPatientId(Integer id) throws DAOException;
	public PatientFamilyHistory getPatientFamilyHistoryByPatientId(Integer id) throws DAOException;
	public PatientPersonalHistory getPatientPersonalHistoryByPatientId(Integer id) throws DAOException;
	public OpdPatientQueue getOpdPatientQueueById(Integer id) throws DAOException;
	public void deleteOpdPatientQueue(OpdPatientQueue opdPatientQueue) throws DAOException;
	public List<OpdPatientQueue> listOpdPatientQueue(String patientName ,  Integer referralConceptId,String status, int min, int max) throws DAOException;
	public List<TriagePatientQueue> listTriagePatientQueue(String patientName ,  Integer referralConceptId,String status, int min, int max) throws DAOException;
	public Integer countOpdPatientQueue(String patientName , String searchType,Integer referralConceptId,String status) throws DAOException;
	//opd patient queue log
	public OpdPatientQueueLog saveOpdPatientQueueLog(OpdPatientQueueLog opdPatientQueueLog) throws DAOException ;
	public OpdPatientQueueLog getOpdPatientQueueLogById(Integer id) throws DAOException;
	public List<OpdPatientQueue> getAllPatientInQueue() throws DAOException ;
	public OpdPatientQueue getOpdPatientQueue(String patientIdentifier,Integer opdConceptId) throws DAOException;
	public TriagePatientQueue getTriagePatientQueue(String patientIdentifier,Integer triageConceptId) throws DAOException;
	public TriagePatientQueue getTriagePatientQueueById(Integer id) throws APIException;
	public TriagePatientQueue saveTriagePatientQueue(TriagePatientQueue triagePatientQueue) throws DAOException;
	public TriagePatientQueueLog saveTriagePatientQueueLog(TriagePatientQueueLog triagePatientQueueLog) throws APIException ;
	public void deleteTriagePatientQueue(TriagePatientQueue triagePatientQueue) throws APIException;
	public TriagePatientData saveTriagePatientData(TriagePatientData triagePatientData) throws APIException ;
	public PatientMedicalHistory savePatientMedicalHistory(PatientMedicalHistory patientMedicalHistory) throws APIException ;
	public PatientDrugHistory savePatientDrugHistory(PatientDrugHistory patientDrugHistory) throws APIException ;
	public PatientPersonalHistory savePatientPersonalHistory(PatientPersonalHistory patientPersonalHistory) throws APIException ;
	public PatientFamilyHistory savePatientFamilyHistory(PatientFamilyHistory patientFamilyHistory) throws APIException ;
	public ConceptAnswer getConceptAnswer(Concept answerConcept) throws DAOException;
	public Encounter getLastOPDEncounter(Patient patient) throws APIException;
	public OpdPatientQueueLog getOpdPatientQueueLogByEncounter(Encounter encounter) throws APIException;
	public Obs getObservationByPersonConceptAndEncounter(Person person,Concept concept,Encounter encounter) throws APIException;
	public OpdPatientQueueLog getOpdPatientQueueLog(String patientIdentifier,Integer opdConceptId) throws DAOException;
	
	public void updatePatientHistoryByPatientId(PatientMedicalHistory patientMedicalHistory) throws DAOException;
	public void updatePatientDrugHistoryByPatientId(PatientDrugHistory patientDrugHistory) throws DAOException;
	public void updatePatientFamilyHistoryByPatientId (PatientFamilyHistory patientFamilyHistory) throws DAOException;
	public void updatePatientPersonalHistoryByPatientId (PatientPersonalHistory patientPersonalHistory) throws DAOException;
	
	
	public List<Obs> getAllDiagnosis(Integer personId) throws DAOException;
	
	public List<Obs> getAllSymptom(Integer personId) throws DAOException;
	public List<Obs> getAllExamination(Integer personId) throws DAOException;
	public TriagePatientData updateTriagePatientData(TriagePatientData triagePatientData) throws DAOException ;
}

