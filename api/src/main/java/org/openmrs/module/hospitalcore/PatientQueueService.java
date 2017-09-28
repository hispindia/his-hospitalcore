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

import java.util.List;



//New Requirement "Editable Dashboard" //
import org.openmrs.Concept;
import org.openmrs.Encounter;
import org.openmrs.Obs;
import org.openmrs.Patient;
import org.openmrs.Person;
import org.openmrs.api.APIException;
import org.openmrs.api.OpenmrsService;
import org.openmrs.module.hospitalcore.model.OpdPatientQueue;
import org.openmrs.module.hospitalcore.model.OpdPatientQueueLog;
import org.openmrs.module.hospitalcore.model.PatientDrugHistory;
import org.openmrs.module.hospitalcore.model.PatientFamilyHistory;
import org.openmrs.module.hospitalcore.model.PatientPersonalHistory;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p> Class: PatientQueueService </p>
 * <p> Package: org.openmrs.module.hospitalcore </p> 
 * <p> Author: Nguyen manh chuyen </p>
 * <p> Update by: Nguyen manh chuyen </p>
 * <p> Version: $1.0 </p>
 * <p> Create date: Feb 16, 2011 12:36:28 PM </p>
 * <p> Update date: Feb 16, 2011 12:36:28 PM </p>
 **/
@Transactional
public interface PatientQueueService extends OpenmrsService {
	//opd patient queue
	public OpdPatientQueue saveOpdPatientQueue(OpdPatientQueue opdPatientQueue) throws APIException;
	public OpdPatientQueue updateOpdPatientQueue(Integer id, String status) throws APIException;
	public OpdPatientQueue getOpdPatientQueueById(Integer id) throws APIException;
	public void deleteOpdPatientQueue(OpdPatientQueue opdPatientQueue) throws APIException;
	public List<OpdPatientQueue> listOpdPatientQueue(String patientName ,Integer referralConceptId,String status, int min, int max) throws APIException;
	public Integer countOpdPatientQueue(String patientName , String searchType,Integer referralConceptId,String status) throws APIException;
	//opd patient queue log
	public OpdPatientQueueLog saveOpdPatientQueueLog(OpdPatientQueueLog opdPatientQueueLog) throws APIException ;
	public OpdPatientQueueLog getOpdPatientQueueLogById(Integer id) throws APIException;
	public List<OpdPatientQueue> getAllPatientInQueue() throws APIException ;
	public OpdPatientQueueLog copyTo(OpdPatientQueue opdPatientQueue)throws APIException ;
	public OpdPatientQueue getOpdPatientQueue(String patientIdentifier,Integer opdConceptId) throws APIException;
	//New Requirement "Editable Dashboard"//
	public Encounter getLastOPDEncounter(Patient patient) throws APIException;
	public OpdPatientQueueLog getOpdPatientQueueLogByEncounter(Encounter enc) throws APIException;
	public Obs getObservationByPersonConceptAndEncounter(Person person,
			Concept concept, Encounter enc)throws APIException;
	//Symptom
	public List<Obs> getAllSymptom(Integer personId) throws APIException;
	public List<Obs> getAllDiagnosis(Integer personId) throws APIException;
    //patient history
	public PatientDrugHistory getPatientDrugHistoryByPatientId (Integer id) throws APIException;
	public PatientFamilyHistory getPatientFamilyHistoryByPatientId (Integer id) throws APIException;
	public PatientPersonalHistory getPatientPersonalHistoryByPatientId (Integer id) throws APIException;
	public PatientDrugHistory savePatientDrugHistory(PatientDrugHistory patientDrugHistory) throws APIException ;
	public PatientPersonalHistory savePatientPersonalHistory(PatientPersonalHistory patientPersonalHistory) throws APIException ;
	public PatientFamilyHistory savePatientFamilyHistory(PatientFamilyHistory patientFamilyHistory) throws APIException ;
	public void updatePatientDrugHistoryByPatientId (PatientDrugHistory patientDrugHistory) throws APIException;
	public void updatePatientFamilyHistoryByPatientId (PatientFamilyHistory patientFamilyHistory) throws APIException;
	public void updatePatientPersonalHistoryByPatientId (PatientPersonalHistory patientPersonalHistory) throws APIException;
	public OpdPatientQueueLog getOpdPatientQueueLog(String patientIdentifier,Integer opdConceptId) throws APIException;
	public OpdPatientQueue getOpdPatientQueueByPatientId(Patient patient) throws APIException;
	public OpdPatientQueueLog getOpdPatientQueueLogByPatientId(Patient patient) throws APIException;
}
