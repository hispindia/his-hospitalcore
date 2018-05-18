/**
 *  Copyright 2011 Society for Health Information Systems Programmes, India (HISP India)
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

import org.openmrs.Patient;
import org.openmrs.module.hospitalcore.util.PatientUtils;

public class InventoryStoreDrugPatient implements  Serializable {
	 private static final long serialVersionUID = 1L;
	 private Integer id;
	 private InventoryStore store;
	 private String name;
	 private String prescription;
	 private Date createdOn;
	 private String createdBy;
	 private Patient patient;
	 private String identifier;
	 private Integer duplicateBill=0;
	 private Integer voided=0;
	 private Date voidedDate;
	 private String voidedBy;
	 private String voidedReason;
	 private String patientCategoryf;
	 private String patientSubcategoryf;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public InventoryStore getStore() {
		return store;
	}
	public void setStore(InventoryStore store) {
		this.store = store;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPrescription() {
		return prescription;
	}
	public void setPrescription(String prescription) {
		this.prescription = prescription;
	}
	public Date getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public Patient getPatient() {
		return patient;
	}
	public void setPatient(Patient patient) {
		this.patient = patient;
	}
	public String getIdentifier() {
		return identifier;
	}
	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}
	public Integer getDuplicateBill() {
		return duplicateBill;
	}
	public void setDuplicateBill(Integer duplicateBill) {
		this.duplicateBill = duplicateBill;
	}
	public Integer getVoided() {
		return voided;
	}
	public void setVoided(Integer voided) {
		this.voided = voided;
	}
	public Date getVoidedDate() {
		return voidedDate;
	}
	public void setVoidedDate(Date voidedDate) {
		this.voidedDate = voidedDate;
	}
	public String getVoidedBy() {
		return voidedBy;
	}
	public void setVoidedBy(String voidedBy) {
		this.voidedBy = voidedBy;
	}
	public String getVoidedReason() {
		return voidedReason;
	}
	public void setVoidedReason(String voidedReason) {
		this.voidedReason = voidedReason;
	}
	public String getPatientCategory(){
		return PatientUtils.getPatientCategory(patient);
	}
	public String getPatientCategoryf() {
		return patientCategoryf;
	}
	public void setPatientCategoryf(String patientCategoryf) {
		this.patientCategoryf = patientCategoryf;
	}
	public String getPatientSubcategoryf() {
		return patientSubcategoryf;
	}
	public void setPatientSubcategoryf(String patientSubcategoryf) {
		this.patientSubcategoryf = patientSubcategoryf;
	}
}
