package com.theotherian.dns;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.apache.log4j.Logger;


/**
 * Checks settings prior to using overridden DNS.  If the ips don't resolve to the overridden
 * values, then something wasn't initialized correctly in the application.
 * @author isimpson
 *
 */
final class DnsValidator {

  private DnsValidator() {}

  private static final Logger LOGGER = Logger.getLogger(DnsValidator.class);

  /**
   * Validate that the host resolves to the expected ip address
   * @param host
   * @param expectedIpAddress
   */
  static void checkOverride(String host, String expectedIpAddress) {
    try {
      LOGGER.debug("Validating that " + host + " resolves to " + expectedIpAddress
        + " for thread " + Thread.currentThread().getName());
      String ipAddress = InetAddress.getByName(host).getHostAddress();
      if (!expectedIpAddress.equals(ipAddress)) {
        throw new RuntimeException("Overridden DNS has not initialized correctly: tried to resolve "
            + host + " to " + expectedIpAddress + ", but was " + ipAddress);
      }
    }
    catch (UnknownHostException e) {
      LOGGER.fatal(host + " is an unknown host", e);
      throw new RuntimeException(e);
    }
  }

}
