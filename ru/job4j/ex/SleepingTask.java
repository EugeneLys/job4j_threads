package ru.job4j.ex;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class SleepingTask implements Runnable {
    int id;

    SleepingTask(int id) {
        this.id = id;
    }

    public void run() {
        try {
            System.out.println("Start " + id);
            long pause = (long) (Math.random()*10);
            TimeUnit.SECONDS.sleep(pause);
            System.out.println(id + " finished after " + pause + " seconds.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        for (int i = 0; i < 5; i++) {
            executorService.submit(new SleepingTask(i));
        }
        executorService.shutdown();
    }
}
