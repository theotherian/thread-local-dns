package com.theotherian.dns;

import sun.net.spi.nameservice.NameService;
import sun.net.spi.nameservice.NameServiceDescriptor;

/**
 * Used to specify the DNS provider information
 * @author isimpson
 *
 */
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
