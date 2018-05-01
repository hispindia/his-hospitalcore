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


package org.openmrs.module.hospitalcore.matcher;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.openmrs.Encounter;
import org.openmrs.EncounterType;
import org.openmrs.Location;
import org.openmrs.Patient;
import org.openmrs.api.context.Context;
import org.openmrs.module.hospitalcore.PatientDashboardService;

public class DateMatcher implements Matcher<Patient> {
	private Date date;
	private Integer range;

	public DateMatcher(String date, Integer range) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
		date = date + " 00:00:00";
		this.date = sdf.parse(date);
		this.range = range;
	}

	public boolean matches(Object obj) {
		Patient patient = (Patient) obj;		
		Date checkedDate = getLastVisitDate(patient);
		Date beforeDate;
		if(range==1){
			beforeDate = date;
		} else {
			beforeDate = calcDate(date, -range);
		}
		Date afterDate = calcDate(date, range);
		System.out.println("checkedDate ==> " + checkedDate);
		System.out.println("beforeDate ==> " + beforeDate);
		System.out.println("afterDate ==> " + afterDate);
		if (checkedDate != null) {
			if ((checkedDate.compareTo(beforeDate) >= 0)
					&& (checkedDate.compareTo(afterDate) <= 0)) {
				return true;
			}
		} else {
			System.out.println("Can't find the checked date of patient with id " + patient.getId());
		}
		return false;
	}

	private Date getLastVisitDate(Patient patient) {
		PatientDashboardService pds = Context
				.getService(PatientDashboardService.class);
		EncounterType encType = Context.getEncounterService().getEncounterType(
				"OPDENCOUNTER");
		List<Encounter> encounters = pds.getEncounter(patient, new Location(1),
				encType, null);
		System.out.println("encounters.size() ==> " + encounters.size());
		Encounter latestEncounter = getLatestEncounter(encounters);
		if (latestEncounter != null)
			return latestEncounter.getDateCreated();
		return null;
	}

	private Encounter getLatestEncounter(List<Encounter> encounters) {

		// get the smallest date
		Date d = new Date(Long.MIN_VALUE);
		Encounter latestEncounter = null;
		for (Encounter e : encounters) {
			if (d.before(e.getDateCreated())) {
				d = e.getDateCreated();
				latestEncounter = e;
			}
		}
		return latestEncounter;
	}

	private Date calcDate(Date date, Integer range) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DATE, range);
		return c.getTime();
	}

}
