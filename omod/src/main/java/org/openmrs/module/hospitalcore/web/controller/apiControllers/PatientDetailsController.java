package org.openmrs.module.hospitalcore.web.controller.apiControllers;

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
@RequestMapping("api/patientdetails.json")
public class PatientDetailsController {
    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody String getApi(@RequestParam(value = "pi", required = true) String pi,
            @RequestParam(value = "date", required = true) String date) {

        boolean notfound = false;

        HospitalCoreService hcs = Context.getService(HospitalCoreService.class);
        List<Map<String, Object>> patientDetails = hcs.getPatientDemographicDetailsAPI(pi, date);
        List<Map<String, Object>> hospitalProperties = hcs.getHospitalProperties();

        Map<String, Object> notFoundError = new HashMap<String, Object>();

        PatientDetail patientDetail = new PatientDetail();

        if (patientDetails.size() == 0) {
            notfound = true;
            notFoundError.put("Error", "No record found.");
        } else {
            patientDetail.setPatientName((String) patientDetails.get(0).get("patientName"));
            patientDetail.setAge((String) patientDetails.get(0).get("age"));
            patientDetail.setGender((String) patientDetails.get(0).get("gender"));
            patientDetail.setVisitDateTime(patientDetails.get(0).get("visit_date").toString());
            patientDetail.setVisitOpd((String) patientDetails.get(0).get("opd_visit"));
            patientDetail.setPatientIdentifier((String) patientDetails.get(0).get("identifier"));
            patientDetail.setHospitalName((String) hospitalProperties.get(0).get("property_value"));
            patientDetail.setNinCode((String) hospitalProperties.get(1).get("property_value"));
            List<Map<String, Object>> diagnosis = hcs
                    .getPatientDiagnosiList((Integer) patientDetails.get(0).get("patient_id"), date);
            String doctorName = "";
            for (Map<String, Object> map : diagnosis) {
                doctorName = (String) map.get("doctorName");
                map.remove("doctorName");
            }
            patientDetail.setDoctorName(doctorName);
            patientDetail.setDiagnosis(diagnosis);

        }

        ObjectMapper mapper = new ObjectMapper();
        String response = "";

        try {
            if (notfound) {
                response = mapper.writeValueAsString(notFoundError);
            } else {
                response = mapper.writeValueAsString(patientDetail);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }
}

/**
 * InnerPatientDetailsController
 */
class PatientDetail {
    String hospitalName;
    String ninCode;
    String patientIdentifier;
    String patientName;
    String age;
    String gender;
    String visitDateTime;
    String visitOpd;
    String doctorName;
    List<Map<String, Object>> diagnosis;

    public String getAge() {
        return age;
    }

    public String getGender() {
        return gender;
    }

    public String getPatientIdentifier() {
        return patientIdentifier;
    }

    public String getVisitDateTime() {
        return visitDateTime;
    }

    public String getVisitOpd() {
        return visitOpd;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public String getNinCode() {
        return ninCode;
    }

    public String getPatientName() {
        return patientName;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public List<Map<String, Object>> getDiagnosis() {
        return diagnosis;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public void setDiagnosis(List<Map<String, Object>> diagnosis) {
        this.diagnosis = diagnosis;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setPatientIdentifier(String patientIdentifier) {
        this.patientIdentifier = patientIdentifier;
    }

    public void setVisitDateTime(String visitDateTime) {
        this.visitDateTime = visitDateTime;
    }

    public void setVisitOpd(String visitOpd) {
        this.visitOpd = visitOpd;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    public void setNinCode(String ninCode) {
        this.ninCode = ninCode;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }
}