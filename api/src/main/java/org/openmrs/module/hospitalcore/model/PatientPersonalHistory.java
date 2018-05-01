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

public class PatientPersonalHistory {

	public String getSmoke() {
		return smoke;
	}

	public void setSmoke(String smoke) {
		this.smoke = smoke;
	}

	public String getSmokeItem() {
		return smokeItem;
	}

	public void setSmokeItem(String smokeItem) {
		this.smokeItem = smokeItem;
	}

	public String getSmokeAverage() {
		return smokeAverage;
	}

	public void setSmokeAverage(String smokeAverage) {
		this.smokeAverage = smokeAverage;
	}

	public String getAlcohol() {
		return alcohol;
	}

	public void setAlcohol(String alcohol) {
		this.alcohol = alcohol;
	}

	public String getAlcoholItem() {
		return alcoholItem;
	}

	public void setAlcoholItem(String alcoholItem) {
		this.alcoholItem = alcoholItem;
	}

	public String getAlcoholAverage() {
		return alcoholAverage;
	}

	public void setAlcoholAverage(String alcoholAverage) {
		this.alcoholAverage = alcoholAverage;
	}

	public String getDrug() {
		return drug;
	}

	public void setDrug(String drug) {
		this.drug = drug;
	}

	public String getDrugItem() {
		return drugItem;
	}

	public void setDrugItem(String drugItem) {
		this.drugItem = drugItem;
	}

	public String getDrugAverage() {
		return drugAverage;
	}

	public void setDrugAverage(String drugAverage) {
		this.drugAverage = drugAverage;
	}

	public String getHivStatus() {
		return hivStatus;
	}

	public void setHivStatus(String hivStatus) {
		this.hivStatus = hivStatus;
	}

	public String getExposedHiv() {
		return exposedHiv;
	}

	public void setExposedHiv(String exposedHiv) {
		this.exposedHiv = exposedHiv;
	}

	public String getExposedHivFactor() {
		return exposedHivFactor;
	}

	public void setExposedHivFactor(String exposedHivFactor) {
		this.exposedHivFactor = exposedHivFactor;
	}

	public String getFamilyHelp() {
		return familyHelp;
	}

	public void setFamilyHelp(String familyHelp) {
		this.familyHelp = familyHelp;
	}

	public String getOtherHelp() {
		return otherHelp;
	}

	public void setOtherHelp(String otherHelp) {
		this.otherHelp = otherHelp;
	}

	public String getIncomeSource() {
		return incomeSource;
	}

	public void setIncomeSource(String incomeSource) {
		this.incomeSource = incomeSource;
	}

	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer patientId;
	private Date createdOn;
	private String smoke;
	private String smokeItem;
	private String smokeAverage;
	private String alcohol;
	private String alcoholItem;
	private String alcoholAverage;
	private String drug;
	private String drugItem;
	private String drugAverage;
	private String hivStatus;
	private String exposedHiv;
	private String exposedHivFactor;
	private String familyHelp;
	private String otherHelp;
	private String incomeSource;

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

}
