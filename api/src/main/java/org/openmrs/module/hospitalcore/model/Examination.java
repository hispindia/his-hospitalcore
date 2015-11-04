

package org.openmrs.module.hospitalcore.model;

import java.io.Serializable;
import java.util.Date;

import org.openmrs.Concept;
import org.openmrs.Encounter;
import org.openmrs.User;

public class Examination implements Serializable{
private Integer examinationId;
private Encounter encounters;
private Concept examinationConcept;
private Date createdDate;
private User creator;
public Integer getExaminationId() {
	return examinationId;
}
public void setExaminationId(Integer examinationId) {
	this.examinationId = examinationId;
}
public Encounter getEncounters() {
	return encounters;
}
public void setEncounters(Encounter encounters) {
	this.encounters = encounters;
}
public Concept getExaminationConcept() {
	return examinationConcept;
}
public void setExaminationConcept(Concept examinationConcept) {
	this.examinationConcept = examinationConcept;
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