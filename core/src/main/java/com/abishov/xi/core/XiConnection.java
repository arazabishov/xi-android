package com.abishov.xi.core;

import android.text.TextUtils;
import com.abishov.xi.core.rpc.Call;
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

  public Observable<Call> calls() {
    return Observable.create(emitter -> {
      try (BufferedReader reader = new BufferedReader(
          new InputStreamReader(process.getInputStream()))) {
        while (isXiProcessAlive()) {
          String output = reader.readLine();
          if (!TextUtils.isEmpty(output)) {
            emitter.onNext(gson.fromJson(output, Call.class));
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

  public Completable send(Call call) {
    return Completable.create((emitter) -> {
      String jsonRpc = gson.toJson(call);

      writer.write(jsonRpc);
      writer.newLine();
      writer.flush();
    });
  }
}
