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

import java.math.RoundingMode;
import java.util.Currency;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.module.ModuleActivator;
import org.openmrs.module.hospitalcore.util.Money;

/**
 * This class contains the logic that is run every time this module is either started or shutdown
 */
public class HospitalCoreActivator implements ModuleActivator{
	
	private Log log = LogFactory.getLog(this.getClass());
	

	public void contextRefreshed() {
		// TODO Auto-generated method stub
		
	}

	public void started() {
		// TODO Auto-generated method stub
		log.info("Started HOSPITALCORE Module");
		Money.init(Currency.getInstance("INR"), RoundingMode.HALF_EVEN);
	}

	public void stopped() {
		// TODO Auto-generated method stub
		log.info("Stoped HOSPITALCORE Module");
	}

	public void willRefreshContext() {
		// TODO Auto-generated method stub
		
	}

	public void willStart() {
		// TODO Auto-generated method stub
		log.info("Starting HOSPITALCORE Module");
	}

	public void willStop() {
		// TODO Auto-generated method stub
		log.info("Shutting down HOSPITALCORE Module");
	}
	
}
