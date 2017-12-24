package com.abishov.xi.editor;

import com.abishov.xi.common.ui.Presenter;
import com.abishov.xi.common.ui.View;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;

final class EditorContract {

  interface EditorView extends View {

    Observable<CharSequence> textChanges();

    Consumer<CharSequence> render();
  }

  interface EditorPresenter extends Presenter<EditorView> {

  }
}
