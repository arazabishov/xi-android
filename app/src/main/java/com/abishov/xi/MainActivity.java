package com.abishov.xi;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;
import com.abishov.xi.editor.view.EditorView;

public class MainActivity extends AppCompatActivity {

  private TextView editorView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    editorView = findViewById(R.id.editorview);
    editorView.setHorizontallyScrolling(true);
    editorView.setMovementMethod(new ScrollingMovementMethod());
    editorView.setCursorVisible(true);
    editorView.setText("This is an example of text rendering on android. This is an example of "
        + "text rendering on android. This is an example of text rendering on android. "
        + "text rendering on android. This is an example of text rendering on android. "
        + "text rendering on android. This is an example of text rendering on android. "
        + "text rendering on android. This is an example of text rendering on android. "
        + "text rendering on android. This is an example of text rendering on android. "
        + "text rendering on android. This is an example of text rendering on android. \n"
        + "text rendering on android. This is an example of text rendering on android. "
        + "text rendering on android. This is an example of text rendering on android. "
        + "text rendering on android. This is an example of text rendering on android. "
        + "text rendering on android. This is an example of text rendering on android. ");
    // editorView.setText("This is an example of text rendering on android.");
  }
}
