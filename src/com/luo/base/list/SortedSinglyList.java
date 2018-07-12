package com.luo.base.list;

import java.util.Iterator;

/**
 * @param <T>
 * @Description 排序循环双链表
 */
public class SortedSinglyList<T extends Comparable<? super T>> extends SinglyList<T> {

	private int count;

	private SeqList<T> seqList;

	// 构造空排序链表，默认调用父类的空构造方法
	public SortedSinglyList() {
		super();
	}

	// 重写带参构造方法，以T重写的compareTo去决定排序规则
	public SortedSinglyList(T[] values) {
		super();
		for (int i = 0; i < values.length; i++) {
			this.insert(values[i]);
		}
	}

	// 返回元素数量
	public int getCount() {
		return count;
	}

	// 重写insert方法
	@Override
	public Node<T> insert(T t) {
		Node<T> front = this.head;
		Node<T> p = front.next; // front指向p的前驱结点
		count++;
		while (p != null && t.compareTo(p.data) > 0) {
			front = p;
			p = p.next;
		}
		front.next = new Node<T>(t, p); // 在front之后、p之前插入值为T的结点
		return front.next; // 返回插入结点
	}

	// 删除
	@Override
	public void delete(T t) {
		Node<T> front = this.head;
		Node<T> p = front.next;
		while (p != null) {
			if (t.compareTo(p.data) == 0) {
				front.next = p.next;
				count--;
				break;
			} else {
				front = p;
				p = p.next;
			}
		}
	}

	// 获取某个元素
	public T get(int i) {

		Node<T> front = this.head;
		Node<T> p = front.next;
		for (int j = 0; p != null && j < i; j++) {
			p = p.next;
		}
		return (i >= 0 && p != null) ? p.data : null;
	}

	// 遍历链表，把每个元素存放入一个顺序表中，方便查找

	public SeqList<T> traverse() {
		seqList = new SeqList<T>(this.getCount());
		Node<T> front = this.head;
		Node<T> p = front.next;
		for (int j = 0; p != null; j++) {
			seqList.insert(j, p.data);
			p = p.next;
		}
		return seqList;
	}

	// 查找某个元素,并返回
	public T find(T t) {
		Node<T> front = this.head;
		Node<T> p = front.next;
		for (int j = 0; p != null; j++) {
			if (t.compareTo(p.data) == 0) {
				return p.data;
			}
			p = p.next;
		}
		return null;
	}

	public int size() {
		return this.count;
	}

}
