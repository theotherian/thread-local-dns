package com.hystericalporpoises.dns;

import sun.net.spi.nameservice.NameService;
import sun.net.spi.nameservice.NameServiceDescriptor;

@SuppressWarnings("restriction")
public class ThreadLocalDnsDescriptor implements NameServiceDescriptor {

  public NameService createNameService() throws Exception {
    return new ThreadLocalNameService();
  }

  public String getProviderName() {
    return "threadlocal";
  }

  public String getType() {
    return "dns";
  }

}
