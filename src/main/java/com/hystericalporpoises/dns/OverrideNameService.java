package com.hystericalporpoises.dns;

import java.util.Map;

import com.google.common.collect.Maps;

final class OverrideNameService {

  private Map<String, String> hostsToIpAddress = Maps.newHashMap();

  void addMappings(IpToHostsMapping...mappings) {
    for (IpToHostsMapping mapping : mappings) {
      for (String host : mapping.getHosts()) {
        hostsToIpAddress.put(host, mapping.getIpAddress());
      }
    }
  }

  final String getIpForHost(String host) {
    return hostsToIpAddress.get(host);
  }
}
