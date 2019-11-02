package com.abishov.xi;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.abishov.xi.core.XiConnection;
import com.abishov.xi.core.XiCore;
import com.abishov.xi.core.rpc.Call;
import com.abishov.xi.core.rpc.Parameters;
import com.abishov.xi.editor.view.EditorView;
import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import java.io.File;

public class MainActivity extends AppCompatActivity {

  private EditorView editorView;
  private CompositeDisposable disposable = new CompositeDisposable();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    Single<XiConnection> xiConnection =
        XiCore.create(this)
            .connect()
            .cache();

    disposable.add(
        xiConnection.toObservable()
            .subscribeOn(Schedulers.io())
            .switchMap(XiConnection::calls)
            .subscribe(event -> System.out.println("XiEvent: " + event), error -> {
              System.out.println("Ooops");
              error.printStackTrace();
            })
    );
//
    openFile(xiConnection, "resume.md");

    editorView = findViewById(R.id.editorview);
//    editorView.setHorizontallyScrolling(true);
//    editorView.setMovementMethod(new ScrollingMovementMethod());
//    editorView.setCursorVisible(true);
//    editorView.setText("This is an example of text rendering on android. This is an example of "
//        + "text rendering on android. This is an example of text rendering on android. "
//        + "text rendering on android. This is an example of text rendering on android. "
//        + "text rendering on android. This is an example of text rendering on android. "
//        + "text rendering on android. This is an example of text rendering on android. "
//        + "text rendering on android. This is an example of text rendering on android. "
//        + "text rendering on android. This is an example of text rendering on android. \n"
//        + "text rendering on android. This is an example of text rendering on android. "
//        + "text rendering on android. This is an example of text rendering on android. "
//        + "text rendering on android. This is an example of text rendering on android. "
//        + "text rendering on android. This is an example of text rendering on android. ");
  }

  private void openFile(Single<XiConnection> xiConnection, String fileName) {
    File file = new File(getFilesDir(), fileName);
    Call call = Call.newView(0, Parameters.create(file.getAbsolutePath()));

    System.out.println("File path: " + file.getAbsolutePath());

    disposable.add(
        xiConnection.toObservable()
            .subscribeOn(Schedulers.io())
            .flatMapCompletable(connection -> connection.send(call))
            .subscribe(() -> {
              System.out.println("Send is complete.");
            }, error -> {
              System.out.println("Ooops");
              error.printStackTrace();
            })

        /*

         */
    );
  }
}