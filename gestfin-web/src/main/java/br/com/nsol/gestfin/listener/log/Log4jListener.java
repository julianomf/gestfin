package br.com.nsol.gestfin.listener.log;

import java.io.InputStream;
import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 * Listener permite iniciar as configurações da aplicação.
 * 
 * @author 
 * 
 */
@WebListener
public class Log4jListener implements ServletContextListener {
	private static final String LOG4J_FILE = "log4j.xml";

	private static boolean isLog4jInitialized = false;

	/**
	 * Iniciliza o Log4j
	 * 
	 * @see javax.servlet.ServletContextListener#contextInitialized(javax.servlet.ServletContextEvent)
	 */
	@Override
	public void contextInitialized(ServletContextEvent event) {
		if (!isLog4jInitialized) {
			InputStream is = null;
			try {
				try {
					is = Log4jListener.class.getClassLoader().getResourceAsStream(LOG4J_FILE);
				} catch (Exception e) {
					System.err.println("Exception: " + LOG4J_FILE); // NOSONAR
				}
				if (is == null) {
					System.err.println("Erro na inicializacao do Log4JListener." + LOG4J_FILE); // NOSONAR
				} else {
					Properties prop = new Properties();
					prop.load(is);
					PropertyConfigurator.configure(prop);
					Logger log = Logger.getLogger(Log4jListener.class);
					log.warn("Log4JListener inicializado corretamente, arquivo de configuracao:" + LOG4J_FILE);
					isLog4jInitialized = true;
				}
			} catch (Exception e) {
				System.err.print(e); // NOSONAR
			}
		}
	}

	/**
	 * @see javax.servlet.ServletContextListener#contextDestroyed(javax.servlet.ServletContextEvent)
	 */
	@Override
	public void contextDestroyed(ServletContextEvent event) {
		// Do nothing
	}
}
