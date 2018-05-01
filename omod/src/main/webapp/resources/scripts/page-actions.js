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

var EVT =
{
	ready : function()
	{
		/**
		 * Page Actions
		 */
		var enableCheck = true;
		var pageId = jQuery("#pageId").val();
		if(enableCheck && pageId != undefined && eval("CHECK." + pageId))
		{
			eval("CHECK." + pageId + "()");
		}
		jQuery('.date-pick').datepicker({minDate: '-100y', dateFormat: 'dd/mm/yy'});

		/**
		 * Ajax Indicator when send and receive data
		 */
		if(jQuery.browser.msie)
		{
			jQuery.ajaxSetup({cache: false});
		}
	
	}
};

var CHECK = 
{
	pageDepartment : function()
	{
		var validator = jQuery("#formDepartment").validate(
		{
			event : "blur",
			rules : 
			{
			
				"name" : { required : true},
				"wards" : { required : true}
			}
		});
	},
	pageDepartmentConcept : function()
	{
		
		jQuery("#diagnosis").autocomplete('autoCompleteDiagnosis.htm', {
			delay:1000,
			scroll: true,
			 parse: function(xml){
	                var results = [];
	                $(xml).find('item').each(function() {
	                    var text = $.trim($(this).find('text').text());
	                    var value = $.trim($(this).find('value').text());
	                    results[results.length] = { 'data': { text: text, value: value },
	                        'result': text, 'value': value
	                    };
	                });
	                return results;

			 },
			formatItem: function(data) {
				  return data.text;
			},
			formatResult: function(data) {
			      return data.text;
			}
			  
			}).result(function(event, item) {
				HOSPITALCORE.onChangeDiagnosis('diagnosis',item.value);
			});
		
		
		jQuery("#procedure").autocomplete('autoCompleteProcedure.htm', {
			delay:1000,
			scroll: true,
			 parse: function(xml){
	                var results = [];
	                $(xml).find('item').each(function() {
	                    var text = $.trim($(this).find('text').text());
	                    var value = $.trim($(this).find('value').text());
	                    results[results.length] = { 'data': { text: text, value: value },
	                        'result': text, 'value': value
	                    };
	                });
	                return results;

			 },
			formatItem: function(data) {
				  return data.text;
			},
			formatResult: function(data) {
			      return data.text;
			}
			  
			}).result(function(event, item) {
				HOSPITALCORE.onChangeDiagnosis('procedure',item.value);
			});

		var validator = jQuery("#formDepartmentConcept").validate(
		{
			event : "blur",
			rules : 
			{
				"selectedDiagnosisList" : { required : true},
				"selectedProcedureList" : { required : true},
				"departmentId" : { required : true}
			}
		});
	}
	
};

/**
 * Pageload actions trigger
 */

jQuery(document).ready(
	function() 
	{
		EVT.ready();
	}
);



