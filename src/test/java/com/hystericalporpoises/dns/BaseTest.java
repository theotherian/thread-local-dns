package com.hystericalporpoises.dns;

import com.google.common.collect.Lists;

public abstract class BaseTest {

  public static final int LOCAL_GOOGLE_PROXY_PORT = 44444;

  public static final int LOCAL_YAHOO_PROXY_PORT = 44445;

  static {
    ThreadLocalDns.initialize(createConfig());
    ThreadLocalDnsConfiguration dnsConfiguration = TestObjects.makeThreadLocalDnsConfiguration();
    OverrideNameService nameService = new OverrideNameService(dnsConfiguration.getMappings());
    OverrideNameServiceManager.initializeForThread(nameService);
    TestServlets.createServlets();
  }

  private static DnsConfiguration createConfig() {
    DnsConfiguration configuration = new DnsConfiguration(Lists.newArrayList(
      new ThreadLocalDnsConfiguration(LOCAL_GOOGLE_PROXY_PORT, Lists.newArrayList(
        new IpToHostsMapping("127.0.0.1", Lists.newArrayList("www.google.com"))
      )),
      new ThreadLocalDnsConfiguration(LOCAL_YAHOO_PROXY_PORT, Lists.newArrayList(
        new IpToHostsMapping("127.0.0.1", Lists.newArrayList("www.yahoo.com"))
      ))
    ));
    return configuration;
  }

}
