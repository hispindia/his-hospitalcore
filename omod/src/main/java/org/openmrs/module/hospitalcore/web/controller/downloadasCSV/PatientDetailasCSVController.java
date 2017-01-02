package org.openmrs.module.hospitalcore.web.controller.downloadasCSV;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
import org.openmrs.module.hospitalcore.IpdService;
import org.openmrs.module.hospitalcore.model.IpdPatientAdmissionLog;
import org.openmrs.module.hospitalcore.model.IpdPatientAdmittedLog;
import org.openmrs.module.hospitalcore.util.GlobalPropertyUtil;
import org.openmrs.module.hospitalcore.util.HospitalCoreConstants;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller("PatientDetailasCSVController")
@RequestMapping("/module/hospitalcore/patientDetailasCSV.form")
public class PatientDetailasCSVController {

	@RequestMapping(method = RequestMethod.GET)
	public String list(
			@RequestParam(value = "searchName", required = false) String searchName,
			@RequestParam(value = "pageSize", required = false) Integer pageSize,
			@RequestParam(value = "currentPage", required = false) Integer currentPage,
			Map<String, Object> model, HttpServletRequest request) {

		return "/module/hospitalcore/downloadasCSV/patientDetailasCSV";
	}

	@RequestMapping(value = "/downloadCSV")
	public void downloadCSV(HttpServletResponse response,
			@RequestParam(value = "date", required = false) String date,
			HttpServletRequest request) throws IOException {

		String dates = date;

		response.setContentType("text/csv");
		SimpleDateFormat formatterExt = new SimpleDateFormat("dd-MM-yyyy");
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String reportName = "Report_On_" + dates + ".csv";
		response.setHeader("Content-disposition", "attachment;filename="
				+ reportName);

		ArrayList<String> rows = new ArrayList<String>();
		rows.add("ID,ninID,patientID,visitID,patientName,mobile,landline,aadhaarNumber,visitDate,visitTime,departmentID,patientTypeID,gender,age");
		rows.add("\n");
		HospitalCoreService hcs = Context.getService(HospitalCoreService.class);
		Set<Patient> enc = hcs.getAllEncounterCurrentDate(dates);

		int patientCount = 1;
		for (Patient pat : enc) {
			String typeofpatient = "";
			String visitId = "";
			String departmentId = "";
			String visitDate = "";

			String hours = "";
			String minute = "";
			String visitTime = "";
			String calculateAge = "";
			Calendar cal = Calendar.getInstance();
			cal.setTime(pat.getBirthdate());
			int yearNew = cal.get(Calendar.YEAR);
			
			Set<Encounter> encounterpatient =hcs.getEncountersByPatientAndDate(pat,dates);
			
			IpdService inService =Context.getService(IpdService.class);	
			/*
			 * Get Visit ID : Equals to encounter ID - For inpatient on
			 * descharge ; For outpatient on registration
			 */
			
			for (Encounter e : encounterpatient) {
				boolean flag=true;
				if (e.getEncounterType().getName().equals("REGINITIAL")
						|| e.getEncounterType().getName().equals("REGREVISIT")) {
					flag=false;
					visitId = e.getEncounterId().toString();
					typeofpatient = "2";
					
					Set<Obs> oList = Context.getObsService().getObservations(e);
					for (Obs o : oList) {
						if (o.getConcept().getName().toString().equals("OPD WARD")) {
							
							visitDate=formatterExt.format(o.getObsDatetime());
							hours = hours+o.getObsDatetime().getHours();
							minute = minute + o.getObsDatetime().getMinutes();
							visitTime = hours.concat(minute);
							departmentId = o.getValueCoded().toString();
							
							Calendar cal2 = Calendar.getInstance();
							cal2.setTime(o.getObsDatetime());
							int yearOld = cal2.get(Calendar.YEAR);
							int yearDiff = yearOld - yearNew;
							if(yearDiff<1) { yearDiff=1;}
							calculateAge = yearDiff +"";
							
							break;
						}
					}
					if(visitTime!=""){
						break;
					}
				}
				if (e.getEncounterType().getName().equals("IPDENCOUNTER") && flag) {
					Set<Obs> oList = Context.getObsService().getObservations(e);
					for (Obs o : oList) {
						if (o.getConcept().getName().toString().equals("ADMISSION OUTCOME")) {
							visitId = e.getEncounterId().toString();
							typeofpatient = "1";
							
							visitDate=formatterExt.format(o.getObsDatetime());
							hours = hours+o.getObsDatetime().getHours();
							minute = minute + o.getObsDatetime().getMinutes();
							visitTime = hours.concat(minute);
							
							IpdPatientAdmissionLog ipl = inService.getIpdPatientAdmissionLogByEncounter(o.getEncounter());
							if(ipl.getStatus().toString().equals("discharge")){
								IpdPatientAdmittedLog ipld = inService.getIpdPatientAdmittedLogByAdmissionLog(ipl);
								departmentId = ipld.getAdmittedWard().toString();
								Calendar cal2 = Calendar.getInstance();
								cal2.setTime(o.getObsDatetime());
								int yearOld = cal2.get(Calendar.YEAR);
								int yearDiff = yearOld - yearNew;
								if(yearDiff<1) { yearDiff=1;}
								calculateAge = yearDiff +"";

								break;
							}
						}
					}
				}
			}
			
			String phone = "";
			List<PersonAttribute> pas = hcs.getPersonAttributes(pat.getId());
			for (PersonAttribute pa : pas) {
				PersonAttributeType attributeType = pa.getAttributeType();
				if (attributeType.getPersonAttributeTypeId() == 16) {
					phone = pa.getValue();
				}
			}
			String ninnumber = GlobalPropertyUtil.getString(
					HospitalCoreConstants.PROPERTY_HOSPITAL_NIN_NUMBER, null);
			String identifier = pat.getPatientIdentifier().getIdentifier();
			String name = pat.getGivenName().concat(" ")
					.concat(pat.getMiddleName()).concat(" ")
					.concat(pat.getFamilyName());

			String gender = "";
			if( pat.getGender().equals("M")){
				gender="1";
			}
			else if( pat.getGender().equals("F")) {
				gender="2";
			}
			else if(pat.getGender().equals("O")) {
				 gender="3";
			}
			
	//		String age = pat.getAge().toString();

			String adharnumber = "";
			for (PersonAttribute pa : pas) {
				PersonAttributeType attributeType = pa.getAttributeType();
				if (attributeType.getPersonAttributeTypeId() == 20) {
					adharnumber = pa.getValue();

				}
			}

			String count = patientCount + "";
			// "ID,ninID,patientID,visitID,patientName,mobile,landline,aadhaarNumber,visitDate,visitTime,departmentID,patientTypeID,gender,age"

			rows.add(count.concat(",").concat(ninnumber).concat(",")
					.concat(identifier).concat(",").concat(visitId).concat(",")
					.concat(name).concat(",").concat(phone).concat(",")
					.concat(phone).concat(",").concat(adharnumber).concat(",")
					.concat(visitDate).concat(",").concat(visitTime)
					.concat(",").concat(departmentId).concat(",")
					.concat(typeofpatient).concat(",").concat(gender)
					.concat(",").concat(calculateAge));

			rows.add("\n");
			patientCount++;
		}
		Iterator<String> iter = rows.iterator();
		while (iter.hasNext()) {
			String outputString = (String) iter.next();
			response.getOutputStream().print(outputString);
		}

		response.getOutputStream().flush();

	}
}
