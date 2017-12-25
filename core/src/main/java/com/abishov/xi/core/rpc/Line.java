package com.abishov.xi.core.rpc;

import com.abishov.xi.core.rpc.AutoValue_Line.GsonTypeAdapter;
import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

@AutoValue
public abstract class Line {

  @SerializedName("text")
  public abstract String text();

  public static TypeAdapter<Line> typeAdapter(Gson gson) {
    return new GsonTypeAdapter(gson);
  }
}
