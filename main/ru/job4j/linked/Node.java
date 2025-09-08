package ru.job4j.linked;

public class Node<T> {
    private final Node<T> next;
    private final T value;

    public Node(Node<T> next, T value) {
        this.next = next;
        this.value = value;
    }

    public Node<T> getNext() {
        return next;
    }

    public void setNext(Node<T> next) {
        System.out.println("Can't set Node.next - class is immutable");
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        System.out.println("Can't set Node.value - class is immutable");
    }
}