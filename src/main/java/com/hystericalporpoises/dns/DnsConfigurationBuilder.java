package com.hystericalporpoises.dns;

import java.util.List;

import com.google.common.collect.Lists;

/**
 * Builder class for configuration instances.  Be sure to do a static import on this class
 * to use its fluent methods ({@link #hosts(String...)}, {@link #to(String)}) when building an
 * instance
 * @author isimpson
 *
 */
public final class DnsConfigurationBuilder {

  private final List<IpToHostsMapping> mappings = Lists.newArrayList();

  private DnsConfigurationBuilder() {}

  /**
   * @return a new builder instance
   */
  public static DnsConfigurationBuilder newBuilder() {
    return new DnsConfigurationBuilder();
  }

  /**
   * Maps all submitted hosts to the given ip address for the configuration instance
   * @param hosts
   * @param to
   * @return the builder instance to add subsequent mappings to or create a configuration instance
   */
  public DnsConfigurationBuilder map(Hosts hosts, IpAddress to) {
    mappings.add(new IpToHostsMapping(to.value(), hosts.values()));
    return this;
  }

  /**
   * A series of hosts to be overridden.  Hosts should be in the same format they would be in a
   * hosts file and not preceded by any sort of protocol definition.
   * @param hosts
   * @return container for host names
   */
  public static Hosts hosts(String...hosts) {
    return new Hosts(hosts);
  }

  /**
   * The string representation of the ip address to override provided hosts to
   * @param ipAddress
   * @return container for ip address
   */
  public static IpAddress to(String ipAddress) {
    return new IpAddress(ipAddress);
  }

  /**
   * @return a configuration instance based on all mapped values
   */
  public ThreadLocalDnsConfiguration build() {
    return new ThreadLocalDnsConfiguration(mappings);
  }

  private static class Hosts {

    private List<String> hosts;

    private Hosts(String...hosts) {
      this.hosts = Lists.newArrayList(hosts);
    }

    private List<String> values() {
      return hosts;
    }
  }

  private static class IpAddress {

    private String ipAddress;

    private IpAddress(String ipAddress) {
      this.ipAddress = ipAddress;
    }

    private String value() {
      return ipAddress;
    }
  }
}
