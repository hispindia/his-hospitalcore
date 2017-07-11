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

public class PatientDrugHistory {

	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer patientId;
	private String currentMedication;
	private String medicationName;
	private String medicationPeriod;
	private String medicationReason;
	private String medicationRecord;
	private String sensitiveMedication;
	private String sensitiveMedicationName;
	private String sensitiveMedicationSymptom;
	private String invasiveContraception;
	private String invasiveContraceptionName; 
	private Date createdOn;
	
	public String getCurrentMedication() {
		return currentMedication;
	}

	public void setCurrentMedication(String currentMedication) {
		this.currentMedication = currentMedication;
	}

	public String getMedicationName() {
		return medicationName;
	}

	public void setMedicationName(String medicationName) {
		this.medicationName = medicationName;
	}

	public String getMedicationPeriod() {
		return medicationPeriod;
	}

	public void setMedicationPeriod(String medicationPeriod) {
		this.medicationPeriod = medicationPeriod;
	}

	public String getMedicationReason() {
		return medicationReason;
	}

	public void setMedicationReason(String medicationReason) {
		this.medicationReason = medicationReason;
	}

	public String getMedicationRecord() {
		return medicationRecord;
	}

	public void setMedicationRecord(String medicationRecord) {
		this.medicationRecord = medicationRecord;
	}

	public String getSensitiveMedication() {
		return sensitiveMedication;
	}

	public void setSensitiveMedication(String sensitiveMedication) {
		this.sensitiveMedication = sensitiveMedication;
	}

	public String getSensitiveMedicationName() {
		return sensitiveMedicationName;
	}

	public void setSensitiveMedicationName(String sensitiveMedicationName) {
		this.sensitiveMedicationName = sensitiveMedicationName;
	}

	public String getSensitiveMedicationSymptom() {
		return sensitiveMedicationSymptom;
	}

	public void setSensitiveMedicationSymptom(String sensitiveMedicationSymptom) {
		this.sensitiveMedicationSymptom = sensitiveMedicationSymptom;
	}

	public String getInvasiveContraception() {
		return invasiveContraception;
	}

	public void setInvasiveContraception(String invasiveContraception) {
		this.invasiveContraception = invasiveContraception;
	}

	public String getInvasiveContraceptionName() {
		return invasiveContraceptionName;
	}

	public void setInvasiveContraceptionName(String invasiveContraceptionName) {
		this.invasiveContraceptionName = invasiveContraceptionName;
	}

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
