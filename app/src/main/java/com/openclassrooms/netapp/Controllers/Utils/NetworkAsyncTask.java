package com.openclassrooms.netapp.Controllers.Utils;

import android.os.AsyncTask;
import android.util.Log;

import java.lang.ref.WeakReference;

public class NetworkAsyncTask extends AsyncTask<String, Void, String> {
    public NetworkAsyncTask (Listeners callback){
        this.callback = new WeakReference<>(callback);
    }

    public interface Listeners {
        void doInBackground();
        void onPreExecute();
        void onPostExecute(String success);
    }

    private final WeakReference<Listeners> callback;

    @Override
    protected String doInBackground(String... url) {
        this.callback.get().doInBackground();
        Log.e("TAG", "AsyncTask is fetching data...");
        return MyHttpURLConnection.startHttpRequest(url[0]);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        this.callback.get().onPreExecute();
        Log.e("TAG", "AsyncTask is started.");
    }

    @Override
    protected void onPostExecute(String success) {
        super.onPostExecute(success);
        this.callback.get().onPostExecute(success);
        Log.e("TAG", "AsyncTask is finished.");
    }
}
