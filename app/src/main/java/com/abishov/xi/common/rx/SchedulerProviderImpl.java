package com.abishov.xi.common.rx;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

final class SchedulerProviderImpl implements SchedulerProvider {

  @Override
  public final Scheduler io() {
    return Schedulers.io();
  }

  @Override
  public Scheduler computation() {
    return Schedulers.computation();
  }

  @Override
  public Scheduler ui() {
    return AndroidSchedulers.mainThread();
  }
}
