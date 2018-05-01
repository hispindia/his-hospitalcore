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


package org.openmrs.module.hospitalcore.web.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.openmrs.Patient;
import org.openmrs.api.context.Context;
import org.openmrs.module.hospitalcore.HospitalCoreService;
import org.openmrs.module.hospitalcore.matcher.AgeMatcher;
import org.openmrs.module.hospitalcore.matcher.DateMatcher;
import org.openmrs.module.hospitalcore.matcher.GenderMatcher;
import org.openmrs.module.hospitalcore.matcher.Matcher;
import org.openmrs.module.hospitalcore.matcher.RelativeNameMatcher;
import org.openmrs.module.hospitalcore.util.HospitalCoreConstants;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller("PatientSearchController")
@RequestMapping("/module/hospitalcore/patientSearch.form")
public class PatientSearchController {

	@RequestMapping(method = RequestMethod.POST)
	public String searchPatient(
			@RequestParam(value = "phrase", required = false) String phrase,
			@RequestParam(value = "view", required = false) String view,
			@RequestParam(value = "currentPage", required = false) Integer currentPage,
			@RequestParam(value = "pageSize", required = false) Integer pageSize,
			HttpServletRequest request, Model model) {
		String prefix = Context.getAdministrationService().getGlobalProperty(
				HospitalCoreConstants.PROPERTY_IDENTIFIER_PREFIX);
		model.addAttribute("prefix", prefix);
		if (phrase.contains("-") && !phrase.contains(prefix)) {
			phrase = prefix + phrase;
		}

		String gender = request.getParameter("gender");
		if (gender.equalsIgnoreCase("any"))
			gender = null;
		Integer age = getInt(request.getParameter("age"));
		Integer ageRange = getInt(request.getParameter("ageRange"));
		String relativeName = request.getParameter("relativeName");
		String date = request.getParameter("date");
		Integer dateRange = getInt(request.getParameter("dateRange"));

		HospitalCoreService hcs = (HospitalCoreService) Context
				.getService(HospitalCoreService.class);
		List<Patient> patients = hcs.searchPatient(phrase, gender, age,
				ageRange, date, dateRange, relativeName);

		// List<Patient> patients = Context.getPatientService()
		// .getPatients(phrase);
		// try {
		// patients = filterPatients(request, patients);
		// } catch (NumberFormatException e) {
		// model.addAttribute("error", e.getMessage());
		// } catch (ParseException e) {
		// e.printStackTrace();
		// }

		for (Enumeration e = request.getParameterNames(); e.hasMoreElements();) {
			String parameterName = (String) e.nextElement();
			model.addAttribute(parameterName,
					request.getParameter(parameterName));
		}

		// PAGING
		if (currentPage > 0) {
			model.addAttribute("prevPage", currentPage - 1);
		}
		if ((currentPage + 1) * pageSize <= patients.size()) {
			model.addAttribute("nextPage", currentPage + 1);
		}
		List<Patient> renderedPatients = pagePatient(patients, currentPage,
				pageSize);

		model.addAttribute("patients", renderedPatients);
		model.addAttribute("size", patients.size());
		return "/module/hospitalcore/patientSearch/" + view;
	}

	// Filter patient list using advance search criteria
	private List<Patient> filterPatients(HttpServletRequest request,
			List<Patient> patients) throws NumberFormatException,
			ParseException {

		List<Patient> filteredPatients = patients;

		// filter using gender
		String genderCriterion = request.getParameter("gender");
		if (!StringUtils.isBlank(genderCriterion)) {
			filteredPatients = select(filteredPatients, new GenderMatcher(
					new String(genderCriterion)));
		}

		// filter using age criteria
		String ageCriterion = request.getParameter("age");
		if (!StringUtils.isBlank(ageCriterion)) {
			String ageRange = request.getParameter("ageRange");
			try {
				filteredPatients = select(filteredPatients, new AgeMatcher(
						new Integer(ageCriterion), new Integer(ageRange)));
			} catch (Exception e) {
				e.printStackTrace();
				throw new NumberFormatException("advancesearch.error.age");
			}
		}

		// filter using relative name
		String relativeNameCriterion = request.getParameter("relativeName");
		if (!StringUtils.isBlank(relativeNameCriterion)) {
			filteredPatients = select(filteredPatients,
					new RelativeNameMatcher(relativeNameCriterion));
		}

		// filter using date of visit
		String dateCriterion = request.getParameter("date");
		if (!StringUtils.isBlank(dateCriterion)) {
			try {
				String dateRange = request.getParameter("dateRange");
				filteredPatients = select(filteredPatients, new DateMatcher(
						dateCriterion, new Integer(dateRange)));
			} catch (Exception e) {
				e.printStackTrace();
				throw new NumberFormatException("advancesearch.error.date");
			}
		}

		return filteredPatients;
	}

	// paging
	private List<Patient> pagePatient(List<Patient> patients, int currentPage,
			int pageSize) {
		int firstIndex = pageSize * currentPage;
		List<Patient> page = new ArrayList<Patient>();
		for (int i = firstIndex; i < (currentPage + 1) * pageSize; i++) {
			if (i < patients.size()) {
				page.add(patients.get(i));
			}
		}
		return page;
	}

	@RequestMapping(method = RequestMethod.GET)
	public String showSearchBox(@RequestParam("view") String view,
			@RequestParam("resultBoxId") String resultBoxId, Model model) {
		model.addAttribute("view", view);
		model.addAttribute("resultBoxId", resultBoxId);
		return "/module/hospitalcore/patientSearch/searchBox";
	}

	private List<Patient> select(List<Patient> patients, Matcher matcher) {
		List<Patient> result = new ArrayList<Patient>();
		for (Patient patient : patients) {
			if (matcher.matches(patient)) {
				result.add(patient);
			}
		}
		return result;
	}

	private Integer getInt(String value) {
		try {
			Integer number = Integer.parseInt(value);
			return number;
		} catch (Exception e) {
			return 0;
		}
	}
}
