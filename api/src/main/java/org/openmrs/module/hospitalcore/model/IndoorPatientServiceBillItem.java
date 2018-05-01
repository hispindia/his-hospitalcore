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

import org.openmrs.Order;

/**
 *
 */
public class IndoorPatientServiceBillItem implements Serializable {
	
	/**
     * 
     */
	private static final long serialVersionUID = 1L;
	
	private Integer indoorPatientServiceBillItemId;
	
	private BillableService service;
	
	private IndoorPatientServiceBill indoorPatientServiceBill;
	
	private BigDecimal unitPrice;
	
	private BigDecimal amount;
	
	private BigDecimal actualAmount;
	
	private Integer quantity;
	
	private String name;
	
	private Date createdDate;
	
	private Order order ;
	
	public Integer getIndoorPatientServiceBillItemId() {
		return indoorPatientServiceBillItemId;
	}

	public void setIndoorPatientServiceBillItemId(
			Integer indoorPatientServiceBillItemId) {
		this.indoorPatientServiceBillItemId = indoorPatientServiceBillItemId;
	}
	
	public BillableService getService() {
		return service;
	}

	public void setService(BillableService service) {
		this.service = service;
	}
	
	public IndoorPatientServiceBill getIndoorPatientServiceBill() {
		return indoorPatientServiceBill;
	}

	public void setIndoorPatientServiceBill(
			IndoorPatientServiceBill indoorPatientServiceBill) {
		this.indoorPatientServiceBill = indoorPatientServiceBill;
	}

	public BigDecimal getUnitPrice() {
		return unitPrice;
	}
	
	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}
	
	public BigDecimal getAmount() {
		return amount;
	}
	
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	
	public Integer getQuantity() {
		return quantity;
	}
	
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Date getCreatedDate() {
		return createdDate;
	}
	
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public BigDecimal getActualAmount() {
		return actualAmount;
	}

	public void setActualAmount(BigDecimal actualAmount) {
		this.actualAmount = actualAmount;
	}
}
