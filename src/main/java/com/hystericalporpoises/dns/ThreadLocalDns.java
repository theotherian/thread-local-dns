package com.hystericalporpoises.dns;

import java.io.IOException;
import java.io.InputStream;
import java.security.Security;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

/**
 * Initializes the thread local settings for your application. Make sure this method
 * is the first thing called in your application, or at least the first thing called before DNS is
 * initialized.<br>
 * If you try to initialize the thread local DNS settings after DNS has initialized, the settings
 * will not be detected.
 */
public final class ThreadLocalDns {

  private static final Logger LOGGER = Logger.getLogger(ThreadLocalDns.class);


  /**
   * Initializes from a configuration object constructed by the user
   * @param configuration
   */
  public static void initialize(DnsConfiguration configuration) {
    initialize();
  }


  /**
   * Initializes from a json string representing a {@link DnsConfiguration} object
   * @param dnsConfigurationJson
   */
  public static void initializeFromJson(String dnsConfigurationJson) {
    DnsConfiguration configuration = JsonService.deserialize(dnsConfigurationJson,
      DnsConfiguration.class);
    initialize(configuration);
  }


  /**
   * Initializes from a stream producing a json string representing a {@link DnsConfiguration} object
   * @param dnsConfigurationJsonStream
   * @throws IOException
   */
  public static void initializeFromJson(InputStream dnsConfigurationJsonStream) throws IOException {
    String json = IOUtils.toString(dnsConfigurationJsonStream);
    initializeFromJson(json);
  }

  private static void initialize() {
    LOGGER.info("Initializing thread local DNS settings");
    System.setProperty("sun.net.spi.nameservice.provider.1", "dns,thread-local-dns");
    Security.setProperty("networkaddress.cache.ttl", "0");
  }

}
