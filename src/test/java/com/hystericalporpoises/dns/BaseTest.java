package com.hystericalporpoises.dns;

import static com.hystericalporpoises.dns.DnsConfigurationBuilder.*;

public abstract class BaseTest {

  static {
    ThreadLocalDns.initialize();
    ThreadLocalDnsConfiguration dnsConfiguration = newBuilder().map(
      hosts("www.google.com", "www.yahoo.com"), to("192.168.1.1")).build();
    OverrideNameService nameService = new OverrideNameService(dnsConfiguration.getMappings());
    OverrideNameServiceManager.initializeForThread(nameService);
  }

}
