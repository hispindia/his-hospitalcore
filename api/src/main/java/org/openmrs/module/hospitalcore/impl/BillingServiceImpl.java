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
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Concept;
import org.openmrs.ConceptAnswer;
import org.openmrs.ConceptClass;
import org.openmrs.ConceptSet;
import org.openmrs.Encounter;
import org.openmrs.EncounterType;
import org.openmrs.Location;
import org.openmrs.Order;
import org.openmrs.OrderType;
import org.openmrs.Patient;
import org.openmrs.api.APIException;
import org.openmrs.api.context.Context;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.hospitalcore.BillingConstants;
import org.openmrs.module.hospitalcore.BillingService;
import org.openmrs.module.hospitalcore.LabService;
import org.openmrs.module.hospitalcore.RadiologyCoreService;
import org.openmrs.module.hospitalcore.concept.TestTree;
import org.openmrs.module.hospitalcore.db.BillingDAO;
import org.openmrs.module.hospitalcore.model.Ambulance;
import org.openmrs.module.hospitalcore.model.AmbulanceBill;
import org.openmrs.module.hospitalcore.model.BillableService;
import org.openmrs.module.hospitalcore.model.Company;
import org.openmrs.module.hospitalcore.model.Driver;
import org.openmrs.module.hospitalcore.model.IndoorPatientServiceBill;
import org.openmrs.module.hospitalcore.model.IndoorPatientServiceBillItem;
import org.openmrs.module.hospitalcore.model.Lab;
import org.openmrs.module.hospitalcore.model.MiscellaneousService;
import org.openmrs.module.hospitalcore.model.MiscellaneousServiceBill;
import org.openmrs.module.hospitalcore.model.OpdTestOrder;
import org.openmrs.module.hospitalcore.model.PatientSearch;
import org.openmrs.module.hospitalcore.model.PatientServiceBill;
import org.openmrs.module.hospitalcore.model.PatientServiceBillItem;
import org.openmrs.module.hospitalcore.model.RadiologyDepartment;
import org.openmrs.module.hospitalcore.model.Receipt;
import org.openmrs.module.hospitalcore.model.Tender;
import org.openmrs.module.hospitalcore.model.TenderBill;
import org.openmrs.module.hospitalcore.util.ConceptAnswerComparator;
import org.openmrs.module.hospitalcore.util.ConceptSetComparator;
import org.openmrs.module.hospitalcore.util.GlobalPropertyUtil;
import org.openmrs.module.hospitalcore.util.HospitalCoreConstants;

public class BillingServiceImpl extends BaseOpenmrsService implements BillingService {
	
	private Log log = LogFactory.getLog(this.getClass());
	
	public BillingServiceImpl() {
	}
	
	protected BillingDAO dao;
	
	public void setDao(BillingDAO dao) {
		this.dao = dao;
	}
	
	/**
	 * @see org.openmrs.module.billing.BillingService#countListTender()
	 */
	public int countListTender() throws APIException {
		return dao.countListTender();
	}
	
	/**
	 * @see org.openmrs.module.billing.BillingService#listTender(int, int)
	 */
	public List<Tender> listTender(int min, int max) throws APIException {
		return dao.listTender(min, max);
	}
	
	/**
	 * @see org.openmrs.module.billing.BillingService#saveTender(org.openmrs.module.billing.model.Tender)
	 */
	public Tender saveTender(Tender tender) throws APIException {
		return dao.saveTender(tender);
	}
	
	/**
	 * @see org.openmrs.module.billing.BillingService#deleteTender(org.openmrs.module.billing.model.Tender)
	 */
	public void deleteTender(Tender tender) throws APIException {
		dao.deleteTender(tender);
	}
	
	/**
	 * @see org.openmrs.module.billing.BillingService#getTenderById(java.lang.Integer)
	 */
	public Tender getTenderById(Integer id) throws APIException {
		return dao.getTenderById(id);
	}
	
	/**
	 * @see org.openmrs.module.billing.BillingService#getTenderByNameAndNumber(java.lang.String,
	 *      int)
	 */
	public Tender getTenderByNameAndNumber(String name, int number) throws APIException {
		return dao.getTenderByNameAndNumber(name, number);
	}
	
	/**
	 * @see org.openmrs.module.billing.BillingService#countListCompany()
	 */
	public int countListCompany() throws APIException {
		return dao.countListCompany();
	}
	
	/**
	 * @see org.openmrs.module.billing.BillingService#deleteCompany(org.openmrs.module.billing.model.Company)
	 */
	public void deleteCompany(Company company) throws APIException {
		dao.deleteCompany(company);
	}
	
	/**
	 * @see org.openmrs.module.billing.BillingService#getCompanyById(java.lang.Integer)
	 */
	public Company getCompanyById(Integer id) throws APIException {
		return dao.getCompanyById(id);
	}
	
	/**
	 * @see org.openmrs.module.billing.BillingService#listCompany(int, int)
	 */
	public List<Company> listCompany(int min, int max) throws APIException {
		return dao.listCompany(min, max);
	}
	
	/**
	 * @see org.openmrs.module.billing.BillingService#saveCompany(org.openmrs.module.billing.model.Company)
	 */
	public Company saveCompany(Company company) throws APIException {
		return dao.saveCompany(company);
	}
	
	/**
	 * @see org.openmrs.module.billing.BillingService#getCompanyByName(java.lang.String)
	 */
	public Company getCompanyByName(String name) throws APIException {
		return dao.getCompanyByName(name);
	}
	
	/**
	 * @see org.openmrs.module.billing.BillingService#countListDriver()
	 */
	public int countListDriver() throws APIException {
		return dao.countListDriver();
	}
	
	/**
	 * @see org.openmrs.module.billing.BillingService#deleteDriver(org.openmrs.module.billing.model.Driver)
	 */
	public void deleteDriver(Driver driver) throws APIException {
		dao.deleteDriver(driver);
	}
	
	/**
	 * @see org.openmrs.module.billing.BillingService#getDriverById(java.lang.Integer)
	 */
	public Driver getDriverById(Integer id) throws APIException {
		return dao.getDriverById(id);
	}
	
	/**
	 * @see org.openmrs.module.billing.BillingService#getDriverByName(java.lang.String)
	 */
	public Driver getDriverByName(String name) throws APIException {
		return dao.getDriveryByName(name);
	}
	
	/**
	 * @see org.openmrs.module.billing.BillingService#listDriver(int, int)
	 */
	public List<Driver> listDriver(int min, int max) throws APIException {
		return dao.listDriver(min, max);
	}
	
	/**
	 * @see org.openmrs.module.billing.BillingService#saveDriver(org.openmrs.module.billing.model.Driver)
	 */
	public Driver saveDriver(Driver driver) throws APIException {
		return dao.saveDriver(driver);
	}
	
	/**
	 * @see org.openmrs.module.billing.BillingService#searchCompany(java.lang.String)
	 */
	public List<Company> searchCompany(String searchText) throws APIException {
		return dao.searchCompany(searchText);
	}
	
	/**
	 * @see org.openmrs.module.billing.BillingService#searchDriver(java.lang.String)
	 */
	public List<Driver> searchDriver(String searchText) throws APIException {
		return dao.searchDriver(searchText);
	}
	
	/**
	 * @see org.openmrs.module.billing.BillingService#getAllCompany()
	 */
	public List<Company> getAllCompany() throws APIException {
		return dao.getAllCompany();
	}
	
	/**
	 * @see org.openmrs.module.billing.BillingService#getAllDriver()
	 */
	public List<Driver> getAllDriver() throws APIException {
		return dao.getAllDriver();
	}
	
	/**
	 * @see org.openmrs.module.billing.BillingService#countListTenderBillByCompany(org.openmrs.module.billing.model.Company)
	 */
	public int countListTenderBillByCompany(Company company) throws APIException {
		return dao.countListTenderBillByCompany(company);
	}
	
	/**
	 * @see org.openmrs.module.billing.BillingService#getAllTenderBill()
	 */
	public List<TenderBill> getAllTenderBill() throws APIException {
		return dao.getAllTenderBill();
	}
	
	/**
	 * @see org.openmrs.module.billing.BillingService#getTenderBillById(java.lang.Integer)
	 */
	public TenderBill getTenderBillById(Integer tenderBillId) throws APIException {
		return dao.getTenderBillById(tenderBillId);
	}
	
	/**
	 * @see org.openmrs.module.billing.BillingService#listTenderBillByCompany(int, int,
	 *      org.openmrs.module.billing.model.Company)
	 */
	public List<TenderBill> listTenderBillByCompany(int min, int max, Company company) throws APIException {
		return dao.listTenderBillByCompany(min, max, company);
	}
	
	/**
	 * @see org.openmrs.module.billing.BillingService#saveTenderBill(org.openmrs.module.billing.model.TenderBill)
	 */
	public TenderBill saveTenderBill(TenderBill tenderBill) throws APIException {
		return dao.saveTenderBill(tenderBill);
	}
	
	/**
	 * @see org.openmrs.module.billing.BillingService#getActiveTenders()
	 */
	public List<Tender> getActiveTenders() throws APIException {
		return dao.getActiveTenders();
	}
	
	/**
	 * @see org.openmrs.module.billing.BillingService#countListAmbulance()
	 */
	public int countListAmbulance() throws APIException {
		return dao.countListAmbulance();
	}
	
	/**
	 * @see org.openmrs.module.billing.BillingService#deleteAmbulance(org.openmrs.module.billing.model.Ambulance)
	 */
	public void deleteAmbulance(Ambulance ambulance) throws APIException {
		dao.deleteAmbulance(ambulance);
	}
	
	/**
	 * @see org.openmrs.module.billing.BillingService#getAllAmbulance()
	 */
	public List<Ambulance> getAllAmbulance() throws APIException {
		return dao.getAllAmbulance();
	}
	
	/**
	 * @see org.openmrs.module.billing.BillingService#getAmbulanceById(java.lang.Integer)
	 */
	public Ambulance getAmbulanceById(Integer id) throws APIException {
		return dao.getAmbulanceById(id);
	}
	
	/**
	 * @see org.openmrs.module.billing.BillingService#getAmbulanceByName(java.lang.String)
	 */
	public Ambulance getAmbulanceByName(String name) throws APIException {
		return dao.getAmbulanceByName(name);
	}
	
	/**
	 * @see org.openmrs.module.billing.BillingService#listAmbulance(int, int)
	 */
	public List<Ambulance> listAmbulance(int min, int max) throws APIException {
		return dao.listAmbulance(min, max);
	}
	
	/**
	 * @see org.openmrs.module.billing.BillingService#saveAmbulance(org.openmrs.module.billing.model.Ambulance)
	 */
	public Ambulance saveAmbulance(Ambulance ambulance) throws APIException {
		return dao.saveAmbulance(ambulance);
	}
	
	/**
	 * @see org.openmrs.module.billing.BillingService#getAllAmbulanceBill()
	 */
	public List<AmbulanceBill> getAllAmbulanceBill() throws APIException {
		return dao.getAllAmbulanceBill();
	}
	
	/**
	 * @see org.openmrs.module.billing.BillingService#getAmbulanceBillById(java.lang.Integer)
	 */
	public AmbulanceBill getAmbulanceBillById(Integer ambulanceBillId) throws APIException {
		return dao.getAmbulanceBillById(ambulanceBillId);
	}
	
	/**
	 * @see org.openmrs.module.billing.BillingService#saveAmbulanceBill(org.openmrs.module.billing.model.AmbulanceBill)
	 */
	public AmbulanceBill saveAmbulanceBill(AmbulanceBill ambulanceBill) throws APIException {
		return dao.saveAmbulanceBill(ambulanceBill);
	}
	
	/**
	 * @see org.openmrs.module.billing.BillingService#countListAmbulanceBillByDriver(org.openmrs.module.billing.model.Driver)
	 */
	public int countListAmbulanceBillByDriver(Driver driver) throws APIException {
		return dao.countListAmbulanceBillByDriver(driver);
	}
	
	/**
	 * @see org.openmrs.module.billing.BillingService#listAmbulanceBillByDriver(int, int,
	 *      org.openmrs.module.billing.model.Driver)
	 */
	public List<AmbulanceBill> listAmbulanceBillByDriver(int min, int max, Driver driver) throws APIException {
		return dao.listAmbulanceBillByDriver(min, max, driver);
	}
	
	/**
	 * @see org.openmrs.module.billing.BillingService#getActiveAmbulances()
	 */
	public List<Ambulance> getActiveAmbulances() throws APIException {
		return dao.getActiveAmbulances();
	}
	
	/**
	 * @see org.openmrs.module.billing.BillingService#traversServices(org.openmrs.Concept,
	 *      java.util.Map, int)
	 */
	public String traversServices(Concept con, Map<Integer, BillableService> services) {
		if (con == null)
			return null;
		Collection<ConceptAnswer> answers = con.getAnswers();
		ConceptClass conceptClass = con.getConceptClass();
		if (answers != null && answers.size() > 0 && !conceptClass.getName().equalsIgnoreCase("Test")
		        && !conceptClass.getName().equalsIgnoreCase("labset")) {
			
			String rs = "";
			rs += "<ul>";
			String child = null;
			for (ConceptAnswer ca : answers) {
				if (ca.getConcept() == con) {
					Concept tmpAnswerConcept = ca.getAnswerConcept();
					ConceptClass tmpConceptClass = tmpAnswerConcept.getConceptClass();
					
					Integer id = tmpAnswerConcept.getConceptId();
					if (ca.getAnswerConcept().getAnswers() == null || ca.getAnswerConcept().getAnswers().size() == 0
					        || tmpConceptClass.getName().equalsIgnoreCase("Test")
					        || tmpConceptClass.getName().equalsIgnoreCase("labset")
					        || tmpConceptClass.getName().equalsIgnoreCase("Procedure")) {
						rs += "<li>";
						rs += "<input   name='cons' type='checkbox'  value='" + id + "' >";
						rs += "<label>" + tmpAnswerConcept.getName() + "</label>";
						rs += "<input id='" + id + "_concept'  name='" + id + "_concept'  type='hidden' value='" + id + "'>";
						rs += "<input id='" + id + "_name'      name='" + id + "_name'      type='hidden' value='"
						        + tmpAnswerConcept.getName() + "'>";
						rs += "<input id='" + id + "_shortname' name='" + id + "_shortname' type='hidden' value='"
						        + tmpAnswerConcept.getName().getShortName() + "'>";
						BillableService s = services.get(id);
						if (s != null) {
							rs += "<span style='vertical-align:middle;'>";
							rs += "&nbsp;&nbsp;<input onblur='updatePrice(this)'  type='text' class='priceField' id='" + id
							        + "_price' name='" + id + "_price' value=" + s.getPrice() + ">";
							rs += "</span>";
						} else {
							rs += "<input onblur='updatePrice(this)' type='text' class='priceField' id='" + id
							        + "_price' name='" + id + "_price' value=''>";
						}
					} else {
						rs += "<li>";
						rs += "<input   name='cons' type='checkbox'  value='" + id + "' >";
						rs += "<label>" + tmpAnswerConcept.getName() + "</label>";
						rs += "<input id='" + id + "_concept'  name='" + id + "_concept'  type='hidden' value='" + id + "'>";
						rs += "<input id='" + id + "_name'      name='" + id + "_name'      type='hidden' value='"
						        + tmpAnswerConcept.getName() + "'>";
						rs += "<input id='" + id + "_shortname' name='" + id + "_shortname' type='hidden' value='"
						        + tmpAnswerConcept.getName().getShortName() + "'>";
						
					}
				}
				child = traversServices(ca.getAnswerConcept(), services);
				
				if (child != null)
					rs += child;
				rs += "</li>";
			}
			rs += "</ul>";
			return rs;
		} else if (con.getConceptSets() != null && con.getConceptSets().size() > 0
		        && !conceptClass.getName().equalsIgnoreCase("Test") && !conceptClass.getName().equalsIgnoreCase("labset")) {
			String rs = "";
			rs += "<ul>";
			String child = null;
			for (ConceptSet ca : con.getConceptSets()) {
				if (ca.getConceptSet() == con) {
					Integer id = ca.getConcept().getConceptId();
					ConceptClass tmpConceptClass = ca.getConcept().getConceptClass();
					if (ca.getConcept().getConceptSets() == null || ca.getConceptSet().getConceptSets().size() == 0
					        || tmpConceptClass.getName().equalsIgnoreCase("Test")
					        || tmpConceptClass.getName().equalsIgnoreCase("labset")
					        || tmpConceptClass.getName().equalsIgnoreCase("Procedure")) {
						rs += "<li>";
						rs += "<input name='cons' type='checkbox'  value='" + id + "' >";
						rs += "<label>" + ca.getConcept().getName() + "</label>";
						rs += "<input id='" + id + "_concept' name='" + id + "_concept' type='hidden' value='" + id + "'>";
						rs += "<input id='" + id + "_name' name='" + id + "_name' type='hidden' value='"
						        + ca.getConcept().getName() + "'>";
						rs += "<input id='" + id + "_shortname'    name='" + id + "_shortname'    type='hidden' value='"
						        + ca.getConcept().getName().getShortName() + "'>";
						BillableService s = services.get(id);
						if (s != null) {
							rs += "<input onblure='updatePrice(this)'  type='text' class='priceField' id='" + id
							        + "_price' name='" + id + "_price' value=" + s.getPrice() + ">";
						} else {
							rs += "<input onblur='updatePrice(this)'  class='priceField' type='text' id='" + id
							        + "_price' name='" + id + "_price' value=''>";
						}
					} else {
						rs += "<li>";
						rs += "<input name='cons' type='checkbox'  value='" + id + "' >";
						rs += "<label>" + ca.getConcept().getName() + "</label>";
						rs += "<input id='" + id + "_concept' name='" + id + "_concept' type='hidden' value='" + id + "'>";
						rs += "<input id='" + id + "_name' name='" + id + "_name' type='hidden' value='"
						        + ca.getConcept().getName() + "'>";
						rs += "<input id='" + id + "_shortname'    name='" + id + "_shortname'    type='hidden' value='"
						        + ca.getConcept().getName().getShortName() + "'>";
					}
				}
				child = traversServices(ca.getConcept(), services);
				if (child != null)
					rs += child;
				rs += "</li>";
			}
			rs += "</ul>";
			return rs;
		}
		return null;
	}
	
	/**
	 * @see org.openmrs.module.billing.BillingService#getAllServices()
	 */
	public List<BillableService> getAllServices() throws APIException {
		return dao.getAllServices();
	}
	
	/**
	 * @see org.openmrs.module.billing.BillingService#getServiceByConceptId(java.lang.Integer)
	 */
	public BillableService getServiceByConceptId(Integer conceptId) throws APIException {
		return dao.getServiceByConceptId(conceptId);
	}
	
	/**
	 * @see org.openmrs.module.billing.BillingService#getServiceById(java.lang.Integer)
	 */
	public BillableService getServiceById(Integer id) throws APIException {
		return dao.getServiceById(id);
	}
	
	/**
	 * @see org.openmrs.module.billing.BillingService#saveService(org.openmrs.module.billing.model.BillableService)
	 */
	public BillableService saveService(BillableService service) throws APIException {
		return dao.saveService(service);
	}
	
	/**
	 * @see org.openmrs.module.billing.BillingService#saveServices(java.util.Collection)
	 */
	public void saveServices(Collection<BillableService> services) throws APIException {
		for (BillableService service : services) {
			dao.saveService(service);
		}
	}
	
	/**
	 * @see org.openmrs.module.billing.BillingService#traversTab(org.openmrs.module.billing.model.BillableService,
	 *      int)
	 */
	public String traversTab(Concept concept, Map<Integer, BillableService> mapServices, int count) throws APIException {
		Integer rootConcept = Integer.valueOf(Context.getAdministrationService().getGlobalProperty(
		    BillingConstants.GLOBAL_PROPRETY_SERVICE_CONCEPT));
		Collection<ConceptAnswer> answers = concept.getAnswers();
		Collection<ConceptSet> conceptSets = concept.getConceptSets();
		ConceptClass conceptClass = concept.getConceptClass();
		BillableService service = mapServices.get(concept.getConceptId());
		if ((service == null || service.getDisable()) && concept.getConceptId().intValue() != rootConcept.intValue()) {
			return null;
		}
		String content = "";
		String tabsLi = "";
		String header = "";
		
		if (answers != null && answers.size() > 0 && !conceptClass.getName().equalsIgnoreCase("Test")
		        && !conceptClass.getName().equalsIgnoreCase("labset")) {
			// show children
			List<ConceptAnswer> children = new ArrayList(answers);
			Collections.sort(children, new ConceptAnswerComparator());
			
			List<ConceptAnswer> noChild = new ArrayList<ConceptAnswer>();
			BillableService s = null;
			for (ConceptAnswer ca : children) {
				if (ca.getConcept() == concept) {
					Concept tmpAnswerConcept = ca.getAnswerConcept();
					s = mapServices.get(tmpAnswerConcept.getId());
					if (s != null && !s.getDisable()) {
						ConceptClass tmpConceptClass = tmpAnswerConcept.getConceptClass();
						if (tmpAnswerConcept.getAnswers() == null || tmpAnswerConcept.getAnswers().size() == 0
						        || tmpConceptClass.getName().equalsIgnoreCase("Test")
						        || tmpConceptClass.getName().equalsIgnoreCase("labset")
						        || tmpConceptClass.getName().equalsIgnoreCase("Procedure")) {
							noChild.add(ca);
						} else {
							String name = StringUtils.isBlank(tmpAnswerConcept.getName().getShortName()) ? tmpAnswerConcept
							        .getName().getName() : tmpAnswerConcept.getName().getShortName();
							tabsLi += "<li><a title='" + tmpAnswerConcept.getName().getName() + "' href='#fragment-"
							        + tmpAnswerConcept.getConceptId() + "'><span>" + name + "</span></a></li>";
						}
					}
				}
			}
			ConceptAnswer tmpConcept = null;
			if (noChild.size() > 0) {
				for (int i = 1; i < noChild.size() + 1; i++) {
					tmpConcept = noChild.get(i - 1);
					BillableService tmpService = mapServices.get(tmpConcept.getAnswerConcept().getConceptId());
					if (tmpService != null && !tmpService.getDisable()) {
						String tmp = "addToBill(\"" + tmpService.getConceptId() + "\",  \"" + tmpService.getName() + "\", "
						        + tmpService.getPrice() + ", 1);";
						String name = StringUtils.isBlank(tmpService.getShortName()) ? tmpService.getName() : tmpService
						        .getShortName();
						header += "<div title='" + tmpService.getName() + "' id='box_" + tmpService.getConceptId()
						        + "' class='udiv boxNormal' onclick='" + tmp + "' >" + name + "</div>";
					}
				}
			}
			
			if (!"".equals(tabsLi)) {
				header += "<div id='container-" + count + "' class='divContainer'>";
				count = count + 1;
				header += "<ul>";
				header += tabsLi;
				header += "</ul>";
				
				String tmp = null;
				for (ConceptAnswer c : children) {
					Concept tmpC = c.getAnswerConcept();
					if ((tmpC.getAnswers() != null && tmpC.getAnswers().size() > 0)
					        || (tmpC.getConceptSets() != null && tmpC.getConceptSets().size() > 0)) {
						tmp = null;
						tmp = traversTab(tmpC, mapServices, count);
						if (tmp != null) {
							content += "<div id='fragment-" + tmpC.getConceptId() + "'>";
							content += tmp;
							content += "</div>";
						}
					}
					count++;
				}
				if (!"".equals(content))
					header += content;
				header += " </div>";
			}
		} else if (conceptSets != null && conceptSets.size() > 0 && !conceptClass.getName().equalsIgnoreCase("Test")
		        && !conceptClass.getName().equalsIgnoreCase("labset")) {
			// show children
			
			List<ConceptSet> children = new ArrayList(conceptSets);
			Collections.sort(children, new ConceptSetComparator());
			List<ConceptSet> noChild = new ArrayList<ConceptSet>();
			BillableService s = null;
			for (ConceptSet ca : children) {
				if (ca.getConceptSet() == concept) {
					Concept tmpConceptSet = ca.getConcept();
					s = mapServices.get(tmpConceptSet.getId());
					if (s != null && !s.getDisable()) {
						ConceptClass tmpConceptClass = ca.getConcept().getConceptClass();
						if (tmpConceptSet.getConceptSets() == null || tmpConceptSet.getConceptSets().size() == 0
						        || tmpConceptClass.getName().equalsIgnoreCase("Test")
						        || tmpConceptClass.getName().equalsIgnoreCase("labset")
						        || tmpConceptClass.getName().equalsIgnoreCase("Procedure")) {
							noChild.add(ca);
						} else {
							String name = StringUtils.isBlank(tmpConceptSet.getName().getShortName()) ? tmpConceptSet
							        .getName().getName() : tmpConceptSet.getName().getShortName();
							tabsLi += "<li><a title='" + tmpConceptSet.getName().getName() + "' href='#fragment-"
							        + tmpConceptSet.getConceptId() + "'><span>" + name + "</span></a></li>";
						}
					}
				}
			}
			ConceptSet tmpConcept = null;
			if (noChild.size() > 0) {
				for (int i = 1; i < noChild.size() + 1; i++) {
					tmpConcept = noChild.get(i - 1);
					BillableService tmpService = mapServices.get(tmpConcept.getConcept().getConceptId());
					if (tmpService != null && !tmpService.getDisable()) {
						String tmp = "addToBill(\"" + tmpService.getConceptId() + "\",  \"" + tmpService.getName() + "\", "
						        + tmpService.getPrice() + ", 1);";
						String name = StringUtils.isBlank(tmpService.getShortName()) ? tmpService.getName() : tmpService
						        .getShortName();
						header += "<div title='" + tmpService.getName() + "' id='box_" + tmpService.getConceptId()
						        + "' class='udiv boxNormal' onclick='" + tmp + "' >" + name + "</div>";
					}
				}
			}
			
			if (!"".equals(tabsLi)) {
				header += "<div id='container-" + count + "' class='divContainer'>";
				count = count + 1;
				header += "<ul>";
				header += tabsLi;
				header += "</ul>";
				
				String tmp = null;
				for (ConceptSet c : conceptSets) {
					Concept tmpC = c.getConcept();
					if ((tmpC.getAnswers() != null && tmpC.getAnswers().size() > 0)
					        || (tmpC.getConceptSets() != null && tmpC.getConceptSets().size() > 0)) {
						tmp = null;
						tmp = traversTab(tmpC, mapServices, count);
						if (tmp != null) {
							content += "<div id='fragment-" + tmpC.getConceptId() + "'>";
							content += tmp;
							content += "</div>";
						}
					}
					count++;
				}
				if (!"".equals(content))
					header += content;
				header += " </div>";
			}
		}
		return header;
	}
	
	/**
	 * @see org.openmrs.module.billing.BillingService#countListPatientServiceBillByPatient(org.openmrs.Patient)
	 */
	public int countListPatientServiceBillByPatient(Patient patient) throws APIException {
		return dao.countListPatientServiceBillByPatient(patient);
	}
	
	/**
	 * @see org.openmrs.module.billing.BillingService#getAllPatientServiceBill()
	 */
	public List<PatientServiceBill> getAllPatientServiceBill() throws APIException {
		return dao.getAllPatientServiceBill();
	}
	
	/**
	 * @see org.openmrs.module.billing.BillingService#getPatientServiceBillById(java.lang.Integer)
	 */
	public PatientServiceBill getPatientServiceBillById(Integer patientServiceBillId) throws APIException {
		return dao.getPatientServiceBillById(patientServiceBillId);
	}
	
	/**
	 * @see org.openmrs.module.billing.BillingService#listPatientServiceBillByPatient(int, int,
	 *      org.openmrs.Patient)
	 */
	public List<PatientServiceBill> listPatientServiceBillByPatient(int min, int max, Patient patient) throws APIException {
		return dao.listPatientServiceBillByPatient(min, max, patient);
	}
	
	/**
	 * @see org.openmrs.module.billing.BillingService#savePatientServiceBill(org.openmrs.module.billing.model.PatientServiceBill)
	 */
	public PatientServiceBill savePatientServiceBill(PatientServiceBill patientServiceBill) throws APIException {
		return dao.savePatientServiceBill(patientServiceBill);
	}
	
	/**
	 * @see org.openmrs.module.billing.BillingService#saveBillEncounterAndOrder(org.openmrs.module.billing.model.PatientServiceBill,
	 *      java.lang.Integer, java.lang.Integer, java.lang.Integer)
	 */
	// public void saveBillEncounterAndOrder(PatientServiceBill bill)
	// throws APIException {
	//
	// Integer gpOrderType = Integer.valueOf(Context
	// .getAdministrationService().getGlobalProperty(
	// "billing.orderTypeId"));
	// Integer gpEncounterType = Integer.valueOf(Context
	// .getAdministrationService().getGlobalProperty(
	// "billing.encounterTypeId"));
	// Integer gpLocation = Integer.valueOf(Context.getAdministrationService()
	// .getGlobalProperty("billing.locationId"));
	// String medicalExaminationIds = Context.getAdministrationService()
	// .getGlobalProperty("billing.medicalExaminationIds");
	//
	// EncounterType encounterType = Context.getEncounterService()
	// .getEncounterType(gpEncounterType);
	// Location location = Context.getLocationService()
	// .getLocation(gpLocation);
	// String[] meIds = medicalExaminationIds.split(",");
	// LabService labService = Context.getService(LabService.class);
	// List<Lab> labs = labService.getAllLab();
	// // Set<PatientServiceBillItem> items = new
	// // HashSet<PatientServiceBillItem>();
	// Encounter enc = new Encounter();
	// enc.setCreator(bill.getCreator());
	// enc.setLocation(location);
	// enc.setDateCreated(new Date());
	// enc.setEncounterDatetime(new Date());
	// enc.setProvider(bill.getCreator());
	// enc.setEncounterType(encounterType);
	// enc.setPatient(bill.getPatient());
	// Lab lab = null;
	// for (PatientServiceBillItem item : bill.getBillItems()) {
	// // PatientServiceBillItem tmpItem = item;
	// Concept concept = Context.getConceptService().getConcept(
	// item.getService().getConceptId());
	//
	// // If item is a medical examination set
	// if (concept.getConceptClass().equals(
	// ConceptClassUtil.getMedicalExamminationClass())) {
	// Collection<ConceptAnswer> answers = concept.getAnswers();
	// Collection<ConceptSet> conceptSets = concept.getConceptSets();
	// if (answers != null && answers.size() > 0) {
	// for (ConceptAnswer ans : answers) {
	//
	// lab = labService.getLabByConcept(
	// ans.getAnswerConcept(), labs);
	// if (lab == null) {
	// // throw new APIException(
	// // "Can not find lab for test: "
	// // + concept.getDisplayString());
	// log.info("Can not find lab for test: "
	// + concept.getDisplayString());
	// } else {
	// Order order = new Order();
	// order.setConcept(ans.getConcept());
	// order.setCreator(bill.getCreator());
	// order.setDateCreated(new Date());
	// order.setOrderer(bill.getCreator());
	// order.setPatient(bill.getPatient());
	// order.setStartDate(new Date());
	// order.setAccessionNumber("0");
	// order.setOrderType(lab.getLabOrderType());
	// order.setEncounter(enc);
	// // tmpItem.setOrder(order);
	// item.setOrder(order);
	// enc.addOrder(order);
	// }
	//
	// }
	// }
	// if (conceptSets != null && conceptSets.size() > 0) {
	// for (ConceptSet con : conceptSets) {
	// lab = labService
	// .getLabByConcept(con.getConcept(), labs);
	// if (lab == null) {
	// // throw new APIException(
	// // "Can not find lab for test: "
	// // + concept.getDisplayString());
	// log.info("Can not find lab for test: "
	// + concept.getDisplayString());
	// } else {
	// Order order = new Order();
	// order.setConcept(con.getConcept());
	// order.setCreator(bill.getCreator());
	// order.setDateCreated(new Date());
	// order.setOrderer(bill.getCreator());
	// order.setPatient(bill.getPatient());
	// order.setStartDate(new Date());
	// order.setAccessionNumber("0");
	// order.setOrderType(lab.getLabOrderType());
	// order.setEncounter(enc);
	// // tmpItem.setOrder(order);
	// item.setOrder(order);
	// enc.addOrder(order);
	// }
	//
	// }
	// }
	//
	// } else {
	// lab = labService.getLabByConcept(concept, labs);
	// if (lab == null) {
	// // throw new APIException("Can not find lab for test: "
	// // + concept.getDisplayString());
	// log.info("Can not find lab for test: "
	// + concept.getDisplayString());
	// } else {
	// Order order = new Order();
	// order.setConcept(concept);
	// order.setCreator(bill.getCreator());
	// order.setDateCreated(new Date());
	// order.setOrderer(Context.getAuthenticatedUser());
	// order.setPatient(bill.getPatient());
	// order.setStartDate(new Date());
	// order.setAccessionNumber("0"); // TODO what is this ???
	// order.setOrderType(lab.getLabOrderType());
	// order.setEncounter(enc);
	// // tmpItem.setOrder(order);
	// item.setOrder(order);
	// enc.addOrder(order);
	// }
	//
	// }
	//
	// // items.add(tmpItem);
	// }// end for loop
	// Context.getEncounterService().saveEncounter(enc);
	// // bill.setBillItems(items);
	// savePatientServiceBill(bill);
	// }
	
	public void saveBillEncounterAndOrder(PatientServiceBill bill) throws APIException {
		Set<Integer> labConceptIds = getLabConceptIds();
		Set<Integer> radiologyConceptIds = getRadiologyConceptIds();
		
		String labEncounterTypeText = GlobalPropertyUtil.getString(BillingConstants.GLOBAL_PROPRETY_LAB_ENCOUNTER_TYPE,
		    "LABENCOUNTER");
		EncounterType labEncounterType = Context.getEncounterService().getEncounterType(labEncounterTypeText);
		
		String radiologyEncounterTypeText = GlobalPropertyUtil.getString(
		    BillingConstants.GLOBAL_PROPRETY_RADIOLOGY_ENCOUNTER_TYPE, "RADIOLOGYENCOUNTER");
		EncounterType radiologyEncounterType = Context.getEncounterService().getEncounterType(radiologyEncounterTypeText);
		
		Integer labOrderTypeId = GlobalPropertyUtil.getInteger(BillingConstants.GLOBAL_PROPRETY_LAB_ORDER_TYPE, 2);
		OrderType labOrderType = Context.getOrderService().getOrderType(labOrderTypeId);
		
		Integer radiologyOrderTypeId = GlobalPropertyUtil.getInteger(BillingConstants.GLOBAL_PROPRETY_RADIOLOGY_ORDER_TYPE,
		    8);
		OrderType radiologyOrderType = Context.getOrderService().getOrderType(radiologyOrderTypeId);
		
		Encounter labEncounter = null;
		Encounter radiologyEncounter = null;
		
		// Get medical examination class
		Integer medicalExaminationClassId = GlobalPropertyUtil.getInteger(
		    HospitalCoreConstants.PROPERTY_MEDICAL_EXAMINATION, 9);
		ConceptClass medicalExaminationClass = Context.getConceptService().getConceptClass(medicalExaminationClassId);
		
		for (PatientServiceBillItem item : bill.getBillItems()) {
			Concept concept = Context.getConceptService().getConcept(item.getService().getConceptId());
			
			// If item is a medical examination set
			if (concept.getConceptClass().equals(medicalExaminationClass)) {
				Collection<ConceptSet> conceptSets = concept.getConceptSets();
				if (conceptSets != null && conceptSets.size() > 0) {
					for (ConceptSet con : conceptSets) {
						if (labConceptIds.contains(con.getConcept().getConceptId())) {
							labEncounter = getEncounter(bill, labEncounter, labEncounterType);
							Order order = addOrder(labEncounter, con.getConcept(), bill, labOrderType);
							item.setOrder(order);
							
						} else if (radiologyConceptIds.contains(con.getConcept().getConceptId())) {
							radiologyEncounter = getEncounter(bill, radiologyEncounter, radiologyEncounterType);
							Order order = addOrder(radiologyEncounter, con.getConcept(), bill, radiologyOrderType);
							item.setOrder(order);
						}
					}
				}
			} else {
				if (labConceptIds.contains(concept.getConceptId())) {
					labEncounter = getEncounter(bill, labEncounter, labEncounterType);
					Order order = addOrder(labEncounter, concept, bill, labOrderType);
					item.setOrder(order);
					
				} else if (radiologyConceptIds.contains(concept.getConceptId())) {
					radiologyEncounter = getEncounter(bill, radiologyEncounter, radiologyEncounterType);
					Order order = addOrder(radiologyEncounter, concept, bill, radiologyOrderType);
					item.setOrder(order);
				}
			}
		}
		
		if (labEncounter != null) {
			Context.getEncounterService().saveEncounter(labEncounter);
		}
		if (radiologyEncounter != null) {
			Context.getEncounterService().saveEncounter(radiologyEncounter);
		}
		savePatientServiceBill(bill);
	}
	
	private Set<Integer> getLabConceptIds() {
		Set<Integer> conceptIdSet = new HashSet<Integer>();
		LabService ls = (LabService) Context.getService(LabService.class);
		List<Lab> labs = ls.getAllLab();
		for (Lab lab : labs) {
			conceptIdSet.addAll(getConceptIdSet(lab.getInvestigationsToDisplay()));
		}
		return conceptIdSet;
	}
	
	private Set<Integer> getRadiologyConceptIds() {
		Set<Integer> conceptIdSet = new HashSet<Integer>();
		RadiologyCoreService rcs = (RadiologyCoreService) Context.getService(RadiologyCoreService.class);
		List<RadiologyDepartment> departments = rcs.getAllRadiologyDepartments();
		for (RadiologyDepartment department : departments) {
			conceptIdSet.addAll(getConceptIdSet(department.getInvestigations()));
		}
		return conceptIdSet;
	}
	
	private Set<Integer> getConceptIdSet(Set<Concept> concepts) {
		Set<Integer> conceptIdSet = new HashSet<Integer>();
		for (Concept concept : concepts) {
			TestTree tree = new TestTree(concept);
			conceptIdSet.addAll(tree.getConceptIDSet());
		}
		return conceptIdSet;
	}
	
	private Encounter getEncounter(PatientServiceBill bill, Encounter encounter, EncounterType encounterType) {
		if (encounter == null) {
			Encounter enc = new Encounter();
			enc.setCreator(bill.getCreator());
			Location location = Context.getLocationService().getLocation(1);
			enc.setLocation(location);
			enc.setDateCreated(new Date());
			enc.setEncounterDatetime(new Date());
			enc.setProvider(bill.getCreator());
			enc.setEncounterType(encounterType);
			enc.setPatient(bill.getPatient());
			return enc;
		} else {
			return encounter;
		}
	}
	
	private Order addOrder(Encounter encounter, Concept concept, PatientServiceBill bill, OrderType orderType) {
		Order order = new Order();
		order.setConcept(concept);
		order.setCreator(bill.getCreator());
		order.setDateCreated(new Date());
		order.setOrderer(Context.getAuthenticatedUser());
		order.setPatient(bill.getPatient());
		order.setStartDate(new Date());
		order.setAccessionNumber("0");
		order.setOrderType(orderType);
		order.setEncounter(encounter);
		encounter.addOrder(order);
		return order;
	}
	
	/**
	 * @see org.openmrs.module.billing.BillingService#getAllActiveCompany()
	 */
	public List<Company> getAllActiveCompany() throws APIException {
		return dao.getAllActiveCompany();
	}
	
	/**
	 * @see org.openmrs.module.billing.BillingService#getAllActiveDriver()
	 */
	public List<Driver> getAllActiveDriver() throws APIException {
		return dao.getAllActiveDriver();
	}
	
	public void disableService(Integer conceptId) throws APIException {
		dao.disableService(conceptId);
	}
	
	/**
	 * TODO void Orders after void a bill
	 **/
	public void voidBill(PatientServiceBill bill) throws APIException {
		bill.setVoided(true);
		savePatientServiceBill(bill);
		Patient pat = bill.getPatient();
	}
	
	public int countListMiscellaneousService() throws APIException {
		return dao.countListMiscellaneousService();
	}
	
	public int countListMiscellaneousServiceBill() throws APIException {
		return dao.countListMiscellaneousServiceBill();
	}
	
	public List<MiscellaneousService> getAllMiscellaneousService() throws APIException {
		return dao.getAllMiscellaneousService();
	}
	
	public List<MiscellaneousServiceBill> getAllMiscellaneousServiceBill() throws APIException {
		return dao.getAllMiscellaneousServiceBill();
	}
	
	public MiscellaneousServiceBill getMiscellaneousServiceBillById(Integer miscellaneousServiceBillId) throws APIException {
		return dao.getMiscellaneousServiceBillById(miscellaneousServiceBillId);
	}
	
	public MiscellaneousService getMiscellaneousServiceById(Integer miscellaneousServiceId) throws APIException {
		return dao.getMiscellaneousServiceById(miscellaneousServiceId);
	}
	
	public List<MiscellaneousService> listMiscellaneousService(int min, int max) throws APIException {
		return dao.listMiscellaneousService(min, max);
	}
	
	public List<MiscellaneousServiceBill> listMiscellaneousServiceBill(int min, int max) throws APIException {
		return dao.listMiscellaneousServiceBill(min, max);
	}
	
	public MiscellaneousService saveMiscellaneousService(MiscellaneousService MiscellaneousService) throws APIException {
		return dao.saveMiscellaneousService(MiscellaneousService);
	}
	
	public MiscellaneousServiceBill saveMiscellaneousServiceBill(MiscellaneousServiceBill MiscellaneousServiceBill)
	                                                                                                               throws APIException {
		return dao.saveMiscellaneousServiceBill(MiscellaneousServiceBill);
	}
	
	public void deleteMiscellaneousService(MiscellaneousService miscellaneousService) throws APIException {
		dao.deleteMiscellaneousService(miscellaneousService);
	}
	
	public MiscellaneousService getMiscellaneousServiceByName(String name) throws APIException {
		return dao.getMiscellaneousServiceByName(name);
	}
	
	public int countListMiscellaneousServiceBill(MiscellaneousService service) throws APIException {
		return dao.countListMiscellaneousServiceBill(service);
	}
	
	public List<MiscellaneousServiceBill> listMiscellaneousServiceBill(int min, int max, MiscellaneousService service)
	                                                                                                                  throws APIException {
		return dao.listMiscellaneousServiceBill(min, max, service);
	}
	
	public Receipt createReceipt() throws APIException {
		Receipt receipt = new Receipt();
		receipt.setPaidDate(new Date());
		return dao.createReceipt(receipt);
		
	}
	
	public void updateReceipt() throws APIException {
		dao.updateReceipt();
	}
	
	public void updateOldBills() {
		dao.updateOldBills();
	}
	
	/**
	 * @see org.openmrs.module.billing.BillingService#getPatientServiceBillByReceiptId(java.lang.Integer)
	 */
	public PatientServiceBill getPatientServiceBillByReceiptId(Integer patientServiceBillReceiptId) throws APIException {
		return dao.getPatientServiceBillByReceiptId(patientServiceBillReceiptId);
	}
	
	public IndoorPatientServiceBill saveIndoorPatientServiceBill(IndoorPatientServiceBill indoorPatientServiceBill) throws APIException {
		return dao.saveIndoorPatientServiceBill(indoorPatientServiceBill);
	}
	
	public void deleteIndoorPatientServiceBill(IndoorPatientServiceBill indoorPatientServiceBill) throws APIException {
		dao.deleteIndoorPatientServiceBill(indoorPatientServiceBill);
	}
	
	public IndoorPatientServiceBill getIndoorPatientServiceBillById(Integer indoorPatientServiceBillId) throws APIException {
		return dao.getIndoorPatientServiceBillById(indoorPatientServiceBillId);
	}
	
	public void saveBillEncounterAndOrderForIndoorPatient(IndoorPatientServiceBill bill) throws APIException {
		Set<Integer> labConceptIds = getLabConceptIds();
		Set<Integer> radiologyConceptIds = getRadiologyConceptIds();
		
		String labEncounterTypeText = GlobalPropertyUtil.getString(BillingConstants.GLOBAL_PROPRETY_LAB_ENCOUNTER_TYPE,
		    "LABENCOUNTER");
		EncounterType labEncounterType = Context.getEncounterService().getEncounterType(labEncounterTypeText);
		
		String radiologyEncounterTypeText = GlobalPropertyUtil.getString(
		    BillingConstants.GLOBAL_PROPRETY_RADIOLOGY_ENCOUNTER_TYPE, "RADIOLOGYENCOUNTER");
		EncounterType radiologyEncounterType = Context.getEncounterService().getEncounterType(radiologyEncounterTypeText);
		
		Integer labOrderTypeId = GlobalPropertyUtil.getInteger(BillingConstants.GLOBAL_PROPRETY_LAB_ORDER_TYPE, 2);
		OrderType labOrderType = Context.getOrderService().getOrderType(labOrderTypeId);
		
		Integer radiologyOrderTypeId = GlobalPropertyUtil.getInteger(BillingConstants.GLOBAL_PROPRETY_RADIOLOGY_ORDER_TYPE,
		    8);
		OrderType radiologyOrderType = Context.getOrderService().getOrderType(radiologyOrderTypeId);
		
		Encounter labEncounter = null;
		Encounter radiologyEncounter = null;
		
		// Get medical examination class
		Integer medicalExaminationClassId = GlobalPropertyUtil.getInteger(
		    HospitalCoreConstants.PROPERTY_MEDICAL_EXAMINATION, 9);
		ConceptClass medicalExaminationClass = Context.getConceptService().getConceptClass(medicalExaminationClassId);
		
		for (IndoorPatientServiceBillItem item : bill.getBillItems()) {
			Concept concept = Context.getConceptService().getConcept(item.getService().getConceptId());
			
			// If item is a medical examination set
			if (concept.getConceptClass().equals(medicalExaminationClass)) {
				Collection<ConceptSet> conceptSets = concept.getConceptSets();
				if (conceptSets != null && conceptSets.size() > 0) {
					for (ConceptSet con : conceptSets) {
						if (labConceptIds.contains(con.getConcept().getConceptId())) {
							labEncounter = getEncounter(bill, labEncounter, labEncounterType);
							Order order = addOrder(labEncounter, con.getConcept(), bill, labOrderType);
							item.setOrder(order);
							
						} else if (radiologyConceptIds.contains(con.getConcept().getConceptId())) {
							radiologyEncounter = getEncounter(bill, radiologyEncounter, radiologyEncounterType);
							Order order = addOrder(radiologyEncounter, con.getConcept(), bill, radiologyOrderType);
							item.setOrder(order);
						}
					}
				}
			} else {
				if (labConceptIds.contains(concept.getConceptId())) {
					labEncounter = getEncounter(bill, labEncounter, labEncounterType);
					Order order = addOrder(labEncounter, concept, bill, labOrderType);
					item.setOrder(order);
					
				} else if (radiologyConceptIds.contains(concept.getConceptId())) {
					radiologyEncounter = getEncounter(bill, radiologyEncounter, radiologyEncounterType);
					Order order = addOrder(radiologyEncounter, concept, bill, radiologyOrderType);
					item.setOrder(order);
				}
			}
		}
		
		/*
		if (labEncounter != null) {
			Context.getEncounterService().saveEncounter(labEncounter);
		}
		if (radiologyEncounter != null) {
			Context.getEncounterService().saveEncounter(radiologyEncounter);
		}
		*/
		saveIndoorPatientServiceBill(bill);
	}
	
	private Encounter getEncounter(IndoorPatientServiceBill bill, Encounter encounter, EncounterType encounterType) {
		if (encounter == null) {
			Encounter enc = new Encounter();
			enc.setCreator(bill.getCreator());
			Location location = Context.getLocationService().getLocation(1);
			enc.setLocation(location);
			enc.setDateCreated(new Date());
			enc.setEncounterDatetime(new Date());
			enc.setProvider(bill.getCreator());
			enc.setEncounterType(encounterType);
			enc.setPatient(bill.getPatient());
			Context.getEncounterService().saveEncounter(enc);
			return enc;
		} else {
			return encounter;
		}
	}
	
	private Order addOrder(Encounter encounter, Concept concept, IndoorPatientServiceBill bill, OrderType orderType) {
		Order order = new Order();
		order.setConcept(concept);
		order.setCreator(bill.getCreator());
		order.setDateCreated(new Date());
		order.setOrderer(Context.getAuthenticatedUser());
		order.setPatient(bill.getPatient());
		order.setStartDate(new Date());
		order.setAccessionNumber("0");
		order.setOrderType(orderType);
		order.setEncounter(encounter);
		encounter.addOrder(order);
		return order;
	}
	//New Requirement add Paid bill & Free bill Both 
	public PatientServiceBillItem getPatientServiceBillItem(Integer billId,String name) throws APIException {
		return dao.getPatientServiceBillItem(billId,name);
	}
	
	 public List<PatientSearch> searchListOfPatient(Date date, String searchKey,int page, int pgSize) throws APIException {
			return dao.searchListOfPatient(date,searchKey,page,pgSize);
		}
	 
	 public int countSearchListOfPatient(Date date, String searchKey,int page) throws APIException {
			return dao.countSearchListOfPatient(date,searchKey,page);
		}
	 
	 public List<OpdTestOrder> listOfOrder(Integer patientId,Date date) throws APIException {
			return dao.listOfOrder(patientId,date);
		}
	 
	 public List<BillableService> listOfServiceOrder(Integer patientId, Integer encounterId) throws APIException {
			return dao.listOfServiceOrder(patientId,encounterId);
		}
	 
	 public BillableService getServiceByConceptName(String conceptName) throws APIException {
			return dao.getServiceByConceptName(conceptName);
		}
	 
	 public OpdTestOrder getOpdTestOrder(Integer encounterId,Integer conceptId) throws APIException {
			return dao.getOpdTestOrder(encounterId,conceptId);
		}
}
