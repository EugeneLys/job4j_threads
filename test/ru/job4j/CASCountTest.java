package ru.job4j;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CASCountTest {

    @Test
    void whenTogether() throws InterruptedException {
        CASCount cas = new CASCount();
        Thread first = new Thread(cas::increment);
        Thread second = new Thread(cas::increment);
        int expected = 2;
        first.start();
        second.start();
        first.join();
        second.join();
        System.out.println(cas.get());
        assertEquals(expected, cas.get());
    }

    @Test
    void whenStepByStep() throws InterruptedException {
        CASCount cas = new CASCount();
        Thread first = new Thread(cas::increment);
        Thread second = new Thread(cas::increment);
        first.start();
        first.join();
        assertEquals(1, cas.get());
        second.start();
        second.join();
        assertEquals(2, cas.get());
    }

}