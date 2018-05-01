package org.openmrs.module.hospitalcore.model;


import java.io.Serializable;
import java.util.Date;

import org.openmrs.Concept;
import org.openmrs.Encounter;
import org.openmrs.User;

public class Symptom implements Serializable{
private Integer symptomId;
private Encounter encounter;
private Concept symptomConcept;
private Date createdDate;
private User creator;
public Integer getSymptomId() {
	return symptomId;
}
public void setSymptomId(Integer symptomId) {
	this.symptomId = symptomId;
}
public Encounter getEncounter() {
	return encounter;
}
public void setEncounter(Encounter encounter) {
	this.encounter = encounter;
}
public Concept getSymptomConcept() {
	return symptomConcept;
}
public void setSymptomConcept(Concept symptomConcept) {
	this.symptomConcept = symptomConcept;
}
public Date getCreatedDate() {
	return createdDate;
}
public void setCreatedDate(Date createdDate) {
	this.createdDate = createdDate;
}
public User getCreator() {
	return creator;
}
public void setCreator(User creator) {
	this.creator = creator;
}

}
