package ru.job4j;

import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.atomic.AtomicInteger;

@ThreadSafe
public class CASCount {
    private final AtomicInteger count = new AtomicInteger();

    public void increment() {
        int temp = count.get();
        if (!count.compareAndSet(temp, count.incrementAndGet())) {
            throw new UnsupportedOperationException("Count is not impl.");
        }
    }

    public int get() {
        return count.get();
    }
}