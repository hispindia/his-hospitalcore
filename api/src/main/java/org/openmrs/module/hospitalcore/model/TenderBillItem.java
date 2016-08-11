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

import org.openmrs.User;


/**
 *
 */
public class TenderBillItem implements Serializable{

	
	/**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private Integer tenderBillItemId;
    
    private String name;

	private BigDecimal amount;
	
	private BigDecimal unitPrice;
	
	private int quantity;
    
    private Date createdDate;
    
    private Boolean voided=false;
    
    private Date voidedDate;
    
    private User voidedby;
    
    private Tender tender;
    
    private TenderBill tenderBill;

	
    public BigDecimal getAmount() {
    	return amount;
    }

	
    public void setAmount(BigDecimal amount) {
    	this.amount = amount;
    }

	
    public BigDecimal getUnitPrice() {
    	return unitPrice;
    }

	
    public void setUnitPrice(BigDecimal unitPrice) {
    	this.unitPrice = unitPrice;
    }

	
    public int getQuantity() {
    	return quantity;
    }

	
    public void setQuantity(int quantity) {
    	this.quantity = quantity;
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

	
    public Tender getTender() {
    	return tender;
    }

	
    public void setTender(Tender tender) {
    	this.tender = tender;
    }

	
    public TenderBill getTenderBill() {
    	return tenderBill;
    }

	
    public void setTenderBill(TenderBill tenderBill) {
    	this.tenderBill = tenderBill;
    }


	
    public Integer getTenderBillItemId() {
    	return tenderBillItemId;
    }


	
    public void setTenderBillItemId(Integer tenderBillItemId) {
    	this.tenderBillItemId = tenderBillItemId;
    }


	
    public String getName() {
    	return name;
    }


	
    public void setName(String name) {
    	this.name = name;
    }
    
 // New Requirement add 'voided_by' column in bills 
    public User getvoidedby() {
		return voidedby;
	}
	
	public void setvoidedby(User voidedby) {
		this.voidedby = voidedby;
	}

}
