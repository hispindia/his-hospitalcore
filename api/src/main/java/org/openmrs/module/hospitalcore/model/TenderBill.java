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
import java.util.HashSet;
import java.util.Set;

import org.openmrs.User;

/**
 *
 */
public class TenderBill implements Serializable {
	
	/**
     * 
     */
	private static final long serialVersionUID = 1L;
	
	private Integer tenderBillId;
	
	private BigDecimal amount;
	
	private String description;
	
	private Boolean printed = false;
	
	private Date createdDate;
	
	private Boolean voided = false;
	
	private Date voidedDate;
	
	private Company company;
	
	private User creator;
	
	private User voidedby;
	
	private Receipt receipt;
	
	private Set<TenderBillItem> billItems;
	
	public BigDecimal getAmount() {
		return amount;
	}
	
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	
	public Boolean getPrinted() {
		return printed;
	}
	
	public void setPrinted(Boolean printed) {
		this.printed = printed;
	}
	
	public Date getCreatedDate() {
		return createdDate;
	}
	
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	
	public Boolean getVoided() {
		return voided;
	}
	
	public void setVoided(Boolean voided) {
		this.voided = voided;
	}
	
	public Date getVoidedDate() {
		return voidedDate;
	}
	
	public void setVoidedDate(Date voidedDate) {
		this.voidedDate = voidedDate;
	}
	
	public Company getCompany() {
		return company;
	}
	
	public void setCompany(Company company) {
		this.company = company;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public Integer getTenderBillId() {
		return tenderBillId;
	}
	
	public void setTenderBillId(Integer tenderBillId) {
		this.tenderBillId = tenderBillId;
	}
	
	public Set<TenderBillItem> getBillItems() {
		return billItems;
	}
	
	public void setBillItems(Set<TenderBillItem> billItems) {
		this.billItems = billItems;
	}
	
	public void addBillItem(TenderBillItem billItem) {
		if (billItems == null)
			billItems = new HashSet<TenderBillItem>();
		billItems.add(billItem);
	}
	
	public User getCreator() {
		return creator;
	}
	
	public void setCreator(User creator) {
		this.creator = creator;
	}

	public Receipt getReceipt() {
		return receipt;
	}

	public void setReceipt(Receipt receipt) {
		this.receipt = receipt;
	}
	// New Requirement add 'voided_by' column in bills 
	
	public User getvoidedby() {
		return voidedby;
	}
	
	public void setvoidedby(User voidedby) {
		this.voidedby = voidedby;
	}
	
}
