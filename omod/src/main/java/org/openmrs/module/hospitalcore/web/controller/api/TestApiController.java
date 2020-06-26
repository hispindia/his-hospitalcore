package org.openmrs.module.hospitalcore.web.controller.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;
import org.openmrs.api.context.Context;
import org.openmrs.module.hospitalcore.HospitalCoreService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("api/testapi.json")
public class TestApiController {
    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody String getApi(@RequestParam(value = "pi", required = true) String pi,
            @RequestParam(value = "date", required = true) String date) {
        HospitalCoreService hcs = Context.getService(HospitalCoreService.class);
        List patientDemographicDetails = hcs.getPatientDemographicDetailsAPI(pi, date);

        List<Map<String, Object>> patientDetailsList = new ArrayList<Map<String,Object>>();
        Map<String, Object> patientDetailsMap = new HashMap<String,Object>();

        for (int i = 0; i < patientDemographicDetails.size(); i++) {
            
        }

        ObjectMapper mapper = new ObjectMapper();
        String response = "";

        try {
            response = mapper.writeValueAsString(patientDemographicDetails);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }
}