package br.com.nsol.gestfin.cache;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;


/**
 * Listener que deve ser colocado no web.xml, para iniciar a configuração do
 * Cache junto com a aplicação.
 * 
 * @author jmfreitas
 * 
 */
public class CacheListener implements ServletContextListener {
    private static final Logger LOGGER = Logger.getLogger(CacheListener.class);

    public void contextInitialized(ServletContextEvent event) {
	LOGGER.debug("###########  Iniciando o cache ###########");
	try {
	    CacheWrapper.init();
	    LOGGER.debug("###########  Cache inicializado ###########");
	} catch (Exception e) {
	    LOGGER.error("###########  Erro iniciando o cache " + e);
	}
    }

    public void contextDestroyed(ServletContextEvent event) {
	LOGGER.debug("###########  Finalizando o cache ###########");
	try {
	    CacheWrapper.destroy();
	    LOGGER.debug("###########  Cache finalizado ###########");
	} catch (Exception e) {
	    LOGGER.error(e);
	}
    }
}
