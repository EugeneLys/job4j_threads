package ru.job4j.io;

import java.io.IOException;
import java.util.function.Predicate;

public interface IReader {

    String getContent(Predicate<Integer> filter) throws IOException;
}
