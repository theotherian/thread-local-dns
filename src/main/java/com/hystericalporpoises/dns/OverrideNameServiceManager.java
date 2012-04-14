package com.hystericalporpoises.dns;

class OverrideNameServiceManager {

  private final InheritableThreadLocal<OverrideNameService> configuration =
      new InheritableThreadLocal<OverrideNameService>();

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
