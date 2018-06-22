package com.example.sami.popularmovies.services;

public interface OnAsyncEventListener<T> {
    void onSuccess(T object);
    void onFailure(Exception e);
    void inProcess();
}
