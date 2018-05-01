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

package org.openmrs.module.hospitalcore.util;

import java.util.Comparator;
//ghanshyam 04/07/2012 New Requirement #274
public class TestModel implements Comparator<TestModel>, Comparable<TestModel>{

	private String startDate;
	private String patientIdentifier;
	private String patientName;
	private String gender;
	private String testName;
	private Integer orderId;
	private String status;
	private Integer testId;
	private Integer givenFormId;
	private Integer notGivenFormId;
	private String acceptedDate;
	private String investigation;
	private Integer givenEncounterId;
	private Integer notGivenEncounterId;
	private Boolean xray;
	private String age;
	//ghanshyam 04/07/2012 New Requirement #274
	public TestModel(){
	   }

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getPatientIdentifier() {
		return patientIdentifier;
	}

	public void setPatientIdentifier(String patientIdentifier) {
		this.patientIdentifier = patientIdentifier;
	}

	public String getPatientName() {
		return patientName;
	}

	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getTestName() {
		return testName;
	}

	public void setTestName(String testName) {
		this.testName = testName;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getTestId() {
		return testId;
	}

	public void setTestId(Integer testId) {
		this.testId = testId;
	}

	public Integer getGivenFormId() {
		return givenFormId;
	}

	public void setGivenFormId(Integer formId) {
		this.givenFormId = formId;
	}

	public String getAcceptedDate() {
		return acceptedDate;
	}

	public void setAcceptedDate(String acceptedDate) {
		this.acceptedDate = acceptedDate;
	}

	public String getInvestigation() {
		return investigation;
	}

	public void setInvestigation(String investigation) {
		this.investigation = investigation;
	}

	public Integer getGivenEncounterId() {
		return givenEncounterId;
	}

	public void setGivenEncounterId(Integer encounterId) {
		this.givenEncounterId = encounterId;
	}

	public Integer getNotGivenFormId() {
		return notGivenFormId;
	}

	public void setNotGivenFormId(Integer notGivenFormId) {
		this.notGivenFormId = notGivenFormId;
	}

	public Integer getNotGivenEncounterId() {
		return notGivenEncounterId;
	}

	public void setNotGivenEncounterId(Integer notGivenEncounterId) {
		this.notGivenEncounterId = notGivenEncounterId;
	}

	public Boolean getXray() {
		return xray;
	}

	public void setXray(Boolean xray) {
		this.xray = xray;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}
	//ghanshyam 04/07/2012 New Requirement #274
	
	 // Overriding the compareTo method
	   public int compareTo(TestModel t){
	      return (this.patientName).compareTo(t.patientName);
	   }
	   
	   // Overriding the compare method
	public int compare(TestModel t, TestModel t1) {
		return 0;
	}

	   }
