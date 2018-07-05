package com.luo.base.list;

/**
 * @param <T>
 * @Description 顺序链表类
 */
public class SinglyList<T> {

	public Node<T> head; // 头指针，指向单链表的头结点
	public int count; // 元素数量

	public SinglyList() {
		this.head = new Node<>(); // 创建空链表
	}

	public SinglyList(T[] values) {
		this();
		Node<T> rear = head;// 创建一个空链表的尾结点
		for (int i = 0; i < values.length; i++) {
			rear.next = new Node<T>(values[i], null);
			rear = rear.next; // rear不断移动指向尾结点
			count++;
		}
	}

	// 判断单链表是否为空,O(1)
	public boolean isEmpty() {
		return this.head.next == null;
	}

	// 返回第i个元素，0<= i<表长度 O(n)
	public T get(int i) {
		Node<T> p = this.head.next; // 默认已经指向第一个结点元素了

		for (int j = 0; j < i && p != null; j++) {
			p = p.next;
		}
		return (i >= 0 && p != null) ? p.data : null;
	}

	public void set(int i, T x) {

	}

	// 返回链表长度
	public int size() {
		Node<T> front = head;
		Node<T> p = front.next;
		for (int j = 0; p != null; j++) {
			count++;
			p = p.next;
		}
		return count;
	}

	public String toString() {
		String str = this.getClass().getName() + "( \n"; // 返回类名
		for (Node<T> p = this.head.next; p != null; p = p.next) {
			str += p.data.toString();
			// if (p.next != null) {
			// str += ",";
			// }
		}
		return str + ")";
	}

	// 这里没有对i进行容错处理
	public Node<T> insert(int i, T x) {
		if (x == null) {
			throw new NullPointerException(" x == null");
		}
		Node<T> front = head;
		for (int j = 0; front.next != null && j < i; j++) {
			front = front.next;
		}
		front.next = new Node<T>(x, front.next);
		count++;
		return front.next; // 返回插入结点
	}

	public Node<T> insert(T x) {
		return insert(Integer.MAX_VALUE, x);
	}

	// 删除某个位置的元素，并返回位置上的元素
	public T remove(int i) {
		Node<T> front = head;
		for (int j = 0; j < i && front.next != null; j++) {
			front = front.next; // 获取要删除元素的前一个节点
		}
		if (i >= 0 && front.next != null) {
			T old = front.next.data;
			front.next = front.next.next;
			count--;
			return old;
		}

		return null;
	}

	// 散列表用 .删除首个与key相等元素结点，返回被删除元素；查找不成功返回null。
	public T remove(T key) {
		Node<T> front = this.head, p = front.next;
		while (p != null && !key.equals(p.data)) // 顺序查找首次出现的与key相等元素
		{
			front = p; // front指向p的前驱结点
			p = p.next;
		}
		if (p != null) // 若查找成功，删除front的后继（p结点）
		{
			front.next = p.next; // 包括头删除、中间/尾删除
			return p.data;
		}
		return null;
	}

	public void clean() {
		head.next = null;
	}

	public void replaceAll(T key, T x) {
		Node<T> p = head;
		for (int i = 0; p.next != null; i++) {
			if (p.next.data.equals(key)) {
				p.next.data = x;
			}
			p = p.next;
		}

	}

	// 把链表中的元素存放进数组
	public Object[] toArray() {
		Node<T> front = this.head;
		Node<T> p = front.next;
		Object[] t = null;
		for (int i = 0; i < this.count && p != null; i++) {
			t[i] = p.data;
		}
		return t;
	}

	public void delete(T x) {

	}

	// 传入一个元素与链表里面的元素比较，若相等，则返回该元素，否则返回null
	// 这里要求T是重写了equals方法
	public T equalsElements(T x) {
		Node<T> front = this.head;
		Node<T> p = front.next;
		for (int i = 0; i < this.count && p != null; i++) {
			if (p.data.equals(x)) {
				return p.data;
			}
			p = p.next;
		}
		return null;
	}

	// 散列表中用到，用于出现的元素，并放进链表中返回，查找不到返回null
	public SeqList<T> search(T key) {
		// 默认大小为64，在这里应该够用
		SeqList<T> tList =new SeqList<T>();
		for (Node<T> p = this.head.next; p != null; p = p.next)
			if (key.equals(p.data)) // 执行T类的equals(Object)方法，运行时多态
				tList.insert(p.data);
		return tList;
	}

	// 散列表中用到，用于尾插入互异元素x，若查找到与x的关键字相同元素，不插入，返回x结点
	public Node<T> insertDifferent(T x) {
		Node<T> front = this.head, p = front.next; // front是p的前驱结点
		while (p != null && !p.data.equals(x)) // 顺序查找
		{
			front = p;
			p = p.next;
		}
		if (p != null) // 查找成功，元素重复，不插入，返回p结点
		{
			System.out.println("x=" + x + "，元素重复，未插入。");
			return p;
		}
		return front.next = new Node<T>(x, null); // 尾插入值为x结点，返回插入结点
	}

	// 散列表中用到，用于可以插入相同元素x，若查找到与x的关键字相同元素，则插入在该元素之前
	public Node<T> insertSame(T x) {
		Node<T> front = this.head, p = front.next; // front是p的前驱结点
		while (p != null && !p.data.equals(x)) // 顺序查找
		{
			front = p;
			p = p.next;
		}
		if (p != null) // 查找成功，元素重复，则在该元素前插入
		{
			return front.next = new Node<T>(x, p);
		}
		return front.next = new Node<T>(x, null); // 尾插入值为x结点，返回插入结点
	}
}
