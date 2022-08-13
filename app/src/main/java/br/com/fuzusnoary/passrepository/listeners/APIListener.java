package br.com.fuzusnoary.passrepository.listeners;

public interface APIListener<T> {
    void onSuccess(T result);
    void onFailure(String message);
}
