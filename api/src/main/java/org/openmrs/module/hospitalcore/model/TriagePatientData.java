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
import java.math.BigDecimal;
import java.util.Date;

public class TriagePatientData implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer id;
	private TriagePatientQueueLog triageLogId;
	private BigDecimal weight;
	private BigDecimal height;
	private BigDecimal mua;
	private BigDecimal chest;
	private BigDecimal abdominal;
	public BigDecimal getMua() {
		return mua;
	}

	public void setMua(BigDecimal mua) {
		this.mua = mua;
	}

	public BigDecimal getChest() {
		return chest;
	}

	public void setChest(BigDecimal chest) {
		this.chest = chest;
	}

	public BigDecimal getAbdominal() {
		return abdominal;
	}

	public void setAbdominal(BigDecimal abdominal) {
		this.abdominal = abdominal;
	}

	private BigDecimal temperature;
	private Integer systolic;
	private Integer daistolic;
	private Integer respiratoryRate;
	private Integer pulsRate;
	private String bloodGroup;
	private Date lastMenstrualDate;
	private String rhesusFactor;
	private String pitct;
	private Date createdOn;
	private Integer encounterOpd;
	
	
	public Integer getEncounterOpd() {
		return encounterOpd;
	}

	public void setEncounterOpd(Integer encounterOpd) {
		this.encounterOpd = encounterOpd;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public TriagePatientQueueLog getTriageLogId() {
		return triageLogId;
	}

	public void setTriageLogId(TriagePatientQueueLog triageLogId) {
		this.triageLogId = triageLogId;
	}

	public BigDecimal getWeight() {
		return weight;
	}

	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}

	public BigDecimal getHeight() {
		return height;
	}

	public void setHeight(BigDecimal height) {
		this.height = height;
	}

	public BigDecimal getTemperature() {
		return temperature;
	}

	public void setTemperature(BigDecimal temperature) {
		this.temperature = temperature;
	}

	public Integer getSystolic() {
		return systolic;
	}

	public void setSystolic(Integer systolic) {
		this.systolic = systolic;
	}

	public Integer getDaistolic() {
		return daistolic;
	}

	public void setDaistolic(Integer daistolic) {
		this.daistolic = daistolic;
	}

	public Integer getRespiratoryRate() {
		return respiratoryRate;
	}

	public void setRespiratoryRate(Integer respiratoryRate) {
		this.respiratoryRate = respiratoryRate;
	}

	public Integer getPulsRate() {
		return pulsRate;
	}

	public void setPulsRate(Integer pulsRate) {
		this.pulsRate = pulsRate;
	}

	public String getBloodGroup() {
		return bloodGroup;
	}

	public void setBloodGroup(String bloodGroup) {
		this.bloodGroup = bloodGroup;
	}

	public Date getLastMenstrualDate() {
		return lastMenstrualDate;
	}

	public void setLastMenstrualDate(Date lastMenstrualDate) {
		this.lastMenstrualDate = lastMenstrualDate;
	}

	public String getRhesusFactor() {
		return rhesusFactor;
	}

	public void setRhesusFactor(String rhesusFactor) {
		this.rhesusFactor = rhesusFactor;
	}

	public String getPitct() {
		return pitct;
	}

	public void setPitct(String pitct) {
		this.pitct = pitct;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

}
