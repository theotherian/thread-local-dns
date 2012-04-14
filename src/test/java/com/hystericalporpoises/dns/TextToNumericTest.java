package com.hystericalporpoises.dns;

import static org.junit.Assert.*;

import org.junit.Test;

public class TextToNumericTest extends BaseTest {

  @Test
  public void convert() throws Exception {
    assertNull(TextToNumeric.convert(null));
    assertNull(TextToNumeric.convert(""));
    assertNull(TextToNumeric.convert(String.valueOf((char) 256)));
    assertNull(TextToNumeric.convert("10.10.10.10.10"));
    assertNull(TextToNumeric.convert("10.10.10"));

    byte[] actual = TextToNumeric.convert("10.10.10.10");
    byte[] expected = new byte[]{10,10,10,10};
    for(int i = 0; i < expected.length; i++) {
      assertEquals(expected[i], actual[i]);
    }
  }

}
