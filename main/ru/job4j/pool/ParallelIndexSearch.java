package ru.job4j.pool;

import java.util.Objects;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ParallelIndexSearch<T> extends RecursiveTask<Integer> {
    private final T[] array;
    private final T required;
    private final int from;
    private final int to;

    public ParallelIndexSearch(T[] array, T required, int from, int to) {
        this.array = array;
        this.required = required;
        this.from = from;
        this.to = to;
    }

    @Override
    protected Integer compute() {
        if (to - from < 10) {
            return indexOf();
        }
        int middle = from + (to - from) / 2;
        ParallelIndexSearch<T> left = new ParallelIndexSearch<>(array, required, from, middle);
        ParallelIndexSearch<T> right = new ParallelIndexSearch<>(array, required, middle, to);
        left.fork();
        right.fork();
        var answer = left.join();
        return Objects.equals(answer, -1) ? right.join() : answer;
    }

    public int indexOf() {
        for (int i = from; i <= to; i++) {
            if(required.equals(array[i])) {
                return i;
            }
        }
        return -1;
    }

    public static <T> Integer search(T[] array, T required) {
        if (!(array.getClass().getComponentType() == required.getClass())) {
            throw new IllegalArgumentException();
        }
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        return forkJoinPool.invoke(new ParallelIndexSearch<>(array, required, 0, array.length - 1));
    }

}
