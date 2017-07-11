package org.openmrs.module.hospitalcore.model;

import java.io.Serializable;


import org.openmrs.Concept;

public class Question implements Serializable{
private Integer questionId;
private Symptom symptom;
private Concept questionConcept;


public Integer getQuestionId() {
	return questionId;
}
public void setQuestionId(Integer questionId) {
	this.questionId = questionId;
}
public Symptom getSymptom() {
	return symptom;
}
public void setSymptom(Symptom symptom) {
	this.symptom = symptom;
}
public Concept getQuestionConcept() {
	return questionConcept;
}
public void setQuestionConcept(Concept questionConcept) {
	this.questionConcept = questionConcept;
}

}
