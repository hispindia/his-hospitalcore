package org.openmrs.module.hospitalcore.model;

import java.util.Date;

import org.openmrs.Patient;

public class PatientCSV {
	private Integer Id;
	private String patientidentifier;
	private String patientName;
	private String Mobile;
	//private String adharNumber;
	private Boolean retired = false;
	private String dept;
	private String patientType;
	private String visitDate;
	private String visitTime;
	private String ninId;
	private String encId;
	private String gender;
	private String age;

	public Integer getId() {
		return Id;
	}

	public void setId(Integer id) {
		Id = id;
	}

	public String getPatientName() {
		return patientName;
	}

	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}

	public String getMobile() {
		return Mobile;
	}

	public void setMobile(String mobile) {
		Mobile = mobile;
	}

/*	public String getAdharNumber() {
		return adharNumber;
	}

	public void setAdharNumber(String adharNumber) {
		this.adharNumber = adharNumber;
	}
*/
	public Boolean getRetired() {
		return retired;
	}

	public void setRetired(Boolean retired) {
		this.retired = retired;
	}

	public String getPatientType() {
		return patientType;
	}

	public void setPatientType(String patientType) {
		this.patientType = patientType;
	}

	public String getEncId() {
		return encId;
	}

	public void setEncId(String encId) {
		this.encId = encId;
	}

	public String getNinId() {
		return ninId;
	}

	public void setNinId(String ninId) {
		this.ninId = ninId;
	}

	public String getPatientidentifier() {
		return patientidentifier;
	}

	public void setPatientidentifier(String patientidentifier) {
		this.patientidentifier = patientidentifier;
	}

	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	public String getVisitTime() {
		return visitTime;
	}

	public void setVisitTime(String visitTime) {
		this.visitTime = visitTime;
	}

	public String getVisitDate() {
		return visitDate;
	}

	public void setVisitDate(String visitDate) {
		this.visitDate = visitDate;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

}
