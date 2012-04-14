package com.hystericalporpoises.dns;

import java.util.List;

import javax.annotation.concurrent.Immutable;

import org.pojomatic.Pojomatic;
import org.pojomatic.annotations.AutoProperty;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

@AutoProperty
@Immutable
public final class DnsConfiguration {

  private List<ThreadLocalDnsConfiguration> dnsConfigurations = Lists.newArrayList();

  public DnsConfiguration() {}

  public DnsConfiguration(List<ThreadLocalDnsConfiguration> dnsConfigurations) {
    this.dnsConfigurations = dnsConfigurations;
  }

  public final List<ThreadLocalDnsConfiguration> getDnsConfigurations() { return ImmutableList.copyOf(dnsConfigurations); }

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
