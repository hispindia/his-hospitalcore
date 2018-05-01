(function($){
    $.fn.extend({
        //plugin name - animatemenu
        toggleSearchBox: function(options) {
 
            var defaults = {                
                resultBoxId: "patientResult",
                view: "ddupatientqueue_search_patient_in_queue",
                params: "",
				beforeSearch: function(){}
            };
             
            var options = jQuery.extend(defaults, options);
         
            return this.each(function() {
				var o = options;
				var obj = jQuery(this);       
				searchBoxId = obj.attr('id');
				ADVSEARCH.toggleAdvanceSearchBox(searchBoxId, o.resultBoxId, o.view, o.params, o.beforeSearch);
            });
        }
    });
})(jQuery);

ADVSEARCH = {
	timeoutId : 0,
	showing : false,
	params : "",
	delayDuration : 1000,
	pageSize : 10,
	beforeSearch: function(){},

	// Toggle advance search box
	toggleAdvanceSearchBox : function(searchBoxId, resultBoxId, view, params, beforeSearch) {
		id = "#" + searchBoxId;
		this.params = params;
		this.beforeSearch = beforeSearch;

		if (this.showing) {
			this.showing = false;
			jQuery(id).html("");
		} else {
			this.showing = true;
			jQuery.get(this.getContextPath()
					+ '/module/hospitalcore/patientSearch.form?view=' + view
					+ '&resultBoxId=' + resultBoxId, function(data) {
				jQuery(id).html(data);
			});
		}
	},

	// search patient
	searchPatient : function(currentPage, pageSize) {
		this.beforeSearch();
		form = jQuery("#searchForm");
		phrase = jQuery("#phrase", form).val();
		if (phrase.length >= 3) {
			jQuery("#ajaxLoader", form).show();
			gender = jQuery("#gender", form).val();
			age = jQuery("#age", form).val();
			ageRange = jQuery("#ageRange", form).val();
			date = jQuery("#dateOfVisit", form).val();
			dateRange = jQuery("#dateRange", form).val();
			relativeName = jQuery("#relativeName", form).val();
			view = jQuery("#view", form).val();

			url = this.getContextPath()
					+ "/module/hospitalcore/patientSearch.form";
			if (this.params.length > 0) {
				url = url + "?" + this.params;
			}
			jQuery.ajax({
				type : "POST",
				url : url,
				data : ({
					phrase : phrase,
					gender : gender,
					age : age,
					ageRange : ageRange,
					date : date,
					dateRange : dateRange,
					relativeName : relativeName,
					view : view,
					currentPage : currentPage,
					pageSize : pageSize
				}),
				success : function(data) {
					resultBoxId = "#" + jQuery("#resultBoxId", form).val();
					jQuery(resultBoxId).html(data);
					jQuery("#ajaxLoader", form).hide();
				},
				error : function(xhr, ajaxOptions, thrownError) {
					alert(xhr);
					jQuery("#ajaxLoader", form).hide();
				}
			});
		}
	},

	// start searching patient
	startSearch : function(e) {
		e = e || window.event;
		ch = e.which || e.keyCode;
		if (ch != null) {
			if ((ch >= 48 && ch <= 57) || (ch >= 96 && ch <= 105)
					|| (ch >= 65 && ch <= 90)
					|| (ch == 109 || ch == 189 || ch == 45) || (ch == 8)
					|| (ch == 46)) {				
			} else if (ch == 13) {
				clearTimeout(this.timeoutId);
				this.timeoutId = setTimeout("ADVSEARCH.delay()",
						this.delayDuration);
			}
		}
	},

	// delay before search
	delay : function() {
		this.searchPatient(0, this.pageSize);
	},

	// get context path in order to build controller url
	getContextPath : function() {
		pn = location.pathname;
		len = pn.indexOf("/", 1);
		cp = pn.substring(0, len);
		return cp;
	}
};
