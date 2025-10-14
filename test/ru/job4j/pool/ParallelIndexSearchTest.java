package ru.job4j.pool;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class ParallelIndexSearchTest {

    @Test
    void whenIncorrectTypeOfElement() {
        Integer[] array = new Integer[100];
        for (int i = 0; i < array.length; i++) {
            array[i] = i;
        }
        ParallelIndexSearch parallelIndexSearch = new ParallelIndexSearch(array, "Hello", 0, 100);
        assertThatThrownBy(() -> parallelIndexSearch.search(array, "Hello"))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void whenNoSuchElement() {
        Integer[] array = new Integer[100];
        for (int i = 0; i < array.length; i++) {
            array[i] = i;
        }
        ParallelIndexSearch<Integer> parallelIndexSearch = new ParallelIndexSearch<>(array, 10, 0, 100);
        assertThat(parallelIndexSearch.search(array, 101)).isEqualTo(-1);
    }

    @Test
    void whenCorrectSearchOfInteger() {
        Integer[] array = new Integer[3];
        for (int i = 0; i < array.length; i++) {
            array[i] = i;
        }
        ParallelIndexSearch<Integer> parallelIndexSearch = new ParallelIndexSearch<>(array, 1,0, 2);
        assertThat(parallelIndexSearch.search(array, 1)).isEqualTo(1);
    }

    @Test
    void whenCorrectSearchOfString() {
        String[] array = new String[] {"Job", "for", "Java"};
        ParallelIndexSearch<String> parallelIndexSearch = new ParallelIndexSearch<>(array, "Java",0, 2);
        assertThat(parallelIndexSearch.search(array, "Java")).isEqualTo(2);
    }
}