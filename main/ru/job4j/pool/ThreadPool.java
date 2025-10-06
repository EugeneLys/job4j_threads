package ru.job4j.pool;

import ru.job4j.SimpleBlockingQueue;

import java.util.LinkedList;
import java.util.List;

public class ThreadPool {
    private final List<Thread> threads = new LinkedList<>();
    private final SimpleBlockingQueue<Runnable> tasks = new SimpleBlockingQueue<>(new LinkedList<>(), 10);

    public ThreadPool() {
        int size = Runtime.getRuntime().availableProcessors();
        while (size > 0) {
            threads.add(new Thread(() -> {
                try {
                    tasks.poll().run();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }));
            size--;
        }
        threads.forEach(Thread::start);
    }

    public void work(Runnable job) throws InterruptedException {
            tasks.offer(job);
    }

    public void shutdown() {
        threads.forEach(Thread::interrupt);
    }
}
