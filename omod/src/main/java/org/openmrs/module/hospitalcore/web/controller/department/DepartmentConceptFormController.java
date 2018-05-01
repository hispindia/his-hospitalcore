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



package org.openmrs.module.hospitalcore.web.controller.department;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.openmrs.Concept;
import org.openmrs.User;
import org.openmrs.api.context.Context;
import org.openmrs.module.hospitalcore.PatientDashboardService;
import org.openmrs.module.hospitalcore.model.Department;
import org.openmrs.module.hospitalcore.model.DepartmentConcept;
import org.openmrs.module.hospitalcore.util.ConceptComparator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * <p> Class: DepartmentConceptFormController </p>
 * <p> Package: org.openmrs.module.hospitalcore.web.controller.department </p> 
 * <p> Author: Nguyen manh chuyen </p>
 * <p> Update by: Nguyen manh chuyen </p>
 * <p> Version: $1.0 </p>
 * <p> Create date: Apr 13, 2011 4:46:16 PM </p>
 * <p> Update date: Apr 13, 2011 4:46:16 PM </p>
 **/

@Controller("DepartmentConceptFormController")
@RequestMapping("/module/hospitalcore/departmentConcept.form")
public class DepartmentConceptFormController {
	@RequestMapping(method = RequestMethod.GET)
	public String firstView(@ModelAttribute("departmentConceptCommand") DepartmentConceptCommand departmentConceptCommand, @RequestParam(value="dId",required=false) Integer id, Model model) {
		PatientDashboardService dashboardService = Context.getService(PatientDashboardService.class);
		List<Department> listDepartment = dashboardService.listDepartment(false);
		model.addAttribute("listDepartment", listDepartment);
		if( id != null ){
			//Department department = dashboardService.getDepartmentById(id);
			List<DepartmentConcept> listDiagnosisDepartment = dashboardService.listByDepartment(id, DepartmentConcept.TYPES[0]);
			List<DepartmentConcept> listProcedureDepartment = dashboardService.listByDepartment(id, DepartmentConcept.TYPES[1]);
			List<DepartmentConcept> listSymptomDepartment = dashboardService.listByDepartment(id, DepartmentConcept.TYPES[3]);
			
			List<Concept> diagnosisList = dashboardService.searchDiagnosis(null);
			Collections.sort(diagnosisList, new ConceptComparator());
			
			
			//model.addAttribute("listDiagnosis", diagnosis);
			List<Concept> procedures = dashboardService.searchProcedure(null);
			Collections.sort(procedures, new ConceptComparator());
			
			
			
			//symptom list should be shown in left
			List<Concept> symptom = dashboardService.searchSymptom(null);
			Collections.sort(symptom, new ConceptComparator());
			
			
			
			List<Concept> listC = new ArrayList<Concept>();
			if(CollectionUtils.isNotEmpty(listDiagnosisDepartment)){
				for(DepartmentConcept c : listDiagnosisDepartment){
					listC.add(c.getConcept());
				}
			}
			diagnosisList.removeAll(listC);
			
			listC = new ArrayList<Concept>();
			if(CollectionUtils.isNotEmpty(listProcedureDepartment)){
				for(DepartmentConcept c : listProcedureDepartment){
					listC.add(c.getConcept());
				}
			}
			procedures.removeAll(listC);
			
			//symptom 
			listC = new ArrayList<Concept>();
			if(CollectionUtils.isNotEmpty(listSymptomDepartment)){
				for(DepartmentConcept c : listSymptomDepartment){
					listC.add(c.getConcept());
				}
			}
			symptom.removeAll(listC);
			
			
			
			
			
			
			//List all department
			
			
			model.addAttribute("listProcedures", procedures);
			model.addAttribute("diagnosisList", diagnosisList);
			model.addAttribute("listSymptom", symptom);
			
			model.addAttribute("dId",id);
			model.addAttribute("listDiagnosisDepartment",listDiagnosisDepartment);
			model.addAttribute("listProcedureDepartment",listProcedureDepartment);
			model.addAttribute("listSymptomDepartment",listSymptomDepartment);
		}
		
		
		return "/module/hospitalcore/department/departmentConcept";
	}
	@RequestMapping(method=RequestMethod.POST)
	public String formSummit(DepartmentConceptCommand command) throws Exception{
		PatientDashboardService dashboardService = Context.getService(PatientDashboardService.class);
		List<DepartmentConcept> listDiagnosisDepartment = dashboardService.listByDepartment(command.getDepartmentId(), DepartmentConcept.TYPES[0]);
		List<DepartmentConcept> listProcedureDepartment = dashboardService.listByDepartment(command.getDepartmentId(), DepartmentConcept.TYPES[1]);
		//symptom
		List<DepartmentConcept> listSymptomDepartment = dashboardService.listByDepartment(command.getDepartmentId(), DepartmentConcept.TYPES[3]);

		
		Department department = dashboardService.getDepartmentById(command.getDepartmentId());
		
		String user = Context.getAuthenticatedUser().getGivenName();
		Date date = new Date();
		//process diagnosis first
		if(CollectionUtils.isEmpty(listDiagnosisDepartment)){
			for(Integer iDiag : command.getSelectedDiagnosisList()){
				DepartmentConcept departmentConcept = new DepartmentConcept();
				departmentConcept.setDepartment(department);
				departmentConcept.setConcept(Context.getConceptService().getConcept(iDiag));
				departmentConcept.setCreatedBy(user);
				departmentConcept.setCreatedOn(date);
				departmentConcept.setTypeConcept(DepartmentConcept.TYPES[0]);
				dashboardService.createDepartmentConcept(departmentConcept);
			}
		}else{
			List<Integer> listIdDiagnosis = new ArrayList<Integer>();
			
			for(Integer i : command.getSelectedDiagnosisList()){
				listIdDiagnosis.add(i);
			}
			
			for(DepartmentConcept dC : listDiagnosisDepartment){
				if(!listIdDiagnosis.contains(dC.getConcept().getId())){
					dashboardService.removeDepartmentConcept(dC);
				}else{
					listIdDiagnosis.remove(dC.getConcept().getId());
				}
			}
			
			//create department diagnosis
			
			if(CollectionUtils.isNotEmpty(listIdDiagnosis)){
				for(Integer diag : listIdDiagnosis){
					DepartmentConcept departmentConcept = new DepartmentConcept();
					departmentConcept.setDepartment(department);
					departmentConcept.setConcept(Context.getConceptService().getConcept(diag));
					departmentConcept.setCreatedBy(user);
					departmentConcept.setCreatedOn(date);
					departmentConcept.setTypeConcept(DepartmentConcept.TYPES[0]);
					dashboardService.createDepartmentConcept(departmentConcept);
				}
			}
			
		}
		//Process procedure
		if(CollectionUtils.isEmpty(listProcedureDepartment)){
			for(Integer iPro : command.getSelectedProcedureList()){
				DepartmentConcept departmentConcept = new DepartmentConcept();
				departmentConcept.setDepartment(department);
				departmentConcept.setConcept(Context.getConceptService().getConcept(iPro));
				departmentConcept.setCreatedBy(user);
				departmentConcept.setCreatedOn(date);
				departmentConcept.setTypeConcept(DepartmentConcept.TYPES[1]);
				dashboardService.createDepartmentConcept(departmentConcept);
			}
		}else{
			List<Integer> listIdProcedure = new ArrayList<Integer>();
			
			for(Integer i : command.getSelectedProcedureList()){
				listIdProcedure.add(i);
			}
			
			for(DepartmentConcept dC : listProcedureDepartment){
				if(!listIdProcedure.contains(dC.getConcept().getId())){
					dashboardService.removeDepartmentConcept(dC);
				}else{
					listIdProcedure.remove(dC.getConcept().getId());
				}
			}
			
			//create department procedure
			
			if(CollectionUtils.isNotEmpty(listIdProcedure)){
				for(Integer proc : listIdProcedure){
					DepartmentConcept departmentConcept = new DepartmentConcept();
					departmentConcept.setDepartment(department);
					departmentConcept.setConcept(Context.getConceptService().getConcept(proc));
					departmentConcept.setCreatedBy(user);
					departmentConcept.setCreatedOn(date);
					departmentConcept.setTypeConcept(DepartmentConcept.TYPES[1]);
					dashboardService.createDepartmentConcept(departmentConcept);
				}
			}
		}
		
		//symptom
		if(CollectionUtils.isEmpty(listSymptomDepartment)){
			for(Integer iSymp : command.getSelectedSymptomList()){
				DepartmentConcept departmentConcept = new DepartmentConcept();
				departmentConcept.setDepartment(department);
				departmentConcept.setConcept(Context.getConceptService().getConcept(iSymp));
				departmentConcept.setCreatedBy(user);
				departmentConcept.setCreatedOn(date);
				departmentConcept.setTypeConcept(DepartmentConcept.TYPES[3]);
				dashboardService.createDepartmentConcept(departmentConcept);
			}
		}else{
			List<Integer> listIdSymptom = new ArrayList<Integer>();
			
			for(Integer i : command.getSelectedSymptomList()){
				listIdSymptom.add(i);
			}
			
			for(DepartmentConcept dC : listSymptomDepartment){
				if(!listIdSymptom.contains(dC.getConcept().getId())){
					dashboardService.removeDepartmentConcept(dC);
				}else{
					listIdSymptom.remove(dC.getConcept().getId());
				}
			}
			
			//create department symptom
			
			if(CollectionUtils.isNotEmpty(listIdSymptom)){
				for(Integer proc : listIdSymptom){
					DepartmentConcept departmentConcept = new DepartmentConcept();
					departmentConcept.setDepartment(department);
					departmentConcept.setConcept(Context.getConceptService().getConcept(proc));
					departmentConcept.setCreatedBy(user);
					departmentConcept.setCreatedOn(date);
					departmentConcept.setTypeConcept(DepartmentConcept.TYPES[3]);
					dashboardService.createDepartmentConcept(departmentConcept);
				}
			}
		}
		
		return "redirect:/module/hospitalcore/departmentConcept.form?dId="+command.getDepartmentId();
	}
}
