package com.theotherian.dns;

import com.google.common.cache.LoadingCache;

import java.net.InetAddress;
import java.util.Collections;
import java.util.concurrent.ExecutionException;

/**
 *
 * @author isimpson
 *
 */
class OverrideNameServiceManager {

  private final InheritableThreadLocal<OverrideNameService> configuration =
      new InheritableThreadLocal<OverrideNameService>() {
      @Override
      protected OverrideNameService initialValue() {
        return new OverrideNameService(Collections.<IpToHostsMapping>emptyList());
      };
  };

  private InheritableThreadLocal<LoadingCache<String, InetAddress[]>> threadLocalDnsCache = new InheritableThreadLocal<LoadingCache<String,InetAddress[]>>() {
    @Override
    protected LoadingCache<String, InetAddress[]> initialValue() {
      return CacheFactory.newCache();
    }
  };

  private static final OverrideNameServiceManager MANAGER = new OverrideNameServiceManager();

  private OverrideNameServiceManager() {}

  static void initializeForThread(OverrideNameService configuration) {
    MANAGER.configuration.set(configuration);
  }

  static String getIpForHost(String host) {
    return instance().getIpForHost(host);
  }

  static boolean hasIpForHost(String host) {
    return instance().hasIpForHost(host);
  }

  static void initializeCache() {
    MANAGER.threadLocalDnsCache.set(CacheFactory.newCache());
  }

  static InetAddress[] lookup(String host) throws ExecutionException {
    return MANAGER.threadLocalDnsCache.get().get(host);
  }

  private static OverrideNameService instance() {
    return MANAGER.configuration.get();
  }

}
