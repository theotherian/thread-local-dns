package com.hystericalporpoises.dns;

import java.util.List;

import org.pojomatic.Pojomatic;
import org.pojomatic.annotations.AutoProperty;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

@AutoProperty
public final class IpToHostsMapping {

  private final String ipAddress;

  private List<String> hosts = Lists.newArrayList();

  public IpToHostsMapping(String ipAddress) {
    this.ipAddress = ipAddress;
  }

  public void addHosts(String...hosts) {
    for (String host : hosts) {
      this.hosts.add(host);
    }
  }

  public String getIpAddress() { return this.ipAddress; }

  public List<String> getHosts() { return ImmutableList.copyOf(hosts); }

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
