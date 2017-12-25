package com.abishov.xi.core.rpc;

import android.support.annotation.Nullable;
import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

@AutoValue
public abstract class Call {

  @Nullable
  @SerializedName("id")
  public abstract Integer id();

  @Nullable
  @SerializedName("method")
  public abstract String method();

  @Nullable
  @SerializedName("params")
  public abstract Parameters parameters();

  public static Call newView(@Nullable Integer id, Parameters parameters) {
    return new AutoValue_Call(id, "new_view", parameters);
  }

  public static TypeAdapter<Call> typeAdapter(Gson gson) {
    return new AutoValue_Call.GsonTypeAdapter(gson);
  }
}
