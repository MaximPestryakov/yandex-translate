package me.maximpestryakov.yandextranslate.api;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiManager {

    private static YandexTranslateApi api;

    private ApiManager() {
    }

    public static void init() {

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(chain -> {
                    Request originalRequest = chain.request();
                    HttpUrl originalUrl = originalRequest.url();

                    HttpUrl url = originalUrl.newBuilder()
                            .addQueryParameter("key", YandexTranslateApi.API_KEY)
                            .build();

                    Request request = originalRequest.newBuilder()
                            .url(url)
                            .build();

                    return chain.proceed(request);
                })
                .build();

        api = new Retrofit.Builder()
                .baseUrl("https://translate.yandex.net/api/v1.5/tr.json/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
                .create(YandexTranslateApi.class);
    }

    public static YandexTranslateApi getApi() {
        return api;
    }
}
