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


package org.openmrs.module.hospitalcore.db;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.openmrs.Concept;
import org.openmrs.Encounter;
import org.openmrs.EncounterType;
import org.openmrs.Obs;
import org.openmrs.Patient;
import org.openmrs.PersonAttribute;
import org.openmrs.api.db.DAOException;
import org.openmrs.module.hospitalcore.concept.ConceptModel;
import org.openmrs.module.hospitalcore.model.CoreForm;
import org.openmrs.module.hospitalcore.model.PatientSearch;

public interface HospitalCoreDAO {

	public List<Obs> listObsGroup(Integer personId, Integer conceptId, Integer min, Integer max) throws DAOException;
	public Obs getObsGroupCurrentDate(Integer personId, Integer conceptId) throws DAOException;	
	public Integer buildConcepts(List<ConceptModel> conceptModels);
	public List<Patient> searchPatient(String nameOrIdentifier,String gender, int age , int rangeAge, String date, int rangeDay,String relativeName) throws DAOException;
	
	/**
	 * Search patients
	 * @param hql
	 * @return
	 */
	public List<Patient> searchPatient(String hql);
	
	/**
	 * Get patient search result count
	 * @param hql
	 * @return
	 */
	public BigInteger getPatientSearchResultCount(String hql);
	
	/** 
	 * Get all attributes of an patient
	 * @param patientId
	 * @return
	 */
	public List<PersonAttribute> getPersonAttributes(Integer patientId);
	
	/**
	 * Get last visit encounter
	 * @param patient
	 * @param types
	 * @return
	 */
	public Encounter getLastVisitEncounter(Patient patient, List<EncounterType> types);
	
	/**
	 * Save core form
	 * @param form
	 * @return
	 */
	public CoreForm saveCoreForm(CoreForm form);

	/**
	 * Get core form by id
	 * @param id
	 * @return
	 */
	public CoreForm getCoreForm(Integer id);

	/**
	 * Get core forms by name
	 * @param conceptName
	 * @return
	 */
	public List<CoreForm> getCoreForms(String conceptName);

	/**
	 * Get all core forms
	 * @return
	 */
	public List<CoreForm> getCoreForms();

	/**
	 * Delete core form
	 * @param form
	 */
	public void deleteCoreForm(CoreForm form);
	
	/**
	 * Save patientSearch
	 * @param patientSearch
	 * @return
	 */
	public PatientSearch savePatientSearch(PatientSearch patientSearch);
	
	/**
	 * 
	 * Auto generated method comment
	 * 
	 * @param patientID
	 * @return
	 */
	public java.util.Date getLastVisitTime (Patient patientID);
	
	//ghanshyam,22-oct-2013,New Requirement #2940 Dealing with dead patient
	public PatientSearch getPatient(int patientID);
	
	//public List<Patient> getAllEncounterCurrentDate(String date,Set<EncounterType> encounterTypes);
	
	public Set<Encounter> getEncountersByPatientAndDate(String date,Set<EncounterType> encounterTypes);
	public Set<Encounter>getEncountersByPatientAndDateFromObs(String date);
	public List<Obs> getObsInstanceForDiagnosis(Encounter encounter,Concept concept) throws DAOException;
	public Integer getNoOfPatientWithDogBite(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithOtherBite(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithSnakeBite(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithRheumatic(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithBigemany(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithCardiac(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithVenous(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithHypertension(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithIschemic(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithRheumaticHeart(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithVericoseVein(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithOtherCardiovascular(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithLymphadenitis(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithCerebralPalsy(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithEncephalitis(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithEpilepsy(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithHemiparesis(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithHydrocephalus(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithInsomnia(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithMigraine(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithOtherNervous(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithParaplegia(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithParkinsonism(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithCerebro(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithVertigo(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithCongenitalDisorders(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithCongenitalHeart(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithDental(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithCaries(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithAcne(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithCutaneous(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithDermatitis(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithHerpes(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithImpetigo(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithOtherSkin(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithPruritus(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithScabies(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithTinea(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithVersicolor(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithUrticaria(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithVitiligo(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithWart(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithDracunculiasis(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithDiarrhea(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithAcutePancreatitis(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithAppendicitis(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithBleeding(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithCholecystitis(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithDiseasesOral (String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithFissure(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithIntestinalObstruction(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithNoninfectiveGastroenteritis(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithChronicLiver(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithPancreatitis(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithPepticUlcer(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithPeritonitis(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithHydatedCystLiver(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithCholelithiasis(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithGastritis(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithHaemorrhoids(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithHernia(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithGITDisorder(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithHeartburn(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithAmoebic(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithCandidiasis(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithBacillary(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithDysentery(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithCretinism(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithCystic(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithThyroidGland(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithDehydration(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithMellitusType1(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithMellitusType2(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithEndemicGoiter(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithHyperthyroidism(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithHypothyroidism(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithMalnutrition(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithDiabetesMellitus(String gender,Integer d0,String d1);
	////////////////////////////////////////////////////////////////////////////////////////
	
	public Integer getNoOfPatientWithSpondylopathies(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithOsteomyelitis(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithChronicOsteomyelitis(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithFractures(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithCancerBreast(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithCancerBronchus(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithCancerCervix(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithCancerLiver(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithOesophagus(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithOralCavity(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithStomach(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithUterus(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithNeoplasm(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithOtherNeoplasm(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithChoriocarcinoma(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithMalignant(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithAlcohol(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithMoodDisorder (String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithPsychiatric(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithSchzophrenia(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithDementia(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithSenile(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithRetardation(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithMental(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithAsthma(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithBronchiectasis(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithLowerRespiratory(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithBronchiolitis(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithBronchoneumonia(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithRespiratory(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithPhysiotherapy(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithHealthServices(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithGonococcus(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithAnogenital(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithTransmitted(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithSyphilis(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithChlamydia(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithGenitalUlcer(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithUrethral(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithChikungunya(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithDengue(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithFilariasis(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithJapaneseEncephalitis(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithKalaAzar(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithMalaria(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithBirthWeight(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithUmbilical(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithNewborn(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithNeonatorum(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithBirthTrauma(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithEctopic(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithEdemaProtenuria(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithSpontaneous(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithPostpartum(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithPuerperalSepsis(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithHemorrhage(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithMaternal(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithLabour(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithEclampsia(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithVomitting(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithAbortive(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithPreterm(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithParasitic(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithPuerperium(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithMalpresentation(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithGestations(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithTwinPregnancy(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithMaterna(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithTeenage(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithEarly(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithPregnacyTest(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithRhIncompatibility(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithFalseLabour(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithBreechDelivery(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithNephrotic(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithOtherBreas(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithGenitourinary(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithParaphimosis(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithTract(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithUrolithiasis(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithDysmenorrhoea(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithOtherGynaecologicalDisorder(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithUterineFibroid(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithAmenorrhoe(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithGenital(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithMenorrhagia(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithOvarian(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithVaginal (String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithVaginitis(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithLeucorrhoea(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithPelvic(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithAnaemia(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithImmune(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithhemorrhagic(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithAnthrax(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithDiphtheria(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithHIV(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithMeasles(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithInfectious(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithPlague(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithSevereSepsis(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithTuberculosis(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithInfestation(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithLeptopirosis(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithParalysis(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithMeningococcal(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithUnspecified(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithRabies(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithTetanus(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithMeningitis(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithChicken(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithLeprosy(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithHerpesSimplex(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithMumps(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithCholera(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithEnteric(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithHepatitisA(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithHepatitisUnspecified(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithHepatitisB(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithHepatitisC(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithHepatitisE(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithLymphadenopathy(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithWhooping(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithInfuenza(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithSwine(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithTraffic(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithDrowning(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithAssault(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithBurn(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithHarm(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithInfected(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithAbuse(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithPoisoning(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithForeign(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithAccidents(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithOtherInjuries(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithArthritisOther(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithDislocation(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithGouty(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithOsteoarthritis(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithwithoutFracture(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithMusculoskeletal(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithPyogenic(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithRheumatoid(String gender,Integer d0,String d1);
	///////////////////////////////////////////////////////////////////////////////////////////
	public Integer getNoOfPatientWithOtherEndocrine(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithPellagra(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithVitaminA(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithVitaminB(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithVitaminD(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithOverweight(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithScurvy(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithDNS(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithHearingLos(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithHoarseness(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithExterna(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithMedia(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithOtalgia(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithTinnitus(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithWax(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithLaryngitis(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithTonsillitis(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithPharangitis(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithChronic(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithOtherENT(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithSinusitis(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithTonsilar(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithRhinitis(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithTractInfection(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithBlindness(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithCataract(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithConjuntivitis(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithCorneal(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithEyeRefraction(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithGlaucoma(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithChoroid (String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithEyeDisorder(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithPterygium(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithStye(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithCornealUlcer(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithTrachoma(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithLocalizedswelling(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithAbdominalPain(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithAcuteAbdomen(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithAnorexia(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithAscites(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithAtaxia(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithcommunicablediseases(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithCough(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithToxicity(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithAphagia(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithDysuria(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithEdema(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithEpistaxis(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithThrive(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithHeadache(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithHematuria(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithFalling(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithInfantile(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithJaundice(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithMemoryDisorder(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithOtherGeneral(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithSyncope(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithUrinary(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithWeakness(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithUnknownOrigin(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithAllergies(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithFebrile(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithRenal(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithProstatic(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithBreast(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithChronicRenal(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithGlomerular(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithHydrocele(String gender,Integer d0,String d1);
	public Integer getNoOfPatientWithDisorderBreast(String gender,Integer d0,String d1);

}
