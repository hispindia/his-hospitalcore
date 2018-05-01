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
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.openmrs.module.hospitalcore.util.ActionValue;

/**
 * <p> Class: InventoryStoreDrugTransaction </p>
 * <p> Package: org.openmrs.module.inventory.model </p> 
 * <p> Author: Nguyen manh chuyen </p>
 * <p> Update by: Nguyen manh chuyen </p>
 * <p> Version: $1.0 </p>
 * <p> Create date: Jan 5, 2011 1:28:02 PM </p>
 * <p> Update date: Jan 5, 2011 1:28:02 PM </p>
 **/
public class InventoryStoreDrugTransaction implements  Serializable {
	 public static final int STATUS_DONE = 1;
	 public static final int STATUS_NOT_YET = 0;
	 private static final long serialVersionUID = 1L;
	 private Integer id;
	 private InventoryStore store;
	 private int status;// =1 done =0 not yet
	 private String description;
	 private int typeTransaction;
	 private Set<InventoryStoreDrugIndent> indents;
	 private Date createdOn;
	 private String createdBy;
	 private String paymentCategory;
	 private String receiptNo;
	 private BigDecimal billAmount;
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
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getDescription() {
		return StringUtils.isBlank(description)?"Unknown" : description ;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getTypeTransaction() {
		return typeTransaction;
	}
	public String getTypeTransactionName() {
		return typeTransaction == 1? ActionValue.TRANSACTION_NAMES.get(0) : ActionValue.TRANSACTION_NAMES.get(1);
	}
	public void setTypeTransaction(int typeTransaction) {
		this.typeTransaction = typeTransaction;
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
	public Set<InventoryStoreDrugIndent> getIndents() {
		return indents;
	}
	public void setIndents(Set<InventoryStoreDrugIndent> indents) {
		this.indents = indents;
	}
	 
	public String getPaymentCategory()
	{
		return paymentCategory;
	}
	public void setPaymentCategory(String paymentCategory)
	{
		 this.paymentCategory=paymentCategory;
	}
	public String getReceiptNo() {
		return receiptNo;
	}
	public void setReceiptNo(String receiptNo) {
		this.receiptNo = receiptNo;
	}
	public BigDecimal getBillAmount() {
		return billAmount;
	}
	public void setBillAmount(BigDecimal billAmount) {
		this.billAmount = billAmount;
	}
}