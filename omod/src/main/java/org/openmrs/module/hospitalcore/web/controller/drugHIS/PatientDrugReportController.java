package org.openmrs.module.hospitalcore.web.controller.drugHIS;

import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@Controller("PatientDrugReportController")
@RequestMapping("/module/hospitalcore/patientDrugReport.form")
public class PatientDrugReportController {
	@RequestMapping(method = RequestMethod.GET)
	public String list(
			@RequestParam(value = "date", required = false) String date,
			Model model, HttpServletRequest request) {
		
		
		
		return "/module/hospitalcore/drugHIS/patientDrugReport";
	}
}

