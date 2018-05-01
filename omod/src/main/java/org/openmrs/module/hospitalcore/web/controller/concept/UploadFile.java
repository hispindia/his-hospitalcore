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


package org.openmrs.module.hospitalcore.web.controller.concept;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

public class UploadFile {
	private CommonsMultipartFile diagnosisFile;
	private CommonsMultipartFile synonymFile;
	private CommonsMultipartFile mappingFile;

	public CommonsMultipartFile getDiagnosisFile() {
		return diagnosisFile;
	}

	public void setDiagnosisFile(CommonsMultipartFile diagnosisFile) {
		this.diagnosisFile = diagnosisFile;
	}

	public CommonsMultipartFile getSynonymFile() {
		return synonymFile;
	}

	public void setSynonymFile(CommonsMultipartFile synonymFile) {
		this.synonymFile = synonymFile;
	}

	public CommonsMultipartFile getMappingFile() {
		return mappingFile;
	}

	public void setMappingFile(CommonsMultipartFile mappingFile) {
		this.mappingFile = mappingFile;
	}

}
