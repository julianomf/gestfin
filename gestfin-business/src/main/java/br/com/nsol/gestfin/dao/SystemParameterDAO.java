package br.com.nsol.gestfin.dao;

import java.util.HashMap;
import java.util.Map;

import javax.ejb.Stateless;

import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.log4j.Logger;

import br.com.nsol.gestfin.factory.GenericDAO;
import br.com.nsol.gestfin.factory.MyBatisSessionManager;
import br.com.nsol.gestfin.types.DaoParameterEnum;
import br.com.nsol.gestfin.cache.CacheType;
import br.com.nsol.gestfin.exceptions.TechnicalException;

/**
 * Permite acesso aos parametros do sistema
 * @author 
 *
 */
@Stateless
public class SystemParameterDAO extends GenericDAO<SystemParameterDAO>{
	private static final long serialVersionUID = 1L;
	private static final Logger LOG = Logger.getLogger(SystemParameterDAO.class);

	public SystemParameterDAO() {
		this(MyBatisSessionManager.getInstance());
	}
	
	/**
	 * Construtor
	 * @param sf Sql Session Factory
	 */
	public SystemParameterDAO(SqlSessionFactory sf) {
		super(SystemParameterDAO.class, sf);
	}

	/**
	 * Busca valor a partir do parametro passado
	 * @param parameterName Nome do parametro
	 * @return
	 */
	public String getParameterValue(String parameterName) throws TechnicalException {
		String cacheKey = parameterName;
		String methodName = "findParameterStringValue";
		String obj = (String) super.getCachedObject(methodName, cacheKey, CacheType.FIND_PARAMETER_STRING_VALUE);
		if (obj == null) {
			Map<String, Object> keys = new HashMap<String, Object>();
			keys.put(DaoParameterEnum.PARAM_NAME.getValue(), parameterName);
			obj = (String) this.find(methodName, keys);
			super.putCacheObject(methodName, cacheKey, CacheType.FIND_PARAMETER_STRING_VALUE, obj);
		}
		return obj;		
	}
	
	/**
	 * Busca valor a partir do parametro passado
	 * @param parameterName Nome do parametro
	 * @return
	 */
	public Integer getParameterIntValue(String parameterName) throws TechnicalException {
		String cacheKey = parameterName;
		String methodName = "findParameterIntValue";
		Integer obj = (Integer) super.getCachedObject(methodName, cacheKey, CacheType.FIND_PARAMETER_INT_VALUE);
		if (obj == null) {
			Map<String, Object> keys = new HashMap<String, Object>();
			keys.put(DaoParameterEnum.PARAM_NAME.getValue(), parameterName);
			obj = (Integer) this.find(methodName, keys);
			super.putCacheObject(methodName, cacheKey, CacheType.FIND_PARAMETER_INT_VALUE, obj);
		}
		return obj;		
	}
	
	/**
	 * Busca valor a partir do parametro passado
	 * @param parameterName Nome do parametro
	 * @return
	 */
	public Float getParameterFloatValue(String parameterName) throws TechnicalException {
		String cacheKey = parameterName;
		String methodName = "findParameterFloatValue";
		Float obj = (Float) super.getCachedObject(methodName, cacheKey, CacheType.FIND_PARAMETER_FLOAT_VALUE);
		if (obj == null) {
			Map<String, Object> keys = new HashMap<String, Object>();
			keys.put(DaoParameterEnum.PARAM_NAME.getValue(), parameterName);
			obj = (Float) this.find(methodName, keys);
			super.putCacheObject(methodName, cacheKey, CacheType.FIND_PARAMETER_FLOAT_VALUE, obj);
		}
		return obj;		
	}
	
	/**
	 * Atualiza valor a partir do parametro passado
	 * @param parameterName Nome do parametro
	 * @param parameterValue valor do parâmetro
	 * @return
	 */
	public void updateParameterValue(String parameterName, String parameterValue) throws TechnicalException {
		LOG.debug("updateParameterValue: " + parameterName + "," + parameterValue);

		Map<String, Object> keys = new HashMap<String, Object>();
		keys.put(DaoParameterEnum.PARAM_NAME.getValue(), parameterName);
		keys.put(DaoParameterEnum.PARAM_VALUE.getValue(), parameterValue);
		this.update("updateParameterValue", keys);
	}

	/**
	 * Atualiza valor a partir do parametro passado
	 * @param parameterName Nome do parametro
	 * @param parameterValue valor do parâmetro
	 * @return
	 */
	public void updateParameterIntValue(String parameterName, Integer parameterValue) throws TechnicalException {
		LOG.debug("updateParameterValue: " + parameterName + "," + parameterValue);

		Map<String, Object> keys = new HashMap<String, Object>();
		keys.put(DaoParameterEnum.PARAM_NAME.getValue(), parameterName);
		keys.put(DaoParameterEnum.PARAM_VALUE.getValue(), parameterValue);
		this.update("updateParameterIntValue", keys);
	}
}
