package com.abishov.xi.core.rpc;

import android.support.annotation.Nullable;
import com.abishov.xi.core.rpc.AutoValue_Update.GsonTypeAdapter;
import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;
import java.util.List;

@AutoValue
public abstract class Update {

  @Nullable
  @SerializedName("ops")
  public abstract List<Operation> operations();

  public static TypeAdapter<Update> typeAdapter(Gson gson) {
    return new GsonTypeAdapter(gson);
  }
}
