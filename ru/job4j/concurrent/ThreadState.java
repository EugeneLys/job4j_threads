package ru.job4j.concurrent;

public class ThreadState {
    public static void main(String[] args) {
        Thread first = new Thread(
                () -> System.out.println(Thread.currentThread().getName())
        );
        Thread second = new Thread(
                () -> System.out.println(Thread.currentThread().getName())
        );
        first.start();
        second.start();
        while (first.getState() != Thread.State.TERMINATED) {
            System.out.println("RUNNUNG 0");
        }
        while (second.getState() != Thread.State.TERMINATED) {
            System.out.println("RUNNING 1");
        }
        System.out.println("All threads are TERMINATED");
    }
}