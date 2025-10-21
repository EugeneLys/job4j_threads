package ru.job4j.pool;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;

class RowColSumTest {

    @Test
    void whenAsyncCorrect() throws ExecutionException, InterruptedException {
        int[][] matrix = new int[][] {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        RowColSum.Sums sums0 = new RowColSum.Sums();
        sums0.setRowSum(6);
        sums0.setColSum(12);
        RowColSum.Sums sums1 = new RowColSum.Sums();
        sums1.setRowSum(15);
        sums1.setColSum(15);
        RowColSum.Sums sums2 = new RowColSum.Sums();
        sums2.setRowSum(24);
        sums2.setColSum(18);
        RowColSum.Sums[] expected = new RowColSum.Sums[] {sums0, sums1, sums2};
        assertArrayEquals(RowColSum.asyncSum(matrix), expected);
    }

    @Test
    void whenCorrect() {
        int[][] matrix = new int[][] {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        RowColSum.Sums sums0 = new RowColSum.Sums();
        sums0.setRowSum(6);
        sums0.setColSum(12);
        RowColSum.Sums sums1 = new RowColSum.Sums();
        sums1.setRowSum(15);
        sums1.setColSum(15);
        RowColSum.Sums sums2 = new RowColSum.Sums();
        sums2.setRowSum(24);
        sums2.setColSum(18);
        RowColSum.Sums[] expected = new RowColSum.Sums[] {sums0, sums1, sums2};
        assertArrayEquals(RowColSum.sum(matrix), expected);
    }
    @Test
    void whenAsyncIncorrect() throws ExecutionException, InterruptedException {
        int[][] matrix = new int[][] {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        RowColSum.Sums sums0 = new RowColSum.Sums();
        sums0.setRowSum(5);
        sums0.setColSum(12);
        RowColSum.Sums sums1 = new RowColSum.Sums();
        sums1.setRowSum(10);
        sums1.setColSum(15);
        RowColSum.Sums sums2 = new RowColSum.Sums();
        sums2.setRowSum(-1);
        sums2.setColSum(18);
        RowColSum.Sums[] expected = new RowColSum.Sums[] {sums0, sums1, sums2};
        assertFalse(Arrays.equals(RowColSum.asyncSum(matrix), expected));
    }

}