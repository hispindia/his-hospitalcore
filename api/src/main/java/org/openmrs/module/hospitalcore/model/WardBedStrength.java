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

import org.openmrs.Concept;
import org.openmrs.User;


/**
 * 
 */
public class WardBedStrength implements Serializable {

	/**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private Integer wardBedStrengthId;
    private Concept ward;
    private Integer bedStrength;
    private Date createdOn;
    private User createdBy;
    
    
    public Integer getWardBedStrengthId() {
		return wardBedStrengthId;
	}
	public void setWardBedStrengthId(Integer wardBedStrengthId) {
		this.wardBedStrengthId = wardBedStrengthId;
	}
	public Concept getWard() {
		return ward;
	}
	public void setWard(Concept ward) {
		this.ward = ward;
	}
	public Integer getBedStrength() {
		return bedStrength;
	}
	public void setBedStrength(Integer bedStrength) {
		this.bedStrength = bedStrength;
	}
	public Date getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}
	public User getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}
	
	
    
  }
