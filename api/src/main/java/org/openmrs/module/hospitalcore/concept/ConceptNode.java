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

import java.util.Set;
import java.util.TreeSet;

import org.openmrs.Concept;

public class ConceptNode implements Comparable<ConceptNode> {

	private Concept concept;
	private Set<ConceptNode> childNodes = new TreeSet<ConceptNode>();
	private ConceptNode parent;

	public ConceptNode() {

	}

	public ConceptNode(Concept concept) {
		this.concept = concept;
	}

	public ConceptNode(Concept concept, ConceptNode parent) {
		this.concept = concept;
		this.parent = parent;
	}

	public int compareTo(ConceptNode o) {
		String mName = concept.getName().getName();
		String oName = o.getConcept().getName().getName();
		return mName.compareToIgnoreCase(oName);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((concept == null) ? 0 : concept.hashCode());
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
		ConceptNode other = (ConceptNode) obj;
		if (concept == null) {
			if (other.concept != null)
				return false;
		} else if (!concept.equals(other.concept))
			return false;
		return true;
	}

	public Concept getConcept() {
		return concept;
	}

	public void setConcept(Concept concept) {
		this.concept = concept;
	}

	public Set<ConceptNode> getChildNodes() {
		return childNodes;
	}

	public void setChildNodes(Set<ConceptNode> childNodes) {
		this.childNodes = childNodes;
	}

	public ConceptNode getParent() {
		return parent;
	}

	public void setParent(ConceptNode parent) {
		this.parent = parent;
	}

	@Override
	public String toString() {
		return "ConceptNode [conceptId=" + concept.getConceptId() + "]";
	}
	
	
}
