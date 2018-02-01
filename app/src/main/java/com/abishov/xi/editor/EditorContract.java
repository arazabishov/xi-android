package com.abishov.xi.editor;

import com.abishov.xi.core.XiCore;
import com.abishov.xi.editor.view.SelectionChangedEvent;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;

public final class EditorContract {

  public interface View extends com.abishov.xi.common.ui.View {

    Observable<SelectionChangedEvent> selectionChangedEvents();

    Consumer<CharSequence> render();
  }

  public interface Presenter extends com.abishov.xi.common.ui.Presenter<View> {

    static Presenter create(XiCore xiCore) {
      return new EditorPresenter(xiCore);
    }
  }
}
