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


package org.openmrs.module.hospitalcore.concept;

import java.util.HashSet;
import java.util.Set;

public class ConceptModel implements Comparable<ConceptModel> {
	private static final String CONCEPT_CLASS = "Diagnosis";
	private static final String DATA_TYPE = "N/A";
	private String name;
	private String conceptClass = CONCEPT_CLASS;
	private String conceptDatatype = DATA_TYPE;
	private String description;
	private String shortname;
	private Set<String> synonyms = new HashSet<String>();
	private Set<Mapping> mappings = new HashSet<Mapping>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getConceptClass() {
		return conceptClass;
	}

	public void setConceptClass(String conceptClass) {
		this.conceptClass = conceptClass;
	}

	public String getConceptDatatype() {
		return conceptDatatype;
	}

	public void setConceptDatatype(String conceptDatatype) {
		this.conceptDatatype = conceptDatatype;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getShortname() {
		return shortname;
	}

	public void setShortname(String shortname) {
		this.shortname = shortname;
	}

	public Set<String> getSynonyms() {
		return synonyms;
	}

	public void setSynonyms(Set<String> synonyms) {
		this.synonyms = synonyms;
	}

	public Set<Mapping> getMappings() {
		return mappings;
	}

	public void setMappings(Set<Mapping> mappings) {
		this.mappings = mappings;
	}
	
	public int compareTo(ConceptModel o) {
		try {			
			return this.getName().compareToIgnoreCase(o.getName());
		} catch(NullPointerException e){
			System.out.println("NULL CONCEPTMODEL");
			System.out.println(this.getName());
			System.out.println(o.getName());
			return -1;
		}
		
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ConceptModel other = (ConceptModel) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
}
