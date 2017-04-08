package me.maximpestryakov.yandextranslate.util;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import io.realm.Realm;
import me.maximpestryakov.yandextranslate.model.Language;

public class LanguagesDeserializer implements JsonDeserializer<List<Language>> {
    @Override
    public List<Language> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {

        List<Language> languages = new ArrayList<>();
        try (Realm realm = Realm.getDefaultInstance()) {
            Set<Entry<String, JsonElement>> entries = json.getAsJsonObject().entrySet();
            for (Entry<String, JsonElement> entry : entries) {
                String code = entry.getKey();
                String title = entry.getValue().getAsString();
                Language language = realm.createObject(Language.class, code);
                language.setTitle(title);
                languages.add(language);
            }
        }

        return languages;
    }
}
