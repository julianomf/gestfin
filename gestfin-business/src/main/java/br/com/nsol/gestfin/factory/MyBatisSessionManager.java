package br.com.nsol.gestfin.factory;

import java.io.IOException;
import java.io.Reader;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.log4j.Logger;

/**
 * Cria uma sessao de gerenciamento do MyBatis
 * @author Volans Informatica Ltda.
 */
public final class MyBatisSessionManager {
	private static final Logger LOG = Logger.getLogger(MyBatisDAOFactoryImpl.class);
	private static final String MYBATIS_CONF_FILE = "mybatis/mybatis.gestfin.config.xml";
	private static SqlSessionFactory manager;

	private MyBatisSessionManager() {
	      //for sonar only
	}

	/**
	 * Retorna uma instancia de sessão do Mybatis
	 * @return Objeto de sessão
	 */
	public static SqlSessionFactory getInstance() {
		if (manager == null) {
			Reader reader = null;
			try {
				reader = Resources.getResourceAsReader(MYBATIS_CONF_FILE);
				LOG.info("Leitura das configurações do MyBatis realizada");
			} catch (IOException e) {
				LOG.error("Erro ao inicializar o mybatis", e);
			}
			manager = new SqlSessionFactoryBuilder().build(reader);
		}
		return manager;
	}

}
