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
 
StringUtils = {

	/** Check Whether A Text Is Blank (null, only space...) Or Note */
	isBlank: function(text){
		if(text == undefined)
			return true;
		if(StringUtils.trim(text).length == 0)
			return true;
		return false;
	},
	
	/** Trim A Text */
	trim: function(text){
		text = StringUtils.toString(text);
		return text.replace(/^\s+|\s+$/g,"");		
	},
	
	/** Convert An Object To String */
	toString: function(obj){
		return "" + obj;
	},
	
	/** Capitalize A String */
	capitalize: function(text){
		text = StringUtils.toString(text);
		return text.replace(/^.|\s\S/g, function(letter){
			return letter.toUpperCase(); 
		});
	},
	
	/** Get substring from right */
	right: function(text, len){		
		text = StringUtils.toString(text);		
		if(!StringUtils.isBlank(text)){			
			return text.substring(text.length - len, text.length);
		}
		return null;
	},
	
	isDigit: function(text){
		text = StringUtils.toString(text);
		pattern = "0123456789";
		if(!StringUtils.isBlank(text)){
			for(i=0; i<text.length; i++){
				if(pattern.indexOf(text[i])<0)
					return false;
			}
		}
		return true;
	}
};