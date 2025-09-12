package ru.job4j;

import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

class SimpleBlockingQueueTest {

    @Test
    void whenProducerBegins() throws InterruptedException {
            AtomicInteger result = new AtomicInteger();
            SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue(new LinkedList(), 1);
            Thread producer = new Thread(() -> {
                try {
                    queue.offer(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
            Thread consumer = new Thread(() -> {
                try {
                    result.set(queue.poll());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
            producer.start();
            Thread.sleep(100);
            consumer.start();
            producer.join();
            consumer.join();
        assertEquals(1, result.get());
    }

    @Test
    void whenConsumerBegins() throws InterruptedException {
        AtomicInteger result = new AtomicInteger();
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue(new LinkedList(), 1);
        Thread producer = new Thread(() -> {
            try {
                queue.offer(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        Thread consumer = new Thread(() -> {
            try {
                result.set(queue.poll());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        consumer.start();
        Thread.sleep(100);
        producer.start();
        producer.join();
        consumer.join();
        assertEquals(1, result.get());
    }

    @Test
    void whenFetchAllThenGetIt() throws InterruptedException {
        final CopyOnWriteArrayList<Integer> buffer = new CopyOnWriteArrayList<>();
        final SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(new LinkedList<>(), 3);
        Thread producer = new Thread(
                () -> {
                    for (int i = 0; i < 5; i++) {
                        try {
                            queue.offer(i);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
        );
        producer.start();
        Thread consumer = new Thread(
                () -> {
                    while (!queue.isEmpty() || !Thread.currentThread().isInterrupted()) {
                        try {
                            buffer.add(queue.poll());
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            Thread.currentThread().interrupt();
                        }
                    }
                }
        );
        consumer.start();
        producer.join();
        consumer.interrupt();
        consumer.join();
        assertThat(buffer).containsExactly(0, 1, 2, 3, 4);
    }

    @Test
    void whenCorrectWithString() throws InterruptedException {
        final CopyOnWriteArrayList<String> buffer = new CopyOnWriteArrayList<>();
        SimpleBlockingQueue<String> queue = new SimpleBlockingQueue<>(new LinkedList<>(), 2);
        List<String> list = List.of("one", "two", "three", "four");
        Thread producer = new Thread(
                () -> {
                    for (String s : list) {
                        try {
                            queue.offer(s);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
        );
        producer.start();
        Thread consumer = new Thread(
                () -> {
                    while (!(queue.isEmpty()) || !Thread.currentThread().isInterrupted()) {
                        try {
                            buffer.add(queue.poll());
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            Thread.currentThread().interrupt();
                        }
                    }
                }
        );
        consumer.start();
        producer.join();
        consumer.interrupt();
        consumer.join();
        assertThat(buffer).containsExactly("one", "two", "three", "four");
    }
}
