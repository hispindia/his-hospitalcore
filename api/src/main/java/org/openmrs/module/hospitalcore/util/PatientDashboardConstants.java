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

public class PatientDashboardConstants {
	//ghanshyam 27/06/2012 tag MS_SHOULD_BE_FINAL code MODULE_ID,CONCEPT_CLASS_NAME_DIAGNOSIS,CONCEPT_CLASS_NAME_PROCEDURE
	public static final String MODULE_ID = "patientdashboard";
	public static final String CONCEPT_CLASS_NAME_SYMPTOM = "Symptom";
	public static final String CONCEPT_CLASS_NAME_DIAGNOSIS = "Diagnosis";
	public static final String CONCEPT_CLASS_NAME_PROCEDURE = "Procedure";
	public static final String CONCEPT_CLASS_NAME_EXAMINATION = "EXAMINATION";
	//ghanshyam 1-june-2013 New Requirement #1633 User must be able to send investigation orders from dashboard to billing
	public static final String CONCEPT_CLASS_NAME_INVESTIGATION = "Test";
	//ghanshyam 12-june-2013 New Requirement #1635 User should be able to send pharmacy orders to issue drugs to a patient from dashboard
	public static final String CONCEPT_CLASS_NAME_DRUG = "Drug";
	public static String PROPERTY_OPDWARD = MODULE_ID + ".OPDRootConcept";
	
	public static String PROPERTY_IPDWARD = MODULE_ID + ".IPDRootConcept";
	public static String PROPERTY_HOSPITAL = MODULE_ID + ".externalHospitalConcept";
	public static String PROPERTY_SYMPTOM = MODULE_ID + ".symptomConcept";
	public static String PROPERTY_EXAMINATION = MODULE_ID + ".examinationConcept";
	public static String PROPERTY_PROVISIONAL_DIAGNOSIS = MODULE_ID + ".provisionalDiagnosisConcept";
	public static String PROPERTY_POST_FOR_PROCEDURE = MODULE_ID + ".postForProcedureConcept";
	//ghanshyam 1-june-2013 New Requirement #1633 User must be able to send investigation orders from dashboard to billing
	public static String PROPERTY_FOR_INVESTIGATION = MODULE_ID + ".investigationConcept";
	//ghanshyam 12-june-2013 New Requirement #1635 User should be able to send pharmacy orders to issue drugs to a patient from dashboard
	public static String PROPERTY_FOR_DRUG = MODULE_ID + ".drugConcept";
	public static String PROPERTY_INTERNAL_REFERRAL = MODULE_ID + ".internalReferralConcept";
	public static String PROPERTY_EXTERNAL_REFERRAL = MODULE_ID +  ".externalReferralConcept";
	public static String PROPERTY_VISIT_OUTCOME = MODULE_ID + ".visitOutcomeConcept";
	public static String PROPERTY_OPD_ENCOUTNER_TYPE = MODULE_ID + ".opdEncounterType";
	public static String PROPERTY_TRIAGE_ENCOUTNER_TYPE = MODULE_ID + ".triageEncounterType";
	public static String PROPERTY_LAB_ENCOUTNER_TYPE = MODULE_ID + ".labEncounterType";
	public static String PROPERTY_INIT_CONCEPT = MODULE_ID + ".initNeededConcept";
	
}
