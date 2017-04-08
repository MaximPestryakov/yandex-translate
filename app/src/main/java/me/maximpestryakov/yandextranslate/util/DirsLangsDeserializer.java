package me.maximpestryakov.yandextranslate.util;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import me.maximpestryakov.yandextranslate.model.Direction;
import me.maximpestryakov.yandextranslate.model.DirsLangs;
import me.maximpestryakov.yandextranslate.model.Language;

public class DirsLangsDeserializer implements JsonDeserializer<DirsLangs> {

    private static final String LANGS_KEY = "langs";
    private static final String DIRS_KEY = "dirs";

    @Override
    public DirsLangs deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {

        DirsLangs dirsLangs = new DirsLangs();
        Set<Entry<String, JsonElement>> entries = json.getAsJsonObject().entrySet();
        Map<String, JsonElement> map = new HashMap<>();
        for (Entry<String, JsonElement> entry : entries) {
            map.put(entry.getKey(), entry.getValue());
        }

        List<Language> languages = new ArrayList<>();
        entries = map.get(LANGS_KEY).getAsJsonObject().entrySet();
        for (Entry<String, JsonElement> entry : entries) {
            String code = entry.getKey();
            String title = entry.getValue().getAsString();
            languages.add(new Language(code, title));
        }
        Collections.sort(languages);
        dirsLangs.setLanguages(languages);

        List<Direction> directions = new ArrayList<>();
        JsonArray dirsJsonArray = map.get(DIRS_KEY).getAsJsonArray();
        for (JsonElement dirJson : dirsJsonArray) {
            Direction direction = new Direction();
            String dir = dirJson.getAsString();
            direction.setValue(dir);
            int del = dir.indexOf('-');
            String from = dir.substring(0, del);
            String to = dir.substring(del + 1);
            Language language = new Language();
            language.setCode(from);
            int fromIndex = Collections.binarySearch(languages, language);
            direction.setFrom(languages.get(fromIndex));
            language.setCode(to);
            int toIndex = Collections.binarySearch(languages, language);
            direction.setTo(languages.get(toIndex));
            directions.add(direction);
        }
        dirsLangs.setDirections(directions);

        return dirsLangs;
    }
}
