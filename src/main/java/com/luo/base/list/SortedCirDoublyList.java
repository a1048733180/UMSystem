package com.luo.base.list;

public class SortedCirDoublyList<T extends Comparable<? super T>> extends CirDoublyList<T> {

	private SeqList<T> seqList;

	private int count;

	public SortedCirDoublyList() {
		super();
	}

	// 由单链表list构造排序循环双链表，重载深拷贝构造方法。直接插入排序，算法同由单链表构造排序单链表
	public SortedCirDoublyList(SinglyList<T> list) {
		super(); // 构造空循环双链表
		for (Node<T> p = list.head.next; p != list.head; p = p.next)// 直接插入排序算法
			this.insert(p.data); // 按值插入
	}

	// 插入x，x!=null，根据x对象大小顺序查找确定插入位置，插入在等值结点之前。
	// 返回插入结点。O(n)。覆盖父类的insert(x)方法
	public DoubleNode<T> insert(T x) {
		if (x == null) {
			throw new NullPointerException("x==null"); // 抛出空对象异常
		}
		if (this.isEmpty() || x.compareTo(this.head.prev.data) > 0) {
			count++;
			return super.insert(x);
		} // 调用父类被覆盖的insert(T)方法，最大值插入在头结点之前，即尾插入，O(1)
		DoubleNode<T> p = this.head.next;
		while (p != head && x.compareTo(p.data) > 0) { // 寻找插入位置（p指向）
			p = p.next;
		}
		DoubleNode<T> q = new DoubleNode<T>(x, p.prev, p); // 在p结点之前插入值为x结点
		p.prev.next = q;
		p.prev = q;
		count++;
		return q; // 返回插入结点
	}

	public T find(T key) {
		for (DoubleNode<T> p = this.head.next; p != head && key.compareTo(p.data) >= 0; p = p.next) {
			if (key.compareTo(p.data) == 0) // 由compareTo()提供比较对象大小和相等的依据
				return p.data;
		}
		return null;
	}

	public SeqList<T> traverse() {
		seqList = new SeqList<T>(this.size());
		DoubleNode<T> front = this.head;
		for (DoubleNode<T> p = front.next; p != front; p = p.next) {
			seqList.insert(p.data);
		}
		return seqList;
	}

	public int size() {
		return this.count;
	}

	public void delete(T t) {

		DoubleNode<T> front = this.head;
		DoubleNode<T> p = this.head.next;
		while (p != front) {
			if (t.compareTo(p.data) == 0) {
				p.prev.next = p.next; // 删除p结点，由JVM稍后释放
				p.next.prev = p.prev;
				count--;
				break;
			} else {				
				p = p.next;
			}
		}

	}
}
