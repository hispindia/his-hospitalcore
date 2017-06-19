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
 * July 10th 2012: Kesavulu add lastDayOfVisist
 * Billing, feature: Search a patient on the basis of last day of visit
 * UC-23- Advance search of patient
 * New Requirement  #317
 **/
jQuery(document).ready(function() {

	/* Format for Date picker plugin*/
	jQuery('#advanceSearchCalendar').datepicker({
		yearRange : 'c-100:c+100',
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		changeYear : true
	});

	/* When click advanceSearchCalendarButton*/
	jQuery("#advanceSearchCalendarButton").click(function() {
		jQuery("#advanceSearchCalendar").datepicker("show");
	});

	/* display the change of advanceSearchCalendar (hidden input) to lastDayOfVisit (visible input)*/
	jQuery("#advanceSearchCalendar").change(function() {
		jQuery("#lastDayOfVisit").val(jQuery(this).val());
	});

});
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
			jQuery("#ageRange", this.form).val(5);
			jQuery("#nameOrgivenNameOrmiddleNameOrfamilyNameOrIdentifier", this.form).keyup(function(event){				
				if(event.keyCode == 13){	
					nameInCapital = StringUtils.capitalize(jQuery("#nameOrgivenNameOrmiddleNameOrfamilyNameOrIdentifier", PATIENTSEARCH.form).val());
					jQuery("#nameOrgivennameOrmiddleNameOrfamilyNameOrIdentifier", PATIENTSEARCH.form).val(nameInCapital);
					PATIENTSEARCH.search(true);
				}
			});
			jQuery("#advanceSearchCalendar", this.form).change(function() {
				PATIENTSEARCH.search(true);
			});
			jQuery("#lastDayOfVisit", this.form).change(function() {
				PATIENTSEARCH.search(true);
			});
			jQuery("#lastVisit", this.form).change(function(){
				PATIENTSEARCH.search(true);
			});
			jQuery("#relativeName", this.form).blur(function(){
				PATIENTSEARCH.search(true);
			});
			jQuery("#age", this.form).blur(function(){
				PATIENTSEARCH.search(true);
			});
			jQuery("#gender", this.form).change(function(){
				PATIENTSEARCH.search(true);
			});
			jQuery("#ageRange", this.form).blur(function(){
				PATIENTSEARCH.search(true);
			});
			jQuery("#phoneNumber", this.form).blur(function(){
				PATIENTSEARCH.search(true);
			});	
			//ghanshyam 12-sept-2013 New Requirement #2684 Introducing a field at the time of registration to put Aadhar Card Number
			jQuery("#acNo", this.form).keyup(function(event) {
				if (event.keyCode == 13) {
					PATIENTSEARCH.search(true);
					}
			});							
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
					jQuery("#searchLoader", PATIENTSEARCH.form).html("<img src='" + openmrsContextPath + "/moduleResources/hospitalcore/ajax-loader.gif" + "'/>&nbsp;");				
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
							jQuery("#searchLoader", PATIENTSEARCH.form).html("");
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
		
		/** SEARCH BY BILL ID
		* June 6th 2012: Thai Chuong - Supported #247
		*/
		searchBillId: function(newQuery){
				if(newQuery == true){
					this.currentRow = 0;		
					// callback
					PATIENTSEARCH.beforeNewSearch();
					jQuery("#searchLoaderBillId", "#searchboxBillId").html("<img src='" + openmrsContextPath + "/moduleResources/hospitalcore/ajax-loader.gif" + "'/>&nbsp;");				
				}
				
				var query = this.buildBillIdQueryFinal();				
				
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
							PATIENTSEARCH.getPatientResultBillIdCount();
							jQuery("#searchLoaderBillId", "#searchboxBillId").html("");
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
		
		/** GET PATIENT RESULT COUNT BY BILL ID
		* June 6th 2012: Thai Chuong - Supported #247
		*/
		getPatientResultBillIdCount: function(){
			
			var query = this.buildCountBillIdQuery();
			
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
			nameOrIdentifier = nameOrIdentifier.replace(/\s/g, "");			
		
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
			this.whereClause+= "AND ps.dead=0 ";
			this.orderClause = " ORDER BY pt.patient_id ASC";
			this.limitClause = " LIMIT " + this.currentRow + ", " + this.rowPerPage;
			*/	
			
			//ghanshyam,22-oct-2013,New Requirement #2940 Dealing with dead patient
			this.selectClause = "SELECT ps.patient_id, ps.identifier, ps.given_name, ps.middle_name, ps.family_name, ps.gender, ps.birthdate, ps.age, ps.person_name_id, ps.dead ";
			this.fromClause   = " FROM patient_search ps";
			this.fromClause  += " INNER JOIN person pe ON pe.person_id = ps.patient_id";
			this.whereClause  = " WHERE";
			this.whereClause += " (ps.identifier LIKE '%" + nameOrIdentifier +"%' OR ps.fullname LIKE '" + nameOrIdentifier +  "%' OR ps.given_name LIKE '" + nameOrIdentifier + "%'OR ps.middle_name LIKE '" + nameOrIdentifier + "%'OR ps.family_name LIKE '" + nameOrIdentifier + "%')";				
			 //ghanshyam,22-oct-2013,New Requirement #2940 Dealing with dead patient
			//this.whereClause += " AND pe.dead=0";
			this.groupClause = " GROUP BY ps.patient_id";
			this.orderClause = " ORDER BY ps.patient_id ASC";
			this.limitClause = " LIMIT " + this.currentRow + ", " + this.rowPerPage;					

			//	Build extended queries
			if(this.advanceSearch){
				this.buildGenderQuery();
				this.buildAgeQuery();
				this.buildRelativeNameQuery();
				this.buildLastDayOfVisitQuery();
				this.buildLastVisitQuery();
				this.buildPhoneNumberQuery();
				//ghanshyam 12-sept-2013 New Requirement #2684 Introducing a field at the time of registration to put Aadhar Card Number
				this.buildAadharCardNumberQuery();
			}
			
			// Return the built query
			//ghanshyam 16-march-2013 Support #1110[Registration]ddu server slow(added groupClause)
			this.query = this.selectClause + this.fromClause + this.whereClause + this.groupClause + this.orderClause + this.limitClause;
			//this.query = this.selectClause + this.fromClause + this.whereClause + this.orderClause + this.limitClause;		
			return this.query;
		},
		
		/** BUILD QUERY - SEARCH PATIENT BY BILL
		* June 6th 2012: Thai Chuong - Supported #247
		*/
		buildBillIdQueryFinal: function(){
			// Get value from form				
		
			// Build essential query
			//ghanshyam 16-march-2013 Support #1110[Registration]ddu server slow(commented old query below and written new query after this commented query)
			/*
			this.selectClause = "SELECT DISTINCT pt.patient_id, pi.identifier, pn.given_name, pn.middle_name, pn.family_name, ps.gender, ps.birthdate, EXTRACT(YEAR FROM (FROM_DAYS(DATEDIFF(NOW(),ps.birthdate)))) age, pn.person_name_id";
			this.fromClause   = " FROM `patient` pt";
			this.fromClause  += " INNER JOIN person ps ON ps.person_id = pt.patient_id";
			this.fromClause  += " INNER JOIN person_name pn ON pn.person_id = ps.person_id";
			this.fromClause  += " INNER JOIN patient_identifier pi ON pi.patient_id = pt.patient_id";
			this.whereClause  = " WHERE";
			this.orderClause = " ORDER BY pt.patient_id ASC";
			this.limitClause = " LIMIT " + this.currentRow + ", " + this.rowPerPage;	
			*/	
			
			//ghanshyam,22-oct-2013,New Requirement #2940 Dealing with dead patient
			this.selectClause = "SELECT ps.patient_id, ps.identifier, ps.given_name, ps.middle_name, ps.family_name, ps.gender, ps.birthdate, ps.age, ps.person_name_id, ps.dead ";
			this.fromClause   = " FROM patient_search ps";
			this.fromClause  += " INNER JOIN person pe ON pe.person_id = ps.patient_id";
			this.whereClause  = " WHERE";		
			this.groupClause = " GROUP BY ps.patient_id";
			this.orderClause = " ORDER BY ps.patient_id ASC";
			this.limitClause = " LIMIT " + this.currentRow + ", " + this.rowPerPage;		

			
			//	Build extended queries
			this.buildBillIdQuery();
			
			
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
			nameOrIdentifier = nameOrIdentifier.replace(/\s/g, "");
		
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
			this.whereClause+= "AND ps.dead=0 ";
			*/
			
			this.selectClause = "SELECT COUNT(DISTINCT ps.patient_id)";
			this.fromClause   = " FROM patient_search ps";
			this.fromClause  += " INNER JOIN person pe ON pe.person_id = ps.patient_id";
			this.whereClause  = " WHERE";
			this.whereClause += " (ps.identifier LIKE '%" + nameOrIdentifier +"%' OR ps.fullname LIKE '" + nameOrIdentifier +  "%' OR ps.given_name LIKE '" + nameOrIdentifier + "%'OR ps.middle_name LIKE '" + nameOrIdentifier + "%'OR ps.family_name LIKE '" + nameOrIdentifier + "%')";	
			//ghanshyam,22-oct-2013,New Requirement #2940 Dealing with dead patient
			//this.whereClause += " AND pe.dead=0";
			
			//	Build extended queries
			if(this.advanceSearch){
				this.buildGenderQuery();
				this.buildAgeQuery();
				this.buildRelativeNameQuery();
				this.buildLastDayOfVisitQuery();
				this.buildLastVisitQuery();
				this.buildPhoneNumberQuery();
				//ghanshyam 12-sept-2013 New Requirement #2684 Introducing a field at the time of registration to put Aadhar Card Number
				this.buildAadharCardNumberQuery();
			}
			
			// Return the built query
			this.query = this.selectClause + this.fromClause + this.whereClause;		
			return this.query;
		},
		
		/** BUILD COUNT PATIENT SEARCH BY BILL ID QUERY
		* June 6th 2012: Thai Chuong - Supported #247
		*/
		buildCountBillIdQuery: function(){
		
			// Get value from form			
		
			// Build essential query
			//ghanshyam 16-march-2013 Support #1110[Registration]ddu server slow(commented old query below and written new query after this commented query)
			/*
			this.selectClause = "SELECT COUNT(DISTINCT pt.patient_id)";
			this.fromClause   = " FROM `patient` pt";
			this.fromClause  += " INNER JOIN person ps ON ps.person_id = pt.patient_id";
			this.fromClause  += " INNER JOIN person_name pn ON pn.person_id = ps.person_id";
			this.fromClause  += " INNER JOIN patient_identifier pi ON pi.patient_id = pt.patient_id";
			this.whereClause  = " WHERE";
			*/
			
			this.selectClause = "SELECT COUNT(DISTINCT ps.patient_id)";
			this.fromClause   = " FROM patient_search ps";
			this.fromClause  += " INNER JOIN person pe ON pe.person_id = ps.patient_id";
			this.whereClause  = " WHERE";

			//	Build extended queries
			this.buildBillIdQuery();
			
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
		
		/** BUILD QUERY FOR AGE */
		buildAgeQuery: function(){
			value =jQuery.trim(jQuery("#age", this.form).val());
			if(value!=undefined && value.length>0){
				
				if(StringUtils.isDigit(StringUtils.right(value, 1))){
					value += "y";					
				}
				type = StringUtils.right(value, 1);
				value = parseInt(value.substring(0, value.length-1));
				range = parseInt(jQuery.trim(jQuery("#ageRange", this.form).val()));
				
				if(type=="y"){									
					
					jQuery("#rangeUnit").html("Year(s)");
					this.whereClause += "AND (EXTRACT(YEAR FROM (FROM_DAYS(DATEDIFF(NOW(),ps.birthdate)))) >=" + (value - range) + " AND EXTRACT(YEAR FROM (FROM_DAYS(DATEDIFF(NOW(),ps.birthdate)))) <= " + (value + range) + ") ";
				} else {
					days = value + range;
					if(type=="m"){
						days = days * 30;
						jQuery("#rangeUnit").html("Month(s)");
					} else if(type=="w") {
						jQuery("#rangeUnit").html("Week(s)");
						days = days * 7;
					} else {
						jQuery("#rangeUnit").html("Day(s)");
					}
					this.whereClause += "AND (DATEDIFF(NOW(),ps.birthdate) <= " + (days) + ") ";
				}
			}
		},
		
		/** BUILD QUERY FOR RELATIVE NAME */
		buildRelativeNameQuery: function(){
			value = jQuery.trim(jQuery("#relativeName", this.form).val());
			personAttributeTypeName = "Father/Husband Name";
			if(value!=undefined && value.length>0){
			     //ghanshyam 16-march-2013 Support #1110[Registration]ddu server slow
			    this.fromClause += " INNER JOIN person_attribute paRelativeName ON ps.patient_id= paRelativeName.person_id";
				//this.fromClause += " INNER JOIN person_attribute paRelativeName ON ps.person_id= paRelativeName.person_id";
				this.fromClause += " INNER JOIN person_attribute_type patRelativeName ON paRelativeName.person_attribute_type_id = patRelativeName.person_attribute_type_id ";
				this.whereClause += " AND (patRelativeName.name LIKE '%" + personAttributeTypeName + "%' AND paRelativeName.value LIKE '%" + value + "%')";
			}
		},
		
		/** BUILD QUERY FOR PHONE NUMBER */
		buildPhoneNumberQuery: function(){
			value = jQuery.trim(jQuery("#phoneNumber", this.form).val());
			phoneNumberAttributeTypeName = "Phone Number";
			if(value!=undefined && value.length>0){
			    //ghanshyam 16-march-2013 Support #1110[Registration]ddu server slow
			    this.fromClause += " INNER JOIN person_attribute paPhoneNumber ON ps.patient_id= paPhoneNumber.person_id";
				//this.fromClause += " INNER JOIN person_attribute paPhoneNumber ON ps.person_id= paPhoneNumber.person_id";
				this.fromClause += " INNER JOIN person_attribute_type patPhoneNumber ON paPhoneNumber.person_attribute_type_id = patPhoneNumber.person_attribute_type_id ";
				this.whereClause += " AND (patPhoneNumber.name LIKE '%" + phoneNumberAttributeTypeName + "%' AND paPhoneNumber.value LIKE '%" + value + "%')";
			}
		},
		/**
		 * July 10th 2012: Kesavulu add lastDayOfVisist
		 * Billing, feature: Search a patient on the basis of last day of visit
		 * UC-23- Advance search of patient
		 * New Requirement  #317
		 **/
	/** BUILD QUERY FOR LAST DAY OF VISIT */
	buildLastDayOfVisitQuery : function() {
		value = jQuery.trim(jQuery("#lastDayOfVisit", this.form).val());
		if (value != undefined && value.length > 0) {
		    //ghanshyam 16-march-2013 Support #1110[Registration]ddu server slow
		    this.fromClause += " INNER JOIN encounter en ON ps.patient_id = en.patient_id";
			//this.fromClause += " INNER JOIN encounter en ON pt.patient_id = en.patient_id";
			this.whereClause += " AND (DATE_FORMAT(DATE(en.encounter_datetime),'%d/%m/%Y') = '"
					+ value + "')";
		}
	},
		/** BUILD QUERY FOR LAST VISIT */
		buildLastVisitQuery: function(){
			value = jQuery.trim(jQuery("#lastVisit", this.form).val());
			if(value!='Any'){
			    //ghanshyam 16-march-2013 Support #1110[Registration]ddu server slow
			    this.fromClause += " INNER JOIN encounter e ON e.patient_id = ps.patient_id";
				//this.fromClause += " INNER JOIN encounter e ON e.patient_id = pt.patient_id";
				this.whereClause += " AND (DATEDIFF(NOW(), e.date_created) <= " + value + ")";
			}
		},
		
		/** BUILD QUERY BILL ID
		* June 6th 2012: Thai Chuong - Supported #247
		*/
		buildBillIdQuery: function(){
			value = jQuery.trim(jQuery("#billId", "#searchboxBillId").val());
			if(value!=undefined && value.length>0){
			    //ghanshyam 16-march-2013 Support #1110[Registration]ddu server slow
				this.fromClause += " INNER JOIN billing_patient_service_bill bill ON bill.patient_id = ps.patient_id";
				//this.fromClause += " INNER JOIN billing_patient_service_bill bill ON bill.patient_id = pt.patient_id";
				this.whereClause += " bill.receipt_id = '" + value + "'";
			}else{
				// look for all patient that have bill
				//ghanshyam 16-march-2013 Support #1110[Registration]ddu server slow(removed DISTINCT keyword and added groupClause)
				this.whereClause = " WHERE ps.patient_id IN (SELECT bill.patient_id FROM billing_patient_service_bill bill WHERE bill.patient_id = ps.patient_id GROUP BY ps.patient_id)";
				//this.whereClause = " WHERE pt.patient_id IN (SELECT DISTINCT bill.patient_id FROM billing_patient_service_bill bill WHERE bill.patient_id = pt.patient_id)";
			}
		},
		//ghanshyam 12-sept-2013 New Requirement #2684 Introducing a field at the time of registration to put Aadhar Card Number
		buildAadharCardNumberQuery: function(){
		    value = jQuery.trim(jQuery("#acNo", this.form).val());
			aadharCardNumberAttributeTypeName = "Aadhar Card Number";
			if(value!=undefined && value.length>0){
			    this.fromClause += " INNER JOIN person_attribute paAadharCardNumber ON ps.patient_id= paAadharCardNumber.person_id";
				this.fromClause += " INNER JOIN person_attribute_type patAadharCardNumber ON paAadharCardNumber.person_attribute_type_id = patAadharCardNumber.person_attribute_type_id ";
				this.whereClause += " AND (patAadharCardNumber.name LIKE '%" + aadharCardNumberAttributeTypeName + "%' AND paAadharCardNumber.value LIKE '%" + value + "%')";
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
			result = result && this.validateAge();
			result = result && this.validatePhoneNumber();
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
		},
		
		/** VALIDATE AGE */
		validateAge: function(){
			if(this.advanceSearch){				
				value = jQuery("#age", this.form).val();
				pattern = "0123456789ymwd";
				for(i=0; i<value.length; i++){
					if(pattern.indexOf(value[i])<0){	
						jQuery("#errorList", this.form).append("<li>Please enter patient age in digits!</li>");
						return false;							
					}
				}	
				return true;
			} else {
				return true;
			}
		},
		
		/** VALIDATE PHONE NUMBER */
		validatePhoneNumber: function(){
			if(this.advanceSearch){				
				value = jQuery("#phoneNumber", this.form).val();
				pattern = "0123456789+ ";
				for(i=0; i<value.length; i++){
					if(pattern.indexOf(value[i])<0){	
						jQuery("#errorList", this.form).append("<li>Please enter phone number in digits!</li>");
						return false;							
					}
				}	
				return true;
			} else {
				return true;
			}
		}
	}
</script>
<form id="patientSearchForm">
	<div id="errorSection">
		
	</div>
	<table cellspacing="10">
		<tr>	
			<td>Name/Identifier</td>
			<td><input id="nameOrgivenNameOrmiddleNameOrfamilyNameOrIdentifier" style="width:300px;"/></td>
			<td><a href="javascript:PATIENTSEARCH.toggleAdvanceSearch();">Advance search</a></td>
			<td id="searchLoader"></td>
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
						<%-- ghanshyam 15-april-2013 New Requirement #1379 [Registration]Gender under Advance Search --%>
						<option value="O">Others</option>
					</select>
				</td>
			</tr>
			<tr>
				<td>Age</td>
				<td>
					<input id="age" style="width: 100px"/>
				</td>				
				<td>Range &plusmn;</td>
				<td>
					<select id="ageRange" style="width: 100px">
						<option value="0">Exact</option>
						<option value="1">1</option>
						<option value="2">2</option>
						<option value="3">3</option>
						<option value="4">4</option>
						<option value="5">5</option>
					</select>
					<span id="rangeUnit"></span>
				</td>
			</tr>
			<tr>
				<td>Last day of visit</td>
				<td><input id="advanceSearchCalendar" type="hidden" /> <input
					id="lastDayOfVisit" name="lastDayOfVisit" style="width: 100px" />
					<img id="advanceSearchCalendarButton"
					src="${pageContext.request.contextPath}/moduleResources/hospitalcore/calendar.gif" />
				</td>
			</tr>
			<tr>
				<td>Last Visit</td>
				<td colspan="3">
					<select id="lastVisit" style="width: 100px">
						<option value="Any">Anytime</option>
						<option value="31">Last month</option>
						<option value="183">Last 6 months</option>
						<option value="366">Last year</option>
					</select>
				</td>	
			</tr>
			<tr>
				<td>Phone number</td>
				<td colspan="3">
					<input id="phoneNumber" style="width: 100px"/>
				</td>	
			</tr>
			<tr>
				<td>Relative Name</td>
				<td colspan="3">
					<input id="relativeName" style="width: 100px"/>
				</td>	
			</tr>
			<!-- ghanshyam 12-sept-2013 New Requirement #2684 Introducing a field at the time of registration to put Aadhar Card Number -->
			<tr>
				<td>Aadhar Card No</td>
				<td colspan="3">
					<input id="acNo" style="width: 100px"/>
				</td>	
			</tr>
		</table>
	</div>	
</form>