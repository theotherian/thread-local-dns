package com.hystericalporpoises.dns;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.commons.lang.SystemUtils;
import org.apache.log4j.Logger;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

/**
 * Reads the hosts file and parses it into a map.  Set up to be platform independent.
 *
 */
final class HostsFileResolver {

  private static final Logger LOGGER = Logger.getLogger(HostsFileResolver.class);

  private static final HostsFileResolver instance = new HostsFileResolver();

  private final Map<String, String> overrides;

  private HostsFileResolver() {
    this.overrides = ImmutableMap.copyOf(parseHostsFile());
  }

  static Map<String, String> overrides() {
    return instance.overrides;
  }

  static boolean hasOverride(String hostname) {
    return instance.overrides.containsKey(hostname);
  }

  static String getOverride(String hostname) {
    return instance.overrides.get(hostname);
  }

  private Map<String, String> parseHostsFile() {
    Map<String, String> overrides = Maps.newHashMap();
    final String hosts;
    if (SystemUtils.IS_OS_WINDOWS) {
      hosts = "c:/WINDOWS/system32/drivers/etc/hosts";
    }
    else if (SystemUtils.IS_OS_UNIX) {
      hosts = "/etc/hosts";
    }
    else {
      LOGGER.error("Unable to find /etc/hosts for " + SystemUtils.OS_NAME);
      hosts = "/etc/hosts";
    }

    FileInputStream fis = null;
    try {
      fis = new FileInputStream(hosts);
      for (;;) { // read lines
        List<String> names = parseLine(fis);
        if (names == null || names.isEmpty()) {
          break;
        }
        String val = names.get(0);
        for (int i = 1; i < names.size(); i ++) { // insert entries
          String key = names.get(i).toLowerCase();
          if (isIpv6(val)) {
            continue;
          }
          else {
            LOGGER.info("Found hosts mapping " + key + " -> " + val);
            overrides.put(key, val);
          }
        } // insert entries
      } // read lines
    }
    catch (IOException e) {
      LOGGER.warn("Unable to read " + hosts, e);
    }
    finally {
      if (fis != null) {
        try { fis.close(); } catch (IOException e) { } // ignore
      }
    }

    return overrides;
  }

  /**
   * Is this an IPv6 form of address?
   * @param token
   * @return
   */
  @VisibleForTesting
  static boolean isIpv6(String token) {
    return token.contains("::");
  }

  /**
   * Return a single non-trivial line from the hosts file
   * @param fis
   * @return null at end, else list of tokens
   * @throws IOException
   */
  @VisibleForTesting
  static List<String> parseLine(FileInputStream fis) throws IOException {
    for (;;) { // Try to find a useful line
      // Load a physical line
      String line = readLine(fis);
      if (line == null) { // end condition
        return null;
      }

      // Tokenize it, but go to next physical line if empty or comment
      StringTokenizer st = new StringTokenizer(line, " \t\r\n");
      if (!st.hasMoreTokens()) {
        continue;
      }
      String token = st.nextToken();
      if (isCommentDelimiter(token)) {
        continue;
      }

      // At this point we have a nontrivial line. Adduce first token,
      // then add remaining tokens to list by scanning.
      ArrayList<String> result = new ArrayList<String>();
      result.add(token);
      while (st.hasMoreTokens()) { // read until eol or comment
        token = st.nextToken();
        if (isCommentDelimiter(token)) {
          break;
        }
        result.add(token);
      } // read until eol or comment

      // Error to have just one token on the line
      if (result.size() == 1) {
        LOGGER.error("Single-token line in hosts file: '" + line + "'");
        continue;
      }

      // Success!
      return result;
    } // Try to find a useful line
  }

  /**
   * Does the given string represent the beginning of a comment?
   *
   * @param token
   * @return
   */
  @VisibleForTesting
  static boolean isCommentDelimiter(String token) {
    char ch = token.charAt(0);
    return ch == ';' || ch == '#';
  }

  /**
   * read a single uninterpreted line from the file
   * @param fis
   * @return
   * @throws IOException
   */
  @VisibleForTesting
  static String readLine(FileInputStream fis) throws IOException {
    // Return null if at EOF and no line
    int ch = fis.read();
    if (ch == -1) {
      return null; // EOF
    }

    // If EOL by itself, return empty line
    if (isEOL(ch)) {
      return "";
    }

    // Build line
    StringBuilder sb = new StringBuilder();
    sb.append((char)ch);
    for (;;) {
      ch = fis.read();
      if (isEOL(ch)) {
        break;
      }
      sb.append((char)ch);
    }
    return sb.toString();
  }


  /**
   * Is the given character an end of line delimiter?
   * @param ch
   * @return
   */
  @VisibleForTesting
  static boolean isEOL(int ch) {
    return ch == -1 || ch == '\r' || ch == '\n';
  }

}
