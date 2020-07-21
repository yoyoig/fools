package com.yoyoig.fools.utils;

import java.io.Closeable;
import java.io.IOException;

/**
 * <p>
 * IO攻击
 * </p>
 *
 * @author mingke.yan@hand-china.com 2020-05-23 4:48 下午
 */
public class CommonIOUtils {

  private CommonIOUtils() {
  }

  public static void close(Closeable close) {
    if (close != null) {
      try {
        close.close();
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    }
  }
}
