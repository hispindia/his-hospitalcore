/**
 *  Copyright 2009 Society for Health Information Systems Programmes, India (HISP India)
 *
 *  This file is part of Hospitalcore module.
 *
 *  Hospitalcore module is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.

 *  Hospitalcore module is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with Hospitalcore module.  If not, see <http://www.gnu.org/licenses/>.
 *
*/
HOSPITALCORE={
		onChangeDiagnosis : function(container, id)
		{
			if(container == 'diagnosis'){
				jQuery("#availableDiagnosisList option[value=" +id+ "]").appendTo("#selectedDiagnosisList");
				jQuery("#availableDiagnosisList option[value=" +id+ "]").remove();
				jQuery("#diagnosis").val("");
			}
			if(container == 'procedure'){
				jQuery("#availableProcedureList option[value=" +id+ "]").appendTo("#selectedProcedureList");
				jQuery("#availableProcedureList option[value=" +id+ "]").remove();
				jQuery("#procedure").val("");
			}
		},
		onChangeDepartment : function(thiz)
		{
			ACT.go('departmentConcept.form?dId='+jQuery(thiz).val());
		},
		submit : function(){
			jQuery('#selectedDiagnosisList option').each(function(i) {  
				 jQuery(this).attr("selected", "selected");  
			}); 
			jQuery('#selectedProcedureList option').each(function(i) {  
				 jQuery(this).attr("selected", "selected");  
			}); 
			jQuery('#selectedSymptomList option').each(function(i) {  
				 jQuery(this).attr("selected", "selected");  
			});
			jQuery("#formDepartmentConcept").submit();
		}
		
};


