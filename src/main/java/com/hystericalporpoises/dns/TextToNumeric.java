package com.hystericalporpoises.dns;

final class TextToNumeric {

  private TextToNumeric() {}

  static byte[] convert(String src) {
    if (src == null || src.length() == 0)
      return null;

    int octets;
    char ch;
    byte[] dst = new byte[4];
    char[] srcb = src.toCharArray();
    boolean saw_digit = false;

    octets = 0;
    int i = 0;
    int cur = 0;
    while (i < srcb.length) {
      ch = srcb[i++];
      if (Character.isDigit(ch)) {
        int sum = dst[cur] * 10 + (Character.digit(ch, 10) & 0xff);

        if (sum > 255)
          return null;

        dst[cur] = (byte) (sum & 0xff);
        if (!saw_digit) {
          if (++octets > 4)
            return null;
          saw_digit = true;
        }
      }
      else if (ch == '.' && saw_digit) {
        if (octets == 4)
          return null;
        cur++;
        dst[cur] = 0;
        saw_digit = false;
      }
      else
        return null;
    }
    if (octets < 4)
      return null;
    return dst;
  }


}
