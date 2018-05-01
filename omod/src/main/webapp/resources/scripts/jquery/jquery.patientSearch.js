(function($){
		$.fn.extend({
			
			showPatientSearchBox: function(options) {
	 
				var defaults = {                
					searchBoxView: "default",
					resultView: "/module/registration/patient/billing",
					target: "#patientSearchResult",
					rowPerPage: 10,
					success: function(data){},
					beforeNewSearch: function(){}
				};
				 
				var options = jQuery.extend(defaults, options);
			 
				return this.each(function() {			
					obj = jQuery(this);
					
					jQuery.ajax({
						type : "GET",
						url : openmrsContextPath + "/module/hospitalcore/searchPatient.form",
						data : ({
							searchBoxView: options.searchBoxView
						}),
						success : function(data) {							
							obj.html(data);
							PATIENTSEARCH.init({
								resultView: options.resultView,
								rowPerPage: options.rowPerPage,
								target    : options.target,
								success   : options.success,
								beforeNewSearch    : options.beforeNewSearch
							});
						},
						error : function(xhr, ajaxOptions, thrownError) {
							alert(thrownError); 
						}
					});
				});
			}
		});
	})(jQuery);