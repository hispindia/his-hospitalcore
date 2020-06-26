<table id="dataTable" class="tablesorter" style="border-collapse: collapse;">
    <thead>
        <tr id="headerRowOne">
            <th rowspan="3" style="border: 1px solid black;">Sr. No.</th>
            <th rowspan="3" style="border: 1px solid black;">ICD - 10</th>
            <th rowspan="3" style="border: 1px solid black;">System/Category</th>
            <th rowspan="3" style="border: 1px solid black;">Diagnosis</th>
        </tr>
        <tr id="headerRowTwo"></tr>
        <tr id="headerRowThree"></tr>
    </thead>
    <tbody id="tableBody"></tbody>
</table>

<script type="text/javascript">
    diagnosisList = JSON.parse(`${diagnosisListJson}`);
    ageGroups = JSON.parse(`${ageGroupsJson}`);
    numberOfPatients = JSON.parse(`${numberOfPatient}`);
    ward = `${ward}`;
    console.log(ward);
    result = [];

    mapArray = {
        "Code":null,
        "Category":null,
        "Diagnosis":null,
        "Values":{
            "0-29 Days (Neonates)":{
                "NewMale":0,
                "NewFemale":0,
                "ReMale":0,
                "ReFemale":0
            },
            "Infants":{
                "NewMale":0,
                "NewFemale":0,
                "ReMale":0,
                "ReFemale":0
            },
            "Under 5":{
                "NewMale":0,
                "NewFemale":0,
                "ReMale":0,
                "ReFemale":0
            },
            "5 - 11 Years":{
                "NewMale":0,
                "NewFemale":0,
                "ReMale":0,
                "ReFemale":0
            },
            "11 - 18 Years":{
                "NewMale":0,
                "NewFemale":0,
                "ReMale":0,
                "ReFemale":0
            },
            "18 - 58 Years":{
                "NewMale":0,
                "NewFemale":0,
                "ReMale":0,
                "ReFemale":0
            },
            "58 And Above":{
                "NewMale":0,
                "NewFemale":0,
                "ReMale":0,
                "ReFemale":0
            }
        }
    }

    for (let i = 0; i < diagnosisList.length; i++) {
        var obj =  JSON.parse(JSON.stringify(mapArray));
        var valueCodes = diagnosisList[i][3].split(", ");
        for (let j = 0; j < numberOfPatients.length-1; j++) {
            var element = numberOfPatients[j];
            var encounterType = element["encounterType"];

            if (encounterType == 5 || encounterType == 6) {
                if (ward == "OPD WARD" && element["diagnosisName"] == "OUTREACH IN FIELD OPD") {
                    console.log("Not OPD")
                    continue;
                }

                if (ward == "OUTREACH OPD" && element["diagnosisName"] != "OUTREACH IN FIELD OPD" ) {
                    console.log("Not Outreach")
                    continue;
                }
                for (let k = j+1; k < numberOfPatients.length; k++) {
                    var nextElement = numberOfPatients[k];
                    var encounterType1 = nextElement["encounterType"];
                    
                        
                    if (encounterType1 == 9 || encounterType1 == 10) {
                        if (valueCodes.indexOf(nextElement["valueCode"] + "") > -1) {
                            obj.Values[nextElement["ageGroup"]][element["visitGender"]] += 1;
                        }
                    } else {
                        j = k-1;
                        break;
                    }   
                }
            }
        }
        obj.Code = diagnosisList[i][0];
        obj.Category = diagnosisList[i][1];
        obj.Diagnosis = diagnosisList[i][2];
        result.push(obj);
    }


    headerRowOne = "";
    headerRowTwo = "";
    headerRowThree = "";
    tableBody = "";
        
    ageGroups.forEach((ageGroup, index) => {
        headerRowOne += "<th colspan='4' style='border: 1px solid black;'>" + ageGroup + "</th>";
        headerRowTwo += "<th colspan='2' style='border: 1px solid black;'>New</th>";
        headerRowTwo += "<th colspan='2' style='border: 1px solid black;'>Follow Up</th>";
        for (let i = 0; i < 2; i++) {
            headerRowThree += "<th style='border: 1px solid black;'>Male</th><th style='border: 1px solid black;'>Female</th>";
        }
    });

    result.forEach((element, index) => {
        tableBody += "<tr>";
        tableBody += "<td style='border: 1px solid black;'>" + (index+1) + "</td>";
        tableBody += "<td style='border: 1px solid black;'>" + element.Code + "</td>";
        tableBody += "<td style='border: 1px solid black;'>" + element.Category + "</td>";
        tableBody += "<td style='border: 1px solid black;'>" + element.Diagnosis + "</td>";
        for (const key in element.Values) {
            if (element.Values.hasOwnProperty(key)) {
                tableBody += "<td style='border: 1px solid black;'>" + element.Values[key].NewMale + "</td>";
                tableBody += "<td style='border: 1px solid black;'>" + element.Values[key].NewFemale + "</td>";
                tableBody += "<td style='border: 1px solid black;'>" + element.Values[key].ReMale + "</td>";
                tableBody += "<td style='border: 1px solid black;'>" + element.Values[key].ReFemale + "</td>";   
            }
        }
        tableBody += "</tr>";
    });

    document.getElementById("headerRowOne").innerHTML += headerRowOne;
    document.getElementById("headerRowTwo").innerHTML += headerRowTwo;
    document.getElementById("headerRowThree").innerHTML += headerRowThree;
    document.getElementById("tableBody").innerHTML = tableBody;
    
</script>