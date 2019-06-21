package org.openmrs.module.hospitalcore.web.controller.diagnosisHIS;

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.openmrs.api.context.Context;
import org.openmrs.module.hospitalcore.HospitalCoreService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@Controller("DiagnosisHISFormatController")
@RequestMapping("/module/hospitalcore/diagnosisHIS.form")
public class DiagnosisHISFormatController {
	@RequestMapping(method=RequestMethod.GET)
	public String list( @RequestParam(value="searchName",required=false)  String searchName, 
							 @RequestParam(value="pageSize",required=false)  Integer pageSize, 
	                         @RequestParam(value="currentPage",required=false)  Integer currentPage,
	                        Model model, HttpServletRequest request){
		
		HospitalCoreService hcs=Context.getService(HospitalCoreService.class);
		  String[] months = new DateFormatSymbols().getMonths();
	        for (String month : months) {
	            System.out.println("month = " + month);
	        }
	        model.addAttribute("month", months);
			
    	
    	return "/module/hospitalcore/diagnosisHIS/diagnosisHIS";
	}

}
