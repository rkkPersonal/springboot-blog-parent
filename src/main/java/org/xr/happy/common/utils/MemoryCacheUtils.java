package org.xr.happy.common.utils;

import com.google.common.cache.CacheLoader;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Steven
 */
public class MemoryCacheUtils {

    private Map<String, CacheLoader> containerMap= new ConcurrentHashMap<>();


}
