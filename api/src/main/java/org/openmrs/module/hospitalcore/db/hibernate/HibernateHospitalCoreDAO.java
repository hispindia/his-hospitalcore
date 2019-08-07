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

package org.openmrs.module.hospitalcore.db.hibernate;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.openmrs.Concept;
import org.openmrs.Encounter;
import org.openmrs.EncounterType;
import org.openmrs.Obs;
import org.openmrs.Patient;
import org.openmrs.PatientIdentifier;
import org.openmrs.Person;
import org.openmrs.PersonAttribute;
import org.openmrs.PersonAttributeType;
import org.openmrs.PersonName;
import org.openmrs.api.context.Context;
import org.openmrs.api.db.DAOException;
import org.openmrs.module.hospitalcore.HospitalCoreService;
import org.openmrs.module.hospitalcore.concept.ConceptModel;
import org.openmrs.module.hospitalcore.concept.Mapping;
import org.openmrs.module.hospitalcore.db.HospitalCoreDAO;
import org.openmrs.module.hospitalcore.model.CoreForm;
import org.openmrs.module.hospitalcore.model.PatientSearch;
import org.openmrs.module.hospitalcore.util.DateUtils;

import java.text.ParseException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

public class HibernateHospitalCoreDAO implements HospitalCoreDAO {

	
	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	@Autowired
	private DataSource dataSource;
      
	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	/*
	 * public Integer getPatientCount(String) { JdbcTemplate jdbcTemplate = new
	 * JdbcTemplate(dataSource); String query =
	 * "SELECT COUNT(*) FROM patient WHERE" + " MONTH(date_created) =" ; return
	 * jdbcTemplate.queryForInt(query); }
	 */

	/* 1 */public Integer getNoOfPatientWithDogBite(String gender, Integer d0,
			String d1) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('DOG BITE')" + "AND Month(o.obs_datetime)="
				+ "'" + d0 + "'" + "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	/* 2 */public Integer getNoOfPatientWithOtherBite(String gender,
			Integer d0, String d1) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers) FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('ANIMAL BITE','HUMAN BITE','INSECT BITE','MONKEY BITE','SCORPION BITE')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	/* 3 */public Integer getNoOfPatientWithSnakeBite(String gender,
			Integer d0, String d1) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('SNAKE BITE')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	/* 4 */public Integer getNoOfPatientWithRheumatic(String gender,
			Integer d0, String d1) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('ACUTE RHEUMATIC FEVER')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	/* 5 */public Integer getNoOfPatientWithBigemany(String gender, Integer d0,
			String d1) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers) FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('ATRIAL BIGEMINY')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	/* 6 */public Integer getNoOfPatientWithCardiac(String gender, Integer d0,
			String d1) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('CONGESTIVE CARDIAC FAILURE','HEART FAILURE')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	/* 7 */public Integer getNoOfPatientWithVenous(String gender, Integer d0,
			String d1) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('DEEP VENOUS THROMBOSIS')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	/* 8 */public Integer getNoOfPatientWithHypertension(String gender,
			Integer d0, String d1) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('HYPERTENSION','HYPERTENSIVE HEART DISEASE','PRIMARY HYPERTENSION','HYPERTENSIVE RETINOPATHY')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	/* 9 */public Integer getNoOfPatientWithIschemic(String gender, Integer d0,
			String d1) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('ACUTE CORONARY SYNDROME','MYOCARDIAL INFARCTION','CORONARY ARTERY DISEASE','ISCHAEMIC HEART DISEASE','ACUTE MYOCARDIAL INFARCTION','ACUTE CORONARY INSUFFICIENCY SYNDROME')"
				+ " AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	/* 10 */public Integer getNoOfPatientWithRheumaticHeart(String gender,
			Integer d0, String d1) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('CHRONIC RHEUMATIC HEART DISEASE','RHEUMATIC HEART DISEASE')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/* 11 */public Integer getNoOfPatientWithVericoseVein(String gender,
			Integer d0, String d1) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('VARICOSE VEINS OF LOWER EXTREMITIES','VARICOSE LEG ULCER','VARICOSE VEINS')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithOtherCardiovascular(String gender,
			Integer d0, String d1) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('VENOUS STASIS','CARDIOMYOPATHY','VARICOCELE','PULMONARY EMBOLISM','MITRAL VALVE DISEASE','OBSTRUCTIVE CARDIOMYOPATHY' ,"
				+ " 'ATHEROSCLEROSIS','ARTERIAL EMBOLISM','ARTERIAL THROMBOSIS','PHLEBITIS AND THROMBOPHLEBITIS','VENOUS EMBOLISM','VENOUS THROMBOSIS',"
				+ "'MICROANGIOPATHY','PERIPHERAL VASCULAR DISEASE','VERRUCOUS ENDOCARDITIS','TRANSIENT ISCHAEMIC ATTACK','VERTEBRAL ARTERY SYNDROME')"
				+ " AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ " AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithLymphadenitis(String gender, Integer d0,
			String d1) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('ADENITIS, NOS','LYMPHADENITIS')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithCerebralPalsy(String gender, Integer d0,
			String d1) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('CEREBRAL PALSY')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithEncephalitis(String gender, Integer d0,
			String d1) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('ENCEPHALITIS, MYELITIS AND ENCEPHALOMYELITIS','MENINGOENCEPHALITIS','VIRAL ENCEPHALITIS','ACUTE ENCEPHALITIS SYNDROME')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithEpilepsy(String gender, Integer d0,
			String d1) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('EPILEPSY','SEIZURE','CONVULSIVE GENERALIZED SEIZURE DISORDER','TONIC-CLONIC SEIZURES')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithHemiparesis(String gender, Integer d0,
			String d1) {
				JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('HEMIPARESIS')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithHydrocephalus(String gender, Integer d0,
			String d1) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('CONGENITAL HYDROCEPHALUS')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithInsomnia(String gender, Integer d0,
			String d1) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('INSOMNIA')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithMigraine(String gender, Integer d0,
			String d1) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('MIGRAINE','CLUSTER HEADACHE')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithOtherNervous(String gender, Integer d0,
			String d1) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('CRANIAL NERVE ABNORMALITIES','PERIPHERAL NEUROPATHY','ENCEPHALOPATHY','REGRESSED MILESTONES','CARPAL TUNNEL SYNDROME','BELL PALSY',"
		        + " 'TRIGEMINAL NEURALGIA','AXONOTMESIS','COMPARTMENT SYNDROME','SLEEP PARALYSIS','MULTIPLE SCLEROSIS','IMPAIRED COGNITION','FIBRILLARY CHOREA','RADICULOPATHY',"
		        + " 'SLEEP APNOEA','SLEEP DISORDERED BREATHING','OTHER NEUROLOGICAL DISORDER','SUPRAORBITAL NEURALGIA','SYRINGOMYELIA','SPINAL SUBDURAL HAEMATOMA')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithParaplegia(String gender, Integer d0,
			String d1) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN  ('SPINAL PARAPLEGIA')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithParkinsonism(String gender, Integer d0,
			String d1) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('PARKINSON DISEASE')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithCerebro(String gender, Integer d0,
			String d1) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('CEREBROVASCULAR ACCIDENT','CEREBROVASCULAR DISEASE','CEREBRAL INFARCTION')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithVertigo(String gender, Integer d0,
			String d1) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('VERTIGO')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithCongenitalDisorders(String gender,
			Integer d0, String d1) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('CONGENITAL DISORDER','CONGENITAL ABNORMALITY','CLEFT LIP AND CLEFT PALATE','RADIAL POLYDACTYLY','CRYPTOTIA','BIFED THUMB',"
		        + " 'CONGENITAL DEFORMITY - POLYDACTYLY','CONGENITAL DEFORMITY - SYNDACTYLY','TONGUE TIE','CONGENITAL DISLOCATION OF HIP','CONGENITAL TALIPES EQUINOVARUS',"
		        + " 'SPINAL DYSGENESIS','CONGENITAL CUBITUS VALGUS','CONGENITAL CUBITUS VARUS','SPINA BIFIDA')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithCongenitalHeart(String gender, Integer d0,
			String d1) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('CONGENITAL HEART DISEASE')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithDental(String gender, Integer d0, String d1) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('IMPACTED TOOTH','PERIODONTAL ABSCESS','DENTAL ABSCESS','GINGIVAL ABSCESS','BONY SPICULE','TOOTH FRACTURE',"
		        + " 'MOBILE TOOTH','PERIODONTAL POCKET','GINGIVAL HYPERPLASIA','TOOTH ATTRITION','TOOTH ABRASION','RETAINED TOOTH','PARTIAL EDENTULISM',"
		        + " 'DRY SOCKET','CHRONIC GINGIVITIS','THERMAL SENSITIVITY','DRAINING SINUS','CERVICAL ABRASION OF TOOTH','MALOCCLUSION OF TEETH','AGGRESSIVE PERIODONTITIS',"
		        + "'EXTRUDED TOOTH','GINGIVITIS','PERIODONTITIS','ACUTE PULPITIS','PERICORONITIS','DENTAL CYST','EPULIS')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithCaries(String gender, Integer d0, String d1) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('GROSS CARIES','CARIES TOOTH WITH PULP EXPOSURE','CARIES TOOTH WITH BONE INFECTION','RAMPANT DENTAL CARIES','SENSITIVE DENTIN','ACUTE PERIODONTITIS',"
	            + " 'PERIAPICAL ABSCESS','XEROSTOMIA','LINGUAL TORUS','EDENTULOUS','ILL FITTING DENTURE','DENTAL CARIES')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithAcne(String gender, Integer d0, String d1) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('ACNE VULGARIS')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithCutaneous(String gender, Integer d0,
			String d1) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('FURUNCULOSIS','ABSCESS','CARBUNCLE')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithDermatitis(String gender, Integer d0,
			String d1) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('DERMATITIS','ALLERGIC DERMATITIS','ECZEMA','PAPULAR PRURITIC ERUPTION','PAPULAR PRURITIC ERUPTION',"
                + " 'ALLERGIC SKIN')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithHerpes(String gender, Integer d0, String d1) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('POSTHERPETIC NEURALGIA','HERPES ZOSTER')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithImpetigo(String gender, Integer d0,
			String d1) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('PYODERMA','IMPETIGO CONTAGIOSA')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithOtherSkin(String gender, Integer d0,
			String d1) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('CELLULITIS','FOLLICULITIS','FUNGAL INFECTION OF THE SKIN','PARONYCHIA','SEBACEOUS CYST',"
                + " 'FIXED DRUG REACTION','HYPOPIGMENTATION','OTHER DISEASES OF SKIN AND SUBCUTANEOUS TISSUE','INFECTIONS OF SKIN AND SUBCUTANEOUS TISSUE',"
                + " 'FINGERNAIL ABNORMALITIES','SUNBURN','PERIPORITIS','DERMATOSCLEROSIS','SOLAR KERATOSIS','DERMATITIS DUE TO SOLAR RADIATION',"
                + " 'ACUTE INFECTION OF THE HAND')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithPruritus(String gender, Integer d0,
			String d1) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('PRURITIS')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithScabies(String gender, Integer d0,
			String d1) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('SCABIES')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithTinea(String gender, Integer d0, String d1) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('TINEA CAPITIS','TINEA CORPORIS')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithVersicolor(String gender, Integer d0,
			String d1) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('TINEA VERSICOLOR')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithUrticaria(String gender, Integer d0,
			String d1) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('URTICARIA')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithVitiligo(String gender, Integer d0,
			String d1) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('VITILIGO')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithWart(String gender, Integer d0, String d1) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('VERRUCA PLANA','PIGMENTED WARTS','HUMAN PAPILLOMAVIRUS')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithDracunculiasis(String gender, Integer d0,
			String d1) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('GUINEA WORM DISEASE')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithDiarrhea(String gender, Integer d0,
			String d1) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('GASTROENTERITIS','ACUTE DIARRHEA','ACUTE GASTROENTERITIS')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithAcutePancreatitis(String gender,
			Integer d0, String d1) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN  ('ACUTE PANCREATITIS')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithAppendicitis(String gender, Integer d0,
			String d1) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('APPENDICITIS')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithBleeding(String gender, Integer d0,
			String d1) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('BLEEDING PER RECTUM')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithCholecystitis(String gender, Integer d0,
			String d1) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('CHOLECYSTITIS')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithDiseasesOral(String gender, Integer d0,
			String d1) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('ULCER OF MOUTH','ORAL SUBMUCOUS FIBROSIS','ORAL SORES','ORAL HAIRY LEUKOPLAKIA','CHRONIC PERIODONTITIS',"
                + " 'ACUTE PAROTITIS','LUDWIGS ANGINA','STOMATITIS','PAROTID SWELLING','SIALOLITHIASIS','SIALADENITIS','DISEASE OF SALIVARY GLANDS',"
                + " 'EXTRA ORAL SWELLING')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithFissure(String gender, Integer d0,
			String d1) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('RECTOVAGINAL FISTULA','VESICOVAGINAL FISTULA','ANAL FISSURE','FISTULA IN ANO')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithIntestinalObstruction(String gender,
			Integer d0, String d1) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('INTESTINAL OBSTRUCTION','LARGE BOWEL OBSTRUCTION')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithNoninfectiveGastroenteritis(String gender,
			Integer d0, String d1) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('NONINFECTIVE ENTERITIS')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithChronicLiver(String gender, Integer d0,
			String d1) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('CHRONIC LIVER DISEASE','CIRRHOSIS')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithPancreatitis(String gender, Integer d0,
			String d1) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('CHRONIC PANCREATITIS')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithPepticUlcer(String gender, Integer d0,
			String d1) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('DUODENAL ULCER','PEPTIC ULCER','GASTRIC ULCER')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithPeritonitis(String gender, Integer d0,
			String d1) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('PERITONITIS')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithHydatedCystLiver(String gender,
			Integer d0, String d1) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('HYDATID CYST OF LIVER')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithCholelithiasis(String gender, Integer d0,
			String d1) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('CHOLELITHIASIS')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithGastritis(String gender, Integer d0,
			String d1) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('ACUTE GASTRITIS','ACID PEPTIC DISEASE','DUODENITIS','GASTRITIS')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithHaemorrhoids(String gender, Integer d0,
			String d1) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('HAEMORRHOIDS')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithHernia(String gender, Integer d0, String d1) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('FEMORAL HERNIA','INGUINAL HERNIA','CHOLESTASIS','DIAPHRAGMATIC HERNIA','UMBILICAL HERNIA')"
				+ " AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ " AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithGITDisorder(String gender, Integer d0,
			String d1) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('GASTROESOPHAGEAL REFLUX DISEASE','IRRITABLE BOWEL SYNDROME','OTHER DISEASES OF OESOPHAGUS','PROCTOCOLITIS'"
                + " 'PECTENOSIS','ANAL STENOSIS','PHARYNGEAL DIVERTICULA')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithHeartburn(String gender, Integer d0,
			String d1) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('GASSY CHEST PAIN','DYSPEPSIA')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithAmoebic(String gender, Integer d0,
			String d1) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('AMOEBIASIS WITH LIVER ABSCESS','AMOEBIC DYSENTERY','AMOEBIC COLITIS','AMOEBIASIS OTHERS','AMOEBIASIS')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithCandidiasis(String gender, Integer d0,
			String d1) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('ORAL CANDIDIASIS')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithBacillary(String gender, Integer d0,
			String d1) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('SHIGELLA DYSENTERY','BACILLARY DYSENTERY')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithDysentery(String gender, Integer d0,
			String d1) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('DYSENTERY')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithCretinism(String gender, Integer d0,
			String d1) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('CRETINISM')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithCystic(String gender, Integer d0, String d1) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('CYSTIC FIBROSIS WITH LIVER DISEASE')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithThyroidGland(String gender, Integer d0,
			String d1) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('THYROID LUMP','ACUTE THYROIDITIS','MULTINODULAR GOITER')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithDehydration(String gender, Integer d0,
			String d1) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('DEHYDRATION')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithMellitusType1(String gender, Integer d0,
			String d1) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('DIABETES MELLITUS TYPE 1')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithMellitusType2(String gender, Integer d0,
			String d1) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('DIABETES MELLITUS TYPE 2')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}
//////////////////////////////////////////////////////////////////////
	public Integer getNoOfPatientWithEndemicGoiter(String gender, Integer d0,
			String d1) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('GOITER','IODINE-DEFICIENCY DISORDER')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithHyperthyroidism(String gender, Integer d0,
			String d1) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('THYROTOXICOSIS')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithHypothyroidism(String gender, Integer d0,
			String d1) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('HYPOTHYROIDISM','HYPERTHYROIDISM')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithMalnutrition(String gender, Integer d0,
			String d1) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('KWASHIORKOR','NUTRITIONAL MARASMUS','MARASMIC KWASHIORKOR','MALNUTRITION')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithDiabetesMellitus(String gender,
			Integer d0, String d1) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('DIABETIC FOOT','DIABETIC RETINOPATHY','MALNUTRITION-RELATED DIABETES MELLITUS')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}
	////////////////////////////////////////////////////////////////////////////
	

	public Integer getNoOfPatientWithOtherEndocrine(String gender, Integer d0,
			String d1) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('SOMATOSTATINOMA','DIABETES INSIPIDUS','DYSLIPIDEMIA','PROLACTINOMA','DIABETES MELLITUS, ADDISONS DISEASE AND MYXEDEMA')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithPellagra(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('PELLAGRA')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithVitaminA(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('VITAMIN A DEFICIENCY')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithVitaminB(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('BURNING FEET SYNDROME')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithVitaminD(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('VITAMIN D DEFICIENCY','RICKETS')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithOverweight(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('OBESITY')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}
	public Integer getNoOfPatientWithScurvy(String gender, Integer d0, String d1) {
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('SCURVY')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithDNS(String gender, Integer d0, String d1) {
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('DEVIATED NASAL SEPTUM')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithHearingLos(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('SENSORY HEARING LOSS, BILATERAL','HEARING LOSS','NEURAL HEARING LOSS, BILATERAL','CONDUCTIVE AND SENSORINEURAL HEARING LOSS',"
                + " 'PRESBYCUSIS','SENSORINEURAL HEARING LOSS')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithHoarseness(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('HOARSENESS')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithExterna(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('CELLULITIS OF EXTERNAL EAR','OTOMYCOSIS','OTITIS EXTERNA')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithMedia(String gender, Integer d0, String d1) {
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('ACUTE SUPPURATIVE OTITIS MEDIA','CHRONIC SUPPURATIVE OTITIS MEDIA','SEROUS OTITIS MEDIA')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		
		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithOtalgia(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('OTORRHEA')"
				
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithTinnitus(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('TINNITUS')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithWax(String gender, Integer d0, String d1) {
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('CERUMEN IMPACTION')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithLaryngitis(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('ACUTE LARYNGITIS','LARYNGITIS')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithTonsillitis(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('CHRONIC TONSILLITIS','TONSILAR HYPERTROPHY')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithPharangitis(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('ACUTE PHARYNGITIS')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithChronic(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('CHRONIC PHARYNGITIS')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithOtherENT(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('EARACHE','TYMPANOSCLEROSIS','NASAL OBSTRUCTION','NASAL VESTIBULITIS','CHRONIC RHINOPHARYNGITIS',"
                + " 'VOCAL CORD DYSFUNCTION','LARYNGEAL VERTIGO','NASAL POLYP','SINUS PAIN','SPLIT LOBULE')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithSinusitis(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('SINUSITIS','CHRONIC SINUSITIS','SPHENOID SINUSITIS','ACUTE SINUSITIS')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithTonsilar(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('CHRONIC TONSILLITIS','TONSILAR HYPERTROPHY')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithRhinitis(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('ALLERGIC RHINITIS','NASAL CONGESTION','POSTERIOR RHINORRHEA')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithTractInfection(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('ACUTE RHINOPHARYNGITIS','ACUTE RHINITIS','UPPER RESPIRATORY TRACT INFECTION','ACUTE UPPER RESPIRATORY INFECTIONS OF MULTIPLE AND UNSPECIFIED SITES',"
                + " 'INFLUENZA LIKE ILLNESS')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithBlindness(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('BLINDNESS AND LOW VISION, BOTH EYES')"
				+ " AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ " AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithCataract(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('CATARACT')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithConjuntivitis(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('CONJUNCTIVITIS','VIRAL CONJUNCTIVITIS','PHLYCTENULAR CONJUNCTIVITIS')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithCorneal(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('CORNEAL OPACITY','CORNEAL ULCER')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithEyeRefraction(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('REFRACTIVE ERROR','PRESBYOPIA','MYOPIA','DEGENERATIVE PROGRESSIVE HIGH MYOPIA')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithGlaucoma(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('GLAUCOMA')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithChoroid(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('RETINOPATHY','RETINAL DYSTROPHY','MACULAR DEGENERATION','MACULAR OEDEMA','AGE RELATED MACULAR DEGENERATION',"
                + " 'VITREORETINAL DISEASE, OTHER')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithEyeDisorder(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('ALLERGIC CONJUNCTIVITIS','BLEPHARITIS','DACRYOCYSTITIS','STRABISMUS','APHAKIA','PSEUDOPHAKIA','OCULAR SURFACE DISORDER','PINGUECULITIS',"
                + " 'ASTHENOPIA','EPIPHORA','DRY EYE SYNDROME','UVEITIS','VITREOUS DEGENERATION','VITREOUS HAEMORRHAGE','COMPUTER VISION SYNDROME',"
				+ " 'OPTIC ATROPHY','SUBCONJUNCTIVAL HAEMORRHAGE','SCLERITIS','DISORDER OF OCULAR ADNEXA','OPTIC NERVE HYPOPLASIA')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithPterygium(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('PTERYGIUM')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithStye(String gender, Integer d0, String d1) {
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('CHALAZION','HORDEOLUM')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithCornealUlcer(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('CORNEAL OPACITY','CORNEAL ULCER')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithTrachoma(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('TRACHOMA')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithLocalizedswelling(String gender,
			Integer d0, String d1) {
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('ANKLE SWELLING')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithAbdominalPain(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('ABDOMINAL PAIN','LOW BACK PAIN','BODY ACHE','STERNAL PAIN','PERINEUM PAIN,MALE','LEG PAIN','NECK PAIN')"
				+ " AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ " AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}


	public Integer getNoOfPatientWithAcuteAbdomen(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('ACUTE ABDOMEN','ACUTE ABDOMINAL PAIN')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithAnorexia(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('ANOREXIA')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithAscites(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('ASCITES')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithAtaxia(String gender, Integer d0, String d1) {
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('ATAXIA')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithcommunicablediseases(String gender,
			Integer d0, String d1) {
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('SYPHILIS CONTACT')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithCough(String gender, Integer d0, String d1) {
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('NON-PRODUCTIVE COUGH')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithToxicity(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('TOXICITY, NOT OTHERWISE SPECIFIED','DRUG TOXICITY','FOOD TOXICITY')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithAphagia(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('SWALLOWING DIFFICULTIES','DYSPHAGIA,CRICOPHARYNGEAL','DYSPHAGIA')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithDysuria(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('DYSURIA')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithEdema(String gender, Integer d0, String d1) {
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('OEDEMA, LEGS')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithEpistaxis(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('EPISTAXIS')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithThrive(String gender, Integer d0, String d1) {
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('FAILURE TO THRIVE')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithHeadache(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('CHRONIC HEADACHE DISORDER')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithHematuria(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('HAEMATURIA')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithFalling(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('HISTORY OF FALL')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithInfantile(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('INFANTILE COLIC')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithJaundice(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('JAUNDICE')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithMemoryDisorder(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('SPATIAL MEMORY DISORDER')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithOtherGeneral(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('FACIAL SWELLING','SENILITY','ABDOMINAL ENLARGEMENT','TACTILE AGNOSIA')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithSyncope(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('SYNCOPE')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithUrinary(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('URINARY INCONTINENCE')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithWeakness(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('WEAKNESS')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithUnknownOrigin(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('FEVER OF UNKNOWN ORIGIN')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithAllergies(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('ALLERGY')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithFebrile(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('FEBRILE CONVULSION')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithRenal(String gender, Integer d0, String d1) {
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('ACUTE RENAL FAILURE')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithProstatic(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('BENIGN PROSTATIC HYPERPLASIA','HYPERPLASIA OF PROSTATE')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithBreast(String gender, Integer d0, String d1) {
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('BREAST LUMP')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithChronicRenal(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('URETERIC COLIC','RENAL COLIC')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithGlomerular(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('GLOMERULAR DISEASES')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithHydrocele(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('HYDROCELE')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithDisorderBreast(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('BREAST ABSCESS','MASTITIS')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}
	public Integer getNoOfPatientWithSpondylopathies(String gender, Integer d0,
			String d1) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('SPONDYLOSIS','ANKYLOSING SPONDYLITIS','CERVICAL SPONDYLITIS','SPONDYLOLISTHESIS','SPONDYLITIS','GUNSTOCK DEFORMITY',\n" + 
				"'CERVICAL SPONDYLOSIS')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithOsteomyelitis(String gender, Integer d0,
			String d1) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('ACUTE OSTEOMYELITIS')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithChronicOsteomyelitis(String gender,
			Integer d0, String d1) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('CHRONIC OSTEOMYELITIS')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithFractures(String gender, Integer d0,
			String d1) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('MANDIBULAR FRACTURE','MAXILLARY FRACTURE','FRACTURE OF CALCANEUS','FRACTURE OF TALUS','FRACTURE BIMALLEOLAR',\n" + 
				"'FRACTURE BOTH BONES LEG (RIGHT)','FRACTURE TRIMALLEOLAR','FRACTURE BOTH BONES LEG (LEFT)','FRACTURE COLLES (L)','FRACTURE BOTH BONES FOREARM (R)',\n" + 
				"'FRACTURE BOTH BONES FOREARM (L)','FRACTURE OLECRANON','FRACTURE HEAD RADIUS','FRACTURE SUPRACONDYLAR HUMERUS','FRACTURE SHAFT OF HUMERUS',\n" + 
				"'FRACTURE NECK OF HUMERUS','FRACTURE CLAVICLE','FRACTURE NECK OF FEMUR','FRACTURE OF ACETABULUM','FRACTURE SUBTROCHANTER',\n" + 
				"'FRACTURE TROCHANTER','FRACTURE OF FEMUR','FRACTURE SUPRACONDYLAR FEMUR','FRACTURE HOFFA','FRACTURE INTERCONDYLAR TIBIA','FRACTURE','FRACTURE COLLES (R)',\n" + 
				"'FRACTURE METATARSAL FOOT','FRACTURE EPIPHYSEAL INJURY','FRACTURE SPINE VERTEBRAE','DELAYED UNION OF FRACTURE','FRACTURE NON-UNION',\n" + 
				"'FRACTURE MALUNION')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithCancerBreast(String gender, Integer d0,
			String d1) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('MALIGNANT NEOPLASM OF BREAST')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithCancerBronchus(String gender, Integer d0,
			String d1) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('MALIGNANT NEOPLASM OF BREAST')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithCancerCervix(String gender, Integer d0,
			String d1) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('CERVICAL CANCER ')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithCancerLiver(String gender, Integer d0,
			String d1) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('MALIGNANT NEOPLASM OF LIVER AND INTRAHEPATIC BILE DUCT')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithOesophagus(String gender, Integer d0,
			String d1) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('MALIGNANT NEOPLASM OF OESOPHAGUS')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithOralCavity(String gender, Integer d0,
			String d1) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('MALIGNANT NEOPLASM OF LIP, ORAL CAVITY AND PHARYNX','CARCINOMA OF TONGUE')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithStomach(String gender, Integer d0,
			String d1) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('MALIGNANT NEOPLASM OF STOMACH')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithUterus(String gender, Integer d0, String d1) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('ENDOMETRIAL CANCER')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithNeoplasm(String gender, Integer d0,
			String d1) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('BENIGN NEOPLASM','THECOMA','BENIGN GENITAL NEOPLASM','BENIGN OVARIAN CYST')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithOtherNeoplasm(String gender, Integer d0,
			String d1) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('MALIGNANT TUMOR OF UNKNOWN ORIGIN','MALIGNANT NEOPLASM OF NECK','NEOPLASM','LEUKAEMIA',"
                + " 'KAPOSI SARCOMA','LEIOMYOSARCOMA','LIPOMA','OSTEOSARCOMA','OSTEOCHONDROMA','EWINGS SARCOMA OF BONE','CARCINOMA OF FALLOPIAN TUBE',"
                + " 'OVARIAN CANCER','UTERINE CANCER','VAGINAL CANCER','CANCER OF VULVA','MALIGNANT TUMOR OF NASAL CAVITY','CANCER NOT OTHERWISE SPECIFIED',"
                + " 'MALIGNANT NEOPLASM OF COLON','MALIGNANT NEOPLASM OF RECTOSIGMOID JUNCTION, RECTUM, ANUS AND ANAL CANAL','MALIGNANT NEOPLASM OF LIVER AND INTRAHEPATIC BILE DUCT',"
                + " 'MALIGNANT NEOPLASM OF PANCREAS','MALIGNANT NEOPLASM OF BLADDER','MALIGNANT NEOPLASM OF BRAIN','HODGKINS DISEASE','MALIGNANT NEOPLASM OF THYROID',"
                + " 'MALIGNANT NEOPLASM OF OTHER MALE GENITAL ORGANS','MALIGNANT NEOPLASM OF BONE AND ARTICULAR CARTILAGE','MALIGNANT NEOPLASM OF PLACENTA',"
                + " 'MALIGNANT NEOPLASM OF PROSTATE','GASTRIC LYMPHOMA','MASTOCYTOSIS','CARCINOMA IN SITU','MALIGNANT NEOPLASM OF FEMALE GENITAL ORGANS',"
                + " 'MALIGNANT TUMOUR OF LARYNX','MEGAKARYOCYTIC MYELOSCLEROSIS','CARCINOMA OF HEART','CANCER OF LYMPHATIC AND HAEMATOPOIETIC TISSUE',"
                + " 'CARCINOMA OF TESTIS','SYNOVIAL SARCOMA','GIANT CELL TUMOUR OF BONE, MALIGNANT','MULTIPLE MYELOMA','OCULAR LYMPHOMA' )"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithChoriocarcinoma(String gender, Integer d0,
			String d1) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('CHORIOCARCINOMA')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithMalignant(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('PLACENTAL SITE TROPHOBLASTIC TUMOR')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithAlcohol(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('DRUG ABUSE','ALCOHOLISM','ACUTE ALCOHOLISM')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithMoodDisorder(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('CHRONIC BIPOLAR I DISORDER, MOST RECENT EPISODE DEPRESSED')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithPsychiatric(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('ORGANIC BRAIN SYNDROME (CHRONIC)','PSYCHOPHYSIOLOGICAL INSOMNIA','ANORGASMIA','TRICHOTILLOMANIA','PICA')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithSchzophrenia(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('PSYCHOSIS','SCHIZOPHRENIA','SEVERE MENTAL DISORDER')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithDementia(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('MILD COGNITIVE DISORDER','DEMENTIA')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithSenile(String gender, Integer d0, String d1) {
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('SENILE DEMENTIA')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithRetardation(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('MENTAL RETARDATION','MENTAL DISABILITY')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithMental(String gender, Integer d0, String d1) {
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('ANXIETY NEUROSIS','GENERALISED ANXIETY DISORDER','COMMON MENTAL DISORDER')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithAsthma(String gender, Integer d0, String d1) {
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('ASTHMA EXACERBATION','ASTHMA','ASTHMATIC BRONCHITIS','ATOPIC ASTHMA')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithBronchiectasis(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('BRONCHIECTASIS')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithLowerRespiratory(String gender,
			Integer d0, String d1) {
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('CHRONIC OBSTRUCTIVE PULMONARY DISEASE','ACUTE EXACERBATION OF CHRONIC OBSTRUCTIVE AIRWAYS DISEASE',"
                + "'CHRONIC BRONCHITIS','EMPHYSEMA')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithBronchiolitis(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('ACUTE BRONCHITIS','TOXOPLASMOSIS, CENTRAL NERVOUS SYSTEM','ACUTE BRONCHIOLITIS')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithBronchoneumonia(String gender, Integer d0,
			String d1) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('PNEUMONIA','BRONCHOPNEUMONIA','LOBAR PNEUMONIA','PNEUMOCYSTIS CARINII PNEUMONIA',"
                + "'LOWER RESPIRATORY TRACT INFECTION','PNEUMONITIS','INTERSTITIAL PNEUMONITIS','BACTERIAL PNEUMONIA')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithRespiratory(String gender, Integer d0,
			String d1) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('ALLERGIC BRONCHITIS','ACUTE RESPIRATORY INFECTIONS','RESPIRATORY DISTRESS HAEMORRHAGIC FEVERS')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithPhysiotherapy(String gender, Integer d0,
			String d1) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('TENS','ULTRASONIC THERAPY','TRACTION','SHORT WAVE DIATHERMY','PARAFFIN WAX BATH',"
				+ "'IFT','FUNCTIONAL ELECTRICAL STIMULATION')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithHealthServices(String gender, Integer d0,
			String d1) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('GENERAL MEDICAL EXAMINATION')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithGonococcus(String gender, Integer d0,
			String d1) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('GONORRHOEA')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithAnogenital(String gender, Integer d0,
			String d1) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('GENITAL HERPES SIMPLEX','ANOGENITAL HERPES VIRAL (HERPES SIMPLEX) INFECTION')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithTransmitted(String gender, Integer d0,
			String d1) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('SEXUALLY TRANSMITTED INFECTION')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithSyphilis(String gender, Integer d0,
			String d1) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('SYPHILIS','SEQUELAE OF INFECTIOUS AND PARISITIC DISEASES','EARLY SYPHILIS','LATE SYPHILIS','OTHER AND UNSPECIFIED SYPHILIS')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithChlamydia(String gender, Integer d0,
			String d1) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('TRICHOMONIASIS')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithGenitalUlcer(String gender, Integer d0,
			String d1) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('CHANCROID','CLIMATIC BUBO','GRANULOMA INGUINALE')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithUrethral(String gender, Integer d0,
			String d1) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('URETHRAL DISCHARGE')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithChikungunya(String gender, Integer d0,
			String d1) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('CHIKUNGUNYA')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithDengue(String gender, Integer d0, String d1) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('DENGUE SHOCK SYNDROME','YELLOW FEVER','DENGUE HAEMORRHAGIC FEVER','DENGUE FEVER')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithFilariasis(String gender, Integer d0,
			String d1) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('FILARIASIS')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithJapaneseEncephalitis(String gender,
			Integer d0, String d1) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('JAPANESE ENCEPHALITIS')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithKalaAzar(String gender, Integer d0,
			String d1) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('CUTANEOUS LEISHMANIASIS','KALA-AZAR')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithMalaria(String gender, Integer d0,
			String d1) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('MALARIA')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithBirthWeight(String gender, Integer d0,
			String d1) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('LOW BIRTH-WEIGHT INFANT')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithUmbilical(String gender, Integer d0,
			String d1) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('INFECTION OF UMBILICAL CORD STUMP' )"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithNewborn(String gender, Integer d0,
			String d1) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('NEONATAL INTESTINAL OBSTRUCTION','RESPIRATORY ARREST OF NEWBORN','HYPOXEMIA OF NEWBORN','UMBILICAL GRANULOMA')"
				+ " AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ " AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithNeonatorum(String gender, Integer d0,
			String d1) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('TETANUS NEONATORUM','OBSTETRICAL TETANUS')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithBirthTrauma(String gender, Integer d0,
			String d1) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('BIRTH TRAUMA')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithEctopic(String gender, Integer d0,
			String d1) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('ECTOPIC PREGNANCY','RUPTURED ECTOPIC PREGNANCY')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithEdemaProtenuria(String gender, Integer d0,
			String d1) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('PREGNANCY INDUCED HYPERTENSION')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithSpontaneous(String gender, Integer d0,
			String d1) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('SPONTANEOUS ABORTION','INEVITABLE ABORTION')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithPostpartum(String gender, Integer d0,
			String d1) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('POSTPARTUM HAEMORRHAGE')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithPuerperalSepsis(String gender, Integer d0,
			String d1) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('PUERPERAL SEPSIS','POSTNATAL INFECTION')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithHemorrhage(String gender, Integer d0,
			String d1) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('ANTEPARTUM HAEMORRHAGE','URETHRAL DISCHARGE','PLACENTA PRAEVIA')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithMaternal(String gender, Integer d0,
			String d1) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('CEPHALOPELVIC DISPROPORTION')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}
   
	public Integer getNoOfPatientWithLabour(String gender, Integer d0, String d1) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('ATONY OF UTERUS WITH HAEMORRHAGE','PERFORATION OF UTERUS')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithEclampsia(String gender, Integer d0,
			String d1) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('PRE-ECLAMPSIA','ECLAMPSIA')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithVomitting(String gender, Integer d0,
			String d1) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('HYPEREMESIS GRAVIDARUM','EMESIS GRAVIDARUM')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithAbortive(String gender, Integer d0,
			String d1) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('GESTATIONAL TROPHOBLASTIC DISEASE','HYDATIDIFORM MOLE','COMPLETE ABORTION',"
                + " 'INCOMPLETE ABORTION','RECURRENT ABORTION','SEPTIC ABORTION','COMPLETE HYDATIDIFORM MOLE','PARTIAL HYDATIDIFORM MOLE')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithPreterm(String gender, Integer d0,
			String d1) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('PRETERM LABOUR')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithParasitic(String gender, Integer d0,
			String d1) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('TOXOPLASMOSIS, CENTRAL NERVOUS SYSTEM','PROGRESSIVE MULTIFOCAL LEUKOENCEPHALOPATHY',\n" + 
				"'MOLLUSCUM CONTAGIOSUM','BRUCELLOSIS','FUNGAL INFECTION','CANDIDIASIS','SCHISTOSOMIASIS','CRYPTOCOCCOSIS','NOCARDIOSIS',\n" + 
				"'CHLAMYDIA','SCRUB TYPHUS','MYCOSIS','RUBELLA','SEQUELAE OF INFECTIOUS AND PARISITIC DISEASES','CONGENITAL RUBELLA','NEUROCYSTICERCOSIS',\n" + 
				"'PARATYPHOID FEVER','DIARRHOEA AND GASTROENTERITIS OF PRESUMED INFECTIOUS ORIGIN')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithPuerperium(String gender, Integer d0,
			String d1) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('DIABETES WITH PREGNANCY')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithMalpresentation(String gender, Integer d0,
			String d1) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('MALPRESENTATION OF FOETUS')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithGestations(String gender, Integer d0,
			String d1) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN  ('MULTIPLE GESTATION','GRAND MULTIPARA')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithTwinPregnancy(String gender, Integer d0,
			String d1) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('TWIN PREGNANCY')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithMaterna(String gender, Integer d0,
			String d1) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('CEPHALOPELVIC DISPROPORTION')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithTeenage(String gender, Integer d0,
			String d1) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('TEENAGE PREGNANCY')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithEarly(String gender, Integer d0, String d1) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('EARLY PREGNANCY')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithPregnacyTest(String gender, Integer d0,
			String d1) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('PREGNANCY')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithRhIncompatibility(String gender,
			Integer d0, String d1) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('RH NEGATIVE PREGNANCY')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithFalseLabour(String gender, Integer d0,
			String d1) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('FALSE LABOUR')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithBreechDelivery(String gender, Integer d0,
			String d1) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('BREECH DELIVERY')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithNephrotic(String gender, Integer d0,
			String d1) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('NEPHROTIC SYNDROME')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithOtherBreas(String gender, Integer d0,
			String d1) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('RETRACTION OF NIPPLE','BENIGN BREAST DISEASE','BREAST DISORDER')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithGenitourinary(String gender, Integer d0,
			String d1) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('URETHRITIS','NEPHROPATHY','ACUTE EPIDIDYMO-ORCHITIS','MALE INFERTILITY','CHRONIC EPIDIDYMO-ORCHITIS',"
                + "'CYSTITIS','BARTHOLIN CYST','STRESS INCONTINENCE','GALACTORRHOEA','BALANOPOSTHITIS','SPERMATOCELE','DILATION OF RENAL CALIX',"
                + "'PYELOCYSTITIS','ACUTE PYONEPHROSIS','SEMINAL VESICULITIS','TORSION OF TESTIS','RENAL TRACT CANDIDIASIS')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithParaphimosis(String gender, Integer d0,
			String d1) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('PARAPHIMOSIS')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithTract(String gender, Integer d0, String d1) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('URINARY TRACT INFECTION')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithUrolithiasis(String gender, Integer d0,
			String d1) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('NEPHROLITHIASIS','UROLITHIASIS')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithDysmenorrhoea(String gender, Integer d0,
			String d1) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('DYSMENORRHOEA')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithOtherGynaecologicalDisorder(String gender,
			Integer d0, String d1) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('MENSTRUAL DYSFUNCTION','CERVICITIS','DYSPAREUNIA','MISSED ABORTION','SECONDARY AMENORRHOEA',\n" + 
				"'ADNEXAL MASS','OLIGOMENORRHOEA','POLYCYSTIC OVARIAN SYNDROME','CERVICAL POLYP','PRURITUS VULVAE','MENOPAUSE','TORSION OF OVARY',\n" + 
				"'HAEMORRHAGIC CYST OF OVARY','ENTEROCELE','SALPINGITIS AND OOPHORITIS','ADENOMYOSIS','DYSFUNCTIONAL UTERINE BLEEDING',\n" + 
				"'FUNCTIONAL OVARIAN CYST','VAGINAL CYST','BARTHOLIN ABSCESS','DISCHARGE PER VAGINUM','ENDOMETRIOSIS','PRIMARY FEMALE INFERTILITY',\n" + 
				"'UTERINE PROLAPSE')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithUterineFibroid(String gender, Integer d0,
			String d1) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('UTERINE FIBROID')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithAmenorrhoe(String gender, Integer d0,
			String d1) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('AMENORRHOEA','PRIMARY AMENORRHOEA')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithGenital(String gender, Integer d0,
			String d1) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('FEMALE GENITAL PROLAPSE')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithMenorrhagia(String gender, Integer d0,
			String d1) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('POLYMENORRHAGIA','MENORRHAGIA')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithOvarian(String gender, Integer d0,
			String d1) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('OVARIAN CYST')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithVaginal(String gender, Integer d0,
			String d1) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('VAGINAL BLEEDING')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithVaginitis(String gender, Integer d0,
			String d1) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('VAGINITIS','VAGINAL CANDIDIASIS','VULVITIS','BACTERIAL VAGINITIS')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithLeucorrhoea(String gender, Integer d0,
			String d1) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('LEUCORRHOEA')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithPelvic(String gender, Integer d0, String d1) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('FEMALE PELVIC INFLAMMATORY DISEASE')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithAnaemia(String gender, Integer d0,
			String d1) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('BLOOD LOSS ANAEMIA','HAEMOLYTIC ANAEMIA','ANAEMIA','ASTHMA EXACERBATION','IRON DEFICIENCY ANAEMIA')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithImmune(String gender, Integer d0, String d1) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('MYELOSCLEROSIS','LEUKOERYTHROBLASTOSIS','PRIMARY NEUTROPENIA','ERYTHRODYSPLASIA','BONE MARROW FAILURE','THALASSEMIA',\n" + 
				"'MULTICENTRIC RETICULOHISTIOCYTOSIS')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithhemorrhagic(String gender, Integer d0,
			String d1) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('THROMBOCYTOPATHY','THROMBOCYTOPENIA')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithAnthrax(String gender, Integer d0,
			String d1) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('ANTHRAX')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithDiphtheria(String gender, Integer d0,
			String d1) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('DIPHTHERIA')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithHIV(String gender, Integer d0, String d1) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('ACQUIRED IMMUNODEFICIENCY SYNDROME','ASYMPTOMATIC HIV INFECTION','HUMAN IMMUNODEFICIENCY VIRUS (HIV)',\n" + 
				"'HIV LIPODYSTROPHY','VACUOLAR MYELOPATHY','WASTING SYNDROME')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithMeasles(String gender, Integer d0,
			String d1) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('MEASLES')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithInfectious(String gender, Integer d0,
			String d1) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('URINARY TRACT INFECTION IN PREGNANCY')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithPlague(String gender, Integer d0, String d1) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('PLAGUE')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithSevereSepsis(String gender, Integer d0,
			String d1) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('SEPTICAEMIA')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithTuberculosis(String gender, Integer d0,
			String d1) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('PULMONARY TUBERCULOSIS','TUBERCULOUS LYMPHADENITIS','TUBERCULOSIS OF NERVOUS SYSTEM','MILIARY TUBERCULOSIS',\n" + 
				"'TUBERCULOSIS OF OTHER ORGANS','TUBERCULOUS ARTHRITIS','MULTI DRUG RESISTANT TUBERCULOSIS','SPUTUM NEGATIVE PULMONARY TUBERCULOSIS',\n" + 
				"'SPUTUM POSITIVE PULMONARY TUBERCULOSIS','EXTRAPULMONARY TUBERCULOSIS')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithInfestation(String gender, Integer d0,
			String d1) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('WORM INFESTATION')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithLeptopirosis(String gender, Integer d0,
			String d1) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('LEPTOSPIROSIS')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithParalysis(String gender, Integer d0,
			String d1) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('ACUTE POLIOMYELITIS','ACUTE FLACCID PARALYSIS')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithMeningococcal(String gender, Integer d0,
			String d1) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('MENINGOCOCCAL MENINGITIS')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithUnspecified(String gender, Integer d0,
			String d1) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('BACTERIAL MENINGITIS','CRYPTOCOCCAL MENINGITIS','MENINGITIS, NOS','VIRAL MENINGITIS')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithRabies(String gender, Integer d0, String d1) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('RABIES')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithTetanus(String gender, Integer d0,
			String d1) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('TETANUS OTHER THAN NEONATAL')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithMeningitis(String gender, Integer d0,
			String d1) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('MENINGOCOCCAL INFECTIONS')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithChicken(String gender, Integer d0,
			String d1) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('VARICELLA')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithLeprosy(String gender, Integer d0,
			String d1) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('LEPROSY')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithHerpesSimplex(String gender, Integer d0,
			String d1) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('HERPES SIMPLEX')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithMumps(String gender, Integer d0, String d1) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('MUMPS','PAROTITIS')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithCholera(String gender, Integer d0,
			String d1) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('CHOLERA')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithEnteric(String gender, Integer d0,
			String d1) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('TYPHOID FEVER')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithHepatitisA(String gender, Integer d0,
			String d1) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('HEPATITIS A','ACUTE HEPATITIS A','VIRAL HEPATITIS A','VIRAL HEPATITIS C')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithHepatitisUnspecified(String gender,
			Integer d0, String d1) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('OTHER ACUTE AND UNSPECIFIED VIRAL HEPATITIS','HEPATITIS')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithHepatitisB(String gender, Integer d0,
			String d1) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('HEPATITIS B','HEPATITIS D','VIRAL HEPATITIS B','ACUTE HEPATITIS B','VIRAL HEPATITIS D')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithHepatitisC(String gender, Integer d0,
			String d1) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('HEPATITIS C')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithHepatitisE(String gender, Integer d0,
			String d1) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('HEPATITIS E','VIRAL HEPATITIS E')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithLymphadenopathy(String gender, Integer d0,
			String d1) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('CERVICAL LYMPHADENOPATHY')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithWhooping(String gender, Integer d0,
			String d1) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('PERTUSSIS')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithInfuenza(String gender, Integer d0,
			String d1) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('INFLUENZA')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithSwine(String gender, Integer d0, String d1) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('SWINE FLU')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithTraffic(String gender, Integer d0,
			String d1) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('ROAD TRAFFIC ACCIDENT')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithDrowning(String gender, Integer d0,
			String d1) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('ACCIDENTAL DROWNING AND SUBMERSION')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithAssault(String gender, Integer d0,
			String d1) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('ASSAULT BY BLUNT OBJECT INITIAL ENCOUNTER')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithBurn(String gender, Integer d0, String d1) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('BURN')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithHarm(String gender, Integer d0, String d1) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('INTENTIONAL SELF HARM UNSPECIFIED')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithInfected(String gender, Integer d0,
			String d1) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('SEPTIC WOUND')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithAbuse(String gender, Integer d0, String d1) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('SEXUAL ASSAULT')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithPoisoning(String gender, Integer d0,
			String d1) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('ACUTE POISONING','TOBACCO  POISONING')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithForeign(String gender, Integer d0,
			String d1) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('FOREIGN BODY IN NOSE','FOREIGN BODY IN EAR','FOREIGN BODY IN THROAT')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithAccidents(String gender, Integer d0,
			String d1) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('ACCIDENT, NOT OTHERWISE SPECIFIED','ACCIDENTAL INJURY')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithOtherInjuries(String gender, Integer d0,
			String d1) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('TRAUMA','INTRACRANIAL INJURIES','SUPERFICIAL INJURY','SPLEEN HAEMATOMA','SPLINTER IN SKIN','PHYSICAL TRAUMA','LACERATION',\n" + 
				"'BLUNT TRAUMA CHEST','BLUNT TRAUMA ABDOMEN','HEAD INJURY','DEEP WOUND','INJURY','SOFT TISSUE INJURY','CONTUSION FOOT','MEDIAL MENISCUS TEAR',\n" + 
				"'INJURY OF EYE','TORN CARTILAGE','MENISCAL TEAR','NERVE INJURY','NEURAPRAXIA','INJURY OF NOSE')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithArthritisOther(String gender, Integer d0,
			String d1) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('ARTHRITIS','POST-INFECTIVE ARTHRITIS')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithDislocation(String gender, Integer d0,
			String d1) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('FRACTURE METATARSAL FOOT','DISLOCATION','SPRAIN','THUMB SPRAIN','RECURRENT SUBLUXATION OF PATELLA',\n" + 
				"'SPRAIN OF LEG','SPRAIN OF KNEE','SHOULDER DISLOCATION','INVERSION INJURY ANKLE')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithGouty(String gender, Integer d0, String d1) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('GOUTY ARTHRITIS','CHRONIC GOUTY ARTHRITIS')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithOsteoarthritis(String gender, Integer d0,
			String d1) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('OSTEOARTHRITIS')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithwithoutFracture(String gender, Integer d0,
			String d1) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('OSTEOPOROSIS')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithMusculoskeletal(String gender, Integer d0,
			String d1) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('JOINT SWELLING','PYOMYOSITIS','TEMPEROMANDIBULAR JOINT DISORDER','TENOSYNOVITIS','MUSCLE SPASTICITY',\n" + 
				"'TRISMUS','GRANULOMA MAXILLA','ACQUIRED CUBITUS VALGUS','ACQUIRED ABDUCTION DEFORMITY OF THE FOOT','EXOSTOSIS','TRIGGER THUMB',\n" + 
				"'OSGOOD SCHLATTER DISEASE','LUMBAR SPONDYLOSIS','REITERS DISEASE','SOLITARY BONE CYST','MUSCLE WEAKNESS OF LOWER EXTREMITY','SPINAL STENOSIS',\n" + 
				"'SHOULDER ENTHESOPATHY','SUDECKS ATROPHY','RUPTURE OF TENDO ACHILLES','LATERAL EPICONDYLITIS','FROZEN SHOULDER','FIBROMYALGIA',\n" + 
				"'DE QUERVAINS DISEASE','AVASCULAR NECROSIS OF THE HEAD OF FEMUR','MEDIAL EPICONDYLITIS OF ELBOW','MOVEMENT DISORDER','BURSITIS',\n" + 
				"'SCABIES','FISTULA OF SOFT PALATE','FISTULA OF HARD PALATE')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithPyogenic(String gender, Integer d0,
			String d1) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('ACUTE SUPPURATIVE ARTHRITIS','SEPTIC ARTHRITIS OF JOINT','SEPTIC HIP')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithRheumatoid(String gender, Integer d0,
			String d1) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT count(pers)FROM ("
				+ " SELECT o.person_id as pers"
				+ " FROM `encounter` e"
				+ " INNER JOIN  patient pat ON pat.patient_id=e.patient_id"
				+ " INNER JOIN patient_search ps ON ps.patient_id=e.patient_id"
				+ " INNER JOIN obs o ON o.person_id=ps.patient_id"
				+ " INNER JOIN concept_name cn ON o.value_coded = cn.concept_id "
				+ " INNER JOIN obs ob2 ON ob2.person_id=o.person_id"
				+ " INNER JOIN concept_name cn2 ON cn2.concept_id=ob2.concept_id AND cn2.name IN ('OPD WARD')"
				+ " AND cn2.concept_name_type = 'FULLY_SPECIFIED'"
				+ " WHERE encounter_type IN(5,6)"
				+ " AND o.concept_id IN (SELECT concept_id "
				+ " FROM concept_name "
				+ "  WHERE NAME IN('Provisional Diagnosis','Final Diagnosis') "
				+ " AND concept_name_type = 'FULLY_SPECIFIED') "
				+ " AND cn.name IN ('RHEUMATOID ARTHRITIS')"
				+ "AND Month(o.obs_datetime)=" + "'" + d0 + "'"
				+ "AND Year(o.obs_datetime)=" + "'" + d1 + "'"
				+ " AND ps.gender=" + "'" + gender + "'"
				+ " group by o.person_id ) ambika";

		return jdbcTemplate.queryForInt(query);
	}
	
	//////////////////////////////////////////////////////////////////////////////////////
	//drugs
	public Integer getNoOfPatientWithAnalgesicsAntiPyreticdrugs(Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT COUNT(pers)from ( "
				       + " SELECT isdp.patient_id AS pers FROM inventory_store_drug_patient isdp"
                       + " INNER JOIN inventory_store_drug_patient_detail isdpd ON isdp.id=isdpd.store_drug_patient_id"
                       + " INNER JOIN inventory_store_drug_transaction_detail isdt ON isdt.id=isdpd.transaction_detail_id "
                       + " INNER JOIN inventory_drug id ON id.id=isdt.drug_id "
                       + " INNER JOIN inventory_drug_category idc ON id.category_id=idc.id "
                       + " WHERE idc.name IN ('ANALGESICS AND ANTIPYRETIC DRUGS')"
                       + " AND MONTH(isdt.created_on)="+ "'" + d0 + "'"
                       + " AND YEAR(isdt.created_on)= " + "'" + d1 + "'" 
                       + " GROUP BY isdp.patient_id "
                       + " )ambika " ;
		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithAntiAllergicdrugs(Integer d0, String d1) {
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT COUNT(pers)from ( "
				       + " SELECT isdp.patient_id AS pers FROM inventory_store_drug_patient isdp"
                       + " INNER JOIN inventory_store_drug_patient_detail isdpd ON isdp.id=isdpd.store_drug_patient_id"
                       + " INNER JOIN inventory_store_drug_transaction_detail isdt ON isdt.id=isdpd.transaction_detail_id "
                       + " INNER JOIN inventory_drug id ON id.id=isdt.drug_id "
                       + " INNER JOIN inventory_drug_category idc ON id.category_id=idc.id "
                       + " WHERE idc.name IN ('ANTI ALLERGIC AND DRUGS USED IN ANAPHYLAXIS')"
                       + " AND MONTH(isdt.created_on)="+ "'" + d0 + "'"
                       + " AND YEAR(isdt.created_on)= " + "'" + d1 + "'" 
                       + " GROUP BY isdp.patient_id "
                       + " )ambika " ;
		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithAntiAnaemicdrugs(Integer d0, String d1) {
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT COUNT(pers)from ( "
				       + " SELECT isdp.patient_id AS pers FROM inventory_store_drug_patient isdp"
                       + " INNER JOIN inventory_store_drug_patient_detail isdpd ON isdp.id=isdpd.store_drug_patient_id"
                       + " INNER JOIN inventory_store_drug_transaction_detail isdt ON isdt.id=isdpd.transaction_detail_id "
                       + " INNER JOIN inventory_drug id ON id.id=isdt.drug_id "
                       + " INNER JOIN inventory_drug_category idc ON id.category_id=idc.id "
                       + " WHERE idc.name IN ('ANTI ANAEMIC DRUGS')"
                       + " AND MONTH(isdt.created_on)="+ "'" + d0 + "'"
                       + " AND YEAR(isdt.created_on)= " + "'" + d1 + "'" 
                       + " GROUP BY isdp.patient_id "
                       + " )ambika " ;
		return jdbcTemplate.queryForInt(query);
	}
	public Integer getNoOfPatientWithAntiDiabeticdrugs(Integer d0, String d1) {
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT COUNT(pers)from ( "
				       + " SELECT isdp.patient_id AS pers FROM inventory_store_drug_patient isdp"
                       + " INNER JOIN inventory_store_drug_patient_detail isdpd ON isdp.id=isdpd.store_drug_patient_id"
                       + " INNER JOIN inventory_store_drug_transaction_detail isdt ON isdt.id=isdpd.transaction_detail_id "
                       + " INNER JOIN inventory_drug id ON id.id=isdt.drug_id "
                       + " INNER JOIN inventory_drug_category idc ON id.category_id=idc.id "
                       + " WHERE idc.name IN ('ANTI DIABETIC DRUGS')"
                       + " AND MONTH(isdt.created_on)="+ "'" + d0 + "'"
                       + " AND YEAR(isdt.created_on)= " + "'" + d1 + "'" 
                       + " GROUP BY isdp.patient_id "
                       + " )ambika " ;
		return jdbcTemplate.queryForInt(query);
	}
	public Integer getNoOfPatientWithAntiEpilepticdrugs(Integer d0, String d1) {
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT COUNT(pers)from ( "
				       + " SELECT isdp.patient_id AS pers FROM inventory_store_drug_patient isdp"
                       + " INNER JOIN inventory_store_drug_patient_detail isdpd ON isdp.id=isdpd.store_drug_patient_id"
                       + " INNER JOIN inventory_store_drug_transaction_detail isdt ON isdt.id=isdpd.transaction_detail_id "
                       + " INNER JOIN inventory_drug id ON id.id=isdt.drug_id "
                       + " INNER JOIN inventory_drug_category idc ON id.category_id=idc.id "
                       + " WHERE idc.name IN ('ANTI EPILEPTIC DRUGS')"
                       + " AND MONTH(isdt.created_on)="+ "'" + d0 + "'"
                       + " AND YEAR(isdt.created_on)= " + "'" + d1 + "'" 
                       + " GROUP BY isdp.patient_id "
                       + " )ambika " ;
		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithAntFilarial(Integer d0, String d1) {
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT COUNT(pers)from ( "
				       + " SELECT isdp.patient_id AS pers FROM inventory_store_drug_patient isdp"
                       + " INNER JOIN inventory_store_drug_patient_detail isdpd ON isdp.id=isdpd.store_drug_patient_id"
                       + " INNER JOIN inventory_store_drug_transaction_detail isdt ON isdt.id=isdpd.transaction_detail_id "
                       + " INNER JOIN inventory_drug id ON id.id=isdt.drug_id "
                       + " INNER JOIN inventory_drug_category idc ON id.category_id=idc.id "
                       + " WHERE idc.name IN ('ANTI FILARIAL')"
                       + " AND MONTH(isdt.created_on)="+ "'" + d0 + "'"
                       + " AND YEAR(isdt.created_on)= " + "'" + d1 + "'" 
                       + " GROUP BY isdp.patient_id "
                       + " )ambika " ;
		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithAntiFungaldrugs(Integer d0, String d1) {
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT COUNT(pers)from ( "
				       + " SELECT isdp.patient_id AS pers FROM inventory_store_drug_patient isdp"
                       + " INNER JOIN inventory_store_drug_patient_detail isdpd ON isdp.id=isdpd.store_drug_patient_id"
                       + " INNER JOIN inventory_store_drug_transaction_detail isdt ON isdt.id=isdpd.transaction_detail_id "
                       + " INNER JOIN inventory_drug id ON id.id=isdt.drug_id "
                       + " INNER JOIN inventory_drug_category idc ON id.category_id=idc.id "
                       + " WHERE idc.name IN ('ANTI FUNGAL DRUGS')"
                       + " AND MONTH(isdt.created_on)="+ "'" + d0 + "'"
                       + " AND YEAR(isdt.created_on)= " + "'" + d1 + "'" 
                       + " GROUP BY isdp.patient_id "
                       + " )ambika " ;
		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithAntiLeishmaniasisdrugs(Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT COUNT(pers)from ( "
				       + " SELECT isdp.patient_id AS pers FROM inventory_store_drug_patient isdp"
                       + " INNER JOIN inventory_store_drug_patient_detail isdpd ON isdp.id=isdpd.store_drug_patient_id"
                       + " INNER JOIN inventory_store_drug_transaction_detail isdt ON isdt.id=isdpd.transaction_detail_id "
                       + " INNER JOIN inventory_drug id ON id.id=isdt.drug_id "
                       + " INNER JOIN inventory_drug_category idc ON id.category_id=idc.id "
                       + " WHERE idc.name IN ('ANTI LEISHMANIASIS DRUGS')"
                       + " AND MONTH(isdt.created_on)="+ "'" + d0 + "'"
                       + " AND YEAR(isdt.created_on)= " + "'" + d1 + "'" 
                       + " GROUP BY isdp.patient_id "
                       + " )ambika " ;
		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithAntiParkinsonismdrugs(Integer d0, String d1) {
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT COUNT(pers)from ( "
				       + " SELECT isdp.patient_id AS pers FROM inventory_store_drug_patient isdp"
                       + " INNER JOIN inventory_store_drug_patient_detail isdpd ON isdp.id=isdpd.store_drug_patient_id"
                       + " INNER JOIN inventory_store_drug_transaction_detail isdt ON isdt.id=isdpd.transaction_detail_id "
                       + " INNER JOIN inventory_drug id ON id.id=isdt.drug_id "
                       + " INNER JOIN inventory_drug_category idc ON id.category_id=idc.id "
                       + " WHERE idc.name IN ('ANTI PARKINSONISM DRUGS')"
                       + " AND MONTH(isdt.created_on)="+ "'" + d0 + "'"
                       + " AND YEAR(isdt.created_on)= " + "'" + d1 + "'" 
                       + " GROUP BY isdp.patient_id "
                       + " )ambika " ;
		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithAntiProtozoaldrugs(Integer d0, String d1) {
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT COUNT(pers)from ( "
				       + " SELECT isdp.patient_id AS pers FROM inventory_store_drug_patient isdp"
                       + " INNER JOIN inventory_store_drug_patient_detail isdpd ON isdp.id=isdpd.store_drug_patient_id"
                       + " INNER JOIN inventory_store_drug_transaction_detail isdt ON isdt.id=isdpd.transaction_detail_id "
                       + " INNER JOIN inventory_drug id ON id.id=isdt.drug_id "
                       + " INNER JOIN inventory_drug_category idc ON id.category_id=idc.id "
                       + " WHERE idc.name IN ('ANTI PROTOZOAL DRUGS')"
                       + " AND MONTH(isdt.created_on)="+ "'" + d0 + "'"
                       + " AND YEAR(isdt.created_on)= " + "'" + d1 + "'" 
                       + " GROUP BY isdp.patient_id "
                       + " )ambika " ;
		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithAntiRabies(Integer d0, String d1) {
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT COUNT(pers)from ( "
				       + " SELECT isdp.patient_id AS pers FROM inventory_store_drug_patient isdp"
                       + " INNER JOIN inventory_store_drug_patient_detail isdpd ON isdp.id=isdpd.store_drug_patient_id"
                       + " INNER JOIN inventory_store_drug_transaction_detail isdt ON isdt.id=isdpd.transaction_detail_id "
                       + " INNER JOIN inventory_drug id ON id.id=isdt.drug_id "
                       + " INNER JOIN inventory_drug_category idc ON id.category_id=idc.id "
                       + " WHERE idc.name IN ('ANTI RABIES')"
                       + " AND MONTH(isdt.created_on)="+ "'" + d0 + "'"
                       + " AND YEAR(isdt.created_on)= " + "'" + d1 + "'" 
                       + " GROUP BY isdp.patient_id "
                       + " )ambika " ;
		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithAntiBacterials(Integer d0, String d1) {
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT COUNT(pers)from ( "
				       + " SELECT isdp.patient_id AS pers FROM inventory_store_drug_patient isdp"
                       + " INNER JOIN inventory_store_drug_patient_detail isdpd ON isdp.id=isdpd.store_drug_patient_id"
                       + " INNER JOIN inventory_store_drug_transaction_detail isdt ON isdt.id=isdpd.transaction_detail_id "
                       + " INNER JOIN inventory_drug id ON id.id=isdt.drug_id "
                       + " INNER JOIN inventory_drug_category idc ON id.category_id=idc.id "
                       + " WHERE idc.name IN ('ANTIBACTERIALS')"
                       + " AND MONTH(isdt.created_on)="+ "'" + d0 + "'"
                       + " AND YEAR(isdt.created_on)= " + "'" + d1 + "'" 
                       + " GROUP BY isdp.patient_id "
                       + " )ambika " ;
		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithAntiCancerdrugs(Integer d0, String d1) {
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT COUNT(pers)from ( "
				       + " SELECT isdp.patient_id AS pers FROM inventory_store_drug_patient isdp"
                       + " INNER JOIN inventory_store_drug_patient_detail isdpd ON isdp.id=isdpd.store_drug_patient_id"
                       + " INNER JOIN inventory_store_drug_transaction_detail isdt ON isdt.id=isdpd.transaction_detail_id "
                       + " INNER JOIN inventory_drug id ON id.id=isdt.drug_id "
                       + " INNER JOIN inventory_drug_category idc ON id.category_id=idc.id "
                       + " WHERE idc.name IN ('ANTICANCER DRUGS')"
                       + " AND MONTH(isdt.created_on)="+ "'" + d0 + "'"
                       + " AND YEAR(isdt.created_on)= " + "'" + d1 + "'" 
                       + " GROUP BY isdp.patient_id "
                       + " )ambika " ;
		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithAntiHelminthics(Integer d0, String d1) {
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT COUNT(pers)from ( "
				       + " SELECT isdp.patient_id AS pers FROM inventory_store_drug_patient isdp"
                       + " INNER JOIN inventory_store_drug_patient_detail isdpd ON isdp.id=isdpd.store_drug_patient_id"
                       + " INNER JOIN inventory_store_drug_transaction_detail isdt ON isdt.id=isdpd.transaction_detail_id "
                       + " INNER JOIN inventory_drug id ON id.id=isdt.drug_id "
                       + " INNER JOIN inventory_drug_category idc ON id.category_id=idc.id "
                       + " WHERE idc.name IN ('ANTIHELMINTHICS')"
                       + " AND MONTH(isdt.created_on)="+ "'" + d0 + "'"
                       + " AND YEAR(isdt.created_on)= " + "'" + d1 + "'" 
                       + " GROUP BY isdp.patient_id "
                       + " )ambika " ;
		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithAntiMalarials(Integer d0, String d1) {
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT COUNT(pers)from ( "
				       + " SELECT isdp.patient_id AS pers FROM inventory_store_drug_patient isdp"
                       + " INNER JOIN inventory_store_drug_patient_detail isdpd ON isdp.id=isdpd.store_drug_patient_id"
                       + " INNER JOIN inventory_store_drug_transaction_detail isdt ON isdt.id=isdpd.transaction_detail_id "
                       + " INNER JOIN inventory_drug id ON id.id=isdt.drug_id "
                       + " INNER JOIN inventory_drug_category idc ON id.category_id=idc.id "
                       + " WHERE idc.name IN ('ANTIMALARIALS')"
                       + " AND MONTH(isdt.created_on)="+ "'" + d0 + "'"
                       + " AND YEAR(isdt.created_on)= " + "'" + d1 + "'" 
                       + " GROUP BY isdp.patient_id "
                       + " )ambika " ;
		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithAntiSeptics(Integer d0, String d1) {
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT COUNT(pers)from ( "
				       + " SELECT isdp.patient_id AS pers FROM inventory_store_drug_patient isdp"
                       + " INNER JOIN inventory_store_drug_patient_detail isdpd ON isdp.id=isdpd.store_drug_patient_id"
                       + " INNER JOIN inventory_store_drug_transaction_detail isdt ON isdt.id=isdpd.transaction_detail_id "
                       + " INNER JOIN inventory_drug id ON id.id=isdt.drug_id "
                       + " INNER JOIN inventory_drug_category idc ON id.category_id=idc.id "
                       + " WHERE idc.name IN ('ANTISEPTICS')"
                       + " AND MONTH(isdt.created_on)="+ "'" + d0 + "'"
                       + " AND YEAR(isdt.created_on)= " + "'" + d1 + "'" 
                       + " GROUP BY isdp.patient_id "
                       + " )ambika " ;
		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithAntiVertigodrugs(Integer d0, String d1) {
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT COUNT(pers)from ( "
				       + " SELECT isdp.patient_id AS pers FROM inventory_store_drug_patient isdp"
                       + " INNER JOIN inventory_store_drug_patient_detail isdpd ON isdp.id=isdpd.store_drug_patient_id"
                       + " INNER JOIN inventory_store_drug_transaction_detail isdt ON isdt.id=isdpd.transaction_detail_id "
                       + " INNER JOIN inventory_drug id ON id.id=isdt.drug_id "
                       + " INNER JOIN inventory_drug_category idc ON id.category_id=idc.id "
                       + " WHERE idc.name IN ('ANTI VERTIGO DRUGS')"
                       + " AND MONTH(isdt.created_on)="+ "'" + d0 + "'"
                       + " AND YEAR(isdt.created_on)= " + "'" + d1 + "'" 
                       + " GROUP BY isdp.patient_id "
                       + " )ambika " ;
		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithAntiVirals(Integer d0, String d1) {
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT COUNT(pers)from ( "
				       + " SELECT isdp.patient_id AS pers FROM inventory_store_drug_patient isdp"
                       + " INNER JOIN inventory_store_drug_patient_detail isdpd ON isdp.id=isdpd.store_drug_patient_id"
                       + " INNER JOIN inventory_store_drug_transaction_detail isdt ON isdt.id=isdpd.transaction_detail_id "
                       + " INNER JOIN inventory_drug id ON id.id=isdt.drug_id "
                       + " INNER JOIN inventory_drug_category idc ON id.category_id=idc.id "
                       + " WHERE idc.name IN ('ANTIVIRAL')"
                       + " AND MONTH(isdt.created_on)="+ "'" + d0 + "'"
                       + " AND YEAR(isdt.created_on)= " + "'" + d1 + "'" 
                       + " GROUP BY isdp.patient_id "
                       + " )ambika " ;
		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithCardiovasculardrugs(Integer d0, String d1) {
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT COUNT(pers)from ( "
				       + " SELECT isdp.patient_id AS pers FROM inventory_store_drug_patient isdp"
                       + " INNER JOIN inventory_store_drug_patient_detail isdpd ON isdp.id=isdpd.store_drug_patient_id"
                       + " INNER JOIN inventory_store_drug_transaction_detail isdt ON isdt.id=isdpd.transaction_detail_id "
                       + " INNER JOIN inventory_drug id ON id.id=isdt.drug_id "
                       + " INNER JOIN inventory_drug_category idc ON id.category_id=idc.id "
                       + " WHERE idc.name IN ('CARDIOVASCULAR DRUGS')"
                       + " AND MONTH(isdt.created_on)="+ "'" + d0 + "'"
                       + " AND YEAR(isdt.created_on)= " + "'" + d1 + "'" 
                       + " GROUP BY isdp.patient_id "
                       + " )ambika " ;
		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithContrastagents(Integer d0, String d1) {
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT COUNT(pers)from ( "
				       + " SELECT isdp.patient_id AS pers FROM inventory_store_drug_patient isdp"
                       + " INNER JOIN inventory_store_drug_patient_detail isdpd ON isdp.id=isdpd.store_drug_patient_id"
                       + " INNER JOIN inventory_store_drug_transaction_detail isdt ON isdt.id=isdpd.transaction_detail_id "
                       + " INNER JOIN inventory_drug id ON id.id=isdt.drug_id "
                       + " INNER JOIN inventory_drug_category idc ON id.category_id=idc.id "
                       + " WHERE idc.name IN ('CONTRAST AGENTS')"
                       + " AND MONTH(isdt.created_on)="+ "'" + d0 + "'"
                       + " AND YEAR(isdt.created_on)= " + "'" + d1 + "'" 
                       + " GROUP BY isdp.patient_id "
                       + " )ambika " ;
		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithDentalpreparation(Integer d0, String d1) {
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT COUNT(pers)from ( "
				       + " SELECT isdp.patient_id AS pers FROM inventory_store_drug_patient isdp"
                       + " INNER JOIN inventory_store_drug_patient_detail isdpd ON isdp.id=isdpd.store_drug_patient_id"
                       + " INNER JOIN inventory_store_drug_transaction_detail isdt ON isdt.id=isdpd.transaction_detail_id "
                       + " INNER JOIN inventory_drug id ON id.id=isdt.drug_id "
                       + " INNER JOIN inventory_drug_category idc ON id.category_id=idc.id "
                       + " WHERE idc.name IN ('DERMATOLOGICAL OINTMENT AND CREAMS')"
                       + " AND MONTH(isdt.created_on)="+ "'" + d0 + "'"
                       + " AND YEAR(isdt.created_on)= " + "'" + d1 + "'" 
                       + " GROUP BY isdp.patient_id "
                       + " )ambika " ;
		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithDermatologicalointmentcreams(Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT COUNT(pers)from ( "
				       + " SELECT isdp.patient_id AS pers FROM inventory_store_drug_patient isdp"
                       + " INNER JOIN inventory_store_drug_patient_detail isdpd ON isdp.id=isdpd.store_drug_patient_id"
                       + " INNER JOIN inventory_store_drug_transaction_detail isdt ON isdt.id=isdpd.transaction_detail_id "
                       + " INNER JOIN inventory_drug id ON id.id=isdt.drug_id "
                       + " INNER JOIN inventory_drug_category idc ON id.category_id=idc.id "
                       + " WHERE idc.name IN ('ANALGESICS AND ANTIPYRETIC DRUGS')"
                       + " AND MONTH(isdt.created_on)="+ "'" + d0 + "'"
                       + " AND YEAR(isdt.created_on)= " + "'" + d1 + "'" 
                       + " GROUP BY isdp.patient_id "
                       + " )ambika " ;
		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithDiuretics(Integer d0, String d1) {
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT COUNT(pers)from ( "
				       + " SELECT isdp.patient_id AS pers FROM inventory_store_drug_patient isdp"
                       + " INNER JOIN inventory_store_drug_patient_detail isdpd ON isdp.id=isdpd.store_drug_patient_id"
                       + " INNER JOIN inventory_store_drug_transaction_detail isdt ON isdt.id=isdpd.transaction_detail_id "
                       + " INNER JOIN inventory_drug id ON id.id=isdt.drug_id "
                       + " INNER JOIN inventory_drug_category idc ON id.category_id=idc.id "
                       + " WHERE idc.name IN ('DIURETICS')"
                       + " AND MONTH(isdt.created_on)="+ "'" + d0 + "'"
                       + " AND YEAR(isdt.created_on)= " + "'" + d1 + "'" 
                       + " GROUP BY isdp.patient_id "
                       + " )ambika " ;
		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithDrugsactingRespiratory(Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT COUNT(pers)from ( "
				       + " SELECT isdp.patient_id AS pers FROM inventory_store_drug_patient isdp"
                       + " INNER JOIN inventory_store_drug_patient_detail isdpd ON isdp.id=isdpd.store_drug_patient_id"
                       + " INNER JOIN inventory_store_drug_transaction_detail isdt ON isdt.id=isdpd.transaction_detail_id "
                       + " INNER JOIN inventory_drug id ON id.id=isdt.drug_id "
                       + " INNER JOIN inventory_drug_category idc ON id.category_id=idc.id "
                       + " WHERE idc.name IN ('DRUGS ACTING ON RESPIRATORY SYSTEM')"
                       + " AND MONTH(isdt.created_on)="+ "'" + d0 + "'"
                       + " AND YEAR(isdt.created_on)= " + "'" + d1 + "'" 
                       + " GROUP BY isdp.patient_id "
                       + " )ambika " ;
		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithDrugscoagulation(Integer d0, String d1) {
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT COUNT(pers)from ( "
				       + " SELECT isdp.patient_id AS pers FROM inventory_store_drug_patient isdp"
                       + " INNER JOIN inventory_store_drug_patient_detail isdpd ON isdp.id=isdpd.store_drug_patient_id"
                       + " INNER JOIN inventory_store_drug_transaction_detail isdt ON isdt.id=isdpd.transaction_detail_id "
                       + " INNER JOIN inventory_drug id ON id.id=isdt.drug_id "
                       + " INNER JOIN inventory_drug_category idc ON id.category_id=idc.id "
                       + " WHERE idc.name IN ('DRUGS AFFECTING COAGULATION')"
                       + " AND MONTH(isdt.created_on)="+ "'" + d0 + "'"
                       + " AND YEAR(isdt.created_on)= " + "'" + d1 + "'" 
                       + " GROUP BY isdp.patient_id "
                       + " )ambika " ;
		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithDrugsGouRheumatoid(Integer d0, String d1) {
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT COUNT(pers)from ( "
				       + " SELECT isdp.patient_id AS pers FROM inventory_store_drug_patient isdp"
                       + " INNER JOIN inventory_store_drug_patient_detail isdpd ON isdp.id=isdpd.store_drug_patient_id"
                       + " INNER JOIN inventory_store_drug_transaction_detail isdt ON isdt.id=isdpd.transaction_detail_id "
                       + " INNER JOIN inventory_drug id ON id.id=isdt.drug_id "
                       + " INNER JOIN inventory_drug_category idc ON id.category_id=idc.id "
                       + " WHERE idc.name IN ('DRUGS FOR GOUT AND RHEUMATOID DISORDERS')"
                       + " AND MONTH(isdt.created_on)="+ "'" + d0 + "'"
                       + " AND YEAR(isdt.created_on)= " + "'" + d1 + "'" 
                       + " GROUP BY isdp.patient_id "
                       + " )ambika " ;
		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithDrugsMigraine(Integer d0, String d1) {
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT COUNT(pers)from ( "
				       + " SELECT isdp.patient_id AS pers FROM inventory_store_drug_patient isdp"
                       + " INNER JOIN inventory_store_drug_patient_detail isdpd ON isdp.id=isdpd.store_drug_patient_id"
                       + " INNER JOIN inventory_store_drug_transaction_detail isdt ON isdt.id=isdpd.transaction_detail_id "
                       + " INNER JOIN inventory_drug id ON id.id=isdt.drug_id "
                       + " INNER JOIN inventory_drug_category idc ON id.category_id=idc.id "
                       + " WHERE idc.name IN ('DRUGS FOR MIGRAINE')"
                       + " AND MONTH(isdt.created_on)="+ "'" + d0 + "'"
                       + " AND YEAR(isdt.created_on)= " + "'" + d1 + "'" 
                       + " GROUP BY isdp.patient_id "
                       + " )ambika " ;
		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithGastrointestinal(Integer d0, String d1) {
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT COUNT(pers)from ( "
				       + " SELECT isdp.patient_id AS pers FROM inventory_store_drug_patient isdp"
                       + " INNER JOIN inventory_store_drug_patient_detail isdpd ON isdp.id=isdpd.store_drug_patient_id"
                       + " INNER JOIN inventory_store_drug_transaction_detail isdt ON isdt.id=isdpd.transaction_detail_id "
                       + " INNER JOIN inventory_drug id ON id.id=isdt.drug_id "
                       + " INNER JOIN inventory_drug_category idc ON id.category_id=idc.id "
                       + " WHERE idc.name IN ('GASTROINTESTINAL DRUGS')"
                       + " AND MONTH(isdt.created_on)="+ "'" + d0 + "'"
                       + " AND YEAR(isdt.created_on)= " + "'" + d1 + "'" 
                       + " GROUP BY isdp.patient_id "
                       + " )ambika " ;
		return jdbcTemplate.queryForInt(query);
	}
	public Integer getNoOfPatientWithAnaesthetics(Integer d0, String d1) {
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT COUNT(pers)from ( "
				       + " SELECT isdp.patient_id AS pers FROM inventory_store_drug_patient isdp"
                       + " INNER JOIN inventory_store_drug_patient_detail isdpd ON isdp.id=isdpd.store_drug_patient_id"
                       + " INNER JOIN inventory_store_drug_transaction_detail isdt ON isdt.id=isdpd.transaction_detail_id "
                       + " INNER JOIN inventory_drug id ON id.id=isdt.drug_id "
                       + " INNER JOIN inventory_drug_category idc ON id.category_id=idc.id "
                       + " WHERE idc.name IN ('ANAESTHETICS')"
                       + " AND MONTH(isdt.created_on)="+ "'" + d0 + "'"
                       + " AND YEAR(isdt.created_on)= " + "'" + d1 + "'" 
                       + " GROUP BY isdp.patient_id "
                       + " )ambika " ;
		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithHormoneEndocrine(Integer d0, String d1) {
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT COUNT(pers)from ( "
				       + " SELECT isdp.patient_id AS pers FROM inventory_store_drug_patient isdp"
                       + " INNER JOIN inventory_store_drug_patient_detail isdpd ON isdp.id=isdpd.store_drug_patient_id"
                       + " INNER JOIN inventory_store_drug_transaction_detail isdt ON isdt.id=isdpd.transaction_detail_id "
                       + " INNER JOIN inventory_drug id ON id.id=isdt.drug_id "
                       + " INNER JOIN inventory_drug_category idc ON id.category_id=idc.id "
                       + " WHERE idc.name IN ('HORMONE AND OTHER ENDOCRINE DRUGS')"
                       + " AND MONTH(isdt.created_on)="+ "'" + d0 + "'"
                       + " AND YEAR(isdt.created_on)= " + "'" + d1 + "'" 
                       + " GROUP BY isdp.patient_id "
                       + " )ambika " ;
		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithImmunologicals(Integer d0, String d1) {
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT COUNT(pers)from ( "
				       + " SELECT isdp.patient_id AS pers FROM inventory_store_drug_patient isdp"
                       + " INNER JOIN inventory_store_drug_patient_detail isdpd ON isdp.id=isdpd.store_drug_patient_id"
                       + " INNER JOIN inventory_store_drug_transaction_detail isdt ON isdt.id=isdpd.transaction_detail_id "
                       + " INNER JOIN inventory_drug id ON id.id=isdt.drug_id "
                       + " INNER JOIN inventory_drug_category idc ON id.category_id=idc.id "
                       + " WHERE idc.name IN ('IMMUNOLOGICALS')"
                       + " AND MONTH(isdt.created_on)="+ "'" + d0 + "'"
                       + " AND YEAR(isdt.created_on)= " + "'" + d1 + "'" 
                       + " GROUP BY isdp.patient_id "
                       + " )ambika " ;
		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithLifeSaving(Integer d0, String d1) {
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT COUNT(pers)from ( "
				       + " SELECT isdp.patient_id AS pers FROM inventory_store_drug_patient isdp"
                       + " INNER JOIN inventory_store_drug_patient_detail isdpd ON isdp.id=isdpd.store_drug_patient_id"
                       + " INNER JOIN inventory_store_drug_transaction_detail isdt ON isdt.id=isdpd.transaction_detail_id "
                       + " INNER JOIN inventory_drug id ON id.id=isdt.drug_id "
                       + " INNER JOIN inventory_drug_category idc ON id.category_id=idc.id "
                       + " WHERE idc.name IN ('LIFE SAVING DRUGS')"
                       + " AND MONTH(isdt.created_on)="+ "'" + d0 + "'"
                       + " AND YEAR(isdt.created_on)= " + "'" + d1 + "'" 
                       + " GROUP BY isdp.patient_id "
                       + " )ambika " ;
		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithMetabolism(Integer d0, String d1) {
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT COUNT(pers)from ( "
				       + " SELECT isdp.patient_id AS pers FROM inventory_store_drug_patient isdp"
                       + " INNER JOIN inventory_store_drug_patient_detail isdpd ON isdp.id=isdpd.store_drug_patient_id"
                       + " INNER JOIN inventory_store_drug_transaction_detail isdt ON isdt.id=isdpd.transaction_detail_id "
                       + " INNER JOIN inventory_drug id ON id.id=isdt.drug_id "
                       + " INNER JOIN inventory_drug_category idc ON id.category_id=idc.id "
                       + " WHERE idc.name IN ('METABOLISM (HYPOLIPIDAEMIC AGENTS)')"
                       + " AND MONTH(isdt.created_on)="+ "'" + d0 + "'"
                       + " AND YEAR(isdt.created_on)= " + "'" + d1 + "'" 
                       + " GROUP BY isdp.patient_id "
                       + " )ambika " ;
		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithMucolytic(Integer d0, String d1) {
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT COUNT(pers)from ( "
				       + " SELECT isdp.patient_id AS pers FROM inventory_store_drug_patient isdp"
                       + " INNER JOIN inventory_store_drug_patient_detail isdpd ON isdp.id=isdpd.store_drug_patient_id"
                       + " INNER JOIN inventory_store_drug_transaction_detail isdt ON isdt.id=isdpd.transaction_detail_id "
                       + " INNER JOIN inventory_drug id ON id.id=isdt.drug_id "
                       + " INNER JOIN inventory_drug_category idc ON id.category_id=idc.id "
                       + " WHERE idc.name IN ('MUCOLYTIC, PROTEOLYTIC AND OTHER ENZYMES')"
                       + " AND MONTH(isdt.created_on)="+ "'" + d0 + "'"
                       + " AND YEAR(isdt.created_on)= " + "'" + d1 + "'" 
                       + " GROUP BY isdp.patient_id "
                       + " )ambika " ;
		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithAntiCholinesterases(Integer d0, String d1) {
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT COUNT(pers)from ( "
				       + " SELECT isdp.patient_id AS pers FROM inventory_store_drug_patient isdp"
                       + " INNER JOIN inventory_store_drug_patient_detail isdpd ON isdp.id=isdpd.store_drug_patient_id"
                       + " INNER JOIN inventory_store_drug_transaction_detail isdt ON isdt.id=isdpd.transaction_detail_id "
                       + " INNER JOIN inventory_drug id ON id.id=isdt.drug_id "
                       + " INNER JOIN inventory_drug_category idc ON id.category_id=idc.id "
                       + " WHERE idc.name IN ('MUSCLE RELAXANT AND ANTICHOLINESTERASES')"
                       + " AND MONTH(isdt.created_on)="+ "'" + d0 + "'"
                       + " AND YEAR(isdt.created_on)= " + "'" + d1 + "'" 
                       + " GROUP BY isdp.patient_id "
                       + " )ambika " ;
		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithOphthalmological(Integer d0, String d1) {
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT COUNT(pers)from ( "
				       + " SELECT isdp.patient_id AS pers FROM inventory_store_drug_patient isdp"
                       + " INNER JOIN inventory_store_drug_patient_detail isdpd ON isdp.id=isdpd.store_drug_patient_id"
                       + " INNER JOIN inventory_store_drug_transaction_detail isdt ON isdt.id=isdpd.transaction_detail_id "
                       + " INNER JOIN inventory_drug id ON id.id=isdt.drug_id "
                       + " INNER JOIN inventory_drug_category idc ON id.category_id=idc.id "
                       + " WHERE idc.name IN ('OPHTHALMOLOGICAL AND ENT PREPARATION')"
                       + " AND MONTH(isdt.created_on)="+ "'" + d0 + "'"
                       + " AND YEAR(isdt.created_on)= " + "'" + d1 + "'" 
                       + " GROUP BY isdp.patient_id "
                       + " )ambika " ;
		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithOpthalmic(Integer d0, String d1) {
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT COUNT(pers)from ( "
				       + " SELECT isdp.patient_id AS pers FROM inventory_store_drug_patient isdp"
                       + " INNER JOIN inventory_store_drug_patient_detail isdpd ON isdp.id=isdpd.store_drug_patient_id"
                       + " INNER JOIN inventory_store_drug_transaction_detail isdt ON isdt.id=isdpd.transaction_detail_id "
                       + " INNER JOIN inventory_drug id ON id.id=isdt.drug_id "
                       + " INNER JOIN inventory_drug_category idc ON id.category_id=idc.id "
                       + " WHERE idc.name IN ('OPTHALMIC DIAGNOSTIC AGENTS')"
                       + " AND MONTH(isdt.created_on)="+ "'" + d0 + "'"
                       + " AND YEAR(isdt.created_on)= " + "'" + d1 + "'" 
                       + " GROUP BY isdp.patient_id "
                       + " )ambika " ;
		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithOxytocics(Integer d0, String d1) {
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT COUNT(pers)from ( "
				       + " SELECT isdp.patient_id AS pers FROM inventory_store_drug_patient isdp"
                       + " INNER JOIN inventory_store_drug_patient_detail isdpd ON isdp.id=isdpd.store_drug_patient_id"
                       + " INNER JOIN inventory_store_drug_transaction_detail isdt ON isdt.id=isdpd.transaction_detail_id "
                       + " INNER JOIN inventory_drug id ON id.id=isdt.drug_id "
                       + " INNER JOIN inventory_drug_category idc ON id.category_id=idc.id "
                       + " WHERE idc.name IN ('OXYTOCICS AND ANTIOXYTOCICS')"
                       + " AND MONTH(isdt.created_on)="+ "'" + d0 + "'"
                       + " AND YEAR(isdt.created_on)= " + "'" + d1 + "'" 
                       + " GROUP BY isdp.patient_id "
                       + " )ambika " ;
		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithPsychotherapeutic(Integer d0, String d1) {
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT COUNT(pers)from ( "
				       + " SELECT isdp.patient_id AS pers FROM inventory_store_drug_patient isdp"
                       + " INNER JOIN inventory_store_drug_patient_detail isdpd ON isdp.id=isdpd.store_drug_patient_id"
                       + " INNER JOIN inventory_store_drug_transaction_detail isdt ON isdt.id=isdpd.transaction_detail_id "
                       + " INNER JOIN inventory_drug id ON id.id=isdt.drug_id "
                       + " INNER JOIN inventory_drug_category idc ON id.category_id=idc.id "
                       + " WHERE idc.name IN ('PSYCHOTHERAPEUTIC DRUGS')"
                       + " AND MONTH(isdt.created_on)="+ "'" + d0 + "'"
                       + " AND YEAR(isdt.created_on)= " + "'" + d1 + "'" 
                       + " GROUP BY isdp.patient_id "
                       + " )ambika " ;
		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithElectrolyte(Integer d0, String d1) {
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT COUNT(pers)from ( "
				       + " SELECT isdp.patient_id AS pers FROM inventory_store_drug_patient isdp"
                       + " INNER JOIN inventory_store_drug_patient_detail isdpd ON isdp.id=isdpd.store_drug_patient_id"
                       + " INNER JOIN inventory_store_drug_transaction_detail isdt ON isdt.id=isdpd.transaction_detail_id "
                       + " INNER JOIN inventory_drug id ON id.id=isdt.drug_id "
                       + " INNER JOIN inventory_drug_category idc ON id.category_id=idc.id "
                       + " WHERE idc.name IN ('SOLUTION CORRECTING WATER AND ELECTROLYTE')"
                       + " AND MONTH(isdt.created_on)="+ "'" + d0 + "'"
                       + " AND YEAR(isdt.created_on)= " + "'" + d1 + "'" 
                       + " GROUP BY isdp.patient_id "
                       + " )ambika " ;
		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithParenteral(Integer d0, String d1) {
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT COUNT(pers)from ( "
				       + " SELECT isdp.patient_id AS pers FROM inventory_store_drug_patient isdp"
                       + " INNER JOIN inventory_store_drug_patient_detail isdpd ON isdp.id=isdpd.store_drug_patient_id"
                       + " INNER JOIN inventory_store_drug_transaction_detail isdt ON isdt.id=isdpd.transaction_detail_id "
                       + " INNER JOIN inventory_drug id ON id.id=isdt.drug_id "
                       + " INNER JOIN inventory_drug_category idc ON id.category_id=idc.id "
                       + " WHERE idc.name IN ('SOLUTIONS FOR PARENTERAL NUTRITION')"
                       + " AND MONTH(isdt.created_on)="+ "'" + d0 + "'"
                       + " AND YEAR(isdt.created_on)= " + "'" + d1 + "'" 
                       + " GROUP BY isdp.patient_id "
                       + " )ambika " ;
		return jdbcTemplate.queryForInt(query);
	}

	public Integer getNoOfPatientWithVitamins(Integer d0, String d1) {
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String query = " SELECT COUNT(pers)from ( "
				       + " SELECT isdp.patient_id AS pers FROM inventory_store_drug_patient isdp"
                       + " INNER JOIN inventory_store_drug_patient_detail isdpd ON isdp.id=isdpd.store_drug_patient_id"
                       + " INNER JOIN inventory_store_drug_transaction_detail isdt ON isdt.id=isdpd.transaction_detail_id "
                       + " INNER JOIN inventory_drug id ON id.id=isdt.drug_id "
                       + " INNER JOIN inventory_drug_category idc ON id.category_id=idc.id "
                       + " WHERE idc.name IN ('VITAMINS AND MINERALS')"
                       + " AND MONTH(isdt.created_on)="+ "'" + d0 + "'"
                       + " AND YEAR(isdt.created_on)= " + "'" + d1 + "'" 
                       + " GROUP BY isdp.patient_id "
                       + " )ambika " ;
		return jdbcTemplate.queryForInt(query);
	}
	
	
	
	SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

	SimpleDateFormat formatterExt = new SimpleDateFormat("dd/MM/yyyy");

	
	public List<Obs> listObsGroup(Integer personId, Integer conceptId,
			Integer min, Integer max) throws DAOException {
		Criteria criteria = sessionFactory.getCurrentSession()
				.createCriteria(Obs.class, "obs")
				.add(Restrictions.eq("obs.person.personId", personId))
				.add(Restrictions.eq("obs.concept.conceptId", conceptId))
				.add(Restrictions.isNull("obs.obsGroup"))
				.addOrder(Order.desc("obs.dateCreated"));
		if (max > 0) {
			criteria.setFirstResult(min).setMaxResults(max);
		}
		List<Obs> list = criteria.list();
		return list;
	}

	public Obs getObsGroupCurrentDate(Integer personId, Integer conceptId)
			throws DAOException {
		Criteria criteria = sessionFactory.getCurrentSession()
				.createCriteria(Obs.class, "obs")
				.add(Restrictions.eq("obs.person.personId", personId))
				.add(Restrictions.eq("obs.concept.conceptId", conceptId))
				.add(Restrictions.isNull("obs.obsGroup"));
		String date = formatterExt.format(new Date());
		String startFromDate = date + " 00:00:00";
		String endFromDate = date + " 23:59:59";
		try {
			criteria.add(Restrictions.and(
					Restrictions.ge("obs.dateCreated",
							formatter.parse(startFromDate)),
					Restrictions.le("obs.dateCreated",
							formatter.parse(endFromDate))));
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error convert date: " + e.toString());
			e.printStackTrace();
		}

		List<Obs> list = criteria.list();
		return CollectionUtils.isNotEmpty(list) ? list.get(0) : null;
	}

	public Integer buildConcepts(List<ConceptModel> conceptModels) {

		HospitalCoreService hcs = Context.getService(HospitalCoreService.class);
		Session session = sessionFactory.getCurrentSession();
		Integer diagnosisNo = 0;
		// Transaction tx = session.beginTransaction();
		// tx.begin();
		for (int i = 0; i < conceptModels.size(); i++) {
			ConceptModel conceptModel = conceptModels.get(i);
			Concept concept = hcs.insertConcept(
					conceptModel.getConceptDatatype(),
					conceptModel.getConceptClass(), conceptModel.getName(), "",
					conceptModel.getDescription());
			System.out.println("concept ==> " + concept.getId());
			for (String synonym : conceptModel.getSynonyms()) {
				hcs.insertSynonym(concept, synonym);
			}

			for (Mapping mapping : conceptModel.getMappings()) {
				hcs.insertMapping(concept, mapping.getSource(),
						mapping.getSourceCode());
			}

			if (i % 20 == 0) {
				session.flush();
				session.clear();
				System.out.println("Imported " + (i + 1) + " diagnosis ("
						+ (i / conceptModels.size() * 100) + "%)");
			}
			diagnosisNo++;
		}
		return diagnosisNo;
		// tx.commit();
	}

	public List<Patient> searchPatient(String nameOrIdentifier, String gender,
			int age, int rangeAge, String date, int rangeDay,
			String relativeName) throws DAOException {
		List<Patient> patients = new Vector<Patient>();

		String hql = "SELECT DISTINCT p.patient_id,pi.identifier,pn.given_name ,pn.middle_name ,pn.family_name ,ps.gender,ps.birthdate ,EXTRACT(YEAR FROM (FROM_DAYS(DATEDIFF(NOW(),ps.birthdate)))) age,pn.person_name_id FROM patient p "
				+ "INNER JOIN person ps ON p.patient_id = ps.person_id "
				+ "INNER JOIN patient_identifier pi ON p.patient_id = pi.patient_id "
				+ "INNER JOIN person_name pn ON p.patient_id = pn.person_id "
				+ "INNER JOIN person_attribute pa ON p.patient_id= pa.person_id "
				+ "INNER JOIN person_attribute_type pat ON pa.person_attribute_type_id = pat.person_attribute_type_id "
				+ "WHERE (pi.identifier like '%"
				+ nameOrIdentifier
				+ "%' "
				+ "OR pn.given_name like '"
				+ nameOrIdentifier
				+ "%' "
				+ "OR pn.middle_name like '"
				+ nameOrIdentifier
				+ "%' "
				+ "OR pn.family_name like '" + nameOrIdentifier + "%') ";
		if (StringUtils.isNotBlank(gender)) {
			hql += " AND ps.gender = '" + gender + "' ";
		}
		if (StringUtils.isNotBlank(relativeName)) {
			hql += " AND pat.name = 'Father/Husband Name' AND pa.value like '"
					+ relativeName + "' ";
		}
		if (StringUtils.isNotBlank(date)) {
			String startDate = DateUtils.getDateFromRange(date, -rangeDay)
					+ " 00:00:00";
			String endtDate = DateUtils.getDateFromRange(date, rangeDay)
					+ " 23:59:59";
			hql += " AND ps.birthdate BETWEEN '" + startDate + "' AND '"
					+ endtDate + "' ";
		}
		if (age > 0) {
			hql += " AND EXTRACT(YEAR FROM (FROM_DAYS(DATEDIFF(NOW(),ps.birthdate)))) >="
					+ (age - rangeAge)
					+ " AND EXTRACT(YEAR FROM (FROM_DAYS(DATEDIFF(NOW(),ps.birthdate)))) <= "
					+ (age + rangeAge) + " ";
		}
		hql += " ORDER BY p.patient_id ASC";

		Query query = sessionFactory.getCurrentSession().createSQLQuery(hql);
		List l = query.list();
		if (CollectionUtils.isNotEmpty(l))
			for (Object obj : l) {
				Object[] obss = (Object[]) obj;
				if (obss != null && obss.length > 0) {
					Person person = new Person((Integer) obss[0]);
					PersonName personName = new PersonName((Integer) obss[8]);

					personName.setGivenName((String) obss[2]);
					personName.setMiddleName((String) obss[3]);
					personName.setFamilyName((String) obss[4]);
					personName.setPerson(person);
					Set<PersonName> names = new HashSet<PersonName>();
					names.add(personName);
					person.setNames(names);
					Patient patient = new Patient(person);
					PatientIdentifier patientIdentifier = new PatientIdentifier();
					patientIdentifier.setPatient(patient);
					patientIdentifier.setIdentifier((String) obss[1]);
					Set<PatientIdentifier> identifier = new HashSet<PatientIdentifier>();
					identifier.add(patientIdentifier);
					patient.setIdentifiers(identifier);
					patient.setGender((String) obss[5]);
					patient.setBirthdate((Date) obss[6]);
					patients.add(patient);
				}

			}
		return patients;
	}

	@SuppressWarnings("rawtypes")
	public List<Patient> searchPatient(String hql) {
		List<Patient> patients = new Vector<Patient>();
		Query query = sessionFactory.getCurrentSession().createSQLQuery(hql);
		List list = query.list();
		if (CollectionUtils.isNotEmpty(list))
			for (Object obj : list) {
				Object[] obss = (Object[]) obj;
				if (obss != null && obss.length > 0) {
					Person person = new Person((Integer) obss[0]);
					PersonName personName = new PersonName((Integer) obss[8]);
					personName.setGivenName((String) obss[2]);
					personName.setMiddleName((String) obss[3]);
					personName.setFamilyName((String) obss[4]);
					personName.setPerson(person);
					Set<PersonName> names = new HashSet<PersonName>();
					names.add(personName);
					person.setNames(names);
					Patient patient = new Patient(person);
					PatientIdentifier patientIdentifier = new PatientIdentifier();
					patientIdentifier.setPatient(patient);
					patientIdentifier.setIdentifier((String) obss[1]);
					Set<PatientIdentifier> identifier = new HashSet<PatientIdentifier>();
					identifier.add(patientIdentifier);
					patient.setIdentifiers(identifier);
					patient.setGender((String) obss[5]);
					patient.setBirthdate((Date) obss[6]);
					// ghanshyam,22-oct-2013,New Requirement #2940 Dealing with
					// dead patient
					if (obss.length > 9) {
						if (obss[9] != null) {
							if (obss[9].toString().equals("1")) {
								patient.setDead(true);
							} else if (obss[9].toString().equals("0")) {
								patient.setDead(false);
							}
						}
					}
					if (obss.length > 10) {
						if (obss[10] != null) {
							if (obss[10].toString().equals("1")) {
								patient.setVoided(true);
							} else if (obss[10].toString().equals("0")) {
								patient.setVoided(false);
							}
						}
					}
					patients.add(patient);
				}
			}
		return patients;
	}

	@SuppressWarnings("rawtypes")
	public BigInteger getPatientSearchResultCount(String hql) {
		BigInteger count = new BigInteger("0");
		Query query = sessionFactory.getCurrentSession().createSQLQuery(hql);
		List list = query.list();
		if (CollectionUtils.isNotEmpty(list)) {
			count = (BigInteger) list.get(0);
		}
		return count;
	}

	@SuppressWarnings("rawtypes")
	public List<PersonAttribute> getPersonAttributes(Integer patientId) {
		List<PersonAttribute> attributes = new ArrayList<PersonAttribute>();
		String hql = "SELECT pa.person_attribute_type_id, pa.`value` FROM person_attribute pa WHERE pa.person_id = "
				+ patientId + " AND pa.voided = 0;";
		Query query = sessionFactory.getCurrentSession().createSQLQuery(hql);
		List l = query.list();
		if (CollectionUtils.isNotEmpty(l)) {
			for (Object obj : l) {
				Object[] obss = (Object[]) obj;
				if (obss != null && obss.length > 0) {
					PersonAttribute attribute = new PersonAttribute();
					PersonAttributeType type = new PersonAttributeType(
							(Integer) obss[0]);
					attribute.setAttributeType(type);
					attribute.setValue((String) obss[1]);
					attributes.add(attribute);
				}
			}
		}

		return attributes;
	}

	public Encounter getLastVisitEncounter(Patient patient,
			List<EncounterType> types) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				Encounter.class);
		criteria.add(Restrictions.eq("patient", patient));
		criteria.add(Restrictions.in("encounterType", types));
		criteria.addOrder(Order.desc("encounterDatetime"));
		criteria.setFirstResult(0);
		criteria.setMaxResults(1);
		return (Encounter) criteria.uniqueResult();
	}

	//
	// CORE FORM
	//
	public CoreForm saveCoreForm(CoreForm form) {
		return (CoreForm) sessionFactory.getCurrentSession().merge(form);
	}

	public CoreForm getCoreForm(Integer id) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				CoreForm.class);
		criteria.add(Restrictions.eq("id", id));
		return (CoreForm) criteria.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	public List<CoreForm> getCoreForms(String conceptName) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				CoreForm.class);
		criteria.add(Restrictions.eq("conceptName", conceptName));
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	public List<CoreForm> getCoreForms() {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				CoreForm.class);
		return criteria.list();
	}

	public void deleteCoreForm(CoreForm form) {
		sessionFactory.getCurrentSession().delete(form);
	}

	//
	// PATIENT_SEARCH
	//
	public PatientSearch savePatientSearch(PatientSearch patientSearch) {
		return (PatientSearch) sessionFactory.getCurrentSession().merge(
				patientSearch);
	}

	/**
	 * @see org.openmrs.module.hospitalcore.db.HospitalCoreDAO#getLastVisitTime(int)
	 */
	public java.util.Date getLastVisitTime(Patient patient) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				Encounter.class);
		Encounter encounter = new Encounter();
		criteria.add(Restrictions.eq("patient", patient));

		// Don't trust in system hour so we use encounterId (auto increase)
		criteria.addOrder(Order.desc("encounterId"));

		// return 1 last row
		criteria.setFirstResult(0); // read the first row (desc reading)
		criteria.setMaxResults(1); // return 1 row

		encounter = (Encounter) criteria.uniqueResult();
		return (java.util.Date) (encounter == null ? null : encounter
				.getEncounterDatetime());
	}

	// ghanshyam,22-oct-2013,New Requirement #2940 Dealing with dead patient
	public PatientSearch getPatient(int patientID) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				PatientSearch.class);
		criteria.add(Restrictions.eq("patientId", patientID));
		return (PatientSearch) criteria.uniqueResult();
	}

	/*
	 * public List<Patient> getAllEncounterCurrentDate(String date,
	 * Set<EncounterType> encounterTypes) { Criteria criteria =
	 * sessionFactory.getCurrentSession().createCriteria( Encounter.class);
	 * 
	 * String startFromDate = date + " 00:00:00"; ; String endFromDate = date +
	 * " 23:59:59"; try { criteria.add(Restrictions.and(
	 * Restrictions.ge("encounterDatetime", formatter.parse(startFromDate)),
	 * Restrictions.le("encounterDatetime", formatter.parse(endFromDate))));
	 * criteria.add(Restrictions.in("encounterType", encounterTypes)); } catch
	 * (ParseException e) { e.printStackTrace(); } List<Encounter> enc =
	 * criteria.list(); List<Patient> dops = new ArrayList<Patient>(); for
	 * (Encounter o : enc) { Patient p = Context.getPatientService()
	 * .getPatient(o.getPatientId()); dops.add(p); } return dops;
	 * 
	 * }
	 */

	public Set<Encounter> getEncountersByPatientAndDate(String date,
			Set<EncounterType> encounterTypes) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				Encounter.class);
		String startFromDate = date + " 00:00:00";
		;
		String endFromDate = date + " 23:59:59";
		try {
			criteria.add(Restrictions.and(
					Restrictions.ge("encounterDatetime",
							formatter.parse(startFromDate)),
					Restrictions.le("encounterDatetime",
							formatter.parse(endFromDate))));
			criteria.add(Restrictions.in("encounterType", encounterTypes));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		List<Encounter> enc = criteria.list();
		Set<Encounter> dops = new LinkedHashSet<Encounter>();
		for (Encounter o : enc) {
			dops.add(o);

		}
		return dops;
	}

	public Set<Encounter> getEncountersByPatientAndDateFromObs(String date) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				Encounter.class);

		String startFromDate = date + " 00:00:00";

		String endFromDate = date + " 23:59:59";

		try {
			criteria.add(Restrictions.and(
					Restrictions.ge("encounterDatetime",
							formatter.parse(startFromDate)),
					Restrictions.le("encounterDatetime",
							formatter.parse(endFromDate))));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		List<Encounter> enc = criteria.list();
		Set<Encounter> dops = new LinkedHashSet<Encounter>();
		for (Encounter o : enc) {

			if (o.getEncounterType().getName().equals("IPDENCOUNTER")) {
				dops.add(o);

			}

		}
		return dops;
	}

	public List<Obs> getObsInstanceForDiagnosis(Encounter encounter,
			Concept concept) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				Obs.class);
		criteria.add(Restrictions.eq("encounter", encounter));
		criteria.add(Restrictions.eq("concept", concept));
		return criteria.list();
	}




}
