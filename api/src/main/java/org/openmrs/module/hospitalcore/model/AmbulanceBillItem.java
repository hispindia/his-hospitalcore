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
public class AmbulanceBillItem implements Serializable{

	/**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private Integer ambulanceBillItemId;
    
    private String name;

    private Ambulance ambulance;
    
    private AmbulanceBill ambulanceBill;
    
    private Integer numberOfTrip;
    
    private BigDecimal amount;
    
    private Date createdDate;
    
	private User voidedby;
	
	
	
	private Boolean voided = false;
	
	private Date voidedDate;
	
	// ghanshyam 07/07/2012 New Requirement #305: Additional details in Ambulance Bill
	
	private String patientName;
	
	//ghanshyam 1/08/2012 feedback of New Requirement #305: Additional details in Ambulance Bill changed receiptNumber from Integer to varchar
	
	private String receiptNumber;
	
    private String origin;
	 
	private String destination;

	
    public String getName() {
    	return name;
    }

	
    public void setName(String name) {
    	this.name = name;
    }

	
    public Ambulance getAmbulance() {
    	return ambulance;
    }

	
    public void setAmbulance(Ambulance ambulance) {
    	this.ambulance = ambulance;
    }

	
    public AmbulanceBill getAmbulanceBill() {
    	return ambulanceBill;
    }

	
    public void setAmbulanceBill(AmbulanceBill ambulanceBill) {
    	this.ambulanceBill = ambulanceBill;
    }

	
    public Integer getNumberOfTrip() {
    	return numberOfTrip;
    }

	
    public void setNumberOfTrip(Integer numberOfTrip) {
    	this.numberOfTrip = numberOfTrip;
    }

	
    public BigDecimal getAmount() {
    	return amount;
    }

	
    public void setAmount(BigDecimal amount) {
    	this.amount = amount;
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


	
    public Integer getAmbulanceBillItemId() {
    	return ambulanceBillItemId;
    }


	
    public void setAmbulanceBillItemId(Integer ambulanceBillItemId) {
    	this.ambulanceBillItemId = ambulanceBillItemId;
    }


	public String getPatientName() {
		return patientName;
	}


	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}


	public String getReceiptNumber() {
		return receiptNumber;
	}


	public void setReceiptNumber(String receiptNumber) {
		this.receiptNumber = receiptNumber;
	}


	public String getOrigin() {
		return origin;
	}


	public void setOrigin(String origin) {
		this.origin = origin;
	}


	public String getDestination() {
		return destination;
	}


	public void setDestination(String destination) {
		this.destination = destination;
	}
	// New Requirement add 'voided_by' column in bills 
	public User getvoidedby() {
		return voidedby;
	}
	
	public void setvoidedby(User voidedby) {
		this.voidedby = voidedby;
	}
	
	
    
}
