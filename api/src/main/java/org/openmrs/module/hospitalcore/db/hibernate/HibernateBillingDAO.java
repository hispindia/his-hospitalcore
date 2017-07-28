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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.classic.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.openmrs.GlobalProperty;
import org.openmrs.Patient;
import org.openmrs.api.context.Context;
import org.openmrs.api.db.DAOException;
import org.openmrs.module.hospitalcore.BillingService;
import org.openmrs.module.hospitalcore.db.BillingDAO;
import org.openmrs.module.hospitalcore.model.Ambulance;
import org.openmrs.module.hospitalcore.model.AmbulanceBill;
import org.openmrs.module.hospitalcore.model.BillableService;
import org.openmrs.module.hospitalcore.model.Company;
import org.openmrs.module.hospitalcore.model.Driver;
import org.openmrs.module.hospitalcore.model.IndoorPatientServiceBill;
import org.openmrs.module.hospitalcore.model.MiscellaneousService;
import org.openmrs.module.hospitalcore.model.MiscellaneousServiceBill;
import org.openmrs.module.hospitalcore.model.OpdTestOrder;
import org.openmrs.module.hospitalcore.model.PatientSearch;
import org.openmrs.module.hospitalcore.model.PatientServiceBill;
import org.openmrs.module.hospitalcore.model.PatientServiceBillItem;
import org.openmrs.module.hospitalcore.model.Receipt;
import org.openmrs.module.hospitalcore.model.Tender;
import org.openmrs.module.hospitalcore.model.TenderBill;
import org.openmrs.module.hospitalcore.util.PatientUtils;

/**
 * Hibernate specific Idcards database methods
 */
public class HibernateBillingDAO implements BillingDAO {
	
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
	
	/**
	 * @see org.openmrs.module.billing.db.BillingDAO#listTender(int, int)
	 */
	@SuppressWarnings("unchecked")
	public List<Tender> listTender(int min, int max) throws DAOException {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Tender.class);
		criteria.setFirstResult(min).setMaxResults(max);
		return criteria.list();
	}
	
	/**
	 * @see org.openmrs.module.billing.db.BillingDAO#saveTender(org.openmrs.module.billing.model.Tender)
	 */
	public Tender saveTender(Tender tender) throws DAOException {
		return (Tender) sessionFactory.getCurrentSession().merge(tender);
	}
	
	/**
	 * @see org.openmrs.module.billing.db.BillingDAO#countListTender()
	 */
	public int countListTender() throws DAOException {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Tender.class);
		Number rs = (Number) criteria.setProjection(Projections.rowCount()).uniqueResult();
		return rs != null ? rs.intValue() : 0;
	}
	
	public void deleteTender(Tender tender) {
		sessionFactory.getCurrentSession().delete(tender);
	}
	
	public Tender getTenderById(Integer id) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Tender.class);
		criteria.add(Restrictions.eq("tenderId", id));
		return (Tender) criteria.uniqueResult();
	}
	
	public Tender getTenderByNameAndNumber(String name, int number) {
		Criteria crit = sessionFactory.getCurrentSession().createCriteria(Tender.class);
		crit.add(Restrictions.eq("name", name));
		crit.add(Restrictions.eq("number", number));
		return (Tender) crit.uniqueResult();
	}
	
	/**
	 * @see org.openmrs.module.billing.db.BillingDAO#countListCompany()
	 */
	public int countListCompany() throws DAOException {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Company.class);
		Number rs = (Number) criteria.setProjection(Projections.rowCount()).uniqueResult();
		return rs != null ? rs.intValue() : 0;
	}
	
	/**
	 * @see org.openmrs.module.billing.db.BillingDAO#deleteCompany(org.openmrs.module.billing.model.Company)
	 */
	public void deleteCompany(Company company) throws DAOException {
		sessionFactory.getCurrentSession().delete(company);
	}
	
	/**
	 * @see org.openmrs.module.billing.db.BillingDAO#getCompanyById(java.lang.Integer)
	 */
	public Company getCompanyById(Integer id) throws DAOException {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Company.class);
		criteria.add(Restrictions.eq("companyId", id));
		return (Company) criteria.uniqueResult();
	}
	
	/**
	 * @see org.openmrs.module.billing.db.BillingDAO#listCompany(int, int)
	 */
	@SuppressWarnings("unchecked")
	public List<Company> listCompany(int min, int max) throws DAOException {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Company.class);
		criteria.setFirstResult(min).setMaxResults(max);
		return criteria.list();
	}
	
	/**
	 * @see org.openmrs.module.billing.db.BillingDAO#saveCompany(org.openmrs.module.billing.model.Company)
	 */
	public Company saveCompany(Company company) throws DAOException {
		return (Company) sessionFactory.getCurrentSession().merge(company);
	}
	
	public Company getCompanyByName(String name) {
		Criteria crit = sessionFactory.getCurrentSession().createCriteria(Company.class);
		crit.add(Restrictions.eq("name", name));
		return (Company) crit.uniqueResult();
	}
	
	/**
	 * @see org.openmrs.module.billing.db.BillingDAO#countListDriver()
	 */
	public int countListDriver() throws DAOException {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Driver.class);
		Number rs = (Number) criteria.setProjection(Projections.rowCount()).uniqueResult();
		return rs != null ? rs.intValue() : 0;
	}
	
	/**
	 * @see org.openmrs.module.billing.db.BillingDAO#deleteDriver(org.openmrs.module.billing.model.Driver)
	 */
	public void deleteDriver(Driver driver) throws DAOException {
		sessionFactory.getCurrentSession().delete(driver);
	}
	
	/**
	 * @see org.openmrs.module.billing.db.BillingDAO#getDriverById(java.lang.Integer)
	 */
	public Driver getDriverById(Integer id) throws DAOException {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Driver.class);
		criteria.add(Restrictions.eq("driverId", id));
		return (Driver) criteria.uniqueResult();
	}
	
	/**
	 * @see org.openmrs.module.billing.db.BillingDAO#getDriveryByName(java.lang.String)
	 */
	public Driver getDriveryByName(String name) throws DAOException {
		Criteria crit = sessionFactory.getCurrentSession().createCriteria(Driver.class);
		crit.add(Restrictions.eq("name", name));
		return (Driver) crit.uniqueResult();
	}
	
	/**
	 * @see org.openmrs.module.billing.db.BillingDAO#listDriver(int, int)
	 */
	@SuppressWarnings("unchecked")
	public List<Driver> listDriver(int min, int max) throws DAOException {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Driver.class);
		criteria.setFirstResult(min).setMaxResults(max);
		return criteria.list();
	}
	
	/**
	 * @see org.openmrs.module.billing.db.BillingDAO#saveDriver(org.openmrs.module.billing.model.Driver)
	 */
	public Driver saveDriver(Driver driver) throws DAOException {
		return (Driver) sessionFactory.getCurrentSession().merge(driver);
	}
	
	/**
	 * @see org.openmrs.module.billing.db.BillingDAO#searchCompany(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public List<Company> searchCompany(String searchText) throws DAOException {
		Criteria crit = sessionFactory.getCurrentSession().createCriteria(Company.class);
		crit.add(Restrictions.like("name", searchText + "%")).add(Restrictions.eq("retired", false));
		return crit.list();
	}
	
	/**
	 * @see org.openmrs.module.billing.db.BillingDAO#searchDriver(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public List<Driver> searchDriver(String searchText) throws DAOException {
		Criteria crit = sessionFactory.getCurrentSession().createCriteria(Driver.class);
		crit.add(Restrictions.like("name", searchText + "%")).add(Restrictions.eq("retired", false));
		;
		return crit.list();
	}
	
	/**
	 * @see org.openmrs.module.billing.db.BillingDAO#getAllCompany()
	 */
	@SuppressWarnings("unchecked")
	public List<Company> getAllCompany() throws DAOException {
		Criteria crit = sessionFactory.getCurrentSession().createCriteria(Company.class);
		crit.addOrder(Order.asc("name"));
		return crit.list();
	}
	
	/**
	 * @see org.openmrs.module.billing.db.BillingDAO#getAllDriver()
	 */
	@SuppressWarnings("unchecked")
	public List<Driver> getAllDriver() throws DAOException {
		Criteria crit = sessionFactory.getCurrentSession().createCriteria(Driver.class);
		crit.addOrder(Order.asc("name"));
		return crit.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Tender> getActiveTenders() {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Tender.class);
		Date today;
		try {
			today = Context.getDateFormat().parse(Context.getDateFormat().format(new Date()));
			criteria.add(Restrictions.ge("closingDate", new java.sql.Date(today.getTime())));
			criteria.add(Restrictions.eq("retired", false));
		}
		catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return criteria.list();
	}
	
	/**
	 * @see org.openmrs.module.billing.db.BillingDAO#countListTenderBillByCompany(org.openmrs.module.billing.model.Company)
	 */
	public int countListTenderBillByCompany(Company company) throws DAOException {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(TenderBill.class);
		criteria.add(Restrictions.eq("company", company));
		Number rs = (Number) criteria.setProjection(Projections.rowCount()).uniqueResult();
		return rs != null ? rs.intValue() : 0;
	}
	
	/**
	 * @see org.openmrs.module.billing.db.BillingDAO#getAllTenderBill()
	 */
	@SuppressWarnings("unchecked")
	public List<TenderBill> getAllTenderBill() throws DAOException {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(TenderBill.class);
		return criteria.list();
	}
	
	/**
	 * @see org.openmrs.module.billing.db.BillingDAO#getTenderBillById(java.lang.Integer)
	 */
	public TenderBill getTenderBillById(Integer tenderBillId) throws DAOException {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(TenderBill.class);
		criteria.add(Restrictions.eq("tenderBillId", tenderBillId));
		return (TenderBill) criteria.uniqueResult();
	}
	
	/**
	 * @see org.openmrs.module.billing.db.BillingDAO#listTenderBillByCompany(int, int,
	 *      org.openmrs.module.billing.model.Company)
	 */
	@SuppressWarnings("unchecked")
	public List<TenderBill> listTenderBillByCompany(int min, int max, Company company) throws DAOException {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(TenderBill.class);
		criteria.add(Restrictions.eq("company", company)).addOrder(Order.desc("createdDate")).setFirstResult(min)
		        .setMaxResults(max);
		return criteria.list();
	}
	
	/**
	 * @see org.openmrs.module.billing.db.BillingDAO#saveTenderBill(org.openmrs.module.billing.model.TenderBill)
	 */
	public TenderBill saveTenderBill(TenderBill tenderBill) throws DAOException {
		return (TenderBill) sessionFactory.getCurrentSession().merge(tenderBill);
	}
	
	/**
	 * @see org.openmrs.module.billing.db.BillingDAO#countListAmbulance()
	 */
	public int countListAmbulance() throws DAOException {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Ambulance.class);
		Number rs = (Number) criteria.setProjection(Projections.rowCount()).uniqueResult();
		return rs != null ? rs.intValue() : 0;
	}
	
	/**
	 * @see org.openmrs.module.billing.db.BillingDAO#deleteAmbulance(org.openmrs.module.billing.model.Ambulance)
	 */
	public void deleteAmbulance(Ambulance ambulance) throws DAOException {
		sessionFactory.getCurrentSession().delete(ambulance);
	}
	
	/**
	 * @see org.openmrs.module.billing.db.BillingDAO#getAllAmbulance()
	 */
	@SuppressWarnings("unchecked")
	public List<Ambulance> getAllAmbulance() throws DAOException {
		Criteria crit = sessionFactory.getCurrentSession().createCriteria(Company.class);
		crit.addOrder(Order.asc("name"));
		return crit.list();
	}
	
	/**
	 * @see org.openmrs.module.billing.db.BillingDAO#getAmbulanceById(java.lang.Integer)
	 */
	public Ambulance getAmbulanceById(Integer id) throws DAOException {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Ambulance.class);
		criteria.add(Restrictions.eq("ambulanceId", id));
		return (Ambulance) criteria.uniqueResult();
	}
	
	/**
	 * @see org.openmrs.module.billing.db.BillingDAO#getAmbulanceByName(java.lang.String)
	 */
	public Ambulance getAmbulanceByName(String name) throws DAOException {
		Criteria crit = sessionFactory.getCurrentSession().createCriteria(Ambulance.class);
		crit.add(Restrictions.eq("name", name));
		return (Ambulance) crit.uniqueResult();
	}
	
	/**
	 * @see org.openmrs.module.billing.db.BillingDAO#listAmbulance(int, int)
	 */
	@SuppressWarnings("unchecked")
	public List<Ambulance> listAmbulance(int min, int max) throws DAOException {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Ambulance.class);
		criteria.setFirstResult(min).setMaxResults(max);
		return criteria.list();
	}
	
	/**
	 * @see org.openmrs.module.billing.db.BillingDAO#saveAmbulance(org.openmrs.module.billing.model.Ambulance)
	 */
	public Ambulance saveAmbulance(Ambulance ambulance) throws DAOException {
		return (Ambulance) sessionFactory.getCurrentSession().merge(ambulance);
	}
	
	/**
	 * @see org.openmrs.module.billing.db.BillingDAO#countListAmbulanceBillByCompany(org.openmrs.module.billing.model.Driver)
	 */
	public int countListAmbulanceBillByDriver(Driver driver) throws DAOException {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(AmbulanceBill.class);
		criteria.add(Restrictions.eq("driver", driver));
		Number rs = (Number) criteria.setProjection(Projections.rowCount()).uniqueResult();
		return rs != null ? rs.intValue() : 0;
	}
	
	/**
	 * @see org.openmrs.module.billing.db.BillingDAO#getAllAmbulanceBill()
	 */
	@SuppressWarnings("unchecked")
	public List<AmbulanceBill> getAllAmbulanceBill() throws DAOException {
		Criteria crit = sessionFactory.getCurrentSession().createCriteria(AmbulanceBill.class);
		crit.addOrder(Order.desc("createdDate"));
		return crit.list();
	}
	
	/**
	 * @see org.openmrs.module.billing.db.BillingDAO#getAmbulanceBillById(java.lang.Integer)
	 */
	public AmbulanceBill getAmbulanceBillById(Integer ambulanceBillId) throws DAOException {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(AmbulanceBill.class);
		criteria.add(Restrictions.eq("ambulanceBillId", ambulanceBillId));
		return (AmbulanceBill) criteria.uniqueResult();
	}
	
	/**
	 * @see org.openmrs.module.billing.db.BillingDAO#listAmbulanceBillByDriver(int, int,
	 *      org.openmrs.module.billing.model.Driver)
	 */
	@SuppressWarnings("unchecked")
	public List<AmbulanceBill> listAmbulanceBillByDriver(int min, int max, Driver driver) throws DAOException {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(AmbulanceBill.class);
		criteria.add(Restrictions.eq("driver", driver)).addOrder(Order.desc("createdDate")).setFirstResult(min)
		        .setMaxResults(max);
		return criteria.list();
	}
	
	/**
	 * @see org.openmrs.module.billing.db.BillingDAO#saveAmbulanceBill(org.openmrs.module.billing.model.AmbulanceBill)
	 */
	public AmbulanceBill saveAmbulanceBill(AmbulanceBill ambulanceBill) throws DAOException {
		return (AmbulanceBill) sessionFactory.getCurrentSession().merge(ambulanceBill);
	}
	
	/**
	 * @see org.openmrs.module.billing.db.BillingDAO#getActiveAmbulances()
	 */
	@SuppressWarnings("unchecked")
	public List<Ambulance> getActiveAmbulances() throws DAOException {
		Criteria crit = sessionFactory.getCurrentSession().createCriteria(Ambulance.class);
		crit.addOrder(Order.asc("name"));
		crit.add(Restrictions.eq("retired", false));
		return crit.list();
	}
	
	/**
	 * @see org.openmrs.module.billing.db.BillingDAO#getAllServices()
	 */
	@SuppressWarnings("unchecked")
	public List<BillableService> getAllServices() throws DAOException {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(BillableService.class);
		return criteria.list();
	}
	
	/**
	 * @see org.openmrs.module.billing.db.BillingDAO#getServiceByConceptId(java.lang.Integer)
	 */
	public BillableService getServiceByConceptId(Integer conceptId) throws DAOException {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(BillableService.class);
		criteria.add(Restrictions.eq("conceptId", conceptId));
		return (BillableService) criteria.uniqueResult();
	}
	
	/**
	 * @see org.openmrs.module.billing.db.BillingDAO#getServiceById(java.lang.Integer)
	 */
	public BillableService getServiceById(Integer id) throws DAOException {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(BillableService.class);
		criteria.add(Restrictions.eq("serviceId", id));
		return (BillableService) criteria.uniqueResult();
	}
	
	/**
	 * @see org.openmrs.module.billing.db.BillingDAO#saveService(org.openmrs.module.billing.model.BillableService)
	 */
	public BillableService saveService(BillableService service) throws DAOException {
		return (BillableService) sessionFactory.getCurrentSession().merge(service);
	}
	
	/**
	 * @see org.openmrs.module.billing.db.BillingDAO#countListPatientServiceBillByPatient(org.openmrs.Patient)
	 */
	public int countListPatientServiceBillByPatient(Patient patient) throws DAOException {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(PatientServiceBill.class);
		criteria.add(Restrictions.eq("patient", patient));
		Number rs = (Number) criteria.setProjection(Projections.rowCount()).uniqueResult();
		return rs != null ? rs.intValue() : 0;
	}
	
	/**
	 * @see org.openmrs.module.billing.db.BillingDAO#getAllPatientServiceBill()
	 */
	@SuppressWarnings("unchecked")
	public List<PatientServiceBill> getAllPatientServiceBill() throws DAOException {
		Criteria crit = sessionFactory.getCurrentSession().createCriteria(PatientServiceBill.class);
		crit.addOrder(Order.asc("createdDate"));
		return crit.list();
	}
	
	/**
	 * @see org.openmrs.module.billing.db.BillingDAO#getPatientServiceBillById(java.lang.Integer)
	 */
	public PatientServiceBill getPatientServiceBillById(Integer patientServiceBillId) throws DAOException {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(PatientServiceBill.class);
		criteria.add(Restrictions.eq("patientServiceBillId", patientServiceBillId));
		return (PatientServiceBill) criteria.uniqueResult();
	}
	
	/**
	 * @see org.openmrs.module.billing.db.BillingDAO#listPatientServiceBillByDriver(int, int,
	 *      org.openmrs.module.billing.model.Driver)
	 */
	@SuppressWarnings("unchecked")
	public List<PatientServiceBill> listPatientServiceBillByPatient(int min, int max, Patient patient) throws DAOException {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(PatientServiceBill.class);
		criteria.add(Restrictions.eq("patient", patient)).addOrder(Order.desc("createdDate")).setFirstResult(min)
		        .setMaxResults(max);
		return criteria.list();
	}
	
	/**
	 * @see org.openmrs.module.billing.db.BillingDAO#savePatientServiceBill(org.openmrs.module.billing.model.PatientServiceBill)
	 */
	public PatientServiceBill savePatientServiceBill(PatientServiceBill patientServiceBill) throws DAOException {
		return (PatientServiceBill) sessionFactory.getCurrentSession().merge(patientServiceBill);
	}
	
	/**
	 * @see org.openmrs.module.billing.db.BillingDAO#getAllActiveCompany()
	 */
	@SuppressWarnings("unchecked")
	public List<Company> getAllActiveCompany() throws DAOException {
		Criteria crit = sessionFactory.getCurrentSession().createCriteria(Company.class);
		crit.addOrder(Order.asc("name")).add(Restrictions.eq("retired", false));
		return crit.list();
	}
	
	/**
	 * @see org.openmrs.module.billing.db.BillingDAO#getAllActiveDriver()
	 */
	@SuppressWarnings("unchecked")
	public List<Driver> getAllActiveDriver() throws DAOException {
		Criteria crit = sessionFactory.getCurrentSession().createCriteria(Driver.class);
		crit.addOrder(Order.asc("name")).add(Restrictions.eq("retired", false));
		return crit.list();
	}
	
	public void disableService(Integer conceptId) throws DAOException {
		BillableService service = getServiceByConceptId(conceptId);
		if (service != null) {
			service.setDisable(true);
			saveService(service);
		}
	}
	
	public int countListMiscellaneousService() throws DAOException {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(MiscellaneousService.class);
		Number rs = (Number) criteria.setProjection(Projections.rowCount()).uniqueResult();
		return rs != null ? rs.intValue() : 0;
	}
	
	public int countListMiscellaneousServiceBill() throws DAOException {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(MiscellaneousServiceBill.class);
		Number rs = (Number) criteria.setProjection(Projections.rowCount()).uniqueResult();
		return rs != null ? rs.intValue() : 0;
	}
	
	@SuppressWarnings("unchecked")
	public List<MiscellaneousService> getAllMiscellaneousService() throws DAOException {
		Criteria crit = sessionFactory.getCurrentSession().createCriteria(MiscellaneousService.class);
		crit.addOrder(Order.asc("name"));
		crit.add(Restrictions.eq("retired", false));
		return crit.list();
		
	}
	
	@SuppressWarnings("unchecked")
	public List<MiscellaneousServiceBill> getAllMiscellaneousServiceBill() throws DAOException {
		Criteria crit = sessionFactory.getCurrentSession().createCriteria(MiscellaneousServiceBill.class);
		crit.addOrder(Order.asc("liableName"));
		crit.add(Restrictions.eq("voided", false));
		return crit.list();
	}
	
	public MiscellaneousServiceBill getMiscellaneousServiceBillById(Integer id) throws DAOException {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(MiscellaneousServiceBill.class);
		criteria.add(Restrictions.eq("id", id));
		return (MiscellaneousServiceBill) criteria.uniqueResult();
	}
	
	public MiscellaneousService getMiscellaneousServiceById(Integer serviceId) throws DAOException {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(MiscellaneousService.class);
		criteria.add(Restrictions.eq("id", serviceId));
		return (MiscellaneousService) criteria.uniqueResult();
	}
	
	@SuppressWarnings("unchecked")
	public List<MiscellaneousService> listMiscellaneousService(int min, int max) throws DAOException {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(MiscellaneousService.class);
		criteria.setFirstResult(min).setMaxResults(max);
		return criteria.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<MiscellaneousServiceBill> listMiscellaneousServiceBill(int min, int max) throws DAOException {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(MiscellaneousServiceBill.class);
		criteria.setFirstResult(min).setMaxResults(max);
		return criteria.list();
	}
	
	public MiscellaneousService saveMiscellaneousService(MiscellaneousService service) throws DAOException {
		return (MiscellaneousService) sessionFactory.getCurrentSession().merge(service);
	}
	
	public MiscellaneousServiceBill saveMiscellaneousServiceBill(MiscellaneousServiceBill bill) throws DAOException {
		return (MiscellaneousServiceBill) sessionFactory.getCurrentSession().merge(bill);
	}
	
	public void deleteMiscellaneousService(MiscellaneousService miscellaneousService) throws DAOException {
		sessionFactory.getCurrentSession().delete(miscellaneousService);
	}
	
	public MiscellaneousService getMiscellaneousServiceByName(String name) throws DAOException {
		Criteria crit = sessionFactory.getCurrentSession().createCriteria(MiscellaneousService.class);
		crit.add(Restrictions.eq("name", name));
		return (MiscellaneousService) crit.uniqueResult();
	}
	
	public int countListMiscellaneousServiceBill(MiscellaneousService service) throws DAOException {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(MiscellaneousServiceBill.class);
		if (service != null) {
			criteria.add(Restrictions.eq("service", service));
		}
		Number rs = (Number) criteria.setProjection(Projections.rowCount()).uniqueResult();
		return rs != null ? rs.intValue() : 0;
	}
	
	@SuppressWarnings("unchecked")
	public List<MiscellaneousServiceBill> listMiscellaneousServiceBill(int min, int max, MiscellaneousService service)
	                                                                                                                  throws DAOException {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(MiscellaneousServiceBill.class);
		if (service != null) {
			criteria.add(Restrictions.eq("service", service));
		}
		criteria.setFirstResult(min).setMaxResults(max);
		criteria.addOrder(Order.desc("createdDate"));
		return criteria.list();
	}
	
	public Receipt createReceipt(Receipt receipt) throws DAOException {
		return (Receipt) sessionFactory.getCurrentSession().merge(receipt);
	}
	
	public void updateReceipt() {
		
		GlobalProperty isUpdated = Context.getAdministrationService().getGlobalPropertyObject("billing.updatedReceiptIds");
		if ("false".equalsIgnoreCase(isUpdated.getPropertyValue())) {
			try {
				GlobalProperty encounterTypeId = Context.getAdministrationService().getGlobalPropertyObject(
				    "billing.encounterTypeId");
				if (encounterTypeId == null || !"6".equalsIgnoreCase(encounterTypeId.getPropertyValue())) {
					encounterTypeId.setPropertyValue("6");
					Context.getAdministrationService().saveGlobalProperty(encounterTypeId);
				}
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			// update receipt for old bills
			isUpdated.setPropertyValue("updating");
			log.info("Start updating receipt ID for old BillingService ");
			BillingService billingService = Context.getService(BillingService.class);
			List<AmbulanceBill> ambulanceBills = billingService.getAllAmbulanceBill();
			Session session = sessionFactory.getCurrentSession();
			Transaction tx = session.beginTransaction();
			tx.begin();
			int i = 0;
			for (AmbulanceBill bill : ambulanceBills) {
				if (bill.getReceipt() == null) {
					i++;
					bill.setReceipt(billingService.createReceipt());
					billingService.saveAmbulanceBill(bill);
					if (i % 20 == 0) { // 20, same as the JDBC batch size
						// flush a batch of inserts and release memory:
						session.flush();
						session.clear();
					}
				}
			}
			List<TenderBill> tenderBills = billingService.getAllTenderBill();
			log.info("Start updating receipt ID for old TenderBill ");
			i = 0;
			for (TenderBill bill : tenderBills) {
				if (bill.getReceipt() == null) {
					i++;
					bill.setReceipt(billingService.createReceipt());
					billingService.saveTenderBill(bill);
					if (i % 20 == 0) { // 20, same as the JDBC batch size
						// flush a batch of inserts and release memory:
						session.flush();
						session.clear();
					}
				}
			}
			List<PatientServiceBill> patientServiceBills = billingService.getAllPatientServiceBill();
			log.info("Start updating receipt ID for old PatientServiceBill ");
			i = 0;
			for (PatientServiceBill bill : patientServiceBills) {
				if (bill.getReceipt() == null) {
					i++;
					bill.setReceipt(billingService.createReceipt());
					billingService.savePatientServiceBill(bill);
					if (i % 20 == 0) { // 20, same as the JDBC batch size
						// flush a batch of inserts and release memory:
						session.flush();
						session.clear();
					}
				}
			}
			
			List<MiscellaneousServiceBill> miscellaneousBills = billingService.getAllMiscellaneousServiceBill();
			log.info("Start updating receipt ID for old MiscellaneousServiceBill ");
			i = 0;
			for (MiscellaneousServiceBill bill : miscellaneousBills) {
				if (bill.getReceipt() == null) {
					i++;
					bill.setReceipt(billingService.createReceipt());
					billingService.saveMiscellaneousServiceBill(bill);
					if (i % 20 == 0) { // 20, same as the JDBC batch size
						// flush a batch of inserts and release memory:
						session.flush();
						session.clear();
					}
				}
			}
			
			isUpdated.setPropertyValue("true");
			Context.getAdministrationService().saveGlobalProperty(isUpdated);
			tx.commit();
			log.info("End updating receipt ID for old bills ");
		}
	}
	
	public void updateOldBills() {
		
		Session session = sessionFactory.getCurrentSession();
		Transaction tx = session.beginTransaction();
		tx.begin();
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(PatientServiceBillItem.class);
		List<PatientServiceBillItem> items = criteria.list();
		int i = 0;
		for (PatientServiceBillItem item : items) {
			
			i++;
			// update old bill
			String category = PatientUtils
			        .getPatientAttribute(item.getPatientServiceBill().getPatient(), "Patient Category");
			System.out.println(category);
			System.out.println(item.getPatientServiceBillItemId());
			if (i % 50 == 0) {
				session.flush();
				session.clear();
			}
		}
		tx.commit();
		
		/*
		 * for (AmbulanceBill bill : ambulanceBills) { if (bill.getReceipt() ==
		 * null) { i++; bill.setReceipt(billingService.createReceipt());
		 * billingService.saveAmbulanceBill(bill); if (i % 20 == 0) { // 20,
		 * same as the JDBC batch size // flush a batch of inserts and release
		 * memory: session.flush(); session.clear(); } } }
		 */
	}
	
	/**
	 * @see org.openmrs.module.billing.db.BillingDAO#getPatientServiceBillByReceiptId(java.lang.Integer)
	 */
	public PatientServiceBill getPatientServiceBillByReceiptId(Integer patientServiceBillReceiptId) throws DAOException {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(PatientServiceBill.class);
		Receipt receipt = new Receipt();
		receipt.setId(patientServiceBillReceiptId);
		
		criteria.add(Restrictions.eq("receipt", receipt));
		return (PatientServiceBill) criteria.uniqueResult();
	}
	
	public IndoorPatientServiceBill saveIndoorPatientServiceBill(
			IndoorPatientServiceBill indoorPatientServiceBill)
			throws DAOException {
		return (IndoorPatientServiceBill) sessionFactory.getCurrentSession()
				.merge(indoorPatientServiceBill);
	}
	
	public void deleteIndoorPatientServiceBill(
			IndoorPatientServiceBill indoorPatientServiceBill)
			throws DAOException {
		sessionFactory.getCurrentSession().delete(indoorPatientServiceBill);
	}
	
	public IndoorPatientServiceBill getIndoorPatientServiceBillById(Integer indoorPatientServiceBillId) throws DAOException {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				IndoorPatientServiceBill.class);
		criteria.add(Restrictions.eq("indoorPatientServiceBillId",
				indoorPatientServiceBillId));
		return (IndoorPatientServiceBill) criteria.uniqueResult();
	}
	
	//New Requirement add Paid bill & Free bill Both 
	public PatientServiceBillItem getPatientServiceBillItem(Integer billId,
			String name) throws DAOException {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				PatientServiceBillItem.class);

		criteria.add(Restrictions.eq("patientServiceBill.patientServiceBillId",
				billId));
		criteria.add(Restrictions.eq("name", name));
		return (PatientServiceBillItem) criteria.uniqueResult();
	}
	
	public List<PatientSearch> searchListOfPatient(Date date, String searchKey,
			int page,int pgSize) throws DAOException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String startDate = sdf.format(date) + " 00:00:00";
		String endDate = sdf.format(date) + " 23:59:59";
		String hql = "SELECT DISTINCT ps from PatientSearch ps,OpdTestOrder o INNER JOIN o.patient p where p.patientId=ps.patientId AND o.scheduleDate BETWEEN '"
				+ startDate
				+ "' AND '"
				+ endDate
				//+ "' AND o.billingStatus=0 AND o.cancelStatus=0 AND o.billableService is NOT NULL GROUP BY o.patient) AND (ps.identifier LIKE '%"
				+ "' AND o.billingStatus=0 AND o.cancelStatus=0 AND o.billableService is NOT NULL AND o.valueCoded NOT IN (SELECT c.answerConcept FROM ConceptAnswer c,ConceptName cn WHERE cn.name='MAJOR OPERATION' AND c.concept=cn.concept) AND (ps.identifier LIKE '%"
				+ searchKey + "%' OR ps.fullname LIKE '%" + searchKey + "%')";
		int firstResult = (page - 1) *pgSize;
		Session session = sessionFactory.getCurrentSession();
		Query q = session.createQuery(hql).setFirstResult(firstResult).setMaxResults(pgSize);
		List<PatientSearch> list = q.list();
		return list;
	}
        
        public int countSearchListOfPatient(Date date, String searchKey,
			int page) throws DAOException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String startDate = sdf.format(date) + " 00:00:00";
		String endDate = sdf.format(date) + " 23:59:59";
		String hql = "SELECT DISTINCT ps from PatientSearch ps,OpdTestOrder o INNER JOIN o.patient p where p.patientId=ps.patientId AND o.scheduleDate BETWEEN '"
				+ startDate
				+ "' AND '"
				+ endDate
				//+ "' AND o.billingStatus=0 AND o.cancelStatus=0 AND o.billableService is NOT NULL GROUP BY o.patient) AND (ps.identifier LIKE '%"
				+ "' AND o.billingStatus=0 AND o.cancelStatus=0 AND o.billableService is NOT NULL AND o.valueCoded NOT IN (SELECT c.answerConcept FROM ConceptAnswer c,ConceptName cn WHERE cn.name='MAJOR OPERATION' AND c.concept=cn.concept) AND (ps.identifier LIKE '%"
				+ searchKey + "%' OR ps.fullname LIKE '" + searchKey + "%')";

		Session session = sessionFactory.getCurrentSession();
                Query q = session.createQuery(hql);
		List<PatientSearch> list = q.list();
		return list.size();
	}
        
        public List<OpdTestOrder> listOfOrder(Integer patientId, Date date)
		throws DAOException {
	/*
	 * Criteria criteria =
	 * sessionFactory.getCurrentSession().createCriteria(
	 * OpdTestOrder.class); criteria.add(Restrictions.eq("patient",
	 * patient)); criteria.add(Restrictions.eq("billingStatus", 0));
	 * criteria.add(Restrictions.eq("cancelStatus", 0)); return
	 * criteria.list();
	 */
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	String startDate = sdf.format(date) + " 00:00:00";
	String endDate = sdf.format(date) + " 23:59:59";
	String hql = "from OpdTestOrder o where o.patient='"
			+ patientId
			+ "' AND o.scheduleDate BETWEEN '"
			+ startDate
			+ "' AND '"
			+ endDate
			+ "' AND o.billingStatus=0 AND o.cancelStatus=0 AND o.billableService is NOT NULL AND o.valueCoded NOT IN (SELECT c.answerConcept FROM ConceptAnswer c,ConceptName cn WHERE cn.name='MAJOR OPERATION' AND c.concept=cn.concept) GROUP BY encounter";
	Session session = sessionFactory.getCurrentSession();
	Query q = session.createQuery(hql);
	List<OpdTestOrder> list = q.list();
	return list;
    }
        
        public List<BillableService> listOfServiceOrder(Integer patientId,
    			Integer encounterId) throws DAOException {
    		String hql = "from BillableService b where b.conceptId IN (SELECT o.valueCoded FROM OpdTestOrder o where o.patient='"
    				+ patientId
    				+ "' AND o.encounter='"
    				+ encounterId
    				//+ "' AND o.billingStatus=0 AND o.cancelStatus=0 AND o.billableService is NOT NULL)";
    				+ "' AND o.billingStatus=0 AND o.cancelStatus=0 AND o.billableService is NOT NULL AND o.valueCoded NOT IN (SELECT c.answerConcept FROM ConceptAnswer c,ConceptName cn WHERE cn.name='MAJOR OPERATION' AND c.concept=cn.concept))";
    		Session session = sessionFactory.getCurrentSession();
    		Query q = session.createQuery(hql);
    		List<BillableService> list = q.list();
    		return list;
    	}

	
}
