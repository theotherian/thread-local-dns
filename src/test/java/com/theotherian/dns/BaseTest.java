package com.theotherian.dns;

import static com.theotherian.dns.DnsConfigurationBuilder.*;

import com.theotherian.dns.OverrideNameService;
import com.theotherian.dns.OverrideNameServiceManager;
import com.theotherian.dns.ThreadLocalDns;
import com.theotherian.dns.ThreadLocalDnsConfiguration;

public abstract class BaseTest {

  static {
    ThreadLocalDns.initialize();
    ThreadLocalDnsConfiguration dnsConfiguration = newBuilder().map(
      hosts("www.google.com", "www.yahoo.com"), to("192.168.1.1")).build();
    OverrideNameService nameService = new OverrideNameService(dnsConfiguration.getMappings());
    OverrideNameServiceManager.initializeForThread(nameService);
  }

}
