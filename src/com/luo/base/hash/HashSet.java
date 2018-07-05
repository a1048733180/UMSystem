package com.luo.base.hash;

import com.luo.base.list.Node;
import com.luo.base.list.SeqList;
import com.luo.base.list.SinglyList;

/*
 *  哈希表类
 */
public class HashSet<T> {

	private SinglyList<T>[] table; // 单链表数组，存放同义词

	private int count = 0; // 元素个数

	private static final float LOAD_FACTRR = 0.75f; // 装填因子，元素个数和元素容量之比

	public HashSet(int length) {
		if (length < 10) {
			length = 10;
		}
		this.table = new SinglyList[length];
		for (int i = 0; i < this.table.length; i++) {
			this.table[i] = new SinglyList<T>();
		}
	}

	public HashSet() {
		this(16);
	}

	private int hash(T x) {
		int key = Math.abs(x.hashCode());
		return key % this.table.length;
	}

	// 返回查找到的关键词为key元素，查找不到则返回null
	public SeqList<T> search(T key) {
		SeqList<T> tt = this.table[this.hash(key)].search(key);
		return tt;
	}

	// 插入x元素
	public boolean add(T x) {
		if (this.count >= this.table.length * LOAD_FACTRR) {
			SinglyList<T>[] temp = this.table;
			this.table = new SinglyList[this.table.length * 2];
			for (int i = 0; i < this.table.length; i++) {
				this.table[i] = new SinglyList<T>();
			}
			this.count = 0;
			for (int i = 0; i < temp.length; i++) {
				for (Node<T> p = temp[i].head.next; p != null; p = p.next) {
					this.add(p.data);
				}
			}
		}
		boolean insert = this.table[this.hash(x)].insertSame(x) != null;
		if (insert) {
			this.count++;
		}
		return insert;
	}

	// 这里的remove是删除一个而已
	public T remove(T key) {
		T x = this.table[this.hash(key)].remove(key);
		if (x != null) {
			this.count--;
		}
		return x;
	}

}
