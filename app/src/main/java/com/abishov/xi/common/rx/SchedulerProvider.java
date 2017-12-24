package com.abishov.xi.common.rx;

import io.reactivex.Scheduler;

public interface SchedulerProvider {

  static SchedulerProvider create() {
    return new SchedulerProviderImpl();
  }

  Scheduler io();

  Scheduler computation();

  Scheduler ui();
}
