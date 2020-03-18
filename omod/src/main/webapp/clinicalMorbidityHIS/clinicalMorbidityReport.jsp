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

    diagnosisList.forEach((element, index) => {
        tableBody += "<tr>";
        tableBody += "<td style='border: 1px solid black;'>" + (index+1) + "</td>";
        tableBody += "<td style='border: 1px solid black;'>" + element.diagnosis.code + "</td>";
        tableBody += "<td style='border: 1px solid black;'>" + element.diagnosis.category + "</td>";
        tableBody += "<td style='border: 1px solid black;'>" + element.diagnosis.classification + "</td>";
        ageGroups.forEach(ageGroup => {
            if (ageGroup in element) {
                tableBody += "<td style='border: 1px solid black;'>" + element[ageGroup].NewMale + "</td>";
                tableBody += "<td style='border: 1px solid black;'>" + element[ageGroup].NewFemale + "</td>";
                tableBody += "<td style='border: 1px solid black;'>" + element[ageGroup].ReMale + "</td>";
                tableBody += "<td style='border: 1px solid black;'>" + element[ageGroup].ReFemale + "</td>";
            } else {
                for (let i = 0; i < 4; i++) {
                    tableBody += "<td style='border: 1px solid black;'>0</td>";
                }
            }
        });
        tableBody += "</tr>";
    });

    document.getElementById("headerRowOne").innerHTML += headerRowOne;
    document.getElementById("headerRowTwo").innerHTML += headerRowTwo;
    document.getElementById("headerRowThree").innerHTML += headerRowThree;
    document.getElementById("tableBody").innerHTML = tableBody;
    
</script>