package me.maximpestryakov.yandextranslate.api;

import me.maximpestryakov.yandextranslate.model.Translation;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface YandexTranslateApi {

    String API_KEY = "trnsl.1.1.20170315T185544Z.ac8a3cd22d02fb61.f9fefe3714c7084a3c51ac24dc7752efa8ceb078";

    @GET("translate")
    Call<Translation> translate(@Query("text") String text, @Query("lang") String lang, @Query("format") String format);
}
