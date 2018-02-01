package com.abishov.xi;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.abishov.xi.core.XiCore;
import com.abishov.xi.editor.EditorContract;
import java.io.File;

public class MainActivity extends AppCompatActivity {

  private EditorContract.View editorView;
  private EditorContract.Presenter editorPresenter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    XiCore xiCore = XiCore.create(this);

    editorView = findViewById(R.id.editorview_main);
    editorPresenter = EditorContract.Presenter.create(xiCore);

    editorPresenter.attach(editorView);

    // createFile();
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    editorPresenter.detach();
  }

  private void createFile() {
    File file = new File(getFilesDir(), "hello.txt");
    System.out.println("File path: " + file.getAbsolutePath());
//    try {
//      System.out.println("File path: " + file.getAbsolutePath());
//      // System.out.println("File created: " + file.createNewFile());
//    } catch (IOException e) {
//      e.printStackTrace();
//    }
  }
}
