/**
 *  Copyright 2012 Society for Health Information Systems Programmes, India (HISP India)
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

package org.openmrs.module.hospitalcore.web.controller.billsearch;

import org.openmrs.api.context.Context;
import org.openmrs.module.hospitalcore.BillingService;
import org.openmrs.module.hospitalcore.model.PatientServiceBill;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Author: Thai Chuong June 10th 2012 Search bill by Bill Id Supported issue #247 & #254
 */
@Controller("HospitalcoreSearchBillController")
@RequestMapping("/module/hospitalcore/findBill.htm")
public class BillSearchController {
	
	@RequestMapping(method = RequestMethod.GET)
	public String searchBill(@RequestParam("billId") String billId, Model model) {
		BillingService bs = Context.getService(BillingService.class);
		int receiptId = 0;
		try {
			receiptId = Integer.parseInt(billId);
		}
		catch (NumberFormatException e) {
			model.addAttribute("Found", "Cannot find bill");
			return "redirect:/module/billing/main.form";
		}
		
		/**
		 * June 20th 2012 - Thai Chuong supported getPatientServiceBillByReceiptId to solve issue
		 * #271
		 */
		PatientServiceBill patientServiceBill = bs.getPatientServiceBillByReceiptId(receiptId);
		
		if (null != patientServiceBill)
			return "redirect:/module/billing/patientServiceBill.list?patientId=" + patientServiceBill.getPatient().getId()
			        + "&billId=" + patientServiceBill.getPatientServiceBillId();
		
		model.addAttribute("Found", "Cannot find bill");
		return "redirect:/module/billing/main.form";
	}
}
