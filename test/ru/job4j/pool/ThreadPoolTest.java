package ru.job4j.pool;

import org.junit.jupiter.api.Test;

class ThreadPoolTest {


    @Test
    void whenProfilingBySystemOutPrintLn() throws InterruptedException {
        Runnable task = () -> System.out.println("Hello " + Math.random());
        ThreadPool threadPool = new ThreadPool();
        for (int i = 0; i < 10; i++) {
            threadPool.work(task);
        }
    }
}