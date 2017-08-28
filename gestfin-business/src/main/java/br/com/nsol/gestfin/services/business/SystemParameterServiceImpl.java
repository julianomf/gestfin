/**
 * 
 */
package br.com.nsol.gestfin.services.business;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.com.nsol.gestfin.dao.SystemParameterDAO;
import br.com.nsol.gestfin.exceptions.TechnicalException;

/**
 * @author 
 *
 */
@Stateless
public class SystemParameterServiceImpl implements SystemParameterService {

	@EJB
	private SystemParameterDAO systemParameterDAO;
	
	/* (non-Javadoc)
	 * @see br.com.nsol.gestfin.business.services.SystemParameterService#fineParameterValue(java.lang.String)
	 */
	@Override
	public String findParameterValue(String parameterName) throws TechnicalException {
		return systemParameterDAO.getParameterValue(parameterName);
	}
	
	/* (non-Javadoc)
	 * @see br.com.nsol.gestfin.business.services.SystemParameterService#findParameterIntValue(java.lang.String)
	 */
	@Override
	public Integer findParameterIntValue(String parameterName) throws TechnicalException {
		return systemParameterDAO.getParameterIntValue(parameterName);
	}

	/* (non-Javadoc)
	 * @see br.com.nsol.gestfin.services.business.SystemParameterService#findParameterFloatValue(java.lang.String)
	 */
	@Override
	public Float findParameterFloatValue(String parameterName) throws TechnicalException {
		return systemParameterDAO.getParameterFloatValue(parameterName);
	}

	/*
	 * (non-Javadoc)
	 * @see br.com.nsol.gestfin.services.business.SystemParameterService#updateParameterValue(java.lang.String, java.lang.String)
	 */
	@Override
	public void updateParameterValue(String parameterName, String parameterValue) throws TechnicalException {
		systemParameterDAO.updateParameterValue(parameterName, parameterValue);
	}

	/* (non-Javadoc)
	 * @see br.com.nsol.gestfin.services.business.SystemParameterService#updateParameterIntValue(java.lang.String, java.lang.Integer)
	 */
	@Override
	public void updateParameterIntValue(String parameterName, Integer parameterValue) throws TechnicalException {
		systemParameterDAO.updateParameterIntValue(parameterName, parameterValue);
	}
}
