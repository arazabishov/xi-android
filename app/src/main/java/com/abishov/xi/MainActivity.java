package com.abishov.xi;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.abishov.xi.core.XiConnection;
import com.abishov.xi.core.XiCore;
import com.abishov.xi.core.rpc.Method;
import com.abishov.xi.core.rpc.Parameters;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    XiCore xiCore = XiCore.create(this);

    xiCore.connect()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(consume(), Throwable::printStackTrace);
  }

  private Consumer<XiConnection> consume() {
    return connection -> {
      connection.commands()
          .subscribeOn(Schedulers.io())
          .observeOn(AndroidSchedulers.mainThread())
          .subscribe(command -> System.out.println("Command: " + command),
              Throwable::printStackTrace);

      connection.send(Method.newView(0, Parameters.create("hello.txt")))
          .subscribeOn(Schedulers.io())
          .observeOn(AndroidSchedulers.mainThread())
          .subscribe(() -> System.out.println("Sent an rpc"));
    };
  }
}
