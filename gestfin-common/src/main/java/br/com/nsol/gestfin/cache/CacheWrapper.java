package br.com.nsol.gestfin.cache;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.util.List;
import java.util.Random;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import net.sf.ehcache.Statistics;
import net.sf.ehcache.config.CacheConfiguration;
import net.sf.ehcache.management.CacheStatistics;

import org.apache.log4j.Logger;

/**
 * Classe utilitária para o gerenciamento do Cache
 * 
 * @author jmfreitas
 */
public final class CacheWrapper {
    private static final Logger LOGGER = Logger.getLogger(CacheWrapper.class);
    private static final String EH_CACHE_FILE = "ehcache.xml";
    private static boolean cacheInicializado = false;
    
    public static String CACHE_SERVER_ID = null;//NOSONAR

    static {
		try {
			init();
		} catch (Exception e) {
			LOGGER.error("#---  Erro iniciando o cache " + e);
		}
    }

    private CacheWrapper() {
		// Do Nothing
    }

    /**
     * Inicializa o Cache
     * 
     * @throws FileNotFoundException
     */
    public static void init() throws FileNotFoundException {
		if (!cacheInicializado) {
			java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

			String ip = null;
			try {
				ip = InetAddress.getLocalHost().toString();
			} catch (Exception e) {
				LOGGER.error("Falha ao obter o IP do servidor.");
			}
			CACHE_SERVER_ID = "CID-" + formatter.format(new java.util.Date()) + (ip != null ? ("-" + ip) : "") + "-"
					+ new Random().nextInt();

			InputStream is = null;
			String ehCacheFile = System.getProperty("dir.app", "") + "/ehcache.xml";
			File file = new File(ehCacheFile);
			if (file.exists()) {
				is = new FileInputStream(file);
			}
			if (is == null) {
				LOGGER.debug(EH_CACHE_FILE + " nao foi identificado em[" + ehCacheFile
						+ "], tentando localizar o arquivo no classpath.");
				is = CacheWrapper.class.getClassLoader().getResourceAsStream(EH_CACHE_FILE);
			}
			if (is == null) {
				throw new FileNotFoundException(EH_CACHE_FILE + " nao encontrado.");
			}
			try {
				CacheManager.create(is);
				Thread.sleep(3000);// wait for synchronize
				cacheInicializado = true;
				LOGGER.debug("#---# O cache inicializado com sucesso #---#");
			} catch (Exception e) {
				LOGGER.error(e);
			} finally {
				try {
					is.close();
				} catch (IOException e) {
					LOGGER.error(e);
				}
			}
		} else {
			LOGGER.debug("O cache ja estava inicializado!");
		}
    }

    /**
     * Reinicia o cache.
     * 
     * @throws FileNotFoundException
     * 
     */
    public static void restart() throws FileNotFoundException {
		if (!cacheInicializado) {
			destroy();
		}
		init();
    }

    /**
     * Destrói o cache
     * 
     */
    public static synchronized void destroy() {
		if (cacheInicializado) {
		    LOGGER.debug("Destruindo o cache.");
		    CacheManager.getInstance().shutdown();
		    cacheInicializado = false;
		}
    }

    /**
     * Inicializa as estatísticas de Cache
     * 
     * @param cacheRegion
     * @return CacheStatistics
     * 
     */
    public static CacheStatistics getCacheStatistics(String cacheRegion) {
		net.sf.ehcache.Cache cache = CacheManager.getInstance().getCache(cacheRegion);
		return new CacheStatistics(cache);
    }

    /**
     * Recupera as estatísticas de Cache
     * 
     * @param cacheRegion
     * @return Statistics
     * @throws Exception
     */
    public static Statistics getStatistics(String cacheRegion) {
		net.sf.ehcache.Cache cache = CacheManager.getInstance().getCache(cacheRegion);
		return cache.getStatistics();
    }

    /**
     * Recupera as Keys da region informada
     * 
     * @param cacheRegion
     * @return List com as keys
     * @throws Exception
     */
    public static List<?> getKeys(String cacheRegion) {
		net.sf.ehcache.Cache cache = CacheManager.getInstance().getCache(cacheRegion);
		return cache.getKeys();
    }

    public static <E> E getObject(CacheType cacheType) {
    	return getObject(cacheType.getRegion().toString(), cacheType.getKey());
    }

    /**
     * Recupera o objeto com idObj da cacheRetion informada.
     * 
     * @param cacheRegion Região do cache
     * @param idObj ID do objeto a ser recuperado
     * @return Objeto recuperado do cache
     */
    @SuppressWarnings("unchecked")
    public static <E> E getObject(String cacheRegion, String idObj) {
		try {
			net.sf.ehcache.Cache cache = CacheManager.getInstance().getCache(cacheRegion);
			if (cache == null) {
				LOGGER.debug(
						"Cache nao inicializado, verifique se o arquivo '" + EH_CACHE_FILE + "' esta no classpath.");
				return null;
			} else {
				Element element = cache.get(new CacheKey(idObj, CACHE_SERVER_ID));
				if (element != null) {
					LOGGER.debug("#--- Objeto " + idObj + " encontrado na regiao " + cacheRegion);
					return (E) element.getObjectValue();
				} else {
					LOGGER.debug("#--- Objeto " + idObj + " NÃO encontrado na região " + cacheRegion);
					return null;
				}
			}
		} catch (Exception e) {
			LOGGER.error("Erro ao recuperar do cache: " + cacheRegion + " idObj " + idObj, e);
			return null;
		}
    }

    public static void setObject(CacheType cacheType, Object value) {
    	setObject(cacheType.getRegion().toString(), cacheType.getKey(), value);
    }

    /**
     * Coloca o objeto value com idObj na cacheRegion informada
     * 
     * @param cacheRegion Região do cache
     * @param idObj ID do objeto a ser recuperado
     * @param value Objeto a ser armazenado
     */
    public static void setObject(String cacheRegion, String idObj, Object value) {
		try {
			net.sf.ehcache.Cache cache = CacheManager.getInstance().getCache(cacheRegion);
			if (cache == null) {
				LOGGER.debug("Regiao do cache[" + cacheRegion + "] nao encontrada, criando uma a partir da default.");
				CacheManager.getInstance().addCache(cacheRegion);
				cache = CacheManager.getInstance().getCache(cacheRegion);
				if (cache == null) {
					LOGGER.error("Nao foi possivel criar o cache[" + cacheRegion + "].");
				}
			}

			if (cache != null) {
				Element element = new Element(new CacheKey(idObj, CACHE_SERVER_ID), value);
				cache.put(element);
			}
		} catch (Exception e) {
			LOGGER.error("Erro ao colocar no cache: " + cacheRegion + " idObj " + idObj, e);
		}
    }

    /**
     * Recupera o CacheManager
     * 
     * @return CacheManager
     * @throws Exception
     */
    public static CacheManager getCacheImpl() {
    	return CacheManager.getInstance();
    }

    /**
     * Cria uma região de cache
     * 
     * @param cacheRegion ID da região
     * @param maxElementsInMemory Máximo de elementos p/ manter em memória
     * @param timeToLiveSeconds
     * @param timeToIdleSeconds
     * @throws Exception
     */
    public static void createRegion(String cacheRegion, int maxElementsInMemory, long timeToLiveSeconds, long timeToIdleSeconds) {
		net.sf.ehcache.Cache cache = CacheManager.getInstance().getCache(cacheRegion);
		if (cache == null) {
			LOGGER.debug((new StringBuilder()).append("Regiao do cache[").append(cacheRegion)
					.append("] nao encontrada, criando uma a partir da default.").toString());
			CacheManager.getInstance().addCache(cacheRegion);
			CacheConfiguration config = CacheManager.getInstance().getCache(cacheRegion).getCacheConfiguration();
			config.setMaxElementsInMemory(maxElementsInMemory);
			config.setTimeToLiveSeconds(timeToLiveSeconds);
			config.setTimeToIdleSeconds(timeToIdleSeconds);
			if (cache == null) {
				LOGGER.error((new StringBuilder()).append("Nao foi possivel criar o cache[").append(cacheRegion)
						.append("].").toString());
			}
		}
    }

    /**
     * Remove um determinado objeto de uma região.
     * 
     * @param cacheRegion
     * @param idObj
     * @throws Exception
     */
    public static void removeObject(String cacheRegion, String idObj) {
		net.sf.ehcache.Cache cache = CacheManager.getInstance().getCache(cacheRegion);
		if (cache == null) {
			LOGGER.debug((new StringBuilder()).append("Regiao do cache[").append(cacheRegion)
					.append("] nao encontrada, criando uma a partir da default.").toString());
			CacheManager.getInstance().addCache(cacheRegion);
			cache = CacheManager.getInstance().getCache(cacheRegion);
			if (cache == null) {
				LOGGER.error((new StringBuilder()).append("Nao foi possivel criar o cache[").append(cacheRegion)
						.append("].").toString());
			}
		}
		if (cache != null) {
			cache.remove(new CacheKey(idObj, CACHE_SERVER_ID));
		}
    }
}
