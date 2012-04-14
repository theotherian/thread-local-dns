package com.hystericalporpoises.dns;

import java.util.List;

import javax.annotation.concurrent.Immutable;

import org.pojomatic.Pojomatic;
import org.pojomatic.annotations.AutoProperty;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

@AutoProperty
@Immutable
public final class ThreadLocalDnsConfiguration {

  private int proxyPort;

  private List<IpToHostsMapping> mappings = Lists.newArrayList();

  public ThreadLocalDnsConfiguration() {}

  public ThreadLocalDnsConfiguration(int proxyPort, List<IpToHostsMapping> mappings) {
    this.proxyPort = proxyPort;
    this.mappings = mappings;
  }

  public final int getProxyPort() { return this.proxyPort; }

  public final List<IpToHostsMapping> getMappings() { return ImmutableList.copyOf(mappings); }

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
