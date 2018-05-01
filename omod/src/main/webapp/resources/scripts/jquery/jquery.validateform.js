/** ADD/OVERWRITE A VALIDATOR */
(function($){
	$.addValidator = function(newValidator) {
	
		overwrite = false;
		jQuery.each(VALIDATEFORM.validators, function(index, validator){
			
			// overwrite the existing
			if(newValidator.name == validator.name){
				overwrite = true;
				if(newValidator.message!=undefined)
					validator.message = newValidator.message;
				if(newValidator.method!=undefined)
					validator.method = newValidator.method;
				return false;
			}
		});
			
		// add new one
		if(!overwrite){
			VALIDATEFORM.validators.push(newValidator);
		}
	}
})(jQuery);

/** VALIDATE */
(function($){
	$.fn.extend({
		
		validateForm: function(options) {
		
			var defaults = {
				showError: null
			};
			 
			var options = jQuery.extend(defaults, options);
			
			var obj = jQuery(this);						
			if(options.showError!=undefined) {
				if(options.showError!=null) {
					VALIDATEFORM.showError = options.showError;
				}
			}
			return VALIDATEFORM.validate(obj);	
		}
	});
})(jQuery);

VALIDATEFORM = {

	/** Default validators */
	validators: [
		{
			name: "required",
			check: function(value, type, name){				
				
				if(type=='radio') {					
					
					if(jQuery("input[name=" + name + "]:checked").length>0)
						return true;
						
				} else {
				
					if(value!=undefined)
						if(value.length>0) 
							return true;
				}
				return false;
			},
			message: "This field must be filled!"
		},
		{
			name: "digit",
			check: function(value, type, name){
			
				if(value!=undefined){
					if(value.length>0){
						pattern = "0123456789";
						for(i=0; i<value.length; i++){
							if(pattern.indexOf(value[i])<0)
								return false;
						}
						return true;
					}
				}
				return false;
			},
			message: "This field must be digits!"
		},
		{
			name: "date",
			check: function(value, type, name){
				try {
					jQuery.datepicker.parseDate("dd/mm/yy", value, null);
					return true;
				}
				catch (error){
					return false;
				}
			},
			message: "This field must be a valid date!"
		}		
	],
	
	/** SHOW ERROR */
	showError: function(input, message) {			
		
		jQuery(input).after("<span class='validationError' style='color:red;'>" + message + "</span>");
	},
	
	/** VALIDATE THE FORM */
	validate: function(obj) {
	
		// remove all validation errors
		jQuery(".validationError").remove();
		
		// validate
		result = true;
		
		var names = "";
		jQuery("input", obj).each(function(index, value){
			input = jQuery(value);
			clss = input.attr("class");
			data = input.val();
			type = input.attr("type");
			name = input.attr("name");
			
			if(names.indexOf("<" + name + ">")<0){
				jQuery.each(VALIDATEFORM.validators, function(index, validator){
					
					if(clss.indexOf(validator.name)>=0){							
						if(!validator.check(data, type, name)) {
							VALIDATEFORM.showError(input, validator.message);
							result = false;
							return false; // break the loop through validators
						}
					}
				});				
			}		

			names = names + "<" + name + ">";			
		});
		return result;
	}
};





var strDate = "03.09.1979";

