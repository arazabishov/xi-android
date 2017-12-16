package com.abishov.xi.core;

import com.google.gson.TypeAdapterFactory;
import com.ryanharter.auto.value.gson.GsonTypeAdapterFactory;

@GsonTypeAdapterFactory
abstract class XiAdapterFactory implements TypeAdapterFactory {

  static TypeAdapterFactory create() {
    return new AutoValueGson_XiAdapterFactory();
  }
}