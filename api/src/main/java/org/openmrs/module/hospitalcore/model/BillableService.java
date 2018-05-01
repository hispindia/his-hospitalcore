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

import org.openmrs.Concept;
import org.openmrs.module.hospitalcore.util.Money;


/**
 *
 */

public class BillableService implements Serializable{

	/**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    
    private Integer serviceId;
    
    private String name;
    
    private String shortName;
    
    private BigDecimal price;
    
    private Integer conceptId;
    
    private Boolean disable = false;
    
    private Concept category;
	
    public Integer getServiceId() {
    	return serviceId;
    }

	
    public void setServiceId(Integer serviceId) {
    	this.serviceId = serviceId;
    }

	
    public String getName() {
    	return name;
    }

	
    public void setName(String name) {
    	this.name = name;
    }

	
    public String getShortName() {
    	return shortName;
    }

	
    public void setShortName(String shortName) {
    	this.shortName = shortName;
    }

	
    public BigDecimal getPrice() {
    	return price;
    }

    public String getTextPrice(){
    	return new Money(this.price).toString();
    }
	
    public void setPrice(BigDecimal price) {
    	this.price = price;
    }

	
    public Integer getConceptId() {
    	return conceptId;
    }

	
    public void setConceptId(Integer conceptId) {
    	this.conceptId = conceptId;
    }

	
    public Boolean getDisable() {
    	return disable;
    }

	
    public void setDisable(Boolean disable) {
    	this.disable = disable;
    }


	public Concept getCategory() {
		return category;
	}


	public void setCategory(Concept category) {
		this.category = category;
	}
}
