package ru.job4j.pool;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class RowColSum {
    public static class Sums {
        private int rowSum;
        private int colSum;

        public int getRowSum() {
            return rowSum;
        }

        public void setRowSum(int rowSum) {
            this.rowSum = rowSum;
        }

        public int getColSum() {
            return colSum;
        }

        public void setColSum(int colSum) {
            this.colSum = colSum;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Sums sums = (Sums) o;
            return rowSum == sums.rowSum && colSum == sums.colSum;
        }

        @Override
        public int hashCode() {
            return Objects.hash(rowSum, colSum);
        }
    }

    public static Sums[] sum(int[][] matrix) {
        Sums[] sums = new Sums[matrix.length];
        int rowSum = 0;
        int colSum = 0;
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                rowSum += matrix[i][j];
            }
            for (int k = 0; k < matrix.length; k++) {
                colSum += matrix[k][i];
            }
            Sums sum = new Sums();
            sum.setRowSum(rowSum);
            sum.setColSum(colSum);
            sums[i] = sum;
            rowSum = 0;
            colSum = 0;
        }
        return sums;
    }


    public static Sums[] asyncSum(int[][] matrix) throws ExecutionException, InterruptedException {
        Sums[] sums = new Sums[matrix.length];
        Map<Integer, CompletableFuture<Sums>> futures = new HashMap<>();
        for (int i = 0; i < matrix.length; i++) {
            futures.put(i, getTask(matrix, i));
        }
        for (Integer key : futures.keySet()) {
            sums[key] = futures.get(key).get();
        }
        return sums;
    }

    public static CompletableFuture<Sums> getTask(int[][] matrix, int start) {
        return CompletableFuture.supplyAsync(() -> {
            Sums result = new Sums();
            int rowSum = 0;
            int colSum = 0;
            for (int i = 0; i < matrix.length; i++) {
                rowSum += matrix[start][i];
            }
            for (int i = 0; i < matrix.length; i++) {
                colSum += matrix[i][start];
            }
            result.setColSum(colSum);
            result.setRowSum(rowSum);
            return result;
        });
    }
}