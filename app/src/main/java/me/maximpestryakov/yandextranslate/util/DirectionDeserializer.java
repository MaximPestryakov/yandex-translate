package me.maximpestryakov.yandextranslate.util;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

import io.realm.Realm;
import me.maximpestryakov.yandextranslate.model.Direction;
import me.maximpestryakov.yandextranslate.model.Language;

public class DirectionDeserializer implements JsonDeserializer<Direction> {

    @Override
    public Direction deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {

        Direction direction = null;
        try (Realm realm = Realm.getDefaultInstance()) {
            String directionCode = json.getAsString();
            String from = directionCode.substring(0, 2);
            Language languageFrom = realm.where(Language.class).equalTo("code", from).findFirst();
            if (languageFrom == null) {
                languageFrom = realm.createObject(Language.class, from);
            }
            String to = directionCode.substring(3, 5);
            Language languageTo = realm.where(Language.class).equalTo("code", to).findFirst();
            if (languageTo == null) {
                languageTo = realm.createObject(Language.class, from);
            }
            direction = realm.createObject(Direction.class, directionCode);
            direction.setFrom(languageFrom);
            direction.setTo(languageTo);
        }
        return direction;
    }
}
