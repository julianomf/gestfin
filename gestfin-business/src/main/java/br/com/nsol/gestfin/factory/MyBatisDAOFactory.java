package br.com.nsol.gestfin.factory;

import org.apache.ibatis.session.SqlSessionFactory;

/**
 * Factory do MyBatis, para obter os DAO's
 */
public abstract class MyBatisDAOFactory {
    public static final String NAME = "MyBatisDAOFactory";
    
    public static final String MYBATIS_CONF_FILE = "mybatis/mybatis.gestfin.config.xml";

    /*
     * Singleton Pattern
     */
    private static MyBatisDAOFactory instance = null;

    protected MyBatisDAOFactory() {
	// Exists only to defeat instantiation.
    }

    /**
     * Retorna a única instância do MyBatisFactory
     * 
     * @return MyBatisDAOFactory
     */
    public static MyBatisDAOFactory getInstance() {
	if (instance == null) {
	    instance = new MyBatisDAOFactoryImpl();
	}
	return instance;
    }

    /**
     * Retorna o entityManager do MyBatis
     * @return SqlSessionFactory
     */
    public abstract SqlSessionFactory getEntityManager();

}