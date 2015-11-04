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

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.openmrs.Concept;
import org.openmrs.ConceptAnswer;
import org.openmrs.ConceptClass;
import org.openmrs.Encounter;
import org.openmrs.EncounterType;
import org.openmrs.Location;
import org.openmrs.Order;
import org.openmrs.Patient;
import org.openmrs.api.APIException;
import org.openmrs.api.context.Context;
import org.openmrs.module.hospitalcore.PatientDashboardService;
import org.openmrs.module.hospitalcore.db.PatientDashboardDAO;
import org.openmrs.module.hospitalcore.model.Answer;
import org.openmrs.module.hospitalcore.model.Department;
import org.openmrs.module.hospitalcore.model.DepartmentConcept;
import org.openmrs.module.hospitalcore.model.Examination;
import org.openmrs.module.hospitalcore.model.InventoryDrug;
import org.openmrs.module.hospitalcore.model.OpdDrugOrder;
import org.openmrs.module.hospitalcore.model.OpdPatientQueueLog;
import org.openmrs.module.hospitalcore.model.OpdTestOrder;
import org.openmrs.module.hospitalcore.model.PatientServiceBill;
import org.openmrs.module.hospitalcore.model.Question;
import org.openmrs.module.hospitalcore.model.Symptom;
import org.openmrs.module.hospitalcore.model.TriagePatientData;
import org.openmrs.module.hospitalcore.util.PatientDashboardConstants;

public class PatientDashboardServiceImpl implements PatientDashboardService {

	public PatientDashboardServiceImpl(){
    }
    
    protected PatientDashboardDAO dao;
	
	public void setDao(PatientDashboardDAO dao) {
		this.dao = dao;
	}
	
	public List<Concept> searchSymptom(String text) throws APIException {
		ConceptClass cc =  Context.getConceptService().getConceptClassByName(PatientDashboardConstants.CONCEPT_CLASS_NAME_SYMPTOM);
		return dao.searchConceptsByNameAndClass(text, cc);
	}
	//Examination
	public List<Concept> searchExamination(String text) throws APIException {
		// TODO Auto-generated method stub
		ConceptClass cc =  Context.getConceptService().getConceptClassByName(PatientDashboardConstants.CONCEPT_CLASS_NAME_EXAMINATION);
		return dao.searchConceptsByNameAndClass(text, cc);
	}
	public List<Concept> searchDiagnosis(String text) throws APIException {
		ConceptClass cc =  Context.getConceptService().getConceptClassByName(PatientDashboardConstants.CONCEPT_CLASS_NAME_DIAGNOSIS);
		return dao.searchConceptsByNameAndClass(text, cc);
	}
	
	public List<Concept> getAnswers(Concept labSet)  throws APIException{
        List<Concept> conceptList = new ArrayList<Concept>();
        if (labSet.getDatatype().isCoded()) {
            if (!labSet.getAnswers().isEmpty()) {
                List<ConceptAnswer> conceptAnswers = new ArrayList<ConceptAnswer>(labSet.getAnswers());
                for (int count = 0; count < conceptAnswers.size(); count++) {
                    Concept conceptAnsName = conceptAnswers.get(count).getAnswerConcept();
                    conceptList.add(conceptAnsName);
                }
            }
        }
        return conceptList;
    }
	public List<Order> getOrders(List<Concept> concepts,  Patient patient, Location location, Date orderStartDate) throws APIException{
		return dao.getOrders(concepts, patient, location, orderStartDate);
		
	}
	public List<Concept> searchProcedure(String text) throws APIException {
		ConceptClass cc =  Context.getConceptService().getConceptClassByName(PatientDashboardConstants.CONCEPT_CLASS_NAME_PROCEDURE);
		return dao.searchConceptsByNameAndClass(text, cc);
	}

	public List<Encounter> getEncounter(Patient p, Location loc,
			EncounterType encType, String date) {
		return dao.getEncounter(p, loc, encType, date);
	}
	public Set<Concept> listDiagnosisByOpd(Integer opdConcept) throws APIException {
		Set<Concept> listDiagnosis = new HashSet<Concept>();
		Concept concept = Context.getConceptService().getConcept(opdConcept);
		if(concept != null && concept.getAnswers() != null && !concept.getAnswers().isEmpty()){
			Concept diagnosisC = null;
			for(ConceptAnswer c : concept.getAnswers()){
				if("diagnosis".equalsIgnoreCase(c.getAnswerConcept().getConceptClass().getName())){
					diagnosisC = c.getAnswerConcept();
					break;
				}
			}
			
			//OPD only one concept have class is diagnosis, get default one concept have diagnosis
			if(diagnosisC == null){
				return null;
			}
			// get answer of OPD
			if(diagnosisC.getAnswers() != null && !diagnosisC.getAnswers().isEmpty()){
				for(ConceptAnswer c : diagnosisC.getAnswers()){
					//
					if(c.getAnswerConcept() != null && c.getAnswerConcept().getAnswers() != null && !c.getAnswerConcept().getAnswers().isEmpty()){
						for(ConceptAnswer cInner : c.getAnswerConcept().getAnswers())
						{
							if(cInner.getAnswerConcept().getConceptClass() != null && "diagnosis".equalsIgnoreCase(cInner.getAnswerConcept().getConceptClass().getName())){
								listDiagnosis.add(cInner.getAnswerConcept());
							}
						}
					}else{
						
						if(c.getAnswerConcept().getConceptClass() != null && "diagnosis".equalsIgnoreCase(c.getAnswerConcept().getConceptClass().getName()))
						{
							listDiagnosis.add(c.getAnswerConcept());
						}
					}
				}
			}
		}
		return listDiagnosis;
	}
	
	//Department
	public Department createDepartment(Department department) throws APIException{
		return dao.createDepartment(department);
	}
	public void removeDepartment(Department department) throws APIException{
		dao.removeDepartment(department);
	}
	public Department getDepartmentById(Integer id) throws APIException{
		return dao.getDepartmentById(id);
	}
	public Department getDepartmentByWard(Integer wardId) throws APIException{
		return dao.getDepartmentByWard(wardId);
	}
	public List<Department> listDepartment(Boolean retired) throws APIException{
		return dao.listDepartment(retired);
	}
	public Department getDepartmentByName(String name) throws APIException{
		return dao.getDepartmentByName(name);
	}
	//DepartmentConcept
	public DepartmentConcept createDepartmentConcept(DepartmentConcept departmentConcept) throws APIException{
		return dao.createDepartmentConcept(departmentConcept);
	}
	public DepartmentConcept getByDepartmentAndConcept(Integer departmentId, Integer conceptId) throws APIException{
		return dao.getByDepartmentAndConcept(departmentId, conceptId);
	}
	public DepartmentConcept getById(Integer id) throws APIException{
		return dao.getById(id);
	}
	public void removeDepartmentConcept(DepartmentConcept departmentConcept) throws APIException{
		dao.removeDepartmentConcept(departmentConcept);
	}
	public List<DepartmentConcept> listByDepartment(Integer departmentId, Integer typeConcept) throws APIException{
		return dao.listByDepartment(departmentId,typeConcept);
	}
	public List<Concept> listByDepartmentByWard(Integer wardId,
			Integer typeConcept) throws APIException {
		return dao.listByDepartmentByWard(wardId, typeConcept);
	}
	//ghanshyam 1-june-2013 New Requirement #1633 User must be able to send investigation orders from dashboard to billing
	public List<Concept> searchInvestigation(String text) throws APIException {
		return dao.searchInvestigation(text);
	}
	public OpdTestOrder saveOrUpdateOpdOrder(OpdTestOrder opdTestOrder) throws APIException {
		return dao.saveOrUpdateOpdOrder(opdTestOrder);
	}
	
	//ghanshyam 12-june-2013 New Requirement #1635 User should be able to send pharmacy orders to issue drugs to a patient from dashboard
	public List<Concept> searchDrug(String text) throws APIException {
		ConceptClass cc =  Context.getConceptService().getConceptClassByName(PatientDashboardConstants.CONCEPT_CLASS_NAME_DRUG);
		return dao.searchConceptsByNameAndClass(text, cc);
	}
	
	public OpdDrugOrder saveOrUpdateOpdDrugOrder(OpdDrugOrder opdDrugOrder) throws APIException {
		return dao.saveOrUpdateOpdDrugOrder(opdDrugOrder);
	}
	
	public List<InventoryDrug> findDrug(String name) throws APIException {
		return dao.findDrug(name);
	}
	public Symptom saveSymptom(Symptom symptom) throws APIException {
		return dao.saveSymptom(symptom);
	}
	public Question saveQuestion(Question question) throws APIException {
		return dao.saveQuestion(question);
	}
	public Answer saveAnswer(Answer answer) throws APIException {
		return dao.saveAnswer(answer);
	}
	public OpdPatientQueueLog getOpdPatientQueueLog(Encounter encounter) {
		return dao.getOpdPatientQueueLog(encounter);
	}
	public List<Symptom> getSymptom(Encounter encounter) {
		return dao.getSymptom(encounter);
	}
	public List<Question> getQuestion(Symptom symptom) {
		return dao.getQuestion(symptom);
	}
	public Answer getAnswer(Question question) {
		return dao.getAnswer(question);
	}
	public List<OpdDrugOrder> getOpdDrugOrder(Encounter encounter) {
		return dao.getOpdDrugOrder(encounter);
	}
	public TriagePatientData getTriagePatientData(Integer triageDataId) {
		return dao.getTriagePatientData(triageDataId);
	}
	
	public TriagePatientData getTriagePatientDataFromEncounter(Integer encounterOpd) {
		return dao.getTriagePatientDataFromEncounter(encounterOpd);
	}

	public Examination saveExamination(Examination examination)
			throws APIException {
		// TODO Auto-generated method stub
		return dao.saveExamination(examination);
	}

	public List<Examination> getExamination(Encounter encounters)
			throws APIException {
		// TODO Auto-generated method stub
		return dao.getExamination(encounters);
	}

	public List<Question> getQuestion(Examination examination)
			throws APIException {
		// TODO Auto-generated method stub
		return dao.getQuestion(examination);
	}

	
}

