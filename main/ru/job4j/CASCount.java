package ru.job4j;

import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.atomic.AtomicInteger;

@ThreadSafe
public class CASCount {
    private final AtomicInteger count = new AtomicInteger();

    public void increment() {
        int temp = count.get();
        do {
            temp++;
        } while (!count.compareAndSet(count.get(), temp));
    }

    public int get() {
        return count.get();
    }
}