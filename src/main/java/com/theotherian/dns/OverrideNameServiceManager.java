package com.theotherian.dns;

import java.util.Collections;

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

  private static OverrideNameService instance() {
    return MANAGER.configuration.get();
  }

}
