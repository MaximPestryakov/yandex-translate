package me.maximpestryakov.yandextranslate.util;

import retrofit2.Call;
import retrofit2.Response;

public class Callback<T> implements retrofit2.Callback<T> {

    private ResponseCallback<T> responseCallback;
    private FailureCallback<T> failureCallback;

    public Callback(ResponseCallback<T> responseCallback, FailureCallback<T> failureCallback) {
        this.responseCallback = responseCallback;
        this.failureCallback = failureCallback;
    }

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        responseCallback.onResponse(call, response);
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        failureCallback.onFailure(call, t);
    }

    public interface ResponseCallback<T> {
        void onResponse(Call<T> call, Response<T> response);
    }

    public interface FailureCallback<T> {
        void onFailure(Call<T> call, Throwable t);
    }
}
