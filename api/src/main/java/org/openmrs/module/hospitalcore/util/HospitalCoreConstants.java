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

/**
 * <p> Class: HospitalCoreConstants </p>
 * <p> Package: org.openmrs.module.hospitalcore.util </p> 
 * <p> Author: Nguyen manh chuyen </p>
 * <p> Update by: Nguyen manh chuyen </p>
 * <p> Version: $1.0 </p>
 * <p> Create date: Mar 24, 2011 1:41:13 PM </p>
 * <p> Update date: Mar 24, 2011 1:41:13 PM </p>
 **/
public class HospitalCoreConstants {
	public static final String MODULE_ID = "hospitalcore";
	public static final String PROPERTY_OBSGROUP = MODULE_ID + ".obsGroup";
	public static final String PROPERTY_MEDICAL_EXAMINATION = MODULE_ID + ".medicalExamination";
	public static final String PROPERTY_IPDENCOUNTER = MODULE_ID + ".ipdEncounter";
	public static final String PROPERTY_IDENTIFIER_PREFIX = MODULE_ID + ".identifier_prefix";
	public static final String PROPERTY_HOSPITAL_NAME = MODULE_ID + ".hospitalName";
	public static final String PROPERTY_HOSPITAL_NIN_NUMBER = MODULE_ID + ".nincode";
	
	
	
	
	public static final String CONCEPT_DATATYPE_CODED = "Coded";
	public static final String CONCEPT_DATATYPE_NA = "N/A";
	public static final String CONCEPT_CLASS_QUESTION = "Question";
	public static final String CONCEPT_CLASS_MISC = "Misc";
	public static final String CONCEPT_ADMISSION_OUTCOME = "Admission outcome";
	
	public static final Integer LABTEST_STATUS_NEW = 0;
	public static final Integer LABTEST_STATUS_ACCEPTED = 1;
	public static final Integer LABTEST_STATUS_PRINTED = 2;	
}
