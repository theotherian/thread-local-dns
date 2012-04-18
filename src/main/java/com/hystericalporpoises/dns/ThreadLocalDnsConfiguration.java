package com.hystericalporpoises.dns;

import java.util.List;

import javax.annotation.concurrent.Immutable;

import org.pojomatic.Pojomatic;
import org.pojomatic.annotations.AutoProperty;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

/**
 * Represents all of the host to ip address mappings for a given thread local scope
 * @author isimpson
 *
 */
@AutoProperty
@Immutable
public final class ThreadLocalDnsConfiguration {

  private List<IpToHostsMapping> mappings = Lists.newArrayList();

  public ThreadLocalDnsConfiguration(List<IpToHostsMapping> mappings) {
    this.mappings = mappings;
  }

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
