package com.hystericalporpoises.dns;

import static org.junit.Assert.*;

import org.junit.Test;


public class JsonServiceTest extends BaseTest {

  @Test
  public void serializeDnsConfiguration() throws Exception {
    DnsConfiguration dnsConfiguration = TestObjects.makeDnsConfiguration();
    String json = JsonService.serialize(dnsConfiguration);
    assertEquals(TestObjects.DNS_CONFIG_JSON, json);
  }

  @Test
  public void deserializeDnsConfiguration() throws Exception {
    DnsConfiguration configuration = JsonService.deserialize(TestObjects.DNS_CONFIG_JSON, DnsConfiguration.class);
    assertEquals(TestObjects.makeDnsConfiguration(), configuration);
  }

}
