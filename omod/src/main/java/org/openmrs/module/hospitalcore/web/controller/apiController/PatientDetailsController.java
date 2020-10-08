package org.openmrs.module.hospitalcore.web.controller.apiController;

import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("api/patientdetails.json")
public class PatientDetailsController {

    DatabaseConnector databaseConnector = new DatabaseConnector();

    DataSource dataSource = databaseConnector.dataSource();

    @RequestMapping(method = RequestMethod.GET)
    public void getApi(HttpServletResponse response, HttpServletRequest request,
            @RequestParam(value = "pi", required = false) String pi,
            @RequestParam(value = "date", required = false) String date) throws Exception {

        String requestOrigin = request.getHeader("Origin");

        response.addHeader("Access-Control-Allow-Origin", requestOrigin);
        response.setContentType("application/json");

        ServletOutputStream out = response.getOutputStream();

        Error error = new Error();

        if (request.getParameter("pi") == null || request.getParameter("date") == null) {
            error.setError("query parameters are missing");
            new ObjectMapper().writeValue(out, error);
        } else {
            JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

            String query = "select ps.patient_id, ps.identifier, concat_ws(' ', ps.given_name, ps.middle_name, ps.family_name) as patientName, ps.gender, concat('', encounter.encounter_datetime) as visit_date, cn.name as opd_visit, case when TIMESTAMPDIFF(year, ps.birthdate, encounter.encounter_datetime) < 1 then case when TIMESTAMPDIFF(month, ps.birthdate, encounter.encounter_datetime) < 1 then concat_ws(' ', TIMESTAMPDIFF(day, ps.birthdate, encounter.encounter_datetime), 'd') else concat_ws(' ', TIMESTAMPDIFF(month, ps.birthdate, encounter.encounter_datetime), 'm') end else concat_ws(' ', TIMESTAMPDIFF(year, ps.birthdate, encounter.encounter_datetime), 'y') end as age from patient_search ps inner join encounter on encounter.patient_id = ps.patient_id inner join obs on obs.encounter_id = encounter.encounter_id inner join concept_name cn on cn.concept_id = obs.value_coded and cn.concept_name_type = 'FULLY_SPECIFIED' where ps.identifier  = '"
                    + pi + "' AND date(encounter.encounter_datetime) = '" + date + "' AND obs.concept_id = 3";

            List<Map<String, Object>> patientDetails = jdbcTemplate.queryForList(query);

            if (patientDetails.size() == 0) {
                error.setError("No record found");
                new ObjectMapper().writeValue(out, error);
            } else {
                query = "select case when gp.property = 'hospital.location_user' then 'hospitalName' else 'ninCode' end as property, gp.property_value from global_property gp where gp.property in ('hospital.location_user', 'hospitalcore.ninCode')";

                List<Map<String, Object>> hospitalProperties = jdbcTemplate.queryForList(query);

                PatientDetail patientDetail = new PatientDetail();

                patientDetail.setPatientName((String) patientDetails.get(0).get("patientName"));
                patientDetail.setAge((String) patientDetails.get(0).get("age"));
                patientDetail.setGender((String) patientDetails.get(0).get("gender"));
                patientDetail.setVisitDateTime(patientDetails.get(0).get("visit_date").toString());
                patientDetail.setVisitOpd((String) patientDetails.get(0).get("opd_visit"));
                patientDetail.setPatientIdentifier((String) patientDetails.get(0).get("identifier"));
                patientDetail.setHospitalName((String) hospitalProperties.get(0).get("property_value"));
                patientDetail.setNinCode((String) hospitalProperties.get(1).get("property_value"));

                query = "select cn.name, cm.source_code as code, concat_ws(' ', pn.given_name, pn.middle_name, pn.family_name) as doctorName from obs inner join concept_name cn on cn.concept_id = obs.value_coded and cn.concept_name_type = 'FULLY_SPECIFIED' inner join concept_name cn1 on cn1.concept_id = obs.concept_id and cn1.concept_name_type = 'FULLY_SPECIFIED' inner join concept_map cm on cm.concept_id = cn.concept_id inner join concept_source cs on cs.concept_source_id = cm.source and cs.name = 'ICD-10-WHO' inner join users on users.user_id = obs.creator inner join person on person.person_id = users.person_id inner join person_name pn on pn.person_id = person.person_id where obs.person_id = "
                        + (Integer) patientDetails.get(0).get("patient_id") + " and date(obs_datetime) = '" + date
                        + "' AND cn1.name in ('FINAL DIAGNOSIS', 'PROVISIONAL DIAGNOSIS') group by obs.value_coded";

                List<Map<String, Object>> diagnosis = jdbcTemplate.queryForList(query);

                if (diagnosis.size() > 0) {
                    patientDetail.setDoctorName((String) diagnosis.get(0).get("doctorName"));
                    for (Map<String, Object> map : diagnosis) {
                        map.remove("doctorName");
                    }
                }
                patientDetail.setDiagnosis(diagnosis);
                new ObjectMapper().writeValue(out, patientDetail);
            }
        }
    }
}

class Error {
    String error;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}

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
        this.patientName = patientName.replaceAll("\\s+", " ").trim();
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
        this.visitOpd = visitOpd.replaceAll("\\s+", " ").trim();
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName.replaceAll("\\s+", " ").trim();
    }

    public void setNinCode(String ninCode) {
        this.ninCode = ninCode;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName.replaceAll("\\s+", " ").trim();
    }
}