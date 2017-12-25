package com.abishov.xi.core.rpc;

import android.support.annotation.Nullable;
import com.abishov.xi.core.rpc.AutoValue_Operation.GsonTypeAdapter;
import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;
import java.util.List;

@AutoValue
public abstract class Operation {

  @Nullable
  @SerializedName("lines")
  public abstract List<Line> lines();

  public static TypeAdapter<Operation> typeAdapter(Gson gson) {
    return new GsonTypeAdapter(gson);
  }
}
