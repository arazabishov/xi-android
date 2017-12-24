package com.abishov.xi.editor;

import android.content.Context;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;

public final class EditorView extends AppCompatEditText implements EditorContract.EditorView {

  public EditorView(Context context) {
    super(context);
  }

  public EditorView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public EditorView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  @Override
  public Observable<CharSequence> textChanges() {
    return Observable.empty();
  }

  @Override
  public Consumer<CharSequence> render() {
    return this::setText;
  }
}
