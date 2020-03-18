package org.openmrs.module.hospitalcore.web.controller.clinicalMorbidity;

import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller("ClinicalMorbidityHISFormatController")
@RequestMapping("/module/hospitalcore/clinicalMorbidityHIS.form")
public class ClinicalMorbidityHISController {
	@RequestMapping(method=RequestMethod.GET)
	public String list(Model model, HttpServletRequest request) {

    	return "/module/hospitalcore/clinicalMorbidityHIS/clinicalMorbidityHIS";
	}
}
