package com.hystericalporpoises.dns;

import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.*;
import static org.mockito.Matchers.eq;

import org.junit.Test;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.gargoylesoftware.htmlunit.WebClient;

public class ProxyThreadTest extends BaseTest {

  @Test
  public void hitProxies() throws Exception {
    validate(LOCAL_GOOGLE_PROXY_PORT, TestServlets.PORT1, "www.google.com", "www.yahoo.com");
    validate(LOCAL_YAHOO_PROXY_PORT, TestServlets.PORT2, "www.yahoo.com", "www.google.com");
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
