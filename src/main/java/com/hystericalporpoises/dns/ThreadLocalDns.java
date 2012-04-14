package com.hystericalporpoises.dns;

import java.security.Security;

import org.apache.log4j.Logger;

/**
 * Initializer class for thread local DNS configuration
 *
 * @author isimpson
 */
public final class ThreadLocalDns {

  private static final Logger LOGGER = Logger.getLogger(ThreadLocalDns.class);

  /**
   * Initializes the thread local settings for your application. Make sure this method
   * is the first thing called in your application, or at least the first thing called before DNS is
   * initialized.<br>
   * If you try to initialize the thread local DNS settings after DNS has initialized, the settings
   * will not be detected.
   */
  public static void initialize() {
    LOGGER.info("Initializing thread local DNS settings");
    System.setProperty("sun.net.spi.nameservice.provider.1", "dns,thread-local-dns");
    Security.setProperty("networkaddress.cache.ttl", "0");
  }

}
