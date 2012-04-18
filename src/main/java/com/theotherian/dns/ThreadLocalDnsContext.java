package com.theotherian.dns;

/**
 * Used to provide code to be executed within a thread that provides overridden DNS configuration.
 * @author isimpson
 *
 */
public interface ThreadLocalDnsContext {

  public void execute();
}
