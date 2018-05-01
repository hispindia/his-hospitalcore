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

package org.openmrs.module.hospitalcore.web.controller.ajax;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.openmrs.Concept;
import org.openmrs.ConceptWord;
import org.openmrs.api.context.Context;
import org.openmrs.module.hospitalcore.HospitalCoreService;
import org.openmrs.module.hospitalcore.model.CoreForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller("HospitalcoreAjaxController")
public class AjaxController {

	/**
	 * Concept search autocomplete for form
	 * 
	 * @param name
	 * @param model
	 * @return
	 */
	@SuppressWarnings("deprecation")
	@RequestMapping(value = "/module/hospitalcore/ajax/autocompleteConceptSearch.htm", method = RequestMethod.GET)
	public String autocompleteConceptSearch(
			@RequestParam(value = "q", required = false) String name,
			Model model) {
		List<ConceptWord> cws = Context.getConceptService().findConcepts(name,
				new Locale("en"), false);
		Set<String> conceptNames = new HashSet<String>();
		for (ConceptWord word : cws) {
			String conceptName = word.getConcept().getName().getName();
			conceptNames.add(conceptName);
		}
		List<String> concepts = new ArrayList<String>();
		concepts.addAll(conceptNames);
		Collections.sort(concepts, new Comparator<String>() {

			public int compare(String o1, String o2) {
				return o1.compareToIgnoreCase(o2);
			}
		});
		model.addAttribute("conceptNames", concepts);
		return "/module/hospitalcore/ajax/autocompleteConceptSearch";
	}

	@RequestMapping(value = "/module/hospitalcore/ajax/checkExistingForm.htm", method = RequestMethod.GET)
	public String checkExistingForm(
			@RequestParam("conceptName") String conceptName,			
			@RequestParam(value = "formId", required = false) Integer formId,
			Model model) {
		Concept concept = Context.getConceptService().getConcept(conceptName);
		boolean duplicatedFormFound = false;
		if (concept != null) {
			HospitalCoreService hcs = (HospitalCoreService) Context
					.getService(HospitalCoreService.class);
			List<CoreForm> forms = hcs.getCoreForms(conceptName);
			if (!CollectionUtils.isEmpty(forms)) {
				if (formId != null) {
					CoreForm form = hcs.getCoreForm(formId);
					if ((forms.size() == 1) && (forms.contains(form))) {

					} else {
						duplicatedFormFound = true;
					}
					if (forms.contains(form)) {
						forms.remove(form);
					}
				} else {					
					duplicatedFormFound = true;
				}

			}
			model.addAttribute("duplicatedFormFound", duplicatedFormFound);
			model.addAttribute("duplicatedForms", forms);
		}
		return "/module/hospitalcore/ajax/checkExistingForm";
	}
}
