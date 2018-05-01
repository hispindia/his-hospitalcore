(function($){
	$.fn.extend({
		
		fillForm: function(data, options) {
 
			var defaults = {
				datatype: "text",
				parameterDelimiter: "||",
				nameValueDelimiter: "=="
			};
			 
			var options = jQuery.extend(defaults, options);
		 
			return this.each(function() {									
				var obj = jQuery(this); 							
				if(options.datatype == "json"){
					FORMFILLING.fillJSON(data, obj, options);
				} else if(options.datatype == "text"){					
					FORMFILLING.fillPlainText(data, obj, options);
				}
			});
		}
	});
})(jQuery);

FORMFILLING = {
	/**
	 * FILL FORM USING JSON DATA
	 * Each key is for an seperate input. If you have multiple values for the same key, please use fillPlainText() instead.
	 */
	fillJSON: function(data, obj, option) {		
		text = this.parseJSONToPlainText(data, option);		
		this.fillPlainText(text, obj, option);
	},
	
	/*
	 * PARSE JSON DATA TO PLAIN TEXT
	 */
	parseJSONToPlainText: function(data, option){
		var result = "";
		for(var key in data){
			var attrName = key;
			var attrValue = data[key];				
			result += attrName + option.nameValueDelimiter + attrValue + option.parameterDelimiter;
		}
		return result;
	},
	
	/**
	 * FILL FORM USING PLAIN TEXT DATA
	 * Fill the form using data in plain text with delimiters (parameterDelimiter: "||", nameValueDelimiter: "==")
	 * Example: patientCat==General||patientCat==MLC||patientCat==DDU Staff||patientCat==Government Employee
	 */
	fillPlainText: function(text, obj, option) {		
		text =  jQuery.trim(text);
		parameters = text.split(option.parameterDelimiter);
		
		for(i=0; i<parameters.length; i++){			
			parameter = parameters[i];
			if(parameter.length>1){
				// get paramter name and parameter value
				parts = parameter.split(option.nameValueDelimiter);			
				var parameterName = jQuery.trim(parts[0]);
				var parameterValue = jQuery.trim(parts[1]);	
				
				// fill the form
				jQuery("input:text[name='" + parameterName + "']", obj).val(parameterValue);	
				jQuery("select[name=" + parameterName + "]", obj).val(parameterValue);
				jQuery("input:checkbox[name='" + parameterName + "']", obj).filter(function(index){
					return jQuery(this).val() == parameterValue;
				}).attr("checked", "checked");				
				jQuery("input:radio[name='" + parameterName + "']", obj).filter(function(index){
					return jQuery(this).val() == parameterValue;
				}).attr("checked", "checked");	
			}						
		}
	},
}