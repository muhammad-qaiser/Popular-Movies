package com.example.sami.popularmovies.services;

import android.os.AsyncTask;

import com.example.sami.popularmovies.utils.NetworkUtils;

import java.io.IOException;
import java.net.URL;

public class FetchMoviesData extends AsyncTask<URL , Void, String >
{
    private Exception mException;
    private OnAsyncEventListener<String> mCallBack;

    public FetchMoviesData(OnAsyncEventListener callBack)
    {
        if(callBack != null)
        mCallBack = callBack;
    }

    @Override
    protected String doInBackground(URL... urls) {
        String data = "";
        try {
            data = NetworkUtils.getResponseFromHttpUrl(urls[0]);
        } catch (IOException e) {
            mException = e;
        }
        return data;
    }

    @Override
    protected void onPreExecute() {

       mCallBack.inProcess();
    }

    @Override
    protected void onPostExecute(String s) {
       if(mCallBack != null)
       {
           if(mException == null)
               mCallBack.onSuccess(s);
           else
               mCallBack.onFailure(mException);
       }
    }
}
