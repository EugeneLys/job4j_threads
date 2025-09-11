package ru.job4j;

import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SimpleBlockingQueueTest {

    @Test
    void whenProducerBegins() throws InterruptedException {
            AtomicInteger result = new AtomicInteger();
            SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue(new LinkedList());
            Thread producer = new Thread(() -> queue.offer(1));
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
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue(new LinkedList());
        Thread producer = new Thread(() -> queue.offer(1));
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
}