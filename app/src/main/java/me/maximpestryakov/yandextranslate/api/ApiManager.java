package me.maximpestryakov.yandextranslate.api;

import com.facebook.stetho.okhttp3.StethoInterceptor;

import me.maximpestryakov.yandextranslate.App;
import me.maximpestryakov.yandextranslate.BuildConfig;
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
        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder()
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
                });
        if (BuildConfig.DEBUG) {
            clientBuilder.addNetworkInterceptor(new StethoInterceptor());
        }
        OkHttpClient client = clientBuilder.build();

        api = new Retrofit.Builder()
                .baseUrl(YandexTranslateApi.URL)
                .addConverterFactory(GsonConverterFactory.create(App.getGson()))
                .client(client)
                .build()
                .create(YandexTranslateApi.class);
    }

    public static YandexTranslateApi getApi() {
        return api;
    }
}
