package org.openmrs.module.hospitalcore.web.controller.downloadasCSV;





import javax.servlet.http.HttpServletRequest;



import org.openmrs.api.context.Context;
import org.openmrs.module.hospitalcore.HospitalCoreService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;

@Controller("DownloadAsCSvController")
@RequestMapping("/module/hospitalcore/downloadCsv.form")
public class DownloadAsCSvController {

		@RequestMapping(method=RequestMethod.GET)
		public String list( @RequestParam(value="searchName",required=false)  String searchName, 
								 @RequestParam(value="pageSize",required=false)  Integer pageSize, 
		                         @RequestParam(value="currentPage",required=false)  Integer currentPage,
		                        Model model, HttpServletRequest request){
			
			HospitalCoreService hcs=Context.getService(HospitalCoreService.class);
			 
		
			
	    	
	    	
	    	return "/module/hospitalcore/downloadasCSV/downloadCsv";
		}


}

