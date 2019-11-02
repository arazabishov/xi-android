package com.abishov.xi.editor.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.Layout;
import android.text.Layout.Alignment;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.widget.HorizontalScrollView;
import com.abishov.xi.R;

public final class EditorView extends HorizontalScrollView {

  private Layout textLayout;
  private TextPaint textPaint;
  private int[] location = new int[2];

  private CharSequence text;

  public EditorView(Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);

    /*
     * I don't understand what exactly fronted should send to the backend, and what to expect from
     * backend. The simplest way would be to start from opening the file, and checking out what
     * backend sends you. Then render the text using your custom view (following unidirectional
     * data flow architecture approach).
     * */
    text = "This is an example of text rendering on android. This is an example of "
        + "text rendering on android. This is an example of text rendering on android. "
        + "text rendering on android. This is an example of text rendering on android. "
        + "text rendering on android. This is an example of text rendering on android. "
        + "text rendering on android. This is an example of text rendering on android. "
        + "text rendering on android. This is an example of text rendering on android. "
        + "text rendering on android. This is an example of text rendering on android. "
        + "text rendering on android. This is an example of text rendering on android. "
        + "text rendering on android. This is an example of text rendering on android. "
        + "text rendering on android. This is an example of text rendering on android. "
        + "text rendering on android. This is an example of text rendering on android. ";

    textPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
    textPaint.setColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));
    textPaint.setTextSize(64);

    textLayout = new StaticLayout(text, textPaint, Integer.MAX_VALUE, Alignment.ALIGN_CENTER,
        1, 1, true);
  }

  @Override
  protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);

    canvas.save();

    getLocationInWindow(location);

    int x = location[0];
    int y = location[1];

    canvas.translate(x, y);

    textLayout.draw(canvas);

    canvas.restore();
  }
}
