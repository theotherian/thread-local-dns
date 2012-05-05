package com.theotherian.dns;

import java.util.List;

import javax.annotation.concurrent.Immutable;

import org.pojomatic.Pojomatic;
import org.pojomatic.annotations.AutoProperty;

import com.google.common.collect.ImmutableList;

/**
 * Represents hosts mapped to an ip address as an override
 * @author isimpson
 *
 */
@AutoProperty
@Immutable
public final class IpToHostsMapping {

  private final String ipAddress;

  private final List<String> hosts;

  public IpToHostsMapping(String ipAddress, List<String> hosts) {
    this.ipAddress = ipAddress;
    this.hosts = ImmutableList.copyOf(hosts);
  }

  public final String getIpAddress() { return this.ipAddress; }

  public final List<String> getHosts() { return this.hosts; }

  @Override public boolean equals(Object o) {
    return Pojomatic.equals(this, o);
  }

  @Override public int hashCode() {
    return Pojomatic.hashCode(this);
  }

  @Override public String toString() {
    return Pojomatic.toString(this);
  }

}
