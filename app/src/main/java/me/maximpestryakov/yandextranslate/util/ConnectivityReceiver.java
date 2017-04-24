package me.maximpestryakov.yandextranslate.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.net.ConnectivityManagerCompat;

public class ConnectivityReceiver extends BroadcastReceiver {

    private static OnNetworkChangeStateListener onNetworkChangeStateListener;

    public static void setOnNetworkChangeStateListener(OnNetworkChangeStateListener onNetworkChangeStateListener) {
        ConnectivityReceiver.onNetworkChangeStateListener = onNetworkChangeStateListener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (onNetworkChangeStateListener != null) {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = ConnectivityManagerCompat.getNetworkInfoFromBroadcast(cm, intent);
            onNetworkChangeStateListener.onChange(networkInfo != null && networkInfo.isConnectedOrConnecting());
        }
    }

    public interface OnNetworkChangeStateListener {
        void onChange(boolean isConnected);
    }
}
