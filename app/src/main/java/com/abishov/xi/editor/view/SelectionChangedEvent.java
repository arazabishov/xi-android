package com.abishov.xi.editor.view;

public final class SelectionChangedEvent {
  private final int start;
  private final int end;

  public SelectionChangedEvent(int start, int end) {
    this.start = start;
    this.end = end;
  }

  public int start() {
    return start;
  }

  public int end() {
    return end;
  }

  @Override
  public String toString() {
    return "SelectionChangedEvent{" +
        "start=" + start +
        ", end=" + end +
        '}';
  }
}
