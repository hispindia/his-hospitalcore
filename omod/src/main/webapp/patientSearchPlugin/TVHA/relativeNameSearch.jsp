 <%--
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
--%> 
<script type="text/javascript">
	/**
	 * June 8th 2012: Thai Chuong add lastDayOfVisist
	 * Registration, feature: Search a patient on the basis of last day of visit
	 * UC-7- Advance search of patient
	 * Supported issue #256
	 */

	/** 
	 ** SEARCH FUNCTION
	 **/
	PATIENTSEARCH = {
		target: "#patientSearchResult",
		resultView: "",
		selectClause: "",
		fromClause: "",
		whereClause: "",
		//ghanshyam 16-march-2013 Support #1110[Registration]ddu server slow(added groupClause)
		groupClause: "",
		orderClause: "",
		limitClause: "",
		query: "",
		currentRow: 0,
		rowPerPage: 10,
		totalRow: 0,		
		advanceSearch: false,
		form: null,
		success: function(data){},
		beforeNewSearch: function(){},
		
		/** CONSTRUCTOR  FOR PATIENT SEARCH
		 * options = {
		 *		rowPerPage: number of rows per result page. Default is 10.
		 * }
		 */		
		init: function(options){
		
			// Set value for configuration			
			this.resultView = options.resultView;
			this.target = options.target;
			this.rowPerPage = options.rowPerPage;	
			this.success = options.success;
			this.beforeNewSearch = options.beforeNewSearch;
			
			// Display form
			this.form = jQuery("#patientSearchForm");
			jQuery("#advanceSearch", this.form).hide();	
			jQuery("#nameOrgivenNameOrmiddleNameOrfamilyNameOrIdentifier", this.form).keyup(function(event){				
				if(event.keyCode == 13){	
					nameInCapital = StringUtils.capitalize(jQuery("#nameOrgivenNameOrmiddleNameOrfamilyNameOrIdentifier", PATIENTSEARCH.form).val());
					jQuery("#nameOrgivenNameOrmiddleNameOrfamilyNameOrIdentifier", PATIENTSEARCH.form).val(nameInCapital);
					PATIENTSEARCH.search(true);
				}
				// ghanshyam 2012-6-12 #261 added validation for special character in patient name
 				else{
				PATIENTSEARCH.validateNameOrIdentifierWithSpecialChar();
				}
			});

			jQuery("#gender", this.form).change(function(){
				PATIENTSEARCH.search(true);
			});				
			
			//ghanshyam 12-sept-2013 New Requirement #2684 Introducing a field at the time of registration to put Aadhar Card Number
			jQuery("#acNo", this.form).keyup(function(event) {
				if (event.keyCode == 13) {
					PATIENTSEARCH.search(true);
					}
			});	
			
			jQuery("#greenBookNo", this.form).keyup(function(event) {
				if (event.keyCode == 13) {
					PATIENTSEARCH.search(true);
					}
			});			
		},
		
		// ghanshyam 2012-6-12 #261 added validation for special character in patient name
		/** VALIDATE NAME OR IDENTIFIER With Special Character*/
		validateNameOrIdentifierWithSpecialChar: function(){
			
			value = jQuery("#nameOrgivenNameOrmiddleNameOrfamilyNameOrIdentifier", this.form).val();
			value = value.toUpperCase();
			if(value.length>=3){
				pattern = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789 -";
				for(i=0; i<value.length; i++){
					if(pattern.indexOf(value[i])<0){	
						jQuery("#errorSection", this.form).html("<ul id='errorList' class='error'></ul>");
						jQuery("#errorList", this.form).append("<li>Please enter patient name/identifier in correct format!</li>");
						return false;							
					}
					jQuery("#errorList", this.form).empty();
				}	
				return true;
			}
		},
		
		
		
		
		/** SEARCH */
		search: function(newQuery){
		
			// validate the form
			validatedResult = this.validate();			
			if(validatedResult) {
				// reset navigation for new query
				if(newQuery == true){
					this.currentRow = 0;		
					// callback
					PATIENTSEARCH.beforeNewSearch();
				}
				
				var query = this.buildQuery();				
				
				jQuery(PATIENTSEARCH.target).mask("<img src='" + openmrsContextPath + "/moduleResources/hospitalcore/ajax-loader.gif" + "'/>&nbsp;");
				
				jQuery.ajax({
					type : "POST",
					url : openmrsContextPath + "/module/hospitalcore/searchPatient.form",
					data : ({
						query: query,
						view: PATIENTSEARCH.resultView
					}),				
					success : function(data) {						
						jQuery(PATIENTSEARCH.target).html(data);						
						if(PATIENTSEARCH.currentRow==0){
							PATIENTSEARCH.getPatientResultCount();
						} else {						
							jQuery(PATIENTSEARCH.target).append("<div>" + PATIENTSEARCH.generateNavigation() + "</div>");	
							
							// callback
							PATIENTSEARCH.success({
								totalRow: PATIENTSEARCH.totalRow
							});
						}
						jQuery(PATIENTSEARCH.target).unmask();
						
					},
					error : function(xhr, ajaxOptions, thrownError) {
						alert(thrownError);
					}
				});
			};
		},
		
		/** GET PATIENT RESULT COUNT */
		getPatientResultCount: function(){
			
			
			var query = this.buildCountQuery();
			
			jQuery.ajax({
				type : "POST",
				url : openmrsContextPath + "/module/hospitalcore/getPatientResultCount.form",
				data : ({
					query: query
				}),				
				success : function(data) {					
					PATIENTSEARCH.totalRow = data;	
					jQuery(PATIENTSEARCH.target).append("<div>" + PATIENTSEARCH.generateNavigation() + "</div>");		
					// callback
					PATIENTSEARCH.success({
						totalRow: PATIENTSEARCH.totalRow
					});
				},
				error : function(xhr, ajaxOptions, thrownError) {
					alert(thrownError);
				}
			});
		},
		
		/** BUILD QUERY */
		buildQuery: function(){
		
			// Get value from form			
			nameOrIdentifier = jQuery.trim(jQuery("#nameOrgivenNameOrmiddleNameOrfamilyNameOrIdentifier", this.form).val());	
			//nameOrIdentifier = nameOrIdentifier.replace(/\s/g, "");			
		
			// Build essential query
			//ghanshyam 16-march-2013 Support #1110[Registration]ddu server slow(commented old query below and written new query after this commented query)
			/*
			this.selectClause = "SELECT DISTINCT pt.patient_id, pi.identifier, pn.given_name, pn.middle_name, pn.family_name, ps.gender, ps.birthdate, EXTRACT(YEAR FROM (FROM_DAYS(DATEDIFF(NOW(),ps.birthdate)))) age, pn.person_name_id";
			this.fromClause   = " FROM `patient` pt";
			this.fromClause  += " INNER JOIN person ps ON ps.person_id = pt.patient_id";
			this.fromClause  += " INNER JOIN person_name pn ON pn.person_id = ps.person_id";
			this.fromClause  += " INNER JOIN patient_identifier pi ON pi.patient_id = pt.patient_id";
			this.whereClause  = " WHERE";
			this.whereClause += " (pi.identifier LIKE '%" + nameOrIdentifier + "%' OR CONCAT(IFNULL(pn.given_name, ''), IFNULL(pn.middle_name, ''), IFNULL(pn.family_name,'')) LIKE '" + nameOrIdentifier + "%')";			
			this.whereClause+= "AND ps.dead=0";
			this.orderClause = " ORDER BY pt.patient_id ASC";
			this.limitClause = " LIMIT " + this.currentRow + ", " + this.rowPerPage;	
			*/
			
			//ghanshyam,22-oct-2013,New Requirement #2940 Dealing with dead patient
			this.selectClause = "SELECT ps.patient_id, ps.identifier, ps.given_name, ps.middle_name, ps.family_name, ps.gender, ps.birthdate, ps.age, ps.person_name_id, ps.dead, ps.admitted ";
			this.fromClause   = " FROM patient_search ps";
			this.fromClause  += " INNER JOIN person pe ON pe.person_id = ps.patient_id";
			this.whereClause  = " WHERE";
			this.whereClause += " (ps.identifier LIKE '%" + nameOrIdentifier +"%' OR ps.fullname LIKE '%" + nameOrIdentifier +  "%')";			
			 //ghanshyam,22-oct-2013,New Requirement #2940 Dealing with dead patient
			//this.whereClause += " AND pe.dead=0";
			this.groupClause = " GROUP BY ps.patient_id";
			this.orderClause = " ORDER BY ps.patient_id ASC";
			this.limitClause = " LIMIT " + this.currentRow + ", " + this.rowPerPage;			

			//	Build extended queries
			if(this.advanceSearch){
				this.buildGenderQuery();
				this.buildAadharCardNumberQuery();
				this.buildGreenBookNumberQuery();
			}
			
			// Return the built query
			//ghanshyam 16-march-2013 Support #1110[Registration]ddu server slow(added groupClause)
			this.query = this.selectClause + this.fromClause + this.whereClause + this.groupClause + this.orderClause + this.limitClause;
			//this.query = this.selectClause + this.fromClause + this.whereClause + this.orderClause + this.limitClause;		
			return this.query;
		},
		
		/** BUILD COUNT QUERY */
		buildCountQuery: function(){
		
			// Get value from form			
			nameOrIdentifier = jQuery.trim(jQuery("#nameOrgivenNameOrmiddleNameOrfamilyNameOrIdentifier", this.form).val());			
			//nameOrIdentifier = nameOrIdentifier.replace(/\s/g, "");
		
			// Build essential query
			//ghanshyam 16-march-2013 Support #1110[Registration]ddu server slow(commented old query below and written new query after this commented query)
			/*
			this.selectClause = "SELECT COUNT(DISTINCT pt.patient_id)";
			this.fromClause   = " FROM `patient` pt";
			this.fromClause  += " INNER JOIN person ps ON ps.person_id = pt.patient_id";
			this.fromClause  += " INNER JOIN person_name pn ON pn.person_id = ps.person_id";
			this.fromClause  += " INNER JOIN patient_identifier pi ON pi.patient_id = pt.patient_id";
			this.whereClause  = " WHERE";
			this.whereClause += " (pi.identifier LIKE '%" + nameOrIdentifier + "%' OR CONCAT(IFNULL(pn.given_name, ''), IFNULL(pn.middle_name, ''), IFNULL(pn.family_name,'')) LIKE '" + nameOrIdentifier + "%')";						
			this.whereClause+= "AND ps.dead=0";
			*/
			
			this.selectClause = "SELECT COUNT(DISTINCT ps.patient_id)";
			this.fromClause   = " FROM patient_search ps";
			this.fromClause  += " INNER JOIN person pe ON pe.person_id = ps.patient_id";
			this.whereClause  = " WHERE";
			this.whereClause += " (ps.identifier LIKE '%" + nameOrIdentifier + "%' OR ps.fullname LIKE '%" + nameOrIdentifier + "%')";	
			 //ghanshyam,22-oct-2013,New Requirement #2940 Dealing with dead patient
			//this.whereClause+= " AND pe.dead=0";
			
			//	Build extended queries
			if(this.advanceSearch){
				this.buildGenderQuery();
				this.buildAadharCardNumberQuery();
				this.buildGreenBookNumberQuery();
			}
			
			// Return the built query
			this.query = this.selectClause + this.fromClause + this.whereClause;		
			return this.query;
		},
		
		/** NEXT PAGE */
		nextPage: function(){
			this.currentRow += this.rowPerPage;
			this.search(false);
		},
		
		/** PREV PAGE */
		prevPage: function(){
			this.currentRow -= this.rowPerPage;
			this.search(false);
		},
		
		/** SHOW ADVANCE SEARCH */
		toggleAdvanceSearch: function(){
			if(this.advanceSearch){
				jQuery("#advanceSearch", this.form).hide();
				this.advanceSearch = false;
			} else {
				jQuery("#advanceSearch", this.form).show();
				this.advanceSearch = true;
			}
			
		},
		
		/** BUILD QUERY FOR GENDER */
		buildGenderQuery: function(){
			value = jQuery.trim(jQuery("#gender", this.form).val());
			if(value!='Any'){
				this.whereClause += " AND (ps.gender = '" + value + "') ";
			}
			//ghanshyam 13-september-2013 New Requirement #1625 Advance search-Without selecting gender if age is selected say undefined
			else{
			this.whereClause += " AND 1 ";
			}
		},
	
		buildAadharCardNumberQuery: function(){
		    value = jQuery.trim(jQuery("#acNo", this.form).val());
			aadharCardNumberAttributeTypeName = "Aadhar Card Number";
			if(value!=undefined && value.length>0){
			    this.fromClause += " INNER JOIN person_attribute paAadharCardNumber ON ps.patient_id= paAadharCardNumber.person_id";
				this.fromClause += " INNER JOIN person_attribute_type patAadharCardNumber ON paAadharCardNumber.person_attribute_type_id = patAadharCardNumber.person_attribute_type_id ";
				this.whereClause += " AND (patAadharCardNumber.name LIKE '%" + aadharCardNumberAttributeTypeName + "%' AND paAadharCardNumber.value LIKE '%" + value + "%')";
			}
		},
		
		buildGreenBookNumberQuery: function(){
		    value = jQuery.trim(jQuery("#greenBookNo", this.form).val());
			greenBookNumberAttributeTypeName = "Green Book No";
			if(value!=undefined && value.length>0){
			    this.fromClause += " INNER JOIN person_attribute paGreenBookNumber ON ps.patient_id= paGreenBookNumber.person_id";
				this.fromClause += " INNER JOIN person_attribute_type patGreenBookNumber ON paGreenBookNumber.person_attribute_type_id = patGreenBookNumber.person_attribute_type_id ";
				this.whereClause += " AND (patGreenBookNumber.name LIKE '%" + greenBookNumberAttributeTypeName + "%' AND paGreenBookNumber.value LIKE '%" + value + "%')";
			}
		},
		
		/** GENERATE THE NAVIGATION BAR */
		generateNavigation: function(){
			navbar = this.totalRow + " patients found.";
			
			if(this.currentRow > 0) {
				navbar += "&nbsp;&nbsp;<a href='javascript:PATIENTSEARCH.prevPage();'>&laquo;&laquo; Prev</a>&nbsp;&nbsp;";
			}
			
			navbar += "page " + (this.currentRow/this.rowPerPage + 1);
			
			if(this.currentRow + this.rowPerPage < this.totalRow) {
				navbar += "&nbsp;&nbsp;<a href='javascript:PATIENTSEARCH.nextPage();'>Next &raquo;&raquo;</a>&nbsp;&nbsp;";
			}
			
			return navbar;
		},
		
		/** VALIDATE FORM BEFORE QUERYING */
		validate: function(){
			jQuery("#errorSection", this.form).html("<ul id='errorList' class='error'></ul>");
			result = true;
			result = result && this.validateNameOrIdentifier();
			return result;
		},
		
		/** VALIDATE NAME OR IDENTIFIER */
		validateNameOrIdentifier: function(){
			
			value = jQuery("#nameOrgivenNameOrmiddleNameOrfamilyNameOrIdentifier", this.form).val();
			value = value.toUpperCase();
			if(value.length>=3){
				pattern = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789 -";
				for(i=0; i<value.length; i++){
					if(pattern.indexOf(value[i])<0){	
						jQuery("#errorList", this.form).append("<li>Please enter patient name/identifier in correct format!</li>");
						return false;							
					}
				}	
				return true;
			} else {
				jQuery("#errorList", this.form).append("<li>Please enter at least 3 letters of patient name/identifier</li>");
				return false;
			}			
		}
	}
</script>
<script type="text/javascript">
	jQuery(document).ready(
			function() {
jQuery("#nameOrgivenNameOrmiddleNameOrfamilyNameOrIdentifier").val(MODEL.relativeName);
});
</script>
<form id="patientSearchForm">
	<div id="errorSection">
		
	</div>
	<table>
		<tr>			
			<td><input id="nameOrgivenNameOrmiddleNameOrfamilyNameOrIdentifier" name="nameOrgivenNameOrmiddleNameOrfamilyNameOrIdentifier" style='width: 152px;' onblur="setRelativeName();"/></td>
			<td><input type="button" value="Advance search"
			onclick="PATIENTSEARCH.toggleAdvanceSearch();" />
		</tr>	
	</table>
	<div id="advanceSearch">
		<table cellspacing="10">
			<tr>
				<td>Gender</td>
				<td colspan="3">
					<select id="gender" style="width: 100px">
						<option value="Any">Any</option>
						<option value="M">Male</option>
						<option value="F">Female</option>
						<option value="O">Others</option>
					</select>
				</td>
			</tr>
			<tr>
				<td>Green Book No</td>
				<td colspan="3">
					<input id="greenBookNo" style="width: 100px"/>
				</td>	
			</tr>
		</table>
	</div>	
</form>