package org.openmrs.module.hospitalcore.web.controller.downloadasCSV;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;



import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.openmrs.Encounter;
import org.openmrs.Obs;
import org.openmrs.Patient;
import org.openmrs.PersonAttribute;
import org.openmrs.PersonAttributeType;
import org.openmrs.api.context.Context;
import org.openmrs.module.hospitalcore.HospitalCoreService;
import org.openmrs.module.hospitalcore.util.GlobalPropertyUtil;
import org.openmrs.module.hospitalcore.util.HospitalCoreConstants;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller("PatientDetailasCSVController")
@RequestMapping("/module/hospitalcore/patientDetailasCSV.form")
public class PatientDetailasCSVController {
	
	@RequestMapping(method=RequestMethod.GET)
	public String list( @RequestParam(value="searchName",required=false)  String searchName, 
							 @RequestParam(value="pageSize",required=false)  Integer pageSize, 
	                         @RequestParam(value="currentPage",required=false)  Integer currentPage,
	                         Map<String, Object> model, HttpServletRequest request){
		
    	
		return "/module/hospitalcore/downloadasCSV/patientDetailasCSV";
	}
	@RequestMapping(value = "/downloadCSV")
	public void downloadCSV(HttpServletResponse response,@RequestParam(value="date",required=false)  String date,HttpServletRequest request) throws IOException {
		
		String dates=date;
		
		response.setContentType("text/csv");
		SimpleDateFormat formatterExt = new SimpleDateFormat("dd/MM/yyyy");
		String reportName = "Report_On_"+dates+".csv";
		response.setHeader("Content-disposition", "attachment;filename="+reportName);
		
		ArrayList<String> rows = new ArrayList<String>();
		rows.add("NINNumber,Identifier,Name,Gender,Age,PhoneNo,LastVisit,AdharNumber,DeparmentName,TypeOfPatient");
		rows.add("\n");
		HospitalCoreService hcs=Context.getService(HospitalCoreService.class);
		 Set<Patient>enc=hcs.getAllEncounterCurrentDate(dates);
        
			
			for(Patient pat:enc)
			{	String typeofpatient="";
				List<Encounter>encounterpatient=Context.getEncounterService().getEncountersByPatient(pat);
			     
			     if(encounterpatient.size()==1)
			     {
			    	 typeofpatient="New Patient" ;
			     }
			     else
			     {
			    	 typeofpatient="Revisit" ;
			     }
				String phone="";
				List<PersonAttribute> pas = hcs.getPersonAttributes(pat.getId());
			    for (PersonAttribute pa : pas) {
				PersonAttributeType attributeType = pa.getAttributeType();
				if (attributeType.getPersonAttributeTypeId() == 16)
				{
					 phone=pa.getValue();    
				}		   
		        }
			    String ninnumber = GlobalPropertyUtil.getString(
						HospitalCoreConstants.PROPERTY_HOSPITAL_NIN_NUMBER, null);
				String identifier=pat.getPatientIdentifier().getIdentifier();
				String name=pat.getGivenName().concat(" ").concat(pat.getMiddleName()).concat(" ").concat(pat.getFamilyName()) ;
				
				String gender=pat.getGender();
			    String age=pat.getAge().toString();
				String lastvisit=formatterExt.format(hcs.getLastVisitTime(pat.getId()));
				String adharnumber="";
				for (PersonAttribute pa : pas) {
					PersonAttributeType attributeType = pa.getAttributeType();
					if (attributeType.getPersonAttributeTypeId() == 20)
					{
						adharnumber=pa.getValue();   
						
					}		   
			        }
				
				String departmentName="";
				List<Obs> o=Context.getObsService().getObservationsByPersonAndConcept(pat,Context.getConceptService().getConceptByName("OPD WARD"));
				for(Obs ob:o)
				{
					departmentName=ob.getValueCoded().getName().toString();
				}
				rows.add(ninnumber.concat(",").concat(identifier).concat(",").concat(name).concat(",").concat(gender).concat(",").
						concat(age).concat(",").concat(phone).concat(",").concat(lastvisit).concat(",").
						concat(adharnumber).concat(",").concat(departmentName).concat(",").concat(typeofpatient));
				
				rows.add("\n");
			}
		Iterator<String> iter = rows.iterator();
		while (iter.hasNext()) {
			String outputString = (String) iter.next();
			response.getOutputStream().print(outputString);
		}
 
		response.getOutputStream().flush();
 
	}
}
