package com.hystericalporpoises.dns;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Maps;

final class OverrideNameService {

  private Map<String, String> hostsToIpAddress = Maps.newHashMap();

  OverrideNameService(List<IpToHostsMapping> mappings) {
    for (IpToHostsMapping mapping : mappings) {
      for (String host : mapping.getHosts()) {
        hostsToIpAddress.put(host, mapping.getIpAddress());
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
