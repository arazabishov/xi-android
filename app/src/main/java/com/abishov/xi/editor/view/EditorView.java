package com.abishov.xi.editor.view;

import android.content.Context;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;
import com.abishov.xi.editor.EditorContract.View;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;

public final class EditorView extends AppCompatEditText implements View {

  private OnSelectionChangedListener onSelectionChangedListener;

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
  public Observable<SelectionChangedEvent> selectionChangedEvents() {
    return new SelectionChangedEventObservable(this);
  }

  @Override
  public Consumer<CharSequence> render() {
    return this::setText;
  }

  @Override
  protected void onSelectionChanged(int selStart, int selEnd) {
    super.onSelectionChanged(selStart, selEnd);

    if (onSelectionChangedListener != null) {
      onSelectionChangedListener.onSelectionChanged(selStart, selEnd);
    }
  }

  public void setOnSelectionChangedListener(
      OnSelectionChangedListener onSelectionChangedListener) {
    this.onSelectionChangedListener = onSelectionChangedListener;
  }

  public interface OnSelectionChangedListener {

    void onSelectionChanged(int start, int end);
  }
}
