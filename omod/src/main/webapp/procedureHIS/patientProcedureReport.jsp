<%@ include file="/WEB-INF/template/include.jsp" %>
<table id="myTable" class="tablesorter" style='width: 80%;'>
<caption><font size="3" color="blue" style="text-align:center">OPD Diagnosis Report</font></caption>
<br/>
<table id="datavalu" class="tablesorter" style='width: 80%;border: 1px solid #000000;'>
<tr>
<thead>
<th style='background-color:#BEBEBE;width:145px;'>
Diagnosis
</th>
<th style='background-color:#BEBEBE;width:145px;'>
ICD -10
</th>
<th style='background-color:#BEBEBE;width:145px;'>
Male
</th>
<th style='background-color:#BEBEBE;width:145px;'>
FeMale
</th>
</thead>
</tr>
	
<tbody>
<tr>
<td>Dogbite</td>
<td>W54.0</td>
<td>${noOfMalepatientWithDogBite}</td>
<td>${noOfFemalepatientWithDogBite}</td>
</tr>
<tr>
<td>Other Bites, Stings and Inanimate/animate mechanical force</td>
<td>T63,W20-W64</td>
<td>${noOfMalepatientWithOtherBite}</td>
<td>${noOfFemalepatientWithOtherBite}</td>
</tr>
<tr>
<td>Snake Bite</td>
<td>T63.00-T63.09</td>
<td>${noOfMalepatientWithSnakeBite}</td>
<td>${noOfFemalepatientWithSnakeBite}</td>
</tr>
<tr>
<td>Acute Rheumatic Fever</td>
<td>I00-I02</td>
<td>${noOfMalepatientWithRheumatic}</td>
<td>${noOfFemalepatientWithRheumatic}</td>
</tr>
<tr>
<td>Cardiac Arrhythmia, Cardiac Block</td>
<td>I44 - I49</td>
<td>${noOfMalepatientWithBigemany}</td>
<td>${noOfFemalepatientWithBigemany}</td>
</tr>
<tr>
<td>Congestive Cardiac Failure/Heart Failure</td>
<td>I50</td>
<td>${noOfMalepatientWithCardiac}</td>
<td>${noOfFemalepatientWithCardiac}</td>
</tr>
<tr>
<td>Deep Vein Thrombosis</td>
<td>I82</td>
<td>${noOfMalepatientWithVenous}</td>
<td>${noOfFemalepatientWithVenous}</td>
</tr>
<tr>
<td>Hypertension and Complications</td>
<td>I10 - I15, H35.03</td>
<td>${noOfMalepatientWithHypertension}</td>
<td>${noOfFemalepatientWithHypertension}</td>
</tr>
<tr>
<td>Ischemic Heart Diseases (include Angina, Myocardial infartion and complications)</td>
<td>I20-I25</td>
<td>${noOfMalepatientWithIschemic}</td>
<td>${noOfFemalepatientWithIschemic}</td>
</tr>
<tr>
<td>Rheumatic Heart Disease</td>
<td>I05 - I09</td>
<td>${noOfMalepatientWithRheumaticHeart}</td>
<td>${noOfFemalepatientWithRheumaticHeart}</td>
</tr>
<tr>
<td>Vericose Vein</td>
<td>I83, 185, 186</td>
<td>${noOfMalepatientWithVericoseVein}</td>
<td>${noOfFemalepatientWithVericoseVein}</td>
</tr>
<tr>
<td>Other Cardiovascular Disorder</td>
<td>I00-I99</td>
<td>${noOfMalepatientWithOtherCardiovascular}</td>
<td>${noOfFemalepatientWithOtherCardiovascular}</td>
</tr>
<tr>
<td>Lymphadenitis</td>
<td>I88, L04</td>
<td>${noOfMalepatientWithLymphadenitis}</td>
<td>${noOfFemalepatientWithLymphadenitis}</td>
</tr>
<tr>
<td>Cerebral Palsy</td>
<td>G80</td>
<td>${noOfMalepatientWithCerebralPalsy}</td>
<td>${noOfFemalepatientWithCerebralPalsy}</td>
</tr>
<tr>
<td>Encephalitis</td>
<td>G04</td>
<td>${noOfMalepatientWithEncephalitis}</td>
<td>${noOfFemalepatientWithEncephalitis}</td>
</tr>
<tr>
<td>Epilepsy/Seizure</td>
<td>G40 - G47</td>
<td>${noOfMalepatientWithEpilepsy}</td>
<td>${noOfFemalepatientWithEpilepsy}</td>
</tr>
<tr>
<td>Hemiparesis/Hemiplegia</td>
<td>G81</td>
<td>${noOfMalepatientWithHemiparesis}</td>
<td>${noOfFemalepatientWithHemiparesis}</td>
</tr>
<tr>
<td>Hydrocephalus</td>
<td>G91</td>
<td>${noOfMalepatientWithHydrocephalus}</td>
<td>${noOfFemalepatientWithHydrocephalus}</td>
</tr>
<tr>
<td>Insomnia</td>
<td>G47</td>
<td>${noOfMalepatientWithInsomnia}</td>
<td>${noOfFemalepatientWithInsomnia}</td>
</tr>
<tr>
<td>Migraine/Cluster Headache</td>
<td>G43, G44</td>
<td>${noOfMalepatientWithMigraine}</td>
<td>${noOfFemalepatientWithMigraine}</td>
</tr>
<tr>
<td>Other Nervous System Disorder</td>
<td>G00 - G99</td>
<td>${noOfMalepatientWithOtherNervous}</td>
<td>${noOfFemalepatientWithOtherNervous}</td>
</tr>
<tr>
<td>Paraplegia(Paraperesis)/Quadriplegia(Quadriperesis)</td>
<td>G82</td>
<td>${noOfMalepatientWithParaplegia}</td>
<td>${noOfFemalepatientWithParaplegia}</td>
</tr>
<tr>
<td>Parkinsonism</td>
<td>G20, G21</td>
<td>${noOfMalepatientWithParkinsonism}</td>
<td>${noOfFemalepatientWithParkinsonism}</td>
</tr>
<tr>
<td>Cerebro Vascular Accident (Stroke)</td>
<td>I63.9</td>
<td>${noOfMalepatientWithCerebro}</td>
<td>${noOfFemalepatientWithCerebro}</td>
</tr>
<tr>
<td>Vertigo/Dizziness/Giddiness</td>
<td>H81, R42</td>
<td>${noOfMalepatientWithVertigo}</td>
<td>${noOfFemalepatientWithVertigo}</td>
</tr>
<tr>
<td>Congenital Disorders</td>
<td>Q00-Q99</td>
<td>${noOfMalepatientWithCongenitalDisorders}</td>
<td>${noOfFemalepatientWithCongenitalDisorders}</td>
</tr>
<tr>
<td>Congenital Heart Disese</td>
<td>Q20-Q28</td>
<td>${noOfMalepatientWithCongenitalHeart}</td>
<td>${noOfFemalepatientWithCongenitalHeart}</td>
</tr>
<tr>
<td>Dental Conditions</td>
<td>K00-K08</td>
<td>${noOfMalepatientWithDental}</td>
<td>${noOfFemalepatientWithDental}</td>
</tr>
<tr>
<td>Dental Caries</td>
<td>K02</td>
<td>${noOfMalepatientWithCaries}</td>
<td>${noOfFemalepatientWithCaries}</td>
</tr>
<tr>
<td>Acne</td>
<td>L70</td>
<td>${noOfMalepatientWithAcne}</td>
<td>${noOfFemalepatientWithAcne}</td>
</tr>
<tr>
<td>Cutaneous Abscess, Furuncle and Carbuncle</td>
<td>L02</td>
<td>${noOfMalepatientWithCutaneous}</td>
<td>${noOfFemalepatientWithCutaneous}</td>
</tr>
<tr>
<td>Dermatitis and Eczema</td>
<td>L20 - L30</td>
<td>${noOfMalepatientWithDermatitis}</td>
<td>${noOfFemalepatientWithDermatitis}</td>
</tr>
<tr>
<td>Herpes Zoster and Complications</td>
<td>B02</td>
<td>${noOfMalepatientWithHerpes}</td>
<td>${noOfFemalepatientWithHerpes}</td>
</tr>
<tr>
<td>Impetigo/Pyoderma</td>
<td>L01, L08.0</td>
<td>${noOfMalepatientWithImpetigo}</td>
<td>${noOfFemalepatientWithImpetigo}</td>
</tr>
<tr>
<td>Other Skin Diseases</td>
<td>L00-L99</td>
<td>${noOfMalepatientWithOtherSkin}</td>
<td>${noOfFemalepatientWithOtherSkin}</td>
</tr>
<tr>
<td>Pruritus</td>
<td>L29</td>
<td>${noOfMalepatientWithPruritus}</td>
<td>${noOfFemalepatientWithPruritus}</td>
</tr>
<tr>
<td>Scabies</td>
<td>B86</td>
<td>${noOfMalepatientWithScabies}</td>
<td>${noOfFemalepatientWithScabies}</td>
</tr>
<tr>
<td>Tinea</td>
<td>B35</td>
<td>${noOfMalepatientWithTinea}</td>
<td>${noOfFemalepatientWithTinea}</td>
</tr>
<tr>
<td>Tinea Versicolor</td>
<td>B36.0</td>
<td>${noOfMalepatientWithVersicolor}</td>
<td>${noOfFemalepatientWithVersicolor}</td>
</tr>
<tr>
<td>Urticaria & Erythemia</td>
<td>L49 - L54</td>
<td>${noOfMalepatientWithUrticaria}</td>
<td>${noOfFemalepatientWithUrticaria}</td>
</tr>
<tr>
<td>Vitiligo</td>
<td>L80</td>
<td>${noOfMalepatientWithVitiligo}</td>
<td>${noOfFemalepatientWithVitiligo}</td>
</tr>
<tr>
<td>Wart</td>
<td>B07</td>
<td>${noOfMalepatientWithWart}</td>
<td>${noOfFemalepatientWithWart}</td>
</tr>
<tr>
<td>Dracunculiasis&Guinea Worm Disease</td>
<td>B72</td>
<td>${noOfMalepatientWithDracunculiasis}</td>
<td>${noOfFemalepatientWithDracunculiasis}</td>
</tr>
<tr>
<td>Acute Diarrheal Disease (including acute gastroenteritis)</td>
<td>B72</td>
<td>${noOfMalepatientWithDiarrhea}</td>
<td>${noOfFemalepatientWithDiarrhea}</td>
</tr>
<tr>
<td>Acute Pancreatitis</td>
<td>K85</td>
<td>${noOfMalepatientWithAcutePancreatitis}</td>
<td>${noOfFemalepatientWithAcutePancreatitis}</td>
</tr>
<tr>
<td>Appendicitis</td>
<td>K35-K38</td>
<td>${noOfMalepatientWithAppendicitis}</td>
<td>${noOfFemalepatientWithAppendicitis}</td>
</tr>
<tr>
<td>Bleeding from Rectum & Anus</td>
<td>K62.5</td>
<td>${noOfMalepatientWithBleeding}</td>
<td>${noOfFemalepatientWithBleeding}</td>
</tr>
<tr>
<td>Cholecystitis</td>
<td>K81</td>
<td>${noOfMalepatientWithCholecystitis}</td>
<td>${noOfFemalepatientWithCholecystitis}</td>
</tr>
<tr>
<td>Diseases of Oral Cavity and Salivary Glands</td>
<td>K09-K14</td>
<td>${noOfMalepatientWithDiseasesOral}</td>
<td>${noOfFemalepatientWithDiseasesOral}</td>
</tr>
<tr>
<td>Fissure&Fistula of Anus, Rectum etc</td>
<td>K09-K14</td>
<td>${noOfMalepatientWithFissure}</td>
<td>${noOfFemalepatientWithFissure}</td>
</tr>
<tr>
<td>Intestinal Obstruction</td>
<td>K56</td>
<td>${noOfMalepatientWithIntestinalObstruction}</td>
<td>${noOfFemalepatientWithIntestinalObstruction}</td>
</tr>
<tr>
<td>Noninfective Gastroenteritis and Colitis (inlcuding Crohn's Dsease and Ulcerative Colitis)</td>
<td>K50-K52</td>
<td>${noOfMalepatientWithNoninfectiveGastroenteritis}</td>
<td>${noOfFemalepatientWithNoninfectiveGastroenteritis}</td>
</tr>
<tr>
<td>Other Diseases of Liver Including Chronic Liver Disease/Cirrhosis and Liver Failure</td>
<td>K70-K77</td>
<td>${noOfMalepatientWithChronicLiver}</td>
<td>${noOfFemalepatientWithChronicLiver}</td>
</tr>
<tr>
<td>Pancreatitis</td>
<td>K85, K86</td>
<td>${noOfMalepatientWithPancreatitis}</td>
<td>${noOfFemalepatientWithPancreatitis}</td>
</tr>
<tr>
<td>Peptic Ulcer</td>
<td>K27</td>
<td>${noOfMalepatientWithPepticUlcer}</td>
<td>${noOfFemalepatientWithPepticUlcer}</td>
</tr>
<tr>
<td>Peritonitis</td>
<td>K65</td>
<td>${noOfMalepatientWithPeritonitis}</td>
<td>${noOfFemalepatientWithPeritonitis}</td>
</tr>
<tr>
<td>Hydated Cyst of Liver</td>
<td>B67</td>
<td>${noOfMalepatientWithHydatedCystLiver}</td>
<td>${noOfFemalepatientWithHydatedCystLiver}</td>
</tr>
<tr>
<td>Cholelithiasis&Gallstone</td>
<td>K80</td>
<td>${noOfMalepatientWithCholelithiasis}</td>
<td>${noOfFemalepatientWithCholelithiasis}</td>
</tr>
<tr>
<td>Gastritis&Duodenitis</td>
<td>K29</td>
<td>${noOfMalepatientWithGastritis}</td>
<td>${noOfFemalepatientWithGastritis}</td>
</tr>
<tr>
<td>Haemorrhoids&Piles</td>
<td>K64</td>
<td>${noOfMalepatientWithHaemorrhoids}</td>
<td>${noOfFemalepatientWithHaemorrhoids}</td>
</tr>
<tr>
<td>Hernia</td>
<td>K40-K46</td>
<td>${noOfMalepatientWithHernia}</td>
<td>${noOfFemalepatientWithHernia}</td>
</tr>
<tr>
<td>Other GIT Disorder</td>
<td>K14-K95</td>
<td>${noOfMalepatientWithGITDisorder}</td>
<td>${noOfFemalepatientWithGITDisorder}</td>
</tr>
<tr>
<td>Heartburn</td>
<td>R12</td>
<td>${noOfMalepatientWithHeartburn}</td>
<td>${noOfFemalepatientWithHeartburn}</td>
</tr>
<tr>
<td>Dysentery Amoebic&Amoebiasis</td>
<td>A06</td>
<td>${noOfMalepatientWithAmoebic}</td>
<td>${noOfFemalepatientWithAmoebic}</td>
</tr>
<tr>
<td>Oral Candidiasis</td>
<td>B37</td>
<td>${noOfMalepatientWithCandidiasis}</td>
<td>${noOfFemalepatientWithCandidiasis}</td>
</tr>
<tr>
<td>Dysentery Bacillary</td>
<td>A03</td>
<td>${noOfMalepatientWithBacillary}</td>
<td>${noOfFemalepatientWithBacillary}</td>
</tr>
<tr>
<td>Dysentery</td>
<td>A03, A06</td>
<td>${noOfMalepatientWithDysentery}</td>
<td>${noOfFemalepatientWithDysentery}</td>
</tr>
<tr>
<td>Cretinism</td>
<td>E00</td>
<td>${noOfMalepatientWithCretinism}</td>
<td>${noOfFemalepatientWithCretinism}</td>
</tr>
<tr>
<td>Cystic Fibrosis</td>
<td>E84</td>
<td>${noOfMalepatientWithCystic}</td>
<td>${noOfFemalepatientWithCystic}</td>
</tr>
<tr>
<td>Other Disorder of Thyroid Gland</td>
<td>E00-E07</td>
<td>${noOfMalepatientWithThyroidGland}</td>
<td>${noOfFemalepatientWithThyroidGland}</td>
</tr>
<tr>
<td>Dehydration</td>
<td>E86</td>
<td>${noOfMalepatientWithDehydration}</td>
<td>${noOfFemalepatientWithDehydration}</td>
</tr>
<tr>
<td>Diabetes Mellitus Type 1</td>
<td>E10</td>
<td>${noOfMalepatientWithMellitusType1}</td>
<td>${noOfFemalepatientWithMellitusType1}</td>
</tr>
<tr>
<td>Diabetes Mellitus Type 2</td>
<td>E11</td>
<td>${noOfMalepatientWithMellitusType2}</td>
<td>${noOfFemalepatientWithMellitusType2}</td>
</tr>
<tr>
<td>Endemic Goiter and Other Iodine Deficinecy Disorder</td>
<td>E04</td>
<td>${noOfMalepatientWithEndemicGoiter}</td>
<td>${noOfFemalepatientWithEndemicGoiter}</td>
</tr>
<tr>
<td>Hyperthyroidism Unspecified</td>
<td>E05.9</td>
<td>${noOfMalepatientWithHyperthyroidism}</td>
<td>${noOfFemalepatientWithHyperthyroidism}</td>
</tr>
<tr>
<td>Hypothyroidism Unspecified</td>
<td>E03.9</td>
<td>${noOfMalepatientWithHypothyroidism}</td>
<td>${noOfFemalepatientWithHypothyroidism}</td>
</tr>
<tr>
<td>Malnutrition</td>
<td>E40-E46</td>
<td>${noOfMalepatientWithMalnutrition}</td>
<td>${noOfFemalepatientWithMalnutrition}</td>
</tr>
<tr>
<td>Other Diabetes Mellitus (Including Complication)</td>
<td>E8-E13</td>
<td>${noOfMalepatientWithDiabetesMellitus}</td>
<td>${noOfFemalepatientWithDiabetesMellitus}</td>
</tr>
<tr>
<td>Spondylopathies</td>
<td>M45-M49</td>
<td>${noOfMalepatientWithSpondylopathies}</td>
<td>${noOfFemalepatientWithSpondylopathies}</td>
</tr>
<tr>
<td>Acute Osteomyelitis</td>
<td></td>
<td>0</td>
<td>0</td>
</tr>
<tr>
<td>Chronic Osteomyelitis</td>
<td>M86</td>
<td>${noOfMalepatientWithChronicOsteomyelitis}</td>
<td>${noOfFemalepatientWithChronicOsteomyelitis}</td>
</tr>
<tr>
<td>Fractures</td>
<td>S01-S99</td>
<td>${noOfMalepatientWithFractures}</td>
<td>${noOfFemalepatientWithFractures}</td>
</tr>
<tr>
<td>Cancer Breast</td>
<td>C50</td>
<td>${noOfMalepatientWithCancerBreast}</td>
<td>${noOfFemalepatientWithCancerBreast}</td>
</tr>
<tr>
<td>Cancer Bronchus/Lung</td>
<td>C34</td>
<td>${noOfMalepatientWithCancerBronchus}</td>
<td>${noOfFemalepatientWithCancerBronchus}</td>
</tr>
<tr>
<td>Cancer Cervix</td>
<td>C53</td>
<td>${noOfMalepatientWithCancerCervix}</td>
<td>${noOfFemalepatientWithCancerCervix}</td>
</tr>
<tr>
<td>Cancer Liver</td>
<td>C22</td>
<td>${noOfMalepatientWithCancerLiver}</td>
<td>${noOfFemalepatientWithCancerLiver}</td>
</tr>
<tr>
<td>Cancer Oesophagus</td>
<td>C15</td>
<td>${noOfMalepatientWithOesophagus}</td>
<td>${noOfFemalepatientWithOesophagus}</td>
</tr>
<tr>
<td>Cancer Oral Cavity</td>
<td>C00-C14</td>
<td>${noOfMalepatientWithOralCavity}</td>
<td>${noOfFemalepatientWithOralCavity}</td>
</tr>
<tr>
<td>Cancer Stomach</td>
<td>C16</td>
<td>${noOfMalepatientWithStomach}</td>
<td>${noOfFemalepatientWithStomach}</td>
</tr>
<tr>
<td>Cancer Uterus</td>
<td>C54</td>
<td>${noOfMalepatientWithUterus}</td>
<td>${noOfFemalepatientWithUterus}</td>
</tr>
<tr>
<td>Neoplasm Benign</td>
<td>D10-D36</td>
<td>${noOfMalepatientWithNeoplasm}</td>
<td>${noOfFemalepatientWithNeoplasm}</td>
</tr>
<tr>
<td>Other Neoplasm</td>
<td>C00-D49</td>
<td>${noOfMalepatientWithOtherNeoplasm}</td>
<td>${noOfFemalepatientWithOtherNeoplasm}</td>
</tr>
<tr>
<td>Choriocarcinoma</td>
<td>C58</td>
<td>${noOfMalepatientWithChoriocarcinoma}</td>
<td>${noOfFemalepatientWithChoriocarcinoma}</td>
</tr>
<tr>
<td>Malignant Hydatidiform Mole</td>
<td>D32.9</td>
<td>${noOfMalepatientWithMalignant}</td>
<td>${noOfFemalepatientWithMalignant}</td>
</tr>
<tr>
<td>Alcohol or Other Substance Use Disorder</td>
<td>F10-F19</td>
<td>${noOfMalepatientWithAlcohol}</td>
<td>${noOfFemalepatientWithAlcohol}</td>
</tr>
<tr>
<td>Mood Disorder (Mania, Bipolar, Major Depression)</td>
<td>F30-F39</td>
<td>${noOfMalepatientWithMoodDisorder}</td>
<td>${noOfFemalepatientWithMoodDisorder}</td>
</tr>
<tr>
<td>Other Psychiatric, Behavioral, Neuro developmental Disorder</td>
<td>F01-F99</td>
<td>${noOfMalepatientWithPsychiatric}</td>
<td>${noOfFemalepatientWithPsychiatric}</td>
</tr>
<tr>
<td>Psychotic Disorder including Schzophrenia</td>
<td>F20-F29</td>
<td>${noOfMalepatientWithSchzophrenia}</td>
<td>${noOfFemalepatientWithSchzophrenia}</td>
</tr>
<tr>
<td>Dementia</td>
<td>F01-F03, G30, G31</td>
<td>${noOfMalepatientWithDementia}</td>
<td>${noOfFemalepatientWithDementia}</td>
</tr>
<tr>
<td>Senile Dementia</td>
<td>F03, G31</td>
<td>${noOfMalepatientWithSenile}</td>
<td>${noOfFemalepatientWithSenile}</td>
</tr>
<tr>
<td>Mental Retardation/Intellectual Disability</td>
<td>F70-F79</td>
<td>${noOfMalepatientWithRetardation}</td>
<td>${noOfFemalepatientWithRetardation}</td>
</tr>
<tr>
<td>Anxiety, Dissociative, Stress related, Somatic & Other Non-Psychotic Mental Disorders</td>
<td>F40-F48</td>
<td>${noOfMalepatientWithMental}</td>
<td>${noOfFemalepatientWithMental}</td>
</tr>
<tr>
<td>Asthma</td>
<td>J45</td>
<td>${noOfMalepatientWithAsthma}</td>
<td>${noOfFemalepatientWithAsthma}</td>
</tr>
<tr>
<td>Bronchiectasis</td>
<td>J47</td>
<td>${noOfMalepatientWithBronchiectasis}</td>
<td>${noOfFemalepatientWithBronchiectasis}</td>
</tr>
<tr>
<td>Chronic Lower Respiratory Tract Infection including Chronic Bronchitis and Emphysema</td>
<td>J40-J47</td>
<td>${noOfMalepatientWithLowerRespiratory}</td>
<td>${noOfFemalepatientWithLowerRespiratory}</td>
</tr>
<tr>
<td>Acute Bronchitis/Bronchiolitis</td>
<td>J20-J21</td>
<td>${noOfMalepatientWithBronchiolitis}</td>
<td>${noOfFemalepatientWithBronchiolitis}</td>
</tr>
<tr>
<td>Pneumonia & Bronchoneumonia</td>
<td>J12-J18</td>
<td>${noOfMalepatientWithBronchoneumonia}</td>
<td>${noOfFemalepatientWithBronchoneumonia}</td>
</tr>
<tr>
<td>Other Respiratory Disorder</td>
<td>J00-J99</td>
<td>${noOfMalepatientWithRespiratory}</td>
<td>${noOfFemalepatientWithRespiratory}</td>
</tr>
<tr>
<td>Physiotherapy</td>
<td>Z51.89</td>
<td>${noOfMalepatientWithPhysiotherapy}</td>
<td>${noOfFemalepatientWithPhysiotherapy}</td>
</tr>
<tr>
<td>Person Encountering Health Services for Dressing, Injection and Other Services</td>
<td>Z00-Z99</td>
<td>${noOfMalepatientWithHealthServices}</td>
<td>${noOfFemalepatientWithHealthServices}</td>
</tr>
<tr>
<td>Gonococcus Infection</td>
<td>A54</td>
<td>${noOfMalepatientWithGonococcus}</td>
<td>${noOfFemalepatientWithGonococcus}</td>
</tr>
<tr>
<td>Herpes Simplex (Anogenital)</td>
<td>A60</td>
<td>${noOfMalepatientWithAnogenital}</td>
<td>${noOfFemalepatientWithAnogenital}</td>
</tr>
<tr>
<td>Other Sexually Transmitted Infection (STI)</td>
<td>A50-A64</td>
<td>${noOfMalepatientWithTransmitted}</td>
<td>${noOfFemalepatientWithTransmitted}</td>
</tr>
<tr>
<td>Syphilis</td>
<td>A50-A53</td>
<td>${noOfMalepatientWithSyphilis}</td>
<td>${noOfFemalepatientWithSyphilis}</td>
</tr>
<tr>
<td>Chlamydia/Trichomonas Valvovaginitis, Cystitis, Urethritis</td>
<td>A56, A59</td>
<td>${noOfMalepatientWithChlamydia}</td>
<td>${noOfFemalepatientWithChlamydia}</td>
</tr>
<tr>
<td>Genital Ulcer (Chancroid, Granuloma Inguinale/Donovanosis, Lymphogranuloma Venerum)</td>
<td>A55, A57, A58</td>
<td>${noOfMalepatientWithGenitalUlcer}</td>
<td>${noOfFemalepatientWithGenitalUlcer}</td>
</tr>
<tr>
<td>Urethral Discharge</td>
<td>R36, A50-A64</td>
<td>${noOfMalepatientWithUrethral}</td>
<td>${noOfFemalepatientWithUrethral}</td>
</tr>
<tr>
<td>Chikungunya</td>
<td>A92.0</td>
<td>${noOfMalepatientWithChikungunya}</td>
<td>${noOfFemalepatientWithChikungunya}</td>
</tr>
<tr>
<td>Dengue/DHF/DSS/Yellow Fever</td>
<td>A90-A91</td>
<td>${noOfMalepatientWithDengue}</td>
<td>${noOfFemalepatientWithDengue}</td>
</tr>
<tr>
<td>Filariasis</td>
<td>B34</td>
<td>${noOfMalepatientWithFilariasis}</td>
<td>${noOfFemalepatientWithFilariasis}</td>
</tr>
<tr>
<td>Japanese Encephalitis</td>
<td>A83.0</td>
<td>${noOfMalepatientWithJapaneseEncephalitis}</td>
<td>${noOfFemalepatientWithJapaneseEncephalitis}</td>
</tr>
<tr>
<td>Kala Azar/Leishmaniasis</td>
<td>B55</td>
<td>${noOfMalepatientWithKalaAzar}</td>
<td>${noOfFemalepatientWithKalaAzar}</td>
</tr>
<tr>
<td>Malaria</td>
<td>B50-B54</td>
<td>${noOfMalepatientWithMalaria}</td>
<td>${noOfFemalepatientWithMalaria}</td>
</tr>
<tr>
<td>Low Birth Weight</td>
<td>P07</td>
<td>${noOfMalepatientWithBirthWeight}</td>
<td>${noOfFemalepatientWithBirthWeight}</td>
</tr>
<tr>
<td>Umbilical Sepsis/Bacterial Sepsis of New Born</td>
<td>P36</td>
<td>${noOfMalepatientWithUmbilical}</td>
<td>${noOfFemalepatientWithUmbilical}</td>
</tr>
<tr>
<td>Other Newborn conditions effected during perinatal period</td>
<td>P00-P96</td>
<td>${noOfMalepatientWithNewborn}</td>
<td>${noOfFemalepatientWithNewborn}</td>
</tr>
<tr>
<td>Tetanus Neonatorum</td>
<td>A33</td>
<td>${noOfMalepatientWithNeonatorum}</td>
<td>${noOfFemalepatientWithNeonatorum}</td>
</tr>
<tr>
<td>Birth Trauma to Newborn</td>
<td>P10-P15</td>
<td>${noOfMalepatientWithBirthTrauma}</td>
<td>${noOfFemalepatientWithBirthTrauma}</td>
</tr>
<tr>
<td>Ectopic Pregnancy (include ruptured ectopic pregnancy)</td>
<td>O00</td>
<td>${noOfMalepatientWithEctopic}</td>
<td>${noOfFemalepatientWithEctopic}</td>
</tr>
<tr>
<td>Edema, Protenuria, and Hypertensive Disorder in Pregnancy, Childbirth & Puerperium</td>
<td>O10-O16</td>
<td>${noOfMalepatientWithEdemaProtenuria}</td>
<td>${noOfFemalepatientWithEdemaProtenuria}</td>
</tr>
<tr>
<td>Spontaneous Abortion</td>
<td>O03</td>
<td>${noOfMalepatientWithSpontaneous}</td>
<td>${noOfFemalepatientWithSpontaneous}</td>
</tr>
<tr>
<td>Postpartum Hemorrhage (PPH)</td>
<td>O72</td>
<td>${noOfMalepatientWithPostpartum}</td>
<td>${noOfFemalepatientWithPostpartum}</td>
</tr>
<tr>
<td>Puerperal Sepsis & Other Puerperal Infections</td>
<td></td>
<td>0</td>
<td>0</td>
</tr>
<tr>
<td>Antepartum Hemorrhage (Placenta Previa/Abruptio Placenta and others)</td>
<td>O44,O45,O20</td>
<td>${noOfMalepatientWithHemorrhage}</td>
<td>${noOfFemalepatientWithHemorrhage}</td>
</tr>
<tr>
<td>Other Maternal & Obstetric Conditions Not Specified</td>
<td>O00-O9A</td>
<td>${noOfMalepatientWithMaternal}</td>
<td>${noOfFemalepatientWithMaternal}</td>
</tr>
<tr>
<td>Other Complication of Labour and Delivery</td>
<td>O60-O77</td>
<td>${noOfMalepatientWithLabour}</td>
<td>${noOfFemalepatientWithLabour}</td>
</tr>
<tr>
<td>Pre-eclampsia/Eclampsia</td>
<td>O14-O15</td>
<td>${noOfMalepatientWithEclampsia}</td>
<td>${noOfFemalepatientWithEclampsia}</td>
</tr>
<tr>
<td>Excessive Vomitting in Pregnancy</td>
<td>O21</td>
<td>${noOfMalepatientWithVomitting}</td>
<td>${noOfFemalepatientWithVomitting}</td>
</tr>
<tr>
<td>Other Pregnancy Conditions with Abortive Outcome (include Hydatidiform Mole)</td>
<td>O00-O08</td>
<td>${noOfMalepatientWithAbortive}</td>
<td>${noOfFemalepatientWithAbortive}</td>
</tr>
<tr>
<td>Preterm Labour</td>
<td>O60</td>
<td>${noOfMalepatientWithPreterm}</td>
<td>${noOfFemalepatientWithPreterm}</td>
</tr>
<tr>
<td>Maternal Infections, Parasitic Diseases Classified elsewhere but Complicating Pregnancy, Childbirth and Puerperium</td>
<td>O98</td>
<td>${noOfMalepatientWithParasitic}</td>
<td>${noOfFemalepatientWithParasitic}</td>
</tr>
<tr>
<td>Diabetes Mellitus in Pregnancy, Childbirth and Puerperium</td>
<td>O24</td>
<td>${noOfMalepatientWithPuerperium}</td>
<td>${noOfFemalepatientWithPuerperium}</td>
</tr>
<tr>
<td>Malpresentation of Foetus</td>
<td></td>
<td>0</td>
<td>0</td>
</tr>
<tr>
<td>Multiple Gestations</td>
<td></td>
<td>0</td>
<td>0</td>
</tr>
<tr>
<td>Twin Pregnancy</td>
<td></td>
<td>0</td>
<td>0</td>
</tr>
<tr>
<td>Maternal Care for Disproportion including Cephalo-pelvic Disproportion</td>
<td>O33</td>
<td>${noOfMalepatientWithMaternal}</td>
<td>${noOfFemalepatientWithMaternal}</td>
</tr>
<tr>
<td>Teenage Pregnancy</td>
<td></td>
<td>0</td>
<td>0</td>
</tr>
<tr>
<td>Early Pregnancy</td>
<td></td>
<td>0</td>
<td>0</td>
</tr>
<tr>
<td>Encounter for Pregnacy Test/Pregnancy State</td>
<td>Z32.0, Z33</td>
<td>${noOfMalepatientWithPregnacyTest}</td>
<td>${noOfFemalepatientWithPregnacyTest}</td>
</tr>
<tr>
<td>Encounter for Rh Incompatibility Status</td>
<td>Z31.82</td>
<td>${noOfMalepatientWithRhIncompatibility}</td>
<td>${noOfFemalepatientWithRhIncompatibility}</td>
</tr>
<tr>
<td>False Labour</td>
<td>O47.9</td>
<td>${noOfMalepatientWithFalseLabour}</td>
<td>${noOfFemalepatientWithFalseLabour}</td>
</tr>
<tr>
<td>Breech Delivery</td>
<td></td>
<td>0</td>
<td>0</td>
</tr>
<tr>
<td>Nephrotic Syndrome</td>
<td>N04</td>
<td>${noOfMalepatientWithNephrotic}</td>
<td>${noOfFemalepatientWithNephrotic}</td>
</tr>
<tr>
<td>Other Breast Disorder</td>
<td>N60-N65</td>
<td>${noOfMalepatientWithOtherBreas}</td>
<td>${noOfFemalepatientWithOtherBreas}</td>
</tr>
<tr>
<td>Other Genitourinary Disorder</td>
<td>N00-N99</td>
<td>${noOfMalepatientWithGenitourinary}</td>
<td>${noOfFemalepatientWithGenitourinary}</td>
</tr>
<tr>
<td>Phimosis/Paraphimosis</td>
<td>N47</td>
<td>${noOfMalepatientWithParaphimosis}</td>
<td>${noOfFemalepatientWithParaphimosis}</td>
</tr>
<tr>
<td>Urinary Tract Infection</td>
<td>N39.0</td>
<td>${noOfMalepatientWithTract}</td>
<td>${noOfFemalepatientWithTract}</td>
</tr>
<tr>
<td>Urolithiasis</td>
<td>N20-N23</td>
<td>${noOfMalepatientWithUrolithiasis}</td>
<td>${noOfFemalepatientWithUrolithiasis}</td>
</tr>
<tr>
<td>Dysmenorrhoea</td>
<td>N94.4-N94.6</td>
<td>${noOfMalepatientWithDysmenorrhoea}</td>
<td>${noOfFemalepatientWithDysmenorrhoea}</td>
</tr>
<tr>
<td>Other Gynaecological Disorder</td>
<td>N70-N99</td>
<td>${noOfMalepatientWithOtherGynaecologicalDisorder}</td>
<td>${noOfFemalepatientWithOtherGynaecologicalDisorder}</td>
</tr>
<tr>
<td>Uterine Fibroid</td>
<td>D25</td>
<td>${noOfMalepatientWithUterineFibroid}</td>
<td>${noOfFemalepatientWithUterineFibroid}</td>
</tr>
<tr>
<td>Amenorrhoea</td>
<td>N91</td>
<td>${noOfMalepatientWithAmenorrhoe}</td>
<td>${noOfFemalepatientWithAmenorrhoe}</td>
</tr>
<tr>
<td>Female Genital Prolapse</td>
<td>N81</td>
<td>${noOfMalepatientWithGenital}</td>
<td>${noOfFemalepatientWithGenital}</td>
</tr>
<tr>
<td>Menorrhagia</td>
<td>N92</td>
<td>${noOfMalepatientWithMenorrhagia}</td>
<td>${noOfFemalepatientWithMenorrhagia}</td>
</tr>
<tr>
<td>Ovarian Cyst Unspecified</td>
<td>N83.20</td>
<td>${noOfMalepatientWithOvarian}</td>
<td>${noOfFemalepatientWithOvarian}</td>
</tr>
<tr>
<td>Uterine and Vaginal Bleeding Unspecified</td>
<td>N93.9</td>
<td>${noOfMalepatientWithVaginal}</td>
<td>${noOfFemalepatientWithVaginal}</td>
</tr>
<tr>
<td>Vaginitis/Vulvitis</td>
<td>N76, B37.3</td>
<td>${noOfMalepatientWithVaginitis}</td>
<td>${noOfFemalepatientWithVaginitis}</td>
</tr>
<tr>
<td>Leucorrhoea</td>
<td>N89.8</td>
<td>${noOfMalepatientWithLeucorrhoea}</td>
<td>${noOfFemalepatientWithLeucorrhoea}</td>
</tr>
<tr>
<td>Female Pelvic Inflamatory Diseases</td>
<td>N70-N77</td>
<td>${noOfMalepatientWithPelvic}</td>
<td>${noOfFemalepatientWithPelvic}</td>
</tr>
<tr>
<td>Anaemia</td>
<td>D50-D64</td>
<td>${noOfMalepatientWithAnaemia}</td>
<td>${noOfFemalepatientWithAnaemia}</td>
</tr>
<tr>
<td>Other Diseases of Blood & Blood Forming Organs & certain Disorders Involving Immune Mechanism</td>
<td>D50-D89</td>
<td>${noOfMalepatientWithImmune}</td>
<td>${noOfFemalepatientWithImmune}</td>
</tr>
<tr>
<td>Purpura and other hemorrhagic conditions including Thrombocytopenia</td>
<td>D69</td>
<td>${noOfMalepatientWithhemorrhagic}</td>
<td>${noOfFemalepatientWithhemorrhagic}</td>
</tr>
<tr>
<td>Anthrax</td>
<td>A22</td>
<td>${noOfMalepatientWithAnthrax}</td>
<td>${noOfFemalepatientWithAnthrax}</td>
</tr>
<tr>
<td>Diphtheria</td>
<td>A36</td>
<td>${noOfMalepatientWithDiphtheria}</td>
<td>${noOfFemalepatientWithDiphtheria}</td>
</tr>
<tr>
<td>HIV/AIDS</td>
<td>B20</td>
<td>${noOfMalepatientWithHIV}</td>
<td>${noOfFemalepatientWithHIV}</td>
</tr>
<tr>
<td>Measles</td>
<td>B05</td>
<td>${noOfMalepatientWithMeasles}</td>
<td>${noOfFemalepatientWithMeasles}</td>
</tr>
<tr>
<td>Other Infectious & Parasitic Diseases</td>
<td>A00-B99</td>
<td>${noOfMalepatientWithInfectious}</td>
<td>${noOfFemalepatientWithInfectious}</td>
</tr>
<tr>
<td>Plague</td>
<td>A20</td>
<td>${noOfMalepatientWithPlague}</td>
<td>${noOfFemalepatientWithPlague}</td>
</tr>
<tr>
<td>Septicemia/Severe Sepsis</td>
<td>R65.2, A41.9</td>
<td>${noOfMalepatientWithSevereSepsis}</td>
<td>${noOfFemalepatientWithSevereSepsis}</td>
</tr>
<tr>
<td>Tuberculosis</td>
<td>A15-A19</td>
<td>${noOfMalepatientWithTuberculosis}</td>
<td>${noOfFemalepatientWithTuberculosis}</td>
</tr>
<tr>
<td>Worm Infestation</td>
<td>B65-B83</td>
<td>${noOfMalepatientWithInfestation}</td>
<td>${noOfFemalepatientWithInfestation}</td>
</tr>
<tr>
<td>Leptopirosis</td>
<td>A27</td>
<td>${noOfMalepatientWithLeptopirosis}</td>
<td>${noOfFemalepatientWithLeptopirosis}</td>
</tr>
<tr>
<td>Acute Flacid Paralysis (under 15 years of age)</td>
<td>A80</td>
<td>${noOfMalepatientWithParalysis}</td>
<td>${noOfFemalepatientWithParalysis}</td>
</tr>
<tr>
<td>Meningitis Meningococcal</td>
<td>A39.0</td>
<td>${noOfMalepatientWithMeningococcal}</td>
<td>${noOfFemalepatientWithMeningococcal}</td>
</tr>
<tr>
<td>Meningitis Unspecified</td>
<td>G00-G03, B45</td>
<td>${noOfMalepatientWithUnspecified}</td>
<td>${noOfFemalepatientWithUnspecified}</td>
</tr>
<tr>
<td>Rabies</td>
<td>A82</td>
<td>${noOfMalepatientWithRabies}</td>
<td>${noOfFemalepatientWithRabies}</td>
</tr>
<tr>
<td>Tetanus (Excluding Neonatal Tetanus)</td>
<td>A35</td>
<td>${noOfMalepatientWithTetanus}</td>
<td>${noOfFemalepatientWithTetanus}</td>
</tr>
<tr>
<td>Meningitis Viral</td>
<td>A87</td>
<td>${noOfMalepatientWithMeningitis}</td>
<td>${noOfFemalepatientWithMeningitis}</td>
</tr>
<tr>
<td>Chicken Pox</td>
<td>B01</td>
<td>${noOfMalepatientWithChicken}</td>
<td>${noOfFemalepatientWithChicken}</td>
</tr>
<tr>
<td>Leprosy</td>
<td>A30</td>
<td>${noOfMalepatientWithLeprosy}</td>
<td>${noOfFemalepatientWithLeprosy}</td>
</tr>
<tr>
<td>Herpes Simplex</td>
<td>B00</td>
<td>${noOfMalepatientWithHerpesSimplex}</td>
<td>${noOfFemalepatientWithHerpesSimplex}</td>
</tr>
<tr>
<td>Mumps</td>
<td>B26</td>
<td>${noOfMalepatientWithMumps}</td>
<td>${noOfFemalepatientWithMumps}</td>
</tr>
<tr>
<td>Cholera (laboratory confirmed)</td>
<td>A00</td>
<td>${noOfMalepatientWithCholera}</td>
<td>${noOfFemalepatientWithCholera}</td>
</tr>
<tr>
<td>Enteric Fever (Typhoid)</td>
<td>A01</td>
<td>${noOfMalepatientWithEnteric}</td>
<td>${noOfFemalepatientWithEnteric}</td>
</tr>
<tr>
<td>Viral Hepatitis A</td>
<td>B15</td>
<td>${noOfMalepatientWithHepatitisA}</td>
<td>${noOfFemalepatientWithHepatitisA}</td>
</tr>
<tr>
<td>Hepatitis Unspecified</td>
<td>B15-B19/K70-K77</td>
<td>${noOfMalepatientWithHepatitisUnspecified}</td>
<td>${noOfFemalepatientWithHepatitisUnspecified}</td>
</tr>
<tr>
<td>Viral Hepatitis B</td>
<td>B16, B18</td>
<td>${noOfMalepatientWithHepatitisB}</td>
<td>${noOfFemalepatientWithHepatitisB}</td>
</tr>
<tr>
<td>Viral Hepatitis C</td>
<td>B17.1, B18</td>
<td>${noOfMalepatientWithHepatitisC}</td>
<td>${noOfFemalepatientWithHepatitisC}</td>
</tr>
<tr>
<td>Viral Hepatitis E</td>
<td>B17.2</td>
<td>${noOfMalepatientWithHepatitisE}</td>
<td>${noOfFemalepatientWithHepatitisE}</td>
</tr>
<tr>
<td>Cervical Lymphadenopathy</td>
<td>R59</td>
<td>${noOfMalepatientWithLymphadenopathy}</td>
<td>${noOfFemalepatientWithLymphadenopathy}</td>
</tr>
<tr>
<td>Whooping Cough/Pertusis</td>
<td>A37</td>
<td>${noOfMalepatientWithWhooping}</td>
<td>${noOfFemalepatientWithWhooping}</td>
</tr>
<tr>
<td>Infuenza</td>
<td>J09-J11</td>
<td>${noOfMalepatientWithInfuenza}</td>
<td>${noOfFemalepatientWithInfuenza}</td>
</tr>
<tr>
<td>Swine flu (H1N1)</td>
<td>J09.X2</td>
<td>${noOfMalepatientWithSwine}</td>
<td>${noOfFemalepatientWithSwine}</td>
</tr>
<tr>
<td>Accident Traffic</td>
<td></td>
<td>0</td>
<td>0</td>
</tr>
<tr>
<td>Accidental Drowning</td>
<td>X92.0-X92.9</td>
<td>${noOfMalepatientWithDrowning}</td>
<td>${noOfFemalepatientWithDrowning}</td>
</tr>
<tr>
<td>Assault (no weapon)</td>
<td>X97-Y04</td>
<td>${noOfMalepatientWithAssault}</td>
<td>${noOfFemalepatientWithAssault}</td>
</tr>
<tr>
<td>Burns and Corrosions</td>
<td>T20-T32</td>
<td>${noOfMalepatientWithBurn}</td>
<td>${noOfFemalepatientWithBurn}</td>
</tr>
<tr>
<td>Self Harm</td>
<td>T14.91</td>
<td>${noOfMalepatientWithHarm}</td>
<td>${noOfFemalepatientWithHarm}</td>
</tr>
<tr>
<td>Infected Wound</td>
<td>L08.9, T81.4</td>
<td>${noOfMalepatientWithInfected}</td>
<td>${noOfFemalepatientWithInfected}</td>
</tr>
<tr>
<td>Sexual Assault/Abuse</td>
<td>T74.2 & T76.2</td>
<td>${noOfMalepatientWithAbuse}</td>
<td>${noOfFemalepatientWithAbuse}</td>
</tr>
<tr>
<td>Poisoning</td>
<td>T36-T50</td>
<td>${noOfMalepatientWithPoisoning}</td>
<td>${noOfFemalepatientWithPoisoning}</td>
</tr>
<tr>
<td>Entrance of Foreign body through Natural Orifice</td>
<td>T15-T19</td>
<td>${noOfMalepatientWithForeign}</td>
<td>${noOfFemalepatientWithForeign}</td>
</tr>
<tr>
<td>Other Accidents</td>
<td>V00-X58</td>
<td>${noOfMalepatientWithAccidents}</td>
<td>${noOfFemalepatientWithAccidents}</td>
</tr>
<tr>
<td>Other Injuries</td>
<td>S00-T88</td>
<td>${noOfMalepatientWithOtherInjuries}</td>
<td>${noOfFemalepatientWithOtherInjuries}</td>
</tr>
<tr>
<td>Arthritis Other</td>
<td>M02, M13, M19</td>
<td>${noOfMalepatientWithArthritisOther}</td>
<td>${noOfFemalepatientWithArthritisOther}</td>
</tr>
<tr>
<td>Dislocation and Sprain</td>
<td></td>
<td>0</td>
<td>0</td>
</tr>
<tr>
<td>Gouty Arthritis</td>
<td>M10</td>
<td>${noOfMalepatientWithGouty}</td>
<td>${noOfFemalepatientWithGouty}</td>
</tr>
<tr>
<td>Osteoarthritis</td>
<td>M15-M19</td>
<td>${noOfMalepatientWithOsteoarthritis}</td>
<td>${noOfFemalepatientWithOsteoarthritis}</td>
</tr>
<tr>
<td>Osteoporosis with or without Fracture</td>
<td>M80-M81</td>
<td>${noOfMalepatientWithwithoutFracture}</td>
<td>${noOfFemalepatientWithwithoutFracture}</td>
</tr>
<tr>
<td>Other Musculoskeletal Disorder</td>
<td>M00-M99</td>
<td>${noOfMalepatientWithMusculoskeletal}</td>
<td>${noOfFemalepatientWithMusculoskeletal}</td>
</tr>
<tr>
<td>Pyogenic Arthritis</td>
<td>M00</td>
<td>${noOfMalepatientWithPyogenic}</td>
<td>${noOfFemalepatientWithPyogenic}</td>
</tr>
<tr>
<td>Rheumatoid Arthritis</td>
<td>M05, M06</td>
<td>${noOfMalepatientWithRheumatoid}</td>
<td>${noOfFemalepatientWithRheumatoid}</td>
</tr>
<tr>
<td>Other Endocrine, Metabolic & Nutritional diseasess</td>
<td>E00-E89</td>
<td>${noOfMalepatientWithOtherEndocrine}</td>
<td>${noOfFemalepatientWithOtherEndocrine}</td>
</tr>
<tr>
<td>Pellagra</td>
<td>E52</td>
<td>${noOfMalepatientWithOtherPellagra}</td>
<td>${noOfFemalepatientWithOtherPellagra}</td>
</tr>
<tr>
<td>Vitamin A Deficiency</td>
<td>E50</td>
<td>${noOfMalepatientWithVitaminA}</td>
<td>${noOfFemalepatientWithVitaminA}</td>
</tr>
<tr>
<td>Vitamin B Deficiency</td>
<td>E51-E53</td>
<td>${noOfMalepatientWithVitaminB}</td>
<td>${noOfFemalepatientWithVitaminB}</td>
</tr>
<tr>
<td>Vitamin D Deficiency</td>
<td>E55</td>
<td>${noOfMalepatientWithVitaminD}</td>
<td>${noOfFemalepatientWithVitaminD}</td>
</tr>
<tr>
<td>Overweight/Obesity</td>
<td>E66</td>
<td>${noOfMalepatientWithOverweight}</td>
<td>${noOfFemalepatientWithOverweight}</td>
</tr>
<tr>
<td>Vitamin C Deficiency/Scurvy</td>
<td>E54</td>
<td>${noOfMalepatientWithScurvy}</td>
<td>${noOfFemalepatientWithScurvy}</td>
</tr>
<tr>
<td>Deviated Nasal Septum (DNS)</td>
<td>J34.2</td>
<td>${noOfMalepatientWithDNS}</td>
<td>${noOfFemalepatientWithDNS}</td>
</tr>
<tr>
<td>Hearing Loss (Conductive/Sensorineural)</td>
<td>H90</td>
<td>${noOfMalepatientWithHearingLos}</td>
<td>${noOfFemalepatientWithHearingLos}</td>
</tr>
<tr>
<td>Hoarseness</td>
<td>R49</td>
<td>${noOfMalepatientWithHoarseness}</td>
<td>${noOfFemalepatientWithHoarseness}</td>
</tr>
<tr>
<td>Otitis Externa</td>
<td>H60</td>
<td>${noOfMalepatientWithExterna}</td>
<td>${noOfFemalepatientWithExterna}</td>
</tr>
<tr>
<td>Otitis Media</td>
<td>H65-H67</td>
<td>${noOfMalepatientWithMedia}</td>
<td>${noOfFemalepatientWithMedia}</td>
</tr>
<tr>
<td>Otorrhea/Otalgia</td>
<td>H92</td>
<td>${noOfMalepatientWithOtalgia}</td>
<td>${noOfFemalepatientWithOtalgia}</td>
</tr>
<tr>
<td>Tinnitus</td>
<td>H93.1</td>
<td>${noOfMalepatientWithTinnitus}</td>
<td>${noOfFemalepatientWithTinnitus}</td>
</tr>
<tr>
<td>Wax in Ear</td>
<td>H61.2</td>
<td>${noOfMalepatientWithWax}</td>
<td>${noOfFemalepatientWithWax}</td>
</tr>
<tr>
<td>Acute Laryngitis</td>
<td>J04.0</td>
<td>${noOfMalepatientWithLaryngitis}</td>
<td>${noOfFemalepatientWithLaryngitis}</td>
</tr>
<tr>
<td>Acute Tonsillitis/Abscess Tonsil</td>
<td>J04.0</td>
<td>${noOfMalepatientWithTonsillitis}</td>
<td>${noOfFemalepatientWithTonsillitis}</td>
</tr>
<tr>
<td>Acute Pharangitis</td>
<td>J02</td>
<td>${noOfMalepatientWithPharangitis}</td>
<td>${noOfFemalepatientWithPharangitis}</td>
</tr>
<tr>
<td>Chronic Pharangitis</td>
<td>J31.2</td>
<td>${noOfMalepatientWithChronic}</td>
<td>${noOfFemalepatientWithChronic}</td>
</tr>
<tr>
<td>Other ENT Disorder</td>
<td>H60-H95, J30-J39</td>
<td>${noOfMalepatientWithOtherENT}</td>
<td>${noOfFemalepatientWithOtherENT}</td>
</tr>
<tr>
<td>Sinusitis (Acute/Chronic)</td>
<td>J01, J32</td>
<td>${noOfMalepatientWithSinusitis}</td>
<td>${noOfFemalepatientWithSinusitis}</td>
</tr>
<tr>
<td>Tonsilar Hypertrophy/Chronic Tonsillitis</td>
<td>J35</td>
<td>${noOfMalepatientWithTonsilar}</td>
<td>${noOfFemalepatientWithTonsilar}</td>
</tr>
<tr>
<td>Allergic/Vasomotor Rhinitis</td>
<td>J30</td>
<td>${noOfMalepatientWithRhinitis}</td>
<td>${noOfFemalepatientWithRhinitis}</td>
</tr>
<tr>
<td>Acute Upper Respiratory Tract Infection</td>
<td>J00-J06</td>
<td>${noOfMalepatientWithTractInfection}</td>
<td>${noOfFemalepatientWithTractInfection}</td>
</tr>
<tr>
<td>Blindness & Low Vision</td>
<td>H54</td>
<td>${noOfMalepatientWithBlindness}</td>
<td>${noOfFemalepatientWithBlindness}</td>
</tr>
<tr>
<td>Cataract</td>
<td>H25-H26</td>
<td>${noOfMalepatientWithCataract}</td>
<td>${noOfFemalepatientWithCataract}</td>
</tr>
<tr>
<td>Conjuntivitis</td>
<td>H10</td>
<td>${noOfMalepatientWithConjuntivitis}</td>
<td>${noOfFemalepatientWithConjuntivitis}</td>
</tr>
<tr>
<td>Other Corneal Disorder</td>
<td>H16-H18</td>
<td>${noOfMalepatientWithCorneal}</td>
<td>${noOfFemalepatientWithCorneal}</td>
</tr>
<tr>
<td>Eye Refraction Disorder include Myopia, Presbyopia, Hypermetropia etc.</td>
<td>H52</td>
<td>${noOfMalepatientWithEyeRefraction}</td>
<td>${noOfFemalepatientWithEyeRefraction}</td>
</tr>
<tr>
<td>Glaucoma</td>
<td>H40-H42</td>
<td>${noOfMalepatientWithGlaucoma}</td>
<td>${noOfFemalepatientWithGlaucoma}</td>
</tr>
<tr>
<td>Other Choroid and Retina Disorder</td>
<td>H30-H36</td>
<td>${noOfMalepatientWithChoroid}</td>
<td>${noOfFemalepatientWithChoroid}</td>
</tr>
<tr>
<td>Other Eye Disorder</td>
<td>H00-H59</td>
<td>${noOfMalepatientWithEyeDisorder}</td>
<td>${noOfFemalepatientWithEyeDisorder}</td>
</tr>
<tr>
<td>Pterygium</td>
<td>H11.0</td>
<td>${noOfMalepatientWithPterygium}</td>
<td>${noOfFemalepatientWithPterygium}</td>
</tr>
<tr>
<td>Stye/Chalazion</td>
<td>H00</td>
<td>${noOfMalepatientWithStye}</td>
<td>${noOfFemalepatientWithStye}</td>
</tr>
<tr>
<td>Corneal Ulcer/Opacities</td>
<td>H16.0, H17</td>
<td>${noOfMalepatientWithCornealUlcer}</td>
<td>${noOfFemalepatientWithCornealUlcer}</td>
</tr>
<tr>
<td>Trachoma</td>
<td>A71</td>
<td>${noOfMalepatientWithTrachoma}</td>
<td>${noOfFemalepatientWithTrachoma}</td>
</tr>
<tr>
<td>Localized swelling, mass and lump</td>
<td>R22</td>
<td>${noOfMalepatientWithLocalizedswelling}</td>
<td>${noOfFemalepatientWithLocalizedswelling}</td>
</tr>
<tr>
<td>Abdominal Pain Unspecified</td>
<td>R10.9</td>
<td>${noOfMalepatientWithAbdominalPain}</td>
<td>${noOfFemalepatientWithAbdominalPain}</td>
</tr>
<tr>
<td>Acute Abdomen</td>
<td>R10.0</td>
<td>${noOfMalepatientWithLocalizedAcuteAbdomen}</td>
<td>${noOfFemalepatientWithLocalizedAcuteAbdomen}</td>
</tr>
<tr>
<td>Anorexia</td>
<td>R63.0</td>
<td>${noOfMalepatientWithAnorexia}</td>
<td>${noOfFemalepatientWithAnorexia}</td>
</tr>
<tr>
<td>Ascites</td>
<td>R18, K70.31, K70.11, K71.51</td>
<td>${noOfMalepatientWithAscites}</td>
<td>${noOfFemalepatientWithAscites}</td>
</tr>
<tr>
<td>Ataxia</td>
<td>R26, R27</td>
<td>${noOfMalepatientWithAtaxia}</td>
<td>${noOfFemalepatientWithAtaxia}</td>
</tr>
<tr>
<td>Contact with or suspected exposure to communicable diseases (STI, TB etc)</td>
<td>Z20</td>
<td>${noOfMalepatientWithcommunicablediseases}</td>
<td>${noOfFemalepatientWithcommunicablediseases}</td>
</tr>
<tr>
<td>Cough</td>
<td>R05</td>
<td>${noOfMalepatientWithCough}</td>
<td>${noOfFemalepatientWithCough}</td>
</tr>
<tr>
<td>Drug Toxicity</td>
<td>T50</td>
<td>${noOfMalepatientWithToxicity}</td>
<td>${noOfFemalepatientWithToxicity}</td>
</tr>
<tr>
<td>Dysphagia/Aphagia</td>
<td>R13</td>
<td>${noOfMalepatientWithAphagia}</td>
<td>${noOfFemalepatientWithAphagia}</td>
</tr>
<tr>
<td>Dysuria</td>
<td>R30.0</td>
<td>${noOfMalepatientWithDysuria}</td>
<td>${noOfFemalepatientWithDysuria}</td>
</tr>
<tr>
<td>Edema</td>
<td>R60</td>
<td>${noOfMalepatientWithEdema}</td>
<td>${noOfFemalepatientWithEdema}</td>
</tr>
<tr>
<td>Epistaxis</td>
<td>R04</td>
<td>${noOfMalepatientWithEpistaxis}</td>
<td>${noOfFemalepatientWithEpistaxis}</td>
</tr>
<tr>
<td>Failure to Thrive/Gain Weight</td>
<td>R62.51,R 62.7</td>
<td>${noOfMalepatientWithThrive}</td>
<td>${noOfFemalepatientWithThrive}</td>
</tr>
<tr>
<td>Headache</td>
<td>R51</td>
<td>${noOfMalepatientWithHeadache}</td>
<td>${noOfFemalepatientWithHeadache}</td>
</tr>
<tr>
<td>Hematuria</td>
<td>R31</td>
<td>${noOfMalepatientWithHematuria}</td>
<td>${noOfFemalepatientWithHematuria}</td>
</tr>
<tr>
<td>History of Falling</td>
<td>Z91.81</td>
<td>${noOfMalepatientWithFalling}</td>
<td>${noOfFemalepatientWithFalling}</td>
</tr>
<tr>
<td>Infantile Colic</td>
<td>R10.83</td>
<td>${noOfMalepatientWithInfantile}</td>
<td>${noOfFemalepatientWithInfantile}</td>
</tr>
<tr>
<td>Jaundice NOS</td>
<td>R17</td>
<td>${noOfMalepatientWithJaundice}</td>
<td>${noOfFemalepatientWithJaundice}</td>
</tr>
<tr>
<td>Memory Disorder</td>
<td>R41.3</td>
<td>${noOfMalepatientWithMemoryDisorder}</td>
<td>${noOfFemalepatientWithMemoryDisorder}</td>
</tr>
<tr>
<td>Other General</td>
<td>R00-R99</td>
<td>${noOfMalepatientWithOtherGeneral}</td>
<td>${noOfFemalepatientWithOtherGeneral}</td>
</tr>
<tr>
<td>Syncope/Blackout/Fainting</td>
<td>R55</td>
<td>${noOfMalepatientWithSyncope}</td>
<td>${noOfFemalepatientWithSyncope}</td>
</tr>
<tr>
<td>Urinary Incontinence</td>
<td>R32, R39.81</td>
<td>${noOfMalepatientWithUrinary}</td>
<td>${noOfFemalepatientWithUrinary}</td>
</tr>
<tr>
<td>Weakness/Tiredness</td>
<td>R53.1-R53.8</td>
<td>${noOfMalepatientWithWeakness}</td>
<td>${noOfFemalepatientWithWeakness}</td>
</tr>
<tr>
<td>Fever of Unknown Origin/PUO</td>
<td>R50</td>
<td>${noOfMalepatientWithUnknownOrigin}</td>
<td>${noOfFemalepatientWithUnknownOrigin}</td>
</tr>
<tr>
<td>Other Allergies</td>
<td>T78</td>
<td>${noOfMalepatientWithAllergies}</td>
<td>${noOfFemalepatientWithAllergies}</td>
</tr>
<tr>
<td>Febrile Convulsion</td>
<td>R56.0</td>
<td>${noOfMalepatientWithFebrile}</td>
<td>${noOfFemalepatientWithFebrile}</td>
</tr>
<tr>
<td>Acute Renal Failure</td>
<td>N17</td>
<td>${noOfMalepatientWithRenal}</td>
<td>${noOfFemalepatientWithRenal}</td>
</tr>
<tr>
<td>Benign Prostatic Hypertropy</td>
<td>N40</td>
<td>${noOfMalepatientWithProstatic}</td>
<td>${noOfFemalepatientWithProstatic}</td>
</tr>
<tr>
<td>Breast Nodule/Lump NOS</td>
<td>N63</td>
<td>${noOfMalepatientWithBreast}</td>
<td>${noOfFemalepatientWithBreast}</td>
</tr>
<tr>
<td>Chronic Renal Failure</td>
<td>N18</td>
<td>${noOfMalepatientWithChronicRenal}</td>
<td>${noOfFemalepatientWithChronicRenal}</td>
</tr>
<tr>
<td>Glomerular Diseases</td>
<td>N00-N08</td>
<td>${noOfMalepatientWithGlomerular}</td>
<td>${noOfFemalepatientWithGlomerular}</td>
</tr>
<tr>
<td>Hydrocele</td>
<td>N43</td>
<td>${noOfMalepatientWithHydrocele}</td>
<td>${noOfFemalepatientWithHydrocele}</td>
</tr>
<tr>
<td>Inflammatory Disorder of Breast</td>
<td>N61</td>
<td>${noOfMalepatientWithDisorderBreast}</td>
<td>${noOfFemalepatientWithDisorderBreast}</td>
</tr>
<tr>
<td>Disease of Gallbladder, Biliary tract and Pancreas</td>
<td>K80-K87</td>
<td>${noOfMalepatientWithGallbladder}</td>
<td>${noOfFemalepatientWithGallbladder}</td>
</tr>
<tr>
<td>Disease of Liver</td>
<td></td>
<td>0</td>
<td>0</td>
</tr>
<tr>
<td>Vitamin Deficiency Unspecified</td>
<td>E56.9</td>
<td>${noOfMalepatientWithVitamin}</td>
<td>${noOfFemalepatientWithVitamin}</td>
</tr>
<tr>
<td>Other Nutritional Deficiency</td>
<td></td>
<td>${noOfMalepatientWithNutritional}</td>
<td>${noOfFemalepatientWithNutritional}</td>
</tr>
<tr>
<td>Abnormal Weight Gain</td>
<td>R63.5</td>
<td>${noOfMalepatientWithGain}</td>
<td>${noOfFemalepatientWithGain}</td>
</tr>
<tr>
<td>Abnormal Weight Loss</td>
<td>R63.4</td>
<td>${noOfMalepatientWithLoss}</td>
<td>${noOfFemalepatientWithLoss}</td>
</tr>
<tr>
<td>Anaphylactic Reaction</td>
<td></td>
<td>0</td>
<td>0</td>
</tr>
<tr>
<td>Drowsiness & Coma</td>
<td>R40</td>
<td>${noOfMalepatientWithDrowsiness}</td>
<td>${noOfFemalepatientWithDrowsiness}</td>
</tr>
<tr>
<td>Pain (Unspecified)</td>
<td>R52</td>
<td>${noOfMalepatientWithPain}</td>
<td>${noOfFemalepatientWithPain}</td>
</tr>
<tr>
<td>Renal Colic</td>
<td>N23</td>
<td>${noOfMalepatientWithRenalColic}</td>
<td>${noOfFemalepatientWithRenalColic}</td>
</tr>
<tr>
<td>Assault (with weapon)</td>
<td>X93-X96</td>
<td>${noOfMalepatientWithAssaultwithweapon}</td>
<td>${noOfFemalepatientWithAssaultwithweapon}</td>
</tr>
<tr>
<td>Person Encountering Health Services for Examination</td>
<td>Z00-Z13</td>
<td>${noOfMalepatientWithExamination}</td>
<td>${noOfFemalepatientWithExamination}</td>
</tr>
<tr>
<td>Person Encountering Health Services in Circumstances Related to Reproduction</td>
<td>Z30-Z39</td>
<td>${noOfMalepatientWithReproduction}</td>
<td>${noOfFemalepatientWithReproduction}</td>
</tr>
<tr>
<td>Placenta Previan</td>
<td>O44</td>
<td>${noOfMalepatientWithPlacentaPrevian}</td>
<td>${noOfFemalepatientWithPlacentaPrevian}</td>
</tr>
<tr>
<td>Pre-existing Essential Hypertension Complicating Pregnancy</td>
<td>O10.0</td>
<td>${noOfMalepatientWithPregnancy}</td>
<td>${noOfFemalepatientWithPregnancy}</td>
</tr>
<tr>
<td>Complication Following (Induced) Termination of Pregnancy</td>
<td>O04</td>
<td>${noOfMalepatientWithComplication}</td>
<td>${noOfFemalepatientWithComplication}</td>
</tr>
<tr>
<td>Prolong Labour</td>
<td>O63</td>
<td>${noOfMalepatientWithProlongLabour}</td>
<td>${noOfFemalepatientWithProlongLabour}</td>
</tr>
<tr>
<td>Obstructed Labour</td>
<td>O64-O65</td>
<td>${noOfMalepatientWithObstructedLabour}</td>
<td>${noOfFemalepatientWithObstructedLabour}</td>
</tr>
<tr>
<td>Diabetes Mellitus in Pregnancy, Childbirth and Puerperium</td>
<td>O24</td>
<td>${noOfMalepatientWithPuerperium}</td>
<td>${noOfFemalepatientWithPuerperium}</td>
</tr>
<tr>
<td>Malnutrition in Pregnancy, Childbirth and Puerperium</td>
<td>O25</td>
<td>${noOfMalepatientWithChildbirth}</td>
<td>${noOfFemalepatientWithChildbirth}</td>
</tr>
<tr>
<td>Anemia Complicating Pregnancy, Childbirth and Puerperium</td>
<td>O99.0</td>
<td>${noOfMalepatientWithAnemiaComplicating}</td>
<td>${noOfFemalepatientWithAnemiaComplicating}</td>
</tr>
<tr>
<td>Grand Multipara</td>
<td></td>
<td>0</td>
<td>0</td>
</tr>
</tbody>

</table>

</table>

