package me.maximpestryakov.yandextranslate.util;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

public class RealmStringDeserializer implements JsonDeserializer<RealmString> {

    @Override
    public RealmString deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        String value = json.getAsString();
        return new RealmString(value);
    }
}
