package job4j.concurrent;

import java.util.Arrays;

public class ConsoleProgress implements Runnable{


    @Override
    public void run(){
        int i = 0;
        var process = new char[] {'-', '\\', '|', '/'};
        while (!Thread.currentThread().isInterrupted()) {
            System.out.print("\rload: " + process[i++]);
            i = i == 3 ? 0 : i;
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
                break;
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
