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

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.openmrs.Order;
import org.openmrs.Role;
import org.openmrs.api.db.DAOException;
import org.openmrs.module.hospitalcore.db.LabDAO;
import org.openmrs.module.hospitalcore.model.Lab;
import org.openmrs.module.hospitalcore.model.LabTest;

public class HibernateLabDAO implements LabDAO {

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
	public List<Lab> getAllActivelab() throws DAOException {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Lab.class);
		criteria.add(Restrictions.eq("retired", false));
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	public List<Lab> getAllLab() throws DAOException {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Lab.class);
		return criteria.list();
	}

	public Lab getLabById(Integer labId) throws DAOException {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Lab.class);
		criteria.add(Restrictions.eq("labId", labId));
		return (Lab) criteria.uniqueResult();
	}

	public Lab getLabByName(String name) throws DAOException {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Lab.class);
		criteria.add(Restrictions.eq("name", name));
		return (Lab) criteria.uniqueResult();
	}

	public LabTest getLabTestById(Integer labTestId) throws DAOException {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(LabTest.class);
		criteria.add(Restrictions.eq("labTestId", labTestId));
		return (LabTest) criteria.uniqueResult();
	}

	public LabTest getLabTestByOrder(Order order) throws DAOException {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(LabTest.class);
		criteria.add(Restrictions.eq("order", order));
		return (LabTest) criteria.uniqueResult();
	}

	public LabTest getLabTestBySampleNumber(String sampleNumber)
			throws DAOException {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(LabTest.class);
		criteria.add(Restrictions.eq("sampleNumber", sampleNumber));
		return (LabTest) criteria.uniqueResult();
	}

	public Lab saveLab(Lab lab) throws DAOException {
		return (Lab) sessionFactory.getCurrentSession().merge(lab);
	}

	public LabTest saveLabTest(LabTest labTest) throws DAOException {
		return (LabTest) sessionFactory.getCurrentSession().merge(labTest);
	}

	public void deleteLab(Lab lab) throws DAOException {
		sessionFactory.getCurrentSession().delete(lab);
	}

	public Lab getLabByRole(Role role) throws DAOException {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Lab.class);
		criteria.add(Restrictions.eq("role", role));
		return (Lab) criteria.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	public List<Lab> getLabByRoles(List<Role> roles) throws DAOException {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Lab.class);
		criteria.add(Restrictions.in("role", roles));
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	public List<LabTest> getLatestLabTestByDate(Date today,Date nextDay, Lab lab)
			throws DAOException {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(LabTest.class);
		criteria.add(Restrictions.eq("lab", lab));
		criteria.add(Restrictions.ge("acceptDate", new java.sql.Date( today.getTime())));
		criteria.add(Restrictions.lt("acceptDate", new java.sql.Date( nextDay.getTime())));
		return criteria.list();
	}

	public void deleteLabTest(LabTest labtest) throws DAOException {
		sessionFactory.getCurrentSession().delete(labtest);
	}

	
}
