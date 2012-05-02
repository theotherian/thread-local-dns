package com.theotherian.dns;

import java.util.List;
import java.util.Map;

import javax.annotation.concurrent.Immutable;

import com.google.common.collect.Maps;

/**
 * Translates DNS overrides into a map for easier lookup
 * @author isimpson
 *
 */
@Immutable
final class OverrideNameService {

  private final Map<String, String> hostsToIpAddress = Maps.newHashMap();

  OverrideNameService(List<IpToHostsMapping> mappings) {
    for (IpToHostsMapping mapping : mappings) {
      for (String host : mapping.getHosts()) {
        // lower case all keys - dns should be case insensitive
        hostsToIpAddress.put(host.toLowerCase(), mapping.getIpAddress());
      }
    }
  }

  final void validate() {
    for (String host : hostsToIpAddress.keySet()) {
      DnsValidator.checkOverride(host, hostsToIpAddress.get(host));
    }
  }

  final String getIpForHost(String host) {
    return hostsToIpAddress.get(host);
  }

  final boolean hasIpForHost(String host) {
    return hostsToIpAddress.containsKey(host);
  }
}
