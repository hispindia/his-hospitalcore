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

import org.openmrs.Encounter;

public class TriagePatientData implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer id;
	private Double weight;
	private Double height;
	private Double bmi;
	private Double systolic;
	private Double daistolic;
	private Double pulsRate;
	private Double temperature;
	private Date lastMenstrualDate;
	private Double fbs;
	private Double rbs;
	private Double ppbs;
	private Date createdOn;
	private TriagePatientQueueLog triageLogId;
	private Encounter encounterOpd;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Double getWeight() {
		return weight;
	}
	public void setWeight(Double weight) {
		this.weight = weight;
	}
	public Double getHeight() {
		return height;
	}
	public void setHeight(Double height) {
		this.height = height;
	}
	public Double getBmi() {
		return bmi;
	}
	public void setBmi(Double bmi) {
		this.bmi = bmi;
	}
	public Double getSystolic() {
		return systolic;
	}
	public void setSystolic(Double systolic) {
		this.systolic = systolic;
	}
	public Double getDaistolic() {
		return daistolic;
	}
	public void setDaistolic(Double daistolic) {
		this.daistolic = daistolic;
	}
	public Double getPulsRate() {
		return pulsRate;
	}
	public void setPulsRate(Double pulsRate) {
		this.pulsRate = pulsRate;
	}
	public Double getTemperature() {
		return temperature;
	}
	public void setTemperature(Double temperature) {
		this.temperature = temperature;
	}
	public Date getLastMenstrualDate() {
		return lastMenstrualDate;
	}
	public void setLastMenstrualDate(Date lastMenstrualDate) {
		this.lastMenstrualDate = lastMenstrualDate;
	}
	
	public Double getFbs() {
		return fbs;
	}

	public void setFbs(Double fbs) {
		this.fbs = fbs;
	}

	public Double getRbs() {
		return rbs;
	}

	public void setRbs(Double rbs) {
		this.rbs = rbs;
	}

	public Double getPpbs() {
		return ppbs;
	}

	public void setPpbs(Double ppbs) {
		this.ppbs = ppbs;
	}
	
	public Date getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}
	public TriagePatientQueueLog getTriageLogId() {
		return triageLogId;
	}
	public void setTriageLogId(TriagePatientQueueLog triageLogId) {
		this.triageLogId = triageLogId;
	}
	public Encounter getEncounterOpd() {
		return encounterOpd;
	}
	public void setEncounterOpd(Encounter encounterOpd) {
		this.encounterOpd = encounterOpd;
	}
	
}
