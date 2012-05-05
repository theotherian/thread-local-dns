package com.theotherian.dns;

import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.*;
import static org.mockito.Matchers.eq;
import static com.theotherian.dns.DnsConfigurationBuilder.*;

import org.junit.Test;
import org.littleshoot.proxy.DefaultHttpProxyServer;
import org.littleshoot.proxy.HttpProxyServer;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.gargoylesoftware.htmlunit.WebClient;
import com.theotherian.dns.ThreadLocalDns;
import com.theotherian.dns.ThreadLocalDnsConfiguration;
import com.theotherian.dns.ThreadLocalDnsContext;

public class ProxyThreadTest extends BaseTest {

  @Test
  public void hitProxies() throws Exception {
    TestServlets.createServlets();

    ThreadLocalDnsConfiguration google = newBuilder().map(hosts("www.google.com"), to("127.0.0.1")).build();
    ThreadLocalDnsConfiguration yahoo = newBuilder().map(hosts("www.yahoo.com"), to("127.0.0.1")).build();

    final int googlePort = 33335;
    final int yahooPort = 33336;
    createContext(google, googlePort);
    createContext(yahoo, yahooPort);

    validate(googlePort, TestServlets.PORT1, "www.google.com", "www.yahoo.com");
    validate(yahooPort, TestServlets.PORT2, "www.yahoo.com", "www.google.com");
  }


  private void createContext(ThreadLocalDnsConfiguration google, final int googlePort) {
    ThreadLocalDns.executeContext(google, new ThreadLocalDnsContext() {

      @Override
      public void execute() {
        final HttpProxyServer proxyServer = new DefaultHttpProxyServer(googlePort);
        proxyServer.start();
        Runtime.getRuntime().addShutdownHook(new Thread() {

          @Override
          public void run() {
            proxyServer.stop();
          }
        });
      }
    });
  }


  private void validate(int proxyPort, int localServletPort, String localhost, String remotehost) {
    DesiredCapabilities capabilities = DesiredCapabilities.htmlUnit();
    capabilities.setCapability(CapabilityType.PROXY, getProxySettings("localhost:" + proxyPort));
    UnrestrictedHtmlUnitDriver driver = new UnrestrictedHtmlUnitDriver(capabilities);

    driver.setJavascriptEnabled(false);
    driver.getWebClient().setThrowExceptionOnScriptError(false);

    driver.get("http://" + localhost + ":" + localServletPort);
    String pageSource = driver.getPageSource();
    assertEquals("ok", pageSource);

    driver.get("http://" + remotehost);
    pageSource = driver.getPageSource();
    assertThat(pageSource, not(eq("ok")));
  }

  private Proxy getProxySettings(String host) {
    Proxy proxy = new Proxy();
    proxy.setHttpProxy(host);
    proxy.setSslProxy(host);
    return proxy;
  }

  private static class UnrestrictedHtmlUnitDriver extends HtmlUnitDriver {

    public UnrestrictedHtmlUnitDriver(Capabilities capabilities) {
      super(capabilities);
    }

    @Override
    public WebClient getWebClient() {
      return super.getWebClient();
    }
  }

}
