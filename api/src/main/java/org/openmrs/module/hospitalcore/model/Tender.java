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

public class Tender implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private int tenderId;
	private int number;
	private String name;
	private String description;
	private Date openingDate;
	private Date closingDate;
	private Date createdDate;
	private Boolean retired=false;
	private Date retiredDate ;
	private BigDecimal price ;
	public int getTenderId() {
		return tenderId;
	}
	public void setTenderId(int tenderId) {
		this.tenderId = tenderId;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Date getOpeningDate() {
		return openingDate;
	}
	public void setOpeningDate(Date openingDate) {
		this.openingDate = openingDate;
	}
	public Date getClosingDate() {
		return closingDate;
	}
	public void setClosingDate(Date closingDate) {
		this.closingDate = closingDate;
	}
    public BigDecimal getPrice() {
    	return price;
    }
    public void setPrice(BigDecimal price) {
    	this.price = price;
    }
	
    public Date getCreatedDate() {
    	return createdDate;
    }
	
    public void setCreatedDate(Date createdDate) {
    	this.createdDate = createdDate;
    }
	
    public Boolean getRetired() {
    	return retired;
    }
	
    public void setRetired(Boolean retired) {
    	this.retired = retired;
    }
	
    public Date getRetiredDate() {
    	return retiredDate;
    }
	
    public void setRetiredDate(Date retiredDate) {
    	this.retiredDate = retiredDate;
    }
}
