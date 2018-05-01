package org.openmrs.module.hospitalcore.model;

import java.io.Serializable;

import org.openmrs.Concept;

public class Answer implements Serializable{
private Integer answerId;
private Question question;
private Question quest;
public Question getQuest() {
	return quest;
}
public void setQuest(Question quest) {
	this.quest = quest;
}
private Concept answerConcept;
private String freeText;
public Integer getAnswerId() {
	return answerId;
}
public void setAnswerId(Integer answerId) {
	this.answerId = answerId;
}
public Question getQuestion() {
	return question;
}
public void setQuestion(Question question) {
	this.question = question;
}
public Concept getAnswerConcept() {
	return answerConcept;
}
public void setAnswerConcept(Concept answerConcept) {
	this.answerConcept = answerConcept;
}
public String getFreeText() {
	return freeText;
}
public void setFreeText(String freeText) {
	this.freeText = freeText;
}

}
