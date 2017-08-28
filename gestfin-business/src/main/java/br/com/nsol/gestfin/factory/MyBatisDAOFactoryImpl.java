package br.com.nsol.gestfin.factory;

import java.io.IOException;
import java.io.Reader;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.log4j.Logger;

/**
 * Classe de implementação do factory MyBatisDAOFactory
 *
 */
public class MyBatisDAOFactoryImpl extends MyBatisDAOFactory {
	private static final Logger LOG = Logger.getLogger(MyBatisDAOFactoryImpl.class);

	private SqlSessionFactory manager;

	@Override
	public SqlSessionFactory getEntityManager() {
		if (this.manager == null) {
			Reader reader = null;
			try {
				reader = Resources.getResourceAsReader(MYBATIS_CONF_FILE);
				LOG.info("Leitura das configurações do MyBatis realizada");
			} catch (IOException e) {
				LOG.error("Erro ao inicializar o mybatis", e);
			}
			this.manager = new SqlSessionFactoryBuilder().build(reader);
		}
		return this.manager;
	}

	/*
	 * Common DAO Instances
	 */

}