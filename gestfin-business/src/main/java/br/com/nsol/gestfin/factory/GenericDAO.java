package br.com.nsol.gestfin.factory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.log4j.Logger;

import br.com.nsol.gestfin.cache.CacheType;
import br.com.nsol.gestfin.cache.CacheWrapper;
import br.com.nsol.gestfin.exceptions.TechnicalException;

/**
 * DAO Genérico que deve ser extendido por todos os DAO's da aplicação 
 * que queiram utilizar o MyBatis
 * 
 */
public abstract class GenericDAO<E> implements Serializable {
    private static final long serialVersionUID = -8858151246267266279L;
    public static final String UNCHECKED = "unchecked";

    /** LOGGER */
    private static final Logger LOGGER = Logger.getLogger(GenericDAO.class);

    /** Nome padrao do SQL para listar os registros */
    private static final String PREFIX_LIST_QUERY = "list";
    
    /** Nome padrao do SQL para retornar um unico registro */
    private static final String PREFIX_FIND_QUERY = "find";
    
    /** Nome padrao do SQL para inserir um registro */
    private static final String PREFIX_INSERT_QUERY = "insert";
    
    /** Nome padrao do SQL para atualizar um registro */
    private static final String PREFIX_UPDATE_QUERY = "update";
    
    /** Nome padrao do SQL para remover um registro */
    private static final String PREFIX_DELETE_QUERY = "delete";

    /** Mensagens de debug para uso do cache */
    public static final String CACHE_FINDING_DATA = "Buscando dados no cache: ";
    public static final String CACHE_DATA_LOADED = "Dados carregados do cache: ";
    public static final String CACHE_DATA_NOT_FOUND = "Dados não existentes no cache, realizando busca no banco de dados: ";
    public static final String CACHE_DATA_ADDING = "Adicionando dados ao cache: ";
    public static final String CACHE_DATA_ADDED = "Dados adicionados ao cache: ";
    
    /** Session Factory do myBatis */
    private SqlSessionFactory sf;
    
    /** Tipo da classe da especificacao do DAO */
    private Class<E> daoType;

    /**
     * Construtor
     */
    public GenericDAO(Class<E> daoType, SqlSessionFactory sf) {
	this.daoType = daoType;
	this.sf = sf;
    }

    /** Retorna a Session Factory deste DAO */
    protected SqlSessionFactory getSessionFactory() {
	return sf;
    }

    /**
     * Retorna um registro com base no ID informado.
     * 
     * @param dtoClass Classe do DTO
     * @param id ID do registro a ser pesquisado
     * @return DTO recuperado do banco de dados
     * @throws TechnicalException
     */
    protected Object find(Class<?> dtoClass, Object id) throws TechnicalException {
	return this.find(dtoClass, PREFIX_FIND_QUERY, id);
    }

    /**
     * Retorna um registro com base nos filtros/modelo informado(s) e no SQL (queryName).
     * 
     * @param dtoClass Classe do DTO
     * @param queryName Nome da query mapeada
     * @param filter Filtros ou Modelo para pesquisa
     * @return DTO recuperado do banco de dados
     * @throws TechnicalException
     */
    protected Object find(Class<?> dtoClass, String queryName, Object filter) throws TechnicalException {
	return this.find(dtoClass.getSimpleName() + "_" + queryName, filter);
    }


    /**
     * Retorna um registro com base nos filtros/modelo informado(s) e no SQL (queryName).
     * 
     * @param queryName Nome da query mapeada
     * @return DTO recuperado do banco de dados
     * @throws TechnicalException
     */
    protected Object find(String queryName) throws TechnicalException {
	SqlSession session = openSession();
	Object obj = null;
	try {
	    long t1 = new Date().getTime();
	    String query = this.daoType.getSimpleName() + "." + queryName;
	    obj = (Object) session.selectOne(query);
	    LOGGER.debug("Query " + query + " executed in " + (new Date().getTime() - t1) + "ms");
	} catch (Exception e) {
	    LOGGER.error(e);
	    throw new TechnicalException(e);
	} finally {
	    closeSession(session);
	}
	return obj;
    }

    /**
     * Retorna um registro com base nos filtros/modelo informado(s) e no SQL (queryName).
     * 
     * @param queryName Nome da query mapeada
     * @param filter Filtros ou Modelo para pesquisa
     * @return DTO recuperado do banco de dados
     * @throws TechnicalException
     */
    protected Object find(String queryName, Object filter) throws TechnicalException {
	SqlSession session = openSession();
	Object obj = null;
	try {
	    long t1 = new Date().getTime();
	    String query = this.daoType.getSimpleName() + "." + queryName;
	    obj = (Object) session.selectOne(query, filter);
	    LOGGER.debug("Query " + query + " executed in " + (new Date().getTime() - t1) + "ms");
	} catch (Exception e) {
	    LOGGER.error(e);
	    throw new TechnicalException(e);
	} finally {
	    closeSession(session);
	}
	return obj;
    }

    /**
     * Retorna uma lista de registros, utilizando o SQL (queryName) para realizar a pesquisa.
     * 
     * @param queryName Nome da query (SQL) mapeada
     * @return List de DTO's recuperados do banco de dados
     * @throws TechnicalException
     */
    protected List<?> list(String queryName) throws TechnicalException {
	return this.list(queryName, null);
    }

    /**
     * Retorna uma lista de todos os registros do DTO informado.
     * 
     * @param dtoClass Classe do DTO
     * @return List de DTO's recuperados do banco de dados
     * @throws TechnicalException
     */
    protected List<?> list(Class<?> dtoClass) throws TechnicalException {
	return this.list(dtoClass, PREFIX_LIST_QUERY, null);
    }

    /**
     * Retorna uma lista de registros com base nos filtros/modelo informado(s), 
     * utilizando o SQL (queryName) para realizar a pesquisa.
     * 
     * @param dtoClass Classe do DTO
     * @param queryName Nome da query (SQL) mapeada
     * @param filter Filtros ou Modelo para pesquisa
     * @return List de DTO's recuperados do banco de dados
     * @throws TechnicalException
     */
    protected List<?> list(Class<?> dtoClass, String queryName, Object filter) throws TechnicalException {
	return this.list(dtoClass.getSimpleName() + "_" + queryName, filter);
    }

    /**
     * Retorna uma lista de registros com base nos filtros/modelo informado(s), 
     * utilizando o SQL (queryName) para realizar a pesquisa.
     * 
     * @param queryName Nome da query (SQL) mapeada
     * @param filter Filtros ou Modelo para pesquisa
     * @return List de DTO's recuperados do banco de dados
     * @throws TechnicalException
     */
    protected List<?> list(String queryName, Object filter) throws TechnicalException {
	SqlSession session = openSession();
	ArrayList<Object> list = null;
	try {
	    long t1 = new Date().getTime();
	    String query = this.daoType.getSimpleName() + "." + queryName;
	    if (filter == null) {
		list = (ArrayList<Object>) session.selectList(query);
	    } else {
		list = (ArrayList<Object>) session.selectList(query, filter);
	    }
	    LOGGER.debug("Query " + query + " executed in " + (new Date().getTime() - t1) + "ms");
	} catch (Exception e) {
	    LOGGER.error(e);
	    throw new TechnicalException(e);
	} finally {
	    closeSession(session);
	}
	return list;
    }

    /**
     * Executa a procedureName informando o(s) parametro(s) (parameter)
     * 
     * @param dtoClass Classe do DTO
     * @param procedureName Nome da procedure a ser chamada
     * @throws TechnicalException
     */
    protected void callProcedure(Class<?> dtoClass, String procedureName) throws TechnicalException {
	this.callProcedure(dtoClass, procedureName, null);
    }

    /**
     * Executa a procedureName informando o(s) parametro(s) (parameter)
     * 
     * @param dtoClass Classe do DTO
     * @param procedureName Nome da procedure a ser chamada
     * @param parameter Parametros da procedure
     * @throws TechnicalException
     */
    protected void callProcedure(Class<?> dtoClass, String procedureName, Object parameter) throws TechnicalException {
	this.callProcedure(dtoClass.getSimpleName() + "_" + procedureName, parameter);
    }

    /**
     * Executa a procedureName informando o(s) parametro(s) (parameter)
     * 
     * @param procedureName Nome da procedure a ser chamada
     * @param parameter Parametros da procedure
     * @throws TechnicalException
     */
    protected void callProcedure(String procedureName, Object parameter) throws TechnicalException {
	SqlSession session = openSession();
	try {
	    String query = this.daoType.getSimpleName() + "." + procedureName;
	    session.selectOne(query, parameter);
	} catch (Exception e) {
	    LOGGER.error(e);
	    throw new TechnicalException(e);
	} finally {
	    closeSession(session);
	}
    }

    /**
     * Insere o registro utilizando o SQL {@link GenericDAO#PREFIX_INSERT_QUERY} para inserir
     * 
     * @param dtoClass Classe do DTO
     * @param o Objeto DTO que será inserido
     * @return int O numero de linhas afetadas pelo insert.
     * @throws TechnicalException
     */
    protected int insert(Class<?> dtoClass, Object o) throws TechnicalException {
	return this.insert(dtoClass, PREFIX_INSERT_QUERY, o);
    }

    /**
     * Insere o registro utilizando o SQL {@link GenericDAO#PREFIX_INSERT_QUERY} para inserir
     * 
     * @param dtoClass Classe do DTO
     * @param queryName Nome da query (SQL) mapeada
     * @param o Objeto DTO que será inserido
     * @return int O numero de linhas afetadas pelo insert.
     * @throws TechnicalException
     */
    protected int insert(Class<?> dtoClass, String queryName, Object o) throws TechnicalException {
	return this.insert(dtoClass.getSimpleName() + "_" + queryName, o);
    }

    /**
     * Insere o registro utilizando o SQL {@link GenericDAO#PREFIX_INSERT_QUERY} para inserir
     * 
     * @param model Objeto DTO que será inserido
     * @return int O numero de linhas afetadas pelo insert.
     * @throws TechnicalException
     */
    protected int insert(String queryName, Object o) throws TechnicalException {
	SqlSession session = openSession();
	Integer status = null;
	try {
	    String query = this.daoType.getSimpleName() + "." + queryName;
	    status = (Integer) session.insert(query, o);
	} catch (Exception e) {
	    LOGGER.error(e);
	    throw new TechnicalException(e);
	} finally {
	    closeSession(session);
	}
	return status;
    }

    /**
     * Atualiza o registro utilizando o SQL {@link GenericDAO#PREFIX_UPDATE_QUERY} para atualizar
     * 
     * @param dtoClass Classe do DTO
     * @param o Objeto DTO que será atualizado
     * @return int O numero de linhas afetadas pelo update.
     * @throws TechnicalException
     */
    protected int update(Class<?> dtoClass, Object o) throws TechnicalException {
	return this.update(dtoClass, PREFIX_UPDATE_QUERY, o);
    }

    /**
     * Atualiza o registro utilizando o SQL {@link GenericDAO#PREFIX_UPDATE_QUERY} para atualizar
     * 
     * @param dtoClass Classe do DTO
     * @param queryName Nome da query (SQL) mapeada
     * @param o Objeto DTO que será atualizado
     * @return int O numero de linhas afetadas pelo update.
     * @throws TechnicalException
     */
    protected int update(Class<?> dtoClass, String queryName, Object o) throws TechnicalException {
	return this.update(dtoClass.getSimpleName() + "_" + queryName, o);
    }

    /**
     * Atualiza o registro utilizando o SQL {@link GenericDAO#PREFIX_UPDATE_QUERY} para atualizar
     * 
     * @param queryName Nome da query (SQL) mapeada
     * @param o Objeto DTO que será atualizado
     * @return int O numero de linhas afetadas pelo update.
     * @throws TechnicalException
     */
    protected int update(String queryName, Object o) throws TechnicalException {
	SqlSession session = openSession();
	Integer status = null;
	try {
	    String query = this.daoType.getSimpleName() + "." + queryName;
	    status = session.update(query, o);
	} catch (Exception e) {
	    LOGGER.error(e);
	    throw new TechnicalException(e);
	} finally {
	    closeSession(session);
	}
	return status;
    }

    /**
     * Remove o registro utilizando o SQL {@link GenericDAO#PREFIX_DELETE_QUERY} para remover
     * 
     * @param dtoClass Classe do DTO
     * @param o Objeto DTO que será removido
     * @return int O numero de linhas afetadas pelo delete.
     * @throws TechnicalException
     */
    protected int delete(Class<?> dtoClass, Object id) throws TechnicalException {
	return this.delete(dtoClass, PREFIX_DELETE_QUERY, id);
    }

    /**
     * Remove o registro utilizando o SQL {@link GenericDAO#PREFIX_DELETE_QUERY} para remover
     * 
     * @param dtoClass Classe do DTO
     * @param queryName Nome da query (SQL) mapeada
     * @param o Objeto DTO que será removido
     * @return int O numero de linhas afetadas pelo delete.
     * @throws TechnicalException
     */
    protected int delete(Class<?> dtoClass, String queryName, Object id) throws TechnicalException {
	return this.delete(dtoClass.getSimpleName() + "_" + queryName, id);
    }

    /**
     * Remove o registro utilizando o SQL {@link GenericDAO#PREFIX_DELETE_QUERY} para remover
     * 
     * @param queryName Nome da query (SQL) mapeada
     * @param o Objeto DTO que será removido
     * @return int O numero de linhas afetadas pelo delete.
     * @throws TechnicalException
     */
    protected int delete(String queryName, Object id) throws TechnicalException {
	SqlSession session = openSession();
	Integer status = null;
	try {
	    String query = this.daoType.getSimpleName() + "." + queryName;
	    status = session.delete(query, id);
	} catch (Exception e) {
	    LOGGER.error(e);
	    throw new TechnicalException(e);
	} finally {
	    closeSession(session);
	}
	return status;
    }

    /**
     * Abre sessão do mybatis
     * @return
     */
    private SqlSession openSession() throws TechnicalException {
        try {
            return sf.openSession();
        } catch (Exception e) {
            LOGGER.error(e);
            throw new TechnicalException(e);
        }
    }

    /**
     * Fecha a sessão do mybatis
     * 
     * @param session
     * @throws TechnicalException
     */
    private void closeSession(SqlSession session) throws TechnicalException {
		if (session != null) {
			try {
				session.close();
			} catch (Exception e) {
				LOGGER.error(e);
				throw new TechnicalException(e);
			}
		}
    }

    /**
     * Helper genérico para obter uma lista de objetos do cache
     * @param methodName
     * @param cacheKey
     * @param cacheType
     * @return
     */
    public List<?> getCachedList(String methodName, Long cacheKey, CacheType cacheType) {
    	LOGGER.debug(CACHE_FINDING_DATA + methodName + ", " + cacheKey);
		Map<Long, List<?>> map = CacheWrapper.getObject(cacheType);
		if (map != null && map.containsKey(cacheKey)) {
			LOGGER.debug(CACHE_DATA_LOADED + methodName + ", " + cacheKey);
			return map.get(cacheKey);
		} else {
			LOGGER.debug(CACHE_DATA_NOT_FOUND + methodName + ", " + cacheKey);
			return null;
		}
    }
    
    /**
     * Helper genérico para salvar uma lista de objetos do cache
     * @param methodName
     * @param cacheKey
     * @param cacheType
     * @param list
     */
    public void putCacheList(String methodName, Long cacheKey, CacheType cacheType, List<?> list) {
		LOGGER.debug(CACHE_DATA_ADDING + methodName + ", " + cacheKey);
		Map<Long, List<?>> map = CacheWrapper.getObject(cacheType);
		if (map == null) {
			map = new HashMap<Long, List<?>>();
		}
		map.put(cacheKey, list);
		CacheWrapper.setObject(cacheType, map);
		LOGGER.debug(CACHE_DATA_ADDED + methodName + ", " + cacheKey);
    }
    
    /**
     * Helper genérico para obter um objeto do cache
     * @param methodName
     * @param cacheKey
     * @param cacheType
     * @return
     */
    public Object getCachedObject(String methodName, Object cacheKey, CacheType cacheType) {
    	LOGGER.debug(CACHE_FINDING_DATA + methodName + ", " + cacheKey);
		Map<Object, Object> map = CacheWrapper.getObject(cacheType);
		if (map != null && map.containsKey(cacheKey)) {
			LOGGER.debug(CACHE_DATA_LOADED + methodName + ", " + cacheKey);
			return map.get(cacheKey);
		} else {
			LOGGER.debug(CACHE_DATA_NOT_FOUND + methodName + ", " + cacheKey);
			return null;
		}
    }

    /**
     * Helper genérico para salvar um objeto no cache
     * @param methodName
     * @param cacheKey
     * @param cacheType
     * @param obj
     */
    public void putCacheObject(String methodName, Object cacheKey, CacheType cacheType, Object obj) {
		LOGGER.debug(CACHE_DATA_ADDING + methodName + ", " + cacheKey);
		Map<Object, Object> map = CacheWrapper.getObject(cacheType);
		if (map == null) {
			map = new HashMap<Object, Object>();
		}
		map.put(cacheKey, obj);
		CacheWrapper.setObject(cacheType, map);
		LOGGER.debug(CACHE_DATA_ADDED + methodName + ", " + cacheKey);
    }

}
