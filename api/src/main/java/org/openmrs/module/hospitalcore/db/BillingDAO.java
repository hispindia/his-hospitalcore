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

import org.hibernate.SessionFactory;
import org.openmrs.Patient;
import org.openmrs.api.db.DAOException;
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

/**
 *
 */
public interface BillingDAO {
	
	/**
	 * TENDER
	 */
	public void setSessionFactory(SessionFactory sessionFactory) throws DAOException;
	
	public Tender saveTender(Tender tender) throws DAOException;
	
	public List<Tender> listTender(int min, int max) throws DAOException;
	
	public int countListTender() throws DAOException;
	
	public Tender getTenderById(Integer id) throws DAOException;
	
	public void deleteTender(Tender tender) throws DAOException;
	
	public Tender getTenderByNameAndNumber(String name, int number) throws DAOException;
	
	public List<Tender> getActiveTenders() throws DAOException;
	
	/**
	 * COMPANY
	 */
	public Company saveCompany(Company company) throws DAOException;
	
	public void deleteCompany(Company company) throws DAOException;
	
	public List<Company> listCompany(int min, int max) throws DAOException;
	
	public int countListCompany() throws DAOException;
	
	public Company getCompanyById(Integer id) throws DAOException;
	
	public Company getCompanyByName(String name) throws DAOException;
	
	public List<Company> searchCompany(String searchText) throws DAOException;
	
	public List<Company> getAllCompany() throws DAOException;
	
	public List<Company> getAllActiveCompany() throws DAOException;
	
	/**
	 * DRIVER
	 */
	public Driver saveDriver(Driver driver) throws DAOException;
	
	public void deleteDriver(Driver driver) throws DAOException;
	
	public List<Driver> listDriver(int min, int max) throws DAOException;
	
	public int countListDriver() throws DAOException;
	
	public Driver getDriverById(Integer id) throws DAOException;
	
	public Driver getDriveryByName(String name) throws DAOException;
	
	public List<Driver> searchDriver(String searchText) throws DAOException;
	
	public List<Driver> getAllDriver() throws DAOException;
	
	public List<Driver> getAllActiveDriver() throws DAOException;
	
	/**
	 * TENDER BILL
	 */
	public TenderBill getTenderBillById(Integer tenderBillId) throws DAOException;
	
	public TenderBill saveTenderBill(TenderBill tenderBill) throws DAOException;
	
	public List<TenderBill> listTenderBillByCompany(int min, int max, Company company) throws DAOException;
	
	public int countListTenderBillByCompany(Company company) throws DAOException;
	
	public List<TenderBill> getAllTenderBill() throws DAOException;
	
	/**
	 * AMBULANCE
	 */
	public Ambulance saveAmbulance(Ambulance ambulance) throws DAOException;
	
	public void deleteAmbulance(Ambulance ambulance) throws DAOException;
	
	public List<Ambulance> listAmbulance(int min, int max) throws DAOException;
	
	public int countListAmbulance() throws DAOException;
	
	public Ambulance getAmbulanceById(Integer id) throws DAOException;
	
	public Ambulance getAmbulanceByName(String name) throws DAOException;
	
	public List<Ambulance> getAllAmbulance() throws DAOException;
	
	public List<Ambulance> getActiveAmbulances() throws DAOException;
	
	/**
	 * AMBULANCE BILL
	 */
	public AmbulanceBill getAmbulanceBillById(Integer ambulanceBillId) throws DAOException;
	
	public AmbulanceBill saveAmbulanceBill(AmbulanceBill ambulanceBill) throws DAOException;
	
	public List<AmbulanceBill> listAmbulanceBillByDriver(int min, int max, Driver driver) throws DAOException;
	
	public int countListAmbulanceBillByDriver(Driver driver) throws DAOException;
	
	public List<AmbulanceBill> getAllAmbulanceBill() throws DAOException;
	
	/**
	 * SERVICE
	 */
	public BillableService getServiceById(Integer id) throws DAOException;
	
	public List<BillableService> getAllServices() throws DAOException;
	
	public BillableService saveService(BillableService service) throws DAOException;
	
	public BillableService getServiceByConceptId(Integer conceptId) throws DAOException;
	
	public void disableService(Integer conceptId) throws DAOException;
	
	/**
	 * PATIENT SERVICE BILL
	 */
	public PatientServiceBill getPatientServiceBillById(Integer patientServiceBillId) throws DAOException;
	
	public PatientServiceBill savePatientServiceBill(PatientServiceBill patientServiceBill) throws DAOException;
	
	public List<PatientServiceBill> listPatientServiceBillByPatient(int min, int max, Patient patient) throws DAOException;
	
	public int countListPatientServiceBillByPatient(Patient patient) throws DAOException;
	
	public List<PatientServiceBill> getAllPatientServiceBill() throws DAOException;
	
	public PatientServiceBill getPatientServiceBillByReceiptId(Integer patientServiceBillReceiptId) throws DAOException;
	
	/**
	 * MISCELLANEOUS BILL
	 */
	public MiscellaneousServiceBill getMiscellaneousServiceBillById(Integer MiscellaneousServiceBillId) throws DAOException;
	
	public MiscellaneousServiceBill saveMiscellaneousServiceBill(MiscellaneousServiceBill MiscellaneousServiceBill)
	                                                                                                               throws DAOException;
	
	public List<MiscellaneousServiceBill> listMiscellaneousServiceBill(int min, int max) throws DAOException;
	
	public int countListMiscellaneousServiceBill() throws DAOException;
	
	public List<MiscellaneousServiceBill> listMiscellaneousServiceBill(int min, int max, MiscellaneousService service)
	                                                                                                                  throws DAOException;
	
	public int countListMiscellaneousServiceBill(MiscellaneousService service) throws DAOException;
	
	public List<MiscellaneousServiceBill> getAllMiscellaneousServiceBill() throws DAOException;
	
	/**
	 * MISCELLANEOUS SERVICE
	 */
	public MiscellaneousService getMiscellaneousServiceById(Integer MiscellaneousServiceId) throws DAOException;
	
	public MiscellaneousService saveMiscellaneousService(MiscellaneousService MiscellaneousService) throws DAOException;
	
	public List<MiscellaneousService> listMiscellaneousService(int min, int max) throws DAOException;
	
	public int countListMiscellaneousService() throws DAOException;
	
	public List<MiscellaneousService> getAllMiscellaneousService() throws DAOException;
	
	public void deleteMiscellaneousService(MiscellaneousService miscellaneousService) throws DAOException;
	
	public MiscellaneousService getMiscellaneousServiceByName(String name) throws DAOException;
	
	/**
	 * Receipt
	 */
	
	public Receipt createReceipt(Receipt receipt) throws DAOException;
	
	public void updateReceipt() throws DAOException;
	
	public void updateOldBills();
	
    public IndoorPatientServiceBill saveIndoorPatientServiceBill(IndoorPatientServiceBill indoorPatientServiceBill) throws DAOException;
	
	public void deleteIndoorPatientServiceBill(IndoorPatientServiceBill indoorPatientServiceBill) throws DAOException;
	
	public IndoorPatientServiceBill getIndoorPatientServiceBillById(Integer indoorPatientServiceBillId) throws DAOException;
	
	//New Requirement add Paid bill & Free bill Both 
	public PatientServiceBillItem getPatientServiceBillItem(Integer billId,String name) throws DAOException;
	
	 public List<PatientSearch> searchListOfPatient(Date date, String searchKey,int page,int pgSize) throws DAOException;
     
     public int countSearchListOfPatient(Date date, String searchKey,int page) throws DAOException;
     
     public List<OpdTestOrder> listOfOrder(Integer patientId,Date date) throws DAOException;
     
     public List<BillableService> listOfServiceOrder(Integer patientId, Integer encounterId) throws DAOException;
}
