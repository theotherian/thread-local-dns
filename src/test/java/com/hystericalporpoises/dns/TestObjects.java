package com.hystericalporpoises.dns;

import com.google.common.collect.Lists;

public class TestObjects {

  public static final String IP_TO_HOSTS_MAPPING_JSON =
      "{\"ipAddress\":\"192.168.1.1\",\"hosts\":[\"www.google.com\",\"www.yahoo.com\"]}";

  public static final String THREAD_LOCAL_DNS_CONFIG_JSON =
      "{\"proxyPort\":12345,\"mappings\":[" + IP_TO_HOSTS_MAPPING_JSON + "," +
          IP_TO_HOSTS_MAPPING_JSON + "]}";

  public static final String DNS_CONFIG_JSON = "{\"dnsConfigurations\":[" +
      THREAD_LOCAL_DNS_CONFIG_JSON + "," + THREAD_LOCAL_DNS_CONFIG_JSON + "]}";

  public static DnsConfiguration makeDnsConfiguration() {
    DnsConfiguration configuration = new DnsConfiguration(
      Lists.newArrayList(makeThreadLocalDnsConfiguration(), makeThreadLocalDnsConfiguration())
    );
    return configuration;
  }

  public static ThreadLocalDnsConfiguration makeThreadLocalDnsConfiguration() {
    ThreadLocalDnsConfiguration configuration = new ThreadLocalDnsConfiguration(12345,
      Lists.newArrayList(makeMapping(), makeMapping()));
    return configuration;
  }

  public static IpToHostsMapping makeMapping() {
    IpToHostsMapping mapping = new IpToHostsMapping("192.168.1.1",
      Lists.newArrayList("www.google.com", "www.yahoo.com"));
    return mapping;
  }

}
