package com.abishov.xi.core.rpc;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

@AutoValue
public abstract class Method {

  @SerializedName("id")
  public abstract int id();

  @SerializedName("method")
  public abstract String method();

  @SerializedName("params")
  public abstract Parameters parameters();

  public static Method newView(int id, Parameters parameters) {
    return new AutoValue_Method(id, "new_view", parameters);
  }

  public static TypeAdapter<Method> typeAdapter(Gson gson) {
    return new AutoValue_Method.GsonTypeAdapter(gson);
  }
}
