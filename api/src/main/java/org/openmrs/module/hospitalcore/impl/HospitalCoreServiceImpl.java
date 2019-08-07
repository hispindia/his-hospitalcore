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


package org.openmrs.module.hospitalcore.impl;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.TreeSet;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Concept;
import org.openmrs.ConceptAnswer;
import org.openmrs.ConceptClass;
import org.openmrs.ConceptDatatype;
import org.openmrs.ConceptDescription;
import org.openmrs.ConceptMap;
import org.openmrs.ConceptName;
import org.openmrs.ConceptNameTag;
import org.openmrs.ConceptSource;
import org.openmrs.Encounter;
import org.openmrs.EncounterType;
import org.openmrs.GlobalProperty;
import org.openmrs.Location;
import org.openmrs.Obs;
import org.openmrs.Patient;
import org.openmrs.PersonAttribute;
import org.openmrs.User;
import org.openmrs.api.APIException;
import org.openmrs.api.ConceptService;
import org.openmrs.api.context.Context;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.hospitalcore.HospitalCoreService;
import org.openmrs.module.hospitalcore.concept.ConceptModel;
import org.openmrs.module.hospitalcore.concept.Mapping;
import org.openmrs.module.hospitalcore.concept.Synonym;
import org.openmrs.module.hospitalcore.db.HospitalCoreDAO;
import org.openmrs.module.hospitalcore.model.CoreForm;
import org.openmrs.module.hospitalcore.model.PatientSearch;
import org.openmrs.module.hospitalcore.util.HospitalCoreConstants;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class HospitalCoreServiceImpl extends BaseOpenmrsService implements
		HospitalCoreService {

	private Log log = LogFactory.getLog(this.getClass());

	public HospitalCoreServiceImpl() {
	}

	protected HospitalCoreDAO dao;

	public List<Obs> listObsGroup(Integer personId, Integer conceptId,
			Integer min, Integer max) throws APIException {
		return dao.listObsGroup(personId, conceptId, min, max);
	}

	private Concept insertConcept(ConceptService conceptService,
			String dataTypeName, String conceptClassName, String concept) {
		try {
			ConceptDatatype datatype = Context.getConceptService()
					.getConceptDatatypeByName(dataTypeName);
			ConceptClass conceptClass = conceptService
					.getConceptClassByName(conceptClassName);
			Concept con = conceptService.getConcept(concept);
			if (con == null) {
				con = new Concept();
				ConceptName name = new ConceptName(concept, Context.getLocale());
				con.addName(name);
				con.setDatatype(datatype);
				con.setConceptClass(conceptClass);
				return conceptService.saveConcept(con);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public void creatConceptQuestionAndAnswer(ConceptService conceptService,
			User user, String conceptParent, String... conceptChild) {
		Concept concept = conceptService.getConcept(conceptParent);
		if (concept == null) {
			insertConcept(conceptService, "Coded", "Question", conceptParent);
		}
		if (concept != null) {

			for (String hn : conceptChild) {
				insertHospital(conceptService, hn);
			}
			addConceptAnswers(concept, conceptChild, user);
		}
	}

	private void addConceptAnswers(Concept concept, String[] answerNames,
			User creator) {
		Set<Integer> currentAnswerIds = new HashSet<Integer>();
		for (ConceptAnswer answer : concept.getAnswers()) {
			currentAnswerIds.add(answer.getAnswerConcept().getConceptId());
		}
		boolean changed = false;
		for (String answerName : answerNames) {
			Concept answer = Context.getConceptService().getConcept(answerName);
			if (!currentAnswerIds.contains(answer.getConceptId())) {
				changed = true;
				ConceptAnswer conceptAnswer = new ConceptAnswer(answer);
				conceptAnswer.setCreator(creator);
				concept.addAnswer(conceptAnswer);
			}
		}
		if (changed) {
			Context.getConceptService().saveConcept(concept);
		}
	}

	private Concept insertHospital(ConceptService conceptService,
			String hospitalName) {
		try {
			ConceptDatatype datatype = Context.getConceptService()
					.getConceptDatatypeByName("N/A");
			ConceptClass conceptClass = conceptService
					.getConceptClassByName("Misc");
			Concept con = conceptService.getConceptByName(hospitalName);
			// System.out.println(con);
			if (con == null) {
				con = new Concept();
				ConceptName name = new ConceptName(hospitalName,
						Context.getLocale());
				con.addName(name);
				con.setDatatype(datatype);
				con.setConceptClass(conceptClass);
				return conceptService.saveConcept(con);
			}
			return con;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public EncounterType insertEncounterTypeByKey(String type)
			throws APIException {
		EncounterType encounterType = null;
		try {
			GlobalProperty gp = Context.getAdministrationService()
					.getGlobalPropertyObject(type);
			encounterType = Context.getEncounterService().getEncounterType(
					gp.getPropertyValue());
			if (encounterType == null) {
				encounterType = new EncounterType(gp.getPropertyValue(), "");
				encounterType = Context.getEncounterService()
						.saveEncounterType(encounterType);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return encounterType;
	}

	/**
	 * 
	 * @param type
	 * @throws APIException
	 */
	/*
	 * public EncounterType insertEncounterTypeByKey(String type) throws
	 * APIException { EncounterType encounterType = null; try { GlobalProperty
	 * gp = Context.getAdministrationService().getGlobalPropertyObject(type);
	 * encounterType =
	 * Context.getEncounterService().getEncounterType(gp.getPropertyValue()); if
	 * (encounterType == null) { encounterType = new
	 * EncounterType(gp.getPropertyValue(), "");
	 * encounterType=Context.getEncounterService
	 * ().saveEncounterType(encounterType); } } catch (Exception e) {
	 * e.printStackTrace(); } return encounterType; }
	 * 
	 * public void addConceptAnswers(ConceptService conceptService, Concept
	 * concept, String[] answerNames, User creator) throws APIException {
	 * Set<Integer> currentAnswerIds = new HashSet<Integer>(); for
	 * (ConceptAnswer answer : concept.getAnswers()) {
	 * currentAnswerIds.add(answer.getAnswerConcept().getConceptId()); } boolean
	 * changed = false;
	 * 
	 * for (String answerName : answerNames) {
	 * System.out.println("======================== CJUE: "+answerName);
	 * insertAnswerConcept(Context.getConceptService(), answerName);
	 * System.out.println("========OUT================ CJUE "+answerName); }
	 * 
	 * for (String answerName : answerNames) {
	 * System.out.println("answerName in== : "+answerName); Concept answer =
	 * Context.getConceptService().getConcept(answerName); if
	 * (!currentAnswerIds.contains(answer.getConceptId())) { changed = true;
	 * System.out.println("co nhay vao day khong "+answer); ConceptAnswer
	 * conceptAnswer = new ConceptAnswer(answer);
	 * conceptAnswer.setCreator(creator); concept.addAnswer(conceptAnswer); } }
	 * if (changed) { System.out.println("nhay vao tao cai concept: ");
	 * Context.getConceptService().saveConcept(concept); } }
	 * 
	 * private Concept insertAnswerConcept(ConceptService conceptService, String
	 * hospitalName) { try { ConceptDatatype datatype =
	 * Context.getConceptService() .getConceptDatatypeByName("N/A");
	 * ConceptClass conceptClass = conceptService
	 * .getConceptClassByName("Misc"); Concept con =
	 * conceptService.getConceptByName(hospitalName); //
	 * System.out.println(con); if (con == null) { con = new Concept();
	 * ConceptName name = new ConceptName(hospitalName, Context.getLocale());
	 * con.addName(name); con.setDatatype(datatype);
	 * con.setConceptClass(conceptClass); return
	 * conceptService.saveConcept(con); } return con; } catch (Exception e) {
	 * e.printStackTrace(); } return null; }
	 * 
	 * public HospitalCoreDAO getDao() { return dao; }
	 * 
	 * public void setDao(HospitalCoreDAO dao) { this.dao = dao; }
	 *//**
	 * Insert a concept unless it exists
	 * 
	 * @param dataTypeName
	 * @param conceptClassName
	 * @param conceptName
	 * @return found concept or created
	 */
	/*
	 * public Concept insertConceptUnlessExist(String dataTypeName, String
	 * conceptClassName, String conceptName) { Concept con = null; try {
	 * ConceptService conceptService = Context.getConceptService();
	 * ConceptDatatype datatype =
	 * Context.getConceptService().getConceptDatatypeByName(dataTypeName);
	 * System.out.println("me datatype: "+datatype); ConceptClass conceptClass =
	 * conceptService .getConceptClassByName(conceptClassName);
	 * System.out.println("me conceptclass: "+conceptClass); con =
	 * conceptService.getConceptByName(conceptName);
	 * System.out.println("be4 con: "+con); if (con == null) { con = new
	 * Concept(); ConceptName name = new
	 * ConceptName(conceptName,Context.getLocale()); con.addName(name);
	 * con.setDatatype(datatype); con.setConceptClass(conceptClass);
	 * System.out.println("con after: datatype: "+con.getDatatype());
	 * System.out.println("con after: conceptClass: "+conceptClass);
	 * //con.setDateCreated(new Date()); Concept ccccc
	 * =conceptService.saveConcept(con); System.out.println("cccccc; "+ccccc);
	 * return ccccc; } } catch (Exception e) { e.printStackTrace(); } return
	 * con; }
	 */
	/**
	 * Create the global obs for a patient
	 * 
	 * @param patient
	 */
	public Concept insertConceptUnlessExist(String dataTypeName,
			String conceptClassName, String conceptName) throws APIException {
		Concept con = null;
		try {
			ConceptService conceptService = Context.getConceptService();
			ConceptDatatype datatype = Context.getConceptService()
					.getConceptDatatypeByName(dataTypeName);
			// System.out.println("me datatype: "+datatype);
			ConceptClass conceptClass = conceptService
					.getConceptClassByName(conceptClassName);
			// System.out.println("me conceptclass: "+conceptClass);
			con = conceptService.getConceptByName(conceptName);
			// System.out.println("be4 con: "+con);
			if (con == null) {
				con = new Concept();
				ConceptName name = new ConceptName(conceptName,
						Context.getLocale());
				con.addName(name);
				con.setDatatype(datatype);
				con.setConceptClass(conceptClass);
				// System.out.println("con after: datatype: "+con.getDatatype());
				// System.out.println("con after: conceptClass: "+conceptClass);
				// con.setDateCreated(new Date());
				Concept ccccc = conceptService.saveConcept(con);
				// System.out.println("cccccc; "+ccccc);
				return ccccc;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}

	public Obs createObsGroup(Patient patient, String properyKey) {
		String opdVisitConceptName = Context.getAdministrationService()
				.getGlobalProperty(properyKey);
		if (!StringUtils.isBlank(opdVisitConceptName)) {
			Concept concept = insertConceptUnlessExist("N/A", "Misc",
					opdVisitConceptName);
			Obs obs = new Obs();
			obs.setPatient(patient);
			obs.setConcept(concept);
			obs.setDateCreated(new Date());
			obs.setObsDatetime(new Date());
			obs.setLocation(new Location(1));
	//System.out.println("patient.getPersonName().getGivenName()"+patient.getPersonName().getGivenName());
			return Context.getObsService().saveObs(obs,
					"Global obs for " + patient.getPersonName().getGivenName());
		}
		return null;
	}

	/**
	 * Get global obs for a patient
	 * 
	 * @param patient
	 * @return
	 */
	public Obs getObsGroup(Patient patient) {
		String name = Context.getAdministrationService().getGlobalProperty(
				HospitalCoreConstants.PROPERTY_OBSGROUP);
		Obs obs = null;
		try {
			Concept concept = Context.getConceptService()
					.getConceptByName(name);
			List<Obs> obses = listObsGroup(patient.getPersonId(),
					concept.getConceptId(), 0, 1);
			obs = CollectionUtils.isEmpty(obses) ? null : obses.get(0);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return obs;
	}

	public Obs getObsGroupCurrentDate(Integer personId) throws APIException {
		String name = Context.getAdministrationService().getGlobalProperty(
				HospitalCoreConstants.PROPERTY_OBSGROUP);
		Concept concept = Context.getConceptService().getConceptByName(name);
		// TODO Auto-generated method stub
		return dao.getObsGroupCurrentDate(personId, concept.getConceptId());
	}

	public HospitalCoreDAO getDao() {
		return dao;
	}

	public void setDao(HospitalCoreDAO dao) {
		this.dao = dao;
	}

	/**
	 * Insert a synonym to an existing concept.
	 * 
	 * @param concept
	 * @param name
	 */
	public void insertSynonym(Concept concept, String name) {
		Locale loc = new Locale("en");
		ConceptName conceptName = new ConceptName(name, loc);
		ConceptNameTag tag = Context.getConceptService()
				.getConceptNameTagByName("synonym");
		conceptName.addTag(tag);
		conceptName.setDateCreated(new Date());
		conceptName.setCreator(Context.getAuthenticatedUser());
		concept.addName(conceptName);
		Context.getConceptService().saveConcept(concept);
	}

	/**
	 * Insert a synonym to an existing concept.
	 * 
	 * @param concept
	 * @param name
	 */
	public void insertMapping(Concept concept, String sourceName,
			String sourceCode) {
		ConceptSource conceptSource = Context.getConceptService()
				.getConceptSourceByName(sourceName);
		List<ConceptMap> conceptMaps = new ArrayList<ConceptMap>();
		conceptMaps.addAll(concept.getConceptMappings());

		boolean found = false;
		for (ConceptMap cm : concept.getConceptMappings()) {
			if (cm.getSource().equals(conceptSource))
				if (cm.getSourceCode().equalsIgnoreCase(sourceCode)) {
					found = true;
					break;
				}
		}

		if (!found) {
			ConceptMap conceptMap = new ConceptMap();
			conceptMap.setConcept(concept);
			conceptMap.setSource(conceptSource);
			conceptMap.setSourceCode(sourceCode);
			conceptMap.setDateCreated(new Date());
			conceptMap.setCreator(Context.getAuthenticatedUser());
			concept.addConceptMapping(conceptMap);
			Context.getConceptService().saveConcept(concept);
		}
	}

	public Concept insertConcept(String dataTypeName, String conceptClassName,
			String name, String shortname, String description)
			throws APIException {
		Concept con = null;
		try {
			ConceptService conceptService = Context.getConceptService();

			con = conceptService.getConceptByName(name);
			if (con == null) {
				con = new Concept();
				Locale loc = new Locale("en");

				// Add concept name
				con = addName(con, name.toUpperCase(), loc);

				// Add concept shortname
				con = addName(con, shortname.toUpperCase(), loc);

				// Add description
				ConceptDescription conceptDescription = new ConceptDescription(
						description, loc);
				con.addDescription(conceptDescription);

				// Add datatype
				ConceptDatatype conceptDatatype = Context.getConceptService()
						.getConceptDatatypeByName(dataTypeName);
				con.setDatatype(conceptDatatype);

				// add conceptClass
				ConceptClass conceptClass = conceptService
						.getConceptClassByName(conceptClassName);
				con.setConceptClass(conceptClass);

				return conceptService.saveConcept(con);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}

	private Concept addName(Concept concept, String name, Locale loc) {
		if (!StringUtils.isBlank(name)) {
			ConceptName conceptName = new ConceptName(name, loc);
			// ConceptNameTag tag = Context.getConceptService()
			// .getConceptNameTagByName(type);
			// conceptName.addTag(tag);
			conceptName.setDateCreated(new Date());
			conceptName.setCreator(Context.getAuthenticatedUser());
			concept.addName(conceptName);
		}
		return concept;
	}

	public Integer importConcepts(InputStream diagnosisStream,
			InputStream mappingStream, InputStream synonymStream)
			throws XPathExpressionException, ParserConfigurationException,
			SAXException, IOException {
		Integer diagnosisNo = 0;
		Set<ConceptModel> concepts = new HashSet<ConceptModel>();
		Set<Mapping> mapping = new HashSet<Mapping>();
		Set<Synonym> synonym = new HashSet<Synonym>();
		if (diagnosisStream != null) {
			concepts = parseDiagnosis(diagnosisStream);
		}
		if (mappingStream != null) {
			mapping = parseMapping(mappingStream);
		}
		if (synonymStream != null) {
			synonym = parseSynonym(synonymStream);
		}

		List<ConceptModel> conceptModels = merge(concepts, mapping, synonym);

		System.out.println("NUMBER OF CONCEPTS + " + conceptModels.size());
		diagnosisNo = dao.buildConcepts(conceptModels);
		return diagnosisNo;
	}

	private Set<ConceptModel> parseDiagnosis(InputStream stream)
			throws ParserConfigurationException, SAXException, IOException,
			XPathExpressionException {
		Set<ConceptModel> concepts = new TreeSet<ConceptModel>();
		DocumentBuilderFactory domFactory = DocumentBuilderFactory
				.newInstance();
		domFactory.setNamespaceAware(true);
		DocumentBuilder builder = domFactory.newDocumentBuilder();
		Document doc = builder.parse(stream);

		NodeList rows = doc.getFirstChild().getChildNodes();
		for (int i = 0; i < rows.getLength(); i++) {
			Node row = rows.item(i);
			if (row.getNodeName().equalsIgnoreCase("row")) {
				ConceptModel cm = new ConceptModel();
				NodeList fields = row.getChildNodes();
				for (int j = 0; j < fields.getLength(); j++) {
					Node field = fields.item(j);
					NamedNodeMap attributes = field.getAttributes();
					if (field.getNodeName().equalsIgnoreCase("field")) {
						String type = attributes.getNamedItem("name")
								.getTextContent();
						String value = field.getTextContent();

						if ("name".equalsIgnoreCase(type)) {
							cm.setName(value);
						}

						if ("description".equalsIgnoreCase(type)) {
							cm.setDescription(value);
						}
					}
				}
				concepts.add(cm);
			}
		}
		return concepts;
	}

	private Set<Mapping> parseMapping(InputStream stream)
			throws ParserConfigurationException, SAXException, IOException,
			XPathExpressionException {
		Set<Mapping> mappings = new TreeSet<Mapping>();
		DocumentBuilderFactory domFactory = DocumentBuilderFactory
				.newInstance();
		domFactory.setNamespaceAware(true);
		DocumentBuilder builder = domFactory.newDocumentBuilder();
		Document doc = builder.parse(stream);

		NodeList rows = doc.getFirstChild().getChildNodes();
		for (int i = 0; i < rows.getLength(); i++) {
			Node row = rows.item(i);
			if (row.getNodeName().equalsIgnoreCase("row")) {
				Mapping cm = new Mapping();
				NodeList fields = row.getChildNodes();
				for (int j = 0; j < fields.getLength(); j++) {
					Node field = fields.item(j);
					NamedNodeMap attributes = field.getAttributes();
					if (field.getNodeName().equalsIgnoreCase("field")) {
						String type = attributes.getNamedItem("name")
								.getTextContent();
						String value = field.getTextContent();

						if ("name".equalsIgnoreCase(type)) {
							cm.setName(value);
						}

						if ("source_code".equalsIgnoreCase(type)) {
							cm.setSourceCode(value);
						}

						if ("source_name".equalsIgnoreCase(type)) {
							cm.setSource(value);
						}
					}
				}
				mappings.add(cm);
			}
		}
		return mappings;
	}

	private Set<Synonym> parseSynonym(InputStream stream)
			throws ParserConfigurationException, SAXException, IOException,
			XPathExpressionException {
		Set<Synonym> synnonyms = new TreeSet<Synonym>();
		DocumentBuilderFactory domFactory = DocumentBuilderFactory
				.newInstance();
		domFactory.setNamespaceAware(true);
		DocumentBuilder builder = domFactory.newDocumentBuilder();
		Document doc = builder.parse(stream);

		NodeList rows = doc.getFirstChild().getChildNodes();
		for (int i = 0; i < rows.getLength(); i++) {
			Node row = rows.item(i);
			if (row.getNodeName().equalsIgnoreCase("row")) {
				Synonym cm = new Synonym();
				NodeList fields = row.getChildNodes();
				for (int j = 0; j < fields.getLength(); j++) {
					Node field = fields.item(j);
					NamedNodeMap attributes = field.getAttributes();
					if (field.getNodeName().equalsIgnoreCase("field")) {
						String type = attributes.getNamedItem("name")
								.getTextContent();
						String value = field.getTextContent();

						if ("concept".equalsIgnoreCase(type)) {
							cm.setName(value);
						}

						if ("synonym".equalsIgnoreCase(type)) {
							cm.setSynonym(value);
						}
					}
				}
				synnonyms.add(cm);
			}
		}
		return synnonyms;
	}

	private List<ConceptModel> merge(Set<ConceptModel> conceptSet,
			Set<Mapping> mappingSet, Set<Synonym> synonymSet) {
		List<ConceptModel> conceptList = new ArrayList<ConceptModel>();
		conceptList.addAll(conceptSet);
		for (Mapping mapping : mappingSet) {
			int index = indexOf(conceptList, mapping.getName());
			if (index >= 0) {
				ConceptModel concept = conceptList.get(index);
				concept.getMappings().add(mapping);
			}
		}

		for (Synonym synonym : synonymSet) {
			int index = indexOf(conceptList, synonym.getName());
			if (index >= 0) {
				ConceptModel concept = conceptList.get(index);
				concept.getSynonyms().add(synonym.getSynonym());
			}
		}

		return conceptList;
	}

	private int indexOf(List<ConceptModel> conceptList, String name) {
		ConceptModel concept = new ConceptModel();
		concept.setName(name);
		return Collections.binarySearch(conceptList, concept);
	}

	public List<Patient> searchPatient(String nameOrIdentifier, String gender,
			int age, int rangeAge, String date, int rangeDay,
			String relativeName) throws APIException {
		return dao.searchPatient(nameOrIdentifier, gender, age, rangeAge, date,
				rangeDay, relativeName);
	}

	public List<Patient> searchPatient(String hql) {
		return dao.searchPatient(hql);
	}

	public BigInteger getPatientSearchResultCount(String hql) {
		return dao.getPatientSearchResultCount(hql);
	}

	public List<PersonAttribute> getPersonAttributes(Integer patientId) {
		return dao.getPersonAttributes(patientId);
	}

	public Encounter getLastVisitEncounter(Patient patient,
			List<EncounterType> types) {
		return dao.getLastVisitEncounter(patient, types);
	}
	
	/**
	 * Save core form
	 * 
	 * @param form
	 * @return
	 */
	public CoreForm saveCoreForm(CoreForm form) {
		return dao.saveCoreForm(form);
	}

	/**
	 * Get core form by id
	 * 
	 * @param id
	 * @return
	 */
	public CoreForm getCoreForm(Integer id) {
		return dao.getCoreForm(id);
	}

	/**
	 * Get core forms by name
	 * 
	 * @param conceptName
	 * @return
	 */
	public List<CoreForm> getCoreForms(String conceptName) {
		return dao.getCoreForms(conceptName);
	}

	/**
	 * Get all core forms
	 * 
	 * @return
	 */
	public List<CoreForm> getCoreForms(){
		return dao.getCoreForms();
	}

	/**
	 * Delete core form
	 * 
	 * @param form
	 */
	public void deleteCoreForm(CoreForm form){
		dao.deleteCoreForm(form);
	}
	
	/**
	 * Save patientSearch
	 */
	public PatientSearch savePatientSearch(PatientSearch patientSearch){
		return dao.savePatientSearch(patientSearch);
	}

	/**
	 * 
	 * @see org.openmrs.module.hospitalcore.HospitalCoreService#getLastVisitTime(int)
	 */
	public java.util.Date getLastVisitTime(Patient patientID) {
	    return dao.getLastVisitTime(patientID);
    }
	
	//ghanshyam,22-oct-2013,New Requirement #2940 Dealing with dead patient
	public PatientSearch getPatient(int patientID){
		return dao.getPatient(patientID);
	}

	/*public List<Patient> getAllEncounterCurrentDate(String date,Set<EncounterType> encounterTypes) {
		// TODO Auto-generated method stub
		return dao.getAllEncounterCurrentDate(date,encounterTypes);
	}*/
	
	public Set<Encounter> getEncountersByPatientAndDate(String date,Set<EncounterType> encounterTypes){
		return dao.getEncountersByPatientAndDate(date,encounterTypes);
	}
	public Set<Encounter> getEncountersByPatientAndDateFromObs(String date){
		return dao.getEncountersByPatientAndDateFromObs(date);
	}
	public List<Obs> getObsInstanceForDiagnosis(Encounter encounter,Concept concept){
		return dao.getObsInstanceForDiagnosis(encounter,concept);
	}


	public Integer getNoOfPatientWithDogBite(String gender,Integer d0,String d1)
	{
		return dao.getNoOfPatientWithDogBite(gender,d0,d1);
	}

	public Integer getNoOfPatientWithOtherBite(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithOtherBite(gender,d0,d1);
	}

	public Integer getNoOfPatientWithSnakeBite(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithSnakeBite(gender,d0,d1);
	}

	public Integer getNoOfPatientWithRheumatic(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithRheumatic(gender,d0,d1);
	}

	public Integer getNoOfPatientWithBigemany(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithBigemany(gender,d0,d1);
	}

	public Integer getNoOfPatientWithCardiac(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithCardiac(gender,d0,d1);
	}

	public Integer getNoOfPatientWithVenous(String gender, Integer d0, String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithVenous(gender,d0,d1);
	}

	public Integer getNoOfPatientWithHypertension(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithHypertension(gender,d0,d1);
	}

	public Integer getNoOfPatientWithIschemic(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithIschemic(gender,d0,d1);
	}

	public Integer getNoOfPatientWithRheumaticHeart(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithRheumaticHeart(gender,d0,d1);
	}
	////////////////////////////////////////////////////////////////////////////////////////
	
	public Integer getNoOfPatientWithVericoseVein(String gender,Integer d0,String d1)
	{
		return dao.getNoOfPatientWithVericoseVein(gender,d0,d1);
	}

	public Integer getNoOfPatientWithOtherCardiovascular(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithOtherCardiovascular(gender,d0,d1);
	}

	public Integer getNoOfPatientWithLymphadenitis(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithLymphadenitis(gender,d0,d1);
	}

	public Integer getNoOfPatientWithCerebralPalsy(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithCerebralPalsy(gender,d0,d1);
	}

	public Integer getNoOfPatientWithEncephalitis(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithEncephalitis(gender,d0,d1);
	}

	public Integer getNoOfPatientWithEpilepsy(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithEpilepsy(gender,d0,d1);
	}

	public Integer getNoOfPatientWithHemiparesis(String gender, Integer d0, String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithHemiparesis(gender,d0,d1);
	}

	public Integer getNoOfPatientWithHydrocephalus(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithHydrocephalus(gender,d0,d1);
	}

	public Integer getNoOfPatientWithInsomnia(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithInsomnia(gender,d0,d1);
	}

	public Integer getNoOfPatientWithMigraine(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithMigraine(gender,d0,d1);
	}

	public Integer getNoOfPatientWithOtherNervous(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithOtherNervous(gender,d0,d1);
	}

	public Integer getNoOfPatientWithParaplegia(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithOtherNervous(gender,d0,d1);
	}

	public Integer getNoOfPatientWithParkinsonism(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithParkinsonism(gender,d0,d1);
	}

	public Integer getNoOfPatientWithCerebro(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithCerebro(gender,d0,d1);
	}

	public Integer getNoOfPatientWithVertigo(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithVertigo(gender,d0,d1);
	}

	public Integer getNoOfPatientWithCongenitalDisorders(String gender,
			Integer d0, String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithCongenitalDisorders(gender,d0,d1);
	}

	public Integer getNoOfPatientWithCongenitalHeart(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithCongenitalHeart(gender,d0,d1);
	}

	public Integer getNoOfPatientWithDental(String gender, Integer d0, String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithDental(gender,d0,d1);
	}

	public Integer getNoOfPatientWithCaries(String gender, Integer d0, String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithCaries(gender,d0,d1);
	}

	public Integer getNoOfPatientWithAcne(String gender, Integer d0, String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithAcne(gender,d0,d1);
	}

	public Integer getNoOfPatientWithCutaneous(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithCutaneous(gender,d0,d1);
	}

	public Integer getNoOfPatientWithDermatitis(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithDermatitis(gender,d0,d1);
	}

	public Integer getNoOfPatientWithHerpes(String gender, Integer d0, String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithHerpes(gender,d0,d1);
	}

	public Integer getNoOfPatientWithImpetigo(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithImpetigo(gender,d0,d1);
	}

	public Integer getNoOfPatientWithOtherSkin(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithOtherSkin(gender,d0,d1);
	}

	public Integer getNoOfPatientWithPruritus(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithPruritus(gender,d0,d1);
	}

	public Integer getNoOfPatientWithScabies(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithScabies(gender,d0,d1);
	}

	public Integer getNoOfPatientWithTinea(String gender, Integer d0, String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithTinea(gender,d0,d1);
	}

	public Integer getNoOfPatientWithVersicolor(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithVersicolor(gender,d0,d1);
	}

	public Integer getNoOfPatientWithUrticaria(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithUrticaria(gender,d0,d1);
	}

	public Integer getNoOfPatientWithVitiligo(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithVitiligo(gender,d0,d1);
	}

	public Integer getNoOfPatientWithWart(String gender, Integer d0, String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithWart(gender,d0,d1);
	}

	public Integer getNoOfPatientWithDracunculiasis(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithDracunculiasis(gender,d0,d1);
	}

	public Integer getNoOfPatientWithDiarrhea(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithDiarrhea(gender,d0,d1);
	}

	public Integer getNoOfPatientWithAcutePancreatitis(String gender,
			Integer d0, String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithAcutePancreatitis(gender,d0,d1);
	}

	public Integer getNoOfPatientWithAppendicitis(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithAppendicitis(gender,d0,d1);
	}

	public Integer getNoOfPatientWithBleeding(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithBleeding(gender,d0,d1);
	}

	public Integer getNoOfPatientWithCholecystitis(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithCholecystitis(gender,d0,d1);
	}


	public Integer getNoOfPatientWithDiseasesOral(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithDiseasesOral(gender,d0,d1);
	}

	public Integer getNoOfPatientWithFissure(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithFissure(gender,d0,d1);
	}

	public Integer getNoOfPatientWithIntestinalObstruction(String gender,
			Integer d0, String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithIntestinalObstruction(gender,d0,d1);
	}

	public Integer getNoOfPatientWithNoninfectiveGastroenteritis(String gender,
			Integer d0, String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithNoninfectiveGastroenteritis(gender,d0,d1);
	}

	public Integer getNoOfPatientWithChronicLiver(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithChronicLiver(gender,d0,d1);
	}

	public Integer getNoOfPatientWithPancreatitis(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithPancreatitis(gender,d0,d1);
	}

	public Integer getNoOfPatientWithPepticUlcer(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithPepticUlcer(gender,d0,d1);
	}

	public Integer getNoOfPatientWithPeritonitis(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithPeritonitis(gender,d0,d1);
	}

	public Integer getNoOfPatientWithHydatedCystLiver(String gender,
			Integer d0, String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithHydatedCystLiver(gender,d0,d1);
	}

	public Integer getNoOfPatientWithCholelithiasis(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithCholelithiasis(gender,d0,d1);
	}

	public Integer getNoOfPatientWithGastritis(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithGastritis(gender,d0,d1);
	}

	public Integer getNoOfPatientWithHaemorrhoids(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithHaemorrhoids(gender,d0,d1);
	}

	public Integer getNoOfPatientWithHernia(String gender, Integer d0, String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithHernia(gender,d0,d1);
	}

	public Integer getNoOfPatientWithGITDisorder(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithGITDisorder(gender,d0,d1);
	}

	public Integer getNoOfPatientWithHeartburn(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithHeartburn(gender,d0,d1);
	}

	public Integer getNoOfPatientWithAmoebic(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithAmoebic(gender,d0,d1);
	}

	public Integer getNoOfPatientWithCandidiasis(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithCandidiasis(gender,d0,d1);
	}

	public Integer getNoOfPatientWithBacillary(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithBacillary(gender,d0,d1);
	}

	public Integer getNoOfPatientWithDysentery(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithDysentery(gender,d0,d1);
	}

	public Integer getNoOfPatientWithCretinism(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithCretinism(gender,d0,d1);
	}

	public Integer getNoOfPatientWithCystic(String gender, Integer d0, String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithCystic(gender,d0,d1);
	}

	public Integer getNoOfPatientWithThyroidGland(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithThyroidGland(gender,d0,d1);
	}

	public Integer getNoOfPatientWithDehydration(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithDehydration(gender,d0,d1);
	}

	public Integer getNoOfPatientWithMellitusType1(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithMellitusType1(gender,d0,d1);
	}

	public Integer getNoOfPatientWithMellitusType2(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithMellitusType2(gender,d0,d1);
	}

	public Integer getNoOfPatientWithEndemicGoiter(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithEndemicGoiter(gender,d0,d1);
	}

	public Integer getNoOfPatientWithHyperthyroidism(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithHyperthyroidism(gender,d0,d1);
	}

	public Integer getNoOfPatientWithHypothyroidism(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithHypothyroidism(gender,d0,d1);
	}

	public Integer getNoOfPatientWithMalnutrition(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithMalnutrition(gender,d0,d1);
	}

	public Integer getNoOfPatientWithDiabetesMellitus(String gender,
			Integer d0, String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithDiabetesMellitus(gender,d0,d1);
	}

	public Integer getNoOfPatientWithOtherEndocrine(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithOtherEndocrine(gender,d0,d1);
	}

	public Integer getNoOfPatientWithPellagra(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithPellagra(gender,d0,d1);
	}

	public Integer getNoOfPatientWithVitaminA(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return  dao.getNoOfPatientWithVitaminA(gender,d0,d1);
	}

	public Integer getNoOfPatientWithVitaminB(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithVitaminB(gender,d0,d1);
	}


	public Integer getNoOfPatientWithVitaminD(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithVitaminD(gender,d0,d1);						
	}



	public Integer getNoOfPatientWithOverweight(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithOverweight(gender,d0,d1);
	}


	public Integer getNoOfPatientWithScurvy(String gender, Integer d0, String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithScurvy(gender,d0,d1);
	}

	public Integer getNoOfPatientWithDNS(String gender, Integer d0, String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithDNS(gender,d0,d1);
	}

	public Integer getNoOfPatientWithHearingLos(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithHearingLos(gender,d0,d1);
	}

	public Integer getNoOfPatientWithHoarseness(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithHoarseness(gender,d0,d1);
	}

	public Integer getNoOfPatientWithExterna(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithExterna(gender,d0,d1);
	}

	public Integer getNoOfPatientWithMedia(String gender, Integer d0, String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithMedia(gender,d0,d1);
	}

	public Integer getNoOfPatientWithOtalgia(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithOtalgia(gender,d0,d1);
	}

	public Integer getNoOfPatientWithTinnitus(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithTinnitus(gender,d0,d1);
	}

	public Integer getNoOfPatientWithWax(String gender, Integer d0, String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithWax(gender,d0,d1);
	}

	public Integer getNoOfPatientWithLaryngitis(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithLaryngitis(gender,d0,d1);
	}

	public Integer getNoOfPatientWithTonsillitis(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithTonsillitis(gender,d0,d1);
	}

	public Integer getNoOfPatientWithPharangitis(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithPharangitis(gender,d0,d1);
	}

	public Integer getNoOfPatientWithChronic(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithChronic(gender,d0,d1);
	}

	public Integer getNoOfPatientWithOtherENT(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithOtherENT(gender,d0,d1);
	}

	public Integer getNoOfPatientWithSinusitis(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithSinusitis(gender,d0,d1);
	}

	public Integer getNoOfPatientWithTonsilar(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithTonsilar(gender,d0,d1);
	}

	public Integer getNoOfPatientWithRhinitis(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithRhinitis(gender,d0,d1);
	}

	public Integer getNoOfPatientWithTractInfection(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithTractInfection(gender,d0,d1);
	}

	public Integer getNoOfPatientWithBlindness(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithBlindness(gender,d0,d1);
	}

	public Integer getNoOfPatientWithCataract(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithCataract(gender,d0,d1);
	}

	public Integer getNoOfPatientWithConjuntivitis(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithConjuntivitis(gender,d0,d1);
	}

	public Integer getNoOfPatientWithCorneal(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithCorneal(gender,d0,d1);
	}

	public Integer getNoOfPatientWithEyeRefraction(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithEyeRefraction(gender,d0,d1);
	}

	public Integer getNoOfPatientWithGlaucoma(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithGlaucoma(gender,d0,d1);
	}

	public Integer getNoOfPatientWithChoroid(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithChoroid(gender,d0,d1);
	}

	public Integer getNoOfPatientWithEyeDisorder(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithEyeDisorder(gender,d0,d1);
	}

	public Integer getNoOfPatientWithPterygium(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithPterygium(gender,d0,d1);
	}

	public Integer getNoOfPatientWithStye(String gender, Integer d0, String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithStye(gender,d0,d1);
	}

	public Integer getNoOfPatientWithCornealUlcer(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithCornealUlcer(gender,d0,d1);
	}

	public Integer getNoOfPatientWithTrachoma(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithTrachoma(gender,d0,d1);
	}

	public Integer getNoOfPatientWithLocalizedswelling(String gender,
			Integer d0, String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithLocalizedswelling(gender,d0,d1);
	}

	public Integer getNoOfPatientWithAbdominalPain(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithAbdominalPain(gender,d0,d1);
	}


	public Integer getNoOfPatientWithAcuteAbdomen(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithAcuteAbdomen(gender,d0,d1);
	}



	public Integer getNoOfPatientWithAnorexia(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithAnorexia(gender,d0,d1);
	}

	public Integer getNoOfPatientWithAscites(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithAscites(gender,d0,d1);
	}

	public Integer getNoOfPatientWithAtaxia(String gender, Integer d0, String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithAtaxia(gender,d0,d1);
	}

	public Integer getNoOfPatientWithcommunicablediseases(String gender,
			Integer d0, String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithcommunicablediseases(gender,d0,d1);
	}

	public Integer getNoOfPatientWithCough(String gender, Integer d0, String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithCough(gender,d0,d1);
	}

	public Integer getNoOfPatientWithToxicity(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithToxicity(gender,d0,d1);
	}

	public Integer getNoOfPatientWithAphagia(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithAphagia(gender,d0,d1);
	}

	public Integer getNoOfPatientWithDysuria(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithDysuria(gender,d0,d1);
	}

	public Integer getNoOfPatientWithEdema(String gender, Integer d0, String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithEdema(gender,d0,d1);
	}

	public Integer getNoOfPatientWithEpistaxis(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithEpistaxis(gender,d0,d1);
	}

	public Integer getNoOfPatientWithThrive(String gender, Integer d0, String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithThrive(gender,d0,d1);
	}

	public Integer getNoOfPatientWithHeadache(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithHeadache(gender,d0,d1);
	}

	public Integer getNoOfPatientWithHematuria(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithHematuria(gender,d0,d1);
	}

	public Integer getNoOfPatientWithFalling(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithFalling(gender,d0,d1);
	}

	public Integer getNoOfPatientWithInfantile(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithInfantile(gender,d0,d1);
	}

	public Integer getNoOfPatientWithJaundice(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithJaundice(gender,d0,d1);
	}

	public Integer getNoOfPatientWithMemoryDisorder(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithMemoryDisorder(gender,d0,d1);
	}

	public Integer getNoOfPatientWithOtherGeneral(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithOtherGeneral(gender,d0,d1);
	}

	public Integer getNoOfPatientWithSyncope(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithSyncope(gender,d0,d1);
	}

	public Integer getNoOfPatientWithUrinary(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithUrinary(gender,d0,d1);
	}

	public Integer getNoOfPatientWithWeakness(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithWeakness(gender,d0,d1);
	}

	public Integer getNoOfPatientWithUnknownOrigin(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithUnknownOrigin(gender,d0,d1);
	}

	public Integer getNoOfPatientWithAllergies(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithAllergies(gender,d0,d1);
	}

	public Integer getNoOfPatientWithFebrile(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithFebrile(gender,d0,d1);
	}

	public Integer getNoOfPatientWithRenal(String gender, Integer d0, String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithRenal(gender,d0,d1);
	}

	public Integer getNoOfPatientWithProstatic(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithProstatic(gender,d0,d1);
	}

	public Integer getNoOfPatientWithBreast(String gender, Integer d0, String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithBreast(gender,d0,d1);
	}

	public Integer getNoOfPatientWithChronicRenal(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithChronicRenal(gender,d0,d1);
	}

	public Integer getNoOfPatientWithGlomerular(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithGlomerular(gender,d0,d1);
	}

	public Integer getNoOfPatientWithHydrocele(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithHydrocele(gender,d0,d1);
	}

	public Integer getNoOfPatientWithDisorderBreast(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithDisorderBreast(gender,d0,d1);
	}

	public Integer getNoOfPatientWithSpondylopathies(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithSpondylopathies(gender,d0,d1);
	}



	public Integer getNoOfPatientWithOsteomyelitis(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithOsteomyelitis(gender,d0,d1);
	}

	public Integer getNoOfPatientWithChronicOsteomyelitis(String gender,
			Integer d0, String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithChronicOsteomyelitis(gender,d0,d1);
	}

	public Integer getNoOfPatientWithFractures(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithFractures(gender,d0,d1);
	}

	public Integer getNoOfPatientWithCancerBreast(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithCancerBreast(gender,d0,d1);
	}

	public Integer getNoOfPatientWithCancerBronchus(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithCancerBronchus(gender,d0,d1);
	}

	public Integer getNoOfPatientWithCancerCervix(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithCancerCervix(gender,d0,d1);
	}

	public Integer getNoOfPatientWithCancerLiver(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithCancerLiver(gender,d0,d1);
	}

	public Integer getNoOfPatientWithOesophagus(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithOesophagus(gender,d0,d1);
	}

	public Integer getNoOfPatientWithOralCavity(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithOralCavity(gender,d0,d1);
	}

	public Integer getNoOfPatientWithStomach(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithStomach(gender,d0,d1);
	}

	public Integer getNoOfPatientWithUterus(String gender, Integer d0, String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithUterus(gender,d0,d1);
	}

	public Integer getNoOfPatientWithNeoplasm(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithNeoplasm(gender,d0,d1);
	}

	public Integer getNoOfPatientWithOtherNeoplasm(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithOtherNeoplasm(gender,d0,d1);
	}

	public Integer getNoOfPatientWithChoriocarcinoma(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithChoriocarcinoma(gender,d0,d1);
	}

	public Integer getNoOfPatientWithMalignant(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithMalignant(gender,d0,d1);
	}

	public Integer getNoOfPatientWithAlcohol(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithAlcohol(gender,d0,d1);
	}

	public Integer getNoOfPatientWithMoodDisorder(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithMoodDisorder(gender,d0,d1);
	}

	public Integer getNoOfPatientWithPsychiatric(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithPsychiatric(gender,d0,d1);
	}

	public Integer getNoOfPatientWithSchzophrenia(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithSchzophrenia(gender,d0,d1);
	}

	public Integer getNoOfPatientWithDementia(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithDementia(gender,d0,d1);
	}

	public Integer getNoOfPatientWithSenile(String gender, Integer d0, String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithSenile(gender,d0,d1);
	}

	public Integer getNoOfPatientWithRetardation(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithRetardation(gender,d0,d1);
	}

	public Integer getNoOfPatientWithMental(String gender, Integer d0, String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithMental(gender,d0,d1);
	}

	public Integer getNoOfPatientWithAsthma(String gender, Integer d0, String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithAsthma(gender,d0,d1);
	}

	public Integer getNoOfPatientWithBronchiectasis(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithBronchiectasis(gender,d0,d1);
	}

	public Integer getNoOfPatientWithLowerRespiratory(String gender,
			Integer d0, String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithLowerRespiratory(gender,d0,d1);
	}

	public Integer getNoOfPatientWithBronchiolitis(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithBronchiolitis(gender,d0,d1);
	}

	public Integer getNoOfPatientWithBronchoneumonia(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithBronchoneumonia(gender,d0,d1);
	}

	public Integer getNoOfPatientWithRespiratory(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithRespiratory(gender,d0,d1);
	}

	public Integer getNoOfPatientWithPhysiotherapy(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithPhysiotherapy(gender,d0,d1);
	}

	public Integer getNoOfPatientWithHealthServices(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithHealthServices(gender,d0,d1);
	}

	public Integer getNoOfPatientWithGonococcus(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithGonococcus(gender,d0,d1);
	}

	public Integer getNoOfPatientWithAnogenital(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithAnogenital(gender,d0,d1);
	}

	public Integer getNoOfPatientWithTransmitted(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithTransmitted(gender,d0,d1);
	}

	public Integer getNoOfPatientWithSyphilis(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithSyphilis(gender,d0,d1);
	}

	public Integer getNoOfPatientWithChlamydia(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithChlamydia(gender,d0,d1);
	}

	public Integer getNoOfPatientWithGenitalUlcer(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithGenitalUlcer(gender,d0,d1);
	}

	public Integer getNoOfPatientWithUrethral(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithUrethral(gender,d0,d1);
	}

	public Integer getNoOfPatientWithChikungunya(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithChikungunya(gender,d0,d1);
	}

	public Integer getNoOfPatientWithDengue(String gender, Integer d0, String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithDengue(gender,d0,d1);
	}

	public Integer getNoOfPatientWithFilariasis(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithFilariasis(gender,d0,d1);
	}

	public Integer getNoOfPatientWithJapaneseEncephalitis(String gender,
			Integer d0, String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithJapaneseEncephalitis(gender,d0,d1);
	}

	public Integer getNoOfPatientWithKalaAzar(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithKalaAzar(gender,d0,d1);
	}

	public Integer getNoOfPatientWithMalaria(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithMalaria(gender,d0,d1);
	}

	public Integer getNoOfPatientWithBirthWeight(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithBirthWeight(gender,d0,d1);
	}

	public Integer getNoOfPatientWithUmbilical(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithUmbilical(gender,d0,d1);
	}

	public Integer getNoOfPatientWithNewborn(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithNewborn(gender,d0,d1);
	}

	public Integer getNoOfPatientWithNeonatorum(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithNeonatorum(gender,d0,d1);
	}

	public Integer getNoOfPatientWithBirthTrauma(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithBirthTrauma(gender,d0,d1);
	}

	public Integer getNoOfPatientWithEctopic(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithEctopic(gender,d0,d1);
	}

	public Integer getNoOfPatientWithEdemaProtenuria(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithEdemaProtenuria(gender,d0,d1);
	}

	public Integer getNoOfPatientWithSpontaneous(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithSpontaneous(gender,d0,d1);
	}

	public Integer getNoOfPatientWithPostpartum(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithPostpartum(gender,d0,d1);
	}

	public Integer getNoOfPatientWithPuerperalSepsis(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithPuerperalSepsis(gender,d0,d1);
	}

	public Integer getNoOfPatientWithHemorrhage(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithHemorrhage(gender,d0,d1);
	}

	public Integer getNoOfPatientWithMaternal(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithMaternal(gender,d0,d1);
	}

	public Integer getNoOfPatientWithLabour(String gender, Integer d0, String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithLabour(gender,d0,d1);
	}

	public Integer getNoOfPatientWithEclampsia(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithEclampsia(gender,d0,d1);
	}

	public Integer getNoOfPatientWithVomitting(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithVomitting(gender,d0,d1);
	}

	public Integer getNoOfPatientWithAbortive(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithAbortive(gender,d0,d1);
	}

	public Integer getNoOfPatientWithPreterm(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithPreterm(gender,d0,d1);
	}

	public Integer getNoOfPatientWithParasitic(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithParasitic(gender,d0,d1);
	}

	public Integer getNoOfPatientWithPuerperium(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithPuerperium(gender,d0,d1);
	}

	public Integer getNoOfPatientWithMalpresentation(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithMalpresentation(gender,d0,d1);
	}

	public Integer getNoOfPatientWithGestations(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithGestations(gender,d0,d1);
	}

	public Integer getNoOfPatientWithTwinPregnancy(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithTwinPregnancy(gender,d0,d1);
	}

	public Integer getNoOfPatientWithMaterna(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithMaterna(gender,d0,d1);
	}

	public Integer getNoOfPatientWithTeenage(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithTeenage(gender,d0,d1);
	}

	public Integer getNoOfPatientWithEarly(String gender, Integer d0, String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithEarly(gender,d0,d1);
	}

	public Integer getNoOfPatientWithPregnacyTest(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithPregnacyTest(gender,d0,d1);
	}

	public Integer getNoOfPatientWithRhIncompatibility(String gender,
			Integer d0, String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithRhIncompatibility(gender,d0,d1);
	}

	public Integer getNoOfPatientWithFalseLabour(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithFalseLabour(gender,d0,d1);
	}

	public Integer getNoOfPatientWithBreechDelivery(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithBreechDelivery(gender,d0,d1);
	}

	public Integer getNoOfPatientWithNephrotic(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithNephrotic(gender,d0,d1);
	}

	public Integer getNoOfPatientWithOtherBreas(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithOtherBreas(gender,d0,d1);
	}

	public Integer getNoOfPatientWithGenitourinary(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithGenitourinary(gender,d0,d1);
	}

	public Integer getNoOfPatientWithParaphimosis(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithParaphimosis(gender,d0,d1);
	}

	public Integer getNoOfPatientWithTract(String gender, Integer d0, String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithTract(gender,d0,d1);
	}

	public Integer getNoOfPatientWithUrolithiasis(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithUrolithiasis(gender,d0,d1);
	}

	public Integer getNoOfPatientWithDysmenorrhoea(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithDysmenorrhoea(gender,d0,d1);
	}

	public Integer getNoOfPatientWithOtherGynaecologicalDisorder(String gender,
			Integer d0, String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithOtherGynaecologicalDisorder(gender,d0,d1);
	}

	public Integer getNoOfPatientWithUterineFibroid(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithUterineFibroid(gender,d0,d1);
	}

	public Integer getNoOfPatientWithAmenorrhoe(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithAmenorrhoe(gender,d0,d1);
	}

	public Integer getNoOfPatientWithGenital(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithGenital(gender,d0,d1);
	}

	public Integer getNoOfPatientWithMenorrhagia(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithMenorrhagia(gender,d0,d1);
	}

	public Integer getNoOfPatientWithOvarian(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithOvarian(gender,d0,d1);
	}

	public Integer getNoOfPatientWithVaginal(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithVaginal(gender,d0,d1);
	}

	public Integer getNoOfPatientWithVaginitis(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithVaginitis(gender,d0,d1);
	}

	public Integer getNoOfPatientWithLeucorrhoea(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithLeucorrhoea(gender,d0,d1);
	}

	public Integer getNoOfPatientWithPelvic(String gender, Integer d0, String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithPelvic(gender,d0,d1);
	}

	public Integer getNoOfPatientWithAnaemia(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithAnaemia(gender,d0,d1);
	}

	public Integer getNoOfPatientWithImmune(String gender, Integer d0, String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithImmune(gender,d0,d1);
	}

	public Integer getNoOfPatientWithhemorrhagic(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithhemorrhagic(gender,d0,d1);
	}

	public Integer getNoOfPatientWithAnthrax(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithAnthrax(gender,d0,d1);
	}

	public Integer getNoOfPatientWithDiphtheria(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithDiphtheria(gender,d0,d1);
	}

	public Integer getNoOfPatientWithHIV(String gender, Integer d0, String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithHIV(gender,d0,d1);
	}

	public Integer getNoOfPatientWithMeasles(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithMeasles(gender,d0,d1);
	}

	public Integer getNoOfPatientWithInfectious(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithInfectious(gender,d0,d1);
	}

	public Integer getNoOfPatientWithPlague(String gender, Integer d0, String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithPlague(gender,d0,d1);
	}

	public Integer getNoOfPatientWithSevereSepsis(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithSevereSepsis(gender,d0,d1);
	}

	public Integer getNoOfPatientWithTuberculosis(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithTuberculosis(gender,d0,d1);
	}

	public Integer getNoOfPatientWithInfestation(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithInfestation(gender,d0,d1);
	}

	public Integer getNoOfPatientWithLeptopirosis(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithLeptopirosis(gender,d0,d1);
	}

	public Integer getNoOfPatientWithParalysis(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithParalysis(gender,d0,d1);
	}

	public Integer getNoOfPatientWithMeningococcal(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithMeningococcal(gender,d0,d1);
	}

	public Integer getNoOfPatientWithUnspecified(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithUnspecified(gender,d0,d1);
	}

	public Integer getNoOfPatientWithRabies(String gender, Integer d0, String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithRabies(gender,d0,d1);
	}

	public Integer getNoOfPatientWithTetanus(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithTetanus(gender,d0,d1);
	}

	public Integer getNoOfPatientWithMeningitis(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithMeningitis(gender,d0,d1);
	}

	public Integer getNoOfPatientWithChicken(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithChicken(gender,d0,d1);
	}

	public Integer getNoOfPatientWithLeprosy(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithLeprosy(gender,d0,d1);
	}

	public Integer getNoOfPatientWithHerpesSimplex(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithHerpesSimplex(gender,d0,d1);
	}

	public Integer getNoOfPatientWithMumps(String gender, Integer d0, String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithMumps(gender,d0,d1);
	}

	public Integer getNoOfPatientWithCholera(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithCholera(gender,d0,d1);
	}

	public Integer getNoOfPatientWithEnteric(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithEnteric(gender,d0,d1);
	}

	public Integer getNoOfPatientWithHepatitisA(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithHepatitisA(gender,d0,d1);
	}

	public Integer getNoOfPatientWithHepatitisUnspecified(String gender,
			Integer d0, String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithHepatitisUnspecified(gender,d0,d1);
	}

	public Integer getNoOfPatientWithHepatitisB(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithHepatitisB(gender,d0,d1);
	}

	public Integer getNoOfPatientWithHepatitisC(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithHepatitisC(gender,d0,d1);
	}

	public Integer getNoOfPatientWithHepatitisE(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithHepatitisE(gender,d0,d1);
	}

	public Integer getNoOfPatientWithLymphadenopathy(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithLymphadenopathy(gender,d0,d1);
	}

	public Integer getNoOfPatientWithWhooping(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithWhooping(gender,d0,d1);
	}

	public Integer getNoOfPatientWithInfuenza(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithInfuenza(gender,d0,d1);
	}

	public Integer getNoOfPatientWithSwine(String gender, Integer d0, String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithSwine(gender,d0,d1);
	}

	public Integer getNoOfPatientWithTraffic(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithTraffic(gender,d0,d1);
	}

	public Integer getNoOfPatientWithDrowning(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithDrowning(gender,d0,d1);
	}

	public Integer getNoOfPatientWithAssault(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithAssault(gender,d0,d1);
	}

	public Integer getNoOfPatientWithBurn(String gender, Integer d0, String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithBurn(gender,d0,d1);
	}

	public Integer getNoOfPatientWithHarm(String gender, Integer d0, String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithHarm(gender,d0,d1);
	}

	public Integer getNoOfPatientWithInfected(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithInfected(gender,d0,d1);
	}

	public Integer getNoOfPatientWithAbuse(String gender, Integer d0, String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithAbuse(gender,d0,d1);
	}

	public Integer getNoOfPatientWithPoisoning(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithPoisoning(gender,d0,d1);
	}

	public Integer getNoOfPatientWithForeign(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithForeign(gender,d0,d1);
	}

	public Integer getNoOfPatientWithAccidents(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithAccidents(gender,d0,d1);
	}

	public Integer getNoOfPatientWithOtherInjuries(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithOtherInjuries(gender,d0,d1);
	}

	public Integer getNoOfPatientWithArthritisOther(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithArthritisOther(gender,d0,d1);
	}

	public Integer getNoOfPatientWithDislocation(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithDislocation(gender,d0,d1);
	}

	public Integer getNoOfPatientWithGouty(String gender, Integer d0, String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithGouty(gender,d0,d1);
	}

	public Integer getNoOfPatientWithOsteoarthritis(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithOsteoarthritis(gender,d0,d1);
	}

	public Integer getNoOfPatientWithwithoutFracture(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithwithoutFracture(gender,d0,d1);
	}

	public Integer getNoOfPatientWithMusculoskeletal(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithMusculoskeletal(gender,d0,d1);
	}

	public Integer getNoOfPatientWithPyogenic(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithPyogenic(gender,d0,d1);
	}

	public Integer getNoOfPatientWithRheumatoid(String gender, Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithRheumatoid(gender,d0,d1);
	}
//drugs
	public Integer getNoOfPatientWithAnalgesicsAntiPyreticdrugs(Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithAnalgesicsAntiPyreticdrugs(d0,d1);
	}

	public Integer getNoOfPatientWithAntiAllergicdrugs(Integer d0, String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithAntiAllergicdrugs(d0,d1);
	}

	public Integer getNoOfPatientWithAntiAnaemicdrugs(Integer d0, String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithAntiAnaemicdrugs(d0,d1);
	}

	public Integer getNoOfPatientWithAntiEpilepticdrugs(Integer d0, String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithAntiEpilepticdrugs(d0,d1);
	}

	public Integer getNoOfPatientWithAntFilarial(Integer d0, String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithAntFilarial(d0,d1);
	}

	public Integer getNoOfPatientWithAntiFungaldrugs(Integer d0, String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithAntiFungaldrugs(d0,d1);
	}

	public Integer getNoOfPatientWithAntiLeishmaniasisdrugs(Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithAntiLeishmaniasisdrugs(d0,d1);
	}

	public Integer getNoOfPatientWithAntiParkinsonismdrugs(Integer d0, String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithAntiParkinsonismdrugs(d0,d1);
	}

	public Integer getNoOfPatientWithAntiProtozoaldrugs(Integer d0, String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithAntiProtozoaldrugs(d0,d1);
	}

	public Integer getNoOfPatientWithAntiRabies(Integer d0, String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithAntiRabies(d0,d1);
	}

	public Integer getNoOfPatientWithAntiBacterials(Integer d0, String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithAntiBacterials(d0,d1);
	}

	public Integer getNoOfPatientWithAntiCancerdrugs(Integer d0, String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithAntiCancerdrugs(d0,d1);
	}

	public Integer getNoOfPatientWithAntiHelminthics(Integer d0, String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithAntiHelminthics(d0,d1);
	}

	public Integer getNoOfPatientWithAntiMalarials(Integer d0, String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithAntiMalarials(d0,d1);
	}

	public Integer getNoOfPatientWithAntiSeptics(Integer d0, String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithAntiSeptics(d0,d1);
	}

	public Integer getNoOfPatientWithAntiVertigodrugs(Integer d0, String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithAntiVertigodrugs(d0,d1);
	}

	public Integer getNoOfPatientWithAntiVirals(Integer d0, String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithAntiVirals(d0,d1);
	}

	public Integer getNoOfPatientWithCardiovasculardrugs(Integer d0, String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithCardiovasculardrugs(d0,d1);
	}

	public Integer getNoOfPatientWithContrastagents(Integer d0, String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithContrastagents(d0,d1);
	}

	public Integer getNoOfPatientWithDentalpreparation(Integer d0, String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithDentalpreparation(d0,d1);
	}

	public Integer getNoOfPatientWithDermatologicalointmentcreams(Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithDermatologicalointmentcreams(d0,d1);
	}

	public Integer getNoOfPatientWithDiuretics(Integer d0, String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithDiuretics(d0,d1);
	}

	public Integer getNoOfPatientWithDrugsactingRespiratory(Integer d0,
			String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithDrugsactingRespiratory(d0,d1);
	}

	public Integer getNoOfPatientWithDrugscoagulation(Integer d0, String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithDrugscoagulation(d0,d1);
	}

	public Integer getNoOfPatientWithDrugsGouRheumatoid(Integer d0, String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithDrugsGouRheumatoid(d0,d1);
	}

	public Integer getNoOfPatientWithDrugsMigraine(Integer d0, String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithDrugsMigraine(d0,d1);
	}

	public Integer getNoOfPatientWithGastrointestinal(Integer d0, String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithGastrointestinal(d0,d1);
	}

	public Integer getNoOfPatientWithHormoneEndocrine(Integer d0, String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithHormoneEndocrine(d0,d1);
	}

	public Integer getNoOfPatientWithImmunologicals(Integer d0, String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithImmunologicals(d0,d1);
	}

	public Integer getNoOfPatientWithLifeSaving(Integer d0, String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithLifeSaving(d0,d1);
	}

	public Integer getNoOfPatientWithMetabolism(Integer d0, String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithMetabolism(d0,d1);
	}

	public Integer getNoOfPatientWithMucolytic(Integer d0, String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithMucolytic(d0,d1);
	}

	public Integer getNoOfPatientWithAntiCholinesterases(Integer d0, String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithAntiCholinesterases(d0,d1);
	}

	public Integer getNoOfPatientWithOphthalmological(Integer d0, String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithOphthalmological(d0,d1);
	}

	public Integer getNoOfPatientWithOpthalmic(Integer d0, String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithOpthalmic(d0,d1);
	}

	public Integer getNoOfPatientWithOxytocics(Integer d0, String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithOxytocics(d0,d1);
	}

	public Integer getNoOfPatientWithPsychotherapeutic(Integer d0, String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithPsychotherapeutic(d0,d1);
	}

	public Integer getNoOfPatientWithElectrolyte(Integer d0, String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithElectrolyte(d0,d1);
	}

	public Integer getNoOfPatientWithParenteral(Integer d0, String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithParenteral(d0,d1);
	}

	public Integer getNoOfPatientWithVitamins(Integer d0, String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithVitamins(d0,d1);
	}

	public Integer getNoOfPatientWithAnaesthetics(Integer d0, String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithAnaesthetics(d0,d1);
	}

	public Integer getNoOfPatientWithAntiDiabeticdrugs(Integer d0, String d1) {
		// TODO Auto-generated method stub
		return dao.getNoOfPatientWithAntiDiabeticdrugs(d0,d1);
	}


}
