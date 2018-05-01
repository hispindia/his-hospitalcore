package org.openmrs.module.hospitalcore.util;

import org.openmrs.Concept;
import org.openmrs.Obs;

public class ObsUtils {

	public static String getValueAsString(Obs obs){
		
		if(obs.getConcept()!=null){
			Concept concept = obs.getConcept();
			if(concept.getDatatype().getName().equalsIgnoreCase("Text")){
				return obs.getValueText();
			} else if(concept.getDatatype().getName().equalsIgnoreCase("Numeric")){
				return obs.getValueNumeric().toString();
			} else if(concept.getDatatype().getName().equalsIgnoreCase("Coded")){
				return obs.getValueCoded().getConceptId().toString();
			}			
		}
		return null;
	}
}
