/**
 *  Copyright 2013 Society for Health Information Systems Programmes, India (HISP India)
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
 *  author:ghanshyam
 *  date:10-april-2013
 *  issue:#1302 Radiology record
 **/

package org.openmrs.module.hospitalcore.db.hibernate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.openmrs.Concept;
import org.openmrs.ConceptAnswer;
import org.openmrs.Patient;
import org.openmrs.api.db.DAOException;
import org.openmrs.module.hospitalcore.db.RadiologyCommonDAO;
import org.openmrs.module.hospitalcore.model.RadiologyTest;

public class HibernateRadiologyCommonDAO implements RadiologyCommonDAO {

	SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

	SimpleDateFormat formatterExt = new SimpleDateFormat("dd/MM/yyyy");

	protected final Log log = LogFactory.getLog(getClass());

	/**
	 * Hibernate session factory
	 */
	private SessionFactory sessionFactory;

	/**
	 * Set session factory
	 * 
	 * @param sessionFactory
	 */
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@SuppressWarnings("unchecked")
	public List<RadiologyTest> getAllTest(Patient patient)
			throws DAOException {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				RadiologyTest.class);
		criteria.add(Restrictions.eq("patient", patient));
		criteria.add(Restrictions.eq("status", "completed"));
		//ghanshyam,date:25-april-2013 Feedback #1302 Add Radiology record of patient in patientdashboard(note:added below sorting criteria)
		//criteria.addOrder(Order.asc("date"));
		criteria.addOrder(Order.desc("date"));

		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	public ConceptAnswer getConceptAnswer(Concept concept) throws DAOException {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				ConceptAnswer.class);
		criteria.add(Restrictions.eq("answerConcept", concept));
		return (ConceptAnswer) criteria.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	public List<RadiologyTest> getAllTest(Patient patient, String date)
			throws DAOException {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				RadiologyTest.class, "radiologytest");
		criteria.add(Restrictions.eq("patient", patient));
		if(!date.equals("all")){
		String dat = date;
		String startFromDate = dat + " 00:00:00";
		String endFromDate = dat + " 23:59:59";
		try {
			criteria.add(Restrictions.and(
					Restrictions.ge("radiologytest.date",
							formatter.parse(startFromDate)),
					Restrictions.le("radiologytest.date",
							formatter.parse(endFromDate))));
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error convert date: " + e.toString());
			e.printStackTrace();
		}
		}
		criteria.add(Restrictions.eq("status", "completed"));
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	public List<RadiologyTest> getAllTest(Patient patient, String date,
			Concept concept) throws DAOException {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				RadiologyTest.class, "radiologytest");
		criteria.add(Restrictions.eq("patient", patient));
		String dat = date;
		String startFromDate = dat + " 00:00:00";
		String endFromDate = dat + " 23:59:59";
		try {
			criteria.add(Restrictions.and(
					Restrictions.ge("radiologytest.date",
							formatter.parse(startFromDate)),
					Restrictions.le("radiologytest.date",
							formatter.parse(endFromDate))));
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error convert date: " + e.toString());
			e.printStackTrace();
		}
		criteria.add(Restrictions.eq("status", "completed"));
		Collection<ConceptAnswer> conanss = concept.getAnswers();
		ArrayList al = new ArrayList();
		for (ConceptAnswer conans : conanss) {
			al.add(conans.getAnswerConcept());
		}
		criteria.add(Restrictions.in("concept", al));
		return criteria.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<RadiologyTest> getAllSubTest(Patient patient, String date,
			Concept concept) throws DAOException {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				RadiologyTest.class, "radiologytest");
		criteria.add(Restrictions.eq("patient", patient));
		criteria.add(Restrictions.eq("concept", concept));
		String dat = date;
		String startFromDate = dat + " 00:00:00";
		String endFromDate = dat + " 23:59:59";
		try {
			criteria.add(Restrictions.and(
					Restrictions.ge("radiologytest.date",
							formatter.parse(startFromDate)),
					Restrictions.le("radiologytest.date",
							formatter.parse(endFromDate))));
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error convert date: " + e.toString());
			e.printStackTrace();
		}
		criteria.add(Restrictions.eq("status", "completed"));
		//ghanshyam,date:25-april-2013 Feedback #1302 Add Radiology record of patient in patientdashboard(note:added below sorting criteria)
		//criteria.addOrder(Order.asc("date"));
		criteria.addOrder(Order.desc("date"));
		return criteria.list();
	}

}
