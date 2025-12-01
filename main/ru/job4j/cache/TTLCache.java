package ru.job4j.cache;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class TTLCache {

    private ConcurrentHashMap<String,String> cache = new ConcurrentHashMap<>();

    public void put(String key, String value, long ttl) {
        cache.put(key, value);
        Executors.newSingleThreadScheduledExecutor().schedule(() -> delete(key), ttl, TimeUnit.MILLISECONDS);
    }

    public String get(String key) {
        return cache.get(key);
    }

    private String delete(String key) {
        return cache.remove(key);
    }
}
