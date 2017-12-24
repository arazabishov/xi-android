package com.abishov.xi.common.utils;

import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;

public final class StringUtils {

  private StringUtils() {
    throw new AssertionError("No instances.");
  }

  public static String join(Collection<String> lines) {
    Objects.requireNonNull(lines);

    Iterator<String> lineIterator = lines.iterator();
    StringBuilder builder = new StringBuilder();
    while (lineIterator.hasNext()) {
      builder.append(lineIterator.next());
      if (lineIterator.hasNext()) {
        builder.append("\n");
      }
    }
    return builder.toString();
  }
}
