package com.theotherian.dns;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.apache.log4j.Logger;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

final class CacheFactory {

  private static final Logger LOGGER = Logger.getLogger(CacheLoader.class);

  static LoadingCache<String, InetAddress[]> newCache() {
    return CacheBuilder.newBuilder().build(new CacheLoader<String, InetAddress[]>() {

      @Override
      public InetAddress[] load(String key) throws Exception {
        LOGGER.debug("Looking up " + key);
        if (OverrideNameServiceManager.hasIpForHost(key)) {
          String ipAddress = OverrideNameServiceManager.getIpForHost(key);
          LOGGER.debug("Found thread local override for " + key + " of " + ipAddress);
          return convertToInetAddress(ipAddress);
        }
        else if (HostsFileResolver.hasOverride(key)) {
          String ipAddress = HostsFileResolver.getOverride(key);
          LOGGER.debug("Found hosts entry for " + key + " of " + ipAddress);
          return convertToInetAddress(ipAddress);
        }
        else {
          LOGGER.debug("No override found for " + key + "");
          return new InetAddress[]{};
        }
      }

      private InetAddress[] convertToInetAddress(String ipAddress) throws UnknownHostException {
        byte[] ipAsBytes = TextToNumeric.convert(ipAddress);
        InetAddress[] address = new InetAddress[1];
        address[0] = InetAddress.getByAddress(ipAsBytes);
        return address;
      }

    });
  }

}
