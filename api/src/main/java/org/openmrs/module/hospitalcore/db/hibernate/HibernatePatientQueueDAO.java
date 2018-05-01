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


package org.openmrs.module.hospitalcore.db.hibernate;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
//New Requirement "Editable Dashboard" //
import java.util.concurrent.TimeUnit;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.openmrs.Concept;
import org.openmrs.Encounter;
import org.openmrs.Obs;
import org.openmrs.Patient;
import org.openmrs.Person;
import org.openmrs.api.APIException;
import org.openmrs.api.context.Context;
import org.openmrs.api.db.DAOException;
import org.openmrs.module.hospitalcore.db.PatientQueueDAO;
import org.openmrs.module.hospitalcore.model.OpdPatientQueue;
import org.openmrs.module.hospitalcore.model.OpdPatientQueueLog;
import org.openmrs.module.hospitalcore.model.PatientDrugHistory;
import org.openmrs.module.hospitalcore.model.PatientFamilyHistory;
import org.openmrs.module.hospitalcore.model.PatientPersonalHistory;

public class HibernatePatientQueueDAO implements PatientQueueDAO {
	SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	SimpleDateFormat formatterExt = new SimpleDateFormat("dd/MM/yyyy");
	//New Requirement "Editable Dashboard"//
	SimpleDateFormat formatter1 = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
	/**
	 * Hibernate session factory
	 */
	private SessionFactory sessionFactory;
	
	/**
	 * Set session factory
	 * 
	 * @param sessionFactory
	 */
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	public OpdPatientQueue saveOpdPatientQueue(OpdPatientQueue opdPatientQueue) throws DAOException {
		return (OpdPatientQueue) sessionFactory.getCurrentSession().merge(opdPatientQueue);
	}
	
	public OpdPatientQueue updateOpdPatientQueue(Integer id, String status) throws DAOException {
		OpdPatientQueue opdPatientQueue = getOpdPatientQueueById(id);
		opdPatientQueue.setStatus(status);
		return (OpdPatientQueue) sessionFactory.getCurrentSession().merge(opdPatientQueue);
	}
	
	public OpdPatientQueue getOpdPatientQueueById(Integer id) throws DAOException {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(OpdPatientQueue.class);
		criteria.add(Restrictions.eq("id", id));
		OpdPatientQueue opdPatientQueue = (OpdPatientQueue) criteria.uniqueResult();
		return opdPatientQueue;
	}
	
	public OpdPatientQueue getOpdPatientQueue(String patientIdentifier,Integer opdConceptId) throws DAOException {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(OpdPatientQueue.class, "queue")
				.createAlias("queue.opdConcept", "opdConcept");
		criteria.add(Restrictions.eq("queue.patientIdentifier", patientIdentifier));
		criteria.add(Restrictions.eq("opdConcept.conceptId", opdConceptId));
		String date = formatterExt.format(new Date());
		String startFromDate = date + " 00:00:00";
		String endFromDate = date + " 23:59:59";
		try {
			criteria.add(Restrictions.and(Restrictions.ge(
					"queue.createdOn", formatter.parse(startFromDate)), Restrictions.le(
					"queue.createdOn", formatter.parse(endFromDate))));
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error convert date: "+ e.toString());
			e.printStackTrace();
		}
		criteria.addOrder(Order.desc("queue.createdOn"));
		
		List<OpdPatientQueue> list = criteria.list();
		return CollectionUtils.isNotEmpty(list) ? list.get(0) : null;
	}
	
	public void deleteOpdPatientQueue(OpdPatientQueue opdPatientQueue) throws DAOException {
		sessionFactory.getCurrentSession().delete(opdPatientQueue);
	}
	
	@SuppressWarnings("unchecked")
	public List<OpdPatientQueue> listOpdPatientQueue(String searchText ,  Integer conceptId,String status, int min, int max) throws DAOException{
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(OpdPatientQueue.class,"opdPatientQueue");
		if(!StringUtils.isBlank(searchText)){
	    	criteria.add(Restrictions.or(Restrictions.like("opdPatientQueue.patientIdentifier",  "%"+searchText+"%"),Restrictions.like("opdPatientQueue.patientName",  "%"+searchText+"%")));
		}
		if(conceptId != null && conceptId > 0){
			criteria.createAlias( "opdPatientQueue.opdConcept","opdConcept");
			criteria.add(Restrictions.eq("opdConcept.conceptId", conceptId));
		}
		if(!StringUtils.isBlank(status)){
			criteria.add(Restrictions.eq("opdPatientQueue.status", status));
		}
		//only get data if that's current date
		//we need this because maybe cron-job not work normal
		String date = formatterExt.format(new Date());
		String startFromDate = date + " 00:00:00";
		String endFromDate = date + " 23:59:59";
		try {
			criteria.add(Restrictions.and(Restrictions.ge(
					"opdPatientQueue.createdOn", formatter.parse(startFromDate)), Restrictions.le(
					"opdPatientQueue.createdOn", formatter.parse(endFromDate))));
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error convert date: "+ e.toString());
			e.printStackTrace();
		}
		criteria.addOrder(Order.asc("opdPatientQueue.createdOn"));
		if(max > 0){
			criteria.setFirstResult(min).setMaxResults(max);
		}
		 List<OpdPatientQueue> list =  criteria.list();

		return list;
	}
	
	public Integer countOpdPatientQueue(String patientName , String searchType,Integer conceptId,String status) throws DAOException{
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(OpdPatientQueue.class,"opdPatientQueue");
		if(!StringUtils.isBlank(patientName)){
			criteria.add(Restrictions.like("opdPatientQueue.patientName",  "%"+patientName+"%"));
		}
		if(conceptId != null){
			criteria.createAlias("opdPatientQueue.opdConcept", "opdConcept");
			criteria.add(Restrictions.eq("opdConcept.conceptId", conceptId));
		}
		if(!StringUtils.isBlank(status)){
			criteria.add(Restrictions.eq("opdPatientQueue.status", status));
		}
		//only get data if that's current date
		//we need this because maybe cron-job not work normal
		String date = formatterExt.format(new Date());
		String startFromDate = date + " 00:00:00";
		String endFromDate = date + " 23:59:59";
		try {
			criteria.add(Restrictions.and(Restrictions.ge(
					"opdPatientQueue.createdOn", formatter.parse(startFromDate)), Restrictions.le(
					"opdPatientQueue.createdOn", formatter.parse(endFromDate))));
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error convert date: "+ e.toString());
			e.printStackTrace();
		}
		Number rs =  (Number) criteria.setProjection( Projections.rowCount() ).uniqueResult();
		return rs != null ? rs.intValue() : 0;
	}
	
	//patient queue log
	public OpdPatientQueueLog saveOpdPatientQueueLog(OpdPatientQueueLog opdPatientQueueLog) throws DAOException {
		return (OpdPatientQueueLog) sessionFactory.getCurrentSession().merge(opdPatientQueueLog);
	}
	
	public OpdPatientQueueLog getOpdPatientQueueLogById(Integer id) throws DAOException {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(OpdPatientQueueLog.class);
		criteria.add(Restrictions.eq("id", id));
		OpdPatientQueueLog opdPatientQueueLog = (OpdPatientQueueLog) criteria.uniqueResult();
		return opdPatientQueueLog;
	}
	//New Requirement "Editable Dashboard" //
	public Encounter getLastOPDEncounter(Patient patient) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Encounter.class,"encounter")
				.createAlias("encounter.encounterType", "encounterType");
		criteria.add(Restrictions.eq("patient", patient));
		criteria.add(Restrictions.eq("encounterType.name","OPDENCOUNTER"));
		criteria.addOrder(Order.desc("dateCreated"));
		criteria.setMaxResults(1);
		return (Encounter) criteria.uniqueResult();
	}
	
	public OpdPatientQueueLog getOpdPatientQueueLogByEncounter(Encounter encounter) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(OpdPatientQueueLog.class);
		criteria.add(Restrictions.eq("encounter", encounter));
		return (OpdPatientQueueLog) criteria.uniqueResult();
	}
	
	public Obs getObservationByPersonConceptAndEncounter(Person person,Concept concept,Encounter encounter) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Obs.class);
		criteria.add(Restrictions.eq("person", person));
		criteria.add(Restrictions.eq("concept", concept));
		criteria.add(Restrictions.eq("encounter", encounter));
		criteria.addOrder(Order.desc("dateCreated"));
		criteria.setMaxResults(1);
		return (Obs) criteria.uniqueResult();
	}
	
	public OpdPatientQueueLog getOpdPatientQueueLog(String patientIdentifier,Integer opdConceptId) throws DAOException {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(OpdPatientQueueLog.class, "queue")
				.createAlias("queue.opdConcept", "opdConcept");
		criteria.add(Restrictions.eq("queue.patientIdentifier", patientIdentifier));
		criteria.addOrder(Order.desc("queue.createdOn"));
		criteria.setMaxResults(1);
		return (OpdPatientQueueLog) criteria.uniqueResult();
	}
	
	@SuppressWarnings("unchecked")
	public List<OpdPatientQueue> getAllPatientInQueue() throws DAOException {
		//for sure everything always get less than one date
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(OpdPatientQueue.class,"opdPatientQueue");
		String date = formatterExt.format(new Date());
		String startFromDate = date + " 00:00:00";
		try {
			criteria.add(Restrictions.lt(
					"opdPatientQueue.createdOn", formatter.parse(startFromDate)));
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error convert date: "+ e.toString());
			e.printStackTrace();
		}
		return criteria.list();
	}

		// TODO Auto-generated method stub
	
	//New Requirement "Editable Dashboard" //
	public List<Obs> getAllDiagnosis(Integer personId) 
			throws DAOException {
				 Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Obs.class,"obs");
				 String toDdate = formatter1.format(new Date());
				
				 Date date1 = new Date(); 
				
				 Date oldDate = new Date(date1.getTime() - TimeUnit.HOURS.toMillis(24));
				 String fromDate = formatter1.format(oldDate);
				
				 
				try {
					criteria.add(Restrictions.lt(
							"obs.obsDatetime", formatter1.parse(toDdate)));
					criteria.add(Restrictions.gt(
							"obs.obsDatetime", formatter1.parse(fromDate)));
					
				} catch (Exception e) {
					// TODO: handle exception
					System.out.println("Error convert date: "+ e.toString());
					e.printStackTrace();
				}
				
				criteria.add(Restrictions.eq(
						"obs.personId",personId));
				criteria.add(Restrictions.eq(
						"obs.concept", Context.getConceptService().getConcept("PROVISIONAL DIAGNOSIS")));

				return criteria.list();
			}

	//Symptom
	
	public List<Obs> getAllSymptom(Integer personId) 
	throws DAOException {

		 Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Obs.class,"obs");
		 String toDdate = formatter1.format(new Date());
		
		 Date date1 = new Date(); 
		
		 Date oldDate = new Date(date1.getTime() - TimeUnit.HOURS.toMillis(24));
		 String fromDate = formatter1.format(oldDate);

		 
		try {
			criteria.add(Restrictions.lt(
					"obs.obsDatetime", formatter1.parse(toDdate)));
			criteria.add(Restrictions.gt(
					"obs.obsDatetime", formatter1.parse(fromDate)));
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error convert date: "+ e.toString());
			e.printStackTrace();
		}
		
		criteria.add(Restrictions.eq(
				"obs.personId",personId));
		criteria.add(Restrictions.eq(
				"obs.concept",Context.getConceptService().getConcept("SYMPTOM")));

		return criteria.list();
	}
	//Patient History
	public PatientDrugHistory getPatientDrugHistoryByPatientId(Integer id) throws DAOException {
		
		
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(PatientDrugHistory.class);
		criteria.add(Restrictions.eq("patientId", id));
		return (PatientDrugHistory) criteria.uniqueResult();
	}

	public PatientFamilyHistory getPatientFamilyHistoryByPatientId(Integer id) throws DAOException {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(PatientFamilyHistory.class);
		criteria.add(Restrictions.eq("patientId", id));
		PatientFamilyHistory patientFamilyHistory = (PatientFamilyHistory) criteria.uniqueResult();
		return patientFamilyHistory;
	}

	public PatientPersonalHistory getPatientPersonalHistoryByPatientId(Integer id) throws DAOException {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(PatientPersonalHistory.class);
		criteria.add(Restrictions.eq("patientId", id));
		PatientPersonalHistory patientPersonalHistory = (PatientPersonalHistory) criteria.uniqueResult();
		return patientPersonalHistory;
	}
	
	public PatientDrugHistory savePatientDrugHistory(PatientDrugHistory patientDrugHistory) throws DAOException {
		
		return (PatientDrugHistory) sessionFactory.getCurrentSession().merge(patientDrugHistory);
	}
	
	public PatientFamilyHistory savePatientFamilyHistory(PatientFamilyHistory patientFamilyHistory) throws DAOException {
		
		return (PatientFamilyHistory) sessionFactory.getCurrentSession().merge(patientFamilyHistory);
	}
	
	public PatientPersonalHistory savePatientPersonalHistory(PatientPersonalHistory patientPersonalHistory) throws DAOException {
		return (PatientPersonalHistory) sessionFactory.getCurrentSession().merge(patientPersonalHistory);
	}
	
	public void updatePatientDrugHistoryByPatientId(PatientDrugHistory patientDrugHistory) throws DAOException {
		
		 sessionFactory.getCurrentSession().merge(patientDrugHistory);
	}

	public void updatePatientFamilyHistoryByPatientId (PatientFamilyHistory patientFamilyHistory)  throws DAOException {
		 sessionFactory.getCurrentSession().merge(patientFamilyHistory);
	}
	
	public void updatePatientPersonalHistoryByPatientId (PatientPersonalHistory patientPersonalHistory) throws DAOException {
		 sessionFactory.getCurrentSession().merge(patientPersonalHistory);
	}
	
	public OpdPatientQueue getOpdPatientQueueByPatientId(Patient patient) throws DAOException {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(OpdPatientQueue.class);
		criteria.add(Restrictions.eq("patient", patient));
		criteria.addOrder(Order.desc("createdOn"));
		criteria.setMaxResults(1);
		OpdPatientQueue opdPatientQueue = (OpdPatientQueue) criteria.uniqueResult();
		return opdPatientQueue;
	}
	
	public OpdPatientQueueLog getOpdPatientQueueLogByPatientId(Patient patient) throws DAOException {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(OpdPatientQueueLog.class);
		criteria.add(Restrictions.eq("patient", patient));
		criteria.addOrder(Order.desc("createdOn"));
		criteria.setMaxResults(1);
		OpdPatientQueueLog opdPatientQueueLog = (OpdPatientQueueLog) criteria.uniqueResult();
		return opdPatientQueueLog;
	}
	
}
