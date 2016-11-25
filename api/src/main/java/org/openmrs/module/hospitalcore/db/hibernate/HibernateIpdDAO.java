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

package org.openmrs.module.hospitalcore.db.hibernate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.openmrs.Concept;
import org.openmrs.ConceptClass;
import org.openmrs.api.APIException;
import org.openmrs.api.context.Context;
import org.openmrs.api.db.DAOException;
import org.openmrs.module.hospitalcore.db.IpdDAO;
import org.openmrs.module.hospitalcore.model.IpdPatientAdmission;
import org.openmrs.module.hospitalcore.model.IpdPatientAdmissionLog;
import org.openmrs.module.hospitalcore.model.IpdPatientAdmitted;
import org.openmrs.module.hospitalcore.model.IpdPatientAdmittedLog;
import org.openmrs.module.hospitalcore.model.IpdPatientVitalStatistics;
import org.openmrs.module.hospitalcore.model.WardBedStrength;

public class HibernateIpdDAO implements IpdDAO {
	SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
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
	public List<IpdPatientAdmission> getAllIpdPatientAdmission()
			throws DAOException {
		return sessionFactory.getCurrentSession()
				.createCriteria(IpdPatientAdmission.class).list();
	}

	@SuppressWarnings("unchecked")
	public List<IpdPatientAdmissionLog> listIpdPatientAdmissionLog(
			Integer patientId, Integer admissionWardId, String status,
			Integer min, Integer max) throws DAOException {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				IpdPatientAdmissionLog.class, "ipdPatientAdmissionLog");
		if (patientId != null && patientId > 0) {
			criteria.createAlias("ipdPatientAdmissionLog.patient", "patient");
			criteria.add(Restrictions.eq("patient.patientId", patientId));
		}
		if (admissionWardId != null && admissionWardId > 0) {
			criteria.add(Restrictions.eq(
					"ipdPatientAdmissionLog.admissionWard.conceptId",
					admissionWardId));
		}
		if (StringUtils.isNotBlank(status)) {
			criteria.add(Restrictions.eq("ipdPatientAdmissionLog.status",
					status));
		}
		if (max > 0) {
			criteria.setFirstResult(min).setMaxResults(max);
		}
		criteria.addOrder(Order.desc("ipdPatientAdmissionLog.admissionDate"));
		List<IpdPatientAdmissionLog> list = criteria.list();
		return list;
	}

	@SuppressWarnings("unchecked")
	public List<IpdPatientAdmitted> getAllIpdPatientAdmitted()
			throws DAOException {
		return sessionFactory.getCurrentSession()
				.createCriteria(IpdPatientAdmitted.class).list();
	}

	@SuppressWarnings("unchecked")
	public List<IpdPatientAdmittedLog> getAllIpdPatientAdmittedLog()
			throws DAOException {
		return sessionFactory.getCurrentSession()
				.createCriteria(IpdPatientAdmittedLog.class).list();
	}

	public IpdPatientAdmission getIpdPatientAdmission(Integer id)
			throws DAOException {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				IpdPatientAdmission.class);
		criteria.add(Restrictions.eq("id", id));
		return (IpdPatientAdmission) criteria.uniqueResult();
	}

	public IpdPatientAdmissionLog getIpdPatientAdmissionLog(Integer id)
			throws DAOException {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				IpdPatientAdmissionLog.class);
		criteria.add(Restrictions.eq("id", id));
		return (IpdPatientAdmissionLog) criteria.uniqueResult();
	}

	public IpdPatientAdmitted getIpdPatientAdmitted(Integer id)
			throws DAOException {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				IpdPatientAdmitted.class);
		criteria.add(Restrictions.eq("id", id));
		return (IpdPatientAdmitted) criteria.uniqueResult();
	}

	public IpdPatientAdmittedLog getIpdPatientAdmittedLog(Integer id)
			throws DAOException {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				IpdPatientAdmittedLog.class);
		criteria.add(Restrictions.eq("id", id));
		return (IpdPatientAdmittedLog) criteria.uniqueResult();
	}

	public IpdPatientAdmission saveIpdPatientAdmission(
			IpdPatientAdmission admission) throws DAOException {
		return (IpdPatientAdmission) sessionFactory.getCurrentSession().merge(
				admission);
	}

	public IpdPatientAdmissionLog saveIpdPatientAdmissionLog(
			IpdPatientAdmissionLog admissionLog) throws DAOException {
		return (IpdPatientAdmissionLog) sessionFactory.getCurrentSession()
				.merge(admissionLog);
	}

	public IpdPatientAdmitted saveIpdPatientAdmitted(IpdPatientAdmitted admitted)
			throws DAOException {
		return (IpdPatientAdmitted) sessionFactory.getCurrentSession().merge(
				admitted);
	}

	public IpdPatientAdmittedLog saveIpdPatientAdmittedLog(
			IpdPatientAdmittedLog admittedLog) throws DAOException {
		return (IpdPatientAdmittedLog) sessionFactory.getCurrentSession()
				.merge(admittedLog);
	}

	// New Requirement load choose ipd page
	public List<IpdPatientAdmission> searchIpdPatientAdmission(
			String patientSearch, ArrayList<Integer> userIds, String fromDate,
			String toDate, String wardId, String status) throws APIException {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				IpdPatientAdmission.class, "patientAdmission");
		if (StringUtils.isNotBlank(fromDate) && StringUtils.isBlank(toDate)) {
			String startFromDate = fromDate + " 00:00:00";
			String endFromDate = fromDate + " 23:59:59";
			try {
				criteria.add(Restrictions.and(Restrictions.ge(
						"patientAdmission.admissionDate",
						formatter.parse(startFromDate)), Restrictions.le(
						"patientAdmission.admissionDate",
						formatter.parse(endFromDate))));
			} catch (Exception e) {
				// TODO: handle exception
				log.error("Error convert date: " + e.toString());
				e.printStackTrace();
			}
		} else if (StringUtils.isBlank(fromDate)
				&& StringUtils.isNotBlank(toDate)) {
			String startToDate = toDate + " 00:00:00";
			String endToDate = toDate + " 23:59:59";
			try {
				criteria.add(Restrictions.and(Restrictions.ge(
						"patientAdmission.admissionDate",
						formatter.parse(startToDate)), Restrictions.le(
						"patientAdmission.admissionDate",
						formatter.parse(endToDate))));
			} catch (Exception e) {
				// TODO: handle exception
				log.error("Error convert date: " + e.toString());
				e.printStackTrace();
			}
		} else if (StringUtils.isNotBlank(fromDate)
				&& StringUtils.isNotBlank(toDate)) {
			String startToDate = fromDate + " 00:00:00";
			String endToDate = toDate + " 23:59:59";
			try {
				criteria.add(Restrictions.and(Restrictions.ge(
						"patientAdmission.admissionDate",
						formatter.parse(startToDate)), Restrictions.le(
						"patientAdmission.admissionDate",
						formatter.parse(endToDate))));
			} catch (Exception e) {
				// TODO: handle exception
				log.error("Error convert date: " + e.toString());
				e.printStackTrace();
			}
		}

		String prefix = Context.getAdministrationService().getGlobalProperty(
				"registration.identifier_prefix");
		if (StringUtils.isNotBlank(patientSearch)) {
			if (patientSearch.contains("-") && !patientSearch.contains(prefix)) {
				patientSearch = prefix + patientSearch;
			}
			if (patientSearch.contains(prefix)) {
				criteria.add(Restrictions.eq(
						"patientAdmission.patientIdentifier", patientSearch));
			} else {
				criteria.add(Restrictions.like("patientAdmission.patientName",
						"%" + patientSearch + "%"));
			}
		}

		if (CollectionUtils.isNotEmpty(userIds)) {
			criteria.createAlias("patientAdmission.opdAmittedUser", "user");
			criteria.add(Restrictions.in("user.id", userIds));
		}
		// New Requirement load choose ipd page
		criteria.createAlias("patientAdmission.admissionWard", "ward");
		criteria.add(Restrictions.eq("ward.conceptId", Integer.parseInt(wardId)));

		if (StringUtils.isNotBlank(status)) {
			criteria.add(Restrictions.eq("patientAdmission.status", status));
		}
		criteria.addOrder(Order.asc("patientAdmission.admissionDate"));
		List<IpdPatientAdmission> list = criteria.list();
		return list;
	}

	public List<IpdPatientAdmitted> searchIpdPatientAdmitted(
			String patientSearch, ArrayList<Integer> userIds, String fromDate,
			String toDate, String wardId, String status) throws APIException {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				IpdPatientAdmitted.class, "patientAdmitted");

		if (!StringUtils.isBlank(fromDate) && StringUtils.isBlank(toDate)) {
			String startFromDate = fromDate + " 00:00:00";
			String endFromDate = fromDate + " 23:59:59";
			try {
				criteria.add(Restrictions.and(Restrictions.ge(
						"patientAdmitted.admissionDate",
						formatter.parse(startFromDate)), Restrictions.le(
						"patientAdmitted.admissionDate",
						formatter.parse(endFromDate))));
			} catch (Exception e) {
				// TODO: handle exception
				log.error("Error convert date: " + e.toString());
				e.printStackTrace();
			}
		} else if (StringUtils.isBlank(fromDate)
				&& !StringUtils.isBlank(toDate)) {
			String startToDate = toDate + " 00:00:00";
			String endToDate = toDate + " 23:59:59";
			try {
				criteria.add(Restrictions.and(Restrictions.ge(
						"patientAdmitted.admissionDate",
						formatter.parse(startToDate)), Restrictions.le(
						"patientAdmitted.admissionDate",
						formatter.parse(endToDate))));
			} catch (Exception e) {
				// TODO: handle exception
				log.error("Error convert date: " + e.toString());
				e.printStackTrace();
			}
		} else if (!StringUtils.isBlank(fromDate)
				&& !StringUtils.isBlank(toDate)) {
			String startToDate = fromDate + " 00:00:00";
			String endToDate = toDate + " 23:59:59";
			try {
				criteria.add(Restrictions.and(Restrictions.ge(
						"patientAdmitted.admissionDate",
						formatter.parse(startToDate)), Restrictions.le(
						"patientAdmitted.admissionDate",
						formatter.parse(endToDate))));
			} catch (Exception e) {
				// TODO: handle exception
				log.error("Error convert date: " + e.toString());
				e.printStackTrace();
			}
		}
		// New Requirement load choose ipd page
		String prefix = Context.getAdministrationService().getGlobalProperty(
				"registration.identifier_prefix");
		if (StringUtils.isNotBlank(patientSearch)) {
			if (patientSearch.contains("-") && !patientSearch.contains(prefix)) {
				patientSearch = prefix + patientSearch;
			}
			if (patientSearch.contains(prefix)) {
				criteria.add(Restrictions.eq(
						"patientAdmitted.patientIdentifier", patientSearch));
			} else {
				criteria.add(Restrictions.like("patientAdmitted.patientName",
						"%" + patientSearch + "%"));
			}
		}

		if (CollectionUtils.isNotEmpty(userIds)) {
			criteria.createAlias("patientAdmitted.ipdAdmittedUser", "user");
			criteria.add(Restrictions.in("user.id", userIds));
		}
		// New Requirement load choose ipd page
		criteria.createAlias("patientAdmitted.admittedWard", "ward");
		criteria.add(Restrictions.eq("ward.conceptId", Integer.parseInt(wardId)));

		if (StringUtils.isNotBlank(status)) {
			criteria.add(Restrictions.eq("patientAdmitted.status", status));
		}
		return criteria.list();
	}

	public void removeIpdPatientAdmission(IpdPatientAdmission admission)
			throws DAOException {
		sessionFactory.getCurrentSession().delete(admission);
	}

	public void removeIpdPatientAdmitted(IpdPatientAdmitted admitted)
			throws DAOException {
		sessionFactory.getCurrentSession().delete(admitted);
	}

	@SuppressWarnings("unchecked")
	public List<IpdPatientAdmittedLog> listAdmittedLogByPatientId(
			Integer patientId) throws DAOException {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				IpdPatientAdmittedLog.class);
		criteria.add(Restrictions.eq("patient.id", patientId));
		criteria.addOrder(Order.desc("admissionDate"));

		return criteria.list();
	}

	public IpdPatientAdmitted getAdmittedByPatientId(Integer patientId)
			throws DAOException {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				IpdPatientAdmitted.class);
		criteria.add(Restrictions.eq("patient.id", patientId));
		List<IpdPatientAdmitted> list = criteria.list();
		return CollectionUtils.isEmpty(list) ? null : list.get(0);
	}

	public void saveWardBedStrength(WardBedStrength wardBedStrength)
			throws DAOException {
		sessionFactory.getCurrentSession().saveOrUpdate(wardBedStrength);

	}

	public WardBedStrength getWardBedStrengthByWardId(Integer wardId)
			throws DAOException {

		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				WardBedStrength.class);
		criteria.add(Restrictions.eq("ward.id", wardId));
		List<WardBedStrength> list = criteria.list();

		return CollectionUtils.isEmpty(list) ? null : list.get(0);
	}
// vital statstics for ipd [atient
	public IpdPatientVitalStatistics saveIpdPatientVitalStatistics(
			IpdPatientVitalStatistics vitalStatistics) throws DAOException {
		return (IpdPatientVitalStatistics) sessionFactory.getCurrentSession()
				.merge(vitalStatistics);
	}
	
	public List<IpdPatientVitalStatistics> getIpdPatientVitalStatistics(
			Integer patientId, Integer patientAdmissionLogId)
			throws DAOException {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				IpdPatientVitalStatistics.class);
		criteria.add(Restrictions.eq("patient.personId", patientId));
		criteria.add(Restrictions.eq("ipdPatientAdmissionLog.id",
				patientAdmissionLogId));
		return criteria.list();
	}
	public List<Concept> getDiet() throws DAOException {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				Concept.class, "con");
		ConceptClass conClass = Context.getConceptService()
				.getConceptClassByName("Diet");
		criteria.add(Restrictions.eq("con.conceptClass", conClass));
		return criteria.list();
	}
}
