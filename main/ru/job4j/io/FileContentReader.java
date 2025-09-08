package ru.job4j.io;

import java.io.*;
import java.util.function.Predicate;

public class FileContentReader implements IReader {
    private final File file;

    FileContentReader(ParseFile parseFile) {
        this.file = parseFile.getFile();
    }

    @Override
    public synchronized String getContent(Predicate<Integer> filter) throws IOException {
        String output = "";
        try (BufferedInputStream input = new BufferedInputStream(new FileInputStream(file))) {
            int data;
            while ((filter.test(data = input.read()))) {
                    output += (char) data;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return output;
    }
}
