package com.hystericalporpoises.dns;

class OverrideNameServiceManager {

  private final InheritableThreadLocal<OverrideNameService> configuration =
      new InheritableThreadLocal<OverrideNameService>();

  private static final OverrideNameServiceManager MANAGER = new OverrideNameServiceManager();

  void initializeForThread(OverrideNameService configuration) {
    MANAGER.configuration.set(configuration);
  }

  String getIpForHost(String host) {
    return MANAGER.configuration.get().getIpForHost(host);
  }
}
