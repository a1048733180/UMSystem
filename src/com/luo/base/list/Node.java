package com.luo.base.list;

/**
 * @Description 链表结点类
 * @param <T>
 */
public class Node<T> {

    public T data;//数据

    public Node<T> next;

    public Node(T data) {
        this.data = data;
    }

    public Node(T data, Node<T> next) {
        this.data = data;
        this.next = next;
    }

    public Node() {
        this(null, null);
    }

    public T getData() {
        return data;
    }

    public void setData(T Data) {
        this.data = Data;
    }

    public Node getNext() {
        return next;
    }

    public void setNext(Node next) {
        this.next = next;
    }


    @Override
    public String toString() {
        return this.data.toString();

    }
}
