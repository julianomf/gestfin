package br.com.nsol.gestfin.cache;


public enum CacheTimerRegion {

    REGION_0001_MIN(), //
    REGION_0002_MIN(), //
    REGION_0003_MIN(), //
    REGION_0005_MIN(), //
    REGION_0010_MIN(), //
    REGION_0015_MIN(), //
    REGION_0020_MIN(), //
    REGION_0030_MIN(), //
    REGION_0060_MIN(), //
    REGION_0120_MIN(), //
    REGION_0360_MIN(), //
    REGION_0720_MIN(), //
    REGION_1440_MIN();

    public static CacheTimerRegion get(String name) {
	for (CacheTimerRegion cacheTimerRegion : values()) {
	    if (cacheTimerRegion.toString().equals(name)) {
		return cacheTimerRegion;
	    }
	}
	return null;
    }

}