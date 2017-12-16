package com.abishov.xi.core.rpc;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

@AutoValue
public abstract class Parameters {

  @SerializedName("file_path")
  public abstract String filePath();

  public static Parameters create(String filePath) {
    return new AutoValue_Parameters(filePath);
  }

  public static TypeAdapter<Parameters> typeAdapter(Gson gson) {
    return new AutoValue_Parameters.GsonTypeAdapter(gson);
  }
}
