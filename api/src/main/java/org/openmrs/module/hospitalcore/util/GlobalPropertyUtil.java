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


package org.openmrs.module.hospitalcore.util;

import org.apache.commons.lang.StringUtils;
import org.openmrs.GlobalProperty;
import org.openmrs.api.context.Context;

public class GlobalPropertyUtil {
	
	/**
	 * Get boolean value from a specific global property. Unless the global property is found, the defaultValue will be returned. 
	 * @param globalPropertyName
	 * @param defaultValue
	 * @return
	 */
	public static Boolean getBoolean(String globalPropertyName, Boolean defaultValue){
		String value = Context.getAdministrationService().getGlobalProperty(
				globalPropertyName);
		
		Boolean result = defaultValue;
		
		if (!StringUtils.isBlank(value)) {
			result = Boolean.parseBoolean(value);
		}
		return result;
	}
	
	/**
	 * Get String value from a specific global property. Unless the global property is found, the defaultValue will be returned. 
	 * @param globalPropertyName
	 * @param defaultValue
	 * @return
	 */
	public static String getString(String globalPropertyName, String defaultValue){
		String value = Context.getAdministrationService().getGlobalProperty(
				globalPropertyName);
		
		String result = defaultValue;
		
		if (!StringUtils.isBlank(value)) {
			result = value;
		}
		return result;
	}
	
	/**
	 * Get Integer value from a specific global property. Unless the global property is found, the defaultValue will be returned. 
	 * @param globalPropertyName
	 * @param defaultValue
	 * @return
	 */
	public static Integer getInteger(String globalPropertyName, Integer defaultValue){
		String value = Context.getAdministrationService().getGlobalProperty(
				globalPropertyName);
		
		Integer result = defaultValue;
		
		if (!StringUtils.isBlank(value)) {
			try {
				result = Integer.parseInt(value);
			} catch(Exception e){
				e.printStackTrace();
			}
		}
		return result;
	}
	
	/**
	 * Set string value for a specific global property
	 * @param globalPropertyName
	 * @param value
	 */
	public static void setString(String globalPropertyName, String value){		
		GlobalProperty gp = Context.getAdministrationService().getGlobalPropertyObject(globalPropertyName);		
		if(gp!=null){			
			gp.setPropertyValue(value);
			Context.getAdministrationService().saveGlobalProperty(gp);			
		}
	}
	
	/**
	 * Save a new global property unless it exists
	 * 
	 * @param name
	 * @param description
	 * @param value
	 */
	public static void saveGlobalProperty(String name, String description,
			Object value) {
		GlobalProperty gp = Context.getAdministrationService()
				.getGlobalPropertyObject(name);
		if (gp == null) {
			gp = new GlobalProperty();
		}
		gp.setProperty(name);
		gp.setDescription(description);
		gp.setPropertyValue(value.toString());
		Context.getAdministrationService().saveGlobalProperty(gp);
	}
}
