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

import java.util.ArrayList;
import java.util.List;

import org.openmrs.api.APIException;
import org.openmrs.api.db.DAOException;
import org.openmrs.module.hospitalcore.model.IpdPatientAdmission;
import org.openmrs.module.hospitalcore.model.IpdPatientAdmissionLog;
import org.openmrs.module.hospitalcore.model.IpdPatientAdmitted;
import org.openmrs.module.hospitalcore.model.IpdPatientAdmittedLog;
import org.openmrs.module.hospitalcore.model.WardBedStrength;

public interface IpdDAO {
	
	public IpdPatientAdmission saveIpdPatientAdmission(IpdPatientAdmission admission) throws DAOException;
	public IpdPatientAdmissionLog saveIpdPatientAdmissionLog(IpdPatientAdmissionLog admissionLog) throws DAOException;
	public IpdPatientAdmitted saveIpdPatientAdmitted(IpdPatientAdmitted admitted) throws DAOException;
	public IpdPatientAdmittedLog saveIpdPatientAdmittedLog(IpdPatientAdmittedLog admitted) throws DAOException;
	
	public IpdPatientAdmittedLog getIpdPatientAdmittedLog(Integer id) throws DAOException;
	public IpdPatientAdmitted getIpdPatientAdmitted(Integer id) throws DAOException;
	public IpdPatientAdmissionLog getIpdPatientAdmissionLog(Integer id) throws DAOException;
	public IpdPatientAdmission getIpdPatientAdmission(Integer id) throws DAOException;
	
	public List<IpdPatientAdmittedLog> getAllIpdPatientAdmittedLog() throws DAOException;
	public List<IpdPatientAdmitted> getAllIpdPatientAdmitted() throws DAOException;
	public List<IpdPatientAdmissionLog> listIpdPatientAdmissionLog(Integer patientId, Integer admissionWardId,String status,Integer min, Integer max)
	throws DAOException;
	public List<IpdPatientAdmission> getAllIpdPatientAdmission() throws DAOException;
	
	// New Requirement load choose ipd page ~wasib
	public List<IpdPatientAdmission> searchIpdPatientAdmission(String patientSearch, ArrayList<Integer> userIds, String fromDate, String toDate, String wardId, String status) throws APIException;
	public List<IpdPatientAdmitted> searchIpdPatientAdmitted(String patientSearch, ArrayList<Integer> userIds, String fromDate, String toDate, String wardId, String status) throws APIException;
	
	public void removeIpdPatientAdmission(IpdPatientAdmission admission) throws DAOException;
	public void removeIpdPatientAdmitted(IpdPatientAdmitted admitted) throws DAOException;
	
	public List<IpdPatientAdmittedLog> listAdmittedLogByPatientId(Integer patientId) throws DAOException;
	
	public IpdPatientAdmitted getAdmittedByPatientId(Integer patientId) throws DAOException;
	
	public void saveWardBedStrength(WardBedStrength wardBedStrength) throws DAOException;
	
	public WardBedStrength getWardBedStrengthByWardId(Integer wardId) throws DAOException;
}
