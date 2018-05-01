/**
 *  Copyright 2013 Society for Health Information Systems Programmes, India (HISP India)
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
 *  author: ghanshyam
 *  date: 10-june-2013
 *  issue no: #1847
 **/

package org.openmrs.module.hospitalcore.model;

import java.util.Date;

import org.openmrs.Patient;

public class IpdPatientVitalStatistics {

	private Integer id;
	private Patient patient;
	private IpdPatientAdmissionLog ipdPatientAdmissionLog;
	// vitalSatatistics Form
	private String bloodPressure;
	private String pulseRate;
	private String temperature;
	private String dietAdvised;
	private String note;
	private Integer creator;
	private Date createdOn;

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

	public IpdPatientAdmissionLog getIpdPatientAdmissionLog() {
		return ipdPatientAdmissionLog;
	}

	public void setIpdPatientAdmissionLog(
			IpdPatientAdmissionLog ipdPatientAdmissionLog) {
		this.ipdPatientAdmissionLog = ipdPatientAdmissionLog;
	}

	public String getBloodPressure() {
		return bloodPressure;
	}

	public void setBloodPressure(String bloodPressure) {
		this.bloodPressure = bloodPressure;
	}

	public String getPulseRate() {
		return pulseRate;
	}

	public void setPulseRate(String pulseRate) {
		this.pulseRate = pulseRate;
	}

	public String getTemperature() {
		return temperature;
	}

	public void setTemperature(String temperature) {
		this.temperature = temperature;
	}

	public String getDietAdvised() {
		return dietAdvised;
	}

	public void setDietAdvised(String dietAdvised) {
		this.dietAdvised = dietAdvised;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Integer getCreator() {
		return creator;
	}

	public void setCreator(Integer creator) {
		this.creator = creator;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}
}
