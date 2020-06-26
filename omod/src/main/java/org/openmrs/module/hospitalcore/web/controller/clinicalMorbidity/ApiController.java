package org.openmrs.module.hospitalcore.web.controller.clinicalMorbidity;


import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;



@Controller
@RequestMapping("module/hospitalcore/apicontroller.json")
public class ApiController {
    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody String getApi() {
        List<String> list = new ArrayList<String>();
        list.add("First");
        list.add("second");

        ObjectMapper mapper = new ObjectMapper();
        String response = "";

        try {
            response = mapper.writeValueAsString(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }
    
}