package com.hystericalporpoises.dns;

import java.io.File;
import java.io.InputStream;

import org.apache.commons.io.FileUtils;

public class SampleFileLoader {

  public static String loadAsUrl(String name) throws Exception {
    InputStream sample = Thread.currentThread().getContextClassLoader().getResourceAsStream(name);
    File temp = File.createTempFile("webdrivertest", name.replaceAll("/", "--"));
    FileUtils.copyInputStreamToFile(sample, temp);
    temp.deleteOnExit();
    return temp.getAbsolutePath();
  }

}
