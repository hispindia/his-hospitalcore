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

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.openmrs.Concept;
import org.openmrs.Encounter;
import org.openmrs.Order;
import org.openmrs.Patient;
import org.openmrs.api.OpenmrsService;
import org.openmrs.module.hospitalcore.form.RadiologyForm;
import org.openmrs.module.hospitalcore.model.RadiologyDepartment;
import org.openmrs.module.hospitalcore.model.RadiologyTest;
import org.openmrs.module.hospitalcore.template.RadiologyTemplate;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface RadiologyService extends OpenmrsService {

	/**
	 * Get radiology test by id
	 * 
	 * @param form
	 * @return
	 */
	public RadiologyForm saveRadiologyForm(RadiologyForm form);

	/**
	 * Get radiology test by id
	 * 
	 * @param id
	 * @return
	 */
	public RadiologyForm getRadiologyFormById(Integer id);

	/**
	 * Get all radiology forms
	 * 
	 * @return
	 */
	public List<RadiologyForm> getAllRadiologyForms();

	/**
	 * Delete radiology form
	 * 
	 * @param form
	 */
	public void deleteRadiologyForm(RadiologyForm form);

	/**
	 * Get radiology forms
	 * 
	 * @param conceptName
	 * @return
	 */
	public List<RadiologyForm> getRadiologyForms(String conceptName);

	/**
	 * Save radiology department
	 * 
	 * @param department
	 * @return
	 */
	public RadiologyDepartment saveRadiologyDepartment(
			RadiologyDepartment department);

	/**
	 * Get radiology department by id
	 * 
	 * @param id
	 * @return
	 */
	public RadiologyDepartment getRadiologyDepartmentById(Integer id);

	/**
	 * Delete a radiology department
	 * 
	 * @param department
	 */
	public void deleteRadiologyDepartment(RadiologyDepartment department);

	/**
	 * get the default radiology department using current user's role
	 * 
	 * @return
	 */
	public RadiologyDepartment getCurrentRadiologyDepartment();

	/**
	 * Find orders
	 * 
	 * @param startDate
	 * @param phrase
	 * @param tests
	 * @param page
	 * @return
	 * @throws ParseException
	 */
	public List<Order> getOrders(Date startDate, String phrase,
			Set<Concept> tests, int page) throws ParseException;

	/**
	 * Count found orders
	 * 
	 * @param startDate
	 * @param phrase
	 * @param tests
	 * @return
	 * @throws ParseException
	 */
	public Integer countOrders(Date startDate, String phrase, Set<Concept> tests)
			throws ParseException;

	/**
	 * Accept a radiology test
	 * 
	 * @param order
	 * @return id of accepted radiology test
	 */
	public Integer acceptTest(Order order);

	/**
	 * Save radiology test
	 * 
	 * @param test
	 * @return
	 */
	public RadiologyTest saveRadiologyTest(RadiologyTest test);

	/**
	 * Get radiology test by id
	 * 
	 * @param id
	 * @return
	 */
	public RadiologyTest getRadiologyTestById(Integer id);

	/**
	 * Get all radiology test
	 * 
	 * @return
	 */
	public List<RadiologyTest> getAllRadiologyTests();

	/**
	 * Delete radiology test
	 * 
	 * @param test
	 */
	public void deleteRadiologyTest(RadiologyTest test);

	/**
	 * Unaccept a test
	 * 
	 * @param testId
	 * @return
	 */
	public String unacceptTest(RadiologyTest test);

	/**
	 * Get radiology test by order
	 * 
	 * @param order
	 * @return
	 */
	public RadiologyTest getRadiologyTestByOrder(Order order);

	/**
	 * Reschedule a test
	 * 
	 * @param order
	 * @param rescheduledDate
	 * @return reschedule status
	 */
	public String rescheduleTest(Order order, Date rescheduledDate);

	/**
	 * Find radiology tests for worklist
	 * 
	 * @param date
	 * @param phrase
	 * @param page
	 *            TODO
	 * @param investigation
	 * @return
	 * @throws ParseException
	 */
	public List<RadiologyTest> getAcceptedRadiologyTests(Date date,
			String phrase, Set<Concept> allowableTests, int page)
			throws ParseException;

	/**
	 * Count radiology tests rows
	 * 
	 * @param date
	 * @param phrase
	 * @param allowableTests
	 * @param page
	 * @return
	 * @throws ParseException
	 */
	public Integer countAcceptedRadiologyTests(Date date, String phrase,
			Set<Concept> allowableTests) throws ParseException;

	/**
	 * Complete a test then return whether the test is successfully completed.
	 * 
	 * @param test
	 * @return
	 */
	public String completeTest(RadiologyTest test);

	/**
	 * Get all radiology tests by date
	 * 
	 * @param date
	 * @param phrase
	 * @param investigation
	 * @return
	 * @throws ParseException
	 */
	public List<RadiologyTest> getAllRadiologyTestsByDate(Date date,
			String phrase, Concept investigation) throws ParseException;

	/**
	 * Get all completed radiology tests
	 * 
	 * @param date
	 * @param phrase
	 * @param allowableTests
	 * @param page
	 *            TODO
	 * @return
	 * @throws ParseException
	 */
	public List<RadiologyTest> getCompletedRadiologyTests(Date date,
			String phrase, Set<Concept> allowableTests, int page)
			throws ParseException;

	/**
	 * Count all completed radiology tests
	 * 
	 * @param date
	 * @param phrase
	 * @param allowableTests
	 * @return
	 * @throws ParseException
	 */
	public Integer countCompletedRadiologyTests(Date date, String phrase,
			Set<Concept> allowableTests) throws ParseException;

	/**
	 * Get all radiology tests by date and patient
	 * 
	 * @param date
	 * @param patient
	 * @return
	 * @throws ParseException
	 */
	public List<RadiologyTest> getRadiologyTestsByDateAndPatient(Date date,
			Patient patient) throws ParseException;
	
	/**
	 * Get radiology test by encounter
	 * @param ecnounter
	 * @return
	 */
	public RadiologyTest getRadiologyTest(Encounter encounter);

	/**
	 * Create concepts for Xray default form
	 */
	public void createConceptsForXrayDefaultForm();

	/**
	 * get default form
	 * 
	 * @return
	 */
	public RadiologyForm getDefaultForm();

	/**
	 * Get Radiology Template
	 * 
	 * @param id
	 * @return
	 */
	public RadiologyTemplate getRadiologyTemplate(Integer id);

	/**
	 * Save Radiology Template
	 * 
	 * @param template
	 * @return
	 */
	public RadiologyTemplate saveRadiologyTemplate(RadiologyTemplate template);

	/**
	 * Get all Radiology Templates
	 * @return
	 */
	public List<RadiologyTemplate> getAllRadiologyTemplates();
	
	/**
	 * Delete Radiology template
	 * @param template
	 */
	public void deleteRadiologyTemplate(RadiologyTemplate template);
	
	/**
	 * Get Radiology Template by concept
	 * @param concept
	 * @return
	 */
	public List<RadiologyTemplate> getRadiologyTemplates(Concept concept);
}
