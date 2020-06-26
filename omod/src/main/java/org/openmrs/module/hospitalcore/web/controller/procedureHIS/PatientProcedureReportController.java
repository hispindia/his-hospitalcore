package org.openmrs.module.hospitalcore.web.controller.procedureHIS;

import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller("PatientProcedureReportController")
@RequestMapping("/module/hospitalcore/patientProcedureReport.form")
public class PatientProcedureReportController {
	@RequestMapping(method = RequestMethod.GET)
	public String list(@RequestParam(value = "M", required = false) String male,
			@RequestParam(value = "date", required = false) String date, Model model, HttpServletRequest request) {

		return "/module/hospitalcore/diagnosisHIS/patientDiagnosisReport";
	}
}
