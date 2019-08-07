package org.openmrs.module.hospitalcore.web.controller.drugHIS;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.openmrs.api.context.Context;
import org.openmrs.module.hospitalcore.HospitalCoreService;
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
		
		
		if(date!=null)
		{String dates = date;
		String[] sd= dates.split("-");Integer month=0;
		if(sd[1].equals("Jan"))
		{
		   month=1;	
		}
		else if(sd[1].equals("Feb"))
		{
			month=2;
			
		}
		else if(sd[1].equals("Mar"))
		{
		   month=3;	
		}
		else if(sd[1].equals("Apr"))
		{
			month=4;
			
		}
		else if(sd[1].equals("May"))
		{
			month=5;
			
		}
		else if(sd[1].equals("Jun"))
		{
		   month=6;	
		}
		else if(sd[1].equals("Jul"))
		{
			month=7;
			
		}
		else if(sd[1].equals("Aug"))
		{
			month=8;
			
		}
		else if(sd[1].equals("Sep"))
		{
		   month=9;	
		}
		else if(sd[1].equals("Oct"))
		{
			month=10;
			
		}
		else if(sd[1].equals("Nov"))
		{
			month=11;
			
		}
		else if(sd[1].equals("Dec"))
		{
		   month=12;	
		}
		
		model.addAttribute("date", sd[0]);
		HospitalCoreService hcs= (HospitalCoreService) Context.getService(HospitalCoreService.class);
		
		Integer noOfpatientWithAnalgesicsAntiPyreticdrugs=hcs.getNoOfPatientWithAnalgesicsAntiPyreticdrugs(month,sd[0]);
		Integer noOfFepatientWithAntiAllergicdrugs=hcs.getNoOfPatientWithAntiAllergicdrugs(month,sd[0]);
		Integer noOfpatientWithAntiAnaemicdrugs=hcs.getNoOfPatientWithAntiAnaemicdrugs(month,sd[0]);
		Integer noOfFepatientWithAntiDiabeticdrugs=hcs.getNoOfPatientWithAntiDiabeticdrugs(month,sd[0]);
		Integer noOfpatientWithAntiEpilepticdrugs=hcs.getNoOfPatientWithAntiEpilepticdrugs(month,sd[0]);
		Integer noOfFepatientWithAntFilarialr=hcs.getNoOfPatientWithAntFilarial(month,sd[0]);
		Integer noOfpatientWithAntiFungaldrugs=hcs.getNoOfPatientWithAntiFungaldrugs(month,sd[0]);
		Integer noOfFepatientWithAntiLeishmaniasisdrugs=hcs.getNoOfPatientWithAntiLeishmaniasisdrugs(month,sd[0]);
		Integer noOfpatientWithAntiParkinsonismdrugs=hcs.getNoOfPatientWithAntiParkinsonismdrugs(month,sd[0]);
		Integer noOfFepatientWithAntiProtozoaldrugs=hcs.getNoOfPatientWithAntiProtozoaldrugs(month,sd[0]);
		Integer noOfpatientWithAntiRabies=hcs.getNoOfPatientWithAntiRabies(month,sd[0]);
		Integer noOfFepatientWithAntiBacterials=hcs.getNoOfPatientWithAntiBacterials(month,sd[0]);
		Integer noOfpatientWithAntiCancerdrugs=hcs.getNoOfPatientWithAntiCancerdrugs(month,sd[0]);
		Integer noOfFepatientWithAntiHelminthics=hcs.getNoOfPatientWithAntiHelminthics(month,sd[0]);
		Integer noOfpatientWithAntiMalarials=hcs.getNoOfPatientWithAntiMalarials(month,sd[0]);
		Integer noOfFepatientWithAntiSeptics=hcs.getNoOfPatientWithAntiSeptics(month,sd[0]);
		Integer noOfpatientWithAntiVertigodrugs=hcs.getNoOfPatientWithAntiVertigodrugs(month,sd[0]);
		Integer noOfFepatientWithAntiVirals=hcs.getNoOfPatientWithAntiVirals(month,sd[0]);
		Integer noOfpatientWithContrastagents=hcs.getNoOfPatientWithCardiovasculardrugs(month,sd[0]);
		Integer noOfFepatientWithAscan=hcs.getNoOfPatientWithContrastagents(month,sd[0]);
		Integer noOfpatientWithDentalpreparation=hcs.getNoOfPatientWithDentalpreparation(month,sd[0]);
		Integer noOfFepatientWithDermatologicalointmentcreams=hcs.getNoOfPatientWithDermatologicalointmentcreams(month,sd[0]);
		Integer noOfpatientWithDiuretics=hcs.getNoOfPatientWithDiuretics(month,sd[0]);
		Integer noOfFepatientWithDrugsactingRespiratory=hcs.getNoOfPatientWithDrugsactingRespiratory(month,sd[0]);
		Integer noOfpatientWithDrugscoagulation=hcs.getNoOfPatientWithDrugscoagulation(month,sd[0]);
		Integer noOfFepatientWithDrugsGouRheumatoid=hcs.getNoOfPatientWithDrugsGouRheumatoid(month,sd[0]);
		Integer noOfpatientWithDrugsMigraine=hcs.getNoOfPatientWithDrugsMigraine(month,sd[0]);
		Integer noOfFepatientWithGastrointestinal=hcs.getNoOfPatientWithGastrointestinal(month,sd[0]);
		Integer noOfpatientWithAnaesthetics=hcs.getNoOfPatientWithAnaesthetics(month,sd[0]);
		Integer noOfFepatientWithHormoneEndocrine=hcs.getNoOfPatientWithHormoneEndocrine(month,sd[0]);
		Integer noOfpatientWithImmunologicals=hcs.getNoOfPatientWithImmunologicals(month,sd[0]);
		Integer noOfFepatientWithLifeSaving=hcs.getNoOfPatientWithLifeSaving(month,sd[0]);
		Integer noOfpatientWithMetabolism=hcs.getNoOfPatientWithMetabolism(month,sd[0]);
		Integer noOfFepatientWithMucolytic=hcs.getNoOfPatientWithMucolytic(month,sd[0]);
		Integer noOfpatientWithAntiCholinesterases=hcs.getNoOfPatientWithAntiCholinesterases(month,sd[0]);
		Integer noOfFepatientWithOphthalmological=hcs.getNoOfPatientWithOphthalmological(month,sd[0]);
		Integer noOfpatientWithOpthalmic=hcs.getNoOfPatientWithOpthalmic(month,sd[0]);
		Integer noOfFepatientWithOxytocics=hcs.getNoOfPatientWithOxytocics(month,sd[0]);
		Integer noOfpatientWithPsychotherapeutic=hcs.getNoOfPatientWithPsychotherapeutic(month,sd[0]);
		Integer noOfFepatientWithElectrolyte=hcs.getNoOfPatientWithElectrolyte(month,sd[0]);
		Integer noOfpatientWithParenteral=hcs.getNoOfPatientWithParenteral(month,sd[0]);
		Integer noOfpatientWithVitamins=hcs.getNoOfPatientWithVitamins(month,sd[0]);
		
		
		model.addAttribute("noOfpatientWithAnalgesicsAntiPyreticdrugs",noOfpatientWithAnalgesicsAntiPyreticdrugs);
		model.addAttribute("noOfFepatientWithAntiAllergicdrugs",noOfFepatientWithAntiAllergicdrugs);
		model.addAttribute("noOfpatientWithAntiAnaemicdrugs",noOfpatientWithAntiAnaemicdrugs);
		model.addAttribute("noOfFepatientWithAntiDiabeticdrugs",noOfFepatientWithAntiDiabeticdrugs);
		model.addAttribute("noOfpatientWithAntiEpilepticdrugs",noOfpatientWithAntiEpilepticdrugs);
		model.addAttribute("noOfFepatientWithAntFilarialr",noOfFepatientWithAntFilarialr);
		model.addAttribute("noOfpatientWithAntiFungaldrugs",noOfpatientWithAntiFungaldrugs);
		model.addAttribute("noOfFepatientWithAntiLeishmaniasisdrugs",noOfFepatientWithAntiLeishmaniasisdrugs);
		model.addAttribute("noOfpatientWithAntiParkinsonismdrugs",noOfpatientWithAntiParkinsonismdrugs);
		model.addAttribute("noOfFepatientWithAntiProtozoaldrugs",noOfFepatientWithAntiProtozoaldrugs);
		model.addAttribute("noOfpatientWithAntiRabies",noOfpatientWithAntiRabies);
		model.addAttribute("noOfFepatientWithAntiBacterials",noOfFepatientWithAntiBacterials);
		model.addAttribute("noOfpatientWithAntiCancerdrugs",noOfpatientWithAntiCancerdrugs);
		model.addAttribute("noOfFepatientWithAntiHelminthics",noOfFepatientWithAntiHelminthics);
		model.addAttribute("noOfpatientWithAntiMalarials",noOfpatientWithAntiMalarials);
		model.addAttribute("noOfFepatientWithAntiSeptics",noOfFepatientWithAntiSeptics);
		model.addAttribute("noOfpatientWithAntiVertigodrugs",noOfpatientWithAntiVertigodrugs);
		model.addAttribute("noOfFepatientWithAntiVirals",noOfFepatientWithAntiVirals);
		model.addAttribute("noOfpatientWithContrastagents",noOfpatientWithContrastagents);
		model.addAttribute("noOfFepatientWithAscan",noOfFepatientWithAscan);
		model.addAttribute("noOfpatientWithDentalpreparation",noOfpatientWithDentalpreparation);
		model.addAttribute("noOfFepatientWithDermatologicalointmentcreams",noOfFepatientWithDermatologicalointmentcreams);
		model.addAttribute("noOfpatientWithDiuretics",noOfpatientWithDiuretics);
		model.addAttribute("noOfFepatientWithDrugsactingRespiratory",noOfFepatientWithDrugsactingRespiratory);
		model.addAttribute("noOfpatientWithDrugscoagulation",noOfpatientWithDrugscoagulation);
		model.addAttribute("noOfFepatientWithDrugsGouRheumatoid",noOfFepatientWithDrugsGouRheumatoid);
		model.addAttribute("noOfpatientWithDrugsMigraine",noOfpatientWithDrugsMigraine);
		model.addAttribute("noOfFepatientWithGastrointestinal",noOfFepatientWithGastrointestinal);
		model.addAttribute("noOfpatientWithAnaesthetics",noOfpatientWithAnaesthetics);
		model.addAttribute("noOfFepatientWithHormoneEndocrine",noOfFepatientWithHormoneEndocrine);
		model.addAttribute("noOfpatientWithImmunologicals",noOfpatientWithImmunologicals);
		model.addAttribute("noOfFepatientWithLifeSaving",noOfFepatientWithLifeSaving);
		model.addAttribute("noOfpatientWithMetabolism",noOfpatientWithMetabolism);
		model.addAttribute("noOfFepatientWithMucolytic",noOfFepatientWithMucolytic);
		model.addAttribute("noOfpatientWithAntiCholinesterases",noOfpatientWithAntiCholinesterases);
		model.addAttribute("noOfFepatientWithOphthalmological",noOfFepatientWithOphthalmological);
		model.addAttribute("noOfpatientWithOpthalmic",noOfpatientWithOpthalmic);
		model.addAttribute("noOfFepatientWithOxytocics",noOfFepatientWithOxytocics);
		model.addAttribute("noOfpatientWithPsychotherapeutic",noOfpatientWithPsychotherapeutic);
		model.addAttribute("noOfFepatientWithElectrolyte",noOfFepatientWithElectrolyte);
		model.addAttribute("noOfpatientWithParenteral",noOfpatientWithParenteral);
		model.addAttribute("noOfpatientWithVitamins",noOfpatientWithVitamins);
		
		}
		return "/module/hospitalcore/drugHIS/patientDrugReport";
	}
	public void controller(
			@RequestParam(value = "date", required = false) String date) {
		
		HospitalCoreService hcs= (HospitalCoreService) Context.getService(HospitalCoreService.class);
		String dates = date;
		String[] sd= dates.split("-");
		
		
///	Integer getcountpat=p
				//hcs.getPatientCount(startOfPeriod,endOfPeriod);
}
}

