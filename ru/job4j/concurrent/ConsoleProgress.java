package job4j.concurrent;

import java.util.Arrays;

public class ConsoleProgress implements Runnable{


    @Override
    public void run(){
        int i = 0;
        var process = new char[] {'-', '\\', '|', '/'};
        while (!Thread.currentThread().isInterrupted()) {
            try {
                System.out.print("\rload: " + process[i++]);
                i = i == 3 ? 0 : i;
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread progress = new Thread(new ConsoleProgress());
        progress.start();
        Thread.sleep(5000);
        progress.interrupt();
    }
}
