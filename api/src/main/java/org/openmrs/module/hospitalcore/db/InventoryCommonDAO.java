/**
 *  Copyright 2013 Society for Health Information Systems Programmes, India (HISP India)
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
 *  author: ghanshyam
 *  date: 18-april-2013
 *  issue: #1391 India module
 **/

package org.openmrs.module.hospitalcore.db;

import java.util.List;

import org.openmrs.Concept;
import org.openmrs.Patient;
import org.openmrs.api.db.DAOException;
import org.openmrs.module.hospitalcore.model.InventoryDrug;
import org.openmrs.module.hospitalcore.model.InventoryDrugFormulation;
import org.openmrs.module.hospitalcore.model.InventoryStoreDrugPatient;
import org.openmrs.module.hospitalcore.model.InventoryStoreDrugPatientDetail;
import org.openmrs.module.hospitalcore.model.InventoryStoreDrugTransactionDetail;

/**
*
*/
public interface InventoryCommonDAO {

	public InventoryStoreDrugPatient getInventoryStoreDrugPatient(Integer billNo) throws DAOException;
	
	public List<InventoryStoreDrugPatientDetail> getInventoryStoreDrugPatientDetail(InventoryStoreDrugPatient isdp) throws DAOException;
	
	public InventoryStoreDrugTransactionDetail getInventoryStoreDrugTransactionDetail(Integer id) throws DAOException;
	
	public List<InventoryStoreDrugPatient> getAllIssueDateByPatientId(Patient patient) throws DAOException;
	
	public List<InventoryStoreDrugPatient> getDeatilOfInventoryStoreDrugPatient(Patient patient,String date) throws DAOException;
	
	public List<InventoryStoreDrugPatientDetail> getDrugDetailOfPatient(InventoryStoreDrugPatient isdpd) throws DAOException;
	
    public InventoryDrug getDrugByName(String name) throws DAOException;
	
	public List<Concept> getDrugFrequency() throws DAOException;
	
	public InventoryDrugFormulation getDrugFormulationById(Integer id) throws DAOException;
	
	public InventoryStoreDrugTransactionDetail expireInventoryStoreDrugTransactionDetail(InventoryStoreDrugTransactionDetail isdtd) throws DAOException;
}
