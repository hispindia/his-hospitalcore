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

import org.openmrs.Concept;
import org.openmrs.api.context.Context;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller("HospitalcoreGetConceptInfoController")
@RequestMapping("/module/hospitalcore/getConceptInfo.form")
public class GetConceptInfoController {

	@RequestMapping(method = RequestMethod.GET)
	public String getInfo(@RequestParam("name") String name, Model model) {
		Concept concept = Context.getConceptService().getConcept(name);
		
		model.addAttribute("conceptDescription", concept.getDescription().getDescription());
		model.addAttribute("conceptDatatype", concept.getDatatype().getName());		

		return "/module/hospitalcore/form/getConceptInfo";
	}
}
