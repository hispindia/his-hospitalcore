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
 **/

package org.openmrs.module.hospitalcore.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import org.openmrs.Concept;
import org.openmrs.ConceptWord;
import org.openmrs.Order;
import org.openmrs.api.context.Context;
import org.openmrs.module.hospitalcore.model.RadiologyTest;

public class RadiologyDashboardUtil {

	private static SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
	private static Map<Concept, String> conceptNames = new HashMap<Concept, String>();
	private static Concept xrayConcept = null;
	private static Set<Concept> xrayConcepts = null;

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
	 * @param test
	 * @return
	 */
	public static TestModel generateModel(RadiologyTest test,
			Map<Concept, Set<Concept>> testTreeMap) {
		return generateModel(test.getOrder(), test, testTreeMap);
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
			tm.setAcceptedDate(test.getDate().toString());
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
