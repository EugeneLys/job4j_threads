package ru.job4j;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;
import java.util.LinkedList;
import java.util.Queue;

@ThreadSafe
public class SimpleBlockingQueue<T> {

    @GuardedBy("this")
    private Queue<T> queue = new LinkedList<>();
    private final int limit;
    private int count;

    public SimpleBlockingQueue(Queue<T> queue, int limit) {
        if (limit < 1) {
            throw new IllegalArgumentException();
        }
        this.queue = queue;
        this.limit = limit;
    }

    public synchronized void offer(T value) throws InterruptedException {
        while (count == limit) {
            wait();
        }
        queue.offer(value);
        count++;
        notifyAll();
    }

    public synchronized T poll() throws InterruptedException {
        while (queue.isEmpty()) {
            wait();
        }
        T result = queue.poll();
        count--;
        notifyAll();
        return result;
    }
}
