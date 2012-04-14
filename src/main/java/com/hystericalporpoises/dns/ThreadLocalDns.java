package com.hystericalporpoises.dns;

import java.io.IOException;
import java.io.InputStream;
import java.security.Security;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import com.google.common.annotations.VisibleForTesting;

/**
 * Initializes the thread local settings for your application. Make sure this method
 * is the first thing called in your application, or at least the first thing called before DNS is
 * initialized.<br>
 * If you try to initialize the thread local DNS settings after DNS has initialized, the settings
 * will not be detected.
 */
public final class ThreadLocalDns {

  private static final Logger LOGGER = Logger.getLogger(ThreadLocalDns.class);

  private static volatile boolean initialized = false;

  /**
   * Initializes from a configuration object constructed by the user
   * @param configuration
   */
  public static void initialize(DnsConfiguration dnsConfiguration) {
    initialize();
    int size = dnsConfiguration.getDnsConfigurations().size();
    ExecutorService threadPool = Executors.newFixedThreadPool(size, new ProxyThreadFactory());

    for (ThreadLocalDnsConfiguration configuration : dnsConfiguration.getDnsConfigurations()) {
      threadPool.submit(new ProxyThread(configuration));
    }
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

  @VisibleForTesting
  static void initialize() {
    if (!initialized) {
      LOGGER.info("Initializing thread local DNS settings");
      ThreadLocalDnsDescriptor descriptor = new ThreadLocalDnsDescriptor();
      String provider = descriptor.getType() + "," + descriptor.getProviderName();
      System.setProperty("sun.net.spi.nameservice.provider.1", provider);
      Security.setProperty("networkaddress.cache.ttl", "0");
      initialized = true;
    }
    else {
      throw new RuntimeException("You can't initialize DNS twice in an application");
    }
  }

  private static class ProxyThreadFactory implements ThreadFactory {

    private int counter = 0;

    @Override
    public Thread newThread(Runnable r) {
      return new Thread(r, "ProxyThread-" + counter++);
    }

  }

}
