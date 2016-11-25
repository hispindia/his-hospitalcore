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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.openmrs.Concept;
import org.openmrs.Encounter;
import org.openmrs.Location;
import org.openmrs.Obs;
import org.openmrs.User;
import org.openmrs.api.APIException;
import org.openmrs.api.context.Context;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.hospitalcore.IpdService;
import org.openmrs.module.hospitalcore.db.IpdDAO;
import org.openmrs.module.hospitalcore.model.IpdPatientAdmission;
import org.openmrs.module.hospitalcore.model.IpdPatientAdmissionLog;
import org.openmrs.module.hospitalcore.model.IpdPatientAdmitted;
import org.openmrs.module.hospitalcore.model.IpdPatientAdmittedLog;
import org.openmrs.module.hospitalcore.model.IpdPatientVitalStatistics;
import org.openmrs.module.hospitalcore.model.WardBedStrength;
import org.openmrs.module.hospitalcore.util.HospitalCoreConstants;

public class IpdServiceImpl extends BaseOpenmrsService implements IpdService {
	public IpdServiceImpl() {
	}

	protected IpdDAO dao;

	public void setDao(IpdDAO dao) {
		this.dao = dao;
	}

	public List<IpdPatientAdmission> getAllIpdPatientAdmission()
			throws APIException {
		return dao.getAllIpdPatientAdmission();
	}

	public List<IpdPatientAdmissionLog> listIpdPatientAdmissionLog(
			Integer patientId, Integer admissionWardId, String status,
			Integer min, Integer max) throws APIException {
		// TODO Auto-generated method stub
		return dao.listIpdPatientAdmissionLog(patientId, admissionWardId,
				status, min, max);
	}

	public List<IpdPatientAdmitted> getAllIpdPatientAdmitted()
			throws APIException {
		return dao.getAllIpdPatientAdmitted();
	}

	public List<IpdPatientAdmittedLog> getAllIpdPatientAdmittedLog()
			throws APIException {
		return dao.getAllIpdPatientAdmittedLog();
	}

	public IpdPatientAdmission getIpdPatientAdmission(Integer id)
			throws APIException {
		return dao.getIpdPatientAdmission(id);
	}

	public IpdPatientAdmissionLog getIpdPatientAdmissionLog(Integer id)
			throws APIException {
		return dao.getIpdPatientAdmissionLog(id);
	}

	public IpdPatientAdmitted getIpdPatientAdmitted(Integer id)
			throws APIException {
		return dao.getIpdPatientAdmitted(id);
	}

	public IpdPatientAdmittedLog getIpdPatientAdmittedLog(Integer id)
			throws APIException {
		return dao.getIpdPatientAdmittedLog(id);
	}

	public IpdPatientAdmission saveIpdPatientAdmission(
			IpdPatientAdmission admission) throws APIException {
		return dao.saveIpdPatientAdmission(admission);
	}

	public IpdPatientAdmissionLog saveIpdPatientAdmissionLog(
			IpdPatientAdmissionLog admissionLog) throws APIException {
		return dao.saveIpdPatientAdmissionLog(admissionLog);
	}

	public IpdPatientAdmitted saveIpdPatientAdmitted(IpdPatientAdmitted admitted)
			throws APIException {
		return dao.saveIpdPatientAdmitted(admitted);
	}

	public IpdPatientAdmittedLog saveIpdPatientAdmittedLog(
			IpdPatientAdmittedLog admitted) throws APIException {
		return dao.saveIpdPatientAdmittedLog(admitted);
	}

	// New Requirement load choose ipd page
	public List<IpdPatientAdmission> searchIpdPatientAdmission(
			String patientSearch, ArrayList<Integer> userIds, String fromDate,
			String toDate, String wardId, String status) throws APIException {
		return dao.searchIpdPatientAdmission(patientSearch, userIds, fromDate,
				toDate, wardId, status);
	}

	public List<IpdPatientAdmitted> searchIpdPatientAdmitted(
			String patientSearch, ArrayList<Integer> userIds, String fromDate,
			String toDate, String wardId, String status) throws APIException {
		return dao.searchIpdPatientAdmitted(patientSearch, userIds, fromDate,
				toDate, wardId, status);
	}

	public void removeIpdPatientAdmission(IpdPatientAdmission admission)
			throws APIException {
		dao.removeIpdPatientAdmission(admission);

	}

	public void removeIpdPatientAdmitted(IpdPatientAdmitted admitted)
			throws APIException {
		dao.removeIpdPatientAdmitted(admitted);
	}

	public IpdPatientAdmitted transfer(Integer id, Integer wardId,
			Integer doctorId, String bed) throws APIException {

		IpdPatientAdmitted from = getIpdPatientAdmitted(id);
		if (from == null)
			throw new APIException("Can not found IpdPatientAdmitted with id :"
					+ id);

		Concept ward = Context.getConceptService().getConcept(wardId);
		if (ward == null)
			throw new APIException("Can not find IPD Ward with id : " + wardId);

		User user = Context.getUserService().getUser(doctorId);
		if (user == null)
			throw new APIException("Can not find Doctor with user id :"
					+ doctorId);

		IpdPatientAdmittedLog log = new IpdPatientAdmittedLog();
		log.setAdmissionDate(new Date());
		log.setAdmittedWard(from.getAdmittedWard());
		log.setBasicPay(from.getBasicPay());
		log.setBed(from.getBed());
		log.setBirthDate(from.getBirthDate());
		log.setCaste(from.getCaste());
		log.setFatherName(from.getFatherName());
		log.setGender(from.getGender());
		log.setIpdAdmittedUser(from.getIpdAdmittedUser());
		log.setMonthlyIncome(from.getMonthlyIncome());
		log.setPatient(from.getPatient());
		log.setPatientAdmittedLogTransferFrom(from
				.getPatientAdmittedLogTransferFrom());
		log.setPatientAddress(from.getPatientAddress());
		log.setPatientIdentifier(from.getPatientIdentifier());
		log.setPatientAdmissionLog(from.getPatientAdmissionLog());
		log.setPatientName(from.getPatientName());
		log.setUser(Context.getAuthenticatedUser());
		log.setStatus(IpdPatientAdmitted.STATUS_TRANSFER);
		log = saveIpdPatientAdmittedLog(log);
		if (log.getId() != null) {
			removeIpdPatientAdmitted(from);
		}

		IpdPatientAdmitted to = new IpdPatientAdmitted();
		to.setAdmissionDate(new Date());
		to.setAdmittedWard(ward);
		to.setBasicPay(from.getBasicPay());
		to.setBed(bed);
		to.setBirthDate(from.getBirthDate());
		to.setCaste(from.getCaste());
		to.setFatherName(from.getFatherName());
		to.setGender(from.getGender());
		to.setUser(Context.getAuthenticatedUser());
		to.setIpdAdmittedUser(user);
		to.setMonthlyIncome(from.getMonthlyIncome());
		to.setPatient(from.getPatient());
		to.setPatientAddress(from.getPatientAddress());
		to.setPatientIdentifier(from.getPatientIdentifier());
		to.setPatientAdmissionLog(from.getPatientAdmissionLog());
		to.setPatientName(from.getPatientName());
		to.setStatus(IpdPatientAdmitted.STATUS_ADMITTED);
		to.setPatientAdmissionLog(log.getPatientAdmissionLog());
		to.setPatientAdmittedLogTransferFrom(log);
		to = saveIpdPatientAdmitted(to);

		return to;
	}

	public IpdPatientAdmittedLog discharge(Integer id, Integer outComeConceptId)
			throws APIException {

		Concept outComeConcept = Context.getConceptService().getConcept(
				outComeConceptId);
		IpdPatientAdmitted admitted = getIpdPatientAdmitted(id);
		IpdPatientAdmittedLog log = new IpdPatientAdmittedLog();
		log.setAdmissionDate(new Date());
		log.setAdmittedWard(admitted.getAdmittedWard());
		log.setBasicPay(admitted.getBasicPay());
		log.setBed(admitted.getBed());
		log.setBirthDate(admitted.getBirthDate());
		log.setCaste(admitted.getCaste());
		log.setFatherName(admitted.getFatherName());
		log.setUser(Context.getAuthenticatedUser());
		log.setGender(admitted.getGender());
		log.setIpdAdmittedUser(admitted.getIpdAdmittedUser());
		log.setMonthlyIncome(admitted.getMonthlyIncome());
		log.setPatient(admitted.getPatient());
		log.setPatientAddress(admitted.getPatientAddress());
		log.setPatientIdentifier(admitted.getPatientIdentifier());
		log.setPatientAdmissionLog(admitted.getPatientAdmissionLog());
		log.setPatientName(admitted.getPatientName());
		log.setPatientAdmittedLogTransferFrom(admitted
				.getPatientAdmittedLogTransferFrom());
		log.setStatus(IpdPatientAdmitted.STATUS_DISCHARGE);
		log.setAdmissionOutCome(outComeConcept.getName().getName());
		log = saveIpdPatientAdmittedLog(log);
		if (log.getId() != null) {
			// CHUYEN set status of admissionLog = discharge
			IpdPatientAdmissionLog admissionLog = admitted
					.getPatientAdmissionLog();
			admissionLog.setStatus(IpdPatientAdmitted.STATUS_DISCHARGE);
			saveIpdPatientAdmissionLog(admissionLog);
			removeIpdPatientAdmitted(admitted);

			// save discharge info to encounter
			Concept conVisitOutCome = Context.getConceptService().getConcept(
					HospitalCoreConstants.CONCEPT_ADMISSION_OUTCOME);
			Location location = new Location(1);

			Encounter ipdEncounter = admissionLog.getIpdEncounter();

			Obs dischargeObs = new Obs();

			dischargeObs.setConcept(conVisitOutCome);
			dischargeObs.setValueCoded(outComeConcept);
			dischargeObs.setCreator(Context.getAuthenticatedUser());
			dischargeObs.setObsDatetime(new Date());
			dischargeObs.setLocation(location);
			dischargeObs.setDateCreated(new Date());
			dischargeObs.setPatient(ipdEncounter.getPatient());
			dischargeObs.setEncounter(ipdEncounter);
			dischargeObs = Context.getObsService().saveObs(dischargeObs,
					"update obs dischargeObs if need");
			ipdEncounter.addObs(dischargeObs);
			Context.getEncounterService().saveEncounter(ipdEncounter);

		}

		return log;
	}

	public List<IpdPatientAdmittedLog> listAdmittedLogByPatientId(
			Integer patientId) throws APIException {
		return dao.listAdmittedLogByPatientId(patientId);
	}

	public IpdPatientAdmitted getAdmittedByPatientId(Integer patientId)
			throws APIException {
		return dao.getAdmittedByPatientId(patientId);
	}

	public void saveWardBedStrength(WardBedStrength wardBedStrength)
			throws APIException {

		dao.saveWardBedStrength(wardBedStrength);

	}

	public WardBedStrength getWardBedStrengthByWardId(Integer wardId)
			throws APIException {

		return dao.getWardBedStrengthByWardId(wardId);
	}
	//Capture Vital statistics for ipd admitted patients
	
	public IpdPatientVitalStatistics saveIpdPatientVitalStatistics(
			IpdPatientVitalStatistics vitalStatistics) throws APIException {
		return dao.saveIpdPatientVitalStatistics(vitalStatistics);
	}
	public List<IpdPatientVitalStatistics> getIpdPatientVitalStatistics(
			Integer patientId, Integer patientAdmissionLogId)
			throws APIException {
		return dao.getIpdPatientVitalStatistics(patientId,
				patientAdmissionLogId);
	}
	public List<Concept> getDiet() throws APIException {
		return dao.getDiet();
	}
}
