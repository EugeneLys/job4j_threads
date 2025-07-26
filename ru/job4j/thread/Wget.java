package ru.job4j.thread;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import static java.lang.Thread.sleep;

public class Wget implements Runnable {
    private final String url;
    private final int speed;

    public Wget(String url, int speed) {
        this.url = url;
        this.speed = speed;
    }

    @Override
    public void run() {
        var file = new File("tmp.xml");
        long pause = 0;
        int count = 0;
        try (var input = new URL(url).openStream();
             var output = new FileOutputStream(file)) {
            var dataBuffer = new byte[512];
            int bytesRead;
            while ((bytesRead = input.read(dataBuffer, 0, dataBuffer.length)) != -1) {
                var downloadAt = System.nanoTime();
                output.write(dataBuffer, 0, bytesRead);
                if (count == 0) {
                    pause = getPause(dataBuffer.length, System.nanoTime() - downloadAt, speed);
                    count++;
                }
                if (pause != -1) {
                    sleep(pause);
                }
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private long getPause(int bufferSize, long time, long speed) {
        return (bufferSize * 1000000L) / time <= speed ? -1 : (bufferSize * 1000000L) / time / speed;
    }

    public static void main(String[] args) throws InterruptedException {
        String url = args[0];
        int speed = Integer.parseInt(args[1]);
        try {
            URL check = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        if (speed < 1) {
            throw new IllegalArgumentException();
        }
        Thread wget = new Thread(new Wget(url, speed));
        wget.start();
        wget.join();
    }
}