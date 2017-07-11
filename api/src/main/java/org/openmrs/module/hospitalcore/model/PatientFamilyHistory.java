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

public class PatientFamilyHistory  {

	
	public String getSiblingStatus() {
		return siblingStatus;
	}

	public void setSiblingStatus(String siblingStatus) {
		this.siblingStatus = siblingStatus;
	}

	public String getSiblingDeathCause() {
		return siblingDeathCause;
	}

	public void setSiblingDeathCause(String siblingDeathCause) {
		this.siblingDeathCause = siblingDeathCause;
	}

	public String getSiblingDeathAge() {
		return siblingDeathAge;
	}

	public void setSiblingDeathAge(String siblingDeathAge) {
		this.siblingDeathAge = siblingDeathAge;
	}

	public String getFamilyIllnessHistory() {
		return familyIllnessHistory;
	}

	public void setFamilyIllnessHistory(String familyIllnessHistory) {
		this.familyIllnessHistory = familyIllnessHistory;
	}

	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer patientId;
	private String fatherStatus;
	private String fatherDeathCause;
	private String fatherDeathAge;
	private String motherStatus;
	private String motherDeathCause;
	private String motherDeathAge;
	private String siblingStatus;
	private String siblingDeathCause;
	private String siblingDeathAge;
	private String familyIllnessHistory;

	
	private Date createdOn;
	
	public Integer getPatientId() {
		return patientId;
	}

	public void setPatientId(Integer patientId) {
		this.patientId = patientId;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public String getFatherStatus() {
		return fatherStatus;
	}

	public void setFatherStatus(String fatherStatus) {
		this.fatherStatus = fatherStatus;
	}

	public String getFatherDeathCause() {
		return fatherDeathCause;
	}

	public void setFatherDeathCause(String fatherDeathCause) {
		this.fatherDeathCause = fatherDeathCause;
	}

	public String getFatherDeathAge() {
		return fatherDeathAge;
	}

	public void setFatherDeathAge(String fatherDeathAge) {
		this.fatherDeathAge = fatherDeathAge;
	}

	public String getMotherStatus() {
		return motherStatus;
	}

	public void setMotherStatus(String motherStatus) {
		this.motherStatus = motherStatus;
	}

	public String getMotherDeathCause() {
		return motherDeathCause;
	}

	public void setMotherDeathCause(String motherDeathCause) {
		this.motherDeathCause = motherDeathCause;
	}

	public String getMotherDeathAge() {
		return motherDeathAge;
	}

	public void setMotherDeathAge(String motherDeathAge) {
		this.motherDeathAge = motherDeathAge;
	}

	
}
