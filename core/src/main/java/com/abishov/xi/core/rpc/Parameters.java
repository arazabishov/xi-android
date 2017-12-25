package com.abishov.xi.core.rpc;

import android.support.annotation.Nullable;
import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

@AutoValue
public abstract class Parameters {

  @Nullable
  @SerializedName("file_path")
  public abstract String filePath();

  @Nullable
  @SerializedName("update")
  public abstract Update update();

  public static Parameters create(@Nullable String filePath) {
    return new AutoValue_Parameters(filePath, null);
  }

  public static TypeAdapter<Parameters> typeAdapter(Gson gson) {
    return new AutoValue_Parameters.GsonTypeAdapter(gson);
  }
}
