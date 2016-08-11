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


package org.openmrs.module.hospitalcore;

import java.util.ArrayList;
import java.util.List;

import org.openmrs.api.APIException;
import org.openmrs.api.OpenmrsService;
import org.openmrs.module.hospitalcore.model.IpdPatientAdmission;
import org.openmrs.module.hospitalcore.model.IpdPatientAdmissionLog;
import org.openmrs.module.hospitalcore.model.IpdPatientAdmitted;
import org.openmrs.module.hospitalcore.model.IpdPatientAdmittedLog;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly=false)
public interface IpdService extends OpenmrsService{
	
	public IpdPatientAdmission saveIpdPatientAdmission(IpdPatientAdmission admission) throws APIException;
	
	public void removeIpdPatientAdmission(IpdPatientAdmission admission) throws APIException;
	
	public IpdPatientAdmissionLog saveIpdPatientAdmissionLog(IpdPatientAdmissionLog admissionLog) throws APIException;
	
	public IpdPatientAdmitted saveIpdPatientAdmitted(IpdPatientAdmitted admitted) throws APIException;
	
	public void removeIpdPatientAdmitted(IpdPatientAdmitted admitted) throws APIException;
	
	public IpdPatientAdmittedLog saveIpdPatientAdmittedLog(IpdPatientAdmittedLog admitted) throws APIException;
	
	@Transactional(readOnly = true)
	public IpdPatientAdmittedLog getIpdPatientAdmittedLog(Integer id) throws APIException;
	
	@Transactional(readOnly = true)
	public IpdPatientAdmitted getIpdPatientAdmitted(Integer id) throws APIException;
	
	@Transactional(readOnly = true)
	public IpdPatientAdmissionLog getIpdPatientAdmissionLog(Integer id) throws APIException;
	
	@Transactional(readOnly = true)
	public IpdPatientAdmission getIpdPatientAdmission(Integer id) throws APIException;
	
	@Transactional(readOnly = true)
	public List<IpdPatientAdmittedLog> getAllIpdPatientAdmittedLog() throws APIException;
	
	@Transactional(readOnly = true)
	public List<IpdPatientAdmitted> getAllIpdPatientAdmitted() throws APIException;
	
	@Transactional(readOnly = true)
	public List<IpdPatientAdmissionLog> listIpdPatientAdmissionLog(Integer patientId, Integer admissionWardId,String status,Integer min, Integer max)
			throws APIException;
	
	@Transactional(readOnly = true)
	public List<IpdPatientAdmission> getAllIpdPatientAdmission() throws APIException;
	
	// New Requirement load choose ipd page 
	@Transactional(readOnly = true)
	public List<IpdPatientAdmission> searchIpdPatientAdmission(String patientSearch, ArrayList<Integer> userIds, String fromDate, String toDate, String wardId, String status) throws APIException;
	
	@Transactional(readOnly = true)
	public List<IpdPatientAdmitted> searchIpdPatientAdmitted(String patientSearch, ArrayList<Integer> userIds, String fromDate, String toDate, String wardId, String status) throws APIException;
	
	public IpdPatientAdmitted transfer(Integer id, Integer wardId, Integer doctorId, String bed) throws APIException;
	
	public IpdPatientAdmittedLog discharge(Integer id, Integer outComeConceptId) throws APIException;
	
	@Transactional(readOnly = true)
	public List<IpdPatientAdmittedLog> listAdmittedLogByPatientId(Integer patientId) throws APIException;
	
	@Transactional(readOnly = true)
	public IpdPatientAdmitted getAdmittedByPatientId(Integer patientId) throws APIException;

}
