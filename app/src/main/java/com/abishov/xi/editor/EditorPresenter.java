package com.abishov.xi.editor;

import com.abishov.xi.editor.EditorContract.EditorView;
import io.reactivex.disposables.CompositeDisposable;

final class EditorPresenter implements EditorContract.EditorPresenter {

  private final CompositeDisposable disposable;

  EditorPresenter() {
    disposable = new CompositeDisposable();
  }

  @Override
  public void attach(EditorView view) {

  }

  @Override
  public void detach() {
    disposable.clear();
  }
}
