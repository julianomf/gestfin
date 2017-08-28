package br.com.nsol.gestfin.cache;

/**
 * 
 */

/**
 * @author dmarson
 * 
 */
public enum CacheType {
    LIST_AOV(CacheTimerRegion.REGION_0060_MIN, "LIST_AOV"), 
    LIST_REGION(CacheTimerRegion.REGION_0060_MIN, "LIST_REGION"), 
    LIST_DEALERS_BY_REGION(CacheTimerRegion.REGION_0060_MIN, "LIST_DEALERS_BY_REGION"), 
    LIST_PURPOSES(CacheTimerRegion.REGION_0060_MIN, "LIST_PURPOSES"), 	
    LIST_VEHICLE_BRANDS(CacheTimerRegion.REGION_0060_MIN, "LIST_VEHICLE_BRANDS"), 
    LIST_VEHICLE_MODELS(CacheTimerRegion.REGION_0060_MIN, "LIST_VEHICLE_MODELS"), 
    LIST_VEHICLE_VERSIONS(CacheTimerRegion.REGION_0060_MIN, "LIST_VEHICLE_VERSIONS"), 
    LIST_VEHICLE_VERSION_YEARS(CacheTimerRegion.REGION_0060_MIN, "LIST_VEHICLE_VERSION_YEARS"), 
    LIST_VEHICLE_DOORS(CacheTimerRegion.REGION_0060_MIN, "LIST_VEHICLE_DOORS"), 
    LIST_TRANSMISSION_TYPES(CacheTimerRegion.REGION_0060_MIN, "LIST_TRANSMISSION_TYPES"), 
    LIST_FUEL_TYPES(CacheTimerRegion.REGION_0060_MIN, "LIST_FUEL_TYPES"), 
    LIST_BODY_TYPES(CacheTimerRegion.REGION_0060_MIN, "LIST_BODY_TYPES"), 
    LIST_VEHICLE_COLORS(CacheTimerRegion.REGION_0060_MIN, "LIST_VEHICLE_COLORS"), 
    LIST_STATES(CacheTimerRegion.REGION_0060_MIN, "LIST_STATES"),
    LIST_OPERATORS(CacheTimerRegion.REGION_0060_MIN, "LIST_OPERATORS"),
    LIST_ESTABLISHMENTS(CacheTimerRegion.REGION_0060_MIN, "LIST_ESTABLISHMENTS"),
    LIST_CARD_FLAGS(CacheTimerRegion.REGION_0060_MIN, "LIST_CARD_FLAGS"),
    LIST_TRANSACTION_TYPES(CacheTimerRegion.REGION_0060_MIN, "LIST_TRANSACTION_TYPES"),
    
    LIST_DAMAGES(CacheTimerRegion.REGION_0060_MIN, "LIST_DAMAGES"), 
    LIST_DAMAGES_DETAILS(CacheTimerRegion.REGION_0060_MIN, "LIST_DAMAGE_DETAILS"),

    LIST_VEHICLE_MODELS_MATCHES(CacheTimerRegion.REGION_0060_MIN, "LIST_VEHICLE_MODELS_MATCHES"), 

    FIND_PARAMETER_STRING_VALUE(CacheTimerRegion.REGION_0060_MIN, "FIND_PARAMETER_STRING_VALUE"),
    FIND_PARAMETER_INT_VALUE(CacheTimerRegion.REGION_0060_MIN, "FIND_PARAMETER_INT_VALUE"),
    FIND_PARAMETER_FLOAT_VALUE(CacheTimerRegion.REGION_0060_MIN, "FIND_PARAMETER_FLOAT_VALUE"),
    
    FIND_JOB_MONITORING_STATUS_LAYER_BY_ID(CacheTimerRegion.REGION_0060_MIN, "FIND_JOB_MONITORING_STATUS_LAYER_BY_ID"),
    FIND_JOB_MONITORING_STATUS_FUNCTION_BY_ID(CacheTimerRegion.REGION_0060_MIN, "FIND_JOB_MONITORING_STATUS_FUNCTION_BY_ID");

    private CacheTimerRegion region;
    private String key;

    private CacheType(CacheTimerRegion region, String key) {
	this.region = region;
	this.key = key;
    }

    public void changeRegion(CacheTimerRegion region) {
	if (region != null) {
	    CacheWrapper.setObject(this, null);
	    this.region = region;
	}
    }

    public CacheTimerRegion getRegion() {
	return region;
    }

    public String getKey() {
	return key;
    }

    public static CacheType get(String name) {
	for (CacheType cacheType : values()) {
	    if (cacheType.toString().equals(name)) {
		return cacheType;
	    }
	}
	return null;
    }

    public static void changeTimerRegion(CacheType cacheType, CacheTimerRegion cacheTimerRegion) {
	cacheType.changeRegion(cacheTimerRegion);
    }

    public static void changeTimerRegion(String cacheType, CacheTimerRegion cacheTimerRegion) {
	CacheType _cacheType = CacheType.get(cacheType);
	if (_cacheType != null) {
	    _cacheType.changeRegion(cacheTimerRegion);
	}
    }
}
