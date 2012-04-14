package com.hystericalporpoises.dns;

import org.apache.log4j.Logger;
import org.littleshoot.proxy.DefaultHttpProxyServer;
import org.littleshoot.proxy.HttpProxyServer;


class ProxyThread implements Runnable {

  private int portNumber;

  private OverrideNameService nameService;

  private static final Logger LOGGER = Logger.getLogger(ProxyThread.class);

  ProxyThread(ThreadLocalDnsConfiguration configuration) {
    this.portNumber = configuration.getProxyPort();
    this.nameService = new OverrideNameService(configuration.getMappings());
  }

  @Override
  public void run() {
    OverrideNameServiceManager.initializeForThread(nameService);
    nameService.validate();

    LOGGER.info("Starting proxy on port " + portNumber);
    final HttpProxyServer proxyServer = new DefaultHttpProxyServer(portNumber);
    proxyServer.start();
    Runtime.getRuntime().addShutdownHook(new Thread() {

      @Override
      public void run() {
        proxyServer.stop();
      }
    });
  }

}
