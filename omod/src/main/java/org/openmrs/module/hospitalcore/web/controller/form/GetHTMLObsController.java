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

package org.openmrs.module.hospitalcore.web.controller.form;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.openmrs.Concept;
import org.openmrs.ConceptAnswer;
import org.openmrs.ConceptSet;
import org.openmrs.api.context.Context;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller("HospitalcoreGetHTMLObsController")
@RequestMapping("/module/hospitalcore/getHTMLObs.form")
public class GetHTMLObsController {

	@RequestMapping(method = RequestMethod.GET)
	public String getHTMLObs(
			@RequestParam("name") String name,
			@RequestParam("type") String type,
			@RequestParam("title") String title,
			@RequestParam("data") String data,
			Model model) {
		Concept concept = Context.getConceptService().getConcept(name);
		if (concept != null) {

			model.addAttribute("obsName", name);
			data = data.replace("\"", "'");
			model.addAttribute("data", data);
			title = title.replace("\"", "'");
			model.addAttribute("title", title);
			if (concept.getDatatype().getName().equalsIgnoreCase("text")) {
				if (type.equalsIgnoreCase("textbox")) {
					model.addAttribute("type", "text");
				} else if (type.equalsIgnoreCase("textarea")) {
					model.addAttribute("type", "textarea");
				}
			} else if (concept.getDatatype().getName()
					.equalsIgnoreCase("numeric")) {
				model.addAttribute("type", "number");
			} else if (concept.getDatatype().getName()
					.equalsIgnoreCase("datetime")) {
				model.addAttribute("type", "datetime");
			} else if (concept.getDatatype().getName()
					.equalsIgnoreCase("coded")) {
				Set<String> options = new HashSet<String>();
				for (ConceptAnswer ca : concept.getAnswers()) {
					Concept c = ca.getAnswerConcept();
					options.add(c.getName().getName());
				}

				for (ConceptSet cs : concept.getConceptSets()) {
					Concept c = cs.getConcept();
					options.add(c.getName().getName());
				}

				model.addAttribute("options", getSortedOptionNames(options));
				if (!type.equalsIgnoreCase("textbox")) {
					model.addAttribute("type", type);
				} else {
					model.addAttribute("type", "selection");
				}
			}
		}

		return "/module/hospitalcore/form/getHTMLObs";
	}

	private List<String> getSortedOptionNames(Set<String> options) {
		List<String> names = new ArrayList<String>();
		names.addAll(options);
		Collections.sort(names);
		return names;
	}
}
