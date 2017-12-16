package com.abishov.xi;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.abishov.xi.core.XiCore;

public class MainActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    XiCore xiCore = XiCore.create(this);
  }
}
