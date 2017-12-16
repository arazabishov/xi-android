package com.abishov.xi.core;

import android.text.TextUtils;
import com.abishov.xi.core.rpc.Method;
import com.google.gson.Gson;
import io.reactivex.Completable;
import io.reactivex.Observable;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;

public final class XiConnection {

  private final Process process;
  private final BufferedWriter writer;
  private final Gson gson;

  XiConnection(Process process, BufferedWriter writer, Gson gson) {
    this.process = process;
    this.writer = writer;
    this.gson = gson;
  }

  public Observable<String> commands() {
    return Observable.create(emitter -> {
      try (BufferedReader reader = new BufferedReader(
          new InputStreamReader(process.getInputStream()))) {
        while (isXiProcessAlive()) {
          String output = reader.readLine();
          if (!TextUtils.isEmpty(output)) {
            emitter.onNext(output);
          }
        }

        emitter.onComplete();
      } catch (IOException ioException) {
        emitter.onError(ioException);
      }
    });
  }

  private boolean isXiProcessAlive() {
    try {
      process.exitValue();
      return false;
    } catch (IllegalThreadStateException e) {
      return true;
    }
  }

  public Completable send(Method method) {
    return Completable.create((emitter) -> {
      String jsonRpc = gson.toJson(method);

      writer.write(jsonRpc);
      writer.newLine();
      writer.flush();
    });
  }
}
