package ru.job4j.pool;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class AsyncDemo {

    private static void iWork() throws InterruptedException {
        int count = 0;
        while (count <10)
        {
            System.out.println("I am working");
            TimeUnit.SECONDS.sleep(1);
            count++;
        }
    }

    public static CompletableFuture<Void> goToTrash() {
        return CompletableFuture.runAsync(() -> {
            System.out.println("I go to trash");
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("I am back from trash.");
        });
    }

    public static CompletableFuture<String> buyProduct(String product) {
        return CompletableFuture.supplyAsync(() -> {
            System.out.println("I go to shop");
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("I bought " + product);
            return product;
        });
    }

    public static void runAsyncExample() throws Exception {
        CompletableFuture<String> bm = buyProduct("milk");
        iWork();
        System.out.println("bought: " + bm.get());
        }

    public static void thenRunExample() throws Exception {
        CompletableFuture<Void> gtt = goToTrash();
        gtt.thenRun(() -> {
            int count = 0;
            while (count < 3) {
                System.out.println("I am washing hands.");
                try {
                    TimeUnit.MILLISECONDS.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                count++;
            }
            System.out.println("I finished washing hands.");
        });
        iWork();
        }

        public static void thenAcceptExample() throws Exception {
        CompletableFuture<String> bm = buyProduct("Milk");
        bm.thenAccept((product) -> System.out.println("I stored " + product + " to the frege"));
        iWork();
        System.out.println("Bought: " + bm.get());
    }

    public static void thenApplyExample() throws Exception {
        CompletableFuture<String> bm = buyProduct("Молоко")
                .thenApply((product) -> "Сын: я налил тебе в кружку " + product + ". Держи.");
        iWork();
        System.out.println(bm.get());
    }

    public static void thenComposeExample() throws Exception {
        CompletableFuture<String> result = goToTrash().thenCompose(a -> buyProduct("Milk"));
        result.get();
    }

    public static void thenCombineExample() throws Exception {
        CompletableFuture<String> result = buyProduct("Молоко")
                .thenCombine(buyProduct("Хлеб"), (r1, r2) -> "Куплены " + r1 + " и " + r2);
        iWork();
        System.out.println(result.get());
    }

    public static CompletableFuture<Void> washHands(String name) {
        return CompletableFuture.runAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(name + ", моет руки");
        });
    }

    public static void allOfExample() throws Exception {
        CompletableFuture<Void> all = CompletableFuture.allOf(
                washHands("Папа"), washHands("Мама"),
                washHands("Ваня"), washHands("Боря")
        );
        TimeUnit.SECONDS.sleep(3);
    }

    public static CompletableFuture<String> whoWashHands(String name) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return name + ", моет руки";
        });
    }

    public static void anyOfExample() throws Exception {
        CompletableFuture<Object> first = CompletableFuture.anyOf(
                whoWashHands("Папа"), whoWashHands("Мама"),
                whoWashHands("Ваня"), whoWashHands("Боря")
        );
        System.out.println("Кто сейчас моет руки?");
        TimeUnit.SECONDS.sleep(1);
        System.out.println(first.get());
    }

    public static void main(String[] args) throws Exception {
        anyOfExample();
    }
}
