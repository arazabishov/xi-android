package com.abishov.xi.editor;

import com.abishov.xi.core.XiConnection;
import com.abishov.xi.core.XiCore;
import com.abishov.xi.core.rpc.Call;
import com.abishov.xi.core.rpc.Line;
import com.abishov.xi.core.rpc.Parameters;
import com.abishov.xi.editor.EditorContract.Presenter;
import com.abishov.xi.editor.EditorContract.View;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

final class EditorPresenter implements Presenter {

  private final XiCore xiCore;
  private final CompositeDisposable disposable;

  EditorPresenter(XiCore xiCore) {
    this.xiCore = xiCore;
    this.disposable = new CompositeDisposable();
  }

  @Override
  public void attach(EditorContract.View view) {
    Single<XiConnection> xiConnection = xiCore.connect()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .cache();

    disposable.add(view.selectionChangedEvents()
        .subscribe(event -> System.out.println("EditorActionEvent: " + event),
            Throwable::printStackTrace));

    disposable.addAll(
        xiConnection.subscribe(openTab(), Throwable::printStackTrace),
        xiConnection.subscribe(consumeCommands(view), Throwable::printStackTrace));
  }

  @Override
  public void detach() {
    disposable.clear();
  }

  private Consumer<XiConnection> consumeCommands(View editorView) {
    return connection -> connection.calls()
        .doOnNext(command -> System.out.println("Command: " + command))
        .filter(call -> "update".equals(call.method()))
        .map(this::joinLines)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(editorView.render(), Throwable::printStackTrace);
  }

  private CharSequence joinLines(Call call) {
    StringBuilder builder = new StringBuilder();

    for (Line line : call.parameters().update().operations().get(0).lines()) {
      builder.append(line.text());
    }

    return builder.toString();
  }

  private Consumer<XiConnection> openTab() {
    return connection -> connection
        .send(Call.newView(0, Parameters.create("/data/user/0/com.abishov.xi/files/hello.txt")))
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(() -> System.out.println("Sent an rpc"));
  }
}
