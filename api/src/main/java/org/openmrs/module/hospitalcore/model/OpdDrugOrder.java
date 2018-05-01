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
 *  date: 12-june-2013
 *  issue no: #1635
 **/

package org.openmrs.module.hospitalcore.model;

import java.util.Date;
import org.openmrs.Concept;
import org.openmrs.Encounter;
import org.openmrs.Patient;
import org.openmrs.User;

public class OpdDrugOrder {
	private static final long serialVersionUID = 1L;
	private Integer opdDrugOrderId;
	private Patient patient;
	private Encounter encounter;
	private InventoryDrug inventoryDrug;
	private InventoryDrugFormulation inventoryDrugFormulation;
	private Concept frequency;
	private Integer noOfDays;
	private String comments;
	private User creator;
	private Date createdOn;
	private int orderStatus; //0=drug order from opd not yet processed,1=drug order from opd processed 
	private int cancelStatus; //0=not yet canceled,1=canceled
    private int orderFrom;  //drug ordered from  opd=0 , treatmentcard=1 , discharge=2
    private String referralWardName;
	public Integer getOpdDrugOrderId() {
		return opdDrugOrderId;
	}
	public void setOpdDrugOrderId(Integer opdDrugOrderId) {
		this.opdDrugOrderId = opdDrugOrderId;
	}
	public Patient getPatient() {
		return patient;
	}
	public void setPatient(Patient patient) {
		this.patient = patient;
	}
	public Encounter getEncounter() {
		return encounter;
	}
	public void setEncounter(Encounter encounter) {
		this.encounter = encounter;
	}
	public InventoryDrug getInventoryDrug() {
		return inventoryDrug;
	}
	public void setInventoryDrug(InventoryDrug inventoryDrug) {
		this.inventoryDrug = inventoryDrug;
	}
	public InventoryDrugFormulation getInventoryDrugFormulation() {
		return inventoryDrugFormulation;
	}
	public void setInventoryDrugFormulation(
			InventoryDrugFormulation inventoryDrugFormulation) {
		this.inventoryDrugFormulation = inventoryDrugFormulation;
	}
	public Concept getFrequency() {
		return frequency;
	}
	public void setFrequency(Concept frequency) {
		this.frequency = frequency;
	}
	public Integer getNoOfDays() {
		return noOfDays;
	}
	public void setNoOfDays(Integer noOfDays) {
		this.noOfDays = noOfDays;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public User getCreator() {
		return creator;
	}
	public void setCreator(User creator) {
		this.creator = creator;
	}
	public Date getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}
	public int getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(int orderStatus) {
		this.orderStatus = orderStatus;
	}
	public int getCancelStatus() {
		return cancelStatus;
	}
	public void setCancelStatus(int cancelStatus) {
		this.cancelStatus = cancelStatus;
	}
	public int getOrderFrom() {
		return orderFrom;
	}
	public void setOrderFrom(int orderFrom) {
		this.orderFrom = orderFrom;
	}
	public String getReferralWardName() {
		return referralWardName;
	}
	public void setReferralWardName(String referralWardName) {
		this.referralWardName = referralWardName;
	}
}
