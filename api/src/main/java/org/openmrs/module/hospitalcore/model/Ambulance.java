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
import java.util.Date;


/**
 *
 */
public class Ambulance implements Serializable {

	/**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private Integer ambulanceId;
    
    private String name;
    
    private String description;
    
    private Date createdDate;
    
    private Boolean retired =false;
    
    private Date retiredDate;

	
    public String getDescription() {
    	return description;
    }

	
    public void setDescription(String description) {
    	this.description = description;
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


	
    public Integer getAmbulanceId() {
    	return ambulanceId;
    }


	
    public void setAmbulanceId(Integer ambulanceId) {
    	this.ambulanceId = ambulanceId;
    }


	
    public String getName() {
    	return name;
    }


	
    public void setName(String name) {
    	this.name = name;
    }
    
}
