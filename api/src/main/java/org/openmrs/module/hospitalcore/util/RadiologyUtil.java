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

package org.openmrs.module.hospitalcore.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.openmrs.Concept;
import org.openmrs.ConceptWord;
import org.openmrs.Encounter;
import org.openmrs.Obs;
import org.openmrs.Order;
import org.openmrs.api.context.Context;
import org.openmrs.module.hospitalcore.RadiologyService;
import org.openmrs.module.hospitalcore.concept.TestTree;
import org.openmrs.module.hospitalcore.form.RadiologyForm;
import org.openmrs.module.hospitalcore.model.RadiologyDepartment;
import org.openmrs.module.hospitalcore.model.RadiologyTest;
import org.springframework.ui.Model;

public class RadiologyUtil {

	private static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	private static Map<Concept, String> conceptNames = new HashMap<Concept, String>();
	private static Concept xrayConcept = null;
	private static Set<Concept> xrayConcepts = null;

	/**
	 * Generate list of test model using orders
	 * 
	 * @param orders
	 * @return
	 */
	public static List<TestModel> generateModelsFromOrders(List<Order> orders,
			Map<Concept, Set<Concept>> testTreeMap) {

		List<TestModel> models = new ArrayList<TestModel>();
		for (Order order : orders) {
			TestModel tm = generateModel(order, testTreeMap);
			models.add(tm);
		}
		return models;
	}

	/**
	 * Generate list of test models using tests
	 * 
	 * @param tests
	 * @return
	 */
	public static List<TestModel> generateModelsFromTests(
			List<RadiologyTest> tests, Map<Concept, Set<Concept>> testTreeMap) {

		List<TestModel> models = new ArrayList<TestModel>();
		for (RadiologyTest test : tests) {
			TestModel tm = generateModel(test, testTreeMap);
			models.add(tm);
		}
		return models;
	}

	/**
	 * Generate a single test model
	 * 
	 * @param order
	 * @return
	 */
	public static TestModel generateModel(Order order,
			Map<Concept, Set<Concept>> testTreeMap) {
		RadiologyService rs = (RadiologyService) Context
				.getService(RadiologyService.class);
		RadiologyTest test = rs.getRadiologyTestByOrder(order);
		return generateModel(order, test, testTreeMap);
	}

	/**
	 * Generate a single test model
	 * 
	 * @param test
	 * @return
	 */
	public static TestModel generateModel(RadiologyTest test,
			Map<Concept, Set<Concept>> testTreeMap) {
		return generateModel(test.getOrder(), test, testTreeMap);
	}

	/**
	 * Format a date
	 * 
	 * @param date
	 * @return
	 */
	public static String formatDate(Date date) {
		return sdf.format(date);
	}

	/**
	 * Parse a string into a date
	 * 
	 * @param dateStr
	 * @return
	 * @throws ParseException
	 */
	public static Date parseDate(String dateStr) throws ParseException {
		return sdf.parse(dateStr);
	}

	private static Set<Concept> getAllInvestigations() {
		RadiologyService rs = (RadiologyService) Context
				.getService(RadiologyService.class);
		RadiologyDepartment department = rs.getCurrentRadiologyDepartment();
		if (department != null) {
			Set<Concept> investigations = department.getInvestigations();
			return investigations;
		}
		return null;
	}

	/**
	 * Get all test trees for all investigation belongs to a department
	 * 
	 * @return
	 */
	public static List<TestTree> getAllTestTrees() {
		List<TestTree> trees = new ArrayList<TestTree>();
		Set<Concept> concepts = getAllInvestigations();
		for (Concept concept : concepts) {
			TestTree tree = new TestTree(concept);
			trees.add(tree);
		}
		return trees;
	}

	/*
	 * REFACTORING
	 */
	private static TestModel generateModel(Order order, RadiologyTest test,
			Map<Concept, Set<Concept>> testTreeMap) {

		TestModel tm = new TestModel();
		tm.setStartDate(sdf.format(order.getStartDate()));
		tm.setPatientIdentifier(order.getPatient().getPatientIdentifier()
				.getIdentifier());
		tm.setPatientName(PatientUtils.getFullName(order.getPatient()));
		tm.setGender(order.getPatient().getGender());
		tm.setAge(PatientUtils.estimateAge(order.getPatient()));
		tm.setTestName(order.getConcept().getName().getName());
		tm.setOrderId(order.getOrderId());

		// if the test is an x-ray test, then turn on this flag
		if (getXrayConcepts(testTreeMap).contains(order.getConcept())) {
			tm.setXray(true);
		} else {
			tm.setXray(false);
		}

		if (test != null) {
			tm.setStatus(test.getStatus());
			tm.setTestId(test.getId());
			if (test.getForm() != null) {
				tm.setGivenFormId(test.getForm().getId());
			}
			if (test.getEncounter() != null) {
				tm.setGivenEncounterId(test.getEncounter()
						.getEncounterId());
			}			
			tm.setAcceptedDate(sdf.format(test.getDate()));
		} else {
			tm.setStatus(null);
		}

		// get investigation from test tree map
		if (testTreeMap != null) {
			for (Concept investigationConcept : testTreeMap.keySet()) {
				Set<Concept> set = testTreeMap.get(investigationConcept);
				if (set.contains(order.getConcept())) {
					tm.setInvestigation(getConceptName(investigationConcept));
				}

			}
		}

		return tm;
	}

	/**
	 * Search for concept using name
	 * 
	 * @param name
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static Concept searchConcept(String name) {
		Concept concept = Context.getConceptService().getConcept(name);
		if (concept != null) {
			return concept;
		} else {
			List<ConceptWord> cws = Context.getConceptService().findConcepts(
					name, new Locale("en"), false);
			if (!cws.isEmpty())
				return cws.get(0).getConcept();
		}
		return null;
	}

	private static Concept getXrayConcept(Map<Concept, Set<Concept>> testTreeMap) {
		if (xrayConcept != null) {
			return xrayConcept;
		} else {
			for (Concept concept : testTreeMap.keySet()) {
				Concept investigation = Context.getConceptService().getConcept(
						concept.getConceptId());
				if (investigation.getName().getName().toLowerCase()
						.contains("x-ray")) {
					xrayConcept = investigation;
					return xrayConcept;
				}
			}
		}
		return null;
	}

	private static Set<Concept> getXrayConcepts(
			Map<Concept, Set<Concept>> testTreeMap) {
		if (xrayConcepts != null) {
			return xrayConcepts;
		} else {
			Concept xrayConcept = getXrayConcept(testTreeMap);
			xrayConcepts = testTreeMap.get(xrayConcept);
			return xrayConcepts;
		}
	}

	/**
	 * generate data for form from an existing encounter
	 * 
	 * @param model
	 * @param encounter
	 */
	public static void generateDataFromEncounter(Model model,
			Encounter encounter, RadiologyForm form) {
		if (encounter != null) {
			List<String> inputNames = new ArrayList<String>();
			List<String> inputValues = new ArrayList<String>();
			for (Obs obs : encounter.getAllObs()) {
				inputNames.add(obs.getConcept().getName().getName());
				inputValues.add(getObsValue(obs));
			}
			model.addAttribute("inputNames", inputNames);
			model.addAttribute("inputValues", inputValues);
			model.addAttribute("inputLength", inputValues.size());
		}
	}

//	private static List<Obs> getOrderedObs(Encounter encounter,
//			final RadiologyForm form) {
//		List<Obs> obs = new ArrayList<Obs>();
//		obs.addAll(encounter.getAllObs());
//		Collections.sort(obs, new Comparator<Obs>() {
//
//			public int compare(Obs o1, Obs o2) {
//				return getPositionInForm(o1, form)
//						- getPositionInForm(o2, form);
//			}
//		});
//		return obs;
//	}
//
//	private static int getPositionInForm(Obs obs, RadiologyForm form) {
//		String name = obs.getConcept().getName().getName();
//		String text = "name='" + name;		
//		if (name.equalsIgnoreCase(RadiologyConstants.DEFAULT_XRAY_FORM_NOTE))
//			return Integer.MAX_VALUE;
//		return form.getContent().indexOf(text);
//	}

	private static String getObsValue(Obs obs) {
		Concept concept = obs.getConcept();
		if (concept.getDatatype().getName().equalsIgnoreCase("Text")) {
			return obs.getValueText();
		} else if (concept.getDatatype().getName().equalsIgnoreCase("Numeric")) {
			return obs.getValueNumeric().toString();
		} else if (concept.getDatatype().getName().equalsIgnoreCase("Coded")) {
			return obs.getValueCoded().getName().getName();
		}
		return null;
	}

	/**
	 * Get name of a detached by hibernate session concept
	 * 
	 * @param searchConcept
	 * @return
	 */
	public static String getConceptName(Concept searchConcept) {
		if (conceptNames.containsKey(searchConcept)) {
			return conceptNames.get(searchConcept);
		} else {
			Concept concept = Context.getConceptService().getConcept(
					searchConcept.getConceptId());
			conceptNames.put(searchConcept, concept.getName().getName());
			return conceptNames.get(searchConcept);
		}
	}
}
