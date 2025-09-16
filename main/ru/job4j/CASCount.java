package ru.job4j;

import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.atomic.AtomicInteger;

@ThreadSafe
public class CASCount {
    private final AtomicInteger count = new AtomicInteger();

    public void increment() {
        int temp = count.get() + 1;
        do {
            count.incrementAndGet();
        } while (!count.compareAndSet(temp, count.get()));

    }

    public int get() {
        return count.get();
    }
}