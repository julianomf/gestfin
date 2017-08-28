package br.com.nsol.gestfin.service;

import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.rmi.PortableRemoteObject;

import org.apache.log4j.Logger;

import br.com.nsol.gestfin.exceptions.TechnicalException;

/**
 * <b>Singleton</b> responsável por realizar o lookup dos Facades
 * 
 * @author 
 */
public final class ServiceLocator {
    private static final Logger LOG = Logger.getLogger(ServiceLocator.class);
	private static final String GESTFIN_BUSINESS_URL = "GESTFIN_BUSINESS_URL";
	private static InitialContext context = null;
	
	/**
	 * Construtor inacessível
	 */
	private ServiceLocator() {
		// Do nothing
	}

	/**
	 * Método responsável por realizar o lookup do Facade passado como
	 * parâmetro
	 * 
	 * @param service
	 * @return
	 * @throws TechnicalException
	 */
	public static Object getService(Class<?> service) throws TechnicalException {
		if (context == null) {
			try {
				Properties prop = new Properties();
				prop.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
				prop.put("jboss.naming.client.ejb.context",true);
				context = new InitialContext(prop);
			} catch (NamingException e) {
				LOG.error("ServiceLocator getService error: ", e);
				throw new TechnicalException("Erro", e);
			}
		}
		return lookup(service);
	}

	/**
	 * Método que realiza o lookup no contexto
	 * 
	 * @param clazz
	 * @return
	 * @throws TechnicalException
	 */
	private static Object lookup(Class<?> clazz) throws TechnicalException {
		try {
			
			String businessUrl = System.getProperty(GESTFIN_BUSINESS_URL);
			if (businessUrl == null || businessUrl.isEmpty()) {
				throw new Exception("Configuração de ambiente inválida: propriedade GESTFIN_BUSINESS_URL não definida.");
			}
			LOG.info("GESTFIN_BUSINESS_URL: " + businessUrl);
			
			Object referece = context.lookup(businessUrl + clazz.getSimpleName() + "!" + clazz.getName() + "Remote");
			
			return PortableRemoteObject.narrow(referece, clazz); 
		} catch (Exception e) {
			LOG.error("ServiceLocator lookup error: ", e);
			throw new TechnicalException("ServiceLocator lookup error", e);
		}
	}
}
