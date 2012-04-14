package com.hystericalporpoises.dns;

import com.google.gson.Gson;

public final class JsonService {

  private static final JsonService SERVICE = new JsonService();

  private JsonService() {}

  private Gson gson = new Gson();

  public static String serialize(Object obj) {
    String json = SERVICE.gson.toJson(obj);
    return json;
  }

  public static <T> T deserialize(String json, Class<T> clazz) {
    T obj = SERVICE.gson.fromJson(json, clazz);
    return obj;
  }

}
