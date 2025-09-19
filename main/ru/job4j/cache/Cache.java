package ru.job4j.cache;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

public class Cache {
    private final Map<Integer, Base> memory = new ConcurrentHashMap<>();

    public boolean add(Base model) throws OptimisticException {
        if (model.id() < 1) {
            throw new OptimisticException("");
        }
        return memory.putIfAbsent(model.id(), model) == null;
    }

    public boolean update(Base model) throws OptimisticException {
        Base stored = memory.get(model.id());
        if (stored.version() != model.version()) {
            throw new OptimisticException("");
        }
        Base newModel = new Base(model.id(), model.name(), model.version() + 1);
        memory.compute(model.id(), (key, value) -> newModel);
        return true;
    }

    public void delete(int id) {
        var find = findById(id);
        find.ifPresent(model -> memory.remove(model.id()));
    }

    public Optional<Base> findById(int id) {
        return Stream.of(memory.get(id)).filter(Objects::nonNull).findFirst();
    }

}
