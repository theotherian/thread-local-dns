package com.hystericalporpoises.dns;

import static org.junit.Assert.*;

import org.junit.Test;

public class JsonServiceTest {

  @Test
  public void serializeIpToHostsMapping() {
    IpToHostsMapping mapping = makeMapping();
    String json = JsonService.serialize(mapping);
    assertEquals("{\"ipAddress\":\"192.168.1.1\",\"hosts\":[\"www.google.com\",\"www.yahoo.com\"]}",
      json);
  }

  @Test
  public void deserializeIpToHostsMapping() throws Exception {
    IpToHostsMapping mapping = JsonService
        .deserialize("{\"ipAddress\":\"192.168.1.1\",\"hosts\":[\"www.google.com\",\"www.yahoo.com\"]}",
          IpToHostsMapping.class);
    IpToHostsMapping expected = makeMapping();
    assertEquals(expected, mapping);
  }

  private IpToHostsMapping makeMapping() {
    IpToHostsMapping mapping = new IpToHostsMapping("192.168.1.1");
    mapping.addHosts("www.google.com", "www.yahoo.com");
    return mapping;
  }

}
