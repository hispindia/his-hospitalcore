/**
 *  Copyright 2013 Society for Health Information Systems Programmes, India (HISP India)
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
 *  author: ghanshyam
 *  date: 18-april-2013
 *  issue: #1391 India module
 **/

package org.openmrs.module.hospitalcore.db.hibernate;

import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.openmrs.Concept;
import org.openmrs.ConceptClass;
import org.openmrs.Patient;
import org.openmrs.api.context.Context;
import org.openmrs.api.db.DAOException;
import org.openmrs.module.hospitalcore.db.InventoryCommonDAO;
import org.openmrs.module.hospitalcore.model.InventoryDrug;
import org.openmrs.module.hospitalcore.model.InventoryDrugFormulation;
import org.openmrs.module.hospitalcore.model.InventoryStoreDrugPatient;
import org.openmrs.module.hospitalcore.model.InventoryStoreDrugPatientDetail;
import org.openmrs.module.hospitalcore.model.InventoryStoreDrugTransactionDetail;

public class HibernateInventoryCommonDAO implements InventoryCommonDAO {
	
    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	
	SimpleDateFormat formatterExt = new SimpleDateFormat("dd/MM/yyyy");

	protected final Log log = LogFactory.getLog(getClass());

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

	@SuppressWarnings("unchecked")
	public InventoryStoreDrugPatient getInventoryStoreDrugPatient(Integer billNo) throws DAOException {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				InventoryStoreDrugPatient.class);
		criteria.add(Restrictions.eq("id", billNo));
		return (InventoryStoreDrugPatient) criteria.uniqueResult();
	}
	
	@SuppressWarnings("unchecked")
	public List<InventoryStoreDrugPatientDetail> getInventoryStoreDrugPatientDetail(InventoryStoreDrugPatient isdp) throws DAOException {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				InventoryStoreDrugPatientDetail.class);
		criteria.add(Restrictions.eq("storeDrugPatient", isdp));
		return criteria.list();
	}
	
	@SuppressWarnings("unchecked")
	public InventoryStoreDrugTransactionDetail getInventoryStoreDrugTransactionDetail(Integer id) throws DAOException {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				InventoryStoreDrugTransactionDetail.class);
		criteria.add(Restrictions.eq("id", id));
		return (InventoryStoreDrugTransactionDetail) criteria.uniqueResult();
	}
	
	@SuppressWarnings("unchecked")
	public List<InventoryStoreDrugPatient> getAllIssueDateByPatientId(
			Patient patient) throws DAOException {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				InventoryStoreDrugPatient.class);
		criteria.add(Restrictions.eq("patient", patient));
		//ghanshyam,date:25-april-2013 Feedback #1391 Add Pharmacy record of patient in Dashboard(note:added below sorting criteria)
		//criteria.addOrder(Order.asc("createdOn"));
		criteria.addOrder(Order.desc("createdOn"));

		return criteria.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<InventoryStoreDrugPatient> getDeatilOfInventoryStoreDrugPatient(Patient patient,String date) throws DAOException {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				InventoryStoreDrugPatient.class);
		criteria.add(Restrictions.eq("patient", patient));
		if(!date.equals("all")){
			String startDate = date + " 00:00:00";
			String endDate = date + " 23:59:59";
			try {
				criteria.add(Restrictions.and(Restrictions.ge("createdOn", formatter.parse(startDate)),
					    Restrictions.le("createdOn", formatter.parse(endDate))));
			}
			catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		else{
			//ghanshyam,date:25-april-2013 Feedback #1391 Add Pharmacy record of patient in Dashboard(note:added below sorting criteria)
			//criteria.addOrder(Order.asc("createdOn"));
			criteria.addOrder(Order.desc("createdOn"));
		}
		
		return criteria.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<InventoryStoreDrugPatientDetail> getDrugDetailOfPatient(InventoryStoreDrugPatient isdpd) throws DAOException {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				InventoryStoreDrugPatientDetail.class);
		criteria.add(Restrictions.eq("storeDrugPatient", isdpd));

		return criteria.list();
	}
	
	public InventoryDrug getDrugByName(String name) throws DAOException {
		Criteria criteria = sessionFactory.getCurrentSession()
				.createCriteria(InventoryDrug.class, "drug")
				.add(Restrictions.eq("drug.name", name));
		return (InventoryDrug) criteria.uniqueResult();
	}
	
	public List<Concept> getDrugFrequency() throws DAOException {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				Concept.class, "con");
		ConceptClass conClass = Context.getConceptService()
				.getConceptClassByName("Frequency");
		criteria.add(Restrictions.eq("con.conceptClass", conClass));
		return criteria.list();
	}
	
	public InventoryDrugFormulation getDrugFormulationById(Integer id) throws DAOException {
		Criteria criteria = sessionFactory.getCurrentSession()
		        .createCriteria(InventoryDrugFormulation.class, "drugFormulation")
		        .add(Restrictions.eq("drugFormulation.id", id));
		return (InventoryDrugFormulation) criteria.uniqueResult();
	}
	
	public InventoryStoreDrugTransactionDetail expireInventoryStoreDrugTransactionDetail(InventoryStoreDrugTransactionDetail isdtd) throws DAOException {
		Criteria criteria = sessionFactory.getCurrentSession()
		        .createCriteria(InventoryStoreDrugTransactionDetail.class, "inventoryStoreDrugTransactionDetail");
		return (InventoryStoreDrugTransactionDetail) sessionFactory.getCurrentSession().merge(isdtd);
	}
}
