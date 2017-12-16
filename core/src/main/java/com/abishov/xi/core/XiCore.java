package com.abishov.xi.core;

import android.content.Context;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.text.TextUtils;
import android.util.Log;
import com.abishov.xi.core.rpc.Method;
import com.abishov.xi.core.rpc.Parameters;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public final class XiCore {

  private static final String TAG = XiCore.class.getSimpleName();

  private static final String ARM64 = "arm64";
  private static final String ARMEABI = "armeabi";
  private static final String X86 = "x86";
  private static final String XI_CORE = "xi-core";

  private static final List<String> SUPPORTED_ARCHITECTURES = Arrays.asList(
      ARM64, ARMEABI, X86
  );

  private final Context context;
  private final Process process;
  private final Gson gson;

  public static XiCore create(Context context) {
    Objects.requireNonNull(context);
    return new XiCore(context);
  }

  private XiCore(Context context) {
    this.context = context;

    String executableFilePath = copyExecutableToInternalStorage();
    try {
      gson = new GsonBuilder()
          .registerTypeAdapterFactory(XiAdapterFactory.create())
          .create();

      process = new ProcessBuilder()
          .command(executableFilePath)
          .start();

      Log.d(TAG, "Started xi-core process, hooray!");

      Method newView = Method.newView(0, Parameters.create("hello.txt"));
      String newViewJson = gson.toJson(newView);

      System.out.println("new_view: " + newViewJson);

      BufferedWriter bufferedWriter = new BufferedWriter(
          new OutputStreamWriter(process.getOutputStream()));

      bufferedWriter.write(newViewJson);
      bufferedWriter.newLine();
      bufferedWriter.flush();

      BufferedReader bufferedReader = new BufferedReader(
          new InputStreamReader(process.getInputStream()));

      while (true) {
        String output = bufferedReader.readLine();
        if (!TextUtils.isEmpty(output)) {
          System.out.println("XiOutput: " + output);
        }
      }
    } catch (IOException ioException) {
      throw new IllegalStateException("Failed to run xi-core executable: "
          + executableFilePath, ioException);
    }
  }

  private String copyExecutableToInternalStorage() {
    String executableFilePath = getSupportedArchitecture() + "/" + XI_CORE;

    InputStream executableInputStream = null;
    try {
      executableInputStream = context.getAssets().open(executableFilePath);
      return writeExecutableFile(executableInputStream);
    } catch (IOException ioException) {
      String exceptionMessage = String.format(Locale.US, "Missing %s executable "
          + "for %s architecture.", XI_CORE, executableFilePath);
      throw new IllegalStateException(exceptionMessage);
    } finally {
      if (executableInputStream != null) {
        try {
          executableInputStream.close();
        } catch (IOException ioException) {
          ioException.printStackTrace();
        }
      }
    }
  }

  private String writeExecutableFile(InputStream executableInputStream) {
    FileOutputStream executableOutputStream = null;
    try {
      File binDirectory = new File(context.getApplicationInfo().dataDir, XI_CORE);
      if (!binDirectory.exists()) {
        if (!binDirectory.mkdir()) {
          Log.d(TAG, String.format(Locale.US, "Failed to create '%s' directory.", XI_CORE));
        }
      }

      File executableFile = new File(binDirectory, XI_CORE);
      if (executableFile.exists()) {
        if (!executableFile.delete()) {
          Log.d(TAG, String.format(Locale.US, "Failed to delete '%s' file.", XI_CORE));
        }
      }

      if (!executableFile.createNewFile()) {
        Log.d(TAG, String.format(Locale.US, "Failed to create '%s' file.", XI_CORE));
      }

      if (!executableFile.setExecutable(true)) {
        Log.d(TAG, String.format(Locale.US, "Failed to set executable "
            + "bits on '%s' file.", XI_CORE));
      }

      byte[] buffer = new byte[1024];
      int bytesRead = executableInputStream.read(buffer);
      executableOutputStream = new FileOutputStream(executableFile);

      while (bytesRead != -1) {
        executableOutputStream.write(buffer);
        bytesRead = executableInputStream.read(buffer);
      }

      return executableFile.getAbsolutePath();
    } catch (IOException ioException) {
      throw new IllegalStateException(ioException);
    } finally {
      if (executableOutputStream != null) {
        try {
          executableOutputStream.close();
        } catch (IOException ioException) {
          ioException.printStackTrace();
        }
      }
    }
  }

  private static String getSupportedArchitecture() {
    List<String> platformArchs = getArchitectures();
    for (String architecture : platformArchs) {
      if (SUPPORTED_ARCHITECTURES.contains(architecture)) {
        return architecture;
      }
    }

    throw new IllegalArgumentException("Unsupported CPU architecture. Next "
        + "architectures are supported: " + SUPPORTED_ARCHITECTURES);
  }

  private static List<String> getArchitectures() {
    if (VERSION.SDK_INT > VERSION_CODES.LOLLIPOP) {
      return Arrays.asList(Build.SUPPORTED_ABIS);
    } else {
      return Arrays.asList(Build.CPU_ABI, Build.CPU_ABI2);
    }
  }
}
