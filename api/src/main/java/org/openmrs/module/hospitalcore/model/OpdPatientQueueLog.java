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

import org.openmrs.Concept;
import org.openmrs.Encounter;
import org.openmrs.Patient;
import org.openmrs.User;

public class OpdPatientQueueLog implements  Serializable {

	
	private static final long serialVersionUID = 1L;
	 private Integer id;
	 private Patient patient;
	 private String patientName;
	 private String patientIdentifier;
	 private Date birthDate;
	 private String sex;
	 //referal type
	 private String  referralConceptName;
	 private Concept referralConcept;
	 //Location
	 private Concept opdConcept;
	 private String opdConceptName;
	 private String status;
	 private User user;
	 private Date createdOn;
	 private Encounter encounter;
	 
	 
	@Override
	public String toString() {
		return "OpdPatientQueueLog [id=" + id + ", patient=" + patient
				+ ", patientName=" + patientName + ", birthDate=" + birthDate + ", sex="
				+ sex + ", referralConceptName=" + referralConceptName
				+ ", referralConcept=" + referralConcept + ", opdConcept="
				+ opdConcept + ", opdConceptName=" + opdConceptName
				+ ", status=" + status + ", user=" + user + ", createdOn="
				+ createdOn + "]";
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Patient getPatient() {
		return patient;
	}
	public void setPatient(Patient patient) {
		this.patient = patient;
	}
	public String getPatientName() {
		return patientName;
	}
	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getReferralConceptName() {
		return referralConceptName;
	}
	public void setReferralConceptName(String referralConceptName) {
		this.referralConceptName = referralConceptName;
	}
	public Concept getReferralConcept() {
		return referralConcept;
	}
	public void setReferralConcept(Concept referralConcept) {
		this.referralConcept = referralConcept;
	}
	public Concept getOpdConcept() {
		return opdConcept;
	}
	public void setOpdConcept(Concept opdConcept) {
		this.opdConcept = opdConcept;
	}
	public String getOpdConceptName() {
		return opdConceptName;
	}
	public void setOpdConceptName(String opdConceptName) {
		this.opdConceptName = opdConceptName;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Date getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}
	public String getPatientIdentifier() {
		return patientIdentifier;
	}
	public void setPatientIdentifier(String patientIdentifier) {
		this.patientIdentifier = patientIdentifier;
	}
	public Date getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}
	public Encounter getEncounter() {
		return encounter;
	}
	public void setEncounter(Encounter encounter) {
		this.encounter = encounter;
	}
	 
	 
	 
	  
}
