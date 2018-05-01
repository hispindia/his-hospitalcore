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

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.openmrs.Concept;
import org.openmrs.Encounter;
import org.openmrs.Order;
import org.openmrs.OrderType;
import org.openmrs.Patient;
import org.openmrs.Role;
import org.openmrs.api.context.Context;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.hospitalcore.BillingConstants;
import org.openmrs.module.hospitalcore.RadiologyService;
import org.openmrs.module.hospitalcore.concept.ConceptNode;
import org.openmrs.module.hospitalcore.concept.TestTree;
import org.openmrs.module.hospitalcore.db.RadiologyDAO;
import org.openmrs.module.hospitalcore.form.RadiologyForm;
import org.openmrs.module.hospitalcore.model.RadiologyDepartment;
import org.openmrs.module.hospitalcore.model.RadiologyTest;
import org.openmrs.module.hospitalcore.template.RadiologyTemplate;
import org.openmrs.module.hospitalcore.util.GlobalPropertyUtil;
import org.openmrs.module.hospitalcore.util.PatientUtils;
import org.openmrs.module.hospitalcore.util.RadiologyConstants;

public class RadiologyServiceImpl extends BaseOpenmrsService implements
		RadiologyService {

	public RadiologyServiceImpl() {
	}

	protected RadiologyDAO dao;

	public void setDao(RadiologyDAO dao) {
		this.dao = dao;
	}

	//
	// RADIOLOGY FORM
	//
	public RadiologyForm saveRadiologyForm(RadiologyForm form) {
		return dao.saveRadiologyForm(form);
	}

	public RadiologyForm getRadiologyFormById(Integer id) {
		return dao.getRadiologyFormById(id);
	}

	public List<RadiologyForm> getAllRadiologyForms() {
		return dao.getAllRadiologyForms();
	}

	public void deleteRadiologyForm(RadiologyForm form) {
		dao.deleteRadiologyForm(form);
	}

	public List<RadiologyForm> getRadiologyForms(String conceptName) {
		return dao.getRadiologyForms(conceptName);
	}

	public RadiologyForm getDefaultForm() {
		Integer formId = GlobalPropertyUtil.getInteger(
				RadiologyConstants.PROPERTY_FORM_DEFAULTXRAY, 0);
		RadiologyForm form = getRadiologyFormById(formId);
		return form;
	}

	//
	// RADIOLOGY DEPARTMENT
	//
	public RadiologyDepartment saveRadiologyDepartment(
			RadiologyDepartment department) {
		return dao.saveRadiologyDepartment(department);
	}

	public RadiologyDepartment getRadiologyDepartmentById(Integer id) {
		return dao.getRadiologyDepartmentById(id);
	}

	public void deleteRadiologyDepartment(RadiologyDepartment department) {
		dao.deleteRadiologyDepartment(department);
	}

	public RadiologyDepartment getCurrentRadiologyDepartment() {
		Set<Role> roles = Context.getAuthenticatedUser().getAllRoles();
		List<RadiologyDepartment> departments = new ArrayList<RadiologyDepartment>();
		for (Role role : roles) {
			RadiologyDepartment department = dao
					.getRadiologyDepartmentByRole(role);
			if (department != null) {
				departments.add(department);
			}
		}

		if (!departments.isEmpty()) {
			return departments.get(0);
		} else {
			return null;
		}
	}

	//
	// ORDER
	//
	public List<Order> getOrders(Date startDate, String phrase,
			Set<Concept> tests, int page) throws ParseException {

		Integer radiologyOrderTypeId = GlobalPropertyUtil.getInteger(
				BillingConstants.GLOBAL_PROPRETY_RADIOLOGY_ORDER_TYPE, 8);
		OrderType orderType = Context.getOrderService().getOrderType(
				radiologyOrderTypeId);

		List<Patient> patients = null;
		if (!StringUtils.isBlank(phrase)) {
			patients = Context.getPatientService().getPatients(phrase);
		}
		List<Order> orders = dao.getOrders(startDate, orderType, tests,
				patients, page, GlobalPropertyUtil.getInteger(
						RadiologyConstants.PROPERTY_PAGESIZE, 20));
		return orders;
	}

	public Integer countOrders(Date startDate, String phrase, Set<Concept> tests)
			throws ParseException {

		Integer radiologyOrderTypeId = GlobalPropertyUtil.getInteger(
				BillingConstants.GLOBAL_PROPRETY_RADIOLOGY_ORDER_TYPE, 8);
		OrderType orderType = Context.getOrderService().getOrderType(
				radiologyOrderTypeId);

		List<Patient> patients = Context.getPatientService()
				.getPatients(phrase);
		return dao.countOrders(startDate, orderType, tests, patients);
	}

	//
	// RADIOLOGY TEST
	//
	public RadiologyTest saveRadiologyTest(RadiologyTest test) {
		return dao.saveRadiologyTest(test);
	}

	public RadiologyTest getRadiologyTestById(Integer id) {
		return dao.getRadiologyTestById(id);
	}

	public List<RadiologyTest> getAllRadiologyTests() {
		return dao.getAllRadiologyTests();
	}

	public void deleteRadiologyTest(RadiologyTest test) {
		dao.deleteRadiologyTest(test);
	}

	public Integer acceptTest(Order order) {
		RadiologyTest test = dao.getRadiologyTestByOrder(order);
		if (test == null) {
			test = new RadiologyTest();
			test.setOrder(order);
			test.setPatient(order.getPatient());
			test.setConcept(order.getConcept());
			test.setCreator(Context.getAuthenticatedUser());
			test.setDate(new Date());
			test.setStatus(RadiologyConstants.TEST_STATUS_ACCEPTED);
			List<RadiologyForm> forms = dao.getRadiologyForms(order
					.getConcept().getName().getName());
			if (CollectionUtils.isEmpty(forms)) {
				test.setForm(getDefaultForm());
			} else {
				test.setForm(forms.get(0));
			}

			RadiologyTest acceptedTest = dao.saveRadiologyTest(test);
			return acceptedTest.getId();
		}
		return -1;
	}

	public String unacceptTest(RadiologyTest test) {
		if (test != null) {
			if (test.getStatus().equalsIgnoreCase(
					RadiologyConstants.TEST_STATUS_ACCEPTED)) {
				dao.deleteRadiologyTest(test);
				return RadiologyConstants.UNACCEPT_TEST_RETURN_STATUS_SUCCESS;
			} else {
				// TODO: add more unaccept test return status here
			}
		} else {
			return RadiologyConstants.UNACCEPT_TEST_RETURN_STATUS_NOT_FOUND;
		}
		return null;
	}

	public String rescheduleTest(Order order, Date rescheduledDate) {

		if (!order.getDiscontinued()) {
			RadiologyTest test = getRadiologyTestByOrder(order);
			if (test != null) {
				if (test.getStatus().equalsIgnoreCase(
						RadiologyConstants.TEST_STATUS_ACCEPTED)) {
					order.setStartDate(rescheduledDate);
					order.setChangedBy(Context.getAuthenticatedUser());
					order.setDateChanged(new Date());
					deleteRadiologyTest(test);
					Context.getOrderService().saveOrder(order);
					return RadiologyConstants.RESCHEDULE_TEST_RETURN_STATUS_SUCCESS;
				} else {
					// TODO: add more reschedule test return status here
					return test.getStatus();
				}
			} else {
				order.setStartDate(rescheduledDate);
				order.setChangedBy(Context.getAuthenticatedUser());
				order.setDateChanged(new Date());
				Context.getOrderService().saveOrder(order);
				return RadiologyConstants.RESCHEDULE_TEST_RETURN_STATUS_SUCCESS;
			}
		}

		return RadiologyConstants.RESCHEDULE_TEST_RETURN_STATUS_ENTERED;
	}

	public RadiologyTest getRadiologyTestByOrder(Order order) {
		return dao.getRadiologyTestByOrder(order);
	}

	public List<RadiologyTest> getAcceptedRadiologyTests(Date date,
			String phrase, Set<Concept> allowableTests, int page)
			throws ParseException {
		List<Patient> patients = null;
		if (!StringUtils.isBlank(phrase)) {
			patients = Context.getPatientService().getPatients(phrase);
		}
		return dao.getRadiologyTests(date,
				RadiologyConstants.TEST_STATUS_ACCEPTED, allowableTests,
				patients, page, GlobalPropertyUtil.getInteger(
						RadiologyConstants.PROPERTY_PAGESIZE, 20));
	}

	public Integer countAcceptedRadiologyTests(Date date, String phrase,
			Set<Concept> allowableTests) throws ParseException {
		List<Patient> patients = null;
		if (!StringUtils.isBlank(phrase)) {
			patients = Context.getPatientService().getPatients(phrase);
		}
		return dao.countRadiologyTests(date,
				RadiologyConstants.TEST_STATUS_ACCEPTED, allowableTests,
				patients);
	}

	public List<RadiologyTest> getCompletedRadiologyTests(Date date,
			String phrase, Set<Concept> allowableTests, int page)
			throws ParseException {

		List<Patient> patients = null;
		if (!StringUtils.isBlank(phrase)) {
			patients = Context.getPatientService().getPatients(phrase);
		}

		List<RadiologyTest> tests = dao.getRadiologyTestsByDiscontinuedDate(
				date, allowableTests, patients, page, GlobalPropertyUtil
						.getInteger(RadiologyConstants.PROPERTY_PAGESIZE, 20));

		return tests;
	}

	public Integer countCompletedRadiologyTests(Date date, String phrase,
			Set<Concept> allowableTests) throws ParseException {

		List<Patient> patients = null;
		if (!StringUtils.isBlank(phrase)) {
			patients = Context.getPatientService().getPatients(phrase);
		}

		return dao.countRadiologyTestsByDiscontinuedDate(date, allowableTests,
				patients);

	}

	public List<RadiologyTest> getAllRadiologyTestsByDate(Date date,
			String phrase, Concept investigation) throws ParseException {
		List<RadiologyTest> tests = dao.getRadiologyTestsByDate(date);
		return filterRadiologyTests(tests, phrase, investigation);
	}

	private List<RadiologyTest> filterRadiologyTests(List<RadiologyTest> tests,
			String phrase, Concept investigation) {

		// patient filtered
		List<RadiologyTest> patientFilteredOrders = new ArrayList<RadiologyTest>();
		for (RadiologyTest test : tests) {
			Patient patient = test.getPatient();
			String fullname = PatientUtils.getFullName(patient).toLowerCase();
			String identifier = patient.getPatientIdentifier().getIdentifier()
					.toLowerCase();
			phrase = phrase.toLowerCase();
			if ((fullname.contains(phrase)) || (identifier.contains(phrase))) {
				patientFilteredOrders.add(test);
			}
		}

		// investigation filtered
		List<RadiologyTest> investigationFilteredOrders = new ArrayList<RadiologyTest>();
		if (investigation != null) {
			TestTree tree = new TestTree(investigation);
			Set<ConceptNode> nodes = tree.getRootNode().getChildNodes();
			for (RadiologyTest test : patientFilteredOrders) {
				ConceptNode node = new ConceptNode(test.getConcept());
				if (nodes.contains(node)) {
					investigationFilteredOrders.add(test);
				}
			}
		} else {
			investigationFilteredOrders = patientFilteredOrders;
		}

		return investigationFilteredOrders;
	}

	public String completeTest(RadiologyTest test) {

		if (test.getStatus() != null) {
			if ((test.getStatus().equalsIgnoreCase(
					RadiologyConstants.TEST_STATUS_ACCEPTED) || (test
					.getStatus()
					.equalsIgnoreCase(RadiologyConstants.TEST_STATUS_COMPLETED)))) {
				Order order = test.getOrder();
				order.setDiscontinued(true);
				order.setDiscontinuedDate(new Date());
				order.setDiscontinuedBy(Context.getAuthenticatedUser());
				Context.getOrderService().saveOrder(order);
				test.setStatus(RadiologyConstants.TEST_STATUS_COMPLETED);
				saveRadiologyTest(test);
				return RadiologyConstants.COMPLETE_TEST_RETURN_STATUS_SUCCESS;
			}
		}
		return RadiologyConstants.COMPLETE_TEST_RETURN_STATUS_NOT_ACCEPTED;
	}

	/*
	 * private int getEncounterAgainstFormNumber(RadiologyTest test) { int
	 * encounterAgainstForm = 0; if (test.getGivenForm() != null)
	 * encounterAgainstForm++; if (test.getNotGivenForm() != null)
	 * encounterAgainstForm++; if (test.getGivenEncounter() != null)
	 * encounterAgainstForm--; if (test.getNotGivenEncounter() != null)
	 * encounterAgainstForm--; return encounterAgainstForm; }
	 */

	public List<RadiologyTest> getRadiologyTestsByDateAndPatient(Date date,
			Patient patient) throws ParseException {
		List<RadiologyTest> tests = dao.getRadiologyTestsByDateAndPatient(date,
				patient);
		return tests;
	}

	public void createConceptsForXrayDefaultForm() {
		dao.createConceptsForXrayDefaultForm();
	}

	/**
	 * Get radiology test by encounter
	 * 
	 * @param ecnounter
	 * @return
	 */
	public RadiologyTest getRadiologyTest(Encounter ecnounter) {
		return dao.getRadiologyTest(ecnounter);
	}

	/*
	 * RADIOLOGY TEMPLATE
	 */
	public RadiologyTemplate getRadiologyTemplate(Integer id) {
		return dao.getRadiologyTemplate(id);
	}

	public RadiologyTemplate saveRadiologyTemplate(RadiologyTemplate template) {
		return dao.saveRadiologyTemplate(template);
	}

	public List<RadiologyTemplate> getAllRadiologyTemplates() {
		return dao.getAllRadiologyTemplates();
	}

	public void deleteRadiologyTemplate(RadiologyTemplate template) {
		dao.deleteRadiologyTemplate(template);
	}

	public List<RadiologyTemplate> getRadiologyTemplates(Concept concept) {
		return dao.getRadiologyTemplates(concept);
	}
}
