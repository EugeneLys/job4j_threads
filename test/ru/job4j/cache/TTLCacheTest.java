package ru.job4j.cache;

import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicReference;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNull;

class TTLCacheTest {

    /*
    Put value in cash, get during TTL and get NULL AFTER TTL
     */
    @Test
    void whenFalseGetAfterTTL() throws InterruptedException {
        TTLCache cache = new TTLCache();
        cache.put("Russia", "Moscow", 1000);
        assertThat(cache.get("Russia").equals("Moscow")).isTrue();
        Thread.sleep(1111);
        assertNull(cache.get("Russia"));
    }

    /*
    MULTITHREADING
    Writing value to var result

    1. Put value in first thread
    2. Get in second during TTL
     */
    @Test
    void whenTrueGetWithMultiThreads() throws InterruptedException {
        TTLCache cache = new TTLCache();
        var result = new AtomicReference<>("");
        Thread first = new Thread(() -> cache.put("Russia", "Moscow", 1000));
        Thread second = new Thread(() -> result.set(cache.get("Russia")));
        first.start();
        second.start();
        first.join();
        second.join();
        assertThat(result.get().equals("Moscow")).isTrue();
    }

    /*
    MULTITHREADING
    Writing value to var result

    1. Put value in first thread
    2. Get in second during TTL
    3. Get NULL in third AFTER TTL
     */
    @Test
    void whenFalseGetAfterTTLWithMultiThreads() throws InterruptedException {
        TTLCache cache = new TTLCache();
        var result = new AtomicReference<>("");
        Thread first = new Thread(() -> cache.put("Russia", "Moscow", 1000));
        Thread second = new Thread(() -> result.set(cache.get("Russia")));
        Thread third = new Thread(() -> result.set(cache.get("Russia")));
        first.start();
        second.start();
        first.join();
        second.join();
        assertThat(result.get().equals("Moscow")).isTrue();
        Thread.sleep(1111);
        third.start();
        third.join();
        assertThat(result.get()).isNull();
    }
}