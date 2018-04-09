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
import android.view.View;
import com.abishov.xi.R;

public final class EditorView extends View {

  private Layout textLayout;
  private TextPaint textPaint;
  private int[] location = new int[2];

  // private Paint highlightPaint;

  // data
  private CharSequence text;

  public EditorView(Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);

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

    textLayout = new StaticLayout(text, textPaint, 1024, Alignment.ALIGN_CENTER,
        1, 1, true);

    System.out.println("TextLayout: " + textLayout.getLineCount());
  }

  @Override
  protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);

    canvas.save();

    // getLocationOnScreen();

    // calculate X and Y coordinates - In this case we want to draw the text in the
    // center of canvas so we calculate
    // text height and number of lines to move Y coordinate to center.
    // float textHeight = getTextHeight(text, textPaint);

    // int numberOfTextLines = textLayout.getLineCount();

    getLocationInWindow(location);

    int x = location[0];
    int y = location[1];

//    float textYCoordinate = exactCenterY() - ((numberOfTextLines * textHeight) / 2);
//
//    // text will be drawn from left
//    float textXCoordinate = bounds.left;

    canvas.translate(x, y);

    // draws static layout on canvas
    textLayout.draw(canvas);

    canvas.restore();

    // textLayout.draw(canvas, new Path(), highlightPaint, 0);
  }

  /**
   * @return text height
   */
//  private float getTextHeight(CharSequence text, Paint paint) {
//    Rect rect = new Rect();
//    paint.getTextBounds(text, 0, text.length(), rect);
//    return rect.height();
//  }
}
