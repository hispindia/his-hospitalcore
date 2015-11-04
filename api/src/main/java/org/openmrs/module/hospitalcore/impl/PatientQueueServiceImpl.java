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



package org.openmrs.module.hospitalcore.impl;

import java.util.List;

import org.openmrs.Concept;
import org.openmrs.ConceptAnswer;
import org.openmrs.Encounter;
import org.openmrs.Obs;
import org.openmrs.Patient;
import org.openmrs.Person;
import org.openmrs.api.APIException;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.hospitalcore.PatientQueueService;
import org.openmrs.module.hospitalcore.db.PatientQueueDAO;
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
 * <p> Class: PatientQueueServiceImpl </p>
 * <p> Package: org.openmrs.module.hospitalcore.impl </p> 
 * <p> Author: Nguyen manh chuyen </p>
 * <p> Update by: Nguyen manh chuyen </p>
 * <p> Version: $1.0 </p>
 * <p> Create date: Feb 16, 2011 12:36:59 PM </p>
 * <p> Update date: Feb 16, 2011 12:36:59 PM </p>
 **/
public class PatientQueueServiceImpl  extends BaseOpenmrsService implements PatientQueueService{
	
	public PatientQueueServiceImpl() {
	}

	protected PatientQueueDAO dao;
	
	

	public PatientQueueDAO getDao() {
		return dao;
	}

	public void setDao(PatientQueueDAO dao) {
		this.dao = dao;
	}

	public OpdPatientQueue saveOpdPatientQueue(OpdPatientQueue opdPatientQueue)
			throws APIException {
		// TODO Auto-generated method stub
		return dao.saveOpdPatientQueue(opdPatientQueue);
	}

	public OpdPatientQueue updateOpdPatientQueue(Integer id, String status)
			throws APIException {
		// TODO Auto-generated method stub
		return dao.updateOpdPatientQueue(id, status);
	}

	public OpdPatientQueue getOpdPatientQueueById(Integer id)
			throws APIException {
		// TODO Auto-generated method stub
		return dao.getOpdPatientQueueById(id);
	}

	public PatientMedicalHistory getPatientHistoryByPatientId (Integer id)
			throws APIException {
		// TODO Auto-generated method stub
		return dao.getPatientHistoryByPatientId(id);
	}

	public PatientDrugHistory getPatientDrugHistoryByPatientId (Integer id)
			throws APIException {
		// TODO Auto-generated method stub
		return dao.getPatientDrugHistoryByPatientId(id);
	}

	public PatientFamilyHistory getPatientFamilyHistoryByPatientId (Integer id)
			throws APIException {
		// TODO Auto-generated method stub
		return dao.getPatientFamilyHistoryByPatientId(id);
	}
	
	public PatientPersonalHistory getPatientPersonalHistoryByPatientId (Integer id)
			throws APIException {
		// TODO Auto-generated method stub
		return dao.getPatientPersonalHistoryByPatientId(id);
	}

	public void deleteOpdPatientQueue(OpdPatientQueue opdPatientQueue)
			throws APIException {
		// TODO Auto-generated method stub
		dao.deleteOpdPatientQueue(opdPatientQueue);
	}

	public List<OpdPatientQueue> listOpdPatientQueue(String patientName,
			Integer referralConceptId, String status, int min, int max)
			throws APIException {
		// TODO Auto-generated method stub
		return dao.listOpdPatientQueue(patientName, referralConceptId, status, min, max);
	}
	
	public List<TriagePatientQueue> listTriagePatientQueue(String patientName,
			Integer referralConceptId, String status, int min, int max)
			throws APIException {
		// TODO Auto-generated method stub
		return dao.listTriagePatientQueue(patientName, referralConceptId, status, min, max);
	}

	public Integer countOpdPatientQueue(String patientName, String searchType,
			Integer referralConceptId, String status) throws APIException {
		// TODO Auto-generated method stub
		return dao.countOpdPatientQueue(patientName, searchType,referralConceptId, status);
	}

	public OpdPatientQueueLog saveOpdPatientQueueLog(
			OpdPatientQueueLog opdPatientQueueLog) throws APIException {
		// TODO Auto-generated method stub
		return dao.saveOpdPatientQueueLog(opdPatientQueueLog);
	}

	public OpdPatientQueueLog getOpdPatientQueueLogById(Integer id)
			throws APIException {
		// TODO Auto-generated method stub
		return dao.getOpdPatientQueueLogById(id);
	}

	public OpdPatientQueueLog copyTo(OpdPatientQueue opdPatientQueue){
		OpdPatientQueueLog opdPatientQueueLog = new OpdPatientQueueLog();
		opdPatientQueueLog.setBirthDate(opdPatientQueue.getBirthDate());
		opdPatientQueueLog.setCreatedOn(opdPatientQueue.getCreatedOn());
		opdPatientQueueLog.setOpdConcept(opdPatientQueue.getOpdConcept());
		opdPatientQueueLog.setOpdConceptName(opdPatientQueue.getOpdConceptName());
		opdPatientQueueLog.setPatientIdentifier(opdPatientQueue.getPatientIdentifier());
		opdPatientQueueLog.setPatient(opdPatientQueue.getPatient());
		opdPatientQueueLog.setPatientName(opdPatientQueue.getPatientName());
		opdPatientQueueLog.setReferralConcept(opdPatientQueue.getReferralConcept());
		opdPatientQueueLog.setReferralConceptName(opdPatientQueue.getReferralConceptName());
		opdPatientQueueLog.setSex(opdPatientQueue.getSex());
		opdPatientQueueLog.setStatus(opdPatientQueue.getStatus());
		opdPatientQueueLog.setUser(opdPatientQueue.getUser());
		return opdPatientQueueLog;
	}
	
	

	public OpdPatientQueue getOpdPatientQueue(String patientIdentifier,Integer opdConceptId)throws APIException {
		// TODO Auto-generated method stub
		return dao.getOpdPatientQueue(patientIdentifier,opdConceptId);
	}
	
	public TriagePatientQueue getTriagePatientQueue(String patientIdentifier,Integer triageConceptId)throws APIException {
		// TODO Auto-generated method stub
		return dao.getTriagePatientQueue(patientIdentifier,triageConceptId);
	}
	
	public TriagePatientQueue getTriagePatientQueueById(Integer id)throws APIException {
		// TODO Auto-generated method stub
		return dao.getTriagePatientQueueById(id);
	}
	
	public TriagePatientQueue saveTriagePatientQueue(TriagePatientQueue triagePatientQueue)throws APIException {
       // TODO Auto-generated method stub
       return dao.saveTriagePatientQueue(triagePatientQueue);
    }
	
	public TriagePatientQueueLog saveTriagePatientQueueLog(TriagePatientQueueLog triagePatientQueueLog)throws APIException {
	       // TODO Auto-generated method stub
	       return dao.saveTriagePatientQueueLog(triagePatientQueueLog);
	}
	
	public void deleteTriagePatientQueue(TriagePatientQueue triagePatientQueue)throws APIException {
    // TODO Auto-generated method stub
    dao.deleteTriagePatientQueue(triagePatientQueue);
    }
	
	public TriagePatientData saveTriagePatientData(TriagePatientData triagePatientData)throws APIException {
	       // TODO Auto-generated method stub
	       return dao.saveTriagePatientData(triagePatientData);
	}

	public List<OpdPatientQueue> getAllPatientInQueue() throws APIException {
		return dao.getAllPatientInQueue();
	}
	
	public ConceptAnswer getConceptAnswer(Concept answerConcept)throws APIException {
	       // TODO Auto-generated method stub
	       return dao.getConceptAnswer(answerConcept);
	}
	
	public Encounter getLastOPDEncounter(Patient patient) {
		return dao.getLastOPDEncounter(patient);
	}
	
	public OpdPatientQueueLog getOpdPatientQueueLogByEncounter(Encounter encounter) {
		return dao.getOpdPatientQueueLogByEncounter(encounter);
	}
	
	public Obs getObservationByPersonConceptAndEncounter(Person person,Concept concept,Encounter encounter) {
		return dao.getObservationByPersonConceptAndEncounter(person,concept,encounter);
	}
	
	public OpdPatientQueueLog getOpdPatientQueueLog(String patientIdentifier,Integer opdConceptId)throws APIException {
		// TODO Auto-generated method stub
		return dao.getOpdPatientQueueLog(patientIdentifier,opdConceptId);
	}

	public PatientMedicalHistory savePatientMedicalHistory(
			PatientMedicalHistory patientMedicalHistory) throws APIException {
		// TODO Auto-generated method stub
		return dao.savePatientMedicalHistory(patientMedicalHistory);
	}
	
	public PatientDrugHistory savePatientDrugHistory(
			PatientDrugHistory patientDrugHistory) throws APIException {
		// TODO Auto-generated method stub
		return dao.savePatientDrugHistory(patientDrugHistory);
	}	

	public PatientPersonalHistory savePatientPersonalHistory(
			PatientPersonalHistory patientPersonalHistory) throws APIException {
		// TODO Auto-generated method stub
		return dao.savePatientPersonalHistory(patientPersonalHistory);
	}	

	public PatientFamilyHistory savePatientFamilyHistory(
			PatientFamilyHistory patientFamilyHistory) throws APIException {
		// TODO Auto-generated method stub
		return dao.savePatientFamilyHistory(patientFamilyHistory);
	}	
	
	
	public void updatePatientHistoryByPatientId (PatientMedicalHistory patientMedicalHistory)
		throws APIException {
		// TODO Auto-generated method stub
		dao.updatePatientHistoryByPatientId(patientMedicalHistory);
	}
	
	public void updatePatientDrugHistoryByPatientId (PatientDrugHistory patientDrugHistory)
	throws APIException {
	// TODO Auto-generated method stub
	dao.updatePatientDrugHistoryByPatientId(patientDrugHistory);
	}
	
	public void updatePatientFamilyHistoryByPatientId (PatientFamilyHistory patientFamilyHistory)
	throws APIException {
	// TODO Auto-generated method stub
	dao.updatePatientFamilyHistoryByPatientId(patientFamilyHistory);
	}

	public void updatePatientPersonalHistoryByPatientId (PatientPersonalHistory patientPersonalHistory)
	throws APIException {
	// TODO Auto-generated method stub
	dao.updatePatientPersonalHistoryByPatientId(patientPersonalHistory);
	}
	
	
	public List<Obs> getAllDiagnosis(Integer personId)
	throws APIException{
		return dao.getAllDiagnosis(personId);
	}
	
	public List<Obs> getAllSymptom(Integer personId)
	throws APIException{
		return dao.getAllSymptom(personId);
	}
	
	public TriagePatientData updateTriagePatientData(TriagePatientData triagePatientData)throws APIException {
	       // TODO Auto-generated method stub
	       return dao.updateTriagePatientData(triagePatientData);
	    }

	public List<Obs> getAllExamination(Integer personId) throws APIException {
		// TODO Auto-generated method stub
		return dao.getAllExamination(personId);
	}
}
