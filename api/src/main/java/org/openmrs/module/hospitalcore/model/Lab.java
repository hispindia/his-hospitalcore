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


package org.openmrs.module.hospitalcore.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import org.openmrs.Concept;
import org.openmrs.EncounterType;
import org.openmrs.OrderType;
import org.openmrs.PatientIdentifierType;
import org.openmrs.Role;

public class Lab implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 716116064672914345L;
	private Integer labId;
	private String name;
	private String description;
	private OrderType labOrderType;
	private EncounterType labTestEncounterType;
	private PatientIdentifierType patientIdentifierType;
	private Set<Concept> investigationsToDisplay;
	private Set<Concept> confidentialTestsToDisplay;
	private Integer rescheduleperiod= 7;
	private Boolean retired = false;
	private Role role;
	private Date retiredDate ;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public OrderType getLabOrderType() {
		return labOrderType;
	}
	public void setLabOrderType(OrderType labOrderType) {
		this.labOrderType = labOrderType;
	}
	public EncounterType getLabTestEncounterType() {
		return labTestEncounterType;
	}
	public void setLabTestEncounterType(EncounterType labTestEncounterType) {
		this.labTestEncounterType = labTestEncounterType;
	}
	public PatientIdentifierType getPatientIdentifierType() {
		return patientIdentifierType;
	}
	public void setPatientIdentifierType(PatientIdentifierType patientIdentifierType) {
		this.patientIdentifierType = patientIdentifierType;
	}
	public Set<Concept> getInvestigationsToDisplay() {
		return investigationsToDisplay;
	}
	public void setInvestigationsToDisplay(Set<Concept> investigationsToDisplay) {
		this.investigationsToDisplay = investigationsToDisplay;
	}
	public Set<Concept> getConfidentialTestsToDisplay() {
		return confidentialTestsToDisplay;
	}
	public void setConfidentialTestsToDisplay(
			Set<Concept> confidentialTestsToDisplay) {
		this.confidentialTestsToDisplay = confidentialTestsToDisplay;
	}
	public Integer getRescheduleperiod() {
		return rescheduleperiod;
	}
	public void setRescheduleperiod(Integer rescheduleperiod) {
		this.rescheduleperiod = rescheduleperiod;
	}
	public Integer getLabId() {
		return labId;
	}
	public void setLabId(Integer labId) {
		this.labId = labId;
	}
	public Boolean getRetired() {
		return retired;
	}
	public void setRetired(Boolean retired) {
		this.retired = retired;
	}
	public Date getRetiredDate() {
		return retiredDate;
	}
	public void setRetiredDate(Date retiredDate) {
		this.retiredDate = retiredDate;
	}
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	
}
