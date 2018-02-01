package com.abishov.xi.editor.view;

import android.os.Looper;
import com.abishov.xi.editor.view.EditorView.OnSelectionChangedListener;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.MainThreadDisposable;

final class SelectionChangedEventObservable extends Observable<SelectionChangedEvent> {

  private final EditorView editorView;

  SelectionChangedEventObservable(EditorView editorView) {
    this.editorView = editorView;
  }

  @Override
  protected void subscribeActual(Observer<? super SelectionChangedEvent> observer) {
    if (checkMainThread(observer)) {
      Listener listener = new Listener(editorView, observer);
      observer.onSubscribe(listener);
      editorView.setOnSelectionChangedListener(listener);
    }
  }

  private static boolean checkMainThread(Observer<?> observer) {
    if (Looper.myLooper() != Looper.getMainLooper()) {
      observer.onError(new IllegalStateException(
          "Expected to be called on the main thread but was " + Thread.currentThread().getName()));
      return false;
    }

    return true;
  }

  private final class Listener extends MainThreadDisposable implements OnSelectionChangedListener {

    private final EditorView view;
    private final Observer<? super SelectionChangedEvent> observer;

    Listener(EditorView view, Observer<? super SelectionChangedEvent> observer) {
      this.view = view;
      this.observer = observer;
    }

    @Override
    public void onSelectionChanged(int start, int end) {
      SelectionChangedEvent selectionChangedEvent = new SelectionChangedEvent(start, end);

      try {
        if (!isDisposed()) {
          observer.onNext(selectionChangedEvent);
        }
      } catch (Exception exception) {
        observer.onError(exception);
        dispose();
      }
    }

    @Override
    protected void onDispose() {
      view.setOnSelectionChangedListener(null);
    }
  }
}
