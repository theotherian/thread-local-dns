package com.hystericalporpoises.dns;

import static org.junit.Assert.*;

import org.junit.Test;

import com.google.common.collect.Lists;

public class JsonServiceTest {

  private static final String IP_TO_HOSTS_MAPPING_JSON =
      "{\"ipAddress\":\"192.168.1.1\",\"hosts\":[\"www.google.com\",\"www.yahoo.com\"]}";

  private static final String THREAD_LOCAL_DNS_CONFIG_JSON =
    "{\"proxyPort\":12345,\"mappings\":[" + IP_TO_HOSTS_MAPPING_JSON + "," +
        IP_TO_HOSTS_MAPPING_JSON + "]}";

  private static final String DNS_CONFIG_JSON = "{\"dnsConfigurations\":[" +
    THREAD_LOCAL_DNS_CONFIG_JSON + "," + THREAD_LOCAL_DNS_CONFIG_JSON + "]}";

  @Test
  public void serializeDnsConfiguration() throws Exception {
    DnsConfiguration dnsConfiguration = makeDnsConfiguration();
    String json = JsonService.serialize(dnsConfiguration);
    assertEquals(DNS_CONFIG_JSON, json);
  }

  @Test
  public void deserializeDnsConfiguration() throws Exception {
    DnsConfiguration configuration = JsonService.deserialize(DNS_CONFIG_JSON, DnsConfiguration.class);
    assertEquals(makeDnsConfiguration(), configuration);
  }

  private DnsConfiguration makeDnsConfiguration() {
    DnsConfiguration configuration = new DnsConfiguration(
      Lists.newArrayList(makeThreadLocalDnsConfiguration(), makeThreadLocalDnsConfiguration())
    );
    return configuration;
  }

  private ThreadLocalDnsConfiguration makeThreadLocalDnsConfiguration() {
    ThreadLocalDnsConfiguration configuration = new ThreadLocalDnsConfiguration(12345,
      Lists.newArrayList(makeMapping(), makeMapping()));
    return configuration;
  }

  private IpToHostsMapping makeMapping() {
    IpToHostsMapping mapping = new IpToHostsMapping("192.168.1.1",
      Lists.newArrayList("www.google.com", "www.yahoo.com"));
    return mapping;
  }

}
