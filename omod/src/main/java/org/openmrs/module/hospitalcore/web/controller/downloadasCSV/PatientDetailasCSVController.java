package org.openmrs.module.hospitalcore.web.controller.downloadasCSV;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.openmrs.Encounter;
import org.openmrs.EncounterType;
import org.openmrs.Obs;
import org.openmrs.Patient;
import org.openmrs.PersonAttribute;
import org.openmrs.PersonAttributeType;
import org.openmrs.api.context.Context;
import org.openmrs.module.hospitalcore.HospitalCoreService;
import org.openmrs.module.hospitalcore.IpdService;
import org.openmrs.module.hospitalcore.model.IpdPatientAdmissionLog;
import org.openmrs.module.hospitalcore.model.IpdPatientAdmittedLog;
import org.openmrs.module.hospitalcore.model.PatientCSV;
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
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		String reportName = "Report_On_" + dates + ".csv";
		response.setHeader("Content-disposition", "attachment;filename="
				+ reportName);

		ArrayList<String> rows = new ArrayList<String>();
		rows.add("ninID,patientID,visitID,patientName,mobile,landline,visitDate,visitTime,departmentID,patientTypeID,gender,age");
		rows.add("\n");
		HospitalCoreService hcs = Context.getService(HospitalCoreService.class);
		Set<EncounterType> encounterTypes = new HashSet<EncounterType>();
		encounterTypes.add(Context.getEncounterService().getEncounterType(
				"REGINITIAL"));
		encounterTypes.add(Context.getEncounterService().getEncounterType(
				"REGREVISIT"));
		List<PatientCSV> records = new ArrayList<PatientCSV>();
		int patientCount = 1;

		String typeofpatient = "";
		String visitId = "";
		String departmentId = "";
		String visitDate = "";

		String hours = "";
		String minute = "";
		String seconds = "";
		String visitTime = "";
		String calculateAge = "";

		IpdService inService = Context.getService(IpdService.class);
		Set<Encounter> encounterpatient = hcs.getEncountersByPatientAndDate(
				dates, encounterTypes);
		Set<Encounter> encounterpatientObs = hcs
				.getEncountersByPatientAndDateFromObs(dates);

		List<Encounter> totalEnc1 = new ArrayList<Encounter>();
		List<Encounter> totalEnc2 = new ArrayList<Encounter>();
		totalEnc1.addAll(encounterpatient);
		totalEnc2.addAll(encounterpatientObs);
		totalEnc1.addAll(totalEnc2);
        
		for (Encounter e : totalEnc1) {
			PatientCSV record = new PatientCSV();
			boolean flag = true;
			
			if (e.getEncounterType().getName().equals("REGINITIAL")
					|| e.getEncounterType().getName().equals("REGREVISIT")) {
				Calendar cal = Calendar.getInstance();
				cal.setTime(e.getPatient().getBirthdate());
				int yearNew = cal.get(Calendar.YEAR);
				flag = false;
				List<PersonAttribute> pas = hcs.getPersonAttributes(e
						.getPatientId());
				String identifier = e.getPatient().getPatientIdentifier()
						.getIdentifier();
				String name = e.getPatient().getGivenName().concat(" ")
						.concat(e.getPatient().getMiddleName()).concat(" ")
						.concat(e.getPatient().getFamilyName());
				record.setEncId(e.getEncounterId().toString());
				record.setPatientType("2");
				record.setPatientName(name);
				record.setNinId(GlobalPropertyUtil.getString(
						HospitalCoreConstants.PROPERTY_HOSPITAL_NIN_NUMBER,
						null));
				record.setPatientidentifier(identifier);
				/*String adharnumber = "";
				for (PersonAttribute pa : pas) {
					PersonAttributeType attributeType = pa.getAttributeType();
					if (attributeType.getPersonAttributeTypeId() == 20) {
						adharnumber = pa.getValue();

					}
				}*/
				/*if (adharnumber.equals("")) {
					record.setAdharNumber("0");
				} else {
					record.setAdharNumber(adharnumber);
				}*/
				String phone = "";

				for (PersonAttribute pa : pas) {
					PersonAttributeType attributeType = pa.getAttributeType();
					if (attributeType.getPersonAttributeTypeId() == 16) {
						phone = pa.getValue();
					}
				}
				if (phone.equals("")) {
					record.setMobile("0");
				} else {
					record.setMobile(phone);
				}

				Set<Obs> oList = Context.getObsService().getObservations(e);
				for (Obs o : oList) {
					if (o.getConcept().getName().toString().equals("OPD WARD")) {
						
						
						visitDate = sdf.format(o.getObsDatetime());
						Integer h = o.getObsDatetime().getHours();
						Integer m = o.getObsDatetime().getMinutes();

						if (h < 10)

						{
							hours = "0" + h.toString();
						} else {
							hours = h.toString();
						}
						if (m < 10) {
							minute = "0" + m.toString();

						} else {
							minute = m.toString();
						}

						visitTime = hours.concat(minute);
						departmentId = o.getValueCoded().toString();
						record.setDept(departmentId);
						record.setVisitDate(visitDate);
						record.setVisitTime(visitTime);
						Calendar cal2 = Calendar.getInstance();
						cal2.setTime(o.getObsDatetime());
						int yearOld = cal2.get(Calendar.YEAR);
						int yearDiff = yearOld - yearNew;
						if (yearDiff < 1) {
							yearDiff = 1;
						}
						calculateAge = yearDiff + "";
						record.setAge(calculateAge);
						// break;
					}

				}

				String gender = "";
				if (e.getPatient().getGender().equals("M")) {
					gender = "1";
				} else if (e.getPatient().getGender().equals("F")) {
					gender = "2";
				} else if (e.getPatient().getGender().equals("O")) {
					gender = "3";
				}
				record.setGender(gender);
				records.add(record);

			}
			if (e.getEncounterType().getName().equals("IPDENCOUNTER") && flag) {
				
				List<PersonAttribute> pas = hcs.getPersonAttributes(e
						.getPatientId());
				String identifier = e.getPatient().getPatientIdentifier()
						.getIdentifier();
				
				String name = e.getPatient().getGivenName().concat(" ")
						.concat(e.getPatient().getMiddleName()).concat(" ")
						.concat(e.getPatient().getFamilyName());
				
				record.setEncId(e.getEncounterId().toString());
				
				record.setPatientName(name);
				record.setNinId(GlobalPropertyUtil.getString(
						HospitalCoreConstants.PROPERTY_HOSPITAL_NIN_NUMBER,
						null));
				
				record.setPatientidentifier(identifier);
				
			/*	String adharnumber = "";
				for (PersonAttribute pa : pas) {
					PersonAttributeType attributeType = pa.getAttributeType();
					if (attributeType.getPersonAttributeTypeId() == 20) {
						adharnumber = pa.getValue();

					}
				}
				/*if (adharnumber.equals("")) {
					record.setAdharNumber("0");
				} else {
					record.setAdharNumber(adharnumber);
				}*/
				String phone = "";

				for (PersonAttribute pa : pas) {
					PersonAttributeType attributeType = pa.getAttributeType();
					if (attributeType.getPersonAttributeTypeId() == 16) {
						phone = pa.getValue();
					}
				}
				if (phone.equals("")) {
					record.setMobile("0");
					
				} else {
					record.setMobile(phone);
					
				}
				Set<Obs> obList = Context.getObsService().getObservations(e);
		
				IpdPatientAdmissionLog ipl = inService
						.getIpdPatientAdmissionLogByEncounter(e);
				if (ipl!=null)
				{
				if (ipl.getStatus().toString().equals("admitted")) {
						visitId = e.getEncounterId().toString();
						
						typeofpatient = "1";
                          
						visitDate = sdf.format(ipl.getIpdEncounter().getEncounterDatetime());
						
						record.setVisitDate(visitDate);
						record.setPatientType("1");
						departmentId = ipl.getAdmissionWard().toString();
						
						record.setDept(departmentId);
						Integer h = ipl.getIpdEncounter().getEncounterDatetime().getHours();
						Integer m = ipl.getIpdEncounter().getEncounterDatetime().getMinutes();
					
						hours = h.toString();
						minute = m.toString();

						seconds = seconds +ipl.getIpdEncounter().getEncounterDatetime().getSeconds();
						if (h < 10)

						{
							hours = "0" + h.toString();
						} else {
							hours = h.toString();
						}
						if (m < 10) {
							minute = "0" + m.toString();

						} else {
							minute = m.toString();
						}

						visitTime = hours.concat(minute);
						
						seconds = seconds + ipl.getIpdEncounter().getEncounterDatetime().getSeconds();
						record.setVisitTime(visitTime);
						
						Calendar calci = Calendar.getInstance();
						calci.setTime(e.getPatient().getBirthdate());
						Calendar calci2 = Calendar.getInstance();
						calci2.setTime(ipl.getIpdEncounter().getEncounterDatetime());
						int yearNew1 = calci.get(Calendar.YEAR);
						int yearOld1 = calci2.get(Calendar.YEAR);
						int yearDiff1 = yearOld1 - yearNew1;
						if (yearDiff1 < 1) {
							yearDiff1 = 1;
						}
						calculateAge = yearDiff1 + "";
						record.setAge(calculateAge);
		
						
					}
				else
				{
					if (ipl.getStatus().toString().equals("discharge")) {
						IpdPatientAdmittedLog ipld = inService
								.getIpdPatientAdmittedLogByAdmissionLog(ipl);
						
						departmentId = ipld.getAdmittedWard().toString();
						record.setDept(departmentId);
						Calendar cal = Calendar.getInstance();
						cal.setTime(e.getPatient().getBirthdate());
						Calendar cal2 = Calendar.getInstance();
						cal2.setTime(ipl.getIpdEncounter().getEncounterDatetime());
						int yearNew = cal.get(Calendar.YEAR);
						int yearOld = cal2.get(Calendar.YEAR);
						int yearDiff = yearOld - yearNew;
						if (yearDiff < 1) {
							yearDiff = 1;
						}
						calculateAge = yearDiff + "";
						record.setAge(calculateAge);
						
					}	
				}
				}
					
				String gender = "";
				if (e.getPatient().getGender().equals("M")) {
					gender = "1";
				} else if (e.getPatient().getGender().equals("F")) {
					gender = "2";
				} else if (e.getPatient().getGender().equals("O")) {
					gender = "3";
				}
				record.setGender(gender);
				
				records.add(record);
			
			}

			/*
			 * Get Visit ID : Equals to encounter ID - For inpatient on
			 * descharge ; For outpatient on registration
			 */

			/*
			 * for (Encounter e : encounterpatient) { boolean flag=true; if
			 * (e.getEncounterType().getName().equals("REGINITIAL") ||
			 * e.getEncounterType().getName().equals("REGREVISIT")) {
			 * flag=false; visitId = e.getEncounterId().toString();
			 * typeofpatient = "2";
			 * 
			 * Set<Obs> oList = Context.getObsService().getObservations(e); for
			 * (Obs o : oList) { if
			 * (o.getConcept().getName().toString().equals("OPD WARD")) {
			 * 
			 * 
			 * visitDate=sdf.format(o.getObsDatetime()); hours =
			 * hours+o.getObsDatetime().getHours(); minute = minute +
			 * o.getObsDatetime().getMinutes(); seconds = seconds +
			 * o.getObsDatetime().getSeconds(); visitTime =
			 * hours.concat(minute); departmentId =
			 * o.getValueCoded().toString();
			 * 
			 * Calendar cal2 = Calendar.getInstance();
			 * cal2.setTime(o.getObsDatetime()); int yearOld =
			 * cal2.get(Calendar.YEAR); int yearDiff = yearOld - yearNew;
			 * if(yearDiff<1) { yearDiff=1;} calculateAge = yearDiff +"";
			 * 
			 * //break; } } if(visitTime!=""){ //break; } }
			 * 
			 * 
			 * 
			 * }
			 */

			/*	
			*/
			// String age = pat.getAge().toString();

			// "ID,ninID,patientID,visitID,patientName,mobile,landline,aadhaarNumber,visitDate,visitTime,departmentID,patientTypeID,gender,age"
			 
		}

		for (PatientCSV pcsv : records) {
			String count = patientCount + "";
			if (pcsv.getVisitDate() != null) { 
				rows.add(pcsv.getNinId().concat(",")
						.concat(pcsv.getPatientidentifier()).concat(",")
						.concat(pcsv.getEncId()).concat(",")
						.concat(pcsv.getPatientName()).concat(",")
						.concat(pcsv.getMobile()).concat(",")
						.concat(pcsv.getMobile()).concat(",")
						.concat(pcsv.getVisitDate()).concat(",")
						.concat(pcsv.getVisitTime()).concat(",")
						.concat(pcsv.getDept()).concat(",")
						.concat(pcsv.getPatientType()).concat(",")
						.concat(pcsv.getGender().concat(",")
						.concat(pcsv.getAge()))
						);

				rows.add("\n");
				patientCount++;
			}
		}
		Iterator<String> iter = rows.iterator();
		while (iter.hasNext()) {
			String outputString = (String) iter.next();
			response.getOutputStream().print(outputString);
		}

		response.getOutputStream().flush();

	}
}
