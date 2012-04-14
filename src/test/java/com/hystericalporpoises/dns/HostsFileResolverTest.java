package com.hystericalporpoises.dns;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

import java.io.FileInputStream;
import java.util.Arrays;

import org.junit.BeforeClass;
import org.junit.Test;

public class HostsFileResolverTest extends BaseTest {

  private static String emptyHosts;

  private static String oneHost;

  private static String multipleHosts;

  private static String malformedHost;

  private static String commentAfterHost;

  @BeforeClass public static void setUpBefore() throws Exception {
    emptyHosts = SampleFileLoader.loadAsUrl("empty-hosts");
    oneHost = SampleFileLoader.loadAsUrl("sample-host");
    multipleHosts = SampleFileLoader.loadAsUrl("sample-hosts");
    malformedHost = SampleFileLoader.loadAsUrl("malformed-host");
    commentAfterHost = SampleFileLoader.loadAsUrl("comment-after-host");
  }


  @Test
  public void isIpv6() throws Exception {
    assertTrue(HostsFileResolver.isIpv6("aa::aa"));
    assertFalse(HostsFileResolver.isIpv6("aa"));
  }

  @Test
  public void parseLine() throws Exception {
    FileInputStream fis = mock(FileInputStream.class);

    // eof
    when(fis.read()).thenReturn(-1);
    assertNull(HostsFileResolver.parseLine(fis));
    reset(fis);

    // empty file
    fis = new FileInputStream(emptyHosts);
    assertNull(HostsFileResolver.parseLine(fis));
    fis.close();

    // one host
    fis = new FileInputStream(oneHost);
    assertEquals(Arrays.asList("10.10.10.10", "www.tendotten.com"), HostsFileResolver.parseLine(fis));
    fis.close();

    // multiple hosts
    fis = new FileInputStream(multipleHosts);
    assertEquals(Arrays.asList("10.10.10.10", "www.tendotten.com"), HostsFileResolver.parseLine(fis));
    fis.close();

    // malformed host
    fis = new FileInputStream(malformedHost);
    assertNull(HostsFileResolver.parseLine(fis));
    fis.close();

    // comment after host
    fis = new FileInputStream(commentAfterHost);
    assertEquals(Arrays.asList("10.10.10.10", "www.tendotten.com"), HostsFileResolver.parseLine(fis));
    fis.close();
  }

  @Test
  public void isEol() throws Exception {
    assertTrue(HostsFileResolver.isEOL(-1));
    assertTrue(HostsFileResolver.isEOL('\n'));
    assertTrue(HostsFileResolver.isEOL('\r'));
    assertFalse(HostsFileResolver.isEOL('a'));
  }

  @Test
  public void readLine() throws Exception {
    FileInputStream fis = mock(FileInputStream.class);

    // eof
    when(fis.read()).thenReturn(-1);
    assertNull(HostsFileResolver.readLine(fis));
    reset(fis);

    //eol
    when(fis.read()).thenReturn((int) '\n');
    assertTrue(HostsFileResolver.readLine(fis).isEmpty());
    reset(fis);

    // valid
    when(fis.read()).thenReturn((int) 'a', (int) 'a', (int) '\n');
    assertEquals("aa", HostsFileResolver.readLine(fis));
    reset(fis);

  }

  @Test
  public void isCommentDelimeter() throws Exception {
    assertTrue(HostsFileResolver.isCommentDelimiter("#"));
    assertTrue(HostsFileResolver.isCommentDelimiter(";"));
    assertFalse(HostsFileResolver.isCommentDelimiter("a"));
  }

}
