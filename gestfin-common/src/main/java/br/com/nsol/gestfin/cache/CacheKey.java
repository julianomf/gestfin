package br.com.nsol.gestfin.cache;

import java.io.Serializable;

/**
 * Classe para ser utilizada como chave dos objetos cacheados.
 * 
 * @author jmfreitas
 */
public class CacheKey implements Serializable {

    private static final long serialVersionUID = -6828678192201199268L;

    /**
     * Key do objeto
     */
    private String key = null;

    /**
     * Usado para identificar a origem do objeto
     */
    private String cacheServerID = null;

    public CacheKey() {
    }

    public CacheKey(String key, String cacheServerID) {
	this.key = key;
	this.cacheServerID = cacheServerID;
    }

    public String toString() {
	return "key[" + key + "] cacheServerID[" + cacheServerID + "]";
    }

    @Override
    public int hashCode() {
	int hash = 0;
	hash += (key != null ? key.hashCode() : 0);
	return hash;
    }

    @Override
    public boolean equals(Object object) {
	if (object instanceof CacheKey) {
	    return ((CacheKey) object).getKey().equals(this.getKey());
	}
	return false;
    }

    public String getKey() {
	return key;
    }

    public void setKey(String key) {
	this.key = key;
    }

    public String getCacheServerID() {
	return cacheServerID;
    }

    public void setCacheServerID(String cacheServerID) {
	this.cacheServerID = cacheServerID;
    }

}
