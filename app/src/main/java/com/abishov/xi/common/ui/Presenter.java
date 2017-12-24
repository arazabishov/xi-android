package com.abishov.xi.common.ui;

public interface Presenter<T extends View> {

  void attach(T view);

  void detach();
}
